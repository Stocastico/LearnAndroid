package org.vicomtech.computervisiondemo.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader

import org.vicomtech.computervisiondemo.R
import timber.log.Timber


class StylizationResultFragment : Fragment() {


    private val loader = object : BaseLoaderCallback(this.context) {
        override fun onManagerConnected(status: Int) {
            Timber.i("onManagerConnected called, status = %d", status)
            when (status) {
                SUCCESS -> {
                    Timber.i("Success, enabling camera")

                    System.loadLibrary("native-lib")

                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.stylization_result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated called")

        val imgPath = arguments?.getString("imgPath") ?: ""

        Timber.i("Img path = %s", imgPath)

        //Load image and display it
        createStyle(imgPath)
    }

    fun createStyle(imgPath : String)
    {

    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Timber.i("OpenCv has some problems while loading ... ")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this.context, loader)
        } else {
            Timber.i("Success OpenCV loading xd")
            loader.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }


    private external fun stylizationFromJNI(input: Long, output: Long)
}
