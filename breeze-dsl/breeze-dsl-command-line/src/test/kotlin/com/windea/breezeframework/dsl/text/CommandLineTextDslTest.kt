package com.windea.breezeframework.dsl.text

import com.windea.breezeframework.dsl.text.CommandLineColor.*
import com.windea.breezeframework.dsl.text.CommandLineDisplayMode.*
import kotlin.test.*

class CommandLineTextDslTest {
	@Test //TESTED OK
	fun test1() {
		println(commandLineText {
			color(Red, "123")
		})
	}
	
	@Test
	fun testColor() {
		
		//	Black(30),
		//	Red(31),
		//	Green(32),
		//	Yellow(33),
		//	Blue(34),
		//	Magenta(35),
		//	Cyan(36),
		//	LightGray(37),
		println(commandLineText { color(Black, "hello world!") })
		println(commandLineText { color(Red, "hello world!") })
		println(commandLineText { color(Green, "hello world!") })
		println(commandLineText { color(Yellow, "hello world!") })
		println(commandLineText { color(Blue, "hello world!") })
		println(commandLineText { color(Magenta, "hello world!") })
		println(commandLineText { color(Cyan, "hello world!") })
		println(commandLineText { color(LightGray, "hello world!") })
	}
	
	@Test //TESTED OK
	fun test2() {
		println(commandLineText {
			bgColor(Red, "123")
		})
	}
	
	@Test //TESTED OK
	fun test3() {
		println(commandLineText {
			"""
			There is a story of ${color(LightBlue, "dragons and dragon knights")}
			who have been traveling all over the known universes
			and telling ${color(Cyan, "stories and legends")} to ${color(DarkGray, "mortal beings")}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color(Yellow, "across the galaxy")}
			long-long ago.
			""".trimIndent()
		})
	}
	
	@Test //TESTED OK
	fun test4() {
		println(commandLineText {
			"""
			=================================================================
			There is a story of ${underline(color(LightBlue, "dragons and dragon knights"))}
			who have been traveling all over the known universes
			and telling ${color(Cyan, "stories and legends")} to ${color(DarkGray, "mortal beings")}
			from time immemorial
			for an immeasurable,
			which used to be very well-known ${color(Yellow, "across the galaxy")}
			long-long ago in the river of time.
			
			Few people know ${italic("who they are")},
			but once all intelligence knew, that is, ${bold(color(Blue, "Breeze Knights"))}.
			=================================================================
			""".trimIndent()
		})
	}
	
	@Test
	fun test5() {
		println(commandLineText { advance { "Hello world!" } }) //no effect
		println(commandLineText { advance(null, null, Default) { "Hello world! Default" } }) //no effect
		println(commandLineText { advance(null, null, Bold) { "Hello world! Bold" } }) //no effect
		println(commandLineText { advance(null, null, LightColor) { "Hello world! LightColor" } })
		println(commandLineText { advance(null, null, Underline) { "Hello world! Underline" } })
		println(commandLineText { advance(null, null, Blink) { "Hello world! Blink" } })
		println(commandLineText { advance(null, null, Italic) { "Hello world! Italic" } })
		println(commandLineText { advance(null, null, Invert) { "Hello world! Invert" } })
	}
}
