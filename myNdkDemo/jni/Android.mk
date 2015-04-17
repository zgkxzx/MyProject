LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS    := -llog
LOCAL_MODULE    := auth
LOCAL_SRC_FILES := auth.c
include $(BUILD_SHARED_LIBRARY)