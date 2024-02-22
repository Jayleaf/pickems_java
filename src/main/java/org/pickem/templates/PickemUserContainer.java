package org.pickem.templates;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class PickemUserContainer
{
    private ArrayList<PickemUser> users = new ArrayList<PickemUser>();
    private final String path = "userdata.json";

    public void addUser(PickemUser user)
    {
        for(PickemUser e : users)
        {
            if(e == user)
            {
                // you don't want to create a user that already exists.
                System.out.println("You tried to create a user that already exists.");
                return;
            }
        }
        users.add(user);
        System.out.println("Added user " + user);
    }

    // this function is busted but will probably never be used, so why fix it?
    public void removeUser(String userId)
    {
        for(PickemUser e : users)
        {
            if(Objects.equals(e.getUserId(), userId))
            {
                users.remove(e);
                return;
            }
        }
        System.out.println("Could not find a user to remove with that user id. (PickemUserContainer.java)");
    }

    public PickemUser getUser(String userId, String tag)
    {
        PickemUser u = new PickemUser(userId, tag);
        for(PickemUser e : users)
        {
            if(Objects.equals(e.getUserId(), u.getUserId()) && Objects.equals(e.getTag(), u.getTag()))
            {
                return e;
            }
        }
        System.out.println("Could not get an user with that user id and/or tag. (PickemUserContainer.java)");
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
            System.out.println("Serialized PickemUser Data. (PickemUserContainer.java)");
        } catch (IOException i)
        {
            System.out.println(i);
            // System.out.println("IOException, usually not important. (PickemUserContainer.java)");
        }
    }

    public void deserializeData()
    {
        PickemUserContainer userList = null;
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            userList = mapper.readValue(new File(path), PickemUserContainer.class);
        }
        catch (IOException i)
        {
            System.out.println(i);
            // System.out.println("IOException, usually not important. (PickemUserContainer.java)");
        }
        this.users = userList != null ? userList.users : this.users;
    }

    public ArrayList<PickemUser> getUsers()
    {
        return users;
    }
}
