package commands;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class Help implements ServerCommand {
 
	@Override
	public void performCommand(Member member, TextChannel channel, Message message) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setDescription( "`!clear [Nachritenanzahl]`: Entfernt die letzten Nachrichten" + "\n" +
								"`!user [User-Tag]`: Zeigt Informationen über einen User an" + "\n" +
								"`!embed [Nachricht]`: Embed eine Nachricht" + "\n");
		builder.setColor(0xffffff);
		builder.setFooter("Bot by No Name Pro", "https://cdn.discordapp.com/avatars/584715834052706336/6ddb0ac8d263b37c2feded03afb1a08e.png");
		builder.setTitle("Commands for this Bot");
		builder.setTimestamp(OffsetDateTime.now());
		
		member.getUser().openPrivateChannel().queue((ch) -> {
			ch.sendMessage(builder.build()).queue();
			
		});
		
	}
}
