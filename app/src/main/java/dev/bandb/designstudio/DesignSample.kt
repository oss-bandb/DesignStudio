package dev.bandb.designstudio

import dev.bandb.design1.compose.Design1ComposeActivity
import dev.bandb.designstudio.design1.Design1Activity
import kotlin.reflect.KClass

object Samples {
    data class DesignSample(
        val title: String,
        val clazz: KClass<*>
    )

    val data = listOf(
        DesignSample("Todo App", Design1Activity::class),
        DesignSample("Todo App (Compose)", Design1ComposeActivity::class),
    )
}