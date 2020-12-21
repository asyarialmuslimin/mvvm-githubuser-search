package com.saifur.githubusersearch.ui.detailuser.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifur.githubusersearch.R
import com.saifur.githubusersearch.ui.detailuser.DetailViewModel
import com.saifur.githubusersearch.ui.listuser.UserAdapter
import kotlinx.android.synthetic.main.fragment_followers.view.*

class FollowersFragment : Fragment() {

    private lateinit var detailViewModel : DetailViewModel
    private var adapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_followers, container, false)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        view.rview_followers.layoutManager = LinearLayoutManager(this.context)
        view.rview_followers.adapter = adapter

        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        detailViewModel.followersData.observe(requireActivity(), {
            if(it != null){
                adapter.setData(it)
            }
        })
        // Inflate the layout for this fragment
        return view
    }
}