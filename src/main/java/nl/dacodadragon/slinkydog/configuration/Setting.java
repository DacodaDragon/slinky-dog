package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Field;

final class Setting {
	String name;
	Field field;

	public void setValue(Object value) {
		setValue(value, true);
	}

	public void setValue(Object value, boolean writeToFile) {
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
}
