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
import org.bukkit.event.Listener;
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
    holo.appendTextLine("Very Cool Zombie");
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
}
