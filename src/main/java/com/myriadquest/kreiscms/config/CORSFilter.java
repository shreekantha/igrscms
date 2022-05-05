package com.myriadquest.kreiscms.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.myriadquest.kreiscms.security.jwt.TokenProvider;

@Configuration
public class CORSFilter extends CorsFilter {

	@Autowired
	private TokenProvider tokenProvider;

	public CORSFilter(CorsConfigurationSource configSource) {
		super(configSource);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "86400"); // 24 Hours
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, x-auth-token");

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);

		} else {
			String token = request.getHeader("Authorization");
			if (token != null && !token.isEmpty()) {
				String tokenId = token.split(" ")[1];
				//SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(tokenId));
				String tenantId = tokenProvider.getTenant(tokenId);
				TenantContext.setCurrentTenant(tenantId);
			}

		}
		super.doFilterInternal(request, response, filterChain);
	}

}
