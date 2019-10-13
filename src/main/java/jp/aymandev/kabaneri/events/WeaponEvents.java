package jp.aymandev.kabaneri.events;

import jp.aymandev.kabaneri.Helper;
import jp.aymandev.kabaneri.items.weapons.ItemWeapon;
import jp.aymandev.kabaneri.items.weapons.WeaponFactory;
import jp.aymandev.kabaneri.items.weapons.WeaponList;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponEvents implements Listener {

  /**
   * When player interacts with item (DIAMOND SWORD) with right click spawning custom snowball which
   * imitating bullet
   */
  @EventHandler
  public void onPlayerItemUse(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    World world = player.getWorld();
    ItemStack heldItemStack = player.getInventory().getItemInMainHand();

    ItemWeapon itemWeapon = WeaponList.getItem(heldItemStack);
    if (itemWeapon == null) {
      return;
    }

    switch (event.getAction()) {
      case LEFT_CLICK_AIR:
      case LEFT_CLICK_BLOCK:
        {
          WeaponFactory.reloadWeapon(heldItemStack);
          world.playSound(player.getLocation(), Sound.BLOCK_WOODEN_DOOR_OPEN, 1f, 1f);

          int ammo = WeaponFactory.getAmmoInWeapon(heldItemStack);
          WeaponEvents.sendCurrentAmmoMessage(ammo, player);
          event.setCancelled(true);
        }
        break;

      case RIGHT_CLICK_AIR:
      case RIGHT_CLICK_BLOCK:
        {
          int ammo = WeaponFactory.getAmmoInWeapon(heldItemStack);
          if (ammo <= 0) {
            WeaponFactory.reloadWeapon(heldItemStack);
            world.playSound(player.getLocation(), Sound.BLOCK_WOODEN_DOOR_OPEN, 1f, 1f);
            ammo = WeaponFactory.getAmmoInWeapon(heldItemStack);
            WeaponEvents.sendCurrentAmmoMessage(ammo, player);
            event.setCancelled(true);
            return;
          }

          WeaponFactory.decreaseAmmo(heldItemStack);
          Location eyeLoc = player.getEyeLocation();
          Snowball snowball = (Snowball) world.spawnEntity(eyeLoc, EntityType.SNOWBALL);
          snowball.setVelocity(eyeLoc.getDirection().multiply(3.0d));
          snowball.setCustomName(Helper.BULLET_NAME_TAG);
          snowball.setInvulnerable(true);
          world.playSound(eyeLoc, Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 0.5f, 1.0f);

          WeaponEvents.sendCurrentAmmoMessage(--ammo, player);
        }
        break;
    }
    event.setCancelled(true);
  }

  private static void sendCurrentAmmoMessage(int ammo, Player player) {
    ComponentBuilder componentBuilder = new ComponentBuilder("[ Ammo: ");
    componentBuilder.append(String.valueOf(ammo)).append(" ]");
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, componentBuilder.create());
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
