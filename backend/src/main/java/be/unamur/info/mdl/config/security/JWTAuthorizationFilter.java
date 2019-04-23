package be.unamur.info.mdl.config.security;

import static be.unamur.info.mdl.config.security.SecurityUtils.HEADER_STRING;
import static be.unamur.info.mdl.config.security.SecurityUtils.TOKEN_PREFIX;

import com.google.common.base.Strings;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@WebFilter(filterName = "JWTAuthorizationFilter")
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);


  public JWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }


  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
    FilterChain chain) throws IOException, ServletException {

    String header = req.getHeader(HEADER_STRING);
    if (Strings.isNullOrEmpty(header) || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    try {
      String token = header.replace(TOKEN_PREFIX, "");
      UsernamePasswordAuthenticationToken authentication = this.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(req, res);
    } catch (SignatureException ex) {
      LOGGER.error("Invalid JWT signature");
    } catch (MalformedJwtException ex) {
      LOGGER.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      LOGGER.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      LOGGER.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      LOGGER.error("JWT claims string is empty.");
    }
  }


  /**
   * Uses Jwts to validate the provided token
   *
   * @param token - The provided token in headers
   */
  private UsernamePasswordAuthenticationToken getAuthentication(String token)
    throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {

    if (token != null) {
      String user = SecurityUtils.parseToken(token);

      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, Collections.EMPTY_LIST);
      }
    }

    return null;
  }

}
