package com.saifur.githubusersearch.utils

import android.content.Context
import android.content.res.Configuration
import androidx.core.content.edit
import com.saifur.githubusersearch.R
import com.saifur.githubusersearch.ui.setting.SettingFragment
import java.util.*

object LangUtil {

    private fun setLang(context: Context, lang:String){
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun changeLang(context: Context, lang: String){
        val spf = context.getSharedPreferences(context.resources.getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE)
        spf.edit{
            putString(SettingFragment.KEY_LANG, lang)
        }
        setLang(context, lang)
    }

    fun getLang(context: Context){
        val spf = context.getSharedPreferences(context.resources.getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE)
        val lang = spf.getString(SettingFragment.KEY_LANG, "en")
        setLang(context, lang!!)
    }
}