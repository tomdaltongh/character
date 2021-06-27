package de.TomDalton.Character.Commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.TomDalton.Character.Main;

public class Ch implements CommandExecutor{

	public Main plugin;
	private File data;
	private FileConfiguration userdata;
	private String prefix;
	
	public Ch(Main plugin){
		this.plugin = plugin;
		FileConfiguration config = this.plugin.getConfig();
		
		data = new File(plugin.getDataFolder(), "/data.yml");
		userdata = YamlConfiguration.loadConfiguration(data);
		Api.saveData(userdata,data);
		
		prefix = Api.getConfigString(config,"nachrichten.prefix").replace('&', '§');
		plugin.getCommand("ch").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		/*
		 *  /ch erstellen (Befehle die auszuführen sind anzeigen, wenn fertig erstellt, Ausweis in Form von Buch?)
		 *  /ch set rpname *name* (String)
		 *  /ch set vorname *vorname* (String)
		 *  /ch set alter *alter* (int)
		 *  /ch set beruf *beruf* (String/jederzeit ausführbar)
		 */
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length==3) {
				if(args[0].equalsIgnoreCase("set")) {
					String mode = args[1];
					if(mode.equalsIgnoreCase("rpname")) {
						userdata.set("userdata."+p.getDisplayName()+".name", args[2]);
						sender.sendMessage(prefix+" Der Name "+args[2]+" wurde gespeichert.");
					}else if(mode.equalsIgnoreCase("vorname")) {
						userdata.set("userdata."+p.getDisplayName()+".vorname", args[2]);
						sender.sendMessage(prefix+" Der Vorname "+args[2]+" wurde gespeichert.");
					}else if(mode.equalsIgnoreCase("alter")) {
						userdata.set("userdata."+p.getDisplayName()+".alter", args[2]);
						sender.sendMessage(prefix+" Das Alter "+args[2]+" wurde gespeichert.");
					}else if(mode.equalsIgnoreCase("beruf")) {
						userdata.set("userdata."+p.getDisplayName()+".beruf", args[2]);
						sender.sendMessage(prefix+" Der Beruf "+args[2]+" wurde gespeichert.");
					}else {
						sender.sendMessage(prefix+" Diese Kategorie wurde noch nicht eingerichtet.");
					}
					Api.saveData(userdata, data);
					sender.sendMessage(prefix+" Überprüfe deine Angaben auf Richtigkeit und korrigiere, falls nötig.");
				}else if(args[0].equalsIgnoreCase("erstellen")) {
					
				}else {
					sender.sendMessage(prefix+" Benutze /ch erstellen um dir Hilfe anzeigen zu lassen.");
				}
			}
		}else {
			System.out.println(prefix + " Dieser Befehl funktioniert nur als Spieler.");
		}
		return false;
	}
	
}
