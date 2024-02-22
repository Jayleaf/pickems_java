package org.pickem.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.pickem.templates.Event;
import org.pickem.templates.EventContainer;
import org.pickem.templates.Pick;

import java.util.Objects;

public class ListEvents {

    public ListEvents(SlashCommandInteractionEvent ie)
    {
        EventContainer ec = new EventContainer();
        ec.deserializeData();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Event List");
        eb.setDescription("List of all currently active events.");
        for(Event e : ec.getEvents())
        {
            String userpick = "None";
            for(Pick p : e.getPicks())
            {
                if(Objects.equals(p.getUserId(), ie.getUser().getId()))
                {
                    Emoji em = Emoji.fromUnicode(p.getChoice().getEmoji());
                    userpick = em.getName();
                }
            }
            String isClosed = e.isClosed() ? "Closed" : "Open";
            eb.addField(e.toFormattedString() + " ("  + isClosed + ")", e.getMessageId() + "\n" + "Your pick: " + userpick, false);
        }
        ie.replyEmbeds(eb.build()).queue();
    }
}
