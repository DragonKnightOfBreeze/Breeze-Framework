package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*

/**
 * 配置类的接口。
 *
 * 这个接口的实现应当是数据类，且对应的应用类应当至少有两个构造方法。
 * 主要构造方法以其为默认参数，而次要构造方法以其代码块为参数。
 */
@UnstableImplementationApi
interface Config
