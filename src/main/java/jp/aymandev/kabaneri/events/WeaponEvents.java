package jp.aymandev.kabaneri.events;

import jp.aymandev.kabaneri.Helper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WeaponEvents implements Listener {

  /**
   * When player interacts with item (DIAMOND SWORD) with right click spawning custom snowball which
   * imitating bullet
   */
  @EventHandler
  public void onPlayerItemUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (player.getInventory().getItemInMainHand().getType() != Material.DIAMOND_SWORD) {
      return;
    }

    switch (event.getAction()) {
      case RIGHT_CLICK_AIR:
      case RIGHT_CLICK_BLOCK:
        World world = player.getWorld();
        Location eyeLoc = player.getEyeLocation();
        Snowball snowball = (Snowball) world.spawnEntity(eyeLoc, EntityType.SNOWBALL);
        snowball.setVelocity(eyeLoc.getDirection().multiply(3.0d));
        snowball.setCustomName(Helper.BULLET_NAME_TAG);
        snowball.setInvulnerable(true);
        world.playSound(eyeLoc, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.5f, 1.0f);
        break;
    }
  }

  /**
   * There's checking if damager is our custom snowball and dealing custom damage, and playing sound
   * effect
   */
  @EventHandler
  public void onSnowballHitEntity(EntityDamageByEntityEvent event) {
    Entity damager = event.getDamager();
    Entity target = event.getEntity();
    if (damager.getCustomName() != null
        && damager.getCustomName().equals(Helper.BULLET_NAME_TAG)
        && target instanceof LivingEntity) {
      LivingEntity livingEntity = (LivingEntity) target;
      livingEntity.damage(Helper.BULLET_DAMAGE, damager);
      target.getWorld().playSound(target.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.15f, 1.0f);

      damager.remove();
      event.setCancelled(true);
    }
  }
}
