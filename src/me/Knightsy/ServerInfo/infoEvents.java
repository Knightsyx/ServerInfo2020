package me.Knightsy.ServerInfo;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class infoEvents implements Listener {
	private Map<String, Long> cooldowns = new HashMap<String, Long>();
	
	private Main plugin;
	
	/* CONSTRUCTORS */
	public infoEvents(){}
	public infoEvents(Main instance) {
		plugin = instance;
	
	/* END CONSTRUCTORS */
		
	}

	
	/* EVENTS HANDLERS */
	@EventHandler()
	public void onclick(InventoryClickEvent event) {
		
		
		if (!event.getInventory().equals(plugin.invInfo))
			return;
		
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getItemMeta() == null) return;
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		event.setCancelled(true);

		Player player = (Player) event.getWhoClicked();
		
		// add cooldown here::
		if (cooldowns.containsKey(player.getName())) {
			if (cooldowns.get(player.getName()) > System.currentTimeMillis()) {
				// they still have time left on the cooldown
				long timeleft = (cooldowns.get(player.getName()) - System.currentTimeMillis()) / 1000;
				player.sendMessage(ChatColor.GOLD + "Wait " + timeleft + " second(s)!");
				return;
			}
		}
		

		cooldowns.put(player.getName(), System.currentTimeMillis() + (1 * 1000));		
		if (event.getSlot() == plugin.getConfig().getInt("disc-slot") && event.getCurrentItem().getType() == Material.getMaterial(plugin.getConfig().getString("disc-material"))) { // Checks if item clicked is Red Stained Glass
			
			
			// Chat Message When Clicked
			player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			TextComponent DiscMessage = new TextComponent (ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("disc-message")));
			DiscMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getConfig().getString("discord-link")));
			DiscMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to join the discord !").color(ChatColor.GRAY).italic(true).create()));
			
			player.spigot().sendMessage(DiscMessage);	
			

		}
		if (event.getSlot() == plugin.getConfig().getInt("forum-slot") && event.getCurrentItem().getType() == Material.getMaterial(plugin.getConfig().getString("forum-material"))) { // Checks if item clicked is Blue Stained Glass
			// Chat Message When Clicked
			player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			TextComponent ForumsMessage = new TextComponent (ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("forum-message")));
			ForumsMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, plugin.getConfig().getString("forums-link")));
			ForumsMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to vist the forums !").color(ChatColor.GRAY).italic(true).create()));
			
			player.spigot().sendMessage(ForumsMessage);	

		}
		if (event.getSlot() == plugin.getConfig().getInt("info-slot") && event.getCurrentItem().getType() == Material.getMaterial(plugin.getConfig().getString("info-material"))) { // Checks if item clicked is Blue Stained Glass
			if (plugin.getConfig().getBoolean("essentials")) {
				Bukkit.dispatchCommand(player, "info");
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			}else {
				for (String msg : plugin.getConfig().getStringList("info.message")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							msg));
				}
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			}
		}
		if (event.getSlot() == plugin.getConfig().getInt("help-slot") && event.getCurrentItem().getType() == Material.getMaterial(plugin.getConfig().getString("help-material"))) { // Checks if item clicked is Blue Stained Glass
			if (plugin.getConfig().getBoolean("essentials")) {
				Bukkit.dispatchCommand(player, "help");
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			}else {
				for (String msg : plugin.getConfig().getStringList("help.message")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							msg));
				}
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			}
		}
		if (event.getSlot() == plugin.getConfig().getInt("rules-slot") && event.getCurrentItem().getType() == Material.getMaterial(plugin.getConfig().getString("rules-material"))) { // Checks if item clicked is Blue Stained Glass
			if (plugin.getConfig().getBoolean("essentials")) {
				Bukkit.dispatchCommand(player, "rules");
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			}else {
				for (String msg : plugin.getConfig().getStringList("rules.message")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							msg));
				}
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			}
		}
		if (event.getSlot() == 8) {
			// closes GUI
			player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
			player.closeInventory();
		}
		
		return;
	}
	/* END EVENTS HANDLERS */

}