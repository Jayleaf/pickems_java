package org.pickem.templates;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class EventContainer
{
    private ArrayList<Event> events = new ArrayList<Event>();
    private final String path = "eventdata.json";


    /*
     *********************************************************************
     *                DO NOT CHANGE ANYTHING IN THIS CLASS               *
     *                       WHILE DATA IS BEING STORED                  *
     *                            IT WILL CORRUPT IT                     *
     *********************************************************************
     */

    public void addEntry(Event event)
    {
        for(Event e : events)
        {
            if(Objects.equals(e.getMessageId(), event.getMessageId()))
            {
                return;
            }
        }
        events.add(event);
    }

    public void removeEntry(String entryId)
    {
        for(Event e : events)
        {
            if(Objects.equals(e.getMessageId(), entryId))
            {
                events.remove(e);
                return;
            }
        }
        System.out.println("Could not find an entry to remove with that entry id. (EntryContainer.java)");
    }

    public Event getEntry(String entryId)
    {
        for(Event e : events)
        {
            if(Objects.equals(e.getMessageId(), entryId))
            {
                return e;
            }
        }
        System.out.println("Could not get an entry with that entry id. (EntryContainer.java)");
        return null;
    }

    public void serializeData()
    {
        try
        {
            File file = new File(path);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.writeValue(file, this);
            System.out.println("Serialized Entry Data. (EntryContainer.java)");
        } catch (IOException i)
        {
            System.out.println(i);
            // System.out.println("IOException, usually not important. (EntryContainer.java)");
        }
    }

    public void deserializeData()
    {
        EventContainer eventList = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            eventList = mapper.readValue(new File(path), EventContainer.class);
        }
        catch (IOException i)
        {
            System.out.println(i);
            System.out.println("IOException, usually not important. (EntryContainer.java)");
        }
        this.events = eventList != null ? eventList.events : this.events;
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }
}
