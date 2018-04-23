package me.tokyojack.bukkit.silkspawners.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.tokyojack.bukkit.silkspawners.Core;

public class SpawnerPlace implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onSpawnerPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();

		if (block == null || block.getType().equals(Material.AIR))
			return;
		
		if (block.getType() != Material.MOB_SPAWNER)
			return;

		ItemStack item = event.getItemInHand();

		if (!item.hasItemMeta())
			return;

		if (!item.getItemMeta().hasDisplayName())
			return;

		String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
		
		// Gets the string between "[" and "]", the uppercases it
		// Then get's the EntityType with it
		EntityType answer = EntityType.valueOf((itemName.substring(itemName.indexOf("[") + 1, itemName.indexOf("]"))).toUpperCase());

		Core.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(Core.getPlugin(), new Runnable() {
			public void run() {
				BlockState blockState = block.getState();
				((CreatureSpawner) blockState).setSpawnedType(answer);
				blockState.update();
			}
		}, 1L);

	}

}
