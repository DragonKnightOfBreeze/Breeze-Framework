package com.windea.utility.common.domain.text

import java.nio.file.*

/**路径信息。相比[Path]更加轻量，同时也能进行解构。*/
data class PathInfo(
	/**文件路径。*/
	val path: String,
	/**文件根路径。*/
	val root: String,
	/**文件所在文件夹。*/
	val parent: String,
	/**文件名。*/
	val fileName: String,
	/**不包含扩展名的文件名。*/
	val fileShotName: String,
	/**包含"."的文件扩展名。*/
	val fileExtension: String
) {
	/**是否存在上一级文件夹。*/
	val hasFileDirectory = parent.isNotEmpty()
	/**是否存在文件扩展名。*/
	val hasFileExtension = fileExtension.isNotEmpty()
	
	
	/**更改文件所在文件夹为新的文件夹。*/
	fun changeParent(newFileDirectory: String): String {
		return newFileDirectory + "\\" + fileName
	}
	
	/**更改文件名为新的文件名。*/
	fun changeFileName(newFileName: String): String {
		return parent + "\\" + newFileName
	}
	
	/**更改不包含扩展名在内的文件名为新的文件名，可指定是否返回全路径[returnFullPath]，默认为true。*/
	fun changeFileShotName(newFileShotName: String, returnFullPath: Boolean = true): String {
		val newFileName = newFileShotName + fileExtension
		return if(returnFullPath) parent + "\\" + newFileName else newFileName
	}
	
	/**更改文件扩展名为新的扩展名，可指定是否返回全路径[returnFullPath]，默认为true。*/
	fun changeFileExtension(newFileExtension: String, returnFullPath: Boolean = true): String {
		val newFileName = fileShotName + newFileExtension
		return if(returnFullPath) parent + "\\" + newFileName else newFileName
	}
	
	override fun toString(): String {
		return path
	}
}
