package github.sanokei.oxidize.commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import github.sanokei.oxidize.Main;

public class PingCommand extends Commands{
	
	@SuppressWarnings("unused")
	private Main plugin;

	public PingCommand(Main plugin) {
		super("ping");
		this.setPlugin(plugin);
		this.addSubCommand(new SubCommands("ping.useAll"));
	}

	private void setPlugin(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	protected String setHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		try{
			if(triggerSubCommand(0, player)){
				command(Bukkit.getPlayer(args[0]));
			}
		}
		catch(Exception e){
			e.printStackTrace();
			player.sendMessage(ChatColor.RED + ""+ ChatColor.BOLD +"Target player query " + args[0] + " either does not exist or is offline.");
		}
		return onCommandParser(sender,cmd,args,true,"ping");
	}
	
	@Override
	protected boolean command(Player player) {
		  int ping;
			try {
				Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
				ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
				player.sendMessage("pong! "+ping+"ms");
				return true;
			}
			catch (Exception e) {
				player.sendMessage(ChatColor.RED + ""+ ChatColor.BOLD +"An error occured while trying to aquire ping.");
				e.printStackTrace();
			}
			return false;
		}

}
