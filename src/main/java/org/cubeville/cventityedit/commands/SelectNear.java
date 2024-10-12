package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.*;

import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class SelectNear extends Command
{
    public SelectNear() {
        super("selectnear");
        addBaseParameter(new CommandParameterDouble());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        double radius = (double) baseParameters.get(0);

        Selection.getInstance().clearEntitySelection(player);

        List<Entity> entities = player.getWorld().getEntities();
        for(Entity e: entities) {
            if(e.getType() == EntityType.PLAYER) continue;
            if(e.getLocation().distance(player.getLocation()) < radius) {
                Selection.getInstance().addEntity(player, e);
            }
        }

        Selection.getInstance().sort(player);
        
        return new CommandResponse("&a" + Selection.getInstance().getSelectedEntities(player).size() + " entities selected.");
    }
}
