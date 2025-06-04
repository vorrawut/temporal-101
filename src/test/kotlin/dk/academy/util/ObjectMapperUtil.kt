package dk.academy.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.boot.jackson.JsonComponentModule
import org.springframework.boot.jackson.JsonMixinModule
import org.springframework.core.io.ClassPathResource
import kotlin.text.decodeToString

// This is over-the-top, but just to ensure the ObjectMapper behaves the same
val objectMapper: ObjectMapper = jacksonObjectMapper()
    .registerModule(Jdk8Module())
    .registerModule(JavaTimeModule())
    .registerModule(ParameterNamesModule())
    .registerModule(JsonComponentModule())
    .registerModule(JsonMixinModule())
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false)

fun readBytes(path: String): ByteArray = readClasspath(path).contentAsByteArray
fun readClasspath(path: String): ClassPathResource = ClassPathResource(path)
fun readString(path: String): String = readClasspath(path).contentAsByteArray.decodeToString()

inline fun <reified T> readObject(path: String): T = objectMapper.readValue(readString(path))
inline fun <reified T> readObject(path: ClassPathResource): T = objectMapper.readValue(path.contentAsByteArray.decodeToString())
