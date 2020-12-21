package com.saifur.githubusersearch.data.model.listuser

data class Users(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)