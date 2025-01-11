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

import org.cubeville.commons.utils.ColorUtils;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class CreateText extends Command
{
    public CreateText() {
        super("create text");
        addBaseParameter(new CommandParameterString());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        Location location = player.getLocation();

        {
            float yaw = location.getYaw();
            yaw += 180.0f;
            if(yaw >= 360.0f) yaw -= 360.0f;
            location.setYaw(yaw);
            location.setPitch(0);
        }
        
        {
            float yaw = (float) Math.toRadians(player.getLocation().getYaw());
            double ox = -4 * Math.sin(yaw);
            double oz = 4 * Math.cos(yaw);
            location.add(ox, 1.5, oz);
        }
        
        String text = ColorUtils.addColor((String) baseParameters.get(0));
        text = text.replace("\\n", "\n");
       
        TextDisplay entity = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        entity.setText(text);
        entity.setAlignment(TextDisplay.TextAlignment.CENTER);
        entity.setBackgroundColor(Color.fromRGB(0, 0, 255)); // White background
        entity.setTextOpacity((byte) 255); // Fully opaque
        entity.setSeeThrough(false);
        entity.setShadowed(true);

        Selection.getInstance().clearEntitySelection(player);
        Selection.getInstance().addEntity(player, entity);
        
        return new CommandResponse("Text created");
    }

}
