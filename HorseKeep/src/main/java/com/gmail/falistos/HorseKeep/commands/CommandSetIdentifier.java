package main.java.com.gmail.falistos.HorseKeep.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.com.gmail.falistos.HorseKeep.HorseKeep;

public class CommandSetIdentifier extends ConfigurableCommand {
	public CommandSetIdentifier(HorseKeep plugin, CommandSender sender, String[] args)
	{
		super(plugin, sender, args);
		
		if (!(sender instanceof Player)) { sender.sendMessage(plugin.lang.get("canOnlyExecByPlayer")); return; }
		
		if (args.length < 2) { sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("missingHorseIdentifier")); return; }
		
		String horseIdentifier = args[1];
		
		if (!plugin.manager.horseIdentifierExists(horseIdentifier)) { sender.sendMessage(plugin.prefix + ChatColor.GOLD + plugin.lang.get("horseDoesntExists")); return; }
		
		if (!plugin.manager.isHorseOwner(horseIdentifier, sender.getName()) && !plugin.perm.has(sender, "horsekeep.admin"))
		{
			sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("dontOwnThisHorse"));
			return;
		}
		
		if (args.length < 3) { sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("missingHorseNewIdentifier")); }

		Boolean hasWhiteSpace = false;
		for (char c : args[2].toCharArray()) {
		    if (Character.isWhitespace(c)) {
		    	hasWhiteSpace = true;
		    }
		}
		
		if (hasWhiteSpace) { sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("identifierNoWhitespaces")); return; }
		
		if (plugin.manager.isHorseIdentifierTaken(args[2])) { sender.sendMessage(plugin.prefix + plugin.lang.get("identifierAlreadyTaken")); return; }
		
		plugin.data.getHorsesData().set("horses."+plugin.manager.getHorseUUID(args[1])+".identifier", args[2]);
		plugin.data.save();
		
		sender.sendMessage(this.getPrefix() + plugin.lang.get("newIdentifierSet")+" "+args[2]);
	}
}