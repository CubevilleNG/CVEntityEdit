package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.*;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class SelectNearest extends Command
{
    class EDist implements Comparable<EDist> {
        public EDist(double dist, Entity entity) {
            this.dist = dist;
            this.entity = entity;
        }

        @Override
        public int compareTo(EDist other) {
            return new Double(dist).compareTo(other.dist);
        }
        
        public double dist;
        public Entity entity;
    }
    
    public SelectNearest() {
        super("selectnearest");
        addOptionalBaseParameter(new CommandParameterInteger());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        int index = 0;
        if(baseParameters.size() == 1)
            index = (int) baseParameters.get(0) - 1;

        Selection.getInstance().clearEntitySelection(player);

        List<EDist> edist = new ArrayList<>();
        List<Entity> entities = player.getWorld().getEntities();
        double mindist = 30.0;
        for(Entity e: entities) {
            if(e.getType() == EntityType.PLAYER) continue;
            double dist = e.getLocation().distance(player.getLocation());
            if(dist >= 30.0) continue;
            edist.add(new EDist(dist, e));
        }

        Collections.sort(edist);

        if(index < 0 || index >= edist.size())
            throw new CommandExecutionException("Invalid index, found " + edist.size() + " entities within 30 blocks radius.");

        Entity entity = edist.get(index).entity;

        Selection.getInstance().addEntity(player, entity);
        Selection.getInstance().flash(player);

        return new CommandResponse("Entity selected: " + entity.getType());
    }
}
