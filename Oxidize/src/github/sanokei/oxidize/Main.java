package github.sanokei.oxidize;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import github.sanokei.oxidize.commands.NPCCommand;
import github.sanokei.oxidize.commands.PingCommand;
import github.sanokei.oxidize.listeners.JoinListener;
import net.minecraft.server.v1_16_R2.EntityPlayer;

public class Main extends JavaPlugin{
	
	private static final HashMap<UUID,Player> playerMenuUtilityMap = new HashMap<>();
	private static final HashMap<String,File> configFileMap = new HashMap<>();
	private static final HashMap<UUID,EntityPlayer> NPC = new HashMap<>();
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		this.getCommand("ping").setExecutor(new PingCommand(this));
		this.getCommand("npc").setExecutor(new NPCCommand());
		Bukkit.getPluginManager().registerEvents(new JoinListener(this),this);
		//TODO read config of NPCs
		
	}
	
	public static void checkPlayer(Player player){
		if(!(playerMenuUtilityMap.containsKey(player.getUniqueId()))){
			playerMenuUtilityMap.put(player.getUniqueId(), player);
		}
	}

	public static HashMap<UUID,EntityPlayer> getNPCList() {
		return NPC;
	}

	public static HashMap<String, File> getConfigFileMap() {
		return configFileMap;
	}
	public static void addConfigFileMap(String filename, File file){
		configFileMap.put(filename, file);
	}
	
	
}