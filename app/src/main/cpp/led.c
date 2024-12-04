//
// Created by yello on 2024-12-04.
//

#include <jni.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <android/log.h>

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LED_ledInit(JNIEnv *env, jobject thiz, jint data) {
    // TODO: implement ledInit()

    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LED_decreaseLife(JNIEnv *env, jobject thiz, jint data) {
    // TODO: implement decreaseLife()

    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LED_ledClear(JNIEnv *env, jobject thiz, jint data) {
    // TODO: implement ledClear()

    return 0;
}