package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.*;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class SelectNo extends Command
{
    public SelectNo() {
        super("selectno");
        addBaseParameter(new CommandParameterInteger());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        int no = (int) baseParameters.get(0);
        Selection.getInstance().setEntitySelection(player, CommandUtils.filterSelectedEntitiesByNo(player, no));

        return null;
    }
}
