package jp.aymandev.kabaneri.items.weapons;

import jp.aymandev.kabaneri.KabaneriCore;
import org.bukkit.ChatColor;
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
  public static final NamespacedKey WEAPON_KEY =
      new NamespacedKey(KabaneriCore.getInstance(), "weapon");

  /**
   * Generating ItemStack for weapon
   *
   * @return Generated weapon ItemStack
   */
  public static ItemStack createWeapon(ItemWeapon itemWeapon) {
    ItemStack itemStack = new ItemStack(itemWeapon.getMaterial());
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return null;
    }

    itemMeta.setDisplayName(ChatColor.RESET + itemWeapon.getName());
    List<String> lore = new ArrayList<>();
    lore.add("Ammo: " + itemWeapon.getMaxAmmo());
    itemMeta.setLore(lore);
    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
    itemMeta.setUnbreakable(true);
    itemMeta.setCustomModelData(itemWeapon.getModelId());

    // Building custom data, for ammo
    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    dataContainer.set(WEAPON_KEY, PersistentDataType.STRING, itemWeapon.getItemTagName());
    dataContainer.set(AMMO_KEY, PersistentDataType.INTEGER, itemWeapon.getMaxAmmo());

    itemStack.setItemMeta(itemMeta);
    return itemStack;
  }

  /**
   * Decreasing current weapon ammo value
   *
   * @param itemStack ItemStack which have to decrease ammo
   */
  public static void decreaseAmmo(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return;
    }

    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    if (!dataContainer.has(AMMO_KEY, PersistentDataType.INTEGER)) {
      return;
    }

    int ammo = dataContainer.get(AMMO_KEY, PersistentDataType.INTEGER);
    dataContainer.set(AMMO_KEY, PersistentDataType.INTEGER, --ammo);

    List<String> lore = new ArrayList<>();
    lore.add("Ammo: " + ammo);
    itemMeta.setLore(lore);

    itemStack.setItemMeta(itemMeta);
  }

  /**
   * Reloading current weapon
   *
   * @param itemStack ItemStack which have to reload
   */
  public static void reloadWeapon(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return;
    }

    ItemWeapon itemWeapon = WeaponList.getItem(itemStack);
    if (itemWeapon == null) {
      return;
    }

    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    if (!dataContainer.has(AMMO_KEY, PersistentDataType.INTEGER)) {
      return;
    }

    dataContainer.set(AMMO_KEY, PersistentDataType.INTEGER, itemWeapon.getMaxAmmo());

    List<String> lore = new ArrayList<>();
    lore.add("Ammo: " + itemWeapon.getMaxAmmo());
    itemMeta.setLore(lore);

    itemStack.setItemMeta(itemMeta);
  }

  /**
   * Use this method for receiving current ammo in weapon
   *
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
