 package com.charter.customer.aop.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CharterSecured {
	Role[] roles() default {};

	boolean enable() default true;

}