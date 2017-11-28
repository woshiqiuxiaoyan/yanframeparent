package annotation;

import java.lang.annotation.*;

/**
 * 配合HandlerMethodArgumentResolver
 * 去空白格 失败
 */
@Deprecated
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrimSpace {
}
