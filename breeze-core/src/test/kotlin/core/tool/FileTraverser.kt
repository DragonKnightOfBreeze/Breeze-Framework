// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.tool

import java.io.*

//需求：
//整理swagger注解
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "token", value = "令牌", paramType = "query", dataType = "string", required = true),
//        @ApiImplicitParam(name = "hotdbGroupId", value = "计算节点组id", paramType = "query", dataType = "int")
//    })

//思路：
//* 遍历包cn.hotdb.management.web.controllers和包cn.hotpu.management.web
//* 遍历每个java文件中的每一行，从"@ApiImplictParam("开始，到匹配的")"结束
//* 重新调整缩进+排序

object FileTraverser {
	val prefix = "@ApiImplicitParams"
	val MatchedPrefix = "("
	val matchedSuffix = ")"
	val childPrefix = "@ApiImplicitParam"
	val childMatchedPrefix = "("
	val childMatchedSuffix = ")"
	val childSeparator = ","
	val fieldSeparator = "="
	val fieldSorts = arrayOf("name", "value", "paramType", "dataType", "required")

	fun traverse(file: File) {
		val newLines = mutableListOf<String>()
		file.forEachLine { line ->
			val trimLine = line.trim()
			newLines.add(line)
		}
		file.writeText(newLines.joinToString("\n"))
	}
}
