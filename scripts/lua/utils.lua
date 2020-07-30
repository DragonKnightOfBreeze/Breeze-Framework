local utils = {}

-- string utils

function utils.quote(str)
    return "'" .. tostring(str) .. "'"
end

-- sql utils


---sql_insert
---@param table_name string
---@param table_columns table
---@param values table
---@return string
function utils.sql_insert(table_name, table_columns, values)
    local sql = "insert into %s (%s) value (%s)"
    return string.format(sql, table_name, table.concat(table_columns, ","), table.concat(values, ","))
end

function utils.sql_insert_batch(table_name, table_columns, values_fun, count)
    local sql = sql_insert_init(table_name, table_columns)
    for i = 1, count do
        sql = sql + sql_insert_next(values_fun())
    end
end

---sql_insert_init
---@param table_name string
---@param table_columns table
---@return string
function utils.sql_insert_init(table_name, table_columns)
    local sql = "insert into %s (%s) values"
    return string.format(sql, table_name, table.concat(table_columns, ","))
end

---sql_insert_next
---@param values table
---@return string
function utils.sql_insert_next(values)
    local sql = "(%s)"
    return string.format(sql, table.concat(values, ","))
end

---sql_select_all
---@param table_name string
---@return string
function utils.sql_select_all(table_name)
    local sql = "select * from %s"
    return string.format(sql, table_name)
end

---sql_delete
---@param table_name string
---@return string
function utils.sql_delete(table_name)
    local sql = "delete from %s"
    return string.format(sql, table_name)
end

---sql_delete
---@param table_name string
---@return string
function utils.sql_truncate(table_name)
    local sql = "truncate %s"
    return string.format(sql, table_name)
end

return utils
