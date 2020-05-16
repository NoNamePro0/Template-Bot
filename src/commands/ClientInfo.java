package commands;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Role;

public class ClientInfo implements ServerCommand {

	public void performCommand(Member member, TextChannel channel, Message message) {
		channel.sendTyping().queue();
		if(member.hasPermission(channel, Permission.MESSAGE_WRITE)) {
			List<Member> members = message.getMentionedMembers();
		
			if(members.size() > 0) {
				for(Member u : members) {
					onInfo(member, u, channel);
				}
			}
			else {
				onInfo(member, member, channel);
			}
		}
		else {
			channel.sendMessage(member.getAsMention() + "Dazu hast du zu wenig Rechte!").complete().delete().queueAfter(10, TimeUnit.SECONDS);
		}
		message.delete().queue();
	}
	
	public void onInfo(Member member, Member u, TextChannel channel) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setFooter("Requested by " + member.getEffectiveName(), member.getUser().getAvatarUrl());
		embed.setThumbnail(u.getUser().getAvatarUrl());
		embed.setColor(0xffffff);
		embed.setTimestamp(OffsetDateTime.now());
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("**User: " + u.getAsMention() + "**\n");
		stringBuilder.append("**Joined**: `" + u.getTimeJoined() + "`\n");
		stringBuilder.append("**Account Created**: `" + u.getTimeCreated() + "`\n\n");
		stringBuilder.append("**Status**: `" + u.getOnlineStatus() + "`\n");
	    StringBuilder activityBuilder = new StringBuilder();
		stringBuilder.append("**Activity**: "); for(Activity activity : u.getActivities()) {activityBuilder.append("`" + activity + "` ");} stringBuilder.append(activityBuilder.toString().trim() + "\n\n");
	    StringBuilder roleBuilder = new StringBuilder();
		stringBuilder.append("**Roles**: "); for(Role role : u.getRoles()) {roleBuilder.append(role.getAsMention() + " ");} stringBuilder.append(roleBuilder.toString().trim() + "\n\n");
		
		embed.setDescription(stringBuilder.toString());
		
		channel.sendMessage(embed.build()).complete().delete().queueAfter(60, TimeUnit.SECONDS);
	}
}
