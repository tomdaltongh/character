package de.TomDalton.Character;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class getVault extends JavaPlugin {

    private Economy econ;

    @Override
    public void onEnable(){
        if (!setupEconomy()) {
            this.getLogger().severe("[Character]"+" Verbindung zu Vault fehlgeschlagen. Plugin wird deaktiviert.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
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