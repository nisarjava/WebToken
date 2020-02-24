package com.sunc.demo.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunc.demo.service.JWTUserDetailService;
import com.sunc.demo.service.utils.TokenUtil;

@Component
public class RequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUserDetailService userDetailsService;

	@Autowired
	private TokenUtil tokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authenticationToken=request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		
		if (authenticationToken != null && authenticationToken.startsWith("Bearer ")) {
			String token=authenticationToken.substring(7);
			String userName=tokenUtil.getUsernameFromToken(token);
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				
				UserDetails details=userDetailsService.loadUserByUsername(userName);
				jwtToken=tokenUtil.generateToken(details);
				if (tokenUtil.validateToken(jwtToken, details)) {
					
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							details, null, details.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
			}
		}
	 }
		filterChain.doFilter(request, response);
	}		

}
