package me.grundyboy34.DynamicSpawn;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicSpawn extends JavaPlugin {
	public boolean enabled;
	public DynamicSpawnConfig config;
	Crypter crypter = new Crypter();
	public String[] commands = { "dynamicspawn", "ds" };
	private DynamicSpawnCommandExecutor myExecutor;
	private final HashMap<Player, Boolean> debugee = new HashMap<Player, Boolean>();
	Logger log = Logger.getLogger("Minecraft");
	public String pluginDir;
	public File configFile;
	public static FileConfiguration configSettings;
	public static Location customSpawn, customFirstSpawn;

	public boolean isDebugging(final Player player) {
		if (debugee.containsKey(player)) {
			return debugee.get(player);
		} else {
			return false;
		}
	}

	public void setDebugging(final Player player, final boolean value) {
		debugee.put(player, value);
	}

	public void onEnable() {
		enabled = true;
		final DynamicSpawnEntityListener entityListener = new DynamicSpawnEntityListener(
				this);
		final DynamicSpawnPlayerListener playerListener = new DynamicSpawnPlayerListener(
				this);
		PluginManager pm = this.getServer().getPluginManager();
		pluginDir = this.getDataFolder().getAbsolutePath();
		configFile = new File(pluginDir + File.separator + "config.yml");
		DynamicSpawn.configSettings = getConfig();
		myExecutor = new DynamicSpawnCommandExecutor(this);
		DynamicSpawnConfig.load(this);
		pm.registerEvents(playerListener, this);
		pm.registerEvents(entityListener, this);
		loadCMD();
		log.info("DynamicSpawn is enabled!");

	}

	public void onDisable() {
		enabled = false;
		log.info("DynamicSpawn disabled!");
	}

	public void loadCMD() {
		for (int i = 0; i < commands.length; i++) {
			getCommand(commands[i]).setExecutor(myExecutor);
		}
	}

}
