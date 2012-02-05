package me.grundyboy34.DynamicSpawn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

    public class DynamicSpawnCommandExecutor implements CommandExecutor {
     
    	private DynamicSpawn plugin;
    	private DynamicSpawnConfig config;
    	private int commandID;
     
    	public DynamicSpawnCommandExecutor(DynamicSpawn plugin) {
    		this.plugin = plugin;
    	}
     
    	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    		Player player = null;    	
    		if (sender instanceof Player) {
    			player = (Player) sender;
    		} 		
    		
    			if (player != null && player.isOp()) {
    		    for (int i=0; i < plugin.commands.length;i++) {
    		    	if (cmd.getName().equalsIgnoreCase(plugin.commands[i])){
    		    		commandID = i;  
    		    		break;
    		    	}
    		    }
    		    
    			switch (commandID)  {    		
    			case 0:
    				if (args.length == 1) {
        			if (args[0].equalsIgnoreCase("on")) {    				
        				if (!plugin.enabled) {
        					plugin.enabled = true;;
        					sender.sendMessage("DynamicSpawn enabled!");
        					return true;
        				} else {
        					sender.sendMessage("DynamicSpawn is already enabled!");
        					return true;
        				}    			
        			} else if (args[0].equalsIgnoreCase("off")) {    				
        				if (plugin.enabled) {
        				       plugin.enabled = false;
        					sender.sendMessage("DynamicSpawn disabled!");
        					return true;
        				} else {
        					sender.sendMessage("DynamicSpawn is already disabled!");
        					return true;
        				}    				
        			} else {    				
        				return false;
        			}   	
        		
        		} else {
        			return false;
        		}
    			}
    		} else {
    			sender.sendMessage("DynamicSpawn commands can only be used by in-game Operators!");
    			return true;
    	      } 	
    	 		
    	return false;
			
    	}
    }
    	
    	
    
