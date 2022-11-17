package nexlsoft.loginsample.data.repository.remote

import nexlsoft.loginsample.data.repository.model.Categories
import nexlsoft.loginsample.data.repository.model.User
import nexlsoft.loginsample.data.repository.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("categories")
    suspend fun getListCategories(): Response<Categories>

    @POST("auth/signup")
    suspend fun login(@Body loginRequest: User): Response<UserResponse>
}
