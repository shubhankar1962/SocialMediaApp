package com.example.socialmediaapp.model

class Reel {
    var reelUrl:String = ""
    var caption:String = ""
    var profileImg:String = ""
    var userName:String = ""
    var userProfileImg:String = ""

    constructor()
    constructor(reelUrl: String, caption: String) {
        this.reelUrl = reelUrl
        this.caption = caption
    }

    constructor(reelUrl: String, caption: String, profileImg: String) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.profileImg = profileImg
    }

}