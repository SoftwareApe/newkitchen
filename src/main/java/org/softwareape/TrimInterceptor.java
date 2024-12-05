package org.softwareape;

import java.util.logging.Logger;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Trimmed
public class TrimInterceptor {
    private static final Logger log = Logger.getLogger(MemberResource.class.getName());

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