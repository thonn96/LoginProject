package nexlsoft.loginsample.ui.categories

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import nexlsoft.loginsample.base.BaseViewModel
import nexlsoft.loginsample.data.repository.model.Categories
import nexlsoft.loginsample.data.repository.remote.RemoteSource


class CategoriesViewModel(
    private val remote: RemoteSource,
) : BaseViewModel() {

    var categories: Categories? = null
    private val mutableLV = MutableLiveData<Categories>()
    val liveData : LiveData<Categories> =  mutableLV
    fun getListCategories() {
        launchCoroutine {
            if(remote.getListCategories().isSuccessful){
                categories = remote.getListCategories().body()
                mutableLV.postValue(categories)
            }
        }
    }


}

