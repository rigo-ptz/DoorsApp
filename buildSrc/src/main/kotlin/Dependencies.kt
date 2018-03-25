/**
 * @author Yamushev Igor
 * @since  17.03.18
 */
object Versions {
    val compileSdkVersion = 27
    val minSdkVersion = 19
    val targetSdkVersion = 27
    val versionCode = 1
    val versionName = "1.0"
    val kotlin = "1.1.51"
    val multidex = "1.0.2"
    val junit = "4.12"
    val testRunner = "1.0.1"
    val appcompat = "27.1.0"
    val espressoCore = "3.0.1"
    val AAC = "1.0.0"
    val dagger2 = "2.10"
    val gson = "2.8.1"
    val retrofit = "2.3.0"
    val okhttp = "3.9.0"
    val databindingCompiler = "3.0.1"
    val rxjava = "2.1.0"
    val rxandroid = "2.0.1"
    val rxkotlin = "2.0.3"
    val decoro = "1.3.4"
    val ktx = "0.2"
    val constraintLayout = "1.1.0-beta5"
}

object Libs {
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"
    val multiDex = "com.android.support:multidex:${Versions.multidex}"
    val junit = "junit:junit:${Versions.junit}"
    val testRunner = "com.android.support.test:runner:${Versions.testRunner}"
    val appcompatV7 = "com.android.support:appcompat-v7:${Versions.appcompat}"
    val supportDesign = "com.android.support:design:${Versions.appcompat}"
    val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
    val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espressoCore}"
    val componentsExtensions = "android.arch.lifecycle:extensions:${Versions.AAC}"
    val componentsCompiler = "android.arch.lifecycle:compiler:${Versions.AAC}"
    val dagger2 = "com.google.dagger:dagger:${Versions.dagger2}"
    val dagger2Compiler = "com.google.dagger:dagger-compiler:${Versions.dagger2}"
    val javaxAnnotation = "javax.annotation:jsr250-api:1.0"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    val databindingCompiler = "com.android.databinding:compiler:${Versions.databindingCompiler}"
    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    val decoro = "ru.tinkoff.decoro:decoro:${Versions.decoro}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
}