package org.cubeville.cventityedit;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import org.cubeville.commons.commands.CommandParser;

import org.cubeville.cventityedit.commands.*;

public class CVEntityEdit extends JavaPlugin
{
    CommandParser commandParser;

    public void onEnable() {
        commandParser = new CommandParser();

        commandParser.addCommand(new Copy());
        commandParser.addCommand(new CopyToBlockEntities());
        commandParser.addCommand(new CreateItem());
        commandParser.addCommand(new CreateText());
        commandParser.addCommand(new GetItem());
        commandParser.addCommand(new Glow());
        commandParser.addCommand(new Info());
        commandParser.addCommand(new List());
        commandParser.addCommand(new Move());
        commandParser.addCommand(new Scale());
        commandParser.addCommand(new Set());
        commandParser.addCommand(new Remove());
        commandParser.addCommand(new SelectNear()); 
        commandParser.addCommand(new SelectWE());
   }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("ee")) {
            return commandParser.execute(sender, args);
        }
        return false;
    }
}
