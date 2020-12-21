package com.saifur.githubusersearch.ui.detailuser.following

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
import kotlinx.android.synthetic.main.fragment_following.view.*

class FollowingFragment : Fragment() {

    private lateinit var detailViewModel : DetailViewModel
    private var adapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_following, container, false)

        adapter.notifyDataSetChanged()

        view.rview_following.layoutManager = LinearLayoutManager(this.context)
        view.rview_following.adapter = adapter

        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        detailViewModel.followingData.observe(requireActivity(), {
            if(it != null){
                adapter.setData(it)
            }
        })

        return view
    }
}