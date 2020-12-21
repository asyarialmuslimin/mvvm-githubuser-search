package com.saifur.githubusersearch.ui.listuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifur.githubusersearch.R
import com.saifur.githubusersearch.data.model.listuser.Item
import com.saifur.githubusersearch.ui.detailuser.DetailActivity
import kotlinx.android.synthetic.main.row_user.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val mData = ArrayList<Item>()

    fun setData(items : ArrayList<Item>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        fun bind(userRow:Item){
            with(itemView){
                Glide
                    .with(this.context)
                    .load(userRow.avatar_url)
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder)
                    .into(user_avatar)
                user_name.text = userRow.login
                user_url.text = userRow.html_url
                card_user.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.PROFILE_EXTRA, userRow.login)
                    intent.putExtra(DetailActivity.ID_EXTRA, userRow.id)
                    context.startActivity(intent)
                }
            }
        }
    }
}