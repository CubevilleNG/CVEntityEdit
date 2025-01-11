package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.*;

import org.cubeville.commons.commands.CommandParameterListInteger;
import org.cubeville.commons.commands.CommandParameterEnumeratedStringList;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class Filter extends Command
{
    public Filter() {
        super("filter");
        addParameter("type", true, new CommandParameterEnumeratedStringList(new HashSet<>(Arrays.asList("itemdisplay", "textdisplay", "display"))));
        addParameter("no", true, new CommandParameterListInteger());
        addParameter("remove", true, new CommandParameterListInteger());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        List<Entity> selectedEntities = Selection.getInstance().getSelectedEntities(player);
        Selection.getInstance().clearEntitySelection(player);

        Set<String> types = null;
        if(parameters.containsKey("type"))
            types = (Set<String>) parameters.get("type");

        List<Integer> nos = null;
        if(parameters.containsKey("no")) {
            nos = (List<Integer>) parameters.get("no");
            for(int i: nos) {
                if(i < 1 || i > selectedEntities.size())
                    throw new CommandExecutionException("Invalid no " + i);
            }
        }

        List<Integer> remove = null;
        if(parameters.containsKey("remove")) {
            if(nos != null) throw new CommandExecutionException("remove and no can't be used together.");
            remove = (List<Integer>) parameters.get("remove");
            for(int i: remove) {
                if(i < 1 || i > selectedEntities.size())
                    throw new CommandExecutionException("Invalid no " + i);
            }
        }
        
        int count = 0;
        for(Entity e: selectedEntities) {
            count++;

            boolean doRemove = false;
            if(remove != null)
                doRemove = remove.contains(count);

            boolean typeIncluded = true;
            if(types != null) {
                if((types.contains("itemdisplay") && e.getType() == EntityType.ITEM_DISPLAY) ||
                   (types.contains("textdisplay") && e.getType() == EntityType.TEXT_DISPLAY) ||
                   (types.contains("display") && e instanceof Display)) {
                    typeIncluded = true;
                }
            }

            boolean noIncluded = true;
            if(nos != null) {
                noIncluded = nos.contains(count);
            }

            if(doRemove) continue;
            if(noIncluded == true && typeIncluded == true)
                Selection.getInstance().addEntity(player, e);
        }

        return new CommandResponse("Number of entities left: " + Selection.getInstance().getSelectedEntities(player).size());
    }
}
