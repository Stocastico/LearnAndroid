package org.vicomtech.computervisiondemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 */
class StylizationViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StylizationViewModel::class.java)) {
            return StylizationViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}