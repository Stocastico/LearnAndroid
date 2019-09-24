package org.vicomtech.computervisiondemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class TitleViewModel : ViewModel() {

    private val _navigateToEdgeDetection = MutableLiveData<Boolean>()
    val navigateToEdgeDetection: LiveData<Boolean>
        get() = _navigateToEdgeDetection

    private val _navigateToNativeEdgeDetection = MutableLiveData<Boolean>()
    val navigateToNativeEdgeDetection: LiveData<Boolean>
        get() = _navigateToNativeEdgeDetection

    fun onEdgeButtonClicked() {
        Timber.i("Edge button clicked!")
        _navigateToEdgeDetection.value = true
    }

    fun onNativeEdgeButtonClicked() {
        Timber.i("Native Edge button clicked!")
        _navigateToNativeEdgeDetection.value = true
    }

    fun doneNavigatingToEdge() {
        _navigateToEdgeDetection.value = false
    }

    fun doneNavigatingToNativeEdge() {
        _navigateToNativeEdgeDetection.value = false
    }

    init {
        Timber.i("TitleViewModel::init called")
        _navigateToEdgeDetection.value = false
        _navigateToNativeEdgeDetection.value = false
    }


}
