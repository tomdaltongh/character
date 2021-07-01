package de.TomDalton.Character;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class getVault{

    private Economy econ;
    private Main plugin;

    public getVault(Main plugin){
    	this.plugin = plugin;
    	
        if (!setupEconomy()) {
            this.plugin.getLogger().severe(" Verbindung zu Vault fehlgeschlagen. Plugin wird deaktiviert.");
            Bukkit.getPluginManager().disablePlugin(this.plugin);
            return;
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public Economy getEconomy() {
        return econ;
    }


}