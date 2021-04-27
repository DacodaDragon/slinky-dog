package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import nl.dacodadragon.slinkydog.SlinkydogDebug;
import nl.dacodadragon.slinkydog.configuration.exceptions.MissingSectionException;
import nl.dacodadragon.slinkydog.configuration.exceptions.MissingSettingException;
import nl.dacodadragon.slinkydog.utility.EnumUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SettingsAccessor {
	private final JavaPlugin plugin;
	private final SettingsContainer settings;
	private final Class<?> type;
	private final FileConfiguration configFile;

	public SettingsAccessor(JavaPlugin plugin, Class<?> configType, FileConfiguration configFile) {
		this.plugin = plugin;
		this.type = configType;
		this.configFile = configFile;
		settings = new SettingsContainer(plugin, configType);

		Field[] fields = configType.getDeclaredFields();
		for (Field field : fields)
			settings.addField(field);

		settings.writeAllDefaultValues(configFile);
		settings.loadValuesFromSettings(configFile);
	}

	public String getSettingValue(String section, String setting) {

		if (!settings.sectionExists(section))
			throw new MissingSectionException(section);

		if (!settings.settingExists(section, setting))
			throw new MissingSettingException(section, setting);

		return "" + settings.getSetting(section, setting).GetValue();
	}

	public List<String> getSectionNames() {
		return settings.getAllSectionNames();
	}

	public List<String> getSettingNames(String section) {
		if (!settings.sectionExists(section))
			return ArgumentConsts.noArgs;
		return settings.getSection(section).getSettingNames();
	}

	public List<String> getSettingArguments(String section, String setting) {
		if (settings.settingExists(section, setting)) {
			Class<?> type = settings.getSection(section).getSetting(setting).field.getType();
			if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				return ArgumentConsts.booleanArgs;
			}

			if (type.isEnum()) {
				return Arrays.asList(EnumUtil.getNames(type));
			}
		}
		return ArgumentConsts.noArgs;
	}

	public void setSetting(String section, String setting, String value) {
		if (!settings.sectionExists(section))
			throw new MissingSectionException(section);

		if (!settings.settingExists(section, setting))
			throw new MissingSettingException(section, setting);

		SectionMapping sectionObject = settings.getSection(section);
		Setting field = sectionObject.getSetting(setting);
		ParseContext context = new ParseContext(section, setting, value, field.field.getType());
		Object rawValue = ConfigurationValueParser.getValue(context);
		field.setValue(rawValue);

		SlinkydogDebug.softNullAssert(sectionObject, "sectionObject");
		SlinkydogDebug.softNullAssert(field, "field");
		SlinkydogDebug.softNullAssert(configFile, "configFile");
		SlinkydogDebug.softNullAssert(rawValue, "rawValue");

		configFile.set(type.getSimpleName() + "." + sectionObject.name + "." + field.name, rawValue);
		plugin.saveConfig();
	}
}