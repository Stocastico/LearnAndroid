#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_org_vicomtech_computervisiondemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Computer Vision Demo!";
    return env->NewStringUTF(hello.c_str());
}
