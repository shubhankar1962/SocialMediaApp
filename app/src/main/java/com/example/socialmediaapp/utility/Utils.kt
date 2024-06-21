package com.example.socialmediaapp.utility

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class Utils {
    companion object
    {
        fun showToast(context: Context,message:String){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }


    }
}