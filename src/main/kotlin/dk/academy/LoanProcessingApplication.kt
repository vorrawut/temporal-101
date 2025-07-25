package dk.academy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class LoanProcessingApplication

fun main(args: Array<String>) {
    runApplication<LoanProcessingApplication>(*args)
}
