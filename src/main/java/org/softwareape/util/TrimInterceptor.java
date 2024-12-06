package org.softwareape.util;

import java.util.logging.Logger;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Priority(0)
@Trimmed
public class TrimInterceptor {
    private static final Logger log = Logger.getLogger(TrimInterceptor.class.getName());

    @AroundInvoke
    public Object trimStrings(InvocationContext context) throws Exception {
        log.info("Trimming paramters.");
        for (Object param : context.getParameters()) {
            if (param != null) {
                for (var field : param.getClass().getDeclaredFields()) {
                    if (field.getType().equals(String.class)) {
                        field.setAccessible(true);
                        String value = (String) field.get(param);
                        if (value != null) {
                            field.set(param, value.trim());
                        }
                    }
                }
            }
        }
        return context.proceed();
    }
}