package org.pickem.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.pickem.templates.Event;
import org.pickem.templates.EventContainer;

import java.util.Objects;

public class CloseEvent
{
    public CloseEvent(String eventId, SlashCommandInteractionEvent ie)
    {
        EventContainer ec = new EventContainer();
        ec.deserializeData();

        for(Event e : ec.getEvents())
        {
            // find our event to close

            if(Objects.equals(e.getMessageId(), eventId))
            {
                e.closeEvent();
                ie.reply("Successfully closed event: " + e.toFormattedString()).queue();
                break;
            }
        }
        ec.serializeData();
    }
}
