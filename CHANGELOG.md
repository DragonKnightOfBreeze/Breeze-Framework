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
* [X] 确保项目能够正常构建，配置可选的依赖（不确定是否生效）。
* [X] breeze-core 类似`TODO()` 的方法。
* [X] 提供扩展注解的容器。
* [X] 提供必要的日志对象（包括到最近一次非内联调用处的日志对象）。
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
* [X] 为有望被标准库实现的实现添加`@OutlookImplementationApi`注解。
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
* [ ] breeze-data TODO 提供mermaid和plantUml的Dsl。
    * 至少为前者的graph，后者的state graph提供Dsl。后者的语法根本不是人玩的。
    * [ ] UPDATING 提供mermaid flow chart的Dsl。
* [ ] breeze-data DELAY 提供更多图形语言的Dsl。
    * 主要是sequence和flow。
* [ ] breeze-core DELAY Uri构建和编码的扩展。
* [ ] breeze-core DELAY 利用反射的类型转换的扩展。
* [ ] breeze-text DELAY en 单数与复数的转化。
* [ ] breeze-text DELAY 数字的简化表示的转化。（1000->1k, 1000->1千）

* [X] TODO 上传到Github。
* [X] TODO 同步项目到Bintray。
* [ ] DELAY 发布项目到JCenter。
* [ ] TODO 完善各个模块的`README.md`文档。
