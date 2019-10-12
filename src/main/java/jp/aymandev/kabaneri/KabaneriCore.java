package jp.aymandev.kabaneri;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import jp.aymandev.kabaneri.commands.CommandGetWeapon;
import jp.aymandev.kabaneri.events.EntityEvents;
import jp.aymandev.kabaneri.events.WeaponEvents;
import jp.aymandev.kabaneri.events.ZombieEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class KabaneriCore extends JavaPlugin implements Listener {

  private static final String[] DEPENDENCIES =
      new String[] {"HolographicDisplays", "ProtocolLib", "PacketWrapper"};
  private static KabaneriCore instance;
  private ProtocolManager protocolManager;

  /** Checking for required dependencies and registering events with instance */
  @Override
  public void onEnable() {
    for (String dependency : DEPENDENCIES) {
      if (!Bukkit.getPluginManager().isPluginEnabled(dependency)) {
        Bukkit.getConsoleSender()
            .sendMessage(
                String.format(
                    "%s[Kabaneri] *** %s is not installed or not enabled. ***",
                    ChatColor.RED, dependency));
        Bukkit.getConsoleSender()
            .sendMessage(ChatColor.RED + "[Kabaneri] *** This plugin will be disabled. ***");
        this.setEnabled(false);
        return;
      }
    }

    this.getLogger().info("Plugin enabled!");
    this.getServer().getPluginManager().registerEvents(new EntityEvents(), this);
    this.getServer().getPluginManager().registerEvents(new ZombieEvents(), this);
    this.getServer().getPluginManager().registerEvents(new WeaponEvents(), this);

    this.getCommand("getweapon").setExecutor(new CommandGetWeapon());

    KabaneriCore.instance = this;
    protocolManager = ProtocolLibrary.getProtocolManager();
  }

  @Override
  public void onDisable() {
    this.getLogger().info("Disabled");
  }

  public static KabaneriCore getInstance() {
    return KabaneriCore.instance;
  }
}
