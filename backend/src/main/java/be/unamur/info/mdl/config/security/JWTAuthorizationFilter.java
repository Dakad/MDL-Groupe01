package be.unamur.info.mdl.config.security;

import static be.unamur.info.mdl.config.security.SecurityUtils.HEADER_STRING;
import static be.unamur.info.mdl.config.security.SecurityUtils.TOKEN_PREFIX;

import com.google.common.base.Strings;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


  public JWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }


  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
    FilterChain chain) throws IOException, ServletException {

    String header = req.getHeader(HEADER_STRING);
    if (!Strings.isNullOrEmpty(header) && header.startsWith(TOKEN_PREFIX)) {
      String token = header.replace(TOKEN_PREFIX, "");
      UsernamePasswordAuthenticationToken authentication = this.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(req, res);
  }


  /**
   * Uses Jwt to validate the provided token
   *
   * @param token - The provided token in headers
   */
  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    if (token != null) {
      String user = SecurityUtils.parseToken(token);
      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
      }
    }

    return null;
  }

}
