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
* [ ] breeze-data 代理实现`DataSerializer`的相关类。

* [ ] DELAY breeze-core Uri构建和编码的扩展。
* [ ] DELAY breeze-core 利用反射的类型转换的扩展。
* [X] DELAY breeze-text en 序数与数量的转化。
* [ ] DELAY breeze-text en 单数与复数的转化。
* [ ] DELAY breeze-text 数字的简化表示的转化。（1000->1k, 1000->1千）
