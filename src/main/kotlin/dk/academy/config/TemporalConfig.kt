package dk.academy.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import io.micrometer.observation.annotation.Observed
import io.temporal.common.converter.*
import io.temporal.worker.WorkerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

/**
 * Configuration class for Temporal data conversion and default settings.
 * Provides beans for data conversion and defines default task queue.
 */
@Configuration
class TemporalConfig {

    companion object {
        const val DEFAULT_TASK_QUEUE = "DefaultTaskQueue"
    }

    /**
     * Creates a DataConverter bean for Temporal workflow data conversion.
     * Supports multiple payload types including null, byte arrays, protobuf, and JSON.
     *
     * @param objectMapper Jackson ObjectMapper for JSON conversion
     * @return Configured DataConverter instance
     */
    @Bean
    fun dataConverter(objectMapper: ObjectMapper): DataConverter {
        return DefaultDataConverter(
            NullPayloadConverter(),
            ByteArrayPayloadConverter(),
            ProtobufJsonPayloadConverter(),
            JacksonJsonPayloadConverter(objectMapper),
        )
    }
}

/**
 * Configuration class for initializing Temporal worker factory.
 * Handles the startup of the worker factory when the application is ready.
 */
@Configuration
class TemporalInitialiser(
    private val factory: WorkerFactory,
) {

    private val logger = KotlinLogging.logger {}

    /**
     * Event listener that starts the Temporal worker factory when the application is ready.
     * This ensures that the worker factory is properly initialized after all Spring beans are created.
     *
     * @param event ApplicationReadyEvent triggered when the application is fully started
     */
    @Observed
    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady(event: ApplicationReadyEvent) {
        if (!factory.isStarted) factory.start()

        logger.info { ">> WorkerFactory :: Started!" }
    }
}
