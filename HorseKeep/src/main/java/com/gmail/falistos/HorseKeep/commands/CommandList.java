package main.java.com.gmail.falistos.HorseKeep.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.com.gmail.falistos.HorseKeep.HorseKeep;

public class CommandList {
	public CommandList(HorseKeep plugin, CommandSender sender, String[] args)
	{
		if (!(sender instanceof Player)) { sender.sendMessage(plugin.lang.get("canOnlyExecByPlayer")); return; }
		
		sender.sendMessage("=== "+ChatColor.GOLD+"["+ChatColor.GREEN+plugin.lang.get("ownedHorses")+ChatColor.GOLD+"] "+ChatColor.RESET+"===");
		
		List<String> horsesList = plugin.manager.getOwnedHorses((Player) sender);
		String stored;
        for (String horseId : horsesList) {
        	if (plugin.manager.isStored(UUID.fromString(horseId))) { stored = ChatColor.RED+" ["+plugin.lang.get("stored")+"]"; }
        	else stored = "";
        	
        	sender.sendMessage("- "+plugin.lang.get("identifier")+": "+ChatColor.AQUA+plugin.manager.getHorseIdentifier(UUID.fromString(horseId))+stored);
        }
	}
}