package com.nadantas.jobopportunitymanagement.modules.candidate.useCases;

import com.nadantas.jobopportunitymanagement.modules.candidate.dto.AuthCandidateRequestDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.dto.AuthCandidateResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.candidate.entities.Candidate;
import com.nadantas.jobopportunitymanagement.modules.candidate.repositories.CandidateRepository;
import com.nadantas.jobopportunitymanagement.providers.JWTProvider;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        Candidate candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> { throw new UsernameNotFoundException("Username/password incorrect.");});
        boolean passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());
        if(!passwordMatches) {
            throw new AuthenticationException("Username/password incorrect.");
        }
        Instant expirationDate = Instant.now().plus(Duration.ofHours(4));
        String authToken = jwtProvider.generateToken(candidate.getId(), "candidate", expirationDate);
        return AuthCandidateResponseDTO.builder()
                .access_token(authToken)
                .expires_in(expirationDate.toEpochMilli())
                .build();
    }
}
