package nl.dacodadragon.slinkydog.configuration;

import java.util.Arrays;
import java.util.List;


import nl.dacodadragon.slinkydog.configuration.exceptions.MissingSectionException;
import nl.dacodadragon.slinkydog.configuration.exceptions.MissingSettingException;
import nl.dacodadragon.slinkydog.utility.EnumUtil;
import nl.dacodadragon.slinkydog.utility.ReflectionUtil;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SettingsAccessor {
	private final JavaPlugin plugin;
	private final SettingsContainer settings;
	private final FileConfiguration configFile;

	public SettingsAccessor(JavaPlugin plugin, Class<?> configType, FileConfiguration configFile) {
		this.plugin = plugin;
		this.configFile = configFile;
		settings = new SettingsContainer(plugin, configType);
		settings.writeAllDefaultValues(configFile);
		settings.loadValuesFromSettings(configFile);
	}

	public String getSettingValue(String section, String setting) {
		return "" + getSetting(section, setting).getValue();
	}

	public List<String> getSectionNames() {
		return settings.getAllSectionNames();
	}

	public List<String> getSettingNames(String section) {
		if (!settings.sectionExists(section))
			return ArgumentConsts.noArgs;
		return settings.getSection(section).getSettingNames();
	}

	public List<String> getSettingArguments(String[] args) {
		if (args.length < 1)
			return ArgumentConsts.noArgs;

		if (!settings.settingExists(args[0], args[1]))
			return ArgumentConsts.noArgs;
		Setting setting = getSetting(args[0], args[1]);
		Class<?> type = setting.getFieldType();

		if (args.length == 3)
			return getArgumentsForType(type);

		if (args.length == 4 && setting.isCollection()) {
			return getArgumentsForType(setting.getElementType());
		}

		return ArgumentConsts.noArgs;
	}

	public List<String> getArgumentsForType(Class<?> type) {
		if (type.equals(boolean.class) || type.equals(Boolean.class)) {
			return ArgumentConsts.booleanArgs;
		}

		if (ReflectionUtil.isCollection(type)) {
			return ArgumentConsts.arrayArgs;
		}
		
		if (type.isEnum()) {
			return Arrays.asList(EnumUtil.getNames(type));
		}

		return ArgumentConsts.noArgs;
	}

	public String getSettingDescription(String sectionName, String settingName) {
		return getSetting(sectionName, settingName).getDescription();
	}

	public void setSetting(String[] args) {
		Setting setting = getSetting(args[0], args[1]);
		
		if (setting.isCollection()) {
			performCollectionAction(setting, args);
			return;
		}
		setPrimitiveValue(setting, args);
	}

	private void performCollectionAction(Setting setting, String[] args) {
		switch (args[2].toLowerCase()) {
			case "add": ; addToCollection(setting, args); break;
			case "remove": removeFromCollection(setting, args); break;
			case "clear": setting.clearCollection(); break;
		}

		configFile.set(setting.getNameInFile(), setting.getValue());
		plugin.saveConfig();
	}

	private void addToCollection(Setting setting, String[] args) {

		String input = args[3];
		if (setting.getFieldType().equals(String.class)){
			input = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
		}

		setting.addValue(parse(setting, input));
	}

	private void removeFromCollection(Setting setting, String[] args){
		String input = args[3];
		if (setting.getFieldType().equals(String.class)){
			input = String.join(" ", Arrays.copyOfRange(args, 3, args.length));
		}

		setting.removeValue(parse(setting, input));
	}

	private void setPrimitiveValue(Setting setting, String[] value) {
		
		String input = value[2];
		if (setting.getFieldType().equals(String.class)){
			input = String.join(" ",Arrays.copyOfRange(value, 2, value.length));
		}
		
		
		setting.setValue(parse(setting, input));
		configFile.set(setting.getNameInFile(), setting.getValue());
		plugin.saveConfig();
	}

	private Object parse(Setting setting, String input){
		ParseContext context = new ParseContext(setting, input);
		return ConfigurationValueParser.parse(context);
	}

	private Setting getSetting(String sectionName, String settingName){
		if (!settings.sectionExists(sectionName))
			throw new MissingSectionException(sectionName);

		if (!settings.settingExists(sectionName, settingName))
			throw new MissingSettingException(sectionName, settingName);

		return settings.getSetting(sectionName, settingName);
	}
}
