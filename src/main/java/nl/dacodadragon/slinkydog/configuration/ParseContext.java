package nl.dacodadragon.slinkydog.configuration;

public final class ParseContext {

	private final Class<?> expectedType;
	private final String section;
	private final String setting;
	private final String value;

	public ParseContext(Setting setting, String value) {
		this.expectedType = setting.getFieldType();
		this.section = setting.getSectionInGame();
		this.setting = setting.getNameInGame();
		this.value = value;
	}

	public ParseContext(String section, String setting, String value, Class<?> expectedType) {
		this.section = section;
		this.setting = setting;
		this.value = value;
		this.expectedType = expectedType;
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
}
