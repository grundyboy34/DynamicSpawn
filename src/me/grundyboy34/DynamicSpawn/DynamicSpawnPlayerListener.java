package me.grundyboy34.DynamicSpawn;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import sun.security.krb5.Config;


public class DynamicSpawnPlayerListener extends PlayerListener{
    public DynamicSpawn plugin;
   Crypter crypter = new Crypter(); 
    
    public DynamicSpawnPlayerListener(DynamicSpawn instance) {
        plugin = instance;
    }    
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	Player player = event.getPlayer();
    	World world = player.getWorld();
    	Server server = player.getServer();
    	String playername = player.getName(); 
    	
    	if (plugin.enabled) {
    	plugin.config.load(plugin.configfile); 
    	ArrayList<Integer> respawnpoints = new ArrayList<Integer>();    	
    	respawnpoints = (ArrayList<Integer>) plugin.configsettings.getIntegerList(playername);      
  
    	event.setRespawnLocation(new Location(world,respawnpoints.get(0),respawnpoints.get(1),respawnpoints.get(2)));
    	} else {
    		return;
    	}
    }
    
    public void onPlayerJoin(PlayerJoinEvent event) {    	
    	Player player = event.getPlayer();
    	World world = player.getWorld();
    	Server server = player.getServer();
    	String playername = player.getName();       	
    	
    	if (player.isOnline() && plugin.enabled) {
    		if (!plugin.configsettings.contains(playername)) {
    		plugin.config.add(plugin.configfile,playername,crypter.getlist(playername));    		
         if (!player.hasPlayedBefore()) {
    		plugin.config.load(plugin.configfile);  
    		
    		ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
    		loadedpoints = (ArrayList<Integer>) plugin.configsettings.getIntegerList(playername);    	
    		player.teleport(new Location(world,loadedpoints.get(0),loadedpoints.get(1),loadedpoints.get(2)));
         } else 
        	 return;
         } else  if (!player.hasPlayedBefore()) {
     		plugin.config.load(plugin.configfile);  
    		
     		ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
     		loadedpoints = (ArrayList<Integer>) plugin.configsettings.getIntegerList(playername);    	
     		player.teleport(new Location(world,loadedpoints.get(0),loadedpoints.get(1),loadedpoints.get(2)));
          } else
        	 return;
         } else return;         
    	}

    }

