package nexlsoft.loginsample.data.repository.remote

import android.util.Log
import nexlsoft.loginsample.data.repository.model.Categories
import nexlsoft.loginsample.data.repository.model.User
import nexlsoft.loginsample.data.repository.model.UserResponse
import retrofit2.Response

class RemoteImpl ( private val apiService: ApiService ) : RemoteSource {
    override suspend fun getListCategories(): Response<Categories> {
        return apiService.getListCategories()
    }

    override suspend fun login(user : User): Response<UserResponse> {
        return apiService.login(user)
    }
}
