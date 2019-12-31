package com.example.madesubmission1.presentation.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madesubmission1.R
import com.example.madesubmission1.data.entities.base.BaseEntities
import com.example.madesubmission1.data.local.MoviesData
import com.example.madesubmission1.data.local.TvShowData
import com.example.madesubmission1.presentation.OnItemCallback
import com.example.madesubmission1.presentation.adapter.recyclerview.ListAdapter
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

    private lateinit var listAdapter: ListAdapter
    private var arrBaseEntities = arrayListOf<BaseEntities>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRVAdapter()
        setupRVDivider()
        setArrMoviesData()
        setupUIClickListener()
    }

    private fun setupRVAdapter() {
        rv_list.layoutManager = LinearLayoutManager(context)

        listAdapter =
            ListAdapter(
                arrBaseEntities
            )

        rv_list.adapter = listAdapter
    }

    private fun setupRVDivider() {
        rv_list.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setArrMoviesData() {
        if (arguments?.getInt(ARG_PAGE_INDEX) == 0) {
            val moviesData = MoviesData(
                resources.getStringArray(R.array.arr_movie_name),
                resources.getStringArray(R.array.arr_movie_top_cast),
                resources.getStringArray(R.array.arr_movie_release_date),
                resources.getStringArray(R.array.arr_movie_description)
            )

            arrBaseEntities.addAll(moviesData.listMovie)
        } else if (arguments?.getInt(ARG_PAGE_INDEX) == 1) {
            val tvShowData = TvShowData(
                resources.getStringArray(R.array.arr_tv_show_name),
                resources.getStringArray(R.array.arr_tv_show_top_cast),
                resources.getStringArray(R.array.arr_tv_show_release_date),
                resources.getStringArray(R.array.arr_tv_show_description)
            )

            arrBaseEntities.addAll(tvShowData.listTvShow)
        }
    }

    private fun setupUIClickListener() {
        listAdapter.setOnItemClickCallback(object : OnItemCallback {
            override fun onItemClicked(baseEntities: BaseEntities) {
                navigateToDetail(baseEntities)
            }
        })
    }

    private fun navigateToDetail(obj: BaseEntities) {
        val toListDetailActivity =
            ListFragmentDirections.actionListFragmentToListDetailActivity(obj)

        val navController = NavHostFragment.findNavController(this)

        navController.navigate(toListDetailActivity)
    }
}
