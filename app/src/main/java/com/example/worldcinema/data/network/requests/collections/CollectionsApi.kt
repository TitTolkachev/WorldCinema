package com.example.worldcinema.data.network.requests.collections

import com.example.worldcinema.data.network.dto.CollectionFormDto
import com.example.worldcinema.data.network.dto.CollectionListItemDto
import com.example.worldcinema.data.network.dto.MovieDto
import com.example.worldcinema.data.network.dto.MovieValueDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsApi {

    @GET("api/collections")
    suspend fun getCollections(): List<CollectionListItemDto>

    @POST("api/collections")
    suspend fun createCollection(@Body body: CollectionFormDto)

    @DELETE("api/collections/{collectionId}")
    suspend fun deleteCollection(@Path("collectionId") collectionId: String)

    @GET("api/collections/{collectionId}/movies")
    suspend fun getMoviesInCollection(@Path("collectionId") collectionId: String): List<MovieDto>

    @POST("api/collections/{collectionId}/movies")
    suspend fun addMovieToCollection(
        @Path("collectionId") collectionId: String,
        @Body body: MovieValueDto
    )

    @DELETE("api/collections/{collectionId}/movies")
    suspend fun deleteMovieFromCollection(
        @Path("collectionId") collectionId: String,
        @Query("movieId") movieId: String
    )
}