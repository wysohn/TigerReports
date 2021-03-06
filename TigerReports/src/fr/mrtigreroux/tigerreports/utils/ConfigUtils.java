package fr.mrtigreroux.tigerreports.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.mrtigreroux.tigerreports.data.ConfigFile;

/**
 * @author MrTigreroux
 */

public class ConfigUtils {
	
	private final static Set<String> ActivationWords = new HashSet<String>(Arrays.asList("true", "t" ,"on", "o", "enabled", "yes", "y", "activated", "act", "a"));
	
	public static boolean isEnabled(ConfigurationSection config, String path) {
		return config.get(path) != null && ActivationWords.contains(config.getString(path));
	}
	
	public static char getColorCharacter() {
		return ConfigFile.CONFIG.get().contains("Config.ColorCharacter") && ConfigFile.CONFIG.get().getString("Config.ColorCharacter") != null && ConfigFile.CONFIG.get().getString("Config.ColorCharacter").length() >= 1 ? ConfigFile.CONFIG.get().getString("Config.ColorCharacter").charAt(0) : '&';
	}
	
	public static String getLineBreakSymbol() {
		return ConfigFile.CONFIG.get().get("Config.LineBreakSymbol") != null ? ConfigFile.CONFIG.get().getString("Config.LineBreakSymbol") : "//";
	}
	
	public static boolean exist(ConfigurationSection config, String path) {
		return config.get(path) != null;
	}

	public static Material getMaterial(ConfigurationSection config, String path) {
		String icon = config.getString(path);
		return icon != null && icon.startsWith("Material-") ? Material.matchMaterial(icon.split("-")[1].toUpperCase().replace(":"+getDamage(config, path), "")) : null;
	}

	public static short getDamage(ConfigurationSection config, String path) {
		try {
			String icon = config.getString(path);
			return icon != null && icon.startsWith("Material-") && icon.contains(":") ? Short.parseShort(icon.split(":")[1]) : 0;
		} catch(Exception NoDamage) {
			return 0;
		}
	}
	
	public static String getSkull(ConfigurationSection config, String path) {
		String icon = config.getString(path);
		return icon != null && icon.startsWith("Skull-") ? icon.split("-")[1] : null;
	}

	public static int getMessagesHistory() {
		return ConfigFile.CONFIG.get().getInt("Config.MessagesHistory");
	}

	public static int getReportedImmunity() {
		return ConfigFile.CONFIG.get().getInt("Config.ReportedImmunity")*1000;
	}
	
	public static int getReportsNotificationsInterval() {
		return ConfigFile.CONFIG.get().getInt("Config.ReportsNotifications.MinutesInterval")*1200;
	}
	
}
