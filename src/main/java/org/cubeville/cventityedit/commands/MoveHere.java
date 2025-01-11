package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

public class MoveHere extends Command
{
    public MoveHere() {
        super("movehere");
        CommandUtils.addNoParameter(this);
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        List<Entity> entities = CommandUtils.filterSelectedEntitiesByNoParameter(player, parameters);

        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        double xoffset = x - entities.get(0).getLocation().getX();
        double yoffset = y - entities.get(0).getLocation().getY();
        double zoffset = z - entities.get(0).getLocation().getZ();
        
        for(Entity e: entities) {
            Location loc = e.getLocation();
            e.teleport(new Location(loc.getWorld(), loc.getX() + xoffset, loc.getY() + yoffset, loc.getZ() + zoffset, loc.getYaw(), loc.getPitch()));
        }

        return new CommandResponse("&aMoved " + entities.size() + " entities.");
    }

}
