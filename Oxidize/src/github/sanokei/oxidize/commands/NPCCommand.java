package github.sanokei.oxidize.commands;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

/* WARNING
 * THIS IS MESSY LEGACY CODE JUST DONT BOTHER MESSING WITH IT
 * IT WILL TAKE MORE TIME TO FIX
 * IF IT WORK DONT FUCKING WASTE YOUR TIME
 * I THINK THATS HOW THE SAYING GOES
 */

public class NPCCommand implements CommandExecutor{
public NPCCommand() {
		super();
	}

	//write to json
	/*JSON format
	 *"UUID":[
	 * 	{ //one npc
	 * 		"UUID": "",
	 * 		"name":"",
		 * 	"type": "static or dynamic",
		 * 	"location": [
		 * 		"x":"",
		 * 		"y":"",
		 * 		"z":"",
		 * 		"pitch":"",
		 * 		"yaw":""
		 * 	],
		 * 	"followHead":"",
		 * 	"followPlayer":"",
		 * 	"world":"",
		 * 	"actionType": "shop, questStart, questCheckpoint, questEnd, talkative", //add more types later
		 * 	"actionID": "shopID or QuestID", 
		 * 	"animationID": "animationID",
		 *}
	 * 
	 * ]
	 * */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		JSONObject npc = new JSONObject(); //jo :flushed:
		Map<String,Object> jo = new LinkedHashMap<>();
		int index = 0;
		Map<String,String> locations = new LinkedHashMap<>();
		String[] locationkey = {"x","y","z","pitch","yaw"};
		String[] actionTypekey = {"shop", "questStart", "questCheckpoint", "questEnd", "talkative"};
		Player player = (Player) sender; //its early shush
		if(!sender.hasPermission("npc.maker"))
			return false;
		if(args.length < 8/*change later*/)
			return false;
		if(args[0] != "static" || args[0] != "dynamic" || args[0] != "s" || args[0] != "d")
			return false;
		if(args[6] != "Overworld" || args[6] != "Nether" || args[6] != "End")
			return false;
		//jo.put("UUID", (String) UUID.randomUUID().toString());
		jo.put("type",args[0].toLowerCase());
		//1,2,3,4,5
		for(int i = 0; i < 6; i++)
			if(args[i+1] == "~")
				locations.put(locationkey[i], String.valueOf(player.getLocation().serialize().get(locationkey[i]))); //gotta look at documentation for this lol
			else
				locations.put(locationkey[i],args[i+1]);
		jo.put("location",locations);
		jo.put("followHead",args[6].toLowerCase());
		jo.put("followPlayer",args[7].toLowerCase());
		if(args[8] == "~")
			jo.put("world",player.getLocation().getWorld().toString());
		else
			jo.put("world",args[8]);
		for(String i : actionTypekey)
			if(i == args[9])
				jo.put("actionType",args[9]);
			else
				index++;
		if(index > actionTypekey.length - 1)
			return false;
		jo.put("actionID",args[10]);
		jo.put("animationID",args[11]); //its just a jo xd
		jo.put("name", args[12]);
		UUID npcUUID = UUID.randomUUID();
		jo.put("UUID", npcUUID.toString());
		//kill me im done wait, a done chicken is already dead...
		//kill me stuff me kill me and take me out of the oven im done
		//nvm
		npc.put(npcUUID.toString(),jo);
		try {
			PrintWriter pw = new PrintWriter("npcs.json");
			pw.write(npc.toJSONString());
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	//creates new NPC in json file
}
//i hate this file with a burning passion
