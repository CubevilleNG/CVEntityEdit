package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.ItemDisplay;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class CreateItem extends Command
{
    public CreateItem() {
        super("create item");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() == Material.AIR)
            throw new CommandExecutionException("Must be holding an item!");

        Location location = player.getLocation();

        ItemDisplay id = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
        id.setItemStack(item);

        Selection.getInstance().clearEntitySelection(player);
        Selection.getInstance().addEntity(player, id);
        
        return null;
    }
}
