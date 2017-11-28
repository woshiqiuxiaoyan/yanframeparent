package annotation;

import java.lang.annotation.*;

/**
 * 配合HandlerMethodArgumentResolver 这个东西来玩
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
