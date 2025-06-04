package dk.academy.config

import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.annotation.Observed
import io.micrometer.observation.aop.ObservedAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for application observability features.
 *
 * This class sets up the necessary beans for enabling observability in the application,
 * including support for the @Observed annotation which allows for automatic
 * instrumentation of methods for metrics and tracing.
 */
@Configuration
class ObservabilityConfig {

    /**
     * Creates an [ObservedAspect] bean that enables the @[Observed] annotation.
     *
     * This aspect intercepts methods annotated with @[Observed] and creates
     * observations that can be used for metrics and distributed tracing.
     *
     * @param registry The [ObservationRegistry] to register observations with
     * @return An [ObservedAspect] instance
     */
    @Bean
    fun observedAspect(registry: ObservationRegistry): ObservedAspect {
        return ObservedAspect(registry)
    }
}
