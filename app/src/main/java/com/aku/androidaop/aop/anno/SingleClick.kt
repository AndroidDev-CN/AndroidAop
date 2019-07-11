package com.aku.androidaop.aop.anno


@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.PROPERTY_GETTER)
annotation class SingleClick(
    val msg: String = "点击过快"
)