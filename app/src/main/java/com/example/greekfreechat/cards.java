package com.example.greekfreechat;

class cards {
    private  String userId;
    private  String name;
    public  cards(String userId,String name){
        this.userId = userId;
        this.name = name;
    }


    public  String GetUserId()
    {
        return  userId;
    }

    public  void setUserId(String userId)
    {
        this.userId=userId;
    }

    public  String GetNameId()
    {
        return  name;
    }

    public  void setName(String name)
    {
        this.name=name;
    }


}
