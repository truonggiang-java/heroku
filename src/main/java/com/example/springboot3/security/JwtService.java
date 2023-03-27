package com.example.springboot3.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "703273357638792F423F4428472B4B6250655368566D597133743677397A2443";

	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claims) {
		final Claims claimData=extractAllClaims(token);
		return claims.apply(claimData);
	}
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> claims=new HashMap<>();
		return createToken(userDetails,claims);
	}

	private String createToken(UserDetails userDetails, Map<String, Object> claims) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 24*60*1000))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}
	
	public boolean validateToken(String token,UserDetails userDetails) {
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpried(token);
	}

	private boolean isTokenExpried(String token) {
		 
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
