package me.grundyboy34.DynamicSpawn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.configuration.file.YamlConfiguration;

public class DynamicSpawnConfig {
	private DynamicSpawn plugin;    
	
	
public DynamicSpawnConfig(File configfile) {
	//this.configsettings = new FileConfiguration();
	if (!configfile.exists()) {		
		try {
			plugin.configsettings.save(configfile);
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(127);
			list.add(64);
			list.add(42);
			add(configfile,"Global First SpawnPoint:", false);
			add(configfile,"Global SpawnPoint:", false);
			add(configfile,"Global SpawnPoint:", list);
			add(configfile,"Global First SpawnPoint:", list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} else {
		try {
			plugin.configsettings.load(configfile);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
	}

public void add(File configfile, String label, Object value) {	
HashMap<String, Object> ConfigMap = new HashMap<String, Object>();
ConfigMap.put(label, value);
for (String key : ConfigMap.keySet()) {
	plugin.configsettings.set(key, ConfigMap.get(key));
}
try {
	plugin.configsettings.save(configfile);
} catch (IOException e) {
	e.printStackTrace();
}
}
public void add(File configfile,String label, Object value, Object value2) {
	HashMap<String, Object> ConfigMap = new HashMap<String, Object>();
	ConfigMap.put(label, value);
	ConfigMap.put(label,value2);
	for (String key : ConfigMap.keySet()) {
		plugin.configsettings.set(key, ConfigMap.get(key));
	}
	try {
		plugin.configsettings.save(configfile);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
public void add(File configfile, String label, Object value, Object value2, Object value3) {
	HashMap<String, Object> ConfigMap = new HashMap<String, Object>();
	ConfigMap.put(label, value);
	ConfigMap.put(label,value2);
	ConfigMap.put(label, value3);
	for (String key : ConfigMap.keySet()) {
		plugin.configsettings.set(key, ConfigMap.get(key));
	}
	try {
		plugin.configsettings.save(configfile);
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public void load(File configfile) {
	try {
		plugin.configsettings.load(configfile);		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	}

