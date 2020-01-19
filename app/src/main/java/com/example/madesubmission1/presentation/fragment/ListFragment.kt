package com.example.madesubmission1.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.api.base.BaseAPI
import com.example.madesubmission1.data.entities.local.base.BaseLocalModel
import com.example.madesubmission1.data.local.MoviesData
import com.example.madesubmission1.data.local.TvShowData
import com.example.madesubmission1.presentation.adapter.recyclerview.ListAPIAdapter
import com.example.madesubmission1.presentation.adapter.recyclerview.ListLocalAdapter
import com.example.madesubmission1.presentation.callback.OnAPIItemCallback
import com.example.madesubmission1.presentation.callback.OnLocalItemCallback
import com.example.madesubmission1.presentation.viewmodel.ListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    companion object {
        private const val ARG_PAGE_INDEX = "page_index"

        fun newInstance(index: Int): ListFragment {
            return ListFragment()
                .apply {
                    val bundle = Bundle().apply {
                        putInt(ARG_PAGE_INDEX, index)
                    }

                    arguments = bundle
                }
        }
    }

    private lateinit var listFragmentViewModel: ListFragmentViewModel
    private var arrBaseLocalModel = arrayListOf<BaseLocalModel>()
    private var arrBaseAPI = arrayListOf<BaseAPI>()
    private val listLocalAdapter = ListLocalAdapter(arrBaseLocalModel)
    private val listAPIAdapter = ListAPIAdapter(arrBaseAPI)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeVM()
        setupRVLayoutManager()
        setupRVDivider()
        setArrData()
        setupUIClickListener()
    }

    private fun initializeVM() {
        listFragmentViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ListFragmentViewModel::class.java
        )
    }

    private fun setupRVLayoutManager() {
        rv_list.layoutManager = LinearLayoutManager(context)
    }

    private fun setupRVDivider() {
        rv_list.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setArrData() {
        if (arguments?.getInt(ARG_PAGE_INDEX) == 0) {
            listFragmentViewModel.getListMovie().observe(this, Observer { apiStateHandler ->
                if (apiStateHandler == null) return@Observer
                spinkit_progress.visibility = View.GONE

                if (apiStateHandler.error == null) {
                    apiStateHandler.baseAPIResponse?.let {
                        setAPIAdapter(it.results)
                    }
                } else {
                    Toast.makeText(context, getString(R.string.load_api_failed), Toast.LENGTH_LONG)
                        .show()
                    setMoviesDataAdapter()
                }
            })
        } else if (arguments?.getInt(ARG_PAGE_INDEX) == 1) {
            listFragmentViewModel.getListTvShow().observe(this, Observer { apiStateHandler ->
                if (apiStateHandler == null) return@Observer
                spinkit_progress.visibility = View.GONE

                if (apiStateHandler.error == null) {
                    apiStateHandler.baseAPIResponse?.let {
                        setAPIAdapter(it.results)
                    }
                } else {
                    Toast.makeText(context, getString(R.string.load_api_failed), Toast.LENGTH_LONG)
                        .show()
                    setTvShowDataAdapter()
                }
            })
        }
    }

    private fun setAPIAdapter(baseAPI: List<BaseAPI>) {
        arrBaseAPI.addAll(baseAPI)
        rv_list.adapter = listAPIAdapter
    }

    private fun setMoviesDataAdapter() {
        val moviesData = MoviesData(
            resources.getStringArray(R.array.arr_movie_name),
            resources.getStringArray(R.array.arr_movie_top_cast),
            resources.getStringArray(R.array.arr_movie_release_date),
            resources.getStringArray(R.array.arr_movie_description)
        )

        arrBaseLocalModel.addAll(moviesData.listMovie)
        rv_list.adapter = listLocalAdapter
    }

    private fun setTvShowDataAdapter() {
        val tvShowData = TvShowData(
            resources.getStringArray(R.array.arr_tv_show_name),
            resources.getStringArray(R.array.arr_tv_show_top_cast),
            resources.getStringArray(R.array.arr_tv_show_release_date),
            resources.getStringArray(R.array.arr_tv_show_description)
        )

        arrBaseLocalModel.addAll(tvShowData.listTvShow)
        rv_list.adapter = listLocalAdapter
    }

    private fun setupUIClickListener() {
        listLocalAdapter.setOnLocalItemClickCallback(object :
            OnLocalItemCallback {
            override fun onItemClicked(baseLocalModel: BaseLocalModel) {
                navigateToDetail(baseLocalModel)
            }
        })

        listAPIAdapter.setOnAPIItemClickCallback(object :
            OnAPIItemCallback {
            override fun onItemClicked(baseAPI: BaseAPI) {
                navigateToDetail(baseAPI)
            }
        })
    }

    private fun navigateToDetail(baseLocalModel: BaseLocalModel) {
        val toListDetailActivity =
            ListFragmentDirections.actionListFragmentToListDetailActivity().apply {
                setBaseLocalModel(baseLocalModel)
            }

        NavHostFragment.findNavController(this).apply {
            navigate(toListDetailActivity)
        }
    }

    private fun navigateToDetail(baseAPI: BaseAPI) {
        val toListDetailActivity =
            ListFragmentDirections.actionListFragmentToListDetailActivity().apply {
                setBaseAPI(baseAPI)
            }

        NavHostFragment.findNavController(this).apply {
            navigate(toListDetailActivity)
        }
    }
}
