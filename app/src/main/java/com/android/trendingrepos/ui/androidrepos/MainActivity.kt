package com.android.trendingrepos.ui.androidrepos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.android.trendingrepos.R
import com.android.trendingrepos.data.Repo
import com.android.trendingrepos.ui.androidrepos.detail.RepoDetailFragment
import com.android.trendingrepos.ui.androidrepos.list.TrendingAndroidReposListFragment
import com.android.trendingrepos.util.extensions.replaceFragmentInActivity
import com.android.trendingrepos.util.extensions.setupActionBar


class MainActivity : AppCompatActivity(), TrendingAndroidReposListFragment.OnRepoSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(false)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.findFragmentById(R.id.contentFrame)
                    as? TrendingAndroidReposListFragment ?: TrendingAndroidReposListFragment.newInstance().also {
                replaceFragmentInActivity(it, R.id.contentFrame)
            }
        }
        supportFragmentManager.addOnBackStackChangedListener {
            setupActionBar(R.id.toolbar) {
                setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
            }
        }
    }

    override fun onRepoSelected(repo: Repo) {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as? RepoDetailFragment ?: RepoDetailFragment.newInstance(repo.id).also {
            replaceFragmentInActivity(it, R.id.contentFrame, true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
