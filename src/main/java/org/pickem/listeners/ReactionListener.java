package org.pickem.listeners;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.pickem.templates.Event;
import org.pickem.templates.EventContainer;
import org.pickem.templates.Pick;
import org.pickem.templates.Player;

import java.util.Objects;

public class ReactionListener extends ListenerAdapter
{
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event)
    {
        super.onMessageReactionAdd(event);
        EventContainer events = new EventContainer();
        events.deserializeData();

        for (Event e : events.getEvents())
        {

            // First of all, lets make sure the event is open at all.
            if(e.isClosed())
            {
                continue;
            }
            if (Objects.equals(e.getMessageId(), event.getMessageId()))
            {
                /* Now that we know we're dealing with the right message, we must first verify that the added reaction
                is of the valid reactions for the message.
                 */
                if (event.getReaction().getEmoji().getName().equals(e.getPlayer().getEmoji()) || event.getReaction().getEmoji().getName().equals(e.getOpponent().getEmoji()))
                {
                    // First, we need to find the player instance that correlates to the user's reaction.
                    Player currentchoice = null;
                    if (event.getReaction().getEmoji().getName().equals(e.getPlayer().getEmoji()))
                    {
                        currentchoice = e.getPlayer();
                    }
                    else if (event.getReaction().getEmoji().getName().equals(e.getOpponent().getEmoji()))
                    {
                        currentchoice = e.getOpponent();
                    }
                    // Now, we update the pick data.
                    for (Pick p : e.getPicks())
                    {
                        if (Objects.equals(p.getUserId(), event.getUserId()))
                        {
                            e.removePick(p);
                            e.addPick(Objects.requireNonNull(event.getUser()).getName(), event.getUserId(), currentchoice);
                            events.serializeData();
                            return;
                        }

                    }
                    // If this point in the code is reached, the user does not have a pick. Thus, we give them one.
                    e.addPick(Objects.requireNonNull(event.getUser()).getName(), event.getUserId(), currentchoice);
                }
            }
        }
        events.serializeData();
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event)
    {
        super.onMessageReactionRemove(event);
        EventContainer events = new EventContainer();
        events.deserializeData();
        for (Event e : events.getEvents())
        {
            // First of all, lets make sure the event is open at all.
            if(e.isClosed())
            {
                continue;
            }
            // Ensure we are dealing with the correct entry.
            if (Objects.equals(e.getMessageId(), event.getMessageId()))
            {
                // Removing a reaction will only update the users pick if the removed reaction was their current pick.
                if (event.getReaction().getEmoji().getName().equals(e.getPlayer().getEmoji()) || event.getReaction().getEmoji().getName().equals(e.getOpponent().getEmoji()))
                {
                    for(Pick p : e.getPicks())
                    {
                        if(Objects.equals(p.getChoice().getEmoji(), event.getReaction().getEmoji().getName()))
                        {
                            e.removePick(p);
                            break;
                        }
                    }
                }
            }
        }
        events.serializeData();
    }
}
