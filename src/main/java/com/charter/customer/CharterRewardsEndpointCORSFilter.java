package com.charter.customer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CharterRewardsEndpointCORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletResponse response = (HttpServletResponse) res;
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Allow-Headers",
					"*");
			chain.doFilter(req, res);
		} catch (Exception e) {

		}
	}
}