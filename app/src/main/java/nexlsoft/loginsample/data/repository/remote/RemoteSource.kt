package nexlsoft.loginsample.data.repository.remote

import nexlsoft.loginsample.data.repository.model.Categories
import nexlsoft.loginsample.data.repository.model.User
import nexlsoft.loginsample.data.repository.model.UserResponse
import retrofit2.Response

interface RemoteSource {
    suspend fun getListCategories(): Response<Categories>

    suspend fun login(user:User) : Response<UserResponse>
}