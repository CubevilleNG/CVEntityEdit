package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;


public class Remove extends Command
{
    public Remove() {
        super("remove");
        CommandUtils.addNoParameter(this);
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        List<Entity> entities = CommandUtils.filterSelectedEntitiesByNoParameter(player, parameters);
        
        for(Entity e: entities)
            e.remove();

        return new CommandResponse("&aTried to remove " + entities.size() + " entities (only if in loaded chunk).");
    }

}
