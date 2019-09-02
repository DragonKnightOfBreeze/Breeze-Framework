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
* [ ] DELAY breeze-core Uri构建和编码的扩展。
* [ ] DELAY breeze-core 利用反射的类型转换的扩展。
* [X] breeze-core 实现元素的向下复制和平滑复制的方法。（`[1,2]->[[1,1],[2,2]]`和`[1,2]->[1,1,2,2]`，已实现`[1,2]->[1,2,1,2]`）
* [X] breeze-core 实现`addPrefix`等方法，~~实现`ifStartsWith`等方法~~。
* [ ] breeze-core 为`Sequence`尽可能地适配`Iterable`的扩展。
* [ ] breeze-text en 序数与数量的转化。
* [ ] breeze-text en 单数与复数的转化。
* [ ] 重构`DataLoader`。
