package me.grundyboy34.DynamicSpawn;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.jline.internal.Log.Level;
import org.bukkit.entity.Player;

public class Crypter {

	public String Encoded;
	public static String Output;
	public String NumberCalc;

	public int getX(String input) {
		encode(input);
		int a = Character.getNumericValue(Encoded.charAt(54))
				+ Character.getNumericValue(Encoded.charAt(36))
				+ Character.getNumericValue(Encoded.charAt(84)
						+ Character.getNumericValue(Encoded.charAt(39)));
		return a * 100;
	}

	public int getZ(String input) {
		int b = Character.getNumericValue(Encoded.charAt(69))
				+ Character.getNumericValue(Encoded.charAt(97))
				+ Character.getNumericValue(Encoded.charAt(71)
						+ Character.getNumericValue(Encoded.charAt(102)));
		return b * 100;
	}

	public void encode(String input) {
		try {
			Encoded = calculateHash(input, "MD2")
					+ calculateHash(input, "SHA-256")
					+ calculateHash(input, "SHA-1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Location getPlayerGeneratedSpawn(Player player) {
		int x = getX(player.getName());
		int y = player.getWorld().getSeaLevel() + 1;
		int z = getZ(player.getName());
		Location tile = new Location(player.getWorld(), x, y, z);
		return findNearest(tile);
	}

	public Location findNearest(Location input) {
		Location newLocation = null;
		int chunkX = input.getBlockX();
		int XStop = chunkX + 16;
		int chunkZ = input.getBlockZ();
		int ZStop = chunkZ + 16;

		for (int x = chunkX - 16; x <= XStop; x++) {
			for (int i = chunkZ - 16; i <= ZStop; i++) {
				for (int h = input.getBlockY() - 10; h <= 256; h++) {
					Location loc = new Location(input.getWorld(), x, h, i);
					boolean blockEmpty = loc.getBlock().isEmpty();
					boolean blockAboveEmpty = getBlockRelative(loc, 0, 1, 0).isEmpty();
					boolean blockBelowEmpty = getBlockRelative(loc, 0, -1, 0).isEmpty();
					boolean blockBelowLiquid = getBlockRelative(loc, 0, -1, 0).isLiquid();
						
					if (blockEmpty && blockAboveEmpty && !blockBelowEmpty && !blockBelowLiquid) {
						if (newLocation == null
								|| input.distance(loc) < input
										.distance(newLocation)) {
							newLocation = loc;
						}
					}
				}
			}
		}
		return newLocation;
	}

	/**
	 * 
	 * @param block
	 *            Location to search relative from
	 * @param xFace
	 *            offset in x
	 * @param yFace
	 *            offset in y
	 * @param zFace
	 *            offset in z
	 * @return returns block at the offset from the relative block
	 */

	public Block getBlockRelative(Location block, int xFace, int yFace,
			int zFace) {
		return block.getWorld().getBlockAt(block.getBlockX() + xFace,
				block.getBlockY() + yFace, block.getBlockZ() + zFace);
	}

	public String calculateHash(String Input, String algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md;
		md = MessageDigest.getInstance(algorithm);
		byte[] hash = new byte[40];
		md.update(Input.getBytes("iso-8859-1"), 0, Input.length());
		hash = md.digest();
		return convertToHex(hash);
	}

	public String convertToHex(byte[] data) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buffer.append((char) ('0' + halfbyte));
				else
					buffer.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);

		}
		return buffer.toString();
	}

}