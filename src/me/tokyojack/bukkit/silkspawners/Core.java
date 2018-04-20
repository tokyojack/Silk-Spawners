package me.tokyojack.bukkit.silkspawners;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.tokyojack.bukkit.silkspawners.events.SpawnerBreak;
import me.tokyojack.bukkit.silkspawners.events.SpawnerPlace;

public class Core extends JavaPlugin {

	private static Core plugin;

	public static Core getPlugin() {
		return plugin;
	}

	public void onEnable() {
		plugin = this;

		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new SpawnerBreak(), this);
		pluginManager.registerEvents(new SpawnerPlace(), this);
	}

}