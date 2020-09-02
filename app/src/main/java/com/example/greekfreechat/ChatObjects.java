package com.example.greekfreechat;

public class ChatObjects {

private  String message;
private Boolean currentUser;

    public ChatObjects(String message, Boolean currentUser){

        this.message = message;
                this.currentUser = currentUser;

    }

    public  String GetMessage() { return  message; }
    public  void setMessage(String message) { this.message=message; }

    public   Boolean      GetCurrentUser() { return currentUser; }
    public  void setCurrentUser(Boolean currentUser) { this.currentUser=currentUser; }

}

