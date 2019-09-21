package com.windea.breezeframework.core.annotations.core

/**表明被注解的类不应该被扩展。仅用于标记。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class AvoidExtension
