package hoannt.android.moviedb.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import hoannt.android.moviedb.R
import hoannt.android.moviedb.TestBroadcast
import hoannt.android.moviedb.ui.list.adapter.MovieListAdapter
import hoannt.android.moviedb.ui.list.viewmodel.MovieListViewmodel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private val TAG = "MainActivity_MinhLam"
    private lateinit var movieViewModel: MovieListViewmodel
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val navControllver = Navigation.findNavController(this, R.id.my_nav_host_fragment)
        setUpBottomNavMenu(navControllver)
        btn_search.setOnClickListener {
            navControllver.navigate(R.id.movieDetailFragment)
        }

        registerReceiver(TestBroadcast(), IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))

//        recyclerView.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        movieListAdapter = MovieListAdapter(emptyList())
//        recyclerView.adapter = movieListAdapter
//        movieViewModel =
//            ViewModelProviders.of(this, viewModelFactory).get(MovieListViewmodel::class.java)
//        movieViewModel.loadMoreMovie()
//        movieViewModel.getMovieLiveData().observe(this, Observer {
//            Log.i(TAG, "onCreate: ${it.data?.get(0)}")
//            if (!it.data.isNullOrEmpty()) {
//                Log.i(TAG, "onCreate: Go to it.data not null")
//                movieListAdapter.setList(it.data)
//            }
//        })
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.search_option -> {
//                Toast.makeText(this, "OnClick on Option Menu", Toast.LENGTH_SHORT).show()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun setUpBottomNavMenu(navController: NavController) {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

}