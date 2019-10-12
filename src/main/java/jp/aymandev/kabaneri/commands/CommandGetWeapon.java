package jp.aymandev.kabaneri.commands;

import jp.aymandev.kabaneri.WeaponFactory;
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
    player.getInventory().setItemInMainHand(WeaponFactory.createWeapon());

    sender.sendMessage(ChatColor.GREEN + "Gived weapons!");
    return true;
  }
}
