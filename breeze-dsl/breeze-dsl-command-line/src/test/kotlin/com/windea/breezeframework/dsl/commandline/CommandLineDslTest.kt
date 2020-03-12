package com.windea.breezeframework.dsl.commandline


import com.windea.breezeframework.dsl.commandline.CommandLine.*
import kotlin.test.*

class CommandLineDslTest {
	@Test
	fun test1() {
		println(commandLine {
			color("123", Color.Red)
		})
	}

	@Test
	fun testColor() {
		println(commandLine { color("hello world!", Color.Black) })
		println(commandLine { color("hello world!", Color.Red) })
		println(commandLine { color("hello world!", Color.Green) })
		println(commandLine { color("hello world!", Color.Yellow) })
		println(commandLine { color("hello world!", Color.Blue) })
		println(commandLine { color("hello world!", Color.Magenta) })
		println(commandLine { color("hello world!", Color.Cyan) })
		println(commandLine { color("hello world!", Color.LightGray) })
	}

	@Test
	fun test2() {
		println(commandLine {
			bgColor("123", Color.Red)
		})
	}

	@Test
	fun test3() {
		println(commandLine {
			"""
			=================================================================
			There is a story of ${color("dragons and dragon knights", Color.LightBlue)}
			who have been traveling all over the known universes
			and telling ${color("stories and legends", Color.Cyan)} to ${color("mortal beings", Color.DarkGray)}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color("across the galaxy", Color.Yellow)}
			long-long ago.
			=================================================================
			""".trimIndent()
		})
	}

	@Test
	fun test4() {
		println(commandLine {
			"""
			=================================================================
			There is a story of ${bold(color("dragons and dragon knights", Color.LightBlue))}
			who have been ${style("traveling all over the known universes", Style.Underline, Style.Italic)}
			and telling ${color("stories and legends", Color.Cyan)} to ${color("mortal beings", Color.Red)}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color("across the galaxy", Color.Yellow)}
			long-long ago in the river of time.

			Few people know ${italic("who they are")},
			but once all intelligence knew, that is, ${bold(color("Breeze Knights", Color.Blue))}.

			${underline(color("And also, that is in the daydream fairy tale.", Color.DarkGray))}
			=================================================================
			""".trimIndent()
		})
	}
}
