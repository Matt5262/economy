package me.matt5262.economy.listeners;

import me.matt5262.economy.Economy;
import me.matt5262.economy.holders.SellMenuHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellMenuListener implements Listener {

    private final Economy plugin;

    public SellMenuListener(Economy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player player)) return;
        if (!(event.getInventory().getHolder() instanceof SellMenuHolder)) return;

        Inventory inv = event.getInventory();
        double total = 0;

        for (ItemStack item : inv.getContents()) {
            if (item == null) continue;
            // if there is no item skip the rest of the code and go onto the next

            Material mat = item.getType();

            double price = switch (mat) {
                case DIAMOND -> 50.0;
                case GOLD_INGOT -> 15.0;
                default -> 0.0;
            };

            if (price > 0) {
                total += price*item.getAmount();
            }

            inv.clear();

            if (total > 0) {
                plugin.addBalance(player.getUniqueId(), total);
                player.sendMessage("§aYou sold your items for §e$" + total);
            }else {
                player.sendMessage("§cYou didn’t sell anything.");
            }

        }

    }

}