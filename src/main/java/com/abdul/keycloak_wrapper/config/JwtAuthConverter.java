package com.abdul.keycloak_wrapper.config;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private static final String PREFERRED_USERNAME = "preferred_username";
    private static final String CLIENT_RESOURCE_ID = "abdul-rest-api";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwtToken) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwtToken).stream(),
                extractResourceRoles(jwtToken).stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwtToken,
                authorities,
                getPrincipalClaimName(jwtToken)
        );
    }

    private String getPrincipalClaimName(Jwt jwtToken) {
        String claimName = JwtClaimNames.SUB;
        if (StringUtils.hasText(PREFERRED_USERNAME)) {
            claimName = PREFERRED_USERNAME;
        }
        return jwtToken.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwtToken) {
        if (Objects.isNull(jwtToken.getClaim("resource_access"))) {
            return Set.of();
        }
        Map<String, Object> resourceAccessClaim = jwtToken.getClaim("resource_access");
        if (Objects.isNull(resourceAccessClaim.get(CLIENT_RESOURCE_ID))) {
            return Set.of();
        }
        Map<String, Object> clientResource = (Map<String, Object>) resourceAccessClaim.get(CLIENT_RESOURCE_ID);
        if (Objects.isNull(clientResource)) {
            return Set.of();
        }
        Collection<String> clientResourceRoles = (Collection<String>) clientResource.get("roles");
        return clientResourceRoles.stream().map(
                role -> new SimpleGrantedAuthority("ROLE_" + role)
        ).collect(Collectors.toSet());
    }
}
