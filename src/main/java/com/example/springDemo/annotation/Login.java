package com.example.springDemo.annotation;

import java.lang.annotation.*;

/**
 * 登录校验
 * @author
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {

}
