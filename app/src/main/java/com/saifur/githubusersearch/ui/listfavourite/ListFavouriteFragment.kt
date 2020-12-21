package com.saifur.githubusersearch.ui.listfavourite

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
import kotlinx.android.synthetic.main.fragment_list_favourite.*

class ListFavouriteFragment : Fragment() {
    lateinit var viewModel:ListFavouriteViewModel
    private var favouriteAdapter = FavouriteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListFavouriteViewModel::class.java)

        rv_favourite.apply {
            adapter = favouriteAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }

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

        viewModel.getListFavourite(requireContext())
        viewModel.listFavourite.observe(viewLifecycleOwner, {result ->
            if(result != null){
                favouriteAdapter.setData(result)
                shimmerLayout.stopShimmer()
                showRView()
            }else{
                showNotFound()
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

    private fun showRView(){
        shimmerLayout.visibility = View.GONE
        init_state_illustration.visibility = View.GONE
        rv_favourite.visibility = View.VISIBLE
        notfound_state_illustration.visibility = View.GONE
    }

    private fun showNotFound(){
        shimmerLayout.visibility = View.GONE
        init_state_illustration.visibility = View.GONE
        rv_favourite.visibility = View.GONE
        notfound_state_illustration.visibility = View.VISIBLE
    }

    private fun searchUser(query:String){
        viewModel.searchUser(requireContext(), query)
    }

    private fun hideKeyboard(context: Context, view:View){
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}