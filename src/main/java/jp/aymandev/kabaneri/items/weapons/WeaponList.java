package jp.aymandev.kabaneri.items.weapons;

import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class WeaponList {

  public static ItemWeapon STEAM_RIFLE;

  public WeaponList() {
    WeaponList.STEAM_RIFLE =
        new ItemWeapon("STEAM_RIFLE", Material.DIAMOND_SWORD, 1, "Steam Gun", 10)
            .setReloadTime(5)
            .setShootDelay(2)
            .setModelId(1);
  }

  private static Map<String, ItemWeapon> ITEMS_LIST = Maps.newHashMap();

  public static void addItem(ItemWeapon itemWeapon) {
    WeaponList.ITEMS_LIST.put(itemWeapon.getItemTagName(), itemWeapon);
  }

  public static ItemWeapon getItemByKey(String weaponKey) {
    if (!WeaponList.ITEMS_LIST.containsKey(weaponKey)) {
      return null;
    }

    return WeaponList.ITEMS_LIST.get(weaponKey);
  }

  public static ItemWeapon getItem(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return null;
    }

    PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
    if (!dataContainer.has(WeaponFactory.WEAPON_KEY, PersistentDataType.STRING)) {
      return null;
    }

    String weaponKey = dataContainer.get(WeaponFactory.WEAPON_KEY, PersistentDataType.STRING);
    return getItemByKey(weaponKey);
  }
}
