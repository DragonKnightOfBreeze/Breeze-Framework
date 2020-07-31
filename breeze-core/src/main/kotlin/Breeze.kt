package com.windea.breezeframework.core

import com.windea.breezeframework.core.extensions.*

/**
 * 微风吹向远方……
 */
object Breeze {
	init {
		println("""
			Breeze is blowing ...
		""".trimIndent())
	}

	 fun Any?.equalsBr(other:Any?, deepOp:Boolean = true): Boolean {
		 return when {
			 this !is Array<*> || other !is Array<*> -> this == other
			 deepOp -> this.contentDeepEquals(other)
			 else -> this.contentEquals(other)
		 }
	 }

	 fun Any?.hashCodeBr(deepOp:Boolean = true): Int {
		 return when {
			 this !is Array<*> -> this.hashCode()
			 deepOp -> this.contentDeepHashCode()
			 else -> this.contentHashCode()
		 }
	 }

	fun Any?.toStringBr(deepOp:Boolean = true): String {
		return when {
			this !is Array<*> -> this.toString()
			deepOp -> this.contentDeepToString()
			else -> this.contentToString()
		}
	}

	fun Any?.isEmptyBr():Boolean{
		return when{
			this == null -> true
			this is Array<*> -> this.isEmpty()
			this is Collection<*> -> this.isEmpty()
			this is Iterable<*> -> this.none()
			this is Map<*,*> -> this.isEmpty()
			this is Sequence<*> -> this.none()
			else -> false
		}
	}

	fun Any?.isNotEmptyBr():Boolean{
		return !this.isEmptyBr()
	}

	inline fun <T,R> T.ifEmptyBr(transform:(T)->R):R where T:R {
		return if(this.isEmptyBr()) transform(this) else this
	}

	inline fun <T,R> T.ifNotEmptyBr(transform:(T)->R):R where T:R {
		return if(this.isNotEmptyBr()) transform(this) else this
	}

	fun <T> Any?.getBr(path:String):T{
		TODO()
	}

	fun <T> Any?.getOrNullBr(path:String):T{
		TODO()
	}

	fun <T> Any?.getOrElseBr(path:String,defaultValue:()->T){
		TODO()
	}

	fun <T> Any?.getOrSetBr(path:String,defaultValue:()->T){
		TODO()
	}

	fun <T> Any?.setBr(path:String,value:T) {
		TODO()
	}

	fun <T> Any?.addBr(path:String,vararg values:T){
		TODO()
	}

	fun <T> Any?.removeBr(path:String){
		TODO()
	}
}
