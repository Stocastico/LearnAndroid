package org.vicomtech.computervisiondemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class StylizationViewModel : ViewModel() {

    private val _navigateToStyleResult = MutableLiveData<Boolean>()
    val navigateToStyleResult: LiveData<Boolean>
        get() = _navigateToStyleResult

    fun onCameraButtonClicked() {
        Timber.i("Stylization button clicked")
        _navigateToStyleResult.value = true
    }

    fun doneNavigatingToStyleResult() {
        _navigateToStyleResult.value = false
    }

    init {
        Timber.i("StylizationViewModel::init called")
        _navigateToStyleResult.value = false
    }
}