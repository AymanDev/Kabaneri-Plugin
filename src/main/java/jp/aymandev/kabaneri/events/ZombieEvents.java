package jp.aymandev.kabaneri.events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import jp.aymandev.kabaneri.Helper;
import jp.aymandev.kabaneri.KabaneriCore;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ZombieEvents implements Listener {

  /**
   * When somewhere in world spawning zombie, creating custom hologram with custom name and health
   * indicator. Attaching hologram to zombie to follow him
   */
  @EventHandler
  public void onZombieSpawning(EntitySpawnEvent event) {
    if (event.getEntityType() != EntityType.ZOMBIE) {
      return;
    }

    Zombie zombie = (Zombie) event.getEntity();
    Hologram holo =
        HologramsAPI.createHologram(
            KabaneriCore.getInstance(), zombie.getEyeLocation().add(0d, 1d, 0d));
    holo.appendTextLine("Kabane");
    holo.appendTextLine(
        ChatColor.RED
            + "♥"
            + ChatColor.RESET
            + ChatColor.BOLD
            + " "
            + Math.round(zombie.getHealth())
            + "/"
            + Math.round(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
    Helper.attachHologramToZombie(zombie, holo);
  }

  /**
   * Zombie Burning Events Just checking combust event and canceling it if entity is zombie. Also
   * checking other combust sources with higher priority and allowing combusting if source in entity
   * or block. This is disabling entity to combust from sun but they still available to combust from
   * entity attacks or fire block
   */
  @EventHandler
  public void onZombieBurningFromSun(EntityCombustEvent event) {
    event.setCancelled(event.getEntityType() == EntityType.ZOMBIE);
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onZombieBurningFromBlock(EntityCombustByBlockEvent event) {
    if (event.getEntityType() == EntityType.ZOMBIE) {
      event.setCancelled(false);
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onZombieBurningFromEntity(EntityCombustByEntityEvent event) {
    if (event.getEntityType() == EntityType.ZOMBIE) {
      event.setCancelled(false);
    }
  }
}
