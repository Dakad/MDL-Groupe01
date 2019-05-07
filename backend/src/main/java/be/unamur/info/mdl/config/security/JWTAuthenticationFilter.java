package be.unamur.info.mdl.config.security;

import static be.unamur.info.mdl.config.security.SecurityUtils.HEADER_STRING;
import static be.unamur.info.mdl.config.security.SecurityUtils.TOKEN_PREFIX;

import be.unamur.info.mdl.dto.CredentialDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;


  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {

    try {
      CredentialDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredentialDTO.class);

      return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          creds.getUsername(),
          creds.getPassword(),
          new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
    FilterChain chain, Authentication auth)  {

    String username = ((CredentialDTO) auth.getPrincipal()).getUsername();
    String token = SecurityUtils.generateToken(username);

    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
  }

}
