package com.saifur.githubusersearch.ui.detailuser

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.saifur.githubusersearch.R
import com.saifur.githubusersearch.data.model.detailuser.UserData
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var detailViewModel:DetailViewModel
    private lateinit var userData: UserData

    var isFavourited = false
    var name = ""
    var url = ""
    var menu:Menu? = null

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3)

    companion object{
        const val PROFILE_EXTRA = "profile_extra"
        const val ID_EXTRA = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        shimmerDetail.startShimmer()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        view_pager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, view_pager){tab, position ->
            tab.text = this.getString(TAB_TITLES[position])
            view_pager.setCurrentItem(tab.position, true)
        }.attach()

        supportActionBar?.elevation = 0f

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        val username = intent.extras?.getString(PROFILE_EXTRA)
        val id = intent.extras?.getInt(ID_EXTRA, 0)

        detailViewModel.checkFavourite(id!!, this)
        detailViewModel.favouriteState.observe(this,{isFavourite ->
            isFavourited = isFavourite
            if(menu != null){
                val icon:Int = if(isFavourite) R.drawable.ic_favorite else R.drawable.ic_favorite_empty
                menu?.getItem(1)?.icon = ContextCompat.getDrawable(this, icon)
            }
            if(isFavourite){
                detailViewModel.getLocalData(id, this)
            }else{
                detailViewModel.getRemoteData(username as String)
            }
        })

        detailViewModel.setFollowing(username!!)
        detailViewModel.setFollowers(username)

        detailViewModel.userData.observe(this, {
            if(it != null){
                showDetail()
                setDetails(it)
                tabs.getTabAt(1)?.setText(getString(R.string.following) + it.following + ")")
                tabs.getTabAt(2)?.setText(getString(R.string.followers) + it.followers + ")")
            }
        })
    }

    private fun setDetails(userData:UserData){
        this.userData = userData
        Glide
            .with(this)
            .load(userData.avatar_url)
            .centerCrop()
            .placeholder(R.drawable.profile_placeholder)
            .into(profile_avatar)
        detail_username.text = userData.login
        collapsing_toolbar.title = userData.name

        name = userData.name ?: ""
        url = userData.html_url ?: ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return when(id){
            R.id.action_share -> {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.something_great) +
                        "$name Github\n$url")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,getString(R.string.share_to)))

                true
            }
            R.id.action_favourite -> {
                if(isFavourited){
                    Toast.makeText(this, "UnFavourited", Toast.LENGTH_SHORT).show()
                   detailViewModel.deleteFavourite(userData, this)
                }else{
                    Toast.makeText(this, "Favourited", Toast.LENGTH_SHORT).show()
                    detailViewModel.addFavourite(userData, this)
                }

                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDetail(){
        shimmerDetail.stopShimmer()
        shimmerDetail.visibility = View.GONE
        main_layout.visibility = View.VISIBLE
    }
}