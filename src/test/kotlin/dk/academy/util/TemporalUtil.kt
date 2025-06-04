package dk.academy.util

import dk.academy.config.TemporalConfig
import io.temporal.client.WorkflowClientOptions

object TemporalUtil {
    private val converters = TemporalConfig().dataConverter(objectMapper)
    val workerClientOptions = WorkflowClientOptions {
        setDataConverter(converters)
    }
}
