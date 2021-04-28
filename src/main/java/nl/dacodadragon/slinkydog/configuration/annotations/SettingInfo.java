package nl.dacodadragon.slinkydog.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
public @interface SettingInfo {
    String nameInGame();
    String sectionInGame();
    String nameInFile();
}