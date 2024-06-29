package com.example.socialmediaapp.model

class Post {
    var postImgUrl:String = ""
    var caption: String = ""
    var uid:String = ""
    var time:String = ""
    var userProfileImg:String = ""
    var userName:String = ""
   // var premiumUser:Boolean? = false

    constructor()

    constructor(postImgUrl:String,caption:String)
    {
        this.postImgUrl = postImgUrl
        this.caption = caption
    }

    constructor(postImgUrl: String, caption: String, uid: String, time: String) {
        this.postImgUrl = postImgUrl
        this.caption = caption
        this.uid = uid
        this.time = time
        //this.premiumUser = premiumUser
        //this.userName = userName
        //this.userProfileImg = userProfileImg
    }

}