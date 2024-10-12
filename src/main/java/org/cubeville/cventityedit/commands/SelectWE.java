package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.IncompleteRegionException;

import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class SelectWE extends Command
{
    public SelectWE() {
        super("selectwe");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        }
        catch(IncompleteRegionException e) {
            throw new IllegalArgumentException("Region selection incomplete.");
        }
        if(selection == null) throw new IllegalArgumentException("No region selected.");

        Selection.getInstance().clearEntitySelection(player);
        
        World world = BukkitAdapter.adapt(worldEdit.getSession(player).getSelectionWorld());
        BlockVector3 min = selection.getMinimumPoint();
        BlockVector3 max = selection.getMaximumPoint();

        List<Entity> entities = player.getWorld().getEntities();
        for(Entity e: entities) {
            if(e.getType() == EntityType.PLAYER) continue;
            if(e.getLocation().getX() >= min.getX() && e.getLocation().getX() <= max.getX() &&
               e.getLocation().getY() >= min.getY() && e.getLocation().getY() <= max.getY() &&
               e.getLocation().getZ() >= min.getZ() && e.getLocation().getZ() <= max.getZ()) {
                Selection.getInstance().addEntity(player, e);
            }
        }

        Selection.getInstance().sort(player);
        
        return new CommandResponse("&a" + Selection.getInstance().getSelectedEntities(player).size() + " entities selected.");
    }
}
