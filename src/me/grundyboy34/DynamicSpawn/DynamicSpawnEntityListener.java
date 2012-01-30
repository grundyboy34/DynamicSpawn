package me.grundyboy34.DynamicSpawn;


import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class DynamicSpawnEntityListener extends EntityListener {
	
public static DynamicSpawn plugin;

public DynamicSpawnEntityListener(DynamicSpawn instance) {
	plugin = instance;
}
	}
