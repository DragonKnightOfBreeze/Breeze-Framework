package com.windea.breezeframework.dsl.text

import com.windea.breezeframework.dsl.text.CommandLineColor.*
import com.windea.breezeframework.dsl.text.CommandLineDisplayMode.*
import org.junit.*

class CommandLineTextDsl {
	@Test //TESTED OK
	fun test1() {
		println(commandLineText {
			color(Red, "123")
		})
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
			for an immeasurable time
			which used to be very well known ${color(Yellow, "across the galaxy")}
			long long ago.
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
			for an immeasurable
			which used to be very well known ${color(Yellow, "across the galaxy")}
			long long ago in the river of time.
			
			Few people know ${italic("who they are")} and ${italic("what mission they bear now")},
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
