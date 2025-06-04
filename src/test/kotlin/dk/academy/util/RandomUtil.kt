package dk.academy.util

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import kotlin.jvm.java

val parameters: EasyRandomParameters = EasyRandomParameters().excludeField { it.name in listOf("createdAt", "updatedAt", "version") }
val random: EasyRandom = EasyRandom(parameters)

inline fun <reified T> EasyRandom.next(): T = nextObject(T::class.java)
