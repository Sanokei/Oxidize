package github.sanokei.oxidize.listeners;
/*import java.io.File;*/

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
/*import org.bukkit.configuration.file.YamlConfiguration;*/
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import github.sanokei.oxidize.Main;
import github.sanokei.oxidize.files.ConfigFiles;

public class JoinListener implements Listener{
	@SuppressWarnings("unused")
	private Main plugin;
	public JoinListener(Main plugin){
		setPlugin(plugin);
	}
	
	private void setPlugin(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void OnJoin(PlayerJoinEvent event){
		FileConfiguration messageConfig = ConfigFiles.getConfigFile("messageConfig.yml");
		if(event.getPlayer().hasPlayedBefore()){
			Bukkit.broadcastMessage(
					ChatColor.translateAlternateColorCodes('&', messageConfig.getString("message-join").replace("<player>", event.getPlayer().getName()))
			);
			//NPCManager.sendPlayerNpcPackets(event.getPlayer());
		}
		else{
			Bukkit.broadcastMessage(
					ChatColor.translateAlternateColorCodes('&', messageConfig.getString("message-firstJoin").replace("<player>", event.getPlayer().getName())
					) //could make it getDisplayName();
			);
		}
	}
}
