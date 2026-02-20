package by.dardem.researchfactorbackend.config

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutineScopesConfig {

    /**
     * Scope for IO-bound operations (service API calls, database queries and etc.)
     */
    @Bean
    fun ioCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Scope for CPU-bound operations (heavy computation inside our service)
     */
    @Bean
    fun defaultCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
