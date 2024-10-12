package org.cubeville.cventityedit.commands;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class Move extends Command
{
    public Move() {
        super("move");
        addBaseParameter(new CommandParameterDouble());
        addBaseParameter(new CommandParameterDouble());
        addBaseParameter(new CommandParameterDouble());
        CommandUtils.addNoParameter(this);
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        List<Entity> entities = CommandUtils.filterSelectedEntitiesByNoParameter(player, parameters);
        
        double x = (double)baseParameters.get(0);
        double y = (double)baseParameters.get(1);
        double z = (double)baseParameters.get(2);

        for(Entity e: entities) {
            Location loc = e.getLocation();
            e.teleport(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z, loc.getYaw(), loc.getPitch()));
        }

        return new CommandResponse("&aMoved " + entities.size() + " entities.");
    }
}
