package dk.academy.config

import io.mockk.*
import io.temporal.worker.WorkerFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.context.event.ApplicationReadyEvent

@ExtendWith(MockitoExtension::class)
class TemporalInitialiserTest {

    private val workerFactory = mockk<WorkerFactory>()
    private val event = mockk<ApplicationReadyEvent>()

    @Test
    fun `Given ApplicationReadyEvent - When workerFactory not started - Should start`() {
        // Given
        every { workerFactory.isStarted } returns false
        every { workerFactory.start() } just Runs

        // When
        val initialiser = TemporalInitialiser(workerFactory)
        initialiser.onApplicationReady(event)

        // Then
        verify { workerFactory.start() }
    }

    @Test
    fun `Given ApplicationReadyEvent - When workerFactory started - Should not start`() {
        // Given
        every { workerFactory.isStarted } returns true

        // When
        val initialiser = TemporalInitialiser(workerFactory)
        initialiser.onApplicationReady(event)

        // Then
        verify(exactly = 0) { workerFactory.start() }
    }
}
