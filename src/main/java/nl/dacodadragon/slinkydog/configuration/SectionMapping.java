package nl.dacodadragon.slinkydog.configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class SectionMapping {
	String name;
	Collection<Setting> settings = new ArrayList<>();

	public boolean containsSetting(String name) {
		for (Setting mapping : settings)
			if (mapping.name.equalsIgnoreCase(name))
				return true;
		return false;
	}

	public Setting getSetting(String name) {
		for (Setting mapping : settings)
			if (mapping.name.equalsIgnoreCase(name))
				return mapping;
		throw null;
	}

	public boolean addField(Field field, String name) {
		if (containsSetting(name))
			return false;

		Setting mapping = new Setting();
		mapping.field = field;
		mapping.name = name;
		settings.add(mapping);
		return true;
	}

	public List<String> getSettingNames() {
		List<String> names = new ArrayList<>();
		for (Setting mapping : settings)
			names.add(mapping.name);
		return names;
	}

	public Collection<Setting> getAllSettings() {
		return settings;
	}
}
