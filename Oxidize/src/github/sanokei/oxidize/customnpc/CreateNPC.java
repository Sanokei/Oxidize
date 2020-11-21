package github.sanokei.oxidize.customnpc;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class CreateNPC {
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
	public CreateNPC(){};
	
	@SuppressWarnings("rawtypes")
	public void createNpcs(){
		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader("npcs.json"));
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jo = (JSONObject) obj; 
		if(jo.get("type") == "static"){
			//String world, String NPCname, UUID uuid
			new NPC((String)jo.get("world"),(String)jo.get("name"),(String)jo.get("UUID"),(Map)jo.get("location"));
		}
		else if(jo.get("type") == "dynamic"){
			
		}
		
	}
}
