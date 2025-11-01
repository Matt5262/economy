package me.matt5262.economy.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.matt5262.economy.Economy;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SellPlaceholder extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true; // keep expansion loaded after reloads
    }

    private final Economy plugin;

    public SellPlaceholder(Economy plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "economy";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Matti";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (identifier.equalsIgnoreCase("balance")) {
            double bal = plugin.getBalances().getOrDefault(player.getUniqueId(), 0.0);
            return String.format("%.2f", bal);
        }
        return null;
    }

}

/*
public class SellPlaceholder extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "sell";
    }

    @Override
    public @NotNull String getAuthor() {
        return "YourName";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (identifier.equalsIgnoreCase("balance")) {
            double bal = plugin.getBalances().getOrDefault(player.getUniqueId(), 0.0);
            return String.format("%.2f", bal);
        }
        return null;
    }
}

 */