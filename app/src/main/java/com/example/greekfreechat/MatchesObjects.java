package com.example.greekfreechat;

class MatchesObjects {

    private  String userId;
    private  String name;
    private  String profileImageUrl;

    public MatchesObjects(String userId, String name , String profileImageUrl){
            this.userId = userId;
        this.name= name;
        this.profileImageUrl= profileImageUrl;

        }


        public  String GetUserId() { return  userId; }
        public  void setUserId(String userId) { this.userId=userId; }


    public  String GetName() { return  name; }
    public  void setName(String userId) { this.name=name; }


    public  String GetProfileImageUrl() { return  profileImageUrl; }
    public  void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl=profileImageUrl; }




}

