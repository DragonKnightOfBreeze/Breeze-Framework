
# 解决思路

> 注意：
> 
> 需要开启计算节点参数（配置->计算节点参数配置）
> 
> * enableOracleFunction=1
> * skipDatatypeCheck=1

> 注意：
> 
> 仅提取成导入表结构的sql，因此删去以下部分：
>
> * `create type`语句
> * `create function`语句
> * `copy ... from stdin`语句（用于导入数据）

注释掉脚本开始的`create schema`等语句，首先创建逻辑库`ezoffice`并导入

```
mysql -uroot -proot -hxxx -P3323 ezoffice < dump-oakaifa-202105240925_new.sql
```

注释掉脚本开始的`set ...`等语句

删除`ezoffice.`

移除`create type`语句（创建表结构时不会用到）

移除`create function`语句（到1388行）（创建表结构时不会用到）

移除`copy tableName(cols) from stdin`语句（导入数据）（从118749行开始）（（创建表结构时不会用到））

注释掉`alter ... ... owner to ...`语句（正则：`(ALTER.+?OWNER TO)`->`-- $1`）（不需要）

移除` create_date timestamp(0) without time zone`中的` without time zone`（不支持）

嵌套的括号`((...))`改为`(...)`（正则：`\(\(([. ]*)\)\)`->`\($1\)`）

语句类似`CONSTRAINT sys_c0011061 CHECK ((id_ IS NOT NULL))`改为`CONSTRAINT sys_c0011061 CHECK (id_ IS NOT NULL)`（正则：`CHECK \(\((.*)\)\)(,?)$`->`CHECK \($1\)$2`）

更改`COMMENT ON TABLE xxx.xxx IS 'xxx';`语句为`ALTER TABLE xxx COMMENT 'xxx';`（正则：`COMMENT ON TABLE (\S+) IS '([^']*)'`->`ALTER TABLE $1 COMMENT '$2'`）

~~更改`COMMENT ON COLUMN xxx.xxx.xxx IS 'xxx';`语句为`ALTER TABLE xxx MODIFY COLUMN xxx COMMENT 'xxx'`（正则：`COMMENT ON COLUMN ((?:[^\s.]+.)[^\s.]+)\.([\S]+) IS '([^']*)'`->`ALTER TABLE $1 MODIFY COLUMN $2 <typedef> COMMENT '$3'`，不确定`<typedef>`，不能直接用正则）~~

移除`COMMENT ON COLUMN xxx.xxx.xxx IS 'xxx';`语句，不能直接使用正则替换，只好直接移除了（正则`^(COMMENT ON COLUMN ([\S]+) IS '([\s\S]*?)';)$`->`/*$1*/`）

移除`::numeric`语法（不需要）（正则：`::[\w ]+`->``）

表的列定义`character varying(n)`，其中n的总和不能超过65535/3（或65535/4）（思路，替换n>=500的字段的类型为`text`）（正则：`character varying\((?:[5-9]|\d\d)\d\d\)`->`text`）（**注意** 这个更改实际上对表结构有一定影响，需要确认）

将类型`bytea`改为`binary`（mysql中的`binary`对应pgsql中的`bytea`）

check约束`CONSTRAINT sys_c0011055 CHECK (is_active_ = ANY (ARRAY[(1), (0)]))`改为`CONSTRAINT sys_c0011055 CHECK (is_active_=0 or is_active_=1`（正则：`(\w+) = ANY \(ARRAY\[\(1\), \(0\)\]\)`->`$1=0 or $1=1`）（其他类似结构也要检查）

括起字段名等的双引号改为反引号

注释掉`create sequence`语句（不支持不需要）（正则：`(CREATE SEQUENCE [\s\S]*?;)`->`/*$1*/`）

`SELECT row_number() OVER () AS `直接替换成`SELECT `

`to_char`函数需要替换（如果是`to_char(<int_col>)`的话将`to_char`替换成`concat`即可）（如果是`to_char(<date>,<foramt>)`的话，使用正则：`to_char\(([^\r\n,]+),([^\r\n\)]+)\)`->`date_format\($1,$2\)`，注意可能需要将`date_format`改为`time_format`）（**注意** 需要手动检查确认）

` t1480_date character varying(24) DEFAULT date_format(statement_sysdate(), 'YYYY-mm-dd')`这样的字符串字段不能设置默认值为当前时间，正则` DEFAULT date_format\([^\r\n]*\)`->``

CASE语句替换（`case ... when ... ... else ... end`替换成`case ... when ... then ... else ... end`）（**注意** 需要手动检查确认）

WITH ... AS ...语句替换（**注意** 需要手动检查确认）

将单独的`character varying`类型的字段的类型替换为`text`（因为不知道长度）（`character varying([^\(])`->`text$1`）

