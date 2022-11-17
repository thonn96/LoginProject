package nexlsoft.loginsample.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    var coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler {
            _, exception ->
        exception.printStackTrace()
    }

    protected fun launchCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            block()
        }
    }

}




