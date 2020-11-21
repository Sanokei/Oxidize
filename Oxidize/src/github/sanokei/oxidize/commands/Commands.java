package github.sanokei.oxidize.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Commands implements CommandExecutor{
	private HashMap<Integer,SubCommands> subcommandMap = new HashMap<Integer,SubCommands>();
	private String commandName;
	public final String TOO_MANY_ARGUMENTS = ChatColor.RED + ""+ ChatColor.BOLD +"Command "+commandName+" had too many arguments";
	public final String NO_PERMISSION = ChatColor.RED + ""+ ChatColor.BOLD +"You do not have permission to execute the "+commandName+" command.";
	public final String ONLY_PLAYERS_ALLOWED_TO_EXECUTE = ChatColor.RED + ""+ ChatColor.BOLD + "Only players may execute the "+commandName+" command. Hi console!";
	
	public Commands(String commandName){
		super();
		this.commandName = commandName;
	}
	
	public void addSubCommand(int indexArgs, SubCommands command){
		subcommandMap.put(indexArgs, command);
	}
	public void addSubCommand(SubCommands command){
		subcommandMap.put(subcommandMap.size(), command);
	}
	public void removeSubCommand(int indexArgs, String subCommandIndex){
		subcommandMap.remove(indexArgs);
	}
	public boolean triggerSubCommand(int index, Player player){
		if(player.hasPermission(subcommandMap.get(index).getPerms())){
			return true;
		}
		return false;
	}
	
	protected abstract String setHelp();
	protected abstract boolean command(Player player);
	protected String getHelp(){
		return setHelp();
	}
	protected boolean onCommandParser(CommandSender sender, Command cmd, String[] args, boolean playerOnly, String permission){
		if(!cmd.getName().equalsIgnoreCase(commandName)){
			return false;
		}
		
		if(playerOnly && !(sender instanceof Player)){
			sender.sendMessage(ONLY_PLAYERS_ALLOWED_TO_EXECUTE);
			return false;
		}
		Player player = (Player)sender;
		if(args.length > subcommandMap.size()){
			player.sendMessage(TOO_MANY_ARGUMENTS);
			return false;
		}
		if(!player.hasPermission(permission)){
			player.sendMessage(NO_PERMISSION);
			return false;
		}
		
		//
		
		if(subcommandMap.isEmpty() || args.length == 0){
			command(player);
			return false;
		}
	return true;
	}
}

