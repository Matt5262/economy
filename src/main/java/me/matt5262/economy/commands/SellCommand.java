package me.matt5262.economy.commands;

import me.matt5262.economy.Economy;
import me.matt5262.economy.holders.SellMenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SellCommand implements CommandExecutor{

    private final Economy plugin;

    public SellCommand(Economy plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("§cOnly players can use this command.");
            return true;
        }
        Inventory inv = Bukkit.createInventory(new SellMenuHolder(), 54, "Sell Menu");
        player.openInventory(inv);
        return true;
    }
}