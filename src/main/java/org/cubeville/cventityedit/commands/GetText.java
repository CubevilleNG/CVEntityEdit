package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.Map;

import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Display;


import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class GetText extends Command
{
    public GetText() {
        super("get text");
        addBaseParameter(new CommandParameterInteger());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, java.util.List<Object> baseParameters)
        throws CommandExecutionException {

        java.util.List<Entity> entities = Selection.getInstance().getSelectedEntities(player);
        if(entities == null || entities.size() == 0) throw new CommandExecutionException("No selection!");

        int entityNo = (int) baseParameters.get(0);
        if(entityNo < 1 || entityNo > entities.size()) throw new CommandExecutionException("Invalid entity index!");

        Entity entity = entities.get(entityNo - 1);

        if(entity.getType() != EntityType.TEXT_DISPLAY) throw new CommandExecutionException("Entity is no itemm display!");

        TextDisplay text = (TextDisplay) entity;

        System.out.println(text.getText());

        return null;
    }
}
