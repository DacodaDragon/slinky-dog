package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Field;
import nl.dacodadragon.slinkydog.configuration.annotations.SettingInfo;

final class Setting {
	private Field field;
	private Class<?> fieldType;
	private String nameInGame;
	private String sectionInGame;
	private String nameInFile;

	public Setting(Field field) {
		this.field = field;
		this.fieldType = field.getType();

		SettingInfo info = getSettingInfo(field);
		this.nameInGame = info.nameInGame();
		this.sectionInGame = info.sectionInGame();
		this.nameInFile = info.nameInFile();
	}

	private SettingInfo getSettingInfo(Field field) {
		SettingInfo info = field.getAnnotation(SettingInfo.class);
		if (info == null){
			final String message = "Field %s is missing a SettingInfo Annotation.";
			throw new NullPointerException(
				String.format(message, field.getName()));
		} 
		return info;
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
}
