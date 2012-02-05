package me.grundyboy34.DynamicSpawn;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;


import org.bukkit.Server;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.*;


public class DynamicSpawn extends JavaPlugin {
	public boolean enabled;
	public DynamicSpawnConfig config;
	Crypter crypter = new Crypter();			
	public String[] commands = {"dynamicspawn"};
	private DynamicSpawnCommandExecutor myExecutor;	
    private final HashMap<Player, Boolean> debugee = new HashMap<Player, Boolean>();
	Logger log = Logger.getLogger("Minecraft");
	public String plugindir;
	public File configfile;
	public static FileConfiguration configsettings;
	public static Location customspawn , customfirstspawn;
	
	
	
	public boolean isDebugging(final Player player) {
		if (debugee.containsKey(player)) {
			return debugee.get(player);
		} else
			return false;
	}
	
	public void setDebugging(final Player player, final boolean value) {
		debugee.put(player, value);
	}
	
		
    public void onEnable(){ 
    	enabled = true;
    	final DynamicSpawnEntityListener entityListener = new DynamicSpawnEntityListener(this);
        final DynamicSpawnPlayerListener playerListener = new DynamicSpawnPlayerListener(this);
    	PluginManager pm = this.getServer().getPluginManager(); 
    	plugindir = this.getDataFolder().getAbsolutePath();
       configfile = new File(plugindir + File.separator + "config.yml");  
       this.configsettings = getConfig();
       config = new DynamicSpawnConfig(configfile);       
    	myExecutor = new DynamicSpawnCommandExecutor(this);	
    	config.load(configfile);
        pm.registerEvents(playerListener, this);
        pm.registerEvents(entityListener, this);
    	loadcmd();
        log.info("DynamicSpawn is enabled!");     
        
       
        
        
    }
     
    public void onDisable(){ 
    	enabled = false;
     log.info("DynamicSpawn disabled!");
      }

    
    public void loadcmd() {
    	for (int i=0; i < commands.length; i++) {
     getCommand(commands[i]).setExecutor(myExecutor);
    	}
    }
    
   }



