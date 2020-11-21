package github.sanokei.oxidize.customnpc;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R2.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R2.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R2.PlayerConnection;
import net.minecraft.server.v1_16_R2.PlayerInteractManager;

/*ONLY USE FOR STATIONARY NPC's
 * 
 * EntityPlay doesnt allow for custom animation because the animations are baked in.
 */
public class NPC extends EntityPlayer{
	private EntityPlayer npc = null;
	//abstract classes
	//
	public NPC(String world, String NPCname, String uuid, @SuppressWarnings("rawtypes") Map location){
		super(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) Bukkit.getWorld(world)).getHandle(), new GameProfile(UUID.fromString(uuid), NPCname), new PlayerInteractManager(((CraftWorld) Bukkit.getWorld(world)).getHandle()));
		this.npc = this;
		reloadPlayerNPCPackets();
		npc.setLocation((double)location.get("x"), (double)location.get("y"), (double)location.get("z"), (float)location.get("yaw"), (float)location.get("pitch"));
		npc.setHeadRotation(getHeadRotation());
	}
	
	public void sendPlayerNpcPackets(Player player){ //start sending npc packet to a player
		PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc,(byte)(npc.yaw*256/360)));
		
	}
	
	public void reloadPlayerNPCPackets(){ //start sending ALL players a new npc packet
	 Bukkit.getOnlinePlayers().stream().forEach(player ->{
		PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
	  connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); 
	 	connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc)); 
	 	connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc,(byte)(npc.yaw*256/360))); 
	});
}

}
