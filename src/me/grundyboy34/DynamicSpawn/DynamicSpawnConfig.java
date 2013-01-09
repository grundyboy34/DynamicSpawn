package me.grundyboy34.DynamicSpawn;

import java.util.ArrayList;

import org.bukkit.Location;

public class DynamicSpawnConfig {

	static DynamicSpawn plugin;

	public static void load(DynamicSpawn thePlugin) {

		plugin = thePlugin;
		
		if (!plugin.configFile.exists()) {
			plugin.getConfig().options().copyDefaults(true);
			Location worldSpawnLocation = plugin.crypter.findNearest(plugin.getServer().getWorlds().get(0).getSpawnLocation());

			setSection("DynamicSpawn.GlobalFirstSpawnpoint.Cords.X",worldSpawnLocation.getBlockX());
			setSection("DynamicSpawn.GlobalFirstSpawnpoint.Cords.Y",worldSpawnLocation.getBlockY());
			setSection("DynamicSpawn.GlobalFirstSpawnpoint.Cords.Z",worldSpawnLocation.getBlockZ());
			setSection("DynamicSpawn.GlobalSpawnpoint.Cords.X", worldSpawnLocation.getBlockX());
			setSection("DynamicSpawn.GlobalSpawnpoint.Cords.Y", worldSpawnLocation.getBlockY());
			setSection("DynamicSpawn.GlobalSpawnpoint.Cords.Z", worldSpawnLocation.getBlockZ());
			setSection("DynamicSpawn.GlobalSpawnpoint.Enabled", false);
			setSection("DynamicSpawn.GlobalFirstSpawnpoint.Enabled", false);
			plugin.saveConfig();
		}

	}
	

	public static void setSection(String label, Object value) {
		plugin.getConfig().set(label, value);

	}

	public static void addSection(String label) {
		plugin.getConfig().createSection(label);

	}
}
