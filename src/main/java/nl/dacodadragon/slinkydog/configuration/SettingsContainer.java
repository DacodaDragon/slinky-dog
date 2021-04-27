package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SettingsContainer {
	private final JavaPlugin plugin;
	Collection<SectionMapping> sectionMappings = new ArrayList<>();
	Class<?> type;

	public SettingsContainer(JavaPlugin plugin, Class<?> type) {
		this.plugin = plugin;
		this.type = type;
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

	public boolean addField(Field field) {
		String name = field.getName();

		if (!name.contains("_"))
			return false;

		String[] values = name.split("_");

		if (values.length != 2)
			return false;

		if (!sectionExists(values[0]))
			createNewSection(values[0]);

		getSection(values[0]).addField(field, values[1]);
		return true;
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
				String settingLocation = type.getSimpleName() + "." + section.name + "." + setting.name;
				if (!config.contains(settingLocation))
					config.set(settingLocation, setting.GetValue());
			}
		}
		plugin.saveConfig();
	}

	public void loadValuesFromSettings(FileConfiguration config) {
		for (SectionMapping section : sectionMappings) {
			for (Setting setting : section.getAllSettings()) {
				String settingLocation = type.getSimpleName() + "." + section.name + "." + setting.name;
				setting.setValue(config.get(settingLocation), false);
			}
		}
	}
}
