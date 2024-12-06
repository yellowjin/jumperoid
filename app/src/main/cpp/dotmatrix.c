//
// Created by yello on 2024-12-04.
//
#include <jni.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <android/log.h>

#define DOTM_MAGIC  0xBC

#define DOTM_SET_RUN    _IOW(DOTM_MAGIC, 9, int)    // JUMPEROID
#define DOTM_SET_OVER   _IOW(DOTM_MAGIC, 10, int)   // GAMEOVER
#define DOTM_SET_CLEAR  _IOW(DOTM_MAGIC, 11, int)   // GAMECLEAR

unsigned int commands[] = {
        DOTM_SET_RUN,   // 0
        DOTM_SET_OVER,  // 1
        DOTM_SET_CLEAR  // 2
};

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_DotMatrix_writeRunning(JNIEnv *env, jobject thiz) {
    // TODO: implement writeRunning()
    int ret;
    int fd = open("/dev/dotmatrix", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "dotmatrix", "device open error");
        return -1;
    }
    ret = ioctl(fd, commands[0], NULL);   // JUMPEROID up scroll
    if (ret < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "dotmatrix", "ioctl error");
        return -1;
    }
    close(fd);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_DotMatrix_writeGameClear(JNIEnv *env, jobject thiz) {
    // TODO: implement writeGameClear()
    int ret;
    int fd = open("/dev/dotmatrix", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "dotmatrix", "device open error");
        return -1;
    }
    ret = ioctl(fd, commands[1], NULL);   // GAMEOVER up scroll
    if (ret < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "dotmatrix", "ioctl error");
        return -1;
    }
    close(fd);
    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_DotMatrix_writeGameOver(JNIEnv *env, jobject thiz) {
    // TODO: implement writeGameOver()
    int ret;
    int fd = open("/dev/dotmatrix", O_RDWR);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "dotmatrix", "device open error");
        return -1;
    }
    ret = ioctl(fd, commands[2], NULL);   // GAMECLEAR up scroll
    if (ret < 0) {
        __android_log_print(ANDROID_LOG_ERROR, "dotmatrix", "ioctl error");
        return -1;
    }
    close(fd);
    return 0;
}