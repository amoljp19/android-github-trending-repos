package com.android.trendingrepos.ui.androidrepos.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.trendingrepos.Injection
import com.android.trendingrepos.R
import com.android.trendingrepos.data.Repo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_repo_detail.*


class RepoDetailFragment : Fragment(), RepoDetailView {
    private lateinit var presenter: RepoDetailPresenter

    companion object {

        private const val ARG_REPO_ID = "REPO_ID"

        fun newInstance(repoId: Long) =
                RepoDetailFragment().apply {
                    arguments = Bundle().apply { putLong(ARG_REPO_ID, repoId) }
                }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = RepoDetailPresenter(Injection.provideReposRepository())
        presenter.attachView(this)
        arguments?.let { presenter.loadRepo(it.getLong(ARG_REPO_ID)) }
    }

    override fun showRepo(repo: Repo) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = repo.name

        Picasso.get().load(repo.owner?.avatarUrl).into(img_owner_avatar)

        repo_title.text = repo.name

        repo_full_name.text = repo.fullName

        repo_full_description.text = repo.description

        repo_watchers.text = String.format("%,d", repo.watchers)

        repo_stars.text = String.format("%,d", repo.stargazersCount)

        repo_forks.text = String.format("%,d", repo.forksCount)

        repo_open_issues.text =  String.format("%,d", repo.openIssues)

        repo_language.text = repo.language
    }

    override fun showNoRepo() {

    }

    override fun showError(error: String) {
        if (error.isEmpty()) {
            return
        }
        context?.let { Toast.makeText(context, error, Toast.LENGTH_SHORT).show() }
    }

    override fun showProgress(show: Boolean) {
        // show some progress view
    }
}