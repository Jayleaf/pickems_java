package org.pickem.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.pickem.templates.*;

import java.util.Objects;

public class DecideEvent
{
    public DecideEvent(SlashCommandInteractionEvent ie, String eventId, String pick)
    {
        EventContainer ec = new EventContainer();
        ec.deserializeData();
        for(Event e : ec.getEvents())
        {
            if(e.parsePickString(pick) != null && Objects.equals(e.getMessageId(), eventId))
            {
                // We have confirmed that the pick is valid, and will now begin dishing out wins and losses.
                Player winningplayer = e.parsePickString(pick);
                PickemUserContainer pc = new PickemUserContainer();
                pc.deserializeData();
                int index = 0;
                System.out.println("Beginning pick processing for event ID " + eventId + ", with leaderboard tag " + e.getTag());
                for(Pick p : e.getPicks())
                {
                    if(Objects.equals(p.getChoice().getName(), winningplayer.getName()))
                    {
                        if(pc.getUser(p.getUserId(), e.getTag()) == null)
                        {
                            pc.addUser(new PickemUser(p.getUserId(), e.getTag()));
                            System.out.println("Created new user.");
                        }
                        pc.getUser(p.getUserId(), e.getTag()).addWin();
                    }
                    else
                    {
                        if(pc.getUser(p.getUserId(), e.getTag()) == null)
                        {
                            pc.addUser(new PickemUser(p.getUserId(), e.getTag()));
                            System.out.println("Created new user.");
                        }
                        pc.getUser(p.getUserId(), e.getTag()).addLoss();
                    }
                }
                pc.serializeData();
                ie.reply("Successfully decided event.").queue();
                return;
            }
        }

        ie.reply("Could not parse the pick for that event. Please try again following the format listed in the command description.").queue();
    }
}
