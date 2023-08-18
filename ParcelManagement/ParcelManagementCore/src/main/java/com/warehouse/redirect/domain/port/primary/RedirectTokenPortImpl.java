package com.warehouse.redirect.domain.port.primary;

import com.warehouse.redirect.domain.model.RedirectRequest;
import com.warehouse.redirect.domain.model.RedirectResponse;
import com.warehouse.redirect.domain.port.secondary.MailServicePort;
import com.warehouse.redirect.domain.service.RedirectService;
import com.warehouse.redirect.domain.service.RedirectTokenGenerator;
import com.warehouse.redirect.domain.vo.RedirectToken;
import com.warehouse.redirect.domain.vo.Token;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class RedirectTokenPortImpl implements RedirectTokenPort {

    private final RedirectService redirectService;

	private final RedirectTokenGenerator redirectTokenGenerator;

	private final MailServicePort mailServicePort;


    @Override
    public RedirectResponse sendRedirectInformation(RedirectRequest request) {
        handleRequest(request);
		final RedirectToken redirectToken = buildRedirectTokenFromRequest(request);
		logRedirectToken(redirectToken);
		final Token token = redirectService.saveRedirectToken(redirectToken);
		mailServicePort.sendRedirectInformation(redirectToken);
        return RedirectResponse.builder()
				.parcelId(request.getParcelId())
				.token(token)
				.build();
    }

	private void logRedirectToken(RedirectToken redirectToken) {
		log.info("Request for redirecting parcel {0} has been recorded", redirectToken.getParcelId());
	}

	private RedirectToken buildRedirectTokenFromRequest(RedirectRequest request) {
		final String token = redirectTokenGenerator.generateToken(request.getParcelId(), request.getEmail());
		return new RedirectToken(token, request.getParcelId(), request.getEmail());
	}

	private void handleRequest(RedirectRequest request) { 
		if (request == null) {
			throw new RuntimeException("");
		} else if (request.getEmail().isEmpty()) {
			throw new RuntimeException("");
		} else if (request.getParcelId() == null) {
			throw new RuntimeException("");
		}
	}
}
