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


		if (plugin.configsettings.getBoolean("Global SpawnPoint:")
				&& player.isOnline() && plugin.enabled) {
			ArrayList<Integer> firstpoints = (ArrayList<Integer>) plugin.configsettings
					.getIntegerList("Global SpawnPoint:");
			event.setRespawnLocation(new Location(world, firstpoints.get(0), firstpoints
					.get(1), firstpoints.get(2)));
		} else {
		if (plugin.enabled) {
			if (player.getBedSpawnLocation().getBlock().getTypeId() != block.BED.id) {
				plugin.config.load(plugin.configfile);
				ArrayList<Integer> respawnpoints = new ArrayList<Integer>();
				respawnpoints = (ArrayList<Integer>) plugin.configsettings
						.getIntegerList(playername);

				event.setRespawnLocation(new Location(world, respawnpoints
						.get(0), respawnpoints.get(1), respawnpoints.get(2)));
			} else {
				event.setRespawnLocation(player.getBedSpawnLocation().subtract(
						1, 0, 1));

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

		if (plugin.configsettings.getBoolean("Global First SpawnPoint:")
				&& player.isOnline() && plugin.enabled) {
			ArrayList<Integer> firstpoints = (ArrayList<Integer>) plugin.configsettings
					.getIntegerList("Global First SpawnPoint:");
			player.teleport(new Location(world, firstpoints.get(0), firstpoints
					.get(1), firstpoints.get(2)));
		} else {

		if (player.isOnline() && plugin.enabled) {
			if (!plugin.configsettings.contains(playername)) {
				plugin.config.add(plugin.configfile, "Players:", playername);
				plugin.config.add(plugin.configfile, playername,
						crypter.getlist(playername));
				if (!player.hasPlayedBefore()) {
					plugin.config.load(plugin.configfile);

					ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
					loadedpoints = (ArrayList<Integer>) plugin.configsettings
							.getIntegerList(playername);
					player.teleport(new Location(world, loadedpoints.get(0),
							loadedpoints.get(1), loadedpoints.get(2)));
				} else
					return;
			} else if (!player.hasPlayedBefore()) {
				plugin.config.load(plugin.configfile);

				ArrayList<Integer> loadedpoints = new ArrayList<Integer>();
				loadedpoints = (ArrayList<Integer>) plugin.configsettings
						.getIntegerList(playername);
				player.teleport(new Location(world, loadedpoints.get(0),
						loadedpoints.get(1), loadedpoints.get(2)));
			} else
				return;
		} else
			return;
	}
	}
}
