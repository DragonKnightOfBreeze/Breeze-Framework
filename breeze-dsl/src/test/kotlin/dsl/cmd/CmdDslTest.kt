package icu.windea.breezeframework.dsl.cmd

import kotlin.test.*

class CmdDslTest {
	@Test
	fun test1() {
		println(cmdDsl {
			color("123", CmdColor.Red)
		})
	}

	@Test
	fun testColor() {
		println(cmdDsl { color("hello world!", CmdColor.Black) })
		println(cmdDsl { color("hello world!", CmdColor.Red) })
		println(cmdDsl { color("hello world!", CmdColor.Green) })
		println(cmdDsl { color("hello world!", CmdColor.Yellow) })
		println(cmdDsl { color("hello world!", CmdColor.Blue) })
		println(cmdDsl { color("hello world!", CmdColor.Magenta) })
		println(cmdDsl { color("hello world!", CmdColor.Cyan) })
		println(cmdDsl { color("hello world!", CmdColor.LightGray) })
	}

	@Test
	fun test2() {
		println(cmdDsl {
			bgColor("123", CmdColor.Red)
		})
	}

	@Test
	fun test3() {
		println(cmdDsl {
			"""
			=================================================================
			There is a story of ${color("dragons and dragon knights", CmdColor.LightBlue)}
			who have been traveling all over the known universes
			and telling ${color("stories and legends", CmdColor.Cyan)} to ${color("mortal beings", CmdColor.DarkGray)}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color("across the galaxy", CmdColor.Yellow)}
			even in the far-long past.
			=================================================================
			""".trimIndent()
		})
	}

	@Test
	fun test4() {
		println(cmdDsl {
			"""
			=================================================================
			There is a story of ${bold(color("dragons and dragon knights", CmdColor.LightBlue))}
			who have been ${style("traveling all over the known universes", CmdStyle.Underline, CmdStyle.Italic)}
			and telling ${color("stories and legends", CmdColor.Cyan)} to ${color("mortal beings", CmdColor.Red)}
			from time immemorial
			for an immeasurable time,
			which used to be very well-known ${color("across the galaxy", CmdColor.Yellow)}
			long-long ago in the river of time.

			Few people know ${italic("who they are")},
			but once all intelligence knew, that is, ${bold(color("Breeze Knights", CmdColor.Blue))}.

			${underline(color("And also, that is in the daydream fairy tale.", CmdColor.DarkGray))}
			=================================================================
			""".trimIndent()
		})
	}
}
