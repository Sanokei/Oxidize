package github.sanokei.oxidize.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import github.sanokei.oxidize.Main;

public class ConfigFiles {
	private static HashMap<String,File> configFileMap = Main.getConfigFileMap();;
	private static Main plugin;

	public static File createConfigFile(String filename){
		try{
			configFileMap.put(filename, new File(plugin.getDataFolder(),filename));
			return configFileMap.get(filename);
		}
		catch(Exception e){
			plugin.getLogger().log(Level.SEVERE,"Could not create config file with desired name. File:" + filename + "Made defaultConfig.yml as placeholder",e);
			if(filename =="defaultConfig.yml"){
				plugin.getLogger().log(Level.SEVERE,"Tried making File:" + filename + "Could not make defaultConfig.yml and or did not exist",e);
				return null;
			}
			return createConfigFile("defaultConfig.yml");
		}
	}
	public static FileConfiguration getConfigFile(String filename){
		try{
			return YamlConfiguration.loadConfiguration(configFileMap.get(filename));
		}
		catch(Exception e){
			return YamlConfiguration.loadConfiguration(createConfigFile(filename));
		}
	}
	
	public static YamlConfiguration getConfigYaml(String filename){ //
		InputStream currentStream;
		try{
			currentStream = plugin.getResource(filename);
		}
		catch(Exception e){
			currentStream = plugin.getResource(getConfigFile(filename).getName());
		}
		YamlConfiguration currentConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(currentStream));
		currentConfig.setDefaults(currentConfig);
		return currentConfig;
	}
	
	public static void saveConfig(String filename){ //Save the file
			try {
				getConfigYaml(filename).save(configFileMap.get(filename));
			}
			catch (IOException e) {
				plugin.getLogger().log(Level.SEVERE,"Could not save config file, could not make config file. Severe warning, corrupt file or no space. File:" + filename,e);
			}
	}
	
	public static void saveDefaultConfig(String filename){ //initilize the file
		try{
			plugin.saveResource(filename,false);
		}
		catch(Exception e){
			plugin.saveResource(getConfigFile(filename).getName(), false);
		}
	}
	
}
