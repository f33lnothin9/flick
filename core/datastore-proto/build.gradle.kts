plugins {
    alias(libs.plugins.flick.android.library)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "ru.resodostudios.flick.core.datastore.proto"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.protobuf.kotlin.lite)
}
