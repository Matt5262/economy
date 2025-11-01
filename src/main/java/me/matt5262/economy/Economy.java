package me.matt5262.economy;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.manager.LocalExpansionManager;

import me.matt5262.economy.commands.SellCommand;
import me.matt5262.economy.expansions.SellPlaceholder;
import me.matt5262.economy.listeners.SellMenuListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public final class Economy extends JavaPlugin {

    private final HashMap<UUID, Double> balances = new HashMap<>();
    /*
        (private = no other classes can change its data) (final = you cannot change balances to something else like a number or a string. You can still change its data tho.)
        (HashMap<UUID, Double> = The type, hashmap, the content, player uuids and their balance.)
        (new HashMap<>(); = assign "balances" to a new HashMap so a HashMap actually exists now.)
     */

    @Override
    public void onEnable() {
        saveDefaultConfig();
        // does your config file not exist then take whatever you've made inside the config.yml and create the file. Does not overwrite if it exists.
        loadBalances();
        // checks if balances exist in
        getCommand("sell").setExecutor(new SellCommand(this));
        getServer().getPluginManager().registerEvents(new SellMenuListener(this), this);
        // registers the command and listener

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPIPlugin.getInstance()
                    .getLocalExpansionManager()
                    .register(new SellPlaceholder(this));
            getLogger().info("Economy placeholders loaded!");
        } else {
            getLogger().warning("PlaceholderAPI not found: placeholders won't work.");
        }

        new BukkitRunnable() {
            // create a bukkitRunnable
            @Override
            public void run() {
                saveBalances();
                getLogger().info("Auto-saved all player balances.");
            }

        }.runTaskTimer(this, 0, 15*60*20);
        // start a repeat timer task that runs every 15 minutes, 1 second is 20 ticks, and it is written in ticks
        getLogger().info("Economy Enabled!");
        // let ppl know that the plugin is enabled.
    }

    @Override
    public void onDisable() {
        saveBalances();
        getLogger().info("Balances saved on disable.");
    }

    public HashMap<UUID, Double> getBalances() {
        return balances;
    }
    // it's a getter method
    // you write HashMap<UUID, Double> because this method needs to know what type it returns

    public void addBalance(UUID uuid, double amount) {
        balances.put(uuid, balances.getOrDefault(uuid, 0.0) + amount);
    }

    public void saveBalances() {
        for (var entry : balances.entrySet()) {
            // for each entry in balances,  do the following code. Before you do it, convert balances into an entry (a live collection for your hashmap that the loop can get/read)
            getConfig().set("balances." + entry.getKey(), entry.getValue());
            // the current entry we are dealing with: get the plugins' config, and set the entry's key under the balances category and write the value next to it
        }
        saveConfig();
        // save changes to config, java method
    }

    public void loadBalances() {
        if (getConfig().isConfigurationSection("balances")) {
            // get config and check if balances section exists
            for (String key : getConfig().getConfigurationSection("balances").getKeys(false)) {
                // the key will be the current uuid it's going through. The (false) argument means it only gets direct keys, not nested ones.
                // basically it takes the keys (uuids) that are under the section balances
                UUID uuid = UUID.fromString(key);
                // turns the key string which is the UUID into an actual UUID type instead of string.
                double value = getConfig().getDouble("balances." + key);
                // sets value to the double (the balance) from the path fx: "balances.UUID: here is the value it'll get"
                balances.put(uuid, value);
                // now you put the uuid into the hashmap so we can use the data then next to it the value that you got from the balance config.
                // remember, "how does it get my uuid if im not in the config?", it doesn't, it only loads those who exists.
                // If you never had money there's no reason for you to exist.
            }
        }
    }
}