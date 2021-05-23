
  package org.sid.filtres;
  
  import java.io.IOException; import java.util.Date; import java.util.HashMap;
  import java.util.Map; import java.util.stream.Collectors;
  
  import javax.servlet.FilterChain; import javax.servlet.ServletException;
  import javax.servlet.http.HttpServletRequest; import
  javax.servlet.http.HttpServletResponse;
  
  import org.sid.entities.AppUser;
import org.sid.repository.AppUserRepository;
import
  org.springframework.security.authentication.AuthenticationManager; import
  org.springframework.security.authentication.
  UsernamePasswordAuthenticationToken; import
  org.springframework.security.core.Authentication; import
  org.springframework.security.core.AuthenticationException; import
  org.springframework.security.core.userdetails.User; import
  org.springframework.security.web.authentication.
  UsernamePasswordAuthenticationFilter;
  
  import com.auth0.jwt.JWT; import com.auth0.jwt.algorithms.Algorithm; import
  com.fasterxml.jackson.databind.json.JsonMapper;
  
  public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  
  private AuthenticationManager authenticationManager;
  private AppUserRepository appUserRepository;
  
  public JWTAuthenticationFilter(AuthenticationManager authenticationManager
		  ,AppUserRepository appUserRepository) {
  
	  this.authenticationManager = authenticationManager;
	  this.appUserRepository = appUserRepository;
	  
	  }
  
  @Override public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

	  AppUser appUser=new AppUser();
	  appUser.setEmail(request.getParameter("email")); 
	  appUser.setPassword(request.getParameter("password")); 
	  return   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(),appUser.getPassword())); 
	  }
  
  @Override protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
	  
	  User authenticatedUser= (User)authResult.getPrincipal();
	  AppUser appuser = appUserRepository.findByEmail(request.getParameter("email"));
	  Algorithm algorithm=Algorithm.HMAC256("myHMACPrivateKey"); 
	  String jwtAccessToken= JWT
			  .create()
			  .withSubject(appuser.get_id())
			  .withExpiresAt(new Date(System.currentTimeMillis()+50*60*1000)) 
			  .withIssuer(request.getRequestURL().toString())
			  .withClaim("id",appuser.get_id())
			  .withClaim("email",appuser.getEmail())
			  .withClaim("username", appuser.getUsername())
			  .sign(algorithm); 
	  String jwtRefreshToken= JWT 
			  .create() 
			  .withSubject(authenticatedUser.getUsername()) 
			  .withExpiresAt(new Date(System.currentTimeMillis()+10*24*3600*1000)) 
			  .withIssuer(request.getRequestURL().toString()) .sign(algorithm);
  
	  Map<String,String> accessToken=new HashMap<>();  
	  accessToken.put("Access_Token",jwtAccessToken); 
	  accessToken.put("Refresh_Token",jwtRefreshToken);  
	  response.setContentType("application/json"); 
	  new  JsonMapper().writeValue(response.getOutputStream(),accessToken);
	  } 
  }
 