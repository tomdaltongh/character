package de.TomDalton.Character;



import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerClass implements org.bukkit.event.Listener{
	
	@SuppressWarnings("unused")
	private Main plugin;

	public ListenerClass(Main plugin) {
		this.plugin = plugin;
	}
	
	//Ausweis Event, muss canceln um klauen der Items zu verhindern
	@EventHandler
	public void InvClick(InventoryClickEvent e) {
		if(e.getWhoClicked().getOpenInventory().getTitle().contains("Ausweis von ")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
	}
}