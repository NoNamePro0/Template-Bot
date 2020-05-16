package commands;

import java.time.OffsetDateTime;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embed implements ServerCommand {

	@Override
	public void performCommand(Member member, TextChannel channel, Message message) {
		String text = message.getContentRaw().substring(7);
		EmbedBuilder embed  = new EmbedBuilder();
		
		embed.setDescription(text);
		embed.setTimestamp(OffsetDateTime.now());
		
		channel.sendMessage(embed.build()).complete();
	}
}
