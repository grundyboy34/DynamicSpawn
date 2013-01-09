package me.grundyboy34.DynamicSpawn;

import java.util.ArrayList;
import java.util.logging.Level;

import net.minecraft.server.v1_4_6.Block;

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
		String playerName = player.getName();
		final Block block = null;
		boolean gsEnabled = plugin.getConfig().getBoolean(
				"DynamicSpawn.GlobalSpawnpoint.Enabled");

		if (gsEnabled && plugin.enabled) {
			ArrayList<Integer> firstpoints = new ArrayList<Integer>();
			firstpoints.add(
					0,
					plugin.getConfig().getInt(
							"DynamicSpawn.GlobalSpawnpoint.Cords.X"));
			firstpoints.add(
					1,
					plugin.getConfig().getInt(
							"DynamicSpawn.GlobalSpawnpoint.Cords.Y"));
			firstpoints.add(
					2,
					plugin.getConfig().getInt(
							"DynamicSpawn.GlobalSpawnpoint.Cords.Z"));
			Location spawnLocation = new Location(world, firstpoints.get(0),
					firstpoints.get(1), firstpoints.get(2));

			event.setRespawnLocation(spawnLocation);
		} else {
			if (plugin.enabled) {
				if (player.getBedSpawnLocation() == null
						|| player.getBedSpawnLocation().getBlock().getTypeId() != Block.BED.id) {
					ArrayList<Integer> respawnpoints = new ArrayList<Integer>();
					respawnpoints.add(
							0,
							plugin.getConfig()
									.getInt("DynamicSpawn.Players."
											+ playerName + ".X"));
					respawnpoints.add(
							1,
							plugin.getConfig()
									.getInt("DynamicSpawn.Players."
											+ playerName + ".Y"));
					respawnpoints.add(
							2,
							plugin.getConfig()
									.getInt("DynamicSpawn.Players."
											+ playerName + ".Z"));
					Location spawnLocation = new Location(world,
							respawnpoints.get(0), respawnpoints.get(1),
							respawnpoints.get(2));

					event.setRespawnLocation(spawnLocation);
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
		String playerName = player.getName();
		boolean fgsEnabled = plugin.getConfig().getBoolean(
				"DynamicSpawn.GlobalFirstSpawnpoint.Enabled");

		if (plugin.enabled) {

			if (!plugin.getConfig().contains(playerName)) {
				Location playerSpawn = crypter.getPlayerGeneratedSpawn(player);
				if (playerSpawn == null) {
					plugin.log.log(Level.SEVERE, "playerSpawn is NULL!");
					return;
				}
				DynamicSpawnConfig.addSection("DynamicSpawn.Players."
						+ playerName);
				DynamicSpawnConfig.addSection("DynamicSpawn.Players."
						+ playerName + ".X");
				DynamicSpawnConfig.addSection("DynamicSpawn.Players."
						+ playerName + ".Y");
				DynamicSpawnConfig.addSection("DynamicSpawn.Players."
						+ playerName + ".Z");
				DynamicSpawnConfig.setSection("DynamicSpawn.Players."
						+ playerName + ".X", playerSpawn.getBlockX());
				DynamicSpawnConfig.setSection("DynamicSpawn.Players."
						+ playerName + ".Y", playerSpawn.getBlockY());
				DynamicSpawnConfig.setSection("DynamicSpawn.Players."
						+ playerName + ".Z", playerSpawn.getBlockZ());
				plugin.saveConfig();

			}

			if (!player.hasPlayedBefore()) {
				if (fgsEnabled) {
					ArrayList<Integer> firstpoints = new ArrayList<Integer>();
					firstpoints.add(
							0,
							plugin.getConfig().getInt(
									"DynamicSpawn.GlobalFirstSpawnpoint.X"));
					firstpoints.add(
							1,
							plugin.getConfig().getInt(
									"DynamicSpawn.GlobalFirstSpawnpoint.Y"));
					firstpoints.add(
							2,
							plugin.getConfig().getInt(
									"DynamicSpawn.GlobalFirstSpawnpoint.Z"));
					Location spawnLocation = new Location(world,
							firstpoints.get(0), firstpoints.get(1),
							firstpoints.get(2));
					while(!world.isChunkLoaded(spawnLocation.getChunk())) {
						world.loadChunk(spawnLocation.getChunk());
						spawnLocation.getChunk().load();
					}
					player.teleport(spawnLocation);
				} else {

					ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
					loadedpoints.add(
							0,
							plugin.getConfig()
									.getInt("DynamicSpawn.Players."
											+ playerName + ".X"));
					loadedpoints.add(
							1,
							plugin.getConfig()
									.getInt("DynamicSpawn.Players."
											+ playerName + ".Y"));
					loadedpoints.add(
							2,
							plugin.getConfig()
									.getInt("DynamicSpawn.Players."
											+ playerName + ".Z"));

					Location spawnLocation = new Location(world,
							loadedpoints.get(0), loadedpoints.get(1),
							loadedpoints.get(2));
					while(!world.isChunkLoaded(spawnLocation.getChunk())) {
						world.loadChunk(spawnLocation.getChunk());
						spawnLocation.getChunk().load();
					}
					player.teleport(spawnLocation);

				}
			}
		}
	}
}
