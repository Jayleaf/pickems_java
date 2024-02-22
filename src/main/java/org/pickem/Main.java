package org.pickem;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.pickem.listeners.CommandListener;
import org.pickem.listeners.ReactionListener;
import org.pickem.listeners.ReadyListener;

import javax.security.auth.login.LoginException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    private final Dotenv config;
    private final ShardManager shardManager;

    /*
    Main class constructor, sets up the bot.
     */

    public Main() throws LoginException
    {
        config = Dotenv.configure().load();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(config.get("TOKEN"));
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.watching("Picks"));
        shardManager = builder.build();

        // Register Listeners

        shardManager.addEventListener(new ReactionListener());
        shardManager.addEventListener(new CommandListener());
        shardManager.addEventListener(new ReadyListener());

    }

    public ShardManager getShardManager()
    {
        return shardManager;
    }

    /*
    Attempt to initialize the bot in the main method.
     */

    public static void main(String[] args) {
        try
        {
            Main bot = new Main();
        }
        catch (LoginException e)
        {
            System.out.println("Login exception occurred. Please check token. (Main.java)");
        }
    }
}