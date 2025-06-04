package dk.academy.config

import io.micrometer.observation.ObservationRegistry
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ObservabilityConfigTest {

    @MockK
    private lateinit var observationRegistry: ObservationRegistry

    @Test
    internal fun `Given observationRegistry - When Bean is created - Should create observedAspect Bean`() {
        // Given
        val config = ObservabilityConfig()

        // When
        val aspect = config.observedAspect(observationRegistry)

        // Then
        assertNotNull(aspect)
    }
}
