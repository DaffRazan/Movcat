package com.daffa.moviecatalogue.ui.main.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daffa.moviecatalogue.core.data.source.Resource
import com.daffa.moviecatalogue.core.domain.model.TvShow
import com.daffa.moviecatalogue.databinding.FragmentTvshowsBinding
import com.daffa.moviecatalogue.ui.detail.DetailFilmActivity
import com.daffa.moviecatalogue.viewmodel.ViewModelFactory
import com.daffa.moviecatalogue.viewmodels.DetailFilmViewModel.Companion.TV_SHOW
import com.daffa.moviecatalogue.viewmodels.MainViewModel
import com.daffa.moviecatalogue.vo.Status


class TvShowsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: TvShowsAdapter

    private var _binding: FragmentTvshowsBinding? = null
    private val fragmentTvShowsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTvshowsBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentTvShowsBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            showLoading(true)
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

            adapter = TvShowsAdapter()

            viewModel.getTvShows.observe(viewLifecycleOwner, handleData)

            fragmentTvShowsBinding.rvTvShow.layoutManager = LinearLayoutManager(context)
            fragmentTvShowsBinding.rvTvShow.setHasFixedSize(true)
            fragmentTvShowsBinding.rvTvShow.adapter = adapter
        }

    }

    private val handleData = Observer<Resource<List<TvShow>>> {
        if (it != null) {
            when (it.status) {
                Status.LOADING -> showLoading(true)
                Status.SUCCESS -> {
                    showLoading(false)
                    it.data?.let { data -> adapter.setTvShow(data) }
                    adapter.setOnItemClickCallback(object :
                        TvShowsAdapter.OnItemClickCallback {
                        override fun onItemClicked(id: String) {
                            selectTvShow(id)
                        }
                    })
                    adapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showLoading(false)
                    Toast.makeText(context, "Something goes wrong...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun selectTvShow(id: String) {
        val intent = Intent(context, DetailFilmActivity::class.java)
        intent.putExtra(DetailFilmActivity.EXTRA_FILM, id)
        intent.putExtra(DetailFilmActivity.EXTRA_CATEGORY, TV_SHOW)

        requireActivity().startActivity(intent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            fragmentTvShowsBinding.progressBar.visibility = View.VISIBLE
        } else {
            fragmentTvShowsBinding.progressBar.visibility = View.GONE
        }
    }

}