package nl.dacodadragon.slinkydog.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SettingInfo {
    String nameInGame();
    String sectionInGame();
    String nameInFile();
}