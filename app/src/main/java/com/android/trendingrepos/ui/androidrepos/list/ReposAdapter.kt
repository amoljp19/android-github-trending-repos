package com.android.trendingrepos.ui.androidrepos.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.trendingrepos.R
import com.android.trendingrepos.data.Repo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposAdapter(private val onRepoClick: (Repo) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    var repos: MutableList<Repo> = mutableListOf()
        set(repos) {
            field.addAll(repos)
            notifyDataSetChanged()
        }

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindRepo(repos[position], onRepoClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return ViewHolder(inflatedView)
    }
}

class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    fun bindRepo(repo: Repo, onRepoClick: (Repo) -> Unit) {
        itemView.setOnClickListener { onRepoClick(repo) }
        Picasso.get().load(repo.owner?.avatarUrl).into(itemView.img_owner_avatar)
        itemView.txt_repo_title.text = repo.name

        itemView.txt_repo_description.text = repo.description

        itemView.txt_repo_watchers.text = " " + String.format("%,d", repo?.forksCount)

        itemView.txt_repo_language.text = repo.language

    }
}