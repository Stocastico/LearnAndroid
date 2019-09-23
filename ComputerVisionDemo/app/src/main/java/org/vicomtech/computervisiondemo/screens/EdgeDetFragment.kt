package org.vicomtech.computervisiondemo.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import org.opencv.android.*
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.vicomtech.computervisiondemo.R
import timber.log.Timber

class EdgeDetFragment : Fragment(), CameraBridgeViewBase.CvCameraViewListener2 {

    private var imgRGBA: Mat  ?= null
    private var imgGray: Mat  ?= null
    private var imgEdge: Mat  ?= null

    private var camView : CameraBridgeViewBase? = null

    private val loader = object : BaseLoaderCallback(this.context) {
        override fun onManagerConnected(status: Int) {
            Timber.i("onManagerConnected called, status = %d", status)
            when (status) {
                SUCCESS -> {
                    Timber.i("Success, enabling camera")

                    System.loadLibrary("native-lib")

                    camView!!.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView called")
        return inflater.inflate(R.layout.edge_det_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated called")
        camView = view.findViewById<CameraBridgeViewBase>(R.id.main_surface)
        camView!!.visibility = SurfaceView.VISIBLE
        camView!!.setCvCameraViewListener(this)
        camView!!.setCameraPermissionGranted()
    }

    override fun onPause() {
        super.onPause()
        if (camView != null) {
            camView!!.disableView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (camView != null) {
            camView!!.disableView()
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume called")
        if (!OpenCVLoader.initDebug()) {
            Timber.i("OpenCv has some problems while loading ... ")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this.context, loader)
        } else {
            Timber.i("Success OpenCV loading xd")
            loader.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        Timber.i("onCameraViewStarted called")
        imgRGBA = Mat(height, width, CvType.CV_8UC4)
        imgGray = Mat(height, width, CvType.CV_8UC1)
        imgEdge = Mat(height, width, CvType.CV_8UC1)
    }

    override fun onCameraViewStopped() {
        imgRGBA!!.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        Timber.i("onCameraFrame called")
        imgRGBA = inputFrame.rgba()
        Imgproc.cvtColor(imgRGBA, imgGray, Imgproc.COLOR_RGBA2GRAY)
        Imgproc.Canny(imgGray, imgEdge, 50.0, 100.0)
        return imgEdge!!
    }

}
