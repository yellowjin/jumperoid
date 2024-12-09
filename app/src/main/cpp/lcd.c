//
// Created by yello on 2024-12-04.
//
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <android/log.h>

#define LCD_MAGIC            0xBD
#define LCD_SET_CURSOR_POS  _IOW(LCD_MAGIC, 0, int)
#define LOG_TAG "LCD"

#define MAX_LEVEL  40
#define BUFFER_SIZE 16

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LCD_init(JNIEnv *env, jobject thiz) {
    // TODO: implement init()
    int i, fd, pos;

    fd = open("/dev/lcd", O_WRONLY);
    if (fd < 0 ) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "Device open error : /dev/lcd\n");
        return 1;
    }

    for(i = 0; i < 32; i += 2){
        pos = 0 + i;
        ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
        write(fd,"  ", strlen("  "));
    }

    close(fd);

    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LCD_writeLCD(JNIEnv *env, jobject thiz, jint current) {
    // TODO: implement writeLCD()
    char str_first[BUFFER_SIZE], str_second[BUFFER_SIZE];
    int fd, pos, i, currLength, progress;

    fd = open("/dev/lcd", O_WRONLY);
    if (fd < 0 ) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "Device open error : /dev/lcd\n");
        return 1;
    }

    pos = 0;
    snprintf(str_first, BUFFER_SIZE, "LEVEL:%2d", current);
    currLength = 8;

    for (i = currLength; i < BUFFER_SIZE; i++) {
        str_first[i] = ' ';
    }
//    __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "%s\n", str_first);
    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
    write(fd, str_first, BUFFER_SIZE);


    pos = 16;
    progress = current * 100 / MAX_LEVEL;
    snprintf(str_second, BUFFER_SIZE, "%3d%%[", progress);
    currLength = 5;

    for (i = 0; i < 10; i++) {
        if (i < progress / 10){
            str_second[currLength++] = '=';
        }
        else if (i == progress / 10){
            str_second[currLength++] = '*';
        }
        else{
            str_second[currLength++] = '-';
        }
    }
    str_second[BUFFER_SIZE-1] = ']';
    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
    write(fd, str_second, BUFFER_SIZE);
//    __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "%s\n", str_second);

    close(fd);
    return 0;
}

//JNIEXPORT jint JNICALL
//Java_com_example_wlf_jumper_devices_LCD_writeLCD(JNIEnv *env, jobject thiz, jstring first, jstring second) {
//    // TODO: implement writeLCD()
//    const char *str_first, *str_second;
//    int fd, pos;
//
//    fd = open("/dev/lcd", O_WRONLY);
//    if (fd < 0 ) {
//        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "Device open error : /dev/lcd\n");
//        return 1;
//    }
//    pos = 0;
//
//    str_first = (*env)->GetStringUTFChars(env, first, NULL);
//    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
//    write(fd, str_first, strlen(str_first));
//
////    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s\n", str_first);
//
//    pos = 16;
//    str_second = (*env)->GetStringUTFChars(env, second, NULL);
//    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
//    write(fd, str_second, strlen(str_second));
////    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s\n", str_second);
//
//    close(fd);
//
//    return 0;
//}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LCD_writeLCDString(JNIEnv *env, jobject thiz, jstring first,
                                                       jstring second) {
//     TODO: implement writeLCDString()
    const char *str_first, *str_second;
    int fd, pos;

    fd = open("/dev/lcd", O_WRONLY);
    if (fd < 0 ) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "Device open error : /dev/lcd\n");
        return 1;
    }
    pos = 0;

    str_first = (*env)->GetStringUTFChars(env, first, NULL);
    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
    write(fd, str_first, strlen(str_first));

//    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s\n", str_first);

    pos = 16;
    str_second = (*env)->GetStringUTFChars(env, second, NULL);
    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
    write(fd, str_second, strlen(str_second));
//    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s\n", str_second);

    close(fd);

    return 0;
}

JNIEXPORT jint JNICALL
Java_com_example_wlf_jumper_devices_LCD_writeLCD2(JNIEnv *env, jobject thiz, jint current) {
    // TODO: implement writeLCD2()
    char str_first[3], str_prog[5];
    int pos, fd, progress;

    fd = open("/dev/lcd", O_WRONLY);
    if (fd < 0 ) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "Device open error : /dev/lcd\n");
        return 1;
    }
    pos = 6;
    snprintf(str_first, 3, "%2d", current);
    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
    write(fd, str_first, 2);

    pos = 16;
    progress = current * 100 / MAX_LEVEL;
    snprintf(str_prog, 5, "%3d%%", progress);
    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
    write(fd, str_prog, 4);

    if (progress/10 == 2) {
        pos = 20;
        snprintf(str_prog, 5, "[=*-");
        ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
        write(fd, str_prog, 4);
    } else if (progress/10 % 2 == 1 && progress / 10 != 1) {
        pos = 20 + progress/10 - 1;
        snprintf(str_first, 3, "=*");
        ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
        write(fd, str_first, 2);
    } else if (progress/10 % 2 == 0 && progress / 10 != 10 && progress / 10 != 0) {
        pos = 20 + progress/10 - 1;
        snprintf(str_prog, 5, "==*-");
        ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
        write(fd, str_prog, 4);
    } else if (progress/10 % 2 == 0 && progress / 10 == 10){
        pos = 20 + progress/10 - 1;
        snprintf(str_prog, 5, "==*]");
        ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
        write(fd, str_prog, 4);
    }

    close(fd);

    return 0;
}