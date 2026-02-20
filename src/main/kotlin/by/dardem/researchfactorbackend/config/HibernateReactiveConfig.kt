package by.dardem.researchfactorbackend.config

import jakarta.persistence.Entity
import liquibase.integration.spring.SpringLiquibase
import org.hibernate.boot.MetadataSources
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.annotation.Configuration
import org.springframework.core.type.filter.AnnotationTypeFilter

@Configuration
class HibernateReactiveConfig(
    private val hibernateProperties: HibernateProperties
) {

    companion object {
        private const val ENTITY_PACKAGE = "by.dardem.researchfactorbackend.domain"
    }

    @Bean
    fun sessionFactory(): Mutiny.SessionFactory {
        val settings = mutableMapOf<String, Any>(
            "hibernate.connection.url" to hibernateProperties.url,
            "hibernate.connection.pool_size" to hibernateProperties.poolSize,
            "hibernate.dialect" to hibernateProperties.dialect,
            "hibernate.show_sql" to hibernateProperties.showSql,
            "hibernate.format_sql" to hibernateProperties.formatSql,
            "hibernate.highlight_sql" to hibernateProperties.highlightSql,
            "jakarta.persistence.schema-generation.database.action" to hibernateProperties.schemaAction
        )

        hibernateProperties.username?.let { settings["hibernate.connection.username"] = it }
        hibernateProperties.password?.let { settings["hibernate.connection.password"] = it }

        val registry = ReactiveServiceRegistryBuilder()
            .applySettings(settings)
            .build()

        val metadataSources = MetadataSources(registry)

        // Сканируем пакет на @Entity
        val scanner = ClassPathScanningCandidateComponentProvider(false)
        scanner.addIncludeFilter(AnnotationTypeFilter(Entity::class.java))
        val entityClasses = scanner.findCandidateComponents(ENTITY_PACKAGE)
            .mapNotNull { it.beanClassName?.let { className -> Class.forName(className)} }

        entityClasses.forEach { metadataSources.addAnnotatedClass(it) }

        return metadataSources.buildMetadata()
            .buildSessionFactory()
            .unwrap(Mutiny.SessionFactory::class.java)
    }

    @Bean
    fun liquibase(): SpringLiquibase {
        val ds = PGSimpleDataSource().apply {
            setURL("jdbc:" + hibernateProperties.url)
            hibernateProperties.username?.let { user = it }
            hibernateProperties.password?.let { password = it }
        }

        return SpringLiquibase().apply {
            dataSource = ds
            changeLog = "classpath:migration/db.changelog-master.xml"
        }
    }
    @ConfigurationProperties(prefix = "hibernate.reactive")
    class HibernateProperties {
        lateinit var url: String
        var username: String? = null
        var password: String? = null
        var poolSize: Int = 10
        lateinit var dialect: String
        var showSql: Boolean = false
        var formatSql: Boolean = false
        var highlightSql: Boolean = false
        lateinit var schemaAction: String
    }
}

