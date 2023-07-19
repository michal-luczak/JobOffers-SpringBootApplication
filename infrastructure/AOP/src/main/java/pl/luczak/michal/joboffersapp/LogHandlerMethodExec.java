package pl.luczak.michal.joboffersapp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogHandlerMethodExec {
    //logger name
    String value();

    Class<?> handlerClazz();

    Class<? extends Throwable> caughtException();
}
