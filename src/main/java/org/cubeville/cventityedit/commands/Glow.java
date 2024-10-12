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


public class Glow extends Command
{
    public Glow() {
        super("glow");
        CommandUtils.addNoParameter(this);
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        List<Entity> entities = CommandUtils.filterSelectedEntitiesByNoParameter(player, parameters);

        // TODO make temporary?
        boolean doglow = false;
        boolean doglowset = false;
        
        for(Entity e: entities) {
            if(!doglowset) {
                doglowset = true;
                doglow = !e.isGlowing();
            }
            e.setGlowing(doglow);
        }

        return new CommandResponse(doglow ? "Glowing activated" : "Glowing deactivated");
    }
}
