package de.TomDalton.Character.Commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import api.Api;
import de.TomDalton.Character.AusweisGUI;
import de.TomDalton.Character.Main;
import de.TomDalton.Character.getVault;

public class Ch implements CommandExecutor{

	public Main plugin;
	private File data;
	private FileConfiguration userdata;
	private String prefix;
	private getVault vault;
	
	public Ch(Main plugin){
		this.plugin = plugin;
		FileConfiguration config = this.plugin.getConfig();
		
		data = new File(plugin.getDataFolder(), "/data.yml");
		userdata = YamlConfiguration.loadConfiguration(data);
		Api.saveData(userdata,data);
		
		prefix = Api.getConfigString(config,"nachrichten.prefix").replace('&', '§');
		vault = new getVault(this.plugin);
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
		 *  /ch erbe *Ausweisnummer*
		 */
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length==3) {
				if(args[0].equalsIgnoreCase("set")) {
					String mode = args[1];
					if(!signed(p)) {
						if(mode.equalsIgnoreCase("rpname")) {
							userdata.set("userdata."+p.getName()+".name", args[2]);
							sender.sendMessage(prefix+" Der Name "+args[2]+" wurde gespeichert.");
						}else if(mode.equalsIgnoreCase("vorname")) {
							userdata.set("userdata."+p.getName()+".vorname", args[2]);
							sender.sendMessage(prefix+" Der Vorname "+args[2]+" wurde gespeichert.");
						}else if(mode.equalsIgnoreCase("alter")) {
							userdata.set("userdata."+p.getName()+".alter", args[2]);
							sender.sendMessage(prefix+" Das Alter "+args[2]+" wurde gespeichert.");
						}else if(mode.equalsIgnoreCase("beruf")) {
							userdata.set("userdata."+p.getName()+".beruf", args[2]);
							sender.sendMessage(prefix+" Der Beruf "+args[2]+" wurde gespeichert.");
						}else {
							sender.sendMessage(prefix+" Diese Kategorie wurde noch nicht eingerichtet.");
						}
						Api.saveData(userdata, data);
					}else {
						if(mode.equalsIgnoreCase("beruf")) {
							userdata.set("userdata."+p.getName()+".beruf", args[2]);
							sender.sendMessage(prefix+" Der Beruf "+args[2]+" wurde gespeichert.");
						}else {
							sender.sendMessage(prefix+" Kategorie noch nicht eingerichtet oder vorherige Angaben wurden bestätigt und können nicht geändert werden.");
						}
						Api.saveData(userdata, data);
					}
					sender.sendMessage(prefix+" (Info) Überprüfe deine Angaben auf Richtigkeit und korrigiere, falls nötig.");
				}else {
					p.sendMessage(prefix+" Benutze /ch erstellen um dir Hilfe anzeigen zu lassen.");
				}
			}else if(args.length==1) {
				if(args[0].equalsIgnoreCase("sign")) {
					userdata.set("userdata."+p.getName()+".signed", "true");

					userdata.set("userdata."+p.getName()+".geld", getKonto(p));
					
					Api.saveData(userdata, data);
					p.sendMessage(prefix+" Du hast deine Angaben bestätigt und kannst diese nun nicht mehr ändern.");
					p.sendMessage(prefix+" >/ch set beruf *aktueller Beruf*< ist davon ausgeschlossen.");
					p.sendMessage(prefix+" Du möchtest einen anderen Charakter erstellen? Nutze >/ch kill< um deinen Fortschritt zu löschen.");
				}else if(args[0].equalsIgnoreCase("kill")) {
					userdata.set("userdata."+p.getName()+".signed", "false");
					userdata.set("userdata."+p.getName()+".name", "unbekannt");
					userdata.set("userdata."+p.getName()+".vorname", "unbekannt");
					userdata.set("userdata."+p.getName()+".alter", 0);
					userdata.set("userdata."+p.getName()+".beruf", "unbekannt");
					
					Api.saveData(userdata, data);
					
					//Geld wird abgezogen und dem (wenn existent) Erbe übergeben
					int aktuell = (int) vault.getEconomy().getBalance(p);
					vault.getEconomy().withdrawPlayer(p, vault.getEconomy().getBalance(p));
					String erbe = userdata.getString("userdata."+p.getName()+".erbe");
					if(erbe != null) {
						vault.getEconomy().depositPlayer(Bukkit.getPlayer(erbe), aktuell);
					}
					userdata.set("userdata."+p.getName()+".geld", 0);
					Api.saveData(userdata, data);
				}else if(args[0].equalsIgnoreCase("erstellen")) {
					p.sendMessage(prefix+" Führe die folgenden Befehle aus, um deinen RP-Charakter zu erstellen.");
					p.sendMessage(prefix+" Bitte beachte bei der Erstellung die aktuellen RP-Richtlinien.");
					p.sendMessage(prefix+" /ch set rpname *Name*");
					p.sendMessage(prefix+" /ch set vorname *Vorname*");
					p.sendMessage(prefix+" /ch set alter *Alter*");
					p.sendMessage(prefix+" /ch set beruf *aktueller Beruf*");
					p.sendMessage(prefix+" Mit dem Befehl /ch sign kannst du deine Angaben bestätigen und bist bestätigter Bürger.");
				}else if(Bukkit.getPlayer(args[0]) != null) {
					AusweisGUI gui = new AusweisGUI(plugin);
					update(Bukkit.getPlayer(args[0]));
					p.openInventory(gui.getIventory(Bukkit.getPlayer(args[0])));
					p.sendMessage(prefix+" Mit dem Befehl /ch *Spielername* kannst du dir den Ausweis eines Spielers ansehen.");
				}else {
					p.sendMessage(prefix+" Benutze /ch erstellen um dir Hilfe anzeigen zu lassen.");
				}
			}else if(args.length==2) {
				if(args[0].equalsIgnoreCase("seterbe")) {
					try {
						Player erbe = Bukkit.getPlayer(args[1]);
						userdata.set("userdata."+p.getName()+".erbe", erbe.getName());
						Api.saveData(userdata, data);
						p.sendMessage(prefix+" Der Spieler "+erbe.getName()+" ist nun dein Erbe.");
					}catch(Exception e) {
						p.sendMessage(prefix+" Der Spieler war noch nicht auf dem Server");
					}
				}
			}
			
			//UPDATE GELD in data.yml? Effizienz?
			update(p);
		}else {
			System.out.println(prefix + " Dieser Befehl funktioniert nur als Spieler.");
		}
		return true;
	}
	
	public void update(Player p) {
		userdata.set("userdata."+p.getName()+".geld", getKonto(p));
		Api.saveData(userdata, data);
	}
	
	public int getKonto(Player p) {
		int geld = 0;
		
		geld = (int) vault.getEconomy().getBalance(p);
		
		return geld;
	}
	
	public boolean signed(Player p) {
		try {
			if(userdata.getString("userdata."+p.getName()+".signed").equals("true")) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}
	
}
