package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Field;

import nl.dacodadragon.slinkydog.configuration.annotations.Description;
import nl.dacodadragon.slinkydog.configuration.annotations.SettingInfo;

final class Setting {
	private Field field;
	private Class<?> fieldType;
	private String nameInGame;
	private String sectionInGame;
	private String nameInFile;

	private String description;

	public Setting(Field field) {
		this.field = field;
		this.fieldType = field.getType();

		SettingInfo info = getSettingInfoForField(field);
		this.nameInGame = info.nameInGame();
		this.sectionInGame = info.sectionInGame();
		this.nameInFile = info.nameInFile();

		this.description = getDescriptionForField(field);
	}

	private SettingInfo getSettingInfoForField(Field field) {
		if (!field.isAnnotationPresent(SettingInfo.class)){
			final String message = "Field %s is missing a SettingInfo Annotation.";
			throw new NullPointerException(
				String.format(message, field.getName()));
		} 
		return field.getAnnotation(SettingInfo.class);
	}

	private String getDescriptionForField(Field field){
		if (field.isAnnotationPresent(Description.class))
			return field.getAnnotation(Description.class).value();
		return "";
	}

	public void setValue(Object value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Object GetValue() {
		try {
			return field.get(null);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Class<?> getFieldType(){
		return fieldType;
	}

	public String getNameInFile() {
		return nameInFile;
	}

	public String getSectionInGame() {
		return sectionInGame;
	}

	public String getNameInGame() {
		return nameInGame;
	}

	public String getDescription(){
		return description;
	}
}
