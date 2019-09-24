package org.vicomtech.computervisiondemo.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import org.vicomtech.computervisiondemo.R
import org.vicomtech.computervisiondemo.databinding.TitleFragmentBinding
import org.vicomtech.computervisiondemo.viewmodels.TitleViewModel
import org.vicomtech.computervisiondemo.viewmodels.TitleViewModelFactory

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModelFactory = TitleViewModelFactory()

        val titleViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            TitleViewModel::class.java
        )

        titleViewModel.navigateToEdgeDetection.observe(this, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(TitleFragmentDirections.actionTitleFragmentToEdgeDet())
                titleViewModel.doneNavigatingToEdge()
            }
        })

        titleViewModel.navigateToNativeEdgeDetection.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToEdgeDetNativeFragment())
                titleViewModel.doneNavigatingToNativeEdge()
            }
        })

        val binding: TitleFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.title_fragment, container, false
        )

        binding.setLifecycleOwner(this)

        binding.titleViewModel = titleViewModel

        return binding.root
        //return inflater.inflate(R.layout.title_fragment, container, false)

        //binding.cannyEdgeDetectionButton.setOnClickListener {
        //  findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToEdgeDet())
        //}
    }

}
