//
// Created by yello on 2024-12-04.
//

#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <android/log.h>

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LED_ledInit(JNIEnv *env, jobject thiz, jint data) {
    // TODO: implement ledInit()
    int fd;
    unsigned char bytedata = 1;
    unsigned char ret;
//    bytedata = data;
    for (int i=0; i<data; i++) {
        bytedata |= (1 << i); // Set the i-th bit
    }

    if ((bytedata < 0) || (bytedata > 0xff)) {  // 0 ~ 255 range
        __android_log_print(ANDROID_LOG_ERROR, "led", "Invalid range");
        return -1;
    }

    fd = open("/dev/led", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "led", "device open error");
        return -1;
    }

    ret = write(fd, &bytedata, 1);
    if (ret < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "led", "device write Error");
        return -1;
    }

    close(fd);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LED_decreaseLife(JNIEnv *env, jobject thiz, jint data) {
    // TODO: implement decreaseLife()
    int fd;
    unsigned char bytedata;
    unsigned char ret;
//    bytedata = data;
    for (int i=0; i<data; i++) {
        bytedata |= (1 << i); // Set the i-th bit
    }
    bytedata = bytedata & 0x0F;

    if ((bytedata < 0) || (bytedata > 0xff)) {  // 0 ~ 255 range
        __android_log_print(ANDROID_LOG_ERROR, "led", "Invalid range");
        return -1;
    }

    fd = open("/dev/led", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "led", "device open error");
        return -1;
    }

    ret = write(fd, &bytedata, 1);
    if (ret < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "led", "device write Error");
        return -1;
    }

    close(fd);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LED_ledClear(JNIEnv *env, jobject thiz) {   //, jint data
    // TODO: implement ledClear()
    int fd;
    unsigned char bytedata;
    unsigned char ret;
    bytedata = 0;
    if ((bytedata < 0) || (bytedata > 0xff)) {  // 0 ~ 255 range
        __android_log_print(ANDROID_LOG_ERROR, "led", "Invalid range");
        return -1;
    }

    fd = open("/dev/led", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "led", "device open error");
        return -1;
    }

    ret = write(fd, &bytedata, 1);
    if (ret < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "led", "device write Error");
        return -1;
    }

    close(fd);
    return 0;
}

