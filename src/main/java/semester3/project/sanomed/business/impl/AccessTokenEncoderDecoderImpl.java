package semester3.project.sanomed.business.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import semester3.project.sanomed.Generated;
import semester3.project.sanomed.business.AccessTokenDecoder;
import semester3.project.sanomed.business.AccessTokenEncoder;
import semester3.project.sanomed.business.exceptions.InvalidAccessTokenException;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Generated
public class AccessTokenEncoderDecoderImpl  implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;
    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(accessToken.getRoles())) {
            claimsMap.put("roles", accessToken.getRoles());
        }
        if (accessToken.getEmployeeId() != null) {
            claimsMap.put("employeeId", accessToken.getEmployeeId());
        }
        if (accessToken.getPatientId() != null) {
            claimsMap.put("patientId", accessToken.getPatientId());
        }
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(50, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(accessTokenEncoded);
            Claims claims = (Claims) jwt.getBody();

            List<String> roles = claims.get("roles", List.class);

            if(roles.contains(RoleEnum.PATIENT.name())){
                return AccessToken.builder()
                        .subject(claims.getSubject())
                        .roles(roles)
                        .patientId(claims.get("patientId", Long.class))
                        .build();
            }

            return AccessToken.builder()
                    .subject(claims.getSubject())
                    .roles(roles)
                    .employeeId(claims.get("employeeId", Long.class))
                    .build();

        } catch (JwtException e) {
                throw new InvalidAccessTokenException(e.getMessage());
        }
    }
}
