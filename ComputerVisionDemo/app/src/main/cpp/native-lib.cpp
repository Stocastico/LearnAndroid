#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

extern "C" JNIEXPORT jstring JNICALL
Java_org_vicomtech_computervisiondemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Computer Vision Demo!";
    return env->NewStringUTF(hello.c_str());
}

extern "C" {
    void JNICALL Java_org_vicomtech_computervisiondemo_screens_EdgeDetNativeFragment_edgeDetectionFromJNI(JNIEnv* env,
            jobject instance, jlong inputAddr, jlong outputAddr)
    {
        cv::Mat &input = *(cv::Mat *) inputAddr;
        cv::Mat &output = *(cv::Mat *) outputAddr;

        cv::Canny(input, output, 50, 100);
    }
}