package com.warehouse.auth;


import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.warehouse.auth.configuration.AuthTestConfiguration;
import com.warehouse.auth.infrastructure.adapter.secondary.RefreshTokenReadRepository;
import com.warehouse.auth.infrastructure.adapter.secondary.entity.RefreshTokenEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = AuthTestConfiguration.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DatabaseSetup("/dataset/refresh_token.xml")
public class RefreshTokenReadRepositoryTest {

    @Autowired
    private RefreshTokenReadRepository repository;

    @Test
    void shouldFindByToken() {
        // given
        final String token = "12345";
        // when
        final Optional<RefreshTokenEntity> refreshToken = repository.findByToken(token);
        // then
        assertTrue(refreshToken.isPresent());
    }

    @Test
    void shouldNotFindByToken() {
        // given
        final String token = "0";
        // when
        final Optional<RefreshTokenEntity> refreshToken = repository.findByToken(token);
        // then
        assertTrue(refreshToken.isEmpty());
    }
}
