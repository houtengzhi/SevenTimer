package com.latitude.seventimer.injector.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by yechy on 2017/3/29.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerService {
}
