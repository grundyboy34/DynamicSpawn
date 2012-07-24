package me.grundyboy34.DynamicSpawn;

import java.util.ArrayList;
import net.minecraft.server.Block;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DynamicSpawnPlayerListener implements Listener {
	public DynamicSpawn plugin;
	Crypter crypter = new Crypter();

	public DynamicSpawnPlayerListener(DynamicSpawn instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		Server server = player.getServer();
		String playername = player.getName();
		final Block block = null;
		boolean gsenabled = plugin.getConfig().getBoolean("DynamicSpawn.GlobalSpawnpoint.Enabled");

		if (gsenabled && player.isOnline() && plugin.enabled) {
		ArrayList<Integer> firstpoints = new ArrayList<Integer>();
		firstpoints.add(0,plugin.getConfig().getInt("DynamicSpawn.GlobalSpawnpoint.Cords.X"));
		firstpoints.add(1,plugin.getConfig().getInt("DynamicSpawn.GlobalSpawnpoint.Cords.Y"));
		firstpoints.add(2,plugin.getConfig().getInt("DynamicSpawn.GlobalSpawnpoint.Cords.Z"));
			
			event.setRespawnLocation(new Location(world, firstpoints.get(0),
					firstpoints.get(1), firstpoints.get(2)));
		} else {
			if (plugin.enabled) {
				if (player.getBedSpawnLocation() == null|| player.getBedSpawnLocation().getBlock().getTypeId() != Block.BED.id) {
					ArrayList<Integer> respawnpoints = new ArrayList<Integer>();
					respawnpoints.add(0, plugin.getConfig().getInt("DynamicSpawn.Players." + playername +".X"));
					respawnpoints.add(1, plugin.getConfig().getInt("DynamicSpawn.Players." + playername +".Y"));
					respawnpoints.add(2, plugin.getConfig().getInt("DynamicSpawn.Players." + playername +".Z"));
									

					event.setRespawnLocation(new Location(world, respawnpoints
							.get(0), respawnpoints.get(1), respawnpoints.get(2)));
				} else {
					event.setRespawnLocation(player.getBedSpawnLocation()
							.subtract(1, 0, 1));

				}
			} else {
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		Server server = player.getServer();
		String playername = player.getName();
		boolean fsenabled = plugin.getConfig().getBoolean("DynamicSpawn.GlobalFirstSpawnpoint.Enabled");

		if (fsenabled && player.isOnline() && plugin.enabled && !player.hasPlayedBefore()) {
			ArrayList<Integer> firstpoints = new ArrayList<Integer>();
			firstpoints.add(0,plugin.getConfig().getInt("DynamicSpawn.GlobalFirstSpawnpoint.X"));
			firstpoints.add(1,plugin.getConfig().getInt("DynamicSpawn.GlobalFirstSpawnpoint.Y"));
			firstpoints.add(2,plugin.getConfig().getInt("DynamicSpawn.GlobalFirstSpawnpoint.Z"));
			player.teleport(new Location(world, firstpoints.get(0), firstpoints
					.get(1), firstpoints.get(2)));
		} else {

			if (player.isOnline() && plugin.enabled) {
				if (!plugin.getConfig().contains(playername)) {
					ArrayList<Integer> playerpoints = crypter.getlist(playername);
					DynamicSpawnConfig.addsection("DynamicSpawn.Players." + playername);
					DynamicSpawnConfig.addsection("DynamicSpawn.Players." + playername + ".X");
					DynamicSpawnConfig.addsection("DynamicSpawn.Players." + playername + ".Y");
					DynamicSpawnConfig.addsection("DynamicSpawn.Players." + playername + ".Z");
					DynamicSpawnConfig.setsection("DynamicSpawn.Players." + playername + ".X", playerpoints.get(0));
					DynamicSpawnConfig.setsection("DynamicSpawn.Players." + playername + ".Y",playerpoints.get(1));
					DynamicSpawnConfig.setsection("DynamicSpawn.Players." + playername + ".Z",playerpoints.get(2));
					plugin.saveConfig();
					if (!player.hasPlayedBefore()) {
						

						ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
						loadedpoints.add(0, plugin.getConfig().getInt("DynamicSpawn.Players."+playername+".X"));
						loadedpoints.add(1, plugin.getConfig().getInt("DynamicSpawn.Players."+playername+".Y"));
						loadedpoints.add(2, plugin.getConfig().getInt("DynamicSpawn.Players."+playername+".Z"));
						
						player.teleport(new Location(world,
								loadedpoints.get(0), loadedpoints.get(1),
								loadedpoints.get(2)));
					} else
						return;
				} else
					return;
			} else
				return;
		}
	}
}
