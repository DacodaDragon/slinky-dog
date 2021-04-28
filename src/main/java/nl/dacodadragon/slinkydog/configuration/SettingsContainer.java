package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SettingsContainer {
	private final JavaPlugin plugin;
	Collection<SectionMapping> sectionMappings = new ArrayList<>();

	public SettingsContainer(JavaPlugin plugin, Class<?> type) {
		this.plugin = plugin;
		addAllFieldsOfType(type);
	}

	public boolean sectionExists(String name) {
		for (SectionMapping mapping : sectionMappings)
			if (mapping.name.equalsIgnoreCase(name))
				return true;
		return false;
	}

	public SectionMapping getSection(String name) {
		for (SectionMapping mapping : sectionMappings)
			if (mapping.name.equalsIgnoreCase(name))
				return mapping;
		return null;
	}

	public Setting getSetting(String section, String setting) {
		return getSection(section).getSetting(setting);
	}

	public boolean settingExists(String section, String setting) {

		if (!sectionExists(section))
			return false;

		return getSection(section).containsSetting(setting);
	}

	private void addAllFieldsOfType(Class<?> type){
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields)
			if (Modifier.isStatic(field.getModifiers()))
				addField(field);
	}

	private void addField(Field field) {

		Setting setting = new Setting(field);
		createSection(setting.getSectionInGame());
		addSettingToSection(setting, setting.getSectionInGame());
	}

	private void createSection(String name) {
		if (!sectionExists(name))
			createNewSection(name);
	}

	private void addSettingToSection(Setting setting, String sectionName){
		getSection(sectionName).addSetting(setting);
	}

	public void createNewSection(String name) {
		SectionMapping mapping = new SectionMapping();
		mapping.name = name;
		sectionMappings.add(mapping);
	}

	public List<String> getAllSectionNames() {
		List<String> names = new ArrayList<>();
		for (SectionMapping mapping : sectionMappings)
			names.add(mapping.name);
		return names;
	}

	public void writeAllDefaultValues(FileConfiguration config) {
		for (SectionMapping section : sectionMappings) {
			for (Setting setting : section.getAllSettings()) {
				String settingLocation = setting.getNameInFile();
				if (!config.contains(settingLocation))
					config.set(settingLocation, setting.GetValue());
			}
		}
		plugin.saveConfig();
	}

	public void loadValuesFromSettings(FileConfiguration config) {
		for (SectionMapping section : sectionMappings) {
			for (Setting setting : section.getAllSettings()) {
				String settingLocation = setting.getNameInFile();
				setting.setValue(config.get(settingLocation));
			}
		}
	}
}
