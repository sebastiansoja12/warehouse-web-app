package com.warehouse.redirect.configuration;

import com.warehouse.mail.domain.service.MailService;
import com.warehouse.redirect.domain.port.primary.RedirectTokenPort;
import com.warehouse.redirect.domain.port.primary.RedirectTokenPortImpl;
import com.warehouse.redirect.domain.port.secondary.MailServicePort;
import com.warehouse.redirect.domain.port.secondary.RedirectServicePort;
import com.warehouse.redirect.domain.port.secondary.RedirectTokenRepository;
import com.warehouse.redirect.domain.service.RedirectService;
import com.warehouse.redirect.domain.service.RedirectServiceImpl;
import com.warehouse.redirect.domain.service.RedirectTokenGenerator;
import com.warehouse.redirect.domain.service.RedirectTokenGeneratorImpl;
import com.warehouse.redirect.infrastructure.adapter.primary.mapper.RedirectRequestMapper;
import com.warehouse.redirect.infrastructure.adapter.primary.mapper.RedirectRequestMapperImpl;
import com.warehouse.redirect.infrastructure.adapter.primary.mapper.RedirectResponseMapper;
import com.warehouse.redirect.infrastructure.adapter.primary.mapper.RedirectResponseMapperImpl;
import com.warehouse.redirect.infrastructure.adapter.secondary.MailAdapter;
import com.warehouse.redirect.infrastructure.adapter.secondary.RedirectParcelAdapter;
import com.warehouse.redirect.infrastructure.adapter.secondary.RedirectTokenReadRepository;
import com.warehouse.redirect.infrastructure.adapter.secondary.RedirectTokenRepositoryImpl;
import com.warehouse.redirect.infrastructure.adapter.secondary.mapper.NotificationMapper;
import com.warehouse.redirect.infrastructure.adapter.secondary.mapper.RedirectTokenMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedirectConfiguration {
    
	@Bean
	public RedirectTokenPort redirectTokenPort(RedirectService redirectService, MailServicePort mailServicePort) {
        final RedirectTokenGenerator redirectTokenGenerator = new RedirectTokenGeneratorImpl();
		return new RedirectTokenPortImpl(redirectService, redirectTokenGenerator, mailServicePort);
	}

    @Bean(name = "redirect.mailServicePort")
    public MailServicePort mailServicePort(MailService mailService) {
        final NotificationMapper mapper = Mappers.getMapper(NotificationMapper.class);
        return new MailAdapter(mailService, mapper);
    }

    @Bean
    public RedirectService redirectService(RedirectTokenRepository redirectTokenRepository) {
        return new RedirectServiceImpl(redirectTokenRepository);
    }

    @Bean
    public RedirectTokenRepository redirectTokenRepository(RedirectTokenReadRepository redirectTokenReadRepository) {
        final RedirectTokenMapper mapper = Mappers.getMapper(RedirectTokenMapper.class);
        return new RedirectTokenRepositoryImpl(redirectTokenReadRepository, mapper);
    }

    @Bean
    public RedirectServicePort redirectServicePort() {
        return new RedirectParcelAdapter();
    }

    // Request mapper
    @Bean(name = "redirect.requestMapper")
    public RedirectRequestMapper requestMapper() {
        return new RedirectRequestMapperImpl();
    }

    // Response mapper
    @Bean(name = "redirect.responseMapper")
    public RedirectResponseMapper responseMapper() {
        return new RedirectResponseMapperImpl();
    }

}
