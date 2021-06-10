// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.tryout

import org.junit.*
import java.lang.reflect.*

interface Builder<T> {
	fun build():T
}


data class Item @JvmOverloads constructor(
	val id:Long = 0,
	val name:String = "123"
)

interface ItemBuilder:Builder<Item>

class ItemBuilderImpl:ItemBuilder{
	override fun build(): Item {
		return Item(0,"test")
	}
}

class BuilderInvocationHandler<T>(val dataType:Class<T>):InvocationHandler{
	override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
		//如果调用build方法
		when {
			method.name == "build"&& (args == null || args.isEmpty()) -> {
				println("invoke build method.")
				return dataType.newInstance() as Any
			}
			method.name == "equals" && (args != null && args.size ==1) -> {
				val other = args[0] as? Class<*> ?:return false
				return dataType == other
			}
			method.name == "hashCode" && (args == null || args.isEmpty()) ->{
				return 1
			}
			method.name == "toString" && (args == null || args.isEmpty()) ->{
				return "Builder(dataType=${dataType})"
			}
			else -> {
				throw UnsupportedOperationException("Unsupported proxy method.")
			}
		}
	}
}

inline fun <reified T:Any> builder() = getBuilderProxy(T::class.java)

fun <T> getBuilderProxy(dataType:Class<T>):Any{
	return Proxy.newProxyInstance(dataType.classLoader,arrayOf(Builder::class.java),BuilderInvocationHandler(dataType))
}

class BuilderTest{
	@Test
	fun test(){
		val builder = builder<Item>()
		builder as Builder<Item>
		println(builder.toString())
		val item = builder.build()
		println(item)
	}
}



