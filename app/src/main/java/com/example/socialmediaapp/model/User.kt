package com.example.socialmediaapp.model

class User {
    var image:String? = null
    var name:String? = null
    var email:String? = null
    var bio:String? = null
    var password:String? = null
    var premiumUser:Boolean? = null

    constructor()
    constructor(image: String?, name: String?, email: String?,bio:String?, password: String?,premiumUser:Boolean) {
        this.image = image
        this.name = name
        this.email = email
        this.bio = bio
        this.password = password
        this.premiumUser = premiumUser

    }

    constructor(email: String?,password: String?){
        this.email = email
        this.password = password
    }


}