package org.vicomtech.computervisiondemo.screens

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import org.opencv.android.*
import android.os.Environment
//import org.opencv.core.CvType
//import org.opencv.core.Mat
import org.vicomtech.computervisiondemo.R
import timber.log.Timber
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import io.fotoapparat.Fotoapparat
import io.fotoapparat.view.CameraView
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import org.vicomtech.computervisiondemo.databinding.StylizationFragmentBinding
import org.vicomtech.computervisiondemo.viewmodels.StylizationViewModel
import org.vicomtech.computervisiondemo.viewmodels.StylizationViewModelFactory
import java.io.File
import java.util.concurrent.Semaphore
import io.fotoapparat.selector.*
import org.vicomtech.computervisiondemo.MainActivity

class StylizationFragment : Fragment() {

//    private var imgRGBA: Mat ?= null
//    private var imgBGR: Mat  ?= null
//    private var imgStyle: Mat ?= null


    /** Saving picture stuff */
    var fotoapparat: Fotoapparat? = null
    var fotoapparatState : FotoapparatState? = null
    val filename = "test.png"
    val sd = Environment.getExternalStorageDirectory()
    val dest = File(sd, filename)

    /** A [Semaphore] to prevent the app from exiting before closing the camera.    */
    private val cameraOpenCloseLock = Semaphore(1)

    private val loader = object : BaseLoaderCallback(this.context) {
//        override fun onManagerConnected(status: Int) {
//            Timber.i("onManagerConnected called, status = %d", status)
//            when (status) {
//                SUCCESS -> {
//                    Timber.i("Success, enabling camera")
//
//                    System.loadLibrary("native-lib")
//
//                    cameraView!!.enableView()
//                }
//                else -> {
//                    super.onManagerConnected(status)
//                }
//            }
//        }
    }

    private fun createFotoapparat(cameraView: CameraView){

        fotoapparat = Fotoapparat(
            context = context!!,
            view = cameraView,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back(),
            logger = loggers(
                logcat()
            ),
            cameraErrorCallback = { error ->
                println("Recorder errors: $error")
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView called")

        val viewModelFactory = StylizationViewModelFactory()

        val stylizationViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            StylizationViewModel::class.java
        )

        stylizationViewModel.navigateToStyleResult.observe(this, Observer {
            if (it == true) {
                takePhoto()

                val pathArg: String = dest.absolutePath ?: ""

                this.findNavController().navigate(StylizationFragmentDirections.actionStylizationFragmentToStylizationResultFragment(pathArg))
                stylizationViewModel.doneNavigatingToStyleResult()
            }
        })

        val binding : StylizationFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.stylization_fragment, container, false
        )

        binding.setLifecycleOwner(this)

        binding.stylizationViewModel = stylizationViewModel

        return binding.root

        //return inflater.inflate(R.layout.stylization_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated called")
        val cameraView = view.findViewById<CameraView>(R.id.main_surface)
        //cameraView!!.visibility = CameraView.VISIBLE

        createFotoapparat(cameraView)
        fotoapparatState = FotoapparatState.OFF
    }

    private fun takePhoto() {
        fotoapparat
            ?.takePicture()
            ?.saveToFile(dest)
    }

    override fun onStart() {
        super.onStart()
        fotoapparat?.start()
        fotoapparatState = FotoapparatState.ON
    }

    override fun onStop() {
        super.onStop()
        fotoapparat?.stop()
        FotoapparatState.OFF
    }

