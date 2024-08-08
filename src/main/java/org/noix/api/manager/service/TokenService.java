package org.noix.api.manager.service;

import lombok.RequiredArgsConstructor;
import org.noix.api.manager.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;




}
