package nl.dacodadragon.slinkydog.configuration;

public final class ParseContext {

	private Class<?> expectedType;
	private String section;
	private String setting;
	private String value;

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
