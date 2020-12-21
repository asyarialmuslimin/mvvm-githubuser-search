package com.saifur.githubusersearch.ui.detailuser

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.saifur.githubusersearch.ui.detailuser.followers.FollowersFragment
import com.saifur.githubusersearch.ui.detailuser.following.FollowingFragment
import com.saifur.githubusersearch.ui.detailuser.profile.ProfileFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when (position){
            0 -> fragment = ProfileFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = FollowersFragment()
        }
        return fragment as Fragment
    }
}