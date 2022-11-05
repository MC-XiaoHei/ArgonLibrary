package xor7studio.actuator;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Inherited
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
@Retention(RUNTIME)
public @interface ActuatorIgnore {
}
