#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <android/log.h>

//
// Created by yello on 2024-12-04.
//

#define LOG_TAG     "pbutton"
#define LOG_D(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOG_E(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

void toBinaryString(unsigned short value, char *output, int bitCount) {
    output[bitCount] = '\0';  // 문자열 끝
    for (int i = bitCount - 1; i >= 0; i--) {
        output[i] = (value & 1) ? '1' : '0';
        value >>= 1;  // 오른쪽으로 1비트 이동
    }
}

JNIEXPORT jchar JNICALL
Java_com_example_wlf_jumper_devices_PushButtons_getButton(JNIEnv *env, jobject thiz) {
    // TODO: implement getButton()
    int fd, i;
    unsigned short buffer = 0x0000;
    unsigned char ret;
    char binaryString[17];

    // push button device file
    fd = open("/dev/pbutton", O_RDWR);
    if (fd < 0) {
        LOG_E("Device open error : /dev/pbutton\n");
        return (jchar) buffer;
    }

    ret = read(fd, &buffer, sizeof(buffer));
    if (ret < 0) {
        LOG_E("Read Error!\n");
        return (jchar) buffer;
    }


//    if (buffer != 0x0000){
//        toBinaryString(buffer, binaryString, 16);
//        LOG_D("pressed button : 0x%04X, %s", buffer, binaryString);
//    }

    close(fd);

    return (jchar)buffer;
}
