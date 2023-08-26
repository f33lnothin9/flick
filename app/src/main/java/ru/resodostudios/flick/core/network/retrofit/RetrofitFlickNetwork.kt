package ru.resodostudios.flick.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.Cast
import ru.resodostudios.flick.core.network.model.Crew
import ru.resodostudios.flick.core.network.model.Movie
import ru.resodostudios.flick.core.network.model.NetworkMovie
import ru.resodostudios.flick.core.network.model.NetworkPerson
import ru.resodostudios.flick.core.network.model.NetworkSearchMovie
import ru.resodostudios.flick.core.network.model.NetworkSearchPeople
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitFlickNetworkApi {

    @GET("shows")
    suspend fun getMovies(): List<NetworkMovie>

    @GET("shows/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): Response<Movie>

    @GET("people")
    suspend fun getPeople(): List<NetworkPerson>

    @GET("search/shows")
    suspend fun searchMovies(
        @Query("q") query: String
    ): List<NetworkSearchMovie>

    @GET("search/people")
    suspend fun searchPeople(
        @Query("q") query: String
    ): List<NetworkSearchPeople>

    @GET("shows/{id}/cast")
    suspend fun getCast(
        @Path("id") id: Int
    ): Response<List<Cast>>

    @GET("shows/{id}/crew")
    suspend fun getCrew(
        @Path("id") id: Int
    ): Response<List<Crew>>
}

private const val FLICK_BASE_URL = "https://api.tvmaze.com/"

@Singleton
class RetrofitFlickNetwork @Inject constructor(
    networkJson: Json
) : FlickNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(FLICK_BASE_URL)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitFlickNetworkApi::class.java)

    override suspend fun getMovies(): List<NetworkMovie> =
        networkApi.getMovies()

    override suspend fun getMovie(id: Int): Response<Movie> =
        networkApi.getMovie(id = id)

    override suspend fun getPeople(): List<NetworkPerson> =
        networkApi.getPeople()

    override suspend fun searchMovies(query: String): List<NetworkSearchMovie> =
        networkApi.searchMovies(query = query)

    override suspend fun searchPeople(query: String): List<NetworkSearchPeople> =
        networkApi.searchPeople(query = query)

    override suspend fun getCast(id: Int): Response<List<Cast>> =
        networkApi.getCast(id = id)

    override suspend fun getCrew(id: Int): Response<List<Crew>> =
        networkApi.getCrew(id = id)
}