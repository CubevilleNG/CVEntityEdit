package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Display;


import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class GetItem extends Command
{
    public GetItem() {
        super("get item");
        addBaseParameter(new CommandParameterInteger());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, java.util.List<Object> baseParameters)
        throws CommandExecutionException {

        java.util.List<Entity> entities = Selection.getInstance().getSelectedEntities(player);
        if(entities == null || entities.size() == 0) throw new CommandExecutionException("No selection!");

        int entityNo = (int) baseParameters.get(0);
        if(entityNo < 1 || entityNo > entities.size()) throw new CommandExecutionException("Invalid entity index!");

        Entity entity = entities.get(entityNo - 1);

        if(entity.getType() != EntityType.ITEM_DISPLAY) throw new CommandExecutionException("Entity is no itemm display!");

        ItemDisplay item = (ItemDisplay) entity;
        
        player.getInventory().setItemInMainHand(item.getItemStack());

        return null;
    }
}
