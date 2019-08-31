import com.windea.utility.common.loaders.*
import org.junit.*

class DataLoaderTests {
	@Test
	fun test1() {
		//TESTED
		val json = "{'a':1,'b':2}"
		val jsonMap1 = JsonLoader.instance.fromString(json)
		println(jsonMap1)
		
		//TESTED
		val jsonPath = "D:\\OneDrive\\My Documents\\My Projects\\Java\\Utility\\Kotlin-Utility\\src\\test\\resources\\test.json"
		val jsonMap2 = JsonLoader.instance.fromFile(jsonPath)
		println(jsonMap2)
		
		//TESTED
		val jsonString = JsonLoader.instance.toString(jsonMap2)
		println(jsonString)
		
		//TESTED 使用File(fullPath).writeText(string)
		val jsonData = mapOf("aaaa" to 1)
		JsonLoader.instance.toFile(jsonData, jsonPath)
		
		//TESTED
		val yaml = "{a: 1}"
		val yamlMap1 = YamlLoader.instance.fromString(yaml)
		println(yamlMap1)
		
		//TESTED
		val yamlPath = "D:\\OneDrive\\My Documents\\My Projects\\Java\\Utility\\Kotlin-Utility\\src\\test\\resources\\test.yml"
		val yamlMap2 = YamlLoader.instance.fromFile(yamlPath)
		println(yamlMap2)
		
		//TESTED
		val yamlString = YamlLoader.instance.toString(yamlMap2)
		println(yamlString)
		
		//TESTED
		val yamlData = mapOf("aaaa" to 1)
		YamlLoader.instance.toFile(yamlData, yamlPath)
	}
	
	//TESTED
	@Test
	fun test2() {
		val yamlPath = "D:\\OneDrive\\My Documents\\My Projects\\Java\\Utility\\Kotlin-Utility\\src\\test\\resources\\test.yml"
		val yamlData = mapOf("a" to 1, "b" to mapOf("c" to 2))
		YamlLoader.instance.toFile(yamlData, yamlPath)
	}
}
