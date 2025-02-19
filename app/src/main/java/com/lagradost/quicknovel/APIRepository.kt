package com.lagradost.quicknovel

import com.lagradost.quicknovel.mvvm.Resource
import com.lagradost.quicknovel.mvvm.normalSafeApiCall
import com.lagradost.quicknovel.mvvm.safeApiCall

data class OnGoingSearch(
    val apiName: String,
    val data: Resource<List<SearchResponse>>
)

class APIRepository(val api: MainAPI) {
    companion object {
        var providersActive = HashSet<String>()
    }

    val name: String get() = api.name
    val mainUrl: String get() = api.mainUrl
    val hasReviews: Boolean get() = api.hasReviews
    val rateLimitTime: Long get() = api.rateLimitTime
    val hasMainPage: Boolean get() = api.hasMainPage

    val iconId: Int? get() = api.iconId
    val iconBackgroundId: Int get() = api.iconBackgroundId
    val mainCategories: List<Pair<String, String>> get() = api.mainCategories
    val orderBys: List<Pair<String, String>> get() = api.orderBys
    val tags: List<Pair<String, String>> get() = api.tags

    suspend fun load(url: String): Resource<LoadResponse> {
        return safeApiCall {
            api.load(api.fixUrl(url))
        }
    }

    suspend fun search(query: String): Resource<List<SearchResponse>> {
        return safeApiCall {
            api.search(query)
        }
    }

    fun loadHtml(url: String): String? {
        return normalSafeApiCall {
            api.loadHtml(api.fixUrl(url))
        }
    }

    suspend fun loadReviews(url: String, page: Int, showSpoilers: Boolean = false): Resource<List<UserReview>> {
        return safeApiCall {
            api.loadReviews(url, page, showSpoilers)
        }
    }

    suspend fun loadMainPage(
        page: Int,
        mainCategory: String?,
        orderBy: String?,
        tag: String?,
    ): Resource<HeadMainPageResponse> {
        return safeApiCall {
            api.loadMainPage(page, mainCategory, orderBy, tag)
        }
    }

}