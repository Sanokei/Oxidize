package github.sanokei.oxidize.commands;

public class SubCommands{
	private String perms;
	
	public SubCommands(String perms) {
		setPerms(perms);
	}
	private void setPerms(String perms){
		this.perms = perms;
	}
	public String getPerms(){
		return perms;
	}
}
