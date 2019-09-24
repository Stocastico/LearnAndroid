#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/photo.hpp>

#define NV21 17
int imageFormat = NV21;

extern "C" {
    void JNICALL Java_org_vicomtech_computervisiondemo_screens_EdgeDetNativeFragment_edgeDetectionFromJNI(JNIEnv* env,
            jobject instance, jlong inputAddr, jlong outputAddr)
    {
        cv::Mat &input = *(cv::Mat *) inputAddr;
        cv::Mat &output = *(cv::Mat *) outputAddr;

        cv::Canny(input, output, 50, 100);
        cv::rotate(output, output, cv::ROTATE_90_CLOCKWISE);
    }

    void JNICALL Java_org_vicomtech_computervisiondemo_screens_StylizationFragment_stylizationFromJNI(JNIEnv* env,
            jobject instance, jlong inputAddr, jlong outputAddr)
    {
        cv::Mat &input = *(cv::Mat *) inputAddr;
        cv::Mat &output = *(cv::Mat *) outputAddr;

        cv::Mat rgb;

        cv::cvtColor(input, rgb, cv::COLOR_RGBA2RGB);
        cv::stylization(rgb, output, 60.0, 0.45);
    }

    JNIEXPORT void JNICALL Java_org_vicomtech_computervisiondemo_setImageFormat(JNIEnv* env, jclass, jint format) {
        imageFormat = format;
    }

}