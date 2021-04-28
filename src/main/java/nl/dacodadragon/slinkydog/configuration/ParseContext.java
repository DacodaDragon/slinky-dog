package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Type;

import nl.dacodadragon.slinkydog.utility.ReflectionUtil;

public final class ParseContext {

	private final Object originalValue;
	private final Class<?> expectedType;
	private final Type expectedGenericType;
	private final String section;
	private final String setting;
	private final String value;

	public ParseContext(Setting setting, String value) {

		this(setting.getSectionInGame(),
			setting.getNameInGame(),
			value,
			setting.getFieldType(),
			setting.getGenericFieldType(),
			setting.getValue());
	}

	public ParseContext(String section, String setting, String value, Class<?> expectedType, Type expectedGenericType, Object originalValue) {
		this.section = section;
		this.setting = setting;
		this.value = value;
		this.expectedType = expectedType;
		this.expectedGenericType = expectedGenericType;
		this.originalValue = originalValue;
	}

	public ParseContext sameContextWithType(Class<?> expectedType){
		return new ParseContext(section, setting, value, expectedType, expectedGenericType, originalValue);
	}

	public Class<?> collectionElementType(){
		return ReflectionUtil.getCollectionElementType(expectedGenericType);
	}

	public boolean isCollection() {
		return ReflectionUtil.isCollection(expectedType);
	}

	public Type getExpectedGenericType(){
		return expectedGenericType;
	}

	public Class<?> getExpectedType() {
		return expectedType;
	}

	public String getSection() {
		return section;
	}

	public String getSetting() {
		return setting;
	}

	public String getValue() {
		return value;
	}

	public Object getSettingValue(){
		return originalValue;
	}
}
