package de.TomDalton.Character;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import api.Api;

public class AusweisGUI implements Listener{
	
	public Main plugin;
	private File data;
	private FileConfiguration userdata;
	private String prefix;
	
	public AusweisGUI(Main plugin){
		this.plugin = plugin;
		
		FileConfiguration config = this.plugin.getConfig();
		
		data = new File(plugin.getDataFolder(), "/data.yml");
		userdata = YamlConfiguration.loadConfiguration(data);
		Api.saveData(userdata,data);
		
		prefix = Api.getConfigString(config,"nachrichten.prefix").replace('&', '�');
		
		
		//
	}
	
	
	public Inventory getIventory(Player p) {
		Inventory ivn = Bukkit.createInventory(null, 9, "Ausweis von "+p.getDisplayName());
		
		ivn = initItems(ivn,p);//Items initialisieren
		
		return ivn;
	}
	
	public Inventory initItems(Inventory ivn, Player p) {
		String name = userdata.getString("userdata."+p.getDisplayName()+".vorname")+" "+userdata.getString("userdata."+p.getDisplayName()+".name");
		int alter = userdata.getInt("userdata."+p.getDisplayName()+".alter");
		String wohnort = userdata.getString("userdata."+p.getDisplayName()+".wohnort");
		int geld = userdata.getInt("userdata."+p.getDisplayName()+".geld");
		String beruf = userdata.getString("userdata."+p.getDisplayName()+".beruf");
		String erbe = userdata.getString("userdata."+p.getDisplayName()+".erbe");
		ivn.addItem(createGuiItem(Material.PLAYER_HEAD, "Vor- und Nachname", name, ""));
		ivn.addItem(createGuiItem(Material.CLOCK, "Alter", alter+"", ""));
		ivn.addItem(createGuiItem(Material.COMPASS, "Wohnort", wohnort+"", ""));
		ivn.addItem(createGuiItem(Material.GOLD_NUGGET, "Geld", geld+"", ""));
		ivn.addItem(createGuiItem(Material.NETHERITE_PICKAXE, "Beruf", beruf+"", ""));
		ivn.addItem(createGuiItem(Material.LEATHER_BOOTS, "Erbe", erbe+"", ""));
		return ivn;
	}
	
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
