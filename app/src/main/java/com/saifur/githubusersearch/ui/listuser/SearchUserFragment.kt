package com.saifur.githubusersearch.ui.listuser

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saifur.githubusersearch.R
import kotlinx.android.synthetic.main.fragment_search_user.*

class SearchUserFragment : Fragment() {
    private lateinit var userViewModel : UserViewModel
    private var adapter = UserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_users.layoutManager = LinearLayoutManager(this.context)
        rv_users.adapter = adapter

        show_search.setOnClickListener {
            search_container.visibility = View.VISIBLE
            it.visibility = View.GONE
            hide_search.visibility = View.VISIBLE
        }

        hide_search.setOnClickListener {
            search_container.visibility = View.GONE
            it.visibility = View.GONE
            show_search.visibility = View.VISIBLE
        }

        userViewModel = ViewModelProvider(requireActivity()).get(
            UserViewModel::class.java)

        userViewModel.getPopularUser()
        userViewModel.listUsers.observe(viewLifecycleOwner, {result ->
            if(result != null){
                if(result.size > 0){
                    adapter.setData(result)
                    shimmerLayout.stopShimmer()
                    showRView()
                }else{
                    showNotFound()
                }
            }
        })

        search_box.setOnKeyListener { _, _, event ->
            if(event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                btn_search.performClick()
            }
            false
        }

        btn_search.setOnClickListener {
            searchUser(search_box.text.toString())
            hideKeyboard(requireContext(), it)
        }
    }

    private fun searchUser(query:String){
        showShimmer()
        if(query != ""){
            userViewModel.searchUsers(query)
        }
    }

    private fun showRView(){
        shimmerLayout.visibility = View.GONE
        init_state_illustration.visibility = View.GONE
        rv_users.visibility = View.VISIBLE
        notfound_state_illustration.visibility = View.GONE
    }

    private fun showNotFound(){
        shimmerLayout.visibility = View.GONE
        init_state_illustration.visibility = View.GONE
        rv_users.visibility = View.GONE
        notfound_state_illustration.visibility = View.VISIBLE
    }

    private fun showShimmer(){
        shimmerLayout.visibility = View.VISIBLE
        init_state_illustration.visibility = View.GONE
        rv_users.visibility = View.GONE
        notfound_state_illustration.visibility = View.GONE
    }

    fun hideKeyboard(context: Context, view:View){
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}