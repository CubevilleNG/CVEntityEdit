package org.cubeville.cventityedit.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.util.Vector;
import org.bukkit.util.Transformation;

import org.joml.Vector3f;
import org.joml.AxisAngle4f;

import org.cubeville.commons.commands.CommandParameterListDouble;
import org.cubeville.commons.commands.CommandParameterVector;
import org.cubeville.commons.commands.CommandParameterEnum;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;


public class Set extends Command
{
    public Set() {
        super("set");
        addParameter("itemtransform", true, new CommandParameterEnum(ItemDisplay.ItemDisplayTransform.class));
        addParameter("move", true, new CommandParameterVector());
        addParameter("scale", true, new CommandParameterVector());
        addParameter("leftrot", true, new CommandParameterListDouble(4, ","));
        addParameter("rightrot", true, new CommandParameterListDouble(4, ","));
        addParameter("orientation", true, new CommandParameterListDouble(2, ","));
        CommandUtils.addNoParameter(this);
    }

    @Override
    public CommandResponse execute(Player player, java.util.Set<String> flags, Map<String, Object> parameters, java.util.List<Object> baseParameters)
        throws CommandExecutionException {

        List<Entity> entities = CommandUtils.filterSelectedEntitiesByNoParameter(player, parameters);

        for(Entity e: entities) {

            if(parameters.containsKey("orientation")) {
                List<Double> yp = (List<Double>) parameters.get("orientation");
                Location loc = e.getLocation();
                loc.setYaw(yp.get(0).floatValue());
                loc.setPitch(yp.get(1).floatValue());
                e.teleport(loc);
            }

            if(e.getType() == EntityType.ITEM_DISPLAY) {
                ItemDisplay item = (ItemDisplay) e;
                if(parameters.containsKey("itemtransform"))
                    item.setItemDisplayTransform((ItemDisplay.ItemDisplayTransform) parameters.get("itemtransform"));
            }

            if(e instanceof Display) {
                Display d = (Display) e;

                Vector3f move = d.getTransformation().getTranslation();
                AxisAngle4f rotLeft = new AxisAngle4f(d.getTransformation().getLeftRotation());
                Vector3f scale = d.getTransformation().getScale();
                AxisAngle4f rotRight = new AxisAngle4f(d.getTransformation().getRightRotation());
                boolean transchanged = false;

                if(parameters.containsKey("scale")) {
                    Vector sp = (Vector) parameters.get("scale");
                    scale = new Vector3f((float) sp.getX(), (float) sp.getY(), (float) sp.getZ());
                    transchanged = true;
                }

                if(parameters.containsKey("leftrot")) {
                    List<Double> rp = (List<Double>) parameters.get("leftrot");
                    rotLeft = new AxisAngle4f((float) Math.toRadians(rp.get(0)), rp.get(1).floatValue(), rp.get(2).floatValue(), rp.get(3).floatValue());
                    transchanged = true;
                }

                if(parameters.containsKey("move")) {
                    Vector sp = (Vector) parameters.get("move");
                    move = new Vector3f((float) sp.getX(), (float) sp.getY(), (float) sp.getZ());
                    transchanged = true;
                }

                if(parameters.containsKey("rightrot")) {
                    List<Double> rp = (List<Double>) parameters.get("rightrot");
                    rotRight = new AxisAngle4f((float) Math.toRadians(rp.get(0)), rp.get(1).floatValue(), rp.get(2).floatValue(), rp.get(3).floatValue());
                    transchanged = true;
                }

                if(transchanged)
                    d.setTransformation(new Transformation(move, rotLeft, scale, rotRight));
            }
        }

        return null;
    }
    
}
