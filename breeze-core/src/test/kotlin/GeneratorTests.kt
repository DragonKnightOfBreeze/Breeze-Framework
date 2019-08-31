import com.windea.utility.common.extensions.*
import com.windea.utility.common.generators.text.*
import org.junit.*

class GeneratorTests {
	@Test
	fun test1() {
		val path = "D:\\OneDrive\\My Documents\\My Projects\\Java\\Utility\\Kotlin-Utility\\src\\test\\resources\\Annotation.yml"
		val outputPath = path.replace(".yml", ".xml")
		IdeaConfigGenerator.generateYamlAnnotation(path.toFile(), outputPath.toFile())
	}
}
