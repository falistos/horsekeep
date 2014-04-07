package main.java.com.gmail.falistos.HorseKeep.commands;

import main.java.com.gmail.falistos.HorseKeep.HorseKeep;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDeleteMember extends ConfigurableCommand {
	public CommandDeleteMember(HorseKeep plugin, CommandSender sender, String[] args)
	{
		super(plugin, sender, args);
		
		if (!(sender instanceof Player)) { sender.sendMessage(plugin.lang.get("canOnlyExecByPlayer")); return; }
		
		if (!plugin.perm.has(sender, "horsekeep.member.remove") && !plugin.perm.has(sender, "horsekeep.admin"))
		{
			sender.sendMessage(this.getPrefix() + ChatColor.RED + plugin.lang.get("noPermission"));
			return;
		}
		
		if (args.length < 2)
		{
			sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("missingHorseIdentifier"));
			return;
		}
		
		String horseIdentifier = args[1];
		
		if (!plugin.manager.horseIdentifierExists(horseIdentifier))
		{
			sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("horseDoesntExists"));
			return;
		}
		
		if (sender instanceof Player)
		{
			if (!plugin.manager.isHorseOwner(horseIdentifier, sender.getName()) && !plugin.perm.has(sender, "horsekeep.admin"))
			{
				sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("dontOwnThisHorse"));
				return;
			}
		}
		
		if (args.length < 3)
		{
			sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("missingPlayer"));
			return;
		}
		
		String playerName = args[2];

		if (!plugin.manager.isHorseMember(horseIdentifier, playerName))
		{
			sender.sendMessage(this.getPrefix() + ChatColor.GOLD + plugin.lang.get("notMemberOfThisHorse").replace("%player", playerName));
			return;
		}
		
		plugin.manager.removeHorseMember(horseIdentifier, playerName);
		
		sender.sendMessage(this.getPrefix() + plugin.lang.get("removedMemberFromHorse").replace("%player", playerName).replace("%id", horseIdentifier));
	}
}