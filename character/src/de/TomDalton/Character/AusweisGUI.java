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
		
		prefix = Api.getConfigString(config,"nachrichten.prefix").replace('&', '§');
		
		
		//
	}
	
	
	public Inventory getIventory(Player p) {
		Inventory ivn = Bukkit.createInventory(p, 9, "Ausweis von "+p.getDisplayName());
		
		ivn = initItems(ivn);//Items initialisieren
		
		return ivn;
	}
	
	public Inventory initItems(Inventory ivn) {
		ivn.addItem(createGuiItem(Material.DIAMOND_SWORD, "Example Sword", "§aFirst line of the lore", "§bSecond line of the lore"));
		ivn.addItem(createGuiItem(Material.IRON_HELMET, "§bExample Helmet", "§aFirst line of the lore", "§bSecond line of the lore"));
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
