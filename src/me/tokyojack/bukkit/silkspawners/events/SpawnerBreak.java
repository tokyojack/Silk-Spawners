package me.tokyojack.bukkit.silkspawners.events;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.tokyojack.bukkit.silkspawners.utils.ItemBuilder;

public class SpawnerBreak implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onSpawnerBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();

		if (block.getType() != Material.MOB_SPAWNER)
			return;

		ItemStack item = player.getItemInHand();

		if (item == null || item.getType().equals(Material.AIR))
			return;

		if (!(item.containsEnchantment(Enchantment.SILK_TOUCH))) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&fYou must have a &6Silk Touch pickaxe &fto mine!"));
			event.setCancelled(true);
			return;
		}
		
		// Casting it as it checks if it's a mob spawner with (block.getType() == Material.MOB_SPAWNER)
		EntityType mobSpawnerType = ((CreatureSpawner) block.getState()).getSpawnedType();

		// Get's the entity name and removes all the "_", and then capitalizes
		String spawnerName = WordUtils.capitalizeFully(mobSpawnerType.name().replaceAll("_", " "));

		// Switches the name "PIG_ZOMBIE" name to "Zombie Pigman" as that's how it's most commonly referred as
		if (mobSpawnerType == EntityType.PIG_ZOMBIE)
			spawnerName = "Zombie Pigman";

		block.getWorld().dropItem(block.getLocation(),
				new ItemBuilder(Material.MOB_SPAWNER).setName(
						ChatColor.translateAlternateColorCodes('&', "&f[" + spawnerName + "] &aSpawner"))
						.toItemStack());
	}

}
