package by.dardem.researchfactorbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication(exclude = [RedisAutoConfiguration::class])
class ResearchFactorBackendApplication

fun main(args: Array<String>) {
    runApplication<ResearchFactorBackendApplication>(*args)
}