`... text DEFAULT ...`这样的字段，需要改回`character varying`类型（**注意** 需要手动检查确认）

其他：

* 字段`readedman`改回`character varying(4000)`类型，因为有默认值
* 字段`bbs_signcontent`改回`character varying(4000)`类型，因为有默认值
* 字段`databases`用反引号括起
* 表`oa_informationtag`使用垂直表
* 字段`bbs_signcontent`改回`character varying(4000)`类型，因为有默认值

SQL语句更改：

```
CREATE VIEW org_organization_v AS
 SELECT to_char(max(b.emp_id)) AS emp_id,
    to_char(max(a.org_id)) AS org_id,
    max(
        CASE to_char(a.orgparentorgid)
            WHEN '0''--'
            ELSE to_char(a.orgparentorgid)
        END) AS orgparentorgid,
    max((a.orgname)) AS orgname
   FROM (org_organization a
     LEFT JOIN org_organization_user b ON (a.org_id = b.org_id))
  GROUP BY a.org_id;
  
-- 改为

CREATE VIEW org_organization_v AS
 SELECT concat(max(b.emp_id)) AS emp_id,
    concat(max(a.org_id)) AS org_id,
    max(
        CASE concat(a.orgparentorgid)
            WHEN '0' then '--'
            ELSE concat(a.orgparentorgid)
        END) AS orgparentorgid,
    max((a.orgname)) AS orgname
   FROM (org_organization a
     LEFT JOIN org_organization_user b ON (a.org_id = b.org_id))
  GROUP BY a.org_id;
```

```
CREATE VIEW org_user_info AS
 WITH tmp AS (
         SELECT a.emp_id,
            a.org_id,
            a.empdutylevel,
            COALESCE(a.empdutyname, ' ') AS empdutyname,
            a.ordercode
           FROM org_sideline a
        UNION
         SELECT t.emp_id,
            t.org_id,
            NULL,
            NULL,
            NULL
           FROM org_organization_user t
        )
 SELECT  orguserid,
    tmp.emp_id,
    tmp.org_id,
    b.orgname,
    COALESCE(tmp.empdutylevel, d.empdutylevel) AS empdutylevel,
    COALESCE(tmp.empdutyname, d.empduty) AS dutyname,
    COALESCE(tmp.ordercode, d.userordercode) AS ordercode,
    b.orgnamestring
   FROM tmp,
    org_organization b,
    org_employee d
  WHERE ((tmp.emp_id = d.emp_id) AND (tmp.org_id = b.org_id) AND (d.domain_id = (0)));

-- 改为：

CREATE VIEW org_user_info AS
 SELECT  orguserid,
    tmp.emp_id,
    tmp.org_id,
    b.orgname,
    COALESCE(tmp.empdutylevel, d.empdutylevel) AS empdutylevel,
    COALESCE(tmp.empdutyname, d.empduty) AS dutyname,
    COALESCE(tmp.ordercode, d.userordercode) AS ordercode,
    b.orgnamestring
   FROM (
         SELECT a.emp_id,
            a.org_id,
            a.empdutylevel,
            COALESCE(a.empdutyname, ' ') AS empdutyname,
            a.ordercode
           FROM org_sideline a
        UNION
         SELECT t.emp_id,
            t.org_id,
            NULL,
            NULL,
            NULL
           FROM org_organization_user t
        ) as tmp,
    org_organization b,
    org_employee d
  WHERE ((tmp.emp_id = d.emp_id) AND (tmp.org_id = b.org_id) AND (d.domain_id = (0)));

```

```
CREATE TABLE tlimit (
    limit_id numeric(7,0) NOT NULL,
    limit_name character varying(400),
    limit_table numeric(7,0) NOT NULL,
    limit_type numeric(1,0) DEFAULT 1,
    limit_field numeric(7,0),
    limit_prytable numeric(7,0),
    limit_pryfield numeric(7,0),
    limit_group text,
    limit_des character varying(200),
    limit_owner character varying(80),
    limit_date timestamp(0) DEFAULT statement_sysdate(),
    domain_id numeric(20,0),
    limit_rewriteset text,
    CONSTRAINT sys_c0012845 CHECK (limit_table IS NOT NULL)
);

-- 改为：

CREATE TABLE tlimit (
    limit_id numeric(7,0) NOT NULL,
    limit_name character varying(400),
    limit_table numeric(7,0) NOT NULL,
    limit_type numeric(1,0) DEFAULT 1,
    limit_field numeric(7,0),
    limit_prytable numeric(7,0),
    limit_pryfield numeric(7,0),
    limit_group text,
    limit_des character varying(200),
    limit_owner character varying(80),
    limit_date timestamp DEFAULT current_timestamp,
    domain_id numeric(20,0),
    limit_rewriteset text,
    CONSTRAINT sys_c0012845 CHECK (limit_table IS NOT NULL)
);
```