package jp.aymandev.kabaneri.items.weapons;

import org.bukkit.Material;

public class ItemWeapon {

  private String itemTagName;
  private Material material;
  private String name;
  private int maxAmmo;
  private int shootDelay;
  private int reloadTime;
  private int modelId;

  public ItemWeapon(
      String itemTagName, Material material, int itemDuration, String name, int maxAmmo) {
    this.itemTagName = itemTagName;
    this.material = material;
    this.name = name;
    this.maxAmmo = maxAmmo;
    this.shootDelay = 0;
    this.reloadTime = 0;
    this.modelId = 0;

    WeaponList.addItem(this);
  }

  public String getItemTagName() {
    return itemTagName;
  }

  public Material getMaterial() {
    return material;
  }


  public String getName() {
    return name;
  }

  public int getMaxAmmo() {
    return maxAmmo;
  }

  public int getShootDelay() {
    return shootDelay;
  }

  public ItemWeapon setShootDelay(int shootDelay) {
    this.shootDelay = shootDelay;
    return this;
  }

  public int getReloadTime() {
    return reloadTime;
  }

  public ItemWeapon setReloadTime(int reloadTime) {
    this.reloadTime = reloadTime;
    return this;
  }

  public int getModelId() {
    return modelId;
  }

  public ItemWeapon setModelId(int modelId) {
    this.modelId = modelId;
    return this;
  }
}
