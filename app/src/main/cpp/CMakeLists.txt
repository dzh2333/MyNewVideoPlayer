
cmake_minimum_required(VERSION 3.18.1)


set(distribution_DIR ../../../../libs/arm64-v8a)

project("myapplication")

file(GLOB_RECURSE SRC_MAIN_DIR_LIST "*.cpp" "*.c")
file(GLOB_RECURSE SRC_PLAYER_DIR_LIST "player/*.cpp" "player/*.c")
file(GLOB_RECURSE SRC_HELPER_DIR_LIST "jnihelper/*.cpp" "jnihelper/*.c")

add_library( # Sets the name of the library.
        myapplication
        SHARED

        ${SRC_MAIN_DIR_LIST}
        ${SRC_PLAYER_DIR_LIST}
        ${SRC_HELPER_DIR_LIST}
        )

add_library( avcodec-59
        SHARED
        IMPORTED)

set_target_properties( avcodec-59
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libavcodec-59.so)

add_library( avfilter-8
        SHARED
        IMPORTED)
set_target_properties( avfilter-8
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libavfilter-8.so)

add_library( avformat-59
        SHARED
        IMPORTED)
set_target_properties( avformat-59
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libavformat-59.so)

add_library( avutil-57
        SHARED
        IMPORTED)
set_target_properties( avutil-57
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libavutil-57.so)

add_library( swresample-4
        SHARED
        IMPORTED)
set_target_properties( swresample-4
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libswresample-4.so)

add_library( swscale-6
        SHARED
        IMPORTED)
set_target_properties( swscale-6
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libswscale-6.so)

add_library(yuv
        SHARED
        IMPORTED)

set_target_properties(yuv
        PROPERTIES IMPORTED_LOCATION
        ${distribution_DIR}/libyuv.so)

include_directories(include/libyuv)
include_directories(include)


find_library(
        log-lib
        log
        android-lib
        android
        EGL)

target_link_libraries( # Specifies the target library.
        myapplication
        avcodec-59
        avfilter-8
        avformat-59
        avutil-57
        swresample-4
        swscale-6
        yuv
        android
        GLESv2 EGL
        OpenSLES
        ${log-lib})