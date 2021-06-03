// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.misc

import icu.windea.breezeframework.core.extension.*
import org.junit.*

class AcceptCharArrayTest {
	@Test
	fun regionMatchTest(){
		println("hello world".regionMatches(3,"llo",0,"llo".length))
		println("hello world".regionMatches(2,"llo",0,"llo".length))
	}

	@Test
	fun test(){
		//val string = "1+2++"
		//val tc = '+';
		//val ts = "++";
		//
		//val chars = string.toCharArray()
		//var index = 0;
		//while(true){
		//	run{
		//		val char = chars[index]
		//		//when{
		//		//	matches(chars,index,char,tc) -> println(1)
		//		//	matches(chars,index,char,ts) -> println(2)
		//		//}
		//	}
		//	index ++;
		//}
	}
}

class CharArrayMatcher(
	val chars:CharArray,
	var index:Int,
	var char:Char
) {
	//regionMatches

	fun matches(otherChar: Char): Boolean {
		val char = chars[index]
		if(char == otherChar) {
			index++;
			return true;
		}
		return false;
	}

	//fun matches(chars: CharArray,val startIndex, otherChars: CharArray): Boolean {
	//	"".regionMatches()
	//	val size = chars.size
	//	val otherSize = otherChars.size
	//	if(size < otherSize) return false;
	//	for(otherChar in otherChars) {
	//		val char = chars[index]
	//		if(otherChar == char){
	//			index ++
	//		}else{
	//			return false
	//		}
	//	}
	//}
}
