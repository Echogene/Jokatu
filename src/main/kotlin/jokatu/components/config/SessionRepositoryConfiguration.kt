package jokatu.components.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.MapSession
import org.springframework.session.MapSessionRepository
import org.springframework.session.SessionRepository
import java.util.concurrent.ConcurrentHashMap

@Configuration
class SessionRepositoryConfiguration {
	@Bean
	fun sessionRepository(): SessionRepository<MapSession> {
		return MapSessionRepository(ConcurrentHashMap())
	}
}
