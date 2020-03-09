package com.windea.breezeframework.core.extensions

/**向当前列表中添加一个元素，然后返回自身。这个方法关注操作的性能。*/
fun <T, C : MutableList<T>> C.with(element: T): C = this.apply { add(element) }

/**向当前列表中添加多个元素，然后返回自身。这个方法关注操作的性能。*/
fun <T, C : MutableList<T>> C.with(vararg elements: T): C = this.apply { elements.forEach { add(it) } }

/**向当前集中添加一个元素，然后返回自身。这个方法关注操作的性能。*/
fun <T, C : MutableSet<T>> C.with(element: T): C = this.apply { add(element) }

/**向当前集中添加多个元素，然后返回自身。这个方法关注操作的性能。*/
fun <T, C : MutableSet<T>> C.with(vararg elements: T): C = this.apply { elements.forEach { add(it) } }

/**向当前映射中添加一个键值对，然后返回自身。这个方法关注操作的性能。*/
fun <K, V, M : MutableMap<K, V>> M.with(key: K, value: V): M = this.apply { put(key, value) }

/**向当前映射中添加多个键值对，然后返回自身。这个方法关注操作的性能。*/
fun <K, V, M : MutableMap<K, V>> M.with(vararg pairs: Pair<K, V>): M = this.apply { putAll(pairs) }


//fun Any.mapToMap():Map<String,Any?>{
//	val properties = this::class.java.methods.filter { it.name.startsWith("get") && it.name != "getClass" }
//
//
//	TODO()
//}
//
//fun Any.mapToMapOrString():Any{
//	val properties = this::class.java.methods.filter { it.name.startsWith("get") && it.name != "getClass" }
//	if(properties.isEmpty()) return this.toString()
//	return properties.associateBy({m->m.name.let { it[3].toLowerCase() + it[4,it.length] }},{it.invoke(this)})
//		.mapValues { (_,value)->
//			val valueType = value::class.java
//			when {
//				valueType.isPrimitive || valueType.isBoxed() -> value
//				Array::class.java.isAssignableFrom(valueType) ->
//			}
//
//		}
//}
//
//fun Class<*>.isBoxed():Boolean = TODO()
