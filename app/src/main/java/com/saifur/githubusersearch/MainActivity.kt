package com.saifur.githubusersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.saifur.githubusersearch.ui.listfavourite.ListFavouriteFragment
import com.saifur.githubusersearch.ui.listuser.SearchUserFragment
import com.saifur.githubusersearch.ui.setting.SettingFragment
import com.saifur.githubusersearch.utils.LangUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = SearchUserFragment()
        addFragment(fragment)

        LangUtil.getLang(baseContext)
        bottomNav.menu.clear()
        bottomNav.inflateMenu(R.menu.bottom_nav_menu)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when(item.itemId){
            R.id.user -> {
                val fragment = SearchUserFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.favourite -> {
                val fragment = ListFavouriteFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.setting -> {
                val fragment = SettingFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment:Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}