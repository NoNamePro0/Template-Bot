package main;

import java.io.IOException;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import managers.*;
import listener.*;

public class Bot {

    public static Bot INSTANCE;
    public ShardManager shardManager;
    public CommandManager cmdMan;

    public static void main(String[] args) { try { new Bot(); } catch (IllegalArgumentException | NullPointerException e) { e.printStackTrace();} }

    public void Bot() throws LoginException, IllegalArgumentException, IOException {
        INSTANCE = this;

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken(Global.TOKEN);

        cmdMan = new CommandManager();

        builder.addEventListeners(new MessageReceived());
        
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setActivity(Activity.of(ActivityType.DEFAULT, "Bot is starting."));

        this.shardManager = builder.build();
        System.out.println("Discord Bot online.");

        setStatus();
    }

    public void Shutdown() {
        if(shardManager != null) {
            shardManager.setStatus(OnlineStatus.OFFLINE);
            shardManager.shutdown();
            System.out.println("Discord Bot offline.");
        }
        else {
            System.out.println("Bot is already offline");
        }
    }

    String Status = "on GitHub.com/NoNamePro0";

    public void setStatus() {
        shardManager.getShards().forEach(jda -> {
            String buildedStatus = Status.replaceAll("%members%", "" + jda.getUsers().size())
                                         .replaceAll("%servers%", "" + jda.getGuilds().size());
            shardManager.setStatus(OnlineStatus.ONLINE);
            jda.getPresence().setActivity(Activity.watching(buildedStatus));
        });
    }

    public CommandManager getCmdMan() {return cmdMan;}
}





