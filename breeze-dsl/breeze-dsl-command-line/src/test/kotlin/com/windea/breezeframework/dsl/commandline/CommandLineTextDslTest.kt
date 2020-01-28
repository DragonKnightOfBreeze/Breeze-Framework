package com.windea.breezeframework.dsl.commandline


import com.windea.breezeframework.dsl.commandline.CommandLineText.*
import kotlin.test.*

class CommandLineTextDslTest {
	@Test OK
	fun test1() {
		println(commandLineText {
			color(Color.Red, "123")
		})
	}

	@Test OK
	fun testColor() {
		println(commandLineText { color(Color.Black, "hello world!") })
		println(commandLineText { color(Color.Red, "hello world!") })
		println(commandLineText { color(Color.Green, "hello world!") })
		println(commandLineText { color(Color.Yellow, "hello world!") })
		println(commandLineText { color(Color.Blue, "hello world!") })
		println(commandLineText { color(Color.Magenta, "hello world!") })
		println(commandLineText { color(Color.Cyan, "hello world!") })
		println(commandLineText { color(Color.LightGray, "hello world!") })
	}

	@Test OK
	fun test2() {
		println(commandLineText {
			bgColor(Color.Red, "123")
		})
	}

	@Test OK
	fun test3() {
		println(commandLineText {
			"""
			=================================================================
			There is a story of ${color(Color.LightBlue, "dragons and dragon knights")}
			who have been traveling all over the known universes
			and telling ${color(Color.Cyan, "stories and legends")} to ${color(Color.DarkGray, "mortal beings")}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color(Color.Yellow, "across the galaxy")}
			long-long ago.
			=================================================================
			""".trimIndent()
		})
	}

	@Test OK
	fun test4() {
		println(commandLineText {
			"""
			=================================================================
			There is a story of ${underline(color(Color.LightBlue, "dragons and dragon knights"))}
			who have been ${style(Style.Underline, Style.Italic) { "traveling all over the known universes" }}
			and telling ${color(Color.Cyan, "stories and legends")} to ${color(Color.DarkGray, "mortal beings")}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color(Color.Yellow, "across the galaxy")}
			long-long ago in the river of time.

			Few people know ${italic("who they are")},
			but once all intelligence knew, that is, ${bold(color(Color.Blue, "Breeze Knights"))}.
			=================================================================
			""".trimIndent()
		})
	}
}
