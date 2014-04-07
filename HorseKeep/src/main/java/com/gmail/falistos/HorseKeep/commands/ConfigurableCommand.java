package main.java.com.gmail.falistos.HorseKeep.commands;

import main.java.com.gmail.falistos.HorseKeep.HorseKeep;

import org.bukkit.command.CommandSender;

public class ConfigurableCommand {
	HorseKeep plugin = null;
	String prefix = null;
	
	public ConfigurableCommand(HorseKeep plugin, CommandSender sender, String[] args)
	{
		this.plugin = plugin;
	}
	
	public void sendMessage(String message, CommandSender sender)
	{
		sender.sendMessage(this.getPrefix() + plugin.lang.get(message));
	}
	
	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}
	
	public String getPrefix()
	{
		return plugin.getChatPrefix();
	}
}
