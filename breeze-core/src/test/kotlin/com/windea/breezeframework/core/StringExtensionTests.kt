package com.windea.breezeframework.core

import com.windea.breezeframework.core.enums.*
import com.windea.breezeframework.core.extensions.*
import org.junit.*

class StringExtensionTests {
	//经测试，String.split()返回的字符串列表长度是不固定的
	
	//TESTED
	@Test
	fun test1() {
		val str = "http://localhost:8080/www.github.com/DragonKnightOfBreeze"
		val strList = str.substringsOrEmpty("://", ":", "/")
		println(strList)
	}
	
	//TESTED
	@Test
	fun test2() {
		val str = "http://www.github.com/DragonKnightOfBreeze"
		val strList = str.substringsOrEmpty("://", ":", "/")
		println(strList)
	}
	
	//TESTED
	@Test
	fun test3() {
		val str = "www.github.com/DragonKnightOfBreeze"
		val strList = str.substringsOrEmpty("://", ":", "/")
		println(strList)
	}
	
	//TESTED
	@Test
	fun test4() {
		val str = "http://localhost:8080/www.github.com/DragonKnightOfBreeze"
		val strList = str.substringsOrEmpty(":", "/", "://")
		println(strList)
	}
	
	//TESTED
	@Test
	fun test5() {
		val str = "www.github.com"
		val strList = str.substrings("://", ":", "/") { _, s -> listOf("http", "localhost", "8080", s) }
		println(strList)
	}
	
	//TESTED
	@Test
	fun test6() {
		val str = "c://Documents/MyBook.txt"
		println(str.toPathInfo())
	}
	
	//TESTED
	@Test
	fun test7() {
		val str = "http://localhost:8080/www.github.com/DragonKnightOfBreeze?name=Windea&weapon=BreezesLanding&weapon=BreathOfBreeze"
		println(str.toUrlInfo())
		println(str.toUrlInfo().queryParamMap.getParam("name"))
		println(str.toUrlInfo().queryParamMap.getParam("weapon"))
		println(str.toUrlInfo().queryParamMap.getParams("weapon"))
	}
	
	//TESTED
	@Test
	fun test8() {
		println("*" * 10)
		println("******" / 3)
	}
	
	//TESTED
	@Test
	fun test9() {
		println("AbcAbc" equalsIc "ABCABC")
		println("AbcAbc" equalsIsc "abcAbc")
		println("AbcAbc" equalsIsc "abc_abc")
		println("AbcAbc" equalsIsc "Abc.Abc")
		println("AbcAbc" equalsIsc "ABC_ABC")
		println("AbcAbc" equalsIsc "Abc Abc")
		println("AbcAbc" equalsIsc "ABC_ABC")
		println("AbcAbc" equalsIsc "abc-abc")
	}
	
	@Test
	fun test10() {
		val str1 = """
		  "abc
		  abc
		    abc
		  abc"
		""".trimIndent()
		println(str1)
		
		val str2 = """
		  "abc
		  abc
		    abc
		  abc"
		""".toMultilineText()
		println(str2)
		
		val str3 = """
		  "abc
		  abc
		    abc
		  abc"
		""".toBreakLineText()
		println(str3)
	}
	
	enum class Abc { A, B, C }
	
	@Test
	fun test11() {
		println("A".toEnumValue<Abc>().name)
	}
	
	@Test
	fun test12() {
		println("A" * 3)
		println("AAAAAAAAA" / 3)
		println("A" * -3)
		println("AAAAAAAAA" / -3)
	}
	
	@Test
	fun test13() {
		println("""
		Name: Windea
		Weapon:
		- BreezesLanding
		- Wind Bow
		- Breath Of Breeze
		""".toMultilineText().deserialize<Any>(DataType.Yaml))
	}
	
	@Test
	fun testEscape() {
		println("123\n123".unescape())
		println("123\\n123".escape())
		
		println("123123".wrapQuote())
		println("123\"123".wrapQuote())
		println("123\'123".wrapQuote("'"))
	}
	
	@Test
	fun testRegexFunction() {
		println("123abc123abc123".split("abc".toRegex()))
		println("123abc123abc123".substrings(".*(abc).*".toRegex()))
		println(".*(abc).*".toRegex().matchEntire("123abc123abc123")?.groupValues)
	}
	
	fun String.removeComment() {}
	
	enum class CommentType(val prefix: String, val infix: String? = null, val suffix: String? = null) {
		JavaLineComment("//"),
		JavaBlockComment("/*", " *", "*/"),
		JavaDocumentComment("/**", " *", "*/"),
		CSharpDocumentComment("///", "///"),
		LuaLineComment("--"),
		LuaBlockComment("--[[", "", "]]"),
		PythonLineComment("#"),
		PythonBlockComment("'''", "'''")
	}
	
	@Test
	fun test14() {
		val strings = arrayOf("1", "2")
		println("123".startsWith(strings))
	}
	
	@Test
	fun test15() {
		println("123{0}123".messageFormat("a"))
		println("1'2'3{0}123".messageFormat("a"))
		println("123{}".customFormat("{}", "a"))
		println("123{0}".customFormat("{index}", "a"))
		println("123\${}".customFormat("\${}", "a"))
	}
	
	@Test
	fun test16() {
		println(listOf("  ", "123", "123").dropWhile { it.isBlank() })
		println(listOf("123", "   ", "123").dropWhile { it.isBlank() })
	}
}
