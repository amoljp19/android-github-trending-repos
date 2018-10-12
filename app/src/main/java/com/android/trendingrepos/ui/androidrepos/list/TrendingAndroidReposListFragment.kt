package com.android.trendingrepos.ui.androidrepos.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.trendingrepos.Injection
import com.android.trendingrepos.R
import com.android.trendingrepos.data.Repo


class TrendingAndroidReposListFragment : Fragment(), TrendingAndroidReposView {

    private var presenter: TrendingAndroidReposPresenter
            = TrendingAndroidReposPresenter(Injection.provideReposRepository())
    private val listAdapter = ReposAdapter { listener?.onRepoSelected(it) }
    private var listener: OnRepoSelectedListener? = null

    companion object {
        fun newInstance() = TrendingAndroidReposListFragment()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnRepoSelectedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnRepoSelectedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_trending_android_repos_list, container, false)

        with(root) {
            findViewById<RecyclerView>(R.id.rv_repos).apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = listAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val rv = recyclerView ?: return
                        val layoutManager = rv.layoutManager as LinearLayoutManager
                        val visibleItemCount = layoutManager.childCount
				        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - 2
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= TrendingAndroidReposPresenter.PAGE_SIZE) {
                            presenter.loadMoreRepos()
                        }

                    }
                })
            }

            findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_container).apply {
                setColorSchemeColors(
                        ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                        ContextCompat.getColor(requireContext(), R.color.colorAccent),
                        ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
                )
                setOnRefreshListener { presenter.loadRepos(false) }
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
        if (listAdapter.itemCount == 0) {
            presenter.loadRepos(true)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setTitle(R.string.app_name)
    }


    override fun showRepos(repos: List<Repo>) {
        listAdapter.repos = repos.toMutableList()
    }

    override fun showError(error: String) {
        if (error.isEmpty()) {
            return
        }
        context?.let { Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
    }

    override fun showProgress(show: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_container)) {
            post { isRefreshing = show }
        }
    }

    interface OnRepoSelectedListener {
        fun onRepoSelected(repo: Repo)
    }
}