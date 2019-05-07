package be.unamur.info.mdl.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public abstract class SecurityUtils {

  @Value("${app.auth.secret}")
  public static final String SECRET = "QW2RoG7!Fq_FO$Anf#C2vg4kTK&kgP3XS#TCe";

  @Value("${app.auth.tokenExpirationInMs}")
  public static final long EXPIRATION_TIME = (long) 864_000_00 * 100; // 100 days

  @Value("${app.auth.prefix}")
  public static final String TOKEN_PREFIX = "Bearer ";

  @Value("${app.auth.header}")
  public static final String HEADER_STRING = "Authorization";

  @Value("${app.auth.secureEndpoints}")
  public static final String[] SECURED_ENDPOINTS = new String[]{
     // "/api/team"
  };

  private SecurityUtils(){
  }

  /**
   *
   * @param subject
   * @return
   */
  public static String generateToken(String subject) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

    return Jwts.builder()
      .setSubject(subject)
      .setIssuedAt(now)
      .setExpiration(expiryDate)
      .signWith(SignatureAlgorithm.HS512, SECRET)
      .compact();
  }


  /**
   *
   * @param token
   * @return
   * @throws ExpiredJwtException
   * @throws UnsupportedJwtException
   * @throws MalformedJwtException
   * @throws SignatureException
   * @throws IllegalArgumentException
   */
  public static String parseToken(String token)
    throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException,
    IllegalArgumentException {

    if (token == null) {
      throw new IllegalArgumentException("Empty or null token");
    }

    return Jwts.parser()
      .setSigningKey(SECRET)
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }
}
