package org.cubeville.cventityedit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Comparator;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.TextDisplay;
import org.bukkit.Color;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Display;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Selection
{
    private static Selection instance;
    private Map<UUID, List<UUID>> entitySelection;

    private Selection() {
        entitySelection = new HashMap<>();
    }

    public static Selection getInstance() {
        if(instance == null)
            instance = new Selection();
        return instance;
    }

    private List<UUID> getEntitySelection(Player player) {
        UUID pid = player.getUniqueId();
        List<UUID> ret = entitySelection.get(pid);
        if(ret == null) {
            ret = new ArrayList<>();
            entitySelection.put(pid, ret);
        }
        return ret;
    }

    public List<Entity> getSelectedEntities(Player player) {
        List<UUID> entityIdList = getEntitySelection(player);
        List<Entity> ret = new ArrayList<>();
        for(UUID eid: entityIdList) {
            Entity e = Bukkit.getEntity(eid);
            if(e != null && e.isValid()) ret.add(e);
        }
        return ret;
    }

    public void clearEntitySelection(Player Player) {
        entitySelection.put(Player.getUniqueId(), new ArrayList<>());
    }

    public void addEntity(Player player, Entity entity) {
        getEntitySelection(player).add(entity.getUniqueId());
    }

    public void setEntitySelection(Player player, List<Entity> entities) {
        clearEntitySelection(player);
        for(Entity e: entities) addEntity(player, e);
    }

    public void flash(Player player) {
        Map<UUID, Color> savedBgColors = new HashMap<>();
        for(UUID eid: entitySelection.get(player.getUniqueId())) {
            Entity e = Bukkit.getServer().getEntity(eid);
            if(e == null || e.isValid() == false) continue;
            if(e.getType() == EntityType.TEXT_DISPLAY) {
                TextDisplay td = (TextDisplay) e;
                savedBgColors.put(e.getUniqueId(), td.getBackgroundColor());
                td.setBackgroundColor(Color.fromARGB(255, 255, 255, 255));
            }
            else {
                e.setGlowing(!e.isGlowing());
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                for(UUID eid: entitySelection.get(player.getUniqueId())) { // TODO better iterate over the map that we created, just in case the selection should happen to change meanwhile, though unlikely
                    Entity e = Bukkit.getServer().getEntity(eid);
                    if(e == null || e.isValid() == false) continue;
                    if(e.getType() == EntityType.TEXT_DISPLAY) {
                        ((TextDisplay) e).setBackgroundColor(savedBgColors.get(e.getUniqueId()));
                    }
                    else {
                        e.setGlowing(!e.isGlowing());
                    }
                }
            }
        }.runTaskLater(CVEntityEdit.getInstance(), 10L);
    }
    
    public void sort(Player player) {
        List<UUID> entities = entitySelection.get(player.getUniqueId());
        if(entities == null) return;

        entities.sort(new Comparator<UUID>() {
                @Override
                public int compare(UUID id1, UUID id2) {
                    Entity e1 = Bukkit.getEntity(id1);
                    Entity e2 = Bukkit.getEntity(id2);

                    boolean e1valid = e1 != null && e1.isValid();
                    boolean e2valid = e2 != null && e2.isValid();

                    if(e1valid == false && e2valid == false) return id1.compareTo(id2);
                    if(e1valid == false) return -1;
                    if(e2valid == false) return 1;
 
                    if(e1 instanceof Display && ! (e2 instanceof Display)) return -1;
                    if(e2 instanceof Display && ! (e1 instanceof Display)) return 1;

                    if(e1.getType() == EntityType.ITEM_DISPLAY && e2.getType() != EntityType.ITEM_DISPLAY) return -1;
                    if(e1.getType() != EntityType.ITEM_DISPLAY && e2.getType() == EntityType.ITEM_DISPLAY) return 1;

                    Double y1 = e1.getLocation().getY();
                    if(e1 instanceof Display) y1 += ((Display) e1).getTransformation().getTranslation().y;
                    Double y2 = e2.getLocation().getY();
                    if(e2 instanceof Display) y2 += ((Display) e2).getTransformation().getTranslation().y;

                    if(y1 != y2)
                        return y2.compareTo(y1);

                    if(e1 instanceof ItemDisplay && e2 instanceof ItemDisplay) {
                        String mat1 = ((ItemDisplay) e1).getItemStack().getType().toString();
                        String mat2 = ((ItemDisplay) e2).getItemStack().getType().toString();
                        
                        if(!mat1.equals(mat2))
                            return mat1.compareTo(mat2);
                    }
                    
                    Double x1 = e1.getLocation().getX() + e1.getLocation().getZ();
                    if(e1 instanceof Display) x1 += ((Display) e1).getTransformation().getTranslation().x + ((Display) e1).getTransformation().getTranslation().z;
                    Double x2 = e2.getLocation().getX() + e2.getLocation().getZ();
                    if(e2 instanceof Display) x2 += ((Display) e2).getTransformation().getTranslation().x + ((Display) e2).getTransformation().getTranslation().z;

                    return x1.compareTo(x2);
                }                
            });
    }
    
}
