package org.pickem.templates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/*
*********************************************************************
*                DO NOT CHANGE ANYTHING IN THIS CLASS               *
*                       WHILE DATA IS BEING STORED                  *
*                            IT WILL CORRUPT IT                     *
*********************************************************************
*/
public class Event
{

    private Player player;
    private Player opponent;
    private String messageId;

    // Message ID = Event ID

    private ArrayList<Pick> picks = new ArrayList<Pick>();

    private String tag;

    private boolean closed = false;

    public Event()
    {

    }
    public Event(Player player, Player opponent, String messageId, String tag, Boolean closed)
    {
        this.player = player;
        this.opponent = opponent;
        this.messageId = messageId;
        this.tag = tag.toUpperCase();
        this.closed = closed;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public void closeEvent()
    {
        this.closed = true;
    }

    public Boolean isClosed()
    {
        return this.closed;
    }
    public String getTag()
    {
        return this.tag;
    }

    public Player getOpponent()
    {
        return this.opponent;
    }

    public String getMessageId()
    {
        return this.messageId;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public void addPick(String username, String id, Player choice)
    {
        Pick pick = new Pick(username, id, choice);
        picks.add(pick);
    }

    public void alterPick(Pick pick, Player newchoice)
    {
        int index = picks.indexOf(pick);
        if(index != -1)
        {
            Pick userpick = picks.get(index);
            userpick.setChoice(newchoice);
            this.picks.set(index, userpick);
        }
        else
        {
            System.out.println("Could not find pick to alter. (Entry.java)");
        }
    }

    public void removePick(Pick pick)
    {
        int index = picks.indexOf(pick);
        if(index != -1)
        {
            picks.remove(index);
        }
        else
        {
            System.out.println("Could not find pick to remove. (Entry.java)");
        }
    }

    public ArrayList<Pick> getPicks()
    {
        return picks;
    }

    public String toFormattedString()
    {
        return getPlayer().getNameFormatted() + " v. " + getOpponent().getNameFormatted();
    }

    public Player parsePickString(String name)
    {
        if(Objects.equals(name.toLowerCase(), getPlayer().getName().toLowerCase()))
        {
            return getPlayer();
        }
        else if(Objects.equals(name.toLowerCase(), getOpponent().getName().toLowerCase()))
        {
            return getOpponent();
        }
        else
        {
            System.out.println("Failed parsing pick string. Input: " + name + "Valid Options: " + getPlayer() + " | " + getOpponent());
            return null;
        }
    }
}
