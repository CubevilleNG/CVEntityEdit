package org.cubeville.cventityedit.commands;

import java.util.ArrayList;
import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import org.cubeville.commons.utils.ColorUtils;
import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class InsertText extends Command
{
    public InsertText() {
        super("text");
        addBaseParameter(new CommandParameterInteger()); // entity no
        addOptionalBaseParameter(new CommandParameterString());  // text to add
        addParameter("insert", true, new CommandParameterInteger());
        addParameter("append", true, new CommandParameterInteger());
        addParameter("replace", true, new CommandParameterInteger());
        addParameter("remove", true, new CommandParameterInteger());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        int entityno = (Integer) baseParameters.get(0);
        String addtext = "";
        if(baseParameters.size() == 2)
            addtext = ColorUtils.addColor((String) baseParameters.get(1));

        if(entityno < 1 || entityno > Selection.getInstance().getSelectedEntities(player).size())
            throw new CommandExecutionException("Not a valid entity no!");
        
        Entity entity = Selection.getInstance().getSelectedEntities(player).get(entityno - 1);
        if(entity.getType() != EntityType.TEXT_DISPLAY) throw new CommandExecutionException("Entity is not a text display!");

        List<String> text = new ArrayList<>();
        for(String s: Arrays.asList(((TextDisplay) entity).getText().split("\\n")))
            text.add(s);

        if(parameters.containsKey("insert")) {
            int lineno = (int) parameters.get("insert");
            if(lineno < 1 || lineno > text.size()) throw new CommandExecutionException("Invalid line no");
            text.add(lineno - 1, addtext);
        }
        else if(parameters.containsKey("append")) {
            int lineno = (int) parameters.get("append");
            if(lineno < 1 || lineno > text.size()) throw new CommandExecutionException("Invalid line no");
            text.set(lineno - 1, text.get(lineno) + addtext);
        }
        else if(parameters.containsKey("replace")) {
            int lineno = (int) parameters.get("replace");
            if(lineno < 1 || lineno > text.size()) throw new CommandExecutionException("Invalid line no");
            text.set(lineno - 1, addtext);
        }
        else if(parameters.containsKey("remove")) {
            int lineno = (int) parameters.get("remove");
            if(lineno < 1 || lineno > text.size()) throw new CommandExecutionException("Invalid line no");
            text.remove(lineno - 1);
        }
        else {
            text.add(addtext);
        }

        String newtext = String.join("\n", text);
        ((TextDisplay) entity).setText(newtext);

        return null;
    }
}
