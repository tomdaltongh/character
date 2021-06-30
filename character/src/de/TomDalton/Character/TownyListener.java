package de.TomDalton.Character;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.palmergames.adventure.bossbar.BossBar.Listener;
import com.palmergames.bukkit.towny.event.TownAddResidentEvent;

import api.Api;

public class TownyListener implements Listener{
	
	public Main plugin;
	private File data;
	private FileConfiguration userdata;
	private String prefix;
	
	public TownyListener(Main plugin){
		this.plugin = plugin;
		
		FileConfiguration config = this.plugin.getConfig();
		
		data = new File(plugin.getDataFolder(), "/data.yml");
		userdata = YamlConfiguration.loadConfiguration(data);
		Api.saveData(userdata,data);
		
		prefix = Api.getConfigString(config,"nachrichten.prefix").replace('&', '§');
	}
	
	public void addResident(TownAddResidentEvent e) {
		//TownJoinEvent theoretisch
		Player p = Bukkit.getPlayer(e.getResident().getUUID());
		p.sendMessage(prefix + " Du bist einer anderen Stadt beigetreten, diese wird automatisch auf deinem Ausweis eingetragen.");
		userdata.set("userdata."+p.getDisplayName()+".wohnort", e.getTown().getName());
		Api.saveData(userdata, data);
	}
}
