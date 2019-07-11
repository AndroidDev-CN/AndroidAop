package com.aku.androidaop.aop.aspect

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.util.concurrent.locks.ReentrantLock


/**
 * [com.aku.androidaop.aop.anno.SingleClick]
 * 可以使用lock、给view设置时间的Tag来限制重复点击
 * fixme 注解参数的获取    针对全局和单个的限定
 */
@Aspect
class SingleClickAspect {

    private val lock = ReentrantLock()

    @Pointcut("execution(@com.aku.androidaop.aop.anno.SingleClick * *(..))") //方法切入点
    fun methodAnnotated() {
    }

    @Around(value = "methodAnnotated()")//在连接点进行方法替换
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        if (lock.isLocked) {
//            val singleClick = (joinPoint.signature as MethodSignature).method.getAnnotation(SingleClick::class.java)
//            ToastUtils.showShort(singleClick?.msg)
            return
        }
        lock.lock()
        joinPoint.proceed()
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000L)
            launch(Dispatchers.Main) {
                lock.unlock()
            }
        }
    }
}