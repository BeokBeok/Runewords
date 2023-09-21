import com.google.devtools.ksp.gradle.KspExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.process.CommandLineArgumentProvider

internal class AndroidLibraryRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            extensions.configure<KspExtension> {
                arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                implementation(libs.findLibrary("room.ktx").get())
                ksp(libs.findLibrary("room.compiler").get())
            }
        }
    }

    class RoomSchemaArgProvider(
        @get:InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File,
    ) : CommandLineArgumentProvider {
        override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
    }
}
