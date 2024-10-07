package ru.resodostudio.flick.core.network.retrofit

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.resodostudio.flick.core.network.BuildConfig
import ru.resodostudio.flick.core.network.FlickNetworkDataSource
import ru.resodostudio.flick.core.network.model.NetworkCastCredits
import ru.resodostudio.flick.core.network.model.NetworkCrewCredits
import ru.resodostudio.flick.core.network.model.NetworkImageExtended
import ru.resodostudio.flick.core.network.model.NetworkMovie
import ru.resodostudio.flick.core.network.model.NetworkPerson
import ru.resodostudio.flick.core.network.model.NetworkSearchMovie
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitFlickNetworkApi {

    @GET("shows")
    suspend fun getMovies(): List<NetworkMovie>

    @GET("shows/{id}")
    suspend fun getMovie(
        @Path("id") id: Int
    ): NetworkMovie

    @GET("shows/{id}/images")
    suspend fun getMovieImages(
        @Path("id") id: Int
    ): List<NetworkImageExtended>

    @GET("person/popular")
    suspend fun getPeople(): NetworkResult<List<NetworkPerson>>

    @GET("people/{id}")
    suspend fun getPerson(
        @Path("id") id: Int
    ): NetworkPerson

    @GET("search/shows")
    suspend fun searchMovies(
        @Query("q") query: String
    ): List<NetworkSearchMovie>

    @GET("people/{id}/castcredits?embed[]=show&embed[]=character")
    suspend fun getCastCredits(
        @Path("id") id: Int
    ): List<NetworkCastCredits>

    @GET("people/{id}/crewcredits?embed=show")
    suspend fun getCrewCredits(
        @Path("id") id: Int
    ): List<NetworkCrewCredits>
}

private const val FLICK_BASE_URL = BuildConfig.BACKEND_URL

@Serializable
private data class NetworkResult<T>(
    val results: T
)

@Singleton
class RetrofitFlickNetwork @Inject constructor(
    networkJson: Json,
    okHttpClient: OkHttpClient
) : FlickNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(FLICK_BASE_URL)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .client(okHttpClient)
        .build()
        .create(RetrofitFlickNetworkApi::class.java)

    override suspend fun getMovies(): List<NetworkMovie> =
        networkApi.getMovies()

    override suspend fun getMovie(id: Int): NetworkMovie =
        networkApi.getMovie(id = id)

    override suspend fun getMovieImages(id: Int): List<NetworkImageExtended> =
        networkApi.getMovieImages(id = id)

    override suspend fun getPeople(): List<NetworkPerson> =
        networkApi.getPeople().results

    override suspend fun getPerson(id: Int): NetworkPerson =
        networkApi.getPerson(id = id)

    override suspend fun searchMovies(query: String): List<NetworkSearchMovie> =
        networkApi.searchMovies(query = query)

    override suspend fun getCastCredits(id: Int): List<NetworkCastCredits> =
        networkApi.getCastCredits(id = id)

    override suspend fun getCrewCredits(id: Int): List<NetworkCrewCredits> =
        networkApi.getCrewCredits(id = id)
}