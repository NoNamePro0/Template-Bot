package commands;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class Clear implements ServerCommand {

	@Override
	public void performCommand(Member member, TextChannel channel, Message message) {
		if(member.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
			
			String[] args = message.getContentDisplay().split(" ");
			if(args.length == 2) {
				try {
					int amount = Integer.parseInt(args[1]);
					channel.purgeMessages(get(channel, amount));
					channel.sendMessage(member.getAsMention()  +  " `" + amount + "` Nachrichten wurden gelöscht.").complete().delete().queueAfter(20, TimeUnit.SECONDS);
					return;
				}
				catch (NumberFormatException e) {e.printStackTrace();}
			}
			else {
				channel.sendMessage(member.getAsMention() + "Nutze `!clear [Nachrichtenanzahl]`").complete().delete().queueAfter(10, TimeUnit.SECONDS);
			}
		}
		else
		{
			channel.sendMessage(member.getAsMention() + "Dazu hast du zu wenig Rechte!").complete().delete().queueAfter(10, TimeUnit.SECONDS);
		}
	}
	
	public java.util.List<Message> get(MessageChannel channel, int amount) {
		java.util.List<Message> messages = new ArrayList<Message>();
		
		int i = amount + 1;
		
		for(Message message : channel.getIterableHistory().cache(false)) {
			if(!message.isPinned()) {
				messages.add(message);
				if(--i <= 0) break;
			}
		}	
		return messages;
	}
}

