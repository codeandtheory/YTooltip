/**
 * To define plugins
 */
object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
}

/**
 * To define dependencies
 */
object Deps {
    // Kotlin
    val core_ktx by lazy { "androidx.core:core-ktx:${Versions.core_ktx}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }

    //Compose
    val compose_ui by lazy { "androidx.compose.ui:ui:${Versions.compose_version}" }
    val compose_material by lazy { "androidx.compose.material:material:${Versions.compose_version}" }
    val compose_preview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.compose_version}" }

    //Android
    val androidx_lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle}" }
    val androidx_activity_compose by lazy { "androidx.activity:activity-compose:${Versions.androidx_activity_compose}" }

    //Test
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val ext_junit by lazy { "androidx.test.ext:junit:${Versions.ext_junit}" }
    val espresso_core by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso_core}" }
    val ui_test_junit by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.compose_version}" }

    val ui_tooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.compose_version}" }
    val ui_test_manifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.compose_version}" }
    val kotlin_stdlib by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk8" }

    //component
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }

    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayout}" }
}