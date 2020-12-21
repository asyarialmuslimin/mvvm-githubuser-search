package com.saifur.githubusersearch.ui.detailuser.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saifur.githubusersearch.R
import com.saifur.githubusersearch.ui.detailuser.DetailViewModel
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        detailViewModel.userData.observe(requireActivity(), {
            if (it != null) {
                view.profile_bio.text = it.bio
                view.profile_location.text = it.location
                view.profile_repo.text = "${it.public_repos} Public Repo"
            }
        })

        return view
    }

}