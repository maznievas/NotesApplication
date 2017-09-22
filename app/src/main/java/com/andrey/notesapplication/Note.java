package com.andrey.notesapplication;

/**
 * Created by sts on 18.09.17.
 */

public class Note {

    private String content;
    private int id;
    private String date;
    private String password;

    public Note()
    {
        content = "";
        id = 0;
        date = "";
        password = "";
    }

    public Note(int id, String content, String date)
    {
        this.id = id;
        this.content = content;
        this.date = date;
       // this.password = password;
    }

    public Note(String content, String date)
    {
        this.content = content;
        this.date = date;
    }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }

    public String getContent()
    {
        return content;
    }

    public String getDate()
    {
        return date;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
 }
