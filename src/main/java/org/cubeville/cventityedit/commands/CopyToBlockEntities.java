package org.cubeville.cventityedit.commands;

import java.util.Set;
import java.util.List;
import java.util.Map;

import org.cubeville.commons.commands.CommandParameterFloat;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;

import org.joml.Vector3f;
import org.joml.AxisAngle4f;

import com.sk89q.worldedit.bukkit.*;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.IncompleteRegionException;

import org.cubeville.cventityedit.Selection;

public class CopyToBlockEntities extends Command
{
    public CopyToBlockEntities() {
        super("copytobe");
        addBaseParameter(new CommandParameterFloat());
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) 
        throws CommandExecutionException {
        
        float scale = (float)baseParameters.get(0);
        
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        Region selection;
        try {
            selection = worldEdit.getSession(player).getSelection(worldEdit.getSession(player).getSelectionWorld());
        }
        catch(IncompleteRegionException e) {
            throw new IllegalArgumentException("Region selection incomplete.");
        }
        if(selection == null) throw new IllegalArgumentException("No region selected.");

        World world = BukkitAdapter.adapt(worldEdit.getSession(player).getSelectionWorld());
        BlockVector3 min = selection.getMinimumPoint();
        BlockVector3 max = selection.getMaximumPoint();

        int xOffset = player.getLocation().getBlockX();
        int yOffset = player.getLocation().getBlockY();
        int zOffset = player.getLocation().getBlockZ();

        int xcnt = 0;
        for(int x = min.getX(); x <= max.getX(); x++) {
            int zcnt = 0;
            for(int z = min.getZ(); z <= max.getZ(); z++) {
                int ycnt = 0;
                for(int y = min.getY(); y <= max.getY(); y++) {
                    Block block = world.getBlockAt(x, y, z);
                    if(block.getType() != Material.AIR) {
                        BlockData blockData = block.getBlockData();
                        float xpos = xcnt * scale + xOffset;
                        float ypos = ycnt * scale + yOffset;
                        float zpos = zcnt * scale + zOffset;
                        Entity entity = world.spawnEntity(new Location(world, xpos, ypos, zpos), EntityType.BLOCK_DISPLAY);
                        Selection.getInstance().addEntity(player, entity);
                        BlockDisplay blockDisplay = (BlockDisplay) entity;
                        blockDisplay.setBlock(blockData);
                        if(scale != 1.0) {
                            Transformation transformation = new Transformation(new Vector3f(0f, 0f, 0f), new AxisAngle4f(0f, 0f, 0f, 0f), new Vector3f(scale, scale, scale), new AxisAngle4f(0f, 0f, 0f, 0f));
                            blockDisplay.setTransformation(transformation);
                        }
                    }
                    ycnt++;
                }
                zcnt++;
            }
            xcnt++;
        }

        return null;
    }

    
}
