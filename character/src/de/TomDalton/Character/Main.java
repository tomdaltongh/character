package de.TomDalton.Character;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import api.Api;
import de.TomDalton.Character.Commands.Ch;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Main extends JavaPlugin{
	
	public FileConfiguration config;
	private File data;
	private YamlConfiguration chardata;
	
	@Override
	public void onEnable() {
		createConfig();
		initData();
		createListener();
		new Ch(this);
		new TownyListener(this);
		//new Permission(this);
	}

	@Override
	public void onDisable() {
		
	}
	
	public void createListener() {
		Bukkit.getServer().getPluginManager().registerEvents(new ListenerClass(this), this);
	}
	
	public void createConfig() {
		this.saveDefaultConfig();
		config = this.getConfig();
		config.addDefault("nachrichten.prefix", "&f[&6Character&f]");
		config.options().copyDefaults(true);
		saveConfig();
	}
	
	public void initData() {
		data = new File(this.getDataFolder(), "/data.yml");
		chardata = YamlConfiguration.loadConfiguration(data);
		Api.saveData(chardata,data);
	}
	
}
