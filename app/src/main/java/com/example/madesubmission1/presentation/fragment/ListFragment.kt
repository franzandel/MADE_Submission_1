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
import com.example.madesubmission1.data.entities.api.APIStateHandler
import com.example.madesubmission1.data.entities.api.MovieAPI
import com.example.madesubmission1.data.entities.api.TvShowAPI
import com.example.madesubmission1.data.entities.api.base.BaseAPI
import com.example.madesubmission1.presentation.activity.RootActivity
import com.example.madesubmission1.presentation.adapter.recyclerview.ListAPIAdapter
import com.example.madesubmission1.presentation.callback.OnAPIItemCallback
import com.example.madesubmission1.presentation.viewmodel.ListFragmentViewModel
import com.example.madesubmission1.utils.hide
import com.example.madesubmission1.utils.showToast
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
    private var arrBaseAPI = arrayListOf<BaseAPI>()
    private var arrFavoriteBaseAPI = arrayListOf<BaseAPI>()
    private var listAPIAdapter: ListAPIAdapter = ListAPIAdapter(arrBaseAPI)

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
        setListAPIAdapterClickListener()
    }

    private fun initializeVM() {
        listFragmentViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(this.activity!!.application)
        ).get(
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
            if (RootActivity.appSession.getHasUserClickedFavorite()) {
                if (RootActivity.appSession.getIsShowingFavorite()) {
                    if (arrFavoriteBaseAPI.isNullOrEmpty()) {
                        getFavoriteMovieAPIFromDB()
                    } else {
                        hideProgressBar()
                        setAPIAdapter(arrFavoriteBaseAPI)
                    }
                } else {
                    if (arrBaseAPI.isNullOrEmpty()) {
                        getMovieAPIFromDB()
                    } else {
                        hideProgressBar()
                        setAPIAdapter(arrBaseAPI)
                    }
                }
            } else {
                getMovieAPI()
            }
        } else if (arguments?.getInt(ARG_PAGE_INDEX) == 1) {
            if (RootActivity.appSession.getHasUserClickedFavorite()) {
                if (RootActivity.appSession.getIsShowingFavorite()) {
                    if (arrFavoriteBaseAPI.isNullOrEmpty()) {
                        getFavoriteTvShowAPIFromDB()
                    } else {
                        hideProgressBar()
                        setAPIAdapter(arrFavoriteBaseAPI)
                    }
                } else {
                    if (arrBaseAPI.isNullOrEmpty()) {
                        getTvShowAPIFromDB()
                    } else {
                        hideProgressBar()
                        setAPIAdapter(arrBaseAPI)
                    }
                }
            } else {
                getTvShowAPI()
            }
        }
    }

    private fun handlingMovieAPIAfterSuccessFetch(apiStateHandler: APIStateHandler<MovieAPI>) {
        hideProgressBar()

        if (apiStateHandler.error == null) {
            apiStateHandler.baseAPIResponse?.let {
                if (listAPIAdapter.itemCount == 0) {
                    setAPIAdapter(it.results)
                }

                insertMovieAPIIntoDBIfNotExist(it.results)
            }
        } else {
            context?.showToast(getString(R.string.load_api_failed), Toast.LENGTH_LONG)
        }
    }

    private fun handlingTvShowAPIAfterSuccessFetch(apiStateHandler: APIStateHandler<TvShowAPI>) {
        hideProgressBar()

        if (apiStateHandler.error == null) {
            apiStateHandler.baseAPIResponse?.let {
                if (listAPIAdapter.itemCount == 0) {
                    setAPIAdapter(it.results)
                }

                insertTvShowAPIIntoDBIfNotExist(it.results)
            }
        } else {
            context?.showToast(getString(R.string.load_api_failed), Toast.LENGTH_LONG)
        }
    }

    private fun insertMovieAPIIntoDBIfNotExist(listMovieAPI: List<MovieAPI>) {
        listFragmentViewModel.getAllMovieAPIFromDB { listMovieAPIFromDB ->
            if (listMovieAPIFromDB.isNullOrEmpty()) {
                listFragmentViewModel.insertAllMovieAPIIntoDB(listMovieAPI)
            }
        }
    }

    private fun insertTvShowAPIIntoDBIfNotExist(listTvShowAPI: List<TvShowAPI>) {
        listFragmentViewModel.getAllTvShowAPIFromDB { listTvShowAPIFromDB ->
            if (listTvShowAPIFromDB.isNullOrEmpty()) {
                listFragmentViewModel.insertAllTvShowAPIIntoDB(listTvShowAPI)
            }
        }
    }

    private fun getMovieAPI() {
        listFragmentViewModel.getListMovieFromAPI()
            .observe(viewLifecycleOwner, Observer { apiStateHandler ->
                handlingMovieAPIAfterSuccessFetch(apiStateHandler)
            })
    }

    private fun getTvShowAPI() {
        listFragmentViewModel.getListTvShowFromAPI()
            .observe(viewLifecycleOwner, Observer { apiStateHandler ->
                handlingTvShowAPIAfterSuccessFetch(apiStateHandler)
            })
    }

    private fun getFavoriteMovieAPIFromDB() {
        listFragmentViewModel.getAllFavoriteMovieAPIFromDB { listFavoriteMovieAPI ->
            hideProgressBar()
            setAPIAdapter(listFavoriteMovieAPI)
        }
    }

    private fun getFavoriteTvShowAPIFromDB() {
        listFragmentViewModel.getAllFavoriteTvShowAPIFromDB { listFavoriteTvShowAPI ->
            hideProgressBar()
            setAPIAdapter(listFavoriteTvShowAPI)
        }
    }

    private fun getMovieAPIFromDB() {
        listFragmentViewModel.getAllMovieAPIFromDB { listMovieAPI ->
            hideProgressBar()
            setAPIAdapter(listMovieAPI)
        }
    }

    private fun getTvShowAPIFromDB() {
        listFragmentViewModel.getAllTvShowAPIFromDB { listTvShowAPI ->
            hideProgressBar()
            setAPIAdapter(listTvShowAPI)
        }
    }

    private fun hideProgressBar() {
        spinkit_progress.hide()
    }

    private fun setAPIAdapter(listBaseAPI: List<BaseAPI>) {
        if (RootActivity.appSession.getIsShowingFavorite()) {
            if (arrFavoriteBaseAPI.isEmpty())
                arrFavoriteBaseAPI.addAll(listBaseAPI)

            setAPIAdapter(arrFavoriteBaseAPI)
            setListAPIAdapterClickListener()
        } else {
            if (arrBaseAPI.isEmpty())
                arrBaseAPI.addAll(listBaseAPI)

            setAPIAdapter(arrBaseAPI)
            setListAPIAdapterClickListener()
        }
    }

    private fun setAPIAdapter(arrBaseAPI: ArrayList<BaseAPI>) {
        listAPIAdapter = ListAPIAdapter(arrBaseAPI)
        rv_list.adapter = listAPIAdapter
    }

    private fun setListAPIAdapterClickListener() {
        listAPIAdapter.setOnAPIItemClickCallback(object :
            OnAPIItemCallback {
            override fun onItemClicked(baseAPI: BaseAPI) {
                navigateToDetail(baseAPI)
            }
        })
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
