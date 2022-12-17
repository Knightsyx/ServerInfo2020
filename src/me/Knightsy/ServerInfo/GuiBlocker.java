package me.Knightsy.ServerInfo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GuiBlocker implements Listener{
   
    Main plugin;
    public List <String> activeGui = new ArrayList<String>();
   
   
    /* CONSTRUCTORS */
    public GuiBlocker(){}
    public GuiBlocker(Main instance){
        plugin = instance;
    }
    /* END CONSTRUCTORS */
   
   
    /* EVENTS HANDLERS */
    @EventHandler
    public void onGuiActivation(InventoryClickEvent event){
       
        Player player = (Player) event.getWhoClicked();
       
        if(activeGui.contains(player.getName()))
            event.setCancelled(true);
    }
   
    @EventHandler
    public void onGuiClosing(InventoryCloseEvent event){
       
        Player player = (Player) event.getPlayer();
       
        if(activeGui.contains(player.getName()))
            activeGui.remove(player.getName());
    }
    /* END EVENTS HANDLERS */
   

    public void addToGUIBlocker(Player player){
        if(!activeGui.contains(player.getName()))
            activeGui.add(player.getName());
    }
   
    public void removeFromGUIBlocker(Player player){
        if(activeGui.contains(player.getName()))
            activeGui.remove(player.getName());
    }
   
   
    /* ERROR-PROOF CHECK */
    public boolean invalidClick(Player player, InventoryClickEvent event){
        if(activeGui.contains(player.getName()))
            if(event.getSlot() == -999 // Checks if player clicks outside the inventory
                    || event.getCurrentItem() == null // Checks for invalid item
                    || event.getCurrentItem().getType() == Material.AIR) // Checks for clicking empty slot
                return true;
        return false;
    }
}