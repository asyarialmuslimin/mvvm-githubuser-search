package com.saifur.githubusersearch.ui.listfavourite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saifur.githubusersearch.R
import com.saifur.githubusersearch.data.model.detailuser.UserData
import com.saifur.githubusersearch.ui.detailuser.DetailActivity
import kotlinx.android.synthetic.main.row_user.view.*

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    private val mData = ArrayList<UserData>()

    fun setData(data:List<UserData>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(rowItem:UserData){
            with(itemView){
                Glide.with(this.context)
                    .load(rowItem.avatar_url)
                    .centerCrop()
                    .placeholder(R.drawable.profile_placeholder)
                    .into(user_avatar)

                user_name.text = rowItem.login
                user_url.text = rowItem.html_url

                card_user.setOnClickListener {
                    val intent = Intent(this.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.PROFILE_EXTRA, rowItem.login)
                    intent.putExtra(DetailActivity.ID_EXTRA, rowItem.id)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size


}