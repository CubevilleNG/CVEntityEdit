package org.cubeville.cventityedit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import org.cubeville.commons.commands.CommandParser;

import org.cubeville.cventityedit.commands.*;

public class CVEntityEdit extends JavaPlugin
{
    CommandParser commandParser;

    static CVEntityEdit instance;

    public static CVEntityEdit getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        
        commandParser = new CommandParser();

        commandParser.addCommand(new Copy());
        commandParser.addCommand(new CopyToBlockEntities());
        commandParser.addCommand(new CreateItem());
        commandParser.addCommand(new CreateText());
        commandParser.addCommand(new Filter());
        commandParser.addCommand(new GetItem());
        commandParser.addCommand(new Glow());
        commandParser.addCommand(new Info());
        commandParser.addCommand(new InsertText());
        commandParser.addCommand(new List());
        commandParser.addCommand(new Move());
        commandParser.addCommand(new MoveHere());
        commandParser.addCommand(new Scale());
        commandParser.addCommand(new Set());
        commandParser.addCommand(new Remove());
        commandParser.addCommand(new SelectNear()); 
        commandParser.addCommand(new SelectNearest()); 
        commandParser.addCommand(new SelectWE());
        commandParser.addCommand(new GetText());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("ee")) {
            return commandParser.execute(sender, args);
        }
        return false;
    }
}
