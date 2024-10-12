package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;


public class CreateText extends Command
{
    public CreateText() {
        super("create text");
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        Location location = player.getLocation();
        location.setYaw((location.getYaw() + 180.0f) % 180);
        
        TextDisplay entity = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        entity.setText("Hello §4World!");
        entity.setAlignment(TextDisplay.TextAlignment.CENTER);
        entity.setBackgroundColor(Color.fromRGB(0, 0, 255)); // White background
        entity.setTextOpacity((byte) 255); // Fully opaque
        entity.setSeeThrough(true);
        entity.setShadowed(false);
        
        return new CommandResponse("Text created");
    }

}
