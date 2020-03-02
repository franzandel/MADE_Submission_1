package com.example.favoriteshower.presentation.fragment

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favoriteshower.R
import com.example.favoriteshower.data.entities.api.base.BaseAPI
import com.example.favoriteshower.external.helper.MappingHelper
import com.example.favoriteshower.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.MOVIE_TABLE_NAME
import com.example.favoriteshower.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.TV_SHOW_TABLE_NAME
import com.example.favoriteshower.external.provider.contract.DatabaseContract.EntertainmentColumns.Companion.getContentURI
import com.example.favoriteshower.presentation.adapter.recyclerview.ListAPIAdapter
import com.example.favoriteshower.presentation.callback.OnAPIItemCallback
import com.example.favoriteshower.utils.hide
import com.example.favoriteshower.utils.show
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.*

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

    private var arrFavoriteBaseAPI = arrayListOf<BaseAPI>()
    private var listAPIAdapter: ListAPIAdapter = ListAPIAdapter(arrFavoriteBaseAPI)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRVLayoutManager()
        setupRVDivider()
        getFavoriteEntertainmentFromProvider()
    }

    private fun getFavoriteEntertainmentFromProvider() {
        GlobalScope.launch(Dispatchers.Main) {
            spinkit_progress.show()
            val listBaseAPI = getDeferredBaseAPIAsync().await()
            spinkit_progress.hide()

            setAPIAdapter(listBaseAPI)
            setListAPIAdapterClickListener()
        }
    }

    private fun getDeferredBaseAPIAsync(): Deferred<ArrayList<BaseAPI>> {
        return GlobalScope.async(Dispatchers.IO) {
            if (getPageIndex() == 0) {
                MappingHelper.mapCursorToArrayList(
                    getTableCursor(MOVIE_TABLE_NAME),
                    MOVIE_TABLE_NAME
                )
            } else {
                MappingHelper.mapCursorToArrayList(
                    getTableCursor(TV_SHOW_TABLE_NAME),
                    TV_SHOW_TABLE_NAME
                )
            }
        }
    }

    private fun getTableCursor(tableName: String): Cursor {
        return context?.contentResolver?.query(
            getContentURI(tableName),
            null,
            null,
            null,
            null
        ) as Cursor
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

    private fun getPageIndex(): Int? {
        return arguments?.getInt(ARG_PAGE_INDEX)
    }
}
