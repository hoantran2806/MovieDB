package hoannt.android.moviedb.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import hoannt.android.moviedb.R
import hoannt.android.moviedb.data.local.entity.MovieEntity
import hoannt.android.moviedb.ui.base.customview.PagerSnapHelper
import hoannt.android.moviedb.ui.list.adapter.MovieListAdapter
import hoannt.android.moviedb.ui.list.viewmodel.ShareMovieViewModel
import hoannt.android.moviedb.ui.search.viewmodel.SearchListViewModel
import io.reactivex.Observable
import io.reactivex.functions.Predicate
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchFragment : Fragment(), MovieListAdapter.RecyclerViewItemClick {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var searchListViewModel: SearchListViewModel
    private lateinit var searchListAdapter: MovieListAdapter
    private val TAG = "SearchFragment_MinhLam"
    private lateinit var shardViewModel: ShareMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        searchListViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        // Inflate the layout for this fragment

        setUpAdapter(view)
        shardViewModel = ViewModelProviders.of(this).get(ShareMovieViewModel::class.java)
        searchListViewModel.getSearchResult().observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                searchListAdapter = MovieListAdapter(it.data as MutableList<MovieEntity>, this)
                list_searched.adapter = searchListAdapter
            }
        })


        return view
    }

    private fun setUpAdapter(view: View) {
        view.list_searched.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        var snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(view.list_searched)
        view.btn_search.setOnClickListener {
            if (!view.edt_search.query.isNullOrEmpty()) {
                searchListViewModel.search(view.edt_search.query.toString(), 1L)
            }
        }
        searchFromView(view.edt_search).debounce(300, TimeUnit.MILLISECONDS)
            .filter(object : Predicate<String> {
                override fun test(t: String): Boolean {
                    return !t.isNullOrEmpty()
                }
            })
            .distinctUntilChanged()
    }

    private fun searchFromView(searchView: SearchView): Observable<String> {
        var subject: PublishSubject<String> = PublishSubject.create()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                Log.i(TAG, "onQueryTextSubmit: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    subject.onNext(newText)
//                    Log.i(TAG, "onQueryTextChange: newText = $newText")
//                    subject
//                        .debounce(500, TimeUnit.MILLISECONDS).subscribe(Consumer {
////                            searchListViewModel.search(it, 1L)
//                            Log.i(TAG, "onQueryTextChange using debounce: $it")
//                        })
                }
                return true
            }

        })
        return subject
    }

    override fun onItemSelected(movieEntity: MovieEntity, imageView: ImageView) {
        Log.i(TAG, "onItemSelected: ${movieEntity.id}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchListViewModel.onViewModelStop()
    }
}