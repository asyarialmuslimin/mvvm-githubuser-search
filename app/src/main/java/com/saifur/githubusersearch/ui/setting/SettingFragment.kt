package com.saifur.githubusersearch.ui.setting

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.saifur.githubusersearch.R
import com.saifur.githubusersearch.service.AlarmReceiver
import com.saifur.githubusersearch.utils.LangUtil
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    lateinit var sharedPreference : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayOf("English", "Indonesia")

        sharedPreference = requireContext().getSharedPreferences(getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE)

        reminder_switch.isChecked = sharedPreference.getBoolean(KEY_REMINDER, false)

        val langSaved = sharedPreference.getString(KEY_LANG, "Indonesia")

        txt_lang_selected.text = if(langSaved == "en") "English" else "Indonesia"

        reminder_switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                AlarmReceiver.setAlarm(requireContext())
            }else{
                AlarmReceiver.cancelAlarm(requireContext())
            }
            sharedPreference.edit {
                putBoolean(KEY_REMINDER, isChecked)
            }
        }

        lang_container.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Select Language")
            builder.setItems(items) { _, which ->
                var selected = ""
                when(items[which]){
                    "English" -> selected = "en"
                    "Indonesia" -> selected = "in"
                }

                LangUtil.changeLang(requireContext(), selected)
                Toast.makeText(requireContext(), getString(R.string.lang_change_message), Toast.LENGTH_SHORT).show()
            }
            val alert = builder.create()
            alert.show()
        }

    }

    companion object{
        const val KEY_REMINDER = "reminder"
        const val KEY_LANG = "lang"
    }
}