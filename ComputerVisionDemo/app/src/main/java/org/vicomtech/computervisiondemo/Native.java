package org.vicomtech.computervisiondemo;

import android.graphics.Rect;

/**
 * Native:  act as an interface between Kotlin and C++
 */
public final class Native {

//    /** analise the raw captured frame from camera to find the face landmarks */
//    public static long[] analiseFrame(byte[] yuv, int rotation, int width, int height, Rect region) {
//
//        return detectLandmarks(
//                yuv, rotation, width, height,
//                region.left, region.top, region.right, region.bottom
//        );
//    }

    public static native void setImageFormat(final int format);
}
