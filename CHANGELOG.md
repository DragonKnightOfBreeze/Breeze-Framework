# 1.x.x

## 1.0.x

### 1.0.0

* [X] 使用gradle构建项目（因为比maven更简洁），使用kts脚本构建
* [X] 迁移[Kotlin-Utility](https://github.com/DragonKnightOfBreeze/Kotlin-Utility)中的代码到此项目
* [X] 初始化仓库成功

### 1.0.1

* [X] breeze-core `eval()` 直接运行脚本
* [X] breeze-core `exec()` 启动控制台
* [X] 尽可能使用懒加载属性
* [X] 将持久化、注解的扩展方法分别移动带单独的文件中
* [X] 确保项目能够正常构建，配置可选的依赖。（不确定是否生效）
* [X] breeze-core 类似`TODO()` 的方法
* [X] 提供扩展注解的容器
* [X] 提供必要的日志对象。（包括到最近一次非内联调用处的日志对象）
* [X] breeze-time 参考khronos更新一部分代码
* [X] breeze-text en 序数与数量的转化
* [X] breeze-core 使类似`TODO()`的方法能够显示正确的日志
* [X] breeze-core 优化和完善`LetterCase`和`ReferenceCase`以及相关代码
* [X] 完成基本的顶层README文档
* [X] breeze-core 进一步完善`LetterCase`和`ReferenceCase`以及相关代码
* [X] breeze-core 参考klutter更新一部分代码
* [X] breeze-core 实现元素的向下复制和平滑复制的方法
* [X] breeze-core 实现`addPrefix`等方法，~~实现`ifStartsWith`等方法~~
* [X] breeze-core 为`Sequence`尽可能地、合理地适配`Iterable`的扩展
* [X] breeze-data 将`loaders`、`generators`包移动到此
* [X] breeze-data 编写`DataSerializer`的相关接口和类，未实现
* [X] breeze-core 扩展`LetterCase`，提供更多的显示格式
* [X] 补充api注解
* [X] breeze-core 进一步完善`LetterCase`，通过添加泛用显示格式放松限制
* [X] 完善测试中
* [X] 参考官方库，将简单的转化方法也改写成内联方法。（为了进行堆栈追踪和日志追踪等）
* [X] 添加`ReflectModifierExtensions`
* [X] 为有望被标准库实现的实现添加`Time`注解
* [X] breeze-reflect 从breeze-core提取代码
* [X] 更新字符串扩展，完善`customFormat`和`toMultilineText`方法
* [X] 实现已编写的标注为未实现的功能并通过测试
* [X] 去除对`kotlin-script`的相关依赖和代码
* [X] breeze-data 代理实现`DataSerializer`的相关类
* [X] breeze-reflect 添加`checkClassForName`方法
* [X] breeze-core 添加核心扩展方法：`once`

### 1.0.2

* [X] 更新版本号
* [X] breeze-data 补充注释，搁置测试
* [X] breeze-core 移除Range的中缀扩展，因为可能返回`ClosedRange<T>`或`Pair`
* [X] breeze-core 将更多简单的扩展方法标为内联。（为了完全消除性能影响）
* [X] breeze-data 完成一个更好的Xml Dsl示例
* [X] breeze-core 添加函数式扩展方法的示例：`curried`、`partial`
* [X] breeze-core 提供通用的`@AllOpen`和`@NoArg`注解
* [X] breeze-data 更新扩展
* [X] breeze-reflect 为`memberProperties`添加一步到位的得到指定属性的扩展。`memberFunctions`同理
* [X] breeze-reflect 将代码按照扩展kotlin反射还是java反射进行明确的分类
* [X] breeze-core 重构涉及正则表达式的代码。用于`matches`方法的正则不需要包含`^``$`和捕捉分组，正则可用原始字符串表示
* [X] breeze-core 重构annotations，明确保留级别
* [X] breeze-core 添加并完善函数式扩展的示例
* [X] breeze-core 为最多6个参数的函数提供扩展
* [X] breeze-core 更注解以及注解相关扩展。（目前版本的Kotlin无法标注重复的可在运行时得到的注解）
* [X] breeze-core 更新System扩展

### 1.0.3

* [X] 更新版本号
* [X] breeze-core 重命名`String.to(FormatCase)`方法为`String.switchTo(FormatCase)`。（为了规避方法名冲突）
* [X] breeze-data 重构：将Dsl入口方法移至顶层，为Config接口声明`invoke`方法。取消懒加载
* [X] breeze-core 更改函数式扩展中的pipe方法的逻辑，将函数作为接受者，而非参数
* [X] breeze-core 添加字符串扩展：`isAlphabetic`、`isNumeric`、`isAlphanumeric`
* [X] breeze-core 提供阶乘和累加的扩展
* [X] breeze-time 去除标准库已提供的扩展
* [X] breeze-core 重构随机数扩展，由对象改为扩展方法
* [X] breeze-text 数字的简化表示的转化。（1000->1k, 1000->1千）
* [X] breeze-core 添加整乘、整除、转化为并发集合的扩展
* [X] breeze-fxgl 协助翻译简体和繁体中文文本
* [X] breeze-core 继续添加一些有用的扩展（如，整数/字符串转枚举值）
* [X] breeze-core 将函数式扩展移到单独的模块

### 1.0.4

* [X] 更新版本号
* [X] breeze-core 添加`Collection.randomOrNull()`扩展
* [X] breeze-core 重构系统扩展
* [X] breeze-core 重构与重新格式化代码
* [X] breeze-data 重构XmlDsl和MermaidFlowDsl
* [X] breeze-core&breeze-data 修复一些不合理的代码逻辑，完善XmlDsl和MermaidFlowDsl
* [X] breeze-dsl 将Dsl相关代码移到单独模块，完善MermaidFlowDsl，完成MermaidSequenceDsl
* [X] breeze-dsl 完成MermaidGanttDsl
* [X] breeze-data 提供equals、hashcode、toString方法的构建方法
* [X] breeze-data 完善equals、hashcode、toString方法的构建方法
* [X] breeze-dsl 更新PumlDsl和PumlStateDiagramDsl中
* [X] breeze-text Base64的相关扩展
* [X] breeze-dsl 完善各种DSL，基本完成PumlStateDiagramDsl
* [X] breeze-dsl 完成PumlStateDiagramDsl，并将公共Dsl提取到PumlDsl。（puml的语法真的是面向人类的吗？）
* [X] breeze-dsl 完善各种Dsl，更新MarkdownDsl中
* [X] breeze-dsl 更新MarkdownDsl中
* [X] breeze-dsl 重构：提取各种DslEntry（能够包含Dsl顶级元素的能力），但不声明顶级的DslEntry
* [X] breeze-dsl 初步完成MarkdownDsl（真复杂）
* [X] breeze-core,breeze-javafx 更新一些javafx的扩展，元组的扩展和数学的扩展
* [X] breeze-core 提供约等于、构建并发集合的扩展
* [X] breeze-core 添加一些委托自Integer类的扩展，添加`tryCatching`、`tryIgnored`扩展
* [X] breeze-dsl 初步完成CreoleDsl
* [X] breeze-dsl 完成CreoleDsl
* [X] breeze-dsl 完善各种Dsl

### 1.0.5

* [X] 更新版本号
* [X] breeze-core,breeze-dsl 一些更新，更新PumlSequenceDsl中
* [X] breeze-core 添加`.editorconfig`，修改`fillAt`、`fillToSize`等扩展的逻辑
* [X] breeze-core 为集合提供足够而必要的的`allIn`扩展
* [X] 更新README。添加额外的模块`breeze-logger`、`breeze-generator`
* [X] breeze-core 添加`checkChance`扩展
* [X] breeze-dsl 完善MarkdownDsl。添加特性支持。重新实现内联dsl
* [X] breeze-core 添加`toStringOrEmpty`扩展
* [X] breeze-core 添加`Colors`，提供标准颜色常量集
* [X] breeze-dsl 提取`CriticMarkupDsl`
* [X] breeze-dsl 补充缺失的Dsl文件
* [X] 一些更新
* [X] breeze-dsl 让本质上是List或Map的DSLElement继承对应的接口并代理实现。（本质上是Tree或Table的例外）
* [X] breeze-dsl 实现`JsonDsl`。（不推荐使用，但是作为规范）
* [X] 整理代码
* [X] breeze-serialization 提供缺失的基础的基于kotlin-jvm的KSerializer
* [X] breeze-dsl MarkdownDsl 支持标题的特性（一般是id）
* [X] 精简注解
* [X] breeze-core 参照标准库，重命名`CollectionExtensions`中的部分方法
* [X] breeze-core 提供`joinToStringOrEmpty`方法（为了优化性能）
* [X] breeze-core 重构`addPrefix`等方法（参照标准库）
* [X] breeze-dsl 重构代码
* [X] breeze-core 精简`toXxxOrDefault`和`toXxxOrElse`方法
* [X] breeze-core 提供字符串的逐行连接和逐行填充扩展
* [X] breeze-dsl MarkdownDsl 对于任何存在内联文本的元素，尽可能地实现`WrapContent`接口
* [X] breeze-core 添加`where`全局扩展
* [X] breeze-data Serializer 允许读取指定泛型类型的数据
* [X] breeze-core 重构代码，去除不必要的代码
* [X] breeze-dsl 完善MarkdownDsl
* [X] breeze-core 提供一些委托属性的便捷写法
* [X] breeze-functional 提供基础函数式方法到最高11个参数的函数
* [X] breeze-core 提供标准颜色的枚举。（考虑改为常量）
* [X] 更新README
* [X] breeze-core 优化`toStringBySelect`等方法
* [X] breeze-core 提供`filterValueNotNull`、`filterValueNotNullTo`扩展
* [X] breeze-time 完善代码
* [X] breeze-text 添加`String.base64Encoded`、`String.base64Decoded`
* [X] breeze-dsl 完成`CommandLineTextDsl`
* [X] breeze-dsl 完善命名和目录
* [X] breeze-core 提供字符串的常量集
* [X] 移除breeze-fxgl（考虑使用LibGDX）
* [X] breeze-dsl 完善`CommandLineTextDsl`，以支持更多的富文本
* [X] breeze-dsl 实现`MermaidPieChartDsl`
* [X] breeze-dsl 实现`MermaidClassSequenceDsl`，同时补充更多便捷的Dsl构建方法，至此`MermaidDsl`已经完全支持
* [X] 更新README
* [X] breeze-dsl 修复不足之处
- [X] breeze-dsl 优化Dsl构建方法
* [X] breeze-dsl 完成`FlowChartDsl`，完善其他的Dsl
* [X] breeze-dsl 实现`SequenceDiagramDsl`，将枚举尽可能地移到对应的类内部
* [X] breeze-dsl 添加接口`CanSplitContent`并适用
* [X] breeze-dsl 实现`MermaidStateDiagram`
* [X] 整理代码

### 1.0.6

- [X] 更新版本
* [X] 优化额外TODO方法的实现，消除对`kotlin-logging`的依赖
* [X] breeze-logger 提供Logger的简单实现。带有颜色、日期和路径
* [X] breeze-logger 调整日志显示风格
* [X] breeze-data 约束可见性
* [X] 更新依赖

### 1.0.7

* [X] 更新版本
* [X] ~~breeze-dsl 提供`SteamTextDsl`。~~
* [X] breeze-core 基本完成`MultiValueMap`
* [X] breeze-core 修正`Vector`中的Bug
* [X] breeze-core 为字符串添加`inline`和`multiline`内联扩展属性。为了间接与美观
* [X] breeze-core 补充注解
* [X] 适用region...endregion注释
* [X] breeze-core 废弃`MultiValueMap`的实现，转而为`Map<K,List<V>>`提供别名扩展
* [X] breeze-logger 完成自己的日志器的实现
* [X] breeze-core 添加`String.alignLeft`等方法
* [X] breeze-core 去除以上扩展，添加`String.orNull`和`Optional.orNull`扩展
* [X] breeze-core 重构和添加`String.alignStart`,`String.addPrefix`,`String.inline`,`String.trimWrap`等扩展
* [X] breeze-dsl 重构代码和整理目录
- [X] breeze-core 添加`String.setPrefix`等扩展
* [X] breeze-dsl 分割为多个子模块
* [X] breeze-core,breeze-text 将`DecodeAndEncodeExtensions`移到`breeze-core`。更改`StringExtensions`中的一些扩展命名
* [X] breeze-linq 提供linq的模拟实现
* [X] breeze-linq 提供`distinct`, `distinctBy`方法
* [X] breeze-linq 提供`limitDesc`方法
* [X] breeze-core 重命名和补充全局代码
* [X] breeze-linq 提供`union`, `unionAll`, `selectMany`方法
* [X] 更新README
* [X] breeze-core 添加`String.lineBreak`扩展
* [X] breeze-http 更新代码。未测试
* [X] breeze-http 完善代码
* [X] breeze-core 删除带有唯一Pair类型参数的中缀方法
* [X] breeze-core 提供废弃且标为隐藏的`T?.toOptional()`方法
* [X] breeze-core 整理代码和注释，提供`Array.stream()`扩展
* [X] 整理代码
* [X] breeze-dsl 重构代码
* [X] breeze-data&breeze-reflect&breeze-dsl 重构与优化代码，排除bug
* [X] breeze-logger 提供`SimpleLogger`

### 1.0.8

* [X] 更新版本
* [X] 整理目录
* [X] 修复`build.gradle.kts`中的sourcesJar配置错误
* [X] 重新上传到bintray，并移除旧的上传
* [X] 改为上传到github

### 1.0.9

* [X] 更新版本
* [X] 清理注解
* [X] breeze-core 更新`DataClassExtensions`，将相关扩展提取为`AnyExtensions`，并提供`smartEquals`等方法
* [X] 清理废弃的代码
* [X] 清理代码
* [X] 更新`Count`数据类的实现（待完善）
* [X] 清理注解和枚举，将不确定的代码移到`breeze-unstable`
* [X] 适用对数组的扩展的型变
* [X] 整理文档和代码

### 1.0.10

* [X] 更新版本
* [X] 添加`@TodoMarker`
* [X] 将`DslBuilder`重命名为`DslDocument`，其他一些更改
* [X] 添加必要的Jvm注解，加强Java兼容性，其他一些相关更改
* [X] breeze-core 添加一些类型别名
* [X] breeze-core 移除自定义的`Cloneable<T>`接口，添加不稳定的`shallowClone`和`deepClone`扩展。移除`List.withKeys`扩展
* [X] breeze-core 添加`String.orEmpty(predicate)`、`String.takeIfNotEmpty`和`String.takeIfNotBlank`
* [X] breeze-core&breeze-reflect 优化部分代码
* [X] 添加必要的`@JvmStatic`和`@JvmOverloads`注解
* [X] breeze-core 添加`String.replaceLooped`扩展
* [X] breeze-core 将`String.wrapQuote`、`String.unwrapQuote`重命名为`String.quote`、`String.unquote`
* [X] breeze-core 优化集合的`deepGet`、`deepSet`、`deepQuery`扩展，添加`String.surroundsWith`、`String.truncate`、`String.toIntRange`等扩展
* [X] breeze-core 将`exac`改为`exec`
* [X] breeze-core 默认使用路径引用
* [X] breeze-core 添加`String.matchesBy`扩展。添加`String.toIntRange`等扩展。重命名和添加`toNumber`、`toNumberOrNull`扩展
* [X] breeze-core 添加`IntRange.toCircledRange`扩展，用于兼容逆向索引
* [X] breeze-core 添加`enumSetOf`、`enumMapOf`等扩展
* [X] breeze-core 完善`exec`扩展，添加`execBlocking`扩展

### 1.0.11

* [X] 更新版本
* [X] 将使用`HashMap`的地方尽可能地改为使用`LinkedHashMap`（为了键的顺序的一致性），重构相关代码
* [X] breeze-core 让集合的`deepQuery`方法允许接受`-`作为数组占位符
* [X] breeze-core 添加`String.toRegexBy`扩展以及其所需要的`String.transformIn`扩展
* [X] breeze-core 让集合的`deepQuery`方法允许接受`m-n`作为数组索引范围占位符
* [X] breeze-core 优化字符串的特定类型操作方法以及集合的深操作方法
* [X] 移除一些认为是冗余的扩展
* [X] breeze-core 添加`Array.swap`、`MutableList.swap`扩展
* [X] breeze-core&breeze-reflect 添加`Any.isInstanceOf`扩展
* [X] breeze-core&breeze-reflect 让`Any.isInstanceOf`扩展兼容原始类型
* [X] 尽可能地抑制编译器错误以匹配标准库中的对应扩展方法
* [X] breeze-core 添加用于元素的类型检查的`Iterable.isIterableOf`等扩展
* [X] 更新项目文档
* [X] 移除或废弃一些认为是冗余的扩展
* [X] （较大的更新）调整代码目录结构
* [X] （标准库缺失）添加`setOfNotNull`、`mapOfValueNotNull`扩展
* [X] 调整注解的添加逻辑
* [X] breeze-core 移除`String.orEmpty(predicate)`，提供`String.orNull()`、`Iterable.orNull`等扩展
* [X] 废弃一些冗余扩展，同时保证能够替换成规范且一致的链式调用写法
* [X] breeze-core 提供（最多到）五元素元组的实现，因为kotlin集合框架的解构方法最多到第五个元素。同时完善相关扩展
* [X] breeze-core 重构用于元素的类型检查的`Iterable.isIterableOf`等扩展
* [X] breeze-dsl 重构部分dsl的实现，去除内联的Dsl元素类
* [X] breeze-core 重构`SystemExtensions.kt`
* [X] breeze-core 实现`Regex.Companion.fromRange`，接收整数范围，转化为合法的正则表达式字符串（难！）
* [X] breeze-core 移除`Int.exactXxx`等扩展，提供`Int.exact`等扩展
* [X] breeze-core 为`NumberExtensions`和`MathExtensions`补充一系列的扩展
* [X] breeze-core 将`mapOfValueNotNull`重命名为`mapOfValuesNotNull`
* [X] breeze-http&breeze-logger 重构代码
* [X] breeze-core 完善对html和xml的转义逻辑。（来自guava）
* [X] breeze-core 优化`String.replaceAll`和`String.escapeBy`等方法
* [X] breeze-core 添加可空数字类型的`orZero`扩展
* [X] 添加并适用一些临时性的注解
* [X] breeze-core 重命名一些方法名，将一些方法改为顺序，优化一些代码逻辑
* [X] breeze-core 为`SystemExtensions`添加`executeCommand`扩展
* [X] breeze-core 添加一些字符串集合的特殊处理扩展
* [X] breeze-core 重构字符串和集合类型的`repeat`相关扩展
* [X] ~~breeze-core 从类型推断出对应的默认值。（来自guava）~~ 

### 1.0.12
 
* [X] 更新版本
* [X] 减少项目依赖

### 1.0.13

* [X] 更新版本
* [X] 移除一些废弃项
* [X] breeze-core 添加`String.replaceIn`、`String.replaceInLast`、`String.replaceEntire`等扩展
* [X] 移除一些废弃项，更新项目文档
* [X] breeze-data 重构代码，重命名一些方法，让它们的用途更加容易理解，更改目录结构
* [X] 重构配置类以及相关类的代码逻辑
* [X] 将breeze-data重命名为breeze-serializer，并添加`kotlinx-serialization`的代理实现

### 1.0.14
 
* [X] 更新版本
* [X] 上传到远程仓库

### 1.0.15
 
* [X] 更新版本
* [X] breeze-core 添加`String.toCharset`扩展
* [X] breeze-core 重构`String.toIntRange`等扩展的逻辑，为字符串、集合和数组添加更多的运算符重载扩展
* [X] breeze-core 重构TODO方法
* [X] 更新项目文档
* [X] 上传到远程仓库

## 1.1.x

### 1.1.0
 
* [X] 更新版本。更新Kotlin版本到1.3.70。
* [X] breeze-core 移除集合类型的`randomOrNull`方法，其他一些更新
* [X] breeze-core 添加`expandTo`和`expand`方法，相当于`fold`的反操作，用于根据指定的操作展开初始值，并收集展开过程中的所有项
* [X] breeze-core 优化集合的深层操作方法，添加集合的`deepGetOrNull`扩展，重命名`castOrNull`为`safeCast`
* [X] 添加breeze-mapper模块，实现JsonMapper的map方法。更新一些字符串处理方法的代码逻辑
* [X] breeze-mapper 初步实现PropertiesMapper的map方法
* [X] breeze-dsl 完善代码
* [X] breeze-mapper 基本实现`Mapper.mapObject`和`Mapper.unmapObject`方法
* [X] breeze-mapper 更新一些字符串处理方法的代码逻辑，初步实现YamlMapper的map方法
* [X] breeze-core 提供更多的类型别名，对于~~原始类型相关类型~~和方法类型
* [X] breeze-core 将集合类型的`innerJoin`扩展重命名为`bind`方法，因为更加贴近需求
* [X] breeze-core 重构已自定义枚举为参数的字符串处理方法，提高灵活性和规范性
* [X] breeze-serializer 让breeze-serializer也委托breeze-mapper实现并作为默认实现，同时完善相关代码
* [X] 更改目录结构
* [X] breeze-dsl-command-line 优化代码
* [X] breeze-dsl-critic-markup & breeze-dsl-command-line 优化代码
* [X] breeze-core 添加`HandledCharSequence`，这个接口的输出字符串可能需要经过额外的处理
* [X] breeze-core 添加用于特殊逻辑的字符串处理的`typing`和`typingAll`方法
* [X] breeze-dsl 优化代码
* [X] breeze-core 让集合的`deepQuery`方法当特定的占位符不匹配类型时，回调为普通字符串

### 1.1.1

* [X] 更新版本。 
* [X] 为集合类型添加`query`扩展（基于`deepQuery`扩展）
* [X] 删除`breeze-dsl-json`和`breeze-dsl-yaml`
* [X] breeze-mapper 完善代码
  * PropertiesMapper 重构代码格式，完善注释，支持将数组类型的值映射为多行逗号分隔表达式
  * JsonMapper 重构代码格式，完善注释
* [X] breeze-core 完善代码
  * 完善DataClassExtensions
  * 实现Identifiable
  * 实现Countable
* [X] 更新项目文档

### 1.1.2
 
* [X] 移除breeze-game模块（应当放到一个独立的框架中）和breeze-text模块（不是非常必要的方法）
* [X] breeze-core 实现`toStringByReference`
* [X] breeze-core 对字符串的多种方式的格式化方法，参数可能不确定，参考Format、MessageFormat、日志器
* [X] breeze-core 对数字的多种方式的格式化方法，参数可能不确定，参考NumberFormat，以及添加前缀0的格式化
* [X] 精简目录结构
* [X] breeze-dsl-bbcode 实现BBCode
* [X] breeze-core 重构`typing`、`typingAll`方法
* [X] breeze-core 补充随机数方法
* [X] breeze-core 移除相反数相关方法
* [X] 添加`String.splitToStrings`扩展
* [X] 添加`with(arg1,arg2,block)`扩展
* [X] 添加`with(arg1,arg2,arg3,block)`扩展

## 1.2.x

### 1.2.0

* [X] 更新版本。更新Kotlin版本到1.3.72。
* [X] 整理和重构代码。
* [X] 添加一些扩展。
* [X] 添加`String.replaceIn`相关扩展
* [X] 重构和添加`String.truncate`相关扩展
* [X] 重构`breeze-dsl`的代码。
* [X] 添加`String.isXxx`等扩展。
* [X] 重构和添加 ~~`String.replaceMatch`~~ 、`String.substringMatch`扩展
* [X] 移除`tryOrIgnore`和`tryOrPrint`扩展并添加`Result.andPrintStackTrace`、`Result.andPrint`和`Result.andPrintln`扩展
* [X] 实现`Any?.toSingleton`和`Any?.toSingletonOrEmpty`。
* [X] 准备添加COPYRIGHT
* [X] 添加COPYRIGHT
* [X] 更新Kotlin版本到1.4.0-rc。

# 长期

* [ ] 添加`BreezeExtensions`
  * [ ] `Any?.equalsBr`
  * [ ] `Any?.hashCodeBr`
  * [ ] `Any?.toStringBr`
  * [ ] `Any?.isEmptyBr`
  * [ ] `Any?.isNotEmptyBr`
  * [ ] `Any?.ifEmptyBr`
  * [ ] `Any?.ifNotEmptyBr`
  * [ ] `<Collection>.joinToStringBr`
  * [ ] `<Collection>.getBr`
  * [ ] `<Collection>.getOrNullBr`
  * [ ] `<Collection>.getOrSetBr`
  * [ ] `<Collection>.getOrElseBr`
  * [ ] `<Collection>.setBr`
  * [ ] `<Collection>.queryBr`
  * [ ] `<Collection>.queryOrNullBr`
  * [ ] `<Collection>.queryOrSetBr`
  * [ ] `<Collection>.queryOrElseBr`
* [X] 移除对非框架类的第三方库的依赖
* [X] 上传到Github
* [X] 同步项目到Bintray
* [X] 发布项目到JCenter。（格式上存在一些问题）
* [ ] 完善各个模块的README文档
* [ ] 按照功能而非类型更改项目代码的目录结构
