package listener;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import main.*;

import java.util.concurrent.TimeUnit;

public class MessageReceived extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		try{
			String message = event.getMessage().getContentDisplay();
			System.out.println("New message: " + event.getGuild().getId() + " (" + event.getGuild().getName() + "), " + event.getChannel().getId() + " (" + event.getChannel().getName() + "), " + event.getMember().getId() + " (" + event.getMember().getEffectiveName() + "), " + event.getMessage().getId() + " (" + event.getMessage().getContentRaw() + ")");

			if(event.isFromType(ChannelType.TEXT)) {
				TextChannel channel = event.getTextChannel();

				if(message.startsWith("!")) {
					String[] args = message.substring(1).split(" ");
					event.getMessage().delete().queue();
				
					if(args.length > 0 ) {
						if(!Bot.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel, event.getMessage())) {
							channel.sendMessage("Unbekannter Befehl. Nutze `!help` um die Befehle aufzulisten!").complete().delete().queueAfter(10, TimeUnit.SECONDS);
						}
					}
				}
				else {}
			}
		} catch(NullPointerException e) {}
		
	}
	
	
}
