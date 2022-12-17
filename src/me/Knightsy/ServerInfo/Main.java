package me.Knightsy.ServerInfo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
public class Main extends JavaPlugin{
	
	public Inventory invInfo;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new infoEvents(this), this);
		this.getServer().getPluginManager().registerEvents(new GuiBlocker(this), this);
		this.saveDefaultConfig();
		createInv();
	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("sinfo")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("You cannot do this!");
				return true;
			}
			if (args.length > 0) {
				// /sinfo reload
				if (args[0].equalsIgnoreCase("reload")) {
					this.reloadConfig();
					for (String msg : this.getConfig().getStringList("reload.messages")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								msg));
					}
				}
			}
			if (args.length == 0) {
				Player player = (Player) sender;
				if(player.hasPermission("sinfo.use")) {
					player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 3.0f);
					player.openInventory(invInfo);
					return true;
				}else {
					player.playSound(player.getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 3.0f);
					player.sendMessage("You do not have access to this command");
				}
			}
		}
		return false;
	}
	
	/* CREATE INVENTORY */	
	public void createInv() { 
		
		invInfo = Bukkit.createInventory(null, 9,ChatColor.GOLD +  this.getConfig().getString("name"));
		
		ItemStack item = new ItemStack(Material.GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Click!");
		meta.setLore(lore);	
		
		
		if (getConfig().getBoolean("disc-enable")) {
			//Discord Panel
			item.setType(Material.getMaterial(getConfig().getString("disc-material")));
			meta.setDisplayName(ChatColor.GOLD + "DISCORD");
			item.setItemMeta(meta);
			invInfo.setItem(getConfig().getInt("disc-slot"), item);
		}else {
			item.setType(Material.AIR);
		}
		
		
		if (getConfig().getBoolean("forums-enable")) {
			//Forums Panel
			item.setType(Material.getMaterial(getConfig().getString("forum-material")));
			meta.setDisplayName(ChatColor.GOLD + "FORUMS");
			item.setItemMeta(meta);
			invInfo.setItem(getConfig().getInt("forum-slot"), item);
		}else {
			item.setType(Material.AIR);
		}
		
		if (getConfig().getBoolean("info-enable")) { 
			//Info Panel
			item.setType(Material.getMaterial(getConfig().getString("info-material")));
			meta.setDisplayName(ChatColor.GOLD + "INFO");
			item.setItemMeta(meta);
			invInfo.setItem(getConfig().getInt("info-slot"), item);
		}else {
			item.setType(Material.AIR);
		}
		
		if (getConfig().getBoolean("help-enable")) {
			//Help Panel
			item.setType(Material.getMaterial(getConfig().getString("help-material")));
			meta.setDisplayName(ChatColor.GOLD + "HELP");
			item.setItemMeta(meta);
			invInfo.setItem(getConfig().getInt("help-slot"), item);
		}else {
			item.setType(Material.AIR);
		}
		
		if(getConfig().getBoolean("rules-enable")) {
			//Rules Panel
			item.setType(Material.getMaterial(getConfig().getString("rules-material")));
			meta.setDisplayName(ChatColor.GOLD + "RULES");
			item.setItemMeta(meta);
			invInfo.setItem(getConfig().getInt("rules-slot"), item);
		}else {
			item.setType(Material.AIR);
		}
		
		
		//Close Button
		
		item.setType(Material.BARRIER);
		meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close Menu");
		lore.clear();
		meta.setLore(lore);
		item.setItemMeta(meta);
		invInfo.setItem(8, item);
		
	}
/* CREATE INVENTORY END*/	
}