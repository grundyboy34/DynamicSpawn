package me.grundyboy34.DynamicSpawn;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.Block;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import sun.security.krb5.Config;

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

		if (DynamicSpawn.configsettings
				.getBoolean("DynamicSpawn.GlobalSpawnpoint.enabled")
				&& player.isOnline() && plugin.enabled) {
			ArrayList<Integer> firstpoints = (ArrayList<Integer>) DynamicSpawn.configsettings
					.getIntegerList("DynamicSpawn.GlobalSpawnpoint.cords");
			event.setRespawnLocation(new Location(world, firstpoints.get(0),
					firstpoints.get(1), firstpoints.get(2)));
		} else {
			if (plugin.enabled) {
				if (player.getBedSpawnLocation() == null|| player.getBedSpawnLocation().getBlock().getTypeId() != Block.BED.id) {
					ArrayList<Integer> respawnpoints = new ArrayList<Integer>();
					respawnpoints = (ArrayList<Integer>) DynamicSpawn.configsettings
							.getIntegerList("DynamicSpawn.Players."
									+ playername);

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

		if (DynamicSpawn.configsettings
				.getBoolean("DynamicSpawn.GlobalFirstSpawnpoint.enabled")
				&& player.isOnline() && plugin.enabled) {
			ArrayList<Integer> firstpoints = (ArrayList<Integer>) DynamicSpawn.configsettings
					.getIntegerList("DynamicSpawn.GlobalFirstSpawnpoint.cords");
			player.teleport(new Location(world, firstpoints.get(0), firstpoints
					.get(1), firstpoints.get(2)));
		} else {

			if (player.isOnline() && plugin.enabled) {
				if (!DynamicSpawn.configsettings
						.contains("DynamicSpawn.Players." + playername)) {
					DynamicSpawnConfig.addsection("DynamicSpawn.Players."
							+ playername);
					DynamicSpawnConfig.setsection("DynamicSpawn.Players."
							+ playername, crypter.getlist(playername));
					plugin.saveConfig();
					if (!player.hasPlayedBefore()) {
						

						ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
						loadedpoints = (ArrayList<Integer>) DynamicSpawn.configsettings
								.getIntegerList("DynamicSpawn.Players."
										+ playername);
						player.teleport(new Location(world,
								loadedpoints.get(0), loadedpoints.get(1),
								loadedpoints.get(2)));
					} else
						return;
				} else if (!player.hasPlayedBefore()) {
					

					ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
					loadedpoints = (ArrayList<Integer>) DynamicSpawn.configsettings
							.getIntegerList("DynamicSpawn.Players."
									+ playername);
					player.teleport(new Location(world, loadedpoints.get(0),
							loadedpoints.get(1), loadedpoints.get(2)));
				} else
					return;
			} else
				return;
		}
	}
}
