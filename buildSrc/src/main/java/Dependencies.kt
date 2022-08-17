/**
 * To define plugins
 */
object BuildPlugins {
    const val android =  "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlin =  "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

/**
 * To define dependencies
 */
object Deps {
    // Kotlin
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val kotlin =  "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //Compose
    const val compose_ui =  "androidx.compose.ui:ui:${Versions.compose_version}"
    const val compose_material =  "androidx.compose.material:material:${Versions.compose_version}"
    const val compose_preview =  "androidx.compose.ui:ui-tooling-preview:${Versions.compose_version}"

    //Android
    const val androidx_lifecycle =  "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle}"
    const val androidx_activity_compose =  "androidx.activity:activity-compose:${Versions.androidx_activity_compose}"

    //Test
    const val junit =  "junit:junit:${Versions.junit}"
    const val ext_junit =  "androidx.test.ext:junit:${Versions.ext_junit}"
    const val espresso_core =  "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val ui_test_junit =  "androidx.compose.ui:ui-test-junit4:${Versions.compose_version}"

    const val ui_tooling =  "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
    const val ui_test_manifest =  "androidx.compose.ui:ui-test-manifest:${Versions.compose_version}"
    const val kotlin_stdlib =  "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

    //component
    const val appcompat =  "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign =  "com.google.android.material:material:${Versions.material}"

    const val constraintLayout =  "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayout}"
}