package me.matt5262.economy;

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
        getCommand("sell").setExecutor(new SellCommand());
        getServer().getPluginManager().registerEvents(new SellMenuListener(), this);
        // registers the command and listener
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SellPlaceholder().regiser();
            // #missing
        }
        // registers PlaceholderAPI
        new BukkitRunnable() {
        // #missing
            @Override
            public void run() {
                saveBalances();
                // #missing
                getLogger().info("Auto-saved all player balances.");
            }
        }.runTaskTimer(this, 0, 15*60*20);
        // #missing
        getLogger().info("SellSystem Enabled!");
        // let ppl know that the plugin is enabled.
    }

    @Override
    public void onDisable() {



    }

    public void loadBalances() {
        if (getConfig().isConfigurationSection("balances")) {
            // get config and check if balances section exists
            for (String key : getConfig().getConfigurationSection("balances").getKeys(false)) {
                // the key will be the current uuid it's going through. The (false) argument means it only gets direct keys, not nested ones.
                // basically it takes the keys (uuids) that are under the section balances
                UUID uuid = UUID.fromString(key);
                // turns uuid into a UUID type from the key which is a string, the string is the actual UUID #missing
                double value = getConfig().getDouble("balances." + key);
                // sets value to the double (the balance) from the path fx: "balances.AUuidHere"
                balances.put(uuid, value);
                // now you put the uuid into the hashmap so we can use the data then next to it the value that you got from the balance config.
                // remember, "how does it get my uuid if im not in the config?", it doesn't, it only loads those who exists.
                // If you never had money there's no reason for you to exist.
            }
        }
    }
}

/*
package me.yourname.sellsystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.UUID;

public class SellSystem extends JavaPlugin {

    private final HashMap<UUID, Double> balances = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadBalances();

        // Register command + listener
        getCommand("sell").setExecutor(new SellCommand(this));
        getServer().getPluginManager().registerEvents(new SellMenuListener(this), this);

        // Register PlaceholderAPI (optional)
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new SellPlaceholder(this).register();
        }

        // Auto-save every 15 minutes (15 * 60 * 20 ticks)
        new BukkitRunnable() {
            @Override
            public void run() {
                saveBalances();
                getLogger().info("Auto-saved all player balances.");
            }
        }.runTaskTimer(this, 0, 15 * 60 * 20);

        getLogger().info("SellSystem enabled!");
    }

                        // YOU REACHED HERE
                        // https://chatgpt.com/g/g-p-68bc14d61a748191b931e544f6104865-coding-in-java-bukkit/c/69026eed-a724-832c-b32a-160ca7e2d911
                        // https://chatgpt.com/g/g-p-68bc14d61a748191b931e544f6104865-coding-in-java-bukkit/c/69027560-5a68-8328-ac62-8902ace77c68

    @Override
    public void onDisable() {
        saveBalances();
        getLogger().info("Balances saved on disable.");
    }

    public HashMap<UUID, Double> getBalances() {
        return balances;
    }

    public void addBalance(UUID uuid, double amount) {
        balances.put(uuid, balances.getOrDefault(uuid, 0.0) + amount);
    }

    // ---------------- Saving and Loading ----------------
    public void saveBalances() {
        for (var entry : balances.entrySet()) {
            getConfig().set("balances." + entry.getKey(), entry.getValue());
        }
        saveConfig();
    }

    public void loadBalances() {
        if (getConfig().isConfigurationSection("balances")) {
            for (String key : getConfig().getConfigurationSection("balances").getKeys(false)) {
                UUID uuid = UUID.fromString(key);
                double value = getConfig().getDouble("balances." + key);
                balances.put(uuid, value);
            }
        }
    }
}
 */