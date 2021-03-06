package nl.dacodadragon.slinkydog.commands;

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
	private final SettingsAccessor settings;

	public CommandConfigure(JavaPlugin plugin, Class<?> configType) {
		SlinkydogDebug.setLogTarget(plugin.getLogger());
		settings = new SettingsAccessor(plugin, configType, plugin.getConfig());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 2) {
			getConfiguration(sender, args[0], args[1]);
			return true;
		}

		if (args.length > 2){
			setConfiguration(sender, args);
			return true;
		}
		
		return false;
	}

	private void getConfiguration(final CommandSender sender, final String section, final String setting) {
		try {
			sender.sendMessage(ChatColor.GOLD + "Value for " + section + "-" + setting + ": " + ChatColor.YELLOW + settings.getSettingValue(section, setting));
			String description = settings.getSettingDescription(section, setting);

			if (!description.equals(""))
			{
				sender.sendMessage(ChatColor.GOLD + "Description: " + ChatColor.YELLOW + description);
			}

		} catch (ConfigurationErrorException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		} catch (RuntimeException e) {
			sender.sendMessage(ChatColor.RED + "Internal exception occurred: " + e.getMessage());
			throw e;
		}
	}

	private void setConfiguration(final CommandSender sender, String[] args) {
		try {
			settings.setSetting(args);
			getConfiguration(sender, args[0], args[1]);
		} catch (ConfigurationErrorException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		} catch (RuntimeException e) {
			sender.sendMessage(ChatColor.RED + "Internal exception occurred: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		switch (args.length) {
		case 1:
			return settings.getSectionNames();
		case 2:
			return settings.getSettingNames(args[0]);
		}

		return settings.getSettingArguments(args);
	}
}
