package jp.aymandev.kabaneri.commands;

import jp.aymandev.kabaneri.items.weapons.ItemWeapon;
import jp.aymandev.kabaneri.items.weapons.WeaponFactory;
import jp.aymandev.kabaneri.items.weapons.WeaponList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGetWeapon implements CommandExecutor {
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "You can use this command only in game!");
      return true;
    }
    Player player = (Player) sender;

    if (args.length >= 1) {
      String weaponKey = String.join(" ", args).replaceAll(" ", "_").toUpperCase();
      ItemWeapon itemWeapon = WeaponList.getItemByKey(weaponKey);
      if (itemWeapon == null) {
        sender.sendMessage(
            ChatColor.RED + String.format("Weapon with name %s not found!", weaponKey));
        return true;
      }

      player.getInventory().setItemInMainHand(WeaponFactory.createWeapon(itemWeapon));
      sender.sendMessage(ChatColor.GREEN + String.format("Gived weapon %s!", itemWeapon.getName()));
      return true;
    }

    sender.sendMessage(ChatColor.RED + "Wrong command usage! Use: /<gw|getweapon> <weapon key>");
    return true;
  }
}
