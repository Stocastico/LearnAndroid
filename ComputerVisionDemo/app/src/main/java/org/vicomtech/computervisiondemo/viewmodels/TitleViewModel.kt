package org.vicomtech.computervisiondemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class TitleViewModel : ViewModel() {

    private val _navigateToEdgeDetection = MutableLiveData<Boolean>()
    val navigateToEdgeDetection: LiveData<Boolean>
        get() = _navigateToEdgeDetection

    fun onEdgeButtonClicked() {
        Timber.i("Edge button clicked!")
        _navigateToEdgeDetection.value = true
    }

    fun doneNavigatingToEdge() {
        _navigateToEdgeDetection.value = false
    }

    init {
        Timber.i("TitleViewModel::init called")
        _navigateToEdgeDetection.value = false
    }


}
