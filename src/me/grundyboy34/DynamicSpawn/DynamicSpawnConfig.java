package me.grundyboy34.DynamicSpawn;

import java.util.ArrayList;


public class DynamicSpawnConfig {

	static DynamicSpawn plugin;
	
	
	

	public static void load(DynamicSpawn thePlugin) {
		ArrayList<Integer> intlist = new ArrayList<Integer>();
		intlist.add(0,567);
		intlist.add(1,64);
		intlist.add(2,465);
 		plugin = thePlugin;
	if (!plugin.configfile.exists()) {
		plugin.getConfig().options().copyDefaults(true);
		setsection("DynamicSpawn.GlobalFirstSpawnpoint.Cords", intlist);
		setsection("DynamicSpawn.GlobalSpawnpoint.Cords", intlist);
		plugin.saveConfig();	
	}
	
	}

	public static void setsection(String label, Object value) {
		plugin.getConfig().set(label, value);
		
		}
	public static void addsection(String label) {
		plugin.getConfig().createSection(label);
		
	}
	}
