package org.cubeville.cventityedit.commands;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.util.Transformation;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.joml.Vector3f;

import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;

import org.cubeville.cventityedit.Selection;

public class Scale extends Command
{
    public Scale() {
        super("scale");
        addBaseParameter(new CommandParameterDouble());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        List<ItemDisplay> items = new ArrayList<>();
        for(Entity e: Selection.getInstance().getSelectedEntities(player))
            if(e.getType() == EntityType.ITEM_DISPLAY) items.add((ItemDisplay) e);

        if(items.size() == 0) throw new CommandExecutionException("No item display entities in selection!");
        
        float factor = (float) ((double) baseParameters.get(0));
        if(factor < 0.1f || factor > 20.0f) throw new CommandExecutionException("Invalid scaling factor! Can be in the range 0.1-20.0.");
        
        for(ItemDisplay item: items) {
            Transformation t = item.getTransformation();
            Vector3f newscale = t.getScale().mul(factor);
            Vector3f newmove = t.getTranslation().mul(factor);
            item.setTransformation(new Transformation(newmove, t.getLeftRotation(), newscale, t.getRightRotation()));
        }

        return null;
    }
}
