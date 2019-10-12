package jp.aymandev.kabaneri.events;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityEvents implements Listener {

  /** If any entity take damage, spawning particles and playing sound effect */
  @EventHandler
  public void onEntityDamaged(EntityDamageEvent event) {
    Entity target = event.getEntity();
    World world = target.getWorld();
    world.spawnParticle(
        Particle.BLOCK_CRACK,
        target.getLocation().add(0d, 0.5d, 0d),
        40,
        Material.RED_WOOL.createBlockData());
    world.playSound(target.getLocation(), Sound.BLOCK_STONE_BREAK, 0.5f, 1.0f);
  }
}