    override fun onResume() {
        super.onResume()
        if(fotoapparatState == FotoapparatState.OFF) {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }


    /**
     * Sets up member variables related to camera.
     */
//    private fun setUpCameraOutputs() {
//
//        val activity = activity
//        val manager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        try {
//            for (cameraId in manager.cameraIdList) {
//                val characteristics = manager.getCameraCharacteristics(cameraId)
//
//                // We don't use a front facing camera in this sample.
//                val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
//                if (cameraDirection != null &&
//                    cameraDirection == CameraCharacteristics.LENS_FACING_FRONT
//                ) {
//                    continue
//                }
//
//                previewSize = Size(PREVIEW_WIDTH, PREVIEW_HEIGHT)
//
//                imageReader = ImageReader.newInstance(
//                    PREVIEW_WIDTH, PREVIEW_HEIGHT,
//                    ImageFormat.YUV_420_888, /*maxImages*/ 2
//                )
//
//                sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
//
//                previewHeight = previewSize!!.height
//                previewWidth = previewSize!!.width
//
//                // Initialize the storage bitmaps once when the resolution is known.
//                rgbBytes = IntArray(previewWidth * previewHeight)
//
//                // Check if the flash is supported.
//                flashSupported =
//                    characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
//
//                this.cameraId = cameraId
//
//                // We've found a viable camera and finished setting up member variables,
//                // so we don't need to iterate through other available cameras.
//                return
//            }
//        } catch (e: CameraAccessException) {
//            Timber.e(e.toString())
//        } catch (e: NullPointerException) {
//            // Currently an NPE is thrown when the Camera2API is used but not supported on the
//            // device this code runs.
//            PosenetFragment.ErrorDialog.newInstance(getString(R.string.camera_error))
//                .show(childFragmentManager, "dialog")
//        }
//    }
//
//    /**
//     * Opens the camera specified by [PosenetActivity.cameraId].
//     */
//    private fun openCamera() {
//        val permissionCamera = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
//
//        setUpCameraOutputs()
//        val manager = activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        try {
//            // Wait for camera to open - 2.5 seconds is sufficient
//            if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
//                throw RuntimeException("Time out waiting to lock camera opening.")
//            }
//            manager.openCamera(cameraId!!, stateCallback, backgroundHandler)
//        } catch (e: CameraAccessException) {
//            Timber.e(e.toString())
//        } catch (e: InterruptedException) {
//            throw RuntimeException("Interrupted while trying to lock camera opening.", e)
//        }
//    }
//
//    /**
//     * Closes the current [CameraDevice].
//     */
//    private fun closeCamera() {
//        if (captureSession == null) {
//            return
//        }
//
//        try {
//            cameraOpenCloseLock.acquire()
//            captureSession!!.close()
//            captureSession = null
//            cameraDevice!!.close()
//            cameraDevice = null
//            imageReader!!.close()
//            imageReader = null
//        } catch (e: InterruptedException) {
//            throw RuntimeException("Interrupted while trying to lock camera closing.", e)
//        } finally {
//            cameraOpenCloseLock.release()
//        }
//    }
//
//    /**
//     * Starts a background thread and its [Handler].
//     */
//    private fun startBackgroundThread() {
//        backgroundThread = HandlerThread("imageAvailableListener").also { it.start() }
//        backgroundHandler = Handler(backgroundThread!!.looper)
//    }
//
//    /**
//     * Stops the background thread and its [Handler].
//     */
//    private fun stopBackgroundThread() {
//        backgroundThread?.quitSafely()
//        try {
//            backgroundThread?.join()
//            backgroundThread = null
//            backgroundHandler = null
//        } catch (e: InterruptedException) {
//            Timber.e(e.toString())
//        }
//    }
//
//    /** Fill the yuvBytes with data from image planes.   */
//    private fun fillBytes(planes: Array<Image.Plane>, yuvBytes: Array<ByteArray?>) {
//        // Row stride is the total number of bytes occupied in memory by a row of an image.
//        // Because of the variable row stride it's not possible to know in
//        // advance the actual necessary dimensions of the yuv planes.
//        for (i in planes.indices) {
//            val buffer = planes[i].buffer
//            if (yuvBytes[i] == null) {
//                yuvBytes[i] = ByteArray(buffer.capacity())
//            }
//            buffer.get(yuvBytes[i]!!)
//        }
//    }
//
//    /** A [OnImageAvailableListener] to receive frames as they are available.  */
//    private var imageAvailableListener = object : ImageReader.OnImageAvailableListener {
//        override fun onImageAvailable(imageReader: ImageReader) {
//            // We need wait until we have some size from onPreviewSizeChosen
//            if (previewWidth == 0 || previewHeight == 0) {
//                return
//            }
//
//            val image = imageReader.acquireLatestImage() ?: return
//            fillBytes(image.planes, yuvBytes)
//
//            ImageUtils.convertYUV420ToARGB8888(
//                yuvBytes[0]!!,
//                yuvBytes[1]!!,
//                yuvBytes[2]!!,
//                previewWidth,
//                previewHeight,
//                /*yRowStride=*/ image.planes[0].rowStride,
//                /*uvRowStride=*/ image.planes[1].rowStride,
//                /*uvPixelStride=*/ image.planes[1].pixelStride,
//                rgbBytes
//            )
//
//            // Create bitmap from int array
//            val imageBitmap = Bitmap.createBitmap(
//                rgbBytes, previewWidth, previewHeight,
//                Bitmap.Config.ARGB_8888
//            )
//
//            // Create rotated version for portrait display
//            val rotateMatrix = Matrix()
//            rotateMatrix.postRotate(90.0f)
//
//            val rotatedBitmap = Bitmap.createBitmap(
//                imageBitmap, 0, 0, previewWidth, previewHeight,
//                rotateMatrix, true
//            )
//            image.close()
//
//            // Process an image for analysis in every 3 frames.
////            frameCounter = (frameCounter + 1) % 3
////            if (frameCounter == 0) {
//                processImage(rotatedBitmap)
//            //}
//        }
//    }
//
//    /** Set the paint color and size.    */
//    private fun setPaint() {
//        paint.color = Color.RED
//        paint.textSize = 80.0f
//        paint.strokeWidth = 8.0f
//    }
//
//    /** Draw bitmap on Canvas.   */
//    private fun draw(canvas: Canvas, bitmap: Bitmap) {
//        val screenWidth: Int = canvas.width
//        val screenHeight: Int = canvas.height
//        setPaint()
//        canvas.drawBitmap(
//            bitmap,
//            Rect(0, 0, previewHeight, previewWidth),
//            Rect(0, 0, screenWidth, screenHeight),
//            paint
//        )
//
////        val widthRatio = screenWidth.toFloat() / MODEL_WIDTH
////        val heightRatio = screenHeight.toFloat() / MODEL_HEIGHT
////
////        // Draw key points over the image.
////        for (keyPoint in person.keyPoints) {
////            if (keyPoint.score > minConfidence) {
////                val position = keyPoint.position
////                val adjustedX: Float = position.x.toFloat() * widthRatio
////                val adjustedY: Float = position.y.toFloat() * heightRatio
////                canvas.drawCircle(adjustedX, adjustedY, circleRadius, paint)
////            }
////        }
////
////        for (line in bodyJoints) {
////            if (
////                (person.keyPoints[line.first.ordinal].score > minConfidence) and
////                (person.keyPoints[line.second.ordinal].score > minConfidence)
////            ) {
////                canvas.drawLine(
////                    person.keyPoints[line.first.ordinal].position.x.toFloat() * widthRatio,
////                    person.keyPoints[line.first.ordinal].position.y.toFloat() * heightRatio,
////                    person.keyPoints[line.second.ordinal].position.x.toFloat() * widthRatio,
////                    person.keyPoints[line.second.ordinal].position.y.toFloat() * heightRatio,
////                    paint
////                )
////            }
////        }
////
////        // Draw confidence score of a person.
////        val scoreMessage = "SCORE: " + "%.2f".format(person.score)
////        canvas.drawText(
////            scoreMessage,
////            (15.0f * widthRatio),
////            (243.0f * heightRatio),
////            paint
////        )
//
//        // Draw!
//        surfaceHolder!!.unlockCanvasAndPost(canvas)
//    }
//
//    /** Process image using Posenet library.   */
//    private fun processImage(bitmap: Bitmap) {
//        // Crop bitmap.
////        val croppedBitmap = cropBitmap(bitmap)
////
////        // Created scaled version of bitmap for model input.
////        val scaledBitmap = Bitmap.createScaledBitmap(croppedBitmap, MODEL_WIDTH, MODEL_HEIGHT, true)
//
//        // Perform inference.
//        val canvas: Canvas = surfaceHolder!!.lockCanvas()
//        draw(canvas, bitmap)
//    }
//
//    /**
//     * Creates a new [CameraCaptureSession] for camera preview.
//     */
//    private fun createCameraPreviewSession() {
//        try {
//
//            // We capture images from preview in YUV format.
//            imageReader = ImageReader.newInstance(
//                previewSize!!.width, previewSize!!.height, ImageFormat.YUV_420_888, 2
//            )
//            imageReader!!.setOnImageAvailableListener(imageAvailableListener, backgroundHandler)
//
//            // This is the surface we need to record images for processing.
//            val recordingSurface = imageReader!!.surface
//
//            // We set up a CaptureRequest.Builder with the output Surface.
//            previewRequestBuilder = cameraDevice!!.createCaptureRequest(
//                CameraDevice.TEMPLATE_PREVIEW
//            )
//            previewRequestBuilder!!.addTarget(recordingSurface)
//
//            // Here, we create a CameraCaptureSession for camera preview.
//            cameraDevice!!.createCaptureSession(
//                listOf(recordingSurface),
//                object : CameraCaptureSession.StateCallback() {
//                    override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
//                        // The camera is already closed
//                        if (cameraDevice == null) return
//
//                        // When the session is ready, we start displaying the preview.
//                        captureSession = cameraCaptureSession
//                        try {
//                            // Auto focus should be continuous for camera preview.
//                            previewRequestBuilder!!.set(
//                                CaptureRequest.CONTROL_AF_MODE,
//                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
//                            )
//                            // Flash is automatically enabled when necessary.
//                            setAutoFlash(previewRequestBuilder!!)
//
//                            // Finally, we start displaying the camera preview.
//                            previewRequest = previewRequestBuilder!!.build()
//                            captureSession!!.setRepeatingRequest(
//                                previewRequest!!,
//                                captureCallback, backgroundHandler
//                            )
//                        } catch (e: CameraAccessException) {
//                            Timber.e(e.toString())
//                        }
//                    }
//
//                    override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession) {
//                        showToast("Failed")
//                    }
//                },
//                null
//            )
//        } catch (e: CameraAccessException) {
//            Timber.e(e.toString())
//        }
//    }
//
//    private fun setAutoFlash(requestBuilder: CaptureRequest.Builder) {
//        if (flashSupported) {
//            requestBuilder.set(
//                CaptureRequest.CONTROL_AE_MODE,
//                CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
//            )
//        }
//    }

//    override fun onPause() {
//        super.onPause()
//        if (cameraView != null) {
//            cameraView!!.disableView()
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        if (cameraView != null) {
//            cameraView!!.disableView()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Timber.i("onResume called")
////        if (!OpenCVLoader.initDebug()) {
////            Timber.i("OpenCv has some problems while loading ... ")
////            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this.context, loader)
////        } else {
////            Timber.i("Success OpenCV loading xd")
////            loader.onManagerConnected(LoaderCallbackInterface.SUCCESS)
////        }
//    }

//    override fun onCameraViewStarted(width: Int, height: Int) {
//        Timber.i("onCameraViewStarted called")
//        imgRGBA = Mat(height, width, CvType.CV_8UC4)
//        imgBGR = Mat(height, width, CvType.CV_8UC3)
//        imgStyle = Mat(height, width, CvType.CV_8UC3)
//    }
//
//    override fun onCameraViewStopped() {
//        imgRGBA!!.release()
//    }
//
//    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
//        val result = Mat()
//
////        imgRGBA = inputFrame.rgba()
////        Imgproc.cvtColor(imgRGBA, imgBGR, Imgproc.COLOR_RGBA2BGR)
////        Photo.stylization(imgBGR, imgStyle)
////        return imgStyle!!
//
////        stylizationFromJNI(inputFrame.rgba().nativeObjAddr, result.nativeObjAddr)
//
////        return result
//
//        return imgRGBA!!
//    }

}

enum class FotoapparatState{
    ON, OFF
}