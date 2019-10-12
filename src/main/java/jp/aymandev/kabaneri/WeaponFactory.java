package jp.aymandev.kabaneri;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WeaponFactory {
  private static final String AMMO_WEAPON_TAG = "ammo";
  private static final NamespacedKey AMMO_KEY =
      new NamespacedKey(KabaneriCore.getInstance(), AMMO_WEAPON_TAG);
  private static final NamespacedKey WEAPON_KEY =
      new NamespacedKey(KabaneriCore.getInstance(), "weapon");

  /**
   * Generating ItemStack for weapon
   * @return Generated weapon ItemStack
   */
  public static ItemStack createWeapon() {
    ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD);
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return null;
    }

    itemMeta.setDisplayName(ChatColor.RESET + "Steam Gun");
    List<String> lore = new ArrayList<>();
    lore.add("Ammo: " + 30);
    itemMeta.setLore(lore);
    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
    itemMeta.setUnbreakable(true);

    // Building custom data, for ammo
    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    dataContainer.set(WEAPON_KEY, PersistentDataType.INTEGER, 1);
    dataContainer.set(AMMO_KEY, PersistentDataType.INTEGER, 30);

    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  /**
   * Decreasing current weapon ammo value
   * @param itemStack ItemStack which have to decrease ammo
   * @return Updated ItemStack. This is optionally because ItemStack immutable
   */
  public static ItemStack decreaseAmmo(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return itemStack;
    }

    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    if (!dataContainer.has(AMMO_KEY, PersistentDataType.INTEGER)) {
      return itemStack;
    }

    int ammo = dataContainer.get(AMMO_KEY, PersistentDataType.INTEGER);
    dataContainer.set(AMMO_KEY, PersistentDataType.INTEGER, --ammo);

    List<String> lore = new ArrayList<>();
    lore.add("Ammo: " + ammo);
    itemMeta.setLore(lore);

    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }


  /**
   * Reloading current weapon
   * @param itemStack ItemStack which have to reload
   * @return Updated ItemStack. This is optionally because ItemStack immutable
   */
  public static ItemStack reloadWeapon(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return itemStack;
    }

    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    if (!dataContainer.has(AMMO_KEY, PersistentDataType.INTEGER)) {
      return itemStack;
    }

    dataContainer.set(AMMO_KEY, PersistentDataType.INTEGER, 30);

    List<String> lore = new ArrayList<>();
    lore.add("Ammo: " + 30);
    itemMeta.setLore(lore);

    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  /**
   * Use this method for receiving current ammo in weapon
   * @param itemStack ItemStack which have to get current ammo state
   * @return Ammo count
   */
  public static int getAmmoInWeapon(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return -1;
    }

    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    if (!dataContainer.has(AMMO_KEY, PersistentDataType.INTEGER)) {
      return -1;
    }
    return dataContainer.get(AMMO_KEY, PersistentDataType.INTEGER);
  }
}
