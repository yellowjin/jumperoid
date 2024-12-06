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
Java_com_example_wlf_jumper_devices_LCD_writeLCD(JNIEnv *env, jobject thiz, jstring first, jstring second) {
    // TODO: implement writeLCD()
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

    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s\n", str_first);

    pos = 16;
    str_second = (*env)->GetStringUTFChars(env, second, NULL);
    ioctl(fd, LCD_SET_CURSOR_POS, &pos, _IOC_SIZE(LCD_SET_CURSOR_POS));
    write(fd, str_second, strlen(str_second));
    __android_log_print(ANDROID_LOG_INFO, LOG_TAG, "%s\n", str_second);

    close(fd);

    return 0;
}
