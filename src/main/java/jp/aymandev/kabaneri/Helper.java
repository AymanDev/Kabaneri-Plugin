package jp.aymandev.kabaneri;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

public class Helper {

  /** Constants for bullet name tag and damage of the bullet */
  public static final String BULLET_NAME_TAG = "BULLET TAG";

  public static final double BULLET_DAMAGE = 5.0d;

  /**
   * Updating position of the hologram to zombie's position. Also updating health line if zombie
   * health was changed
   *
   * @param zombie Zombie which have hologram
   * @param hologram Hologram which follow zombie each second
   */
  public static void attachHologramToZombie(Zombie zombie, Hologram hologram) {
    new BukkitRunnable() {
      double prevHealth = 0d;

      @Override
      public void run() {
        hologram.teleport(zombie.getEyeLocation().add(0d, 1d, 0.d));

        if (prevHealth != zombie.getHealth()) {
          hologram.removeLine(1);
          hologram.insertTextLine(
              1,
              ChatColor.RED
                  + "♥"
                  + ChatColor.RESET
                  + " "
                  + zombie.getHealth()
                  + "/"
                  + zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
          prevHealth = zombie.getHealth();
        }

        if (zombie.isDead()) {
          hologram.delete();
          cancel();
        }
      }
    }.runTaskTimer(KabaneriCore.getInstance(), 1L, 1L);
  }
}
