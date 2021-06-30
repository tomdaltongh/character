package de.TomDalton.Character;



import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerClass implements org.bukkit.event.Listener{
	
	@SuppressWarnings("unused")
	private Main plugin;

	public ListenerClass(Main plugin) {
		this.plugin = plugin;
	}

	/**
	 * ungenutzt / nebenprojekt
	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
		Player sender = e.getPlayer();
		for (Player player : Bukkit.getOnlinePlayers()) {
		    Location location = player.getLocation();
		    if(e.getMessage().charAt(0)=='.') {
		    	if (sender.getWorld().getName().equals(player.getWorld().getName()) && location.distanceSquared(sender.getLocation()) <= 5 * 5) {
			        if(sender!=player) {
			        	player.sendMessage(sender.getDisplayName() + " *flüstert* " + e.getMessage());
			        }
			        sender.sendMessage(sender.getDisplayName() + " *flüstert* " + e.getMessage());
			        e.setCancelled(true);
			    }
		    }
		}
	}
	*/
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		//plugin.loadPermissions(e.getPlayer());
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		//plugin.perms.remove(e.getPlayer().getUniqueId());
	}
}