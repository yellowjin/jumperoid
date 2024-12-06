#!/bin/bash

insmod 7segment.ko
insmod led.ko
insmod lcd.ko
insmod dotmatrix.ko
insmod pbutton.ko

chmod 666 /dev/7segment
chmod 666 /dev/led
chmod 666 /dev/lcd
chmod 666 /dev/dotmatrix
chmod 666 /dev/pbutton

setenforce 0

