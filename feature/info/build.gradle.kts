plugins {
    id("runewords.android.library")
    id("runewords.android.library.compose")
    id("runewords.android.hilt")
}

android {
    namespace = "com.beok.runewords.info"
}

dependencies {
    implementation(project(":common"))

    implementation(libs.hilt.navigation.compose)
}
