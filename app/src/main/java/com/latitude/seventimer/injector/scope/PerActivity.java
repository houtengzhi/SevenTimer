package com.latitude.seventimer.injector.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by yechy on 2017/3/29.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
