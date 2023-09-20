package ru.resodostudios.flick.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.resodostudios.flick.core.network.FlickNetworkDataSource
import ru.resodostudios.flick.core.network.model.NetworkCast
import ru.resodostudios.flick.core.network.model.NetworkCastCredits
import ru.resodostudios.flick.core.network.model.NetworkCrew
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
    ): NetworkMovie

    @GET("people")
    suspend fun getPeople(): List<NetworkPerson>

    @GET("people/{id}")
    suspend fun getPerson(
        @Path("id") id: Int
    ): NetworkPerson

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
    ): List<NetworkCast>

    @GET("shows/{id}/crew")
    suspend fun getCrew(
        @Path("id") id: Int
    ): List<NetworkCrew>

    @GET("people/{id}/castcredits?embed[]=show&embed[]=character")
    suspend fun getCastCredits(
        @Path("id") id: Int
    ): List<NetworkCastCredits>
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

    override suspend fun getMovie(id: Int): NetworkMovie =
        networkApi.getMovie(id = id)

    override suspend fun getPeople(): List<NetworkPerson> =
        networkApi.getPeople()

    override suspend fun getPerson(id: Int): NetworkPerson =
        networkApi.getPerson(id = id)

    override suspend fun searchMovies(query: String): List<NetworkSearchMovie> =
        networkApi.searchMovies(query = query)

    override suspend fun searchPeople(query: String): List<NetworkSearchPeople> =
        networkApi.searchPeople(query = query)

    override suspend fun getCast(id: Int): List<NetworkCast> =
        networkApi.getCast(id = id)

    override suspend fun getCrew(id: Int): List<NetworkCrew> =
        networkApi.getCrew(id = id)

    override suspend fun getCastCredits(id: Int): List<NetworkCastCredits> =
        networkApi.getCastCredits(id = id)
}