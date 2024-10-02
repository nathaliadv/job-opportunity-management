package com.nadantas.jobopportunitymanagement.modules.company.useCases;


import com.nadantas.jobopportunitymanagement.modules.company.dto.AuthCompanyDTO;
import com.nadantas.jobopportunitymanagement.modules.company.dto.AuthCompanyResponseDTO;
import com.nadantas.jobopportunitymanagement.modules.company.entities.Company;
import com.nadantas.jobopportunitymanagement.modules.company.repositories.CompanyRepository;
import com.nadantas.jobopportunitymanagement.providers.JWTProvider;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;


@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        Company company = this.companyRepository.findByUsername(authCompanyDTO.username()).orElseThrow(
                () -> { throw new UsernameNotFoundException("Username/password incorrect.");
        });
        boolean passwordMatches = this.passwordEncoder.matches(authCompanyDTO.password(), company.getPassword());
        if(!passwordMatches) {
            throw new AuthenticationException("Username/password incorrect.");
        }
        Instant expirationDate = Instant.now().plus(Duration.ofHours(2));
        String token = jwtProvider.generateToken(company.getId(), "company", expirationDate);
        return AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expirationDate.toEpochMilli())
                .build();
    }
}
