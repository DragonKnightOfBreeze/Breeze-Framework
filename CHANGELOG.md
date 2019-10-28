# 1.x.x

## 1.0.x

### 1.0.0

* [X] 使用gradle构建项目（因为比maven更简洁），使用kts脚本构建。
* [X] 迁移[Kotlin-Utility](https://github.com/DragonKnightOfBreeze/Kotlin-Utility)中的代码到此项目。
* [X] 初始化仓库成功。
 
### 1.0.1

* [X] breeze-core `eval()` 直接运行脚本。
* [X] breeze-core `exec()` 启动控制台。
* [X] 尽可能使用懒加载属性。
* [X] 将持久化、注解的扩展方法分别移动带单独的文件中。
* [X] 确保项目能够正常构建，配置可选的依赖。（不确定是否生效）
* [X] breeze-core 类似`TODO()` 的方法。
* [X] 提供扩展注解的容器。
* [X] 提供必要的日志对象。（包括到最近一次非内联调用处的日志对象）
* [X] breeze-time 参考khronos更新一部分代码。
* [X] breeze-text en 序数与数量的转化。
* [X] breeze-core 使类似`TODO()`的方法能够显示正确的日志。
* [X] breeze-core 优化和完善`LetterCase`和`ReferenceCase`以及相关代码。 
* [X] 完成基本的顶层README文档。
* [X] breeze-core 进一步完善`LetterCase`和`ReferenceCase`以及相关代码。
* [X] breeze-core 参考klutter更新一部分代码。
* [X] breeze-core 实现元素的向下复制和平滑复制的方法。
* [X] breeze-core 实现`addPrefix`等方法，~~实现`ifStartsWith`等方法~~。
* [X] breeze-core 为`Sequence`尽可能地、合理地适配`Iterable`的扩展。
* [X] breeze-data 将`loaders`、`generators`包移动到此。
* [X] breeze-data 编写`DataSerializer`的相关接口和类，未实现。
* [X] breeze-core 扩展`LetterCase`，提供更多的显示格式。
* [X] 补充api注解。
* [X] breeze-core 进一步完善`LetterCase`，通过添加泛用显示格式放松限制。
* [X] 完善测试中。
* [X] 参考官方库，将简单的转化方法也改写成内联方法。（为了进行堆栈追踪和日志追踪等）
* [X] 添加`ReflectModifierExtensions`。
* [X] 为有望被标准库实现的实现添加`Time`注解。
* [X] breeze-reflect 从breeze-core提取代码。
* [X] 更新字符串扩展，完善`customFormat`和`toMultilineText`方法。
* [X] 实现已编写的标注为未实现的功能并通过测试。
* [X] 去除对`kotlin-script`的相关依赖和代码。
* [X] breeze-data 代理实现`DataSerializer`的相关类。
* [X] breeze-reflect 添加`checkClassForName`方法。
* [X] breeze-core 添加核心扩展方法：`once`。

### 1.0.2

* [X] 更新版本号。
* [X] breeze-data 补充注释，搁置测试。
* [X] breeze-core 移除Range的中缀扩展，因为可能返回`ClosedRange<T>`或`Pair`。
* [X] breeze-core 将更多简单的扩展方法标为内联。（为了完全消除性能影响）
* [X] breeze-data 完成一个更好的Xml Dsl示例。
* [X] breeze-core 添加函数式扩展方法的示例：`curried`、`partial`。
* [X] breeze-core 提供通用的`@AllOpen`和`@NoArg`注解。
* [X] breeze-data 更新扩展。
* [X] breeze-reflect 为`memberProperties`添加一步到位的得到指定属性的扩展。`memberFunctions`同理。
* [X] breeze-reflect 将代码按照扩展kotlin反射还是java反射进行明确的分类。 
* [X] breeze-core 重构涉及正则表达式的代码。用于`matches`方法的正则不需要包含`^``$`和捕捉分组，正则可用原始字符串表示。
* [X] breeze-core 重构annotations，明确保留级别。
* [X] breeze-core 添加并完善函数式扩展的示例。
* [X] breeze-core 为最多6个参数的函数提供扩展。
* [X] breeze-core 更注解以及注解相关扩展。（目前版本的Kotlin无法标注重复的可在运行时得到的注解）
* [X] breeze-core 更新System扩展。

### 1.0.3

* [X] 更新版本号。
* [X] breeze-core 重命名`String.to(FormatCase)`方法为`String.switchTo(FormatCase)`。（为了规避方法名冲突）
* [X] breeze-data 重构：将Dsl入口方法移至顶层，为Config接口声明`invoke`方法。取消懒加载。
* [X] breeze-core 更改函数式扩展中的pipe方法的逻辑，将函数作为接受者，而非参数。
* [X] breeze-core 添加字符串扩展：`isAlphabetic`、`isNumeric`、`isAlphanumeric`。
* [X] breeze-core 提供阶乘和累加的扩展。
* [X] breeze-time 去除标准库已提供的扩展。
* [X] breeze-core 重构随机数扩展，由对象改为扩展方法。
* [X] breeze-text 数字的简化表示的转化。（1000->1k, 1000->1千）
* [X] breeze-core 添加整乘、整除、转化为并发集合的扩展。
* [X] breeze-fxgl 协助翻译简体和繁体中文文本。
* [X] breeze-core 继续添加一些有用的扩展（如，整数/字符串转枚举值）。
* [X] breeze-core 将函数式扩展移到单独的模块。

### 1.0.4

* [X] 更新版本号。
* [X] breeze-core 添加`Collection.randomOrNull()`扩展。
* [X] breeze-core 重构系统扩展。
* [X] breeze-core 重构与重新格式化代码。
* [X] breeze-data 重构XmlDsl和MermaidFlowDsl。
* [X] breeze-core&breeze-data 修复一些不合理的代码逻辑，完善XmlDsl和MermaidFlowDsl。
* [X] breeze-dsl 将Dsl相关代码移到单独模块，完善MermaidFlowDsl，完成MermaidSequenceDsl。
* [X] breeze-dsl 完成MermaidGanttDsl。
* [X] breeze-data 提供equals、hashcode、toString方法的构建方法。
* [X] breeze-data 完善equals、hashcode、toString方法的构建方法。
* [X] breeze-dsl 更新PumlDsl和PumlStateDiagramDsl中。
* [X] breeze-text Base64的相关扩展。
* [X] breeze-dsl 完善各种DSL，基本完成PumlStateDiagramDsl。 
* [X] breeze-dsl 完成PumlStateDiagramDsl，并将公共Dsl提取到PumlDsl。（puml的语法真的是面向人类的吗？）
* [X] breeze-dsl 完善各种Dsl，更新MarkdownDsl中。
* [X] breeze-dsl 更新MarkdownDsl中。
* [X] breeze-dsl 重构：提取各种DslEntry（能够包含Dsl顶级元素的能力），但不声明顶级的DslEntry。
* [X] breeze-dsl 初步完成MarkdownDsl（真复杂）。
* [X] breeze-core,breeze-javafx 更新一些javafx的扩展，元组的扩展和数学的扩展。
* [X] breeze-core 提供约等于、构建并发集合的扩展。
* [X] breeze-core 添加一些委托自Integer类的扩展，添加`tryCatching`、`tryIgnored`扩展。
* [X] breeze-dsl 初步完成CreoleDsl。
* [X] breeze-dsl 完成CreoleDsl。
* [X] breeze-dsl 完善各种Dsl。

### 1.0.5

* [X] 更新版本号。
* [X] breeze-core,breeze-dsl 一些更新，更新PumlSequenceDsl中。
* [X] breeze-core 添加`.editorconfig`，修改`fillAt`、`fillToSize`等扩展的逻辑。
* [X] breeze-core 为集合提供足够而必要的的`allIn`扩展。
* [X] 更新README。添加额外的模块`breeze-logger`、`breeze-generator`。
* [X] breeze-core 添加`checkChance`扩展。
* [X] breeze-dsl 完善MarkdownDsl。添加特性支持。重新实现内联dsl。
* [X] breeze-core 添加`toStringOrEmpty`扩展。
* [X] breeze-core 添加`Colors`，提供标准颜色常量集。
* [X] breeze-dsl 提取`CriticMarkupDsl`。
* [X] breeze-dsl 补充缺失的Dsl文件。
* [X] 一些更新。
* [X] breeze-dsl 让本质上是List或Map的DSLElement继承对应的接口并代理实现。（本质上是Tree或Table的例外）
* [X] breeze-dsl 实现`JsonDsl`。（不推荐使用，但是作为规范）
* [X] 整理代码。
* [X] breeze-serialization 提供缺失的基础的基于kotlin-jvm的KSerializer。
* [X] breeze-dsl MarkdownDsl 支持标题的特性（一般是id）。 
* [X] 精简注解。
* [X] breeze-core 参照标准库，重命名`CollectionExtensions`中的部分方法。
* [X] breeze-core 提供`joinToStringOrEmpty`方法（为了优化性能）。
* [X] breeze-core 重构`addPrefix`等方法（参照标准库）。
* [X] breeze-dsl 重构代码。
* [X] breeze-core 精简`toXxxOrDefault`和`toXxxOrElse`方法。
* [X] breeze-core 提供字符串的逐行连接和逐行填充扩展。
* [X] breeze-dsl MarkdownDsl 对于任何存在内联文本的元素，尽可能地实现`WrapContent`接口。
* [X] breeze-core 添加`where`全局扩展。
* [X] breeze-data Serializer 允许读取指定泛型类型的数据。
* [X] breeze-core 重构代码，去除不必要的代码。
* [X] breeze-dsl 完善MarkdownDsl。
* [X] breeze-core 提供一些委托属性的便捷写法。
* [X] breeze-functional 提供基础函数式方法到最高11个参数的函数。
* [X] breeze-core 提供标准颜色的枚举。（考虑改为常量）
* [X] 更新README。
* [X] breeze-core 优化`toStringBySelect`等方法。
* [X] breeze-core 提供`filterValueNotNull`、`filterValueNotNullTo`扩展。
* [X] breeze-time 完善代码。
* [X] breeze-text 添加`String.base64Encoded`、`String.base64Decoded`。
* [X] breeze-dsl 完成`CommandLineTextDsl`。
* [X] breeze-dsl 完善命名和目录。
* [X] breeze-core 提供字符串的常量集。
* [X] 移除breeze-fxgl（考虑使用LibGDX）。
* [X] breeze-dsl 完善`CommandLineTextDsl`，以支持更多的富文本。
* [X] breeze-dsl 实现`MermaidPieChartDsl`。
* [X] breeze-dsl 实现`MermaidClassSequenceDsl`，同时补充更多便捷的Dsl构建方法，至此`MermaidDsl`已经完全支持。
* [X] 更新README。
* [X] breeze-dsl 修复不足之处。
- [X] breeze-dsl 优化Dsl构建方法。
* [X] breeze-dsl 完成`FlowChartDsl`，完善其他的Dsl。
* [X] breeze-dsl 实现`SequenceDiagramDsl`，将枚举尽可能地移到对应的类内部。
* [X] breeze-dsl 添加接口`CanSplitContent`并适用。
* [X] breeze-dsl 实现`MermaidStateDiagram`。
* [X] 整理代码。

### 1.0.6

- [X] 更新版本。
* [X] 优化额外TODO方法的实现，消除对`kotlin-logging`的依赖。
* [X] breeze-logger 提供Logger的简单实现。带有颜色、日期和路径。
* [X] breeze-logger 调整日志显示风格。
* [X] breeze-data 约束可见性。
* [X] 更新依赖。

### 1.0.7

* [X] 更新版本。
* [X] ~~breeze-dsl 提供`SteamTextDsl`。~~
* [X] breeze-core 基本`MultiValueMap`。
* [ ] breeze-dsl 分割为多个子模块。
* [ ] breeze-dsl 优化：对于表示转换的元素，可以通过`"a"(...) fromTo "b"`的语法构建。
* [ ] breeze-dsl 实现`YamlDsl`。（不推荐使用，但是作为规范）
***
* [ ] breeze-core&breeze-text 考虑两者之间的界限。
* [ ] breeze-serialization 提供yaml的序列化实现。
* [ ] breeze-serialization 提供xml的序列化实现。
* [ ] breeze-dsl 编写一种泛用而灵活的富文本Dsl的规范。不提供实现。
* [ ] breeze-dsl 编写一种泛用而灵活的思维导图Dsl的规范。不提供实现。
* [ ] breeze-game 初步完成有限状态机的规范。
* [ ] breeze-game 初步完成实体&组件的规范。
* [ ] breeze-core Uri构建和编码的扩展。
* [ ] breeze-core 利用反射的类型转换的扩展。
* [ ] breeze-text 英文单数与复数的转化。
* [ ] breeze-core 提供额外的集合的实现。参考Guava。
* [ ] breeze-logger 完成自己的日志器的实现。
* [ ] 提供一种“全局变量池”的实现，类似依赖注入，避免`var foo = ...`写法。
* [ ] 提供一种Result的实现。
* [ ] ［可能］ 提供生成器以从json/yaml文件生成java/kotlin数据类。

# 长期

* [X] 移除对非框架类的第三方库的依赖。
* [ ] 检查`awesome-kotlin`中可参考的、有必要参考的项目。
***
* [X] 上传到Github。
* [X] 同步项目到Bintray。
* [ ] 发布项目到JCenter。
* [ ] 完善各个模块的`README.md`文档。
