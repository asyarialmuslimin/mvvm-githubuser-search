package com.saifur.consumerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifur.consumerapp.data.UserData
import kotlinx.android.synthetic.main.row_favuser.view.*

class UserFavAdapter : RecyclerView.Adapter<UserFavAdapter.ViewHolder>() {

    private val mData = ArrayList<UserData>()

    fun setData(data:List<UserData>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(rowItem: UserData){
            with(itemView){
                Glide.with(this.context)
                    .load(rowItem.avatar_url)
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder)
                    .into(user_avatar)

                user_name.text = rowItem.login
                user_url.text = rowItem.html_url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_favuser, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}