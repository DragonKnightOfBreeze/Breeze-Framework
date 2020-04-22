package com.windea.breezeframework.dsl

/**注明这个注解定义了一个顶级的领域特定语言构建方法。这些方法用于生成文档或进行配置。*/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class TopDslFunction

/**注明这个注解定义了一个内联的领域特定语言构建方法。这些方法不会自动注册对应的元素。*/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class InlineDslFunction

/**注明这个注解定义了一个领域特定语言构建方法。*/
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class DslFunction

//TODO 让注解处理器生效
/**注明这个注解定义了一个领域特定语言文件。这个文件是由Kotlin脚本编写的，其输出结果是一个[DslDocument]。*/
@MustBeDocumented
@Target(AnnotationTarget.FILE)
annotation class DslFile

/**注明这个注解定义了必须被显式（延迟）初始化的属性。*/
@MustBeDocumented
@Target(AnnotationTarget.PROPERTY)
annotation class MustBeInitialized
