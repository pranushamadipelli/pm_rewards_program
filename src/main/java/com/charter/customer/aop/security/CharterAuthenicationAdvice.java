
package com.charter.customer.aop.security;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component("charterAuthenicationAdvice")
public class CharterAuthenicationAdvice {

	@Value("${charter.security.enable}")
	private String securityEnable;

	

	@Before("@annotation(CharterSecured)")
	public void methodBefore(JoinPoint joinPoint) throws Throwable {
		boolean secureEnable = Boolean.valueOf(System.getProperty("charter.security.enable", securityEnable));
		if (secureEnable) {
			Method method = ((Method) ((MethodSignature) joinPoint.getSignature()).getMethod());
			CharterSecured secured = method.getAnnotation(CharterSecured.class);
			if (secured == null) {
				secured = joinPoint.getTarget().getClass().getAnnotation(CharterSecured.class);
			}
			if (secured != null && secured.enable()) {
				Role[] allowedRoles = secured.roles();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null || !authentication.isAuthenticated()) {
					throw new SecurityException("User is not authenticated");
				}
				if (!hasRequiredRole(authentication, allowedRoles)) {
					throw new SecurityException("hasRequiredRole");
				}
			}
		}
	}

	private boolean hasRequiredRole(Authentication authentication, Role[] allowedRoles) {
		for (Role role : allowedRoles) {
			if (authentication.getAuthorities().stream()
					.anyMatch(authority -> authority.getAuthority().equals(role.name()))) {
				return true;
			}
		}
		return false;
	}

}
