package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.Map;

import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ItemDisplay;

import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class List extends Command
{
    public List() {
        super("list");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, java.util.List<Object> baseParameters)
        throws CommandExecutionException {

        java.util.List<Entity> entities = Selection.getInstance().getSelectedEntities(player);
        if(entities == null || entities.size() == 0) throw new CommandExecutionException("No selection.");

        CommandResponse response = new CommandResponse();
        
        int count = 0;
        for(Entity e: entities) {
            count++;

            if(e.getType() == EntityType.ITEM_DISPLAY) {
                ItemDisplay item = (ItemDisplay) e;
                response.addMessage("&8" + count + ") &rItem: " + item.getItemStack().getType());
            }

            else if(e.getType() == EntityType.TEXT_DISPLAY) {
                TextDisplay text = (TextDisplay) e;
                response.addMessage("&8" + count + ") &rText: " + text.getText());
            }
            
            else {
                response.addMessage("&8" + count + ") &rEntity: " + e.getType());
            }
            
        }

        return response;
    }
}
