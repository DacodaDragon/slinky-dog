package nl.dacodadragon.slinkydog.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
public @interface Description {
    String value();
}
