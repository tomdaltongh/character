package de.TomDalton.Character;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class getVault{

    private Economy econ;
    private Main plugin;

    public getVault(Main plugin){
    	this.plugin = plugin;
    	
        if (!setupEconomy()) {
            this.plugin.getLogger().severe("[Character]"+" Verbindung zu Vault fehlgeschlagen.");
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