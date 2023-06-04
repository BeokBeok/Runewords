plugins {
    id("runewords.android.library")
    id("runewords.android.library.compose")
    id("runewords.android.hilt")
    id("de.mannodermaus.android-junit5")
}

android {
    namespace = "com.beok.runewords.detail"

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":tracking"))

    implementation(libs.play.services.ads)
    implementation(libs.play.core.ktx)

    implementation(libs.firebase.firestore.ktx)

    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)

    testImplementation(libs.assertj.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test.junit)
}
