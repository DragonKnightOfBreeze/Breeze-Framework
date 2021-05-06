// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.misc

import icu.windea.breezeframework.core.extension.*

interface Traverser<T,ET> {

}

class StringTraverser(
	private val value:String
):Traverser<String,Char> {
	var index :Int= 0
	//private set
	var element: Char = value[index]
	//private set

	fun traverse(block:()->Unit){
		for(c in value) {
			block()
		}
		index++;
		//next();
	}

	//fun reset(){
	//	index = 0;
	//}
	//
	//fun next(){
	//	index++;
	//}
	//
	//fun prev(){
	//	index--;
	//}
	//
	//fun accept(element:Char):Boolean{
	//	return element == this.element
	//}
	//
	//fun acceptAndTraverse(element:Char):Boolean{
	//	if(element == this.element){
	//		next();
	//		return true;
	//	}
	//	return false;
	//}
}
