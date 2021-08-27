// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("FileExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import java.io.*
import java.net.*
import java.util.zip.*

/**不包含扩展名的文件名。*/
val File.shortName: String get() = this.nameWithoutExtension

//https://zhidao.baidu.com/question/2078337860385272108.html
//https://www.oschina.net/question/112255_44552
//https://www.iteye.com/blog/chinacheng-841270
//https://www.cnblogs.com/chenglc/p/7117847.html

/**得到文件的MIME类型。*/
val File.mimeType: String?
	get() = URLConnection.guessContentTypeFromName(this.name)

/**得到文件的真实MIME类型。不保证准确性。*/
val File.actualMimeType: String?
	get() = URLConnection.guessContentTypeFromStream(this.inputStream())


/**创建当前文件以及所有的父目录。*/
fun File.createFile(): Boolean {
	this.parentFile.mkdirs()
	return this.createNewFile()
}

/**创建当前目录以及所有的父目录。*/
fun File.createDirectory(): Boolean {
	return this.mkdirs()
}

/**
 * 使用zip格式压缩当前文件或目录。
 */
@UnstableApi
@Throws(IOException::class)
fun File.zip(fileName: String = "$name.zip"): File {
	val source = this
	check(source.exists()) { "Source file or directory does not exist." }
	val target = File(source.parentFile, fileName)
	if(target.exists()) target.delete() //覆盖旧的文件
	val outputStream = ZipOutputStream(BufferedOutputStream(FileOutputStream(target)))
	outputStream.use {
		addEntry("/", source, it)
	}
	return target
}

@UnstableApi
@Throws(IOException::class)
fun File.unzip(fileName: String){
	val source = this
	check(source.exists()) { "Source file does not exist." }
	val inputStream = ZipInputStream(BufferedInputStream(FileInputStream(source)))
	var entry: ZipEntry? = null
	while(inputStream.nextEntry.also { entry = it } != null && !entry!!.isDirectory) {
		val target = File(source.parent, entry!!.name)
		if(!target.parentFile.exists()) {
			target.parentFile.mkdirs() //创建文件父目录
		}
		val buffer = ByteArray(1024 * 10)
		val outputStream = BufferedOutputStream(FileOutputStream(target),buffer.size)
		outputStream.use {
			var read: Int
			while(inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
				outputStream.write(buffer, 0, read)
			}
			outputStream.flush()
		}
	}
	inputStream.closeEntry()
}

@Throws(IOException::class)
private fun addEntry(base: String, source: File, outputStream: ZipOutputStream) {
	val entry = base + source.name
	if(source.isDirectory) {
		source.listFiles()?.forEach { file ->
			addEntry("$entry/", file, outputStream)
		}
	} else {
		val buffer = ByteArray(1024 * 10)
		val inputStream = BufferedInputStream(FileInputStream(source), buffer.size)
		inputStream.use {
			var read: Int
			outputStream.putNextEntry(ZipEntry(entry))
			while(inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
				outputStream.write(buffer, 0, read)
			}
			outputStream.closeEntry()
		}
	}
}


/**将当前文件转化为统一资源标识符。*/
fun File.toUri(): URI = this.toURI()

/**将当前文件转化为统一资源定位符。*/
fun File.toUrl(): URL = this.toURI().toURL()
