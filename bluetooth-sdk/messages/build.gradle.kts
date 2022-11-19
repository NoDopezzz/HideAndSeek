plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.21"
}

dependencies {
    implementation(Libraries.Utils.jsonSerialization)
}