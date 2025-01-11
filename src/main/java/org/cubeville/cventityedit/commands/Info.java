package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.Map;

import org.bukkit.entity.TextDisplay;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;

import org.joml.Vector3f;
import org.joml.AxisAngle4f;

import org.cubeville.commons.commands.CommandParameterInteger;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class Info extends Command
{
    public Info() {
        super("info");
        addOptionalBaseParameter(new CommandParameterInteger());
        addParameter("rel", true, new CommandParameterInteger());
    }

    private String coordinateMessage(String label, double X, double Y, double Z) {
        return label + (Math.round(X * 100) / 100.0) + " / " + (Math.round(Y * 100) / 100.0) + " / " + (Math.round(Z * 100) / 100.0);
    }
                            
    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, java.util.List<Object> baseParameters)
        throws CommandExecutionException {

        java.util.List<Entity> entities = Selection.getInstance().getSelectedEntities(player);
        if(entities == null || entities.size() == 0) throw new CommandExecutionException("No selection.");

        int entityNo = 1;
        if(baseParameters.size() > 0)
            entityNo = (int) baseParameters.get(0);
        
        if(entityNo < 1 || entityNo > entities.size()) throw new CommandExecutionException("Invalid entity index.");

        Entity entity = entities.get(entityNo - 1);

        CommandResponse response = new CommandResponse();

        response.addMessage("UUID: " + entity.getUniqueId());

        if(entity.getType() == EntityType.ITEM_DISPLAY) {
            ItemDisplay item = (ItemDisplay) entity;
            response.addMessage("Item: " + item.getItemStack().getType());
            response.addMessage("Transform: " + item.getItemDisplayTransform());
        }

        else if(entity.getType() == EntityType.TEXT_DISPLAY) {
            TextDisplay text = (TextDisplay) entity;
            response.addMessage("Text: " + text.getText());
        }
        
        else {
            response.addMessage("Unknown entity type");
        }

        response.addMessage(coordinateMessage("XYZ: ", entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ()));
        response.addMessage("Yaw , Pitch: " + (Math.round(entity.getLocation().getYaw() * 10) / 10) + ", " + (Math.round(entity.getLocation().getPitch() * 10) / 10));
        
        if(parameters.containsKey("rel")) {
            int rel = (int) parameters.get("rel");
            if(rel < 1 || rel > entities.size()) throw new CommandExecutionException("rel parameters inavlid!");
            Location relloc = entities.get(rel - 1).getLocation();
            response.addMessage(coordinateMessage("Rel: ", entity.getLocation().getX() - relloc.getX(), entity.getLocation().getY() - relloc.getY(), entity.getLocation().getZ() - relloc.getZ()));
        }
        
        if(entity instanceof Display) {
            Transformation t = ((Display) entity).getTransformation();

            Vector3f move = t.getTranslation();
            if(move.x != 0.0f || move.y != 0.0f || move.z != 0.0f)
                response.addMessage(coordinateMessage("Move: ", move.x, move.y, move.z));

            AxisAngle4f rotateLeft = new AxisAngle4f(t.getLeftRotation());
            if(rotateLeft.angle != 0.0f)
                response.addMessage(coordinateMessage("Rotate left: " + Math.round(Math.toDegrees(rotateLeft.angle)) + " degrees, ", rotateLeft.x, rotateLeft.y, rotateLeft.z));

            Vector3f scale = t.getScale();
            if(scale.x != 1.0f || scale.y != 1.0f || scale.z != 1.0f)
                response.addMessage(coordinateMessage("Scale: ", scale.x, scale.y, scale.z));

            AxisAngle4f rotateRight = new AxisAngle4f(t.getRightRotation());
            if(rotateRight.angle != 0.0f)
                response.addMessage(coordinateMessage("Rotate right: " + Math.round(Math.toDegrees(rotateRight.angle)) + " degrees, ", rotateRight.x, rotateRight.y, rotateRight.z));
        }

        return response;
    }

}
