package com.example.clickclickclick.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kotlincoroutines.util.BACKGROUND
import com.example.android.kotlincoroutines.util.singleArgViewModelFactory
import com.example.clickclickclick.repository.TitleRefreshCallback
import com.example.clickclickclick.repository.TitleRefreshError
import com.example.clickclickclick.repository.TitleRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(val repository: TitleRepository):ViewModel() {

    companion object {
        /**
         * Factory for creating [MainViewModel]
         *
         * @param arg the repository to pass to [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }



    /**
     * Request a snackbar to display a string.
     *
     * This variable is private because we don't want to expose MutableLiveData
     *
     * MutableLiveData allows anyone to set a value, and MainViewModel is the only
     * class that should be setting values.
     */
    private val _snackBar=MutableLiveData<String>()


    /**
     * Request a snackbar to display a string.
     */

    val snackbar : LiveData<String>
     get() = _snackBar


    val title = repository.title


    private val _spinner = MutableLiveData<Boolean>(false)


    /**
     * Show a loading spinner if true
     */
    val spinner :LiveData<Boolean>
      get() = _spinner

    /**
     * Count of taps on the screen
     */
    private var tapCount = 0

    /**
     * LiveData with formatted tap count.
     */
    private val _taps = MutableLiveData<String>("$tapCount taps")

    /**
     * Public view of tap live data.
     */
    val taps: LiveData<String>
        get() = _taps



    /**
     * Respond to onClick events by refreshing the title.
     *
     * The loading spinner will display until a result is returned, and errors will trigger
     * a snackbar.
     */
    fun onMainViewClicked() {
        refreshTitle()
        updateTaps()
    }


    /**
     * Wait one second then update the tap count.
     */
    private fun updateTaps() {
        // TODO: Convert updateTaps to use coroutines
        tapCount++

        //Scope starts when the onstart of the activity is called  is called and ends when the on clear method in viewmodel class is called is called
        //Couroutine code
        viewModelScope.launch {
            delay(1_000)
            _taps.postValue("${tapCount} taps")
        }

//        BACKGROUND.submit {
//            Thread.sleep(1_000)
//            _taps.postValue("${tapCount} taps")
//        }
    }

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        _snackBar.value = null
    }

    /**
     * Refresh the title, showing a loading spinner while it refreshes and errors via snackbar.
     */
    fun refreshTitle() {
        // TODO: Convert refreshTitle to use coroutines
   viewModelScope.launch {

       try{
           _spinner.value = true

           repository.refreshTitle()

       }catch (error:TitleRefreshError){
           _snackBar.value=error.message
       }finally {
                    _spinner.value=false
       }

   }
//        repository.refreshTitleWithCallbacks(object : TitleRefreshCallback {
//            override fun onCompleted() {
//                _spinner.postValue(false)
//            }
//
//            override fun onError(cause: Throwable) {
//                _snackBar.postValue(cause.message)
//                _spinner.postValue(false)
//            }
//        })
    }

}