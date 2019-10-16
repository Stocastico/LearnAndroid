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

    private val _navigateToStylization = MutableLiveData<Boolean>()
    val navigateToStylization: LiveData<Boolean>
        get() = _navigateToStylization

    private val _navigateToPosenet = MutableLiveData<Boolean>()
    val navigateToPosenet: LiveData<Boolean>
        get() = _navigateToPosenet

    fun onEdgeButtonClicked() {
        Timber.i("Edge button clicked!")
        _navigateToEdgeDetection.value = true
    }

    fun onNativeEdgeButtonClicked() {
        Timber.i("Native Edge button clicked!")
        _navigateToNativeEdgeDetection.value = true
    }

    fun onStylizationButtonClicked() {
        Timber.i("Stylization button clicked")
        _navigateToStylization.value = true
    }

    fun onPosenetButtonClicked() {
        Timber.i("Stylization button clicked")
        _navigateToPosenet.value = true
    }

    fun doneNavigatingToEdge() {
        _navigateToEdgeDetection.value = false
    }

    fun doneNavigatingToNativeEdge() {
        _navigateToNativeEdgeDetection.value = false
    }

    fun doneNavigatingToStylization() {
        _navigateToStylization.value = false
    }

    fun doneNavigatingToPosenet() {
        _navigateToPosenet.value = false
    }

    init {
        Timber.i("TitleViewModel::init called")
        _navigateToEdgeDetection.value = false
        _navigateToNativeEdgeDetection.value = false
        _navigateToStylization.value = false
    }


}
