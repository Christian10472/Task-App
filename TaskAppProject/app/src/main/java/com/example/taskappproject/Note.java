package com.example.taskappproject;

public class Note {
    private int id;
    private String noteName, noteBody;

    public Note(int id, String noteName, String noteBody) {
        this.id = id;
        this.noteName = noteName;
        this.noteBody = noteBody;
    }

    public Note() {
    }

    //toString
    @Override
    public String toString() {
        return "TaskInformationModel{" +
                "id=" + id +
                ", noteName='" + noteName + '\'' +
                ", noteBody='" + noteBody + '}';
    }

    //Setters and Getters
    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return noteName;
    }

    public void setName(String noteName) {
        this.noteName = noteName;
    }

    public String getBody() {
        return noteBody;
    }

    public void setBody(String noteBody) {
        this.noteBody = noteBody;
    }
}
