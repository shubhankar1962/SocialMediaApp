package com.example.socialmediaapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.PostsItemRvBinding
import com.example.socialmediaapp.model.Post
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.USER_NODE
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class HomePostAdapter(val context:Context,val postList:ArrayList<Post>):RecyclerView.Adapter<HomePostAdapter.HomePostViewHolder>() {

    inner class HomePostViewHolder(val binding: PostsItemRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostViewHolder {
        val binding = PostsItemRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return HomePostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: HomePostViewHolder, position: Int) {
        var isLike:Boolean = false
        var cntLike = 0
        var premiumUser:Boolean? = false

        try{
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get()
                .addOnSuccessListener {
                    val user:User = it.toObject<User>()!!
                    premiumUser = user.premiumUser
                    holder.binding.profileName.text = user!!.name
                    if(premiumUser == true){
                        Glide.with(context).load(postList.get(position)).placeholder(R.drawable.blue_tick).into(holder.binding.blueTick)
                    }
                    Glide.with(context).load(user!!.image).placeholder(R.drawable.profile_icon).into(holder.binding.profileImg)
                }
        }
        catch (e:Exception)
        {

        }

        Glide.with(context).load(postList.get(position).postImgUrl).placeholder(R.drawable.addpost_icon).into(holder.binding.postImg)
        holder.binding.caption.text = postList.get(position).caption


        try{
            val time = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.profiletime.text = time
        }
        catch (e:Exception){
            holder.binding.profiletime.text = ""
        }
      //  holder.binding.profileName.text = postList.get(position).userName
        Glide.with(context).load(postList.get(position).userProfileImg).placeholder(R.drawable.profile_icon).into(holder.binding.profileImg)

        holder.binding.like.setOnClickListener {
            if(!isLike) {
                cntLike++
                holder.binding.like.setImageResource(R.drawable.red_heart)
                isLike = !isLike
            }else
            {
                holder.binding.like.setImageResource(R.drawable.like_icon)
                isLike = !isLike
                cntLike--
            }
            holder.binding.noOfLikes.text = cntLike.toString()
        }
        holder.binding.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,postList.get(position).postImgUrl)
            context.startActivity(intent)
        }


    }
}