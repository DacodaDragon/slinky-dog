package nl.dacodadragon.slinkydog.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class SectionMapping {
	String name;
	Collection<Setting> settings = new ArrayList<>();

	public boolean containsSetting(String name) {
		for (Setting mapping : settings)
			if (mapping.getNameInGame().equalsIgnoreCase(name))
				return true;
		return false;
	}

	public Setting getSetting(String name) {
		for (Setting mapping : settings)
			if (mapping.getNameInGame().equalsIgnoreCase(name))
				return mapping;
		throw null;
	}

	public boolean addSetting(Setting setting) {
		if (containsSetting(setting.getNameInGame()))
			return false;

		settings.add(setting);
		return true;
	}

	public List<String> getSettingNames() {
		List<String> names = new ArrayList<>();
		for (Setting mapping : settings)
			names.add(mapping.getNameInGame());
		return names;
	}

	public Collection<Setting> getAllSettings() {
		return settings;
	}
}
