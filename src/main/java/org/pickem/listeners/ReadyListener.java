package org.pickem.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);
        JDA jda = event.getJDA();
        Guild guild = jda.getGuildById("846615027997737001");
        System.out.println(guild.toString());
        if(guild != null)
        {
            guild.upsertCommand("manualevent", "Create a manual event")
                    .addOption(OptionType.STRING, "player", "The first player of your event.", true)
                    .addOption(OptionType.STRING, "playeremoji", "The emoji for your first player.", true)
                    .addOption(OptionType.STRING, "opponent", "The second player / opponent of your event.", true)
                    .addOption(OptionType.STRING, "opponentemoji", "The emoji for the second player of your event", true)
                    .addOption(OptionType.STRING, "tag", "identifier, not case sensitive. Examples: NFL, MLB, UFC, NBA", true)
                    .queue();
            guild.upsertCommand("listevents", "list all currently active events")
                    .queue();
            guild.upsertCommand("closeevent", "close an event by ID")
                    .addOption(OptionType.STRING, "eventid", "ID of the event to close. Found in the event message footer.", true)
                    .queue();
            guild.upsertCommand("decideevent", "give an event a designated winner, and assign wins/losses to the pickers.")
                    .addOption(OptionType.STRING, "eventid", "ID of the event to decide", true)
                    .addOption(OptionType.STRING, "winner", "The name of the winner to pick (I.e. in Bills v. Chiefs, Bills or Chiefs are valid options.)", true)
                    .queue();
            guild.upsertCommand("pickrecord", "list your picks record or the record of another person for a specific leaderboard tag.")
                    .addOption(OptionType.STRING, "tag", "the tag for the record you'd like to retrieve. I.e. NFL, MLB, NBA, etc.", true)
                    .addOption(OptionType.USER, "user", "user to retrieve the record of. Leave empty for yourself.", false)
                    .queue();
            System.out.println("Registered Commands. (ReadyListener.java)");
        }
    }
}
