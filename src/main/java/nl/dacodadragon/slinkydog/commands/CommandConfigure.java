package nl.dacodadragon.slinkydog.commands;

import java.util.ArrayList;
import java.util.List;

import nl.dacodadragon.slinkydog.SlinkydogDebug;
import nl.dacodadragon.slinkydog.configuration.exceptions.ConfigurationErrorException;
import nl.dacodadragon.slinkydog.configuration.SettingsAccessor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandConfigure implements CommandExecutor, TabCompleter {
	private final JavaPlugin plugin;
	private final SettingsAccessor settings;

	public CommandConfigure(JavaPlugin plugin, Class<?> configType) {
		this.plugin = plugin;
		settings = new SettingsAccessor(plugin, configType, plugin.getConfig());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		switch (args.length) {
		case 2:
			getConfiguration(sender, args[0], args[1]);
			return true;
		case 3:
			setConfiguration(sender, args[0], args[1], args[2]);
			return true;
		}
		return false;
	}

	private void getConfiguration(final CommandSender sender, final String section, final String setting) {
		try {
			sender.sendMessage(ChatColor.GOLD + "Value for " + section + "-" + setting + ": " + ChatColor.YELLOW + settings.getSettingValue(section, setting));
		} catch (ConfigurationErrorException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		} catch (RuntimeException e) {
			sender.sendMessage(ChatColor.RED + "Internal exception occurred.");
			SlinkydogDebug.error(e.getMessage());
		}
	}

	private void setConfiguration(final CommandSender sender, final String section, final String setting, final String value) {
		try {
			settings.setSetting(section, setting, value);
			getConfiguration(sender, section, setting);
		} catch (ConfigurationErrorException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		} catch (RuntimeException e) {
			sender.sendMessage(ChatColor.RED + "Internal exception occurred.");
			SlinkydogDebug.error(e.getMessage());
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		switch (args.length) {
		case 1:
			return settings.getSectionNames();
		case 2:
			return settings.getSettingNames(args[0]);
		case 3:
			return settings.getSettingArguments(args[0], args[1]);
		}

		return new ArrayList<>();
	}
}
