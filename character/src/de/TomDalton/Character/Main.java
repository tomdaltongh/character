package de.TomDalton.Character;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import api.Api;
import de.TomDalton.Character.Commands.Ch;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class Main extends JavaPlugin{
	
	public FileConfiguration config;
	public HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	private File data;
	private YamlConfiguration chardata;
	
	@Override
	public void onEnable() {
		createConfig();
		initData();
		createListener();
		new Ch(this);
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
