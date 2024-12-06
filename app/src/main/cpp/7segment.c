//
// Created by yello on 2024-11-30.
//
#include <jni.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <android/log.h>

#define LOG_TAG "7Segment"

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_SevenSegment_SSegmentWrite(JNIEnv *env, jobject thiz, jint data) {
    // TODO: implement SSegmentWrite()

    int fd, ret;
    unsigned char bytevalues[4];

    if ((data < 0) || (data > 9999)) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,"Invalid range!\n");
        return -1;
    }

    fd = open("/dev/7segment", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,"Device open error : /dev/7segment\n");
        return -1;
    }

    bytevalues[0] = data / 1000;
    data = data % 1000;
    bytevalues[1] = data / 100;
    data = data % 100;
    bytevalues[2] = data / 10;
    data = data % 10;
    bytevalues[3] = data;

    ret = write(fd, bytevalues, 4);
    if (ret < 0) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "write_error");

        return -1;
    }

    close(fd);

    return 0;
}