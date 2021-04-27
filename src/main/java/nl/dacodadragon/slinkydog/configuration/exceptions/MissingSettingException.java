package nl.dacodadragon.slinkydog.configuration.exceptions;

public class MissingSettingException extends ConfigurationErrorException {
	private static final long serialVersionUID = 7788676904867579830L;

	private static final String ERROR_MISSING_SETTING = "Setting with name \"%s\" does not exist in section \"%s\".";

	private String section;
	private String setting;

	public MissingSettingException(String section, String setting) {
		super(ERROR_MISSING_SETTING, setting, section);
		this.section = section;
		this.setting = setting;
	}

	public String getSection() {
		return section;
	}

	public String getSetting() {
		return setting;
	}
}
