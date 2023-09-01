package com.warehouse.redirect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.warehouse.redirect.infrastructure.adapter.secondary.entity.RedirectTokenEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.warehouse.redirect.domain.vo.RedirectToken;
import com.warehouse.redirect.domain.vo.Token;
import com.warehouse.redirect.infrastructure.adapter.secondary.RedirectTokenReadRepository;
import com.warehouse.redirect.infrastructure.adapter.secondary.RedirectTokenRepositoryImpl;
import com.warehouse.redirect.infrastructure.adapter.secondary.mapper.RedirectTokenMapper;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
class RedirectTokenRepositoryImplTest {


    @Mock
    private RedirectTokenReadRepository redirectTokenReadRepository;

    @Mock
    private RedirectTokenMapper mapper;

    private RedirectTokenRepositoryImpl redirectTokenRepository;


    @BeforeEach
    void setup() {
        redirectTokenRepository = new RedirectTokenRepositoryImpl(redirectTokenReadRepository, mapper);
    }

    @Test
    void shouldSaveToken() {
        // given
        final RedirectToken redirectToken = new RedirectToken("12345", 1L, "test@test.pl");

        final RedirectTokenEntity entity = new RedirectTokenEntity();
        entity.setExpiryDate(Instant.now().plusSeconds(86400L));
        entity.setCreatedDate(Instant.now());
        entity.setToken("12345");
        entity.setEmail("test@test.pl");
        entity.setParcelId(1L);

        doReturn(entity)
                .when(mapper)
                .map(redirectToken);
        // when
        final Token token = redirectTokenRepository.save(redirectToken);
        // then
        assertEquals(expectedToBe("12345"), token.getValue());
        verify(redirectTokenReadRepository, times(1)).save(entity);
    }

    private <T> T expectedToBe(T value) {
        return value;
    }
}
