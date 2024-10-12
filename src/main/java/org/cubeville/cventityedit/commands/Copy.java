package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.BlockDisplay;

import com.sk89q.worldedit.bukkit.*;

import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class Copy extends Command
{
    public Copy() {
        super("copy");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        List<Entity> entities = Selection.getInstance().getSelectedEntities(player);
        if(entities == null || entities.size() == 0) throw new CommandExecutionException("No selection.");

        for(Entity e: entities) {
            e.copy(e.getLocation());
            // if(e.getType() == EntityType.BLOCK_DISPLAY) {
            //     Entity ne = e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.BLOCK_DISPLAY);
            //     BlockDisplay bde = (BlockDisplay) e;
            //     BlockDisplay bdne = (BlockDisplay) ne;
            //     bdne.setBlock(bde.getBlock());
            //     bdne.setTransformation(bde.getTransformation());
            // }
            // one day: e.copy(e.getLocation());
        }

        return null;
    }
}
