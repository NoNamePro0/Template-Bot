package managers;

import java.util.concurrent.ConcurrentHashMap;
import commands.*;
import commands.types.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {
	
	public ConcurrentHashMap<String, ServerCommand> commands;
	
	public CommandManager() {
		this.commands = new ConcurrentHashMap<>();

		// Clear
		this.commands.put("clear", new Clear());
		this.commands.put("clean", new Clear());
		this.commands.put("remove", new Clear());

		// Help
		this.commands.put("help", new Help());
		this.commands.put("commands", new Help());
		this.commands.put("command", new Help());
		this.commands.put("hilfe", new Help());
		this.commands.put("befehle", new Help());

		// ClientInfo
		this.commands.put("clientinfo", new ClientInfo());
		this.commands.put("user", new ClientInfo());
		this.commands.put("client", new ClientInfo());

		// Embed
		this.commands.put("embed", new Embed());
		this.commands.put("message", new Embed());
	}
	
	
	public boolean perform(String command, Member member, TextChannel channel, Message message) {
		
		ServerCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null) {
			cmd.performCommand(member, channel, message);
			return true;
		}
		else {}
		return false;
	}
}
