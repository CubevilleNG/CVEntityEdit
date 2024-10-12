package org.cubeville.cventityedit.commands;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;

import org.cubeville.cventityedit.Selection;

public class CommandUtils
{
    public static void addNoParameter(Command command) {
        command.addParameter("no", true, new CommandParameterInteger());
    }

    public static List<Entity> filterSelectedEntitiesByNo(Player player, int no)
        throws CommandExecutionException {

        List<Entity> entities = Selection.getInstance().getSelectedEntities(player);
        if(entities.size() == 0) throw new CommandExecutionException("No selection!");

        if(no < 1 || no > entities.size()) throw new CommandExecutionException("Invalid no. parameter!");

        Entity e = entities.get(no - 1);
        List<Entity> ret = new ArrayList<>();
        ret.add(e);
        return ret;
    }
    
    public static List<Entity> filterSelectedEntitiesByNoParameter(Player player, Map<String, Object> parameters)
        throws CommandExecutionException {

        Object noObj = parameters.get("no");
        if(noObj == null) return Selection.getInstance().getSelectedEntities(player);

        int no = (int) noObj;

        return filterSelectedEntitiesByNo(player, no);
    }
}
