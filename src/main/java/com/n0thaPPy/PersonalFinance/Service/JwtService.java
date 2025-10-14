package com.n0thaPPy.PersonalFinance.Service;

import com.n0thaPPy.PersonalFinance.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String secretkey=getSecretKey();
    public String generateToken(String username, Set<Roles> roles) {

        Map<String, Object> claims= new HashMap<>();
        List<String>rolesList=roles.stream().map(Enum::name).toList();

        claims.put("roles",rolesList);
        claims.put("iat",new Date(System.currentTimeMillis()));
        claims.put("exp",new Date(System.currentTimeMillis()*1000*60*60));
        claims.put("sub",username);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public String getSecretKey()
    {
        SecretKey secretKey= Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public Key getKey()
    {
        byte[]keyByte= Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyByte);
    }
    public Claims extractKey(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
    public String extractUsername(String token)
    {
        return extractKey(token).getSubject();
    }
    public boolean isExpired(String token)
    {
        return extractKey(token).getExpiration().before(new Date());
    }
}
