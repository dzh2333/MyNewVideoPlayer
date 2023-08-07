

extern "C"{

#include <libswscale/swscale.h>
#include <libavcodec/avcodec.h>
#include <libavutil/imgutils.h>
#include <libyuv.h>
}
#include <android/native_window.h>
#include <locale>

#include "GLVideoView.h"
#include "XTexture.h"
#include "XLog.h"
#include "../nativelib.h"
#include "../jnihelper/AndroidHelper.h"
#include "FFdecode.h"
#include "FFPlayerBuilder.h"
#include "IPlayerPorxy.h"


void GLVideoView::SetRender(ANativeWindow *win)
{
    index = 4;
    view = static_cast<ANativeWindow *>(win);

}

//BUG
void GLVideoView::Close()
{

    if(txt)
    {
        txt = 0;
    }

//        mux.lock();
////    XLOGE("解锁--上锁  GLVideoView");
////
//    if(txt)
//        {
//            txt->Drop();
//            txt = 0;
//        }
//    mux.unlock();
//    XLOGE("解锁--上锁  GLVideoView   完成");
}

/**
 * 渲染数据
 * @param data
 */
void GLVideoView::Render(XData data)
{
    isExit = false;

    ANativeWindow_setBuffersGeometry(view,
                                     data.width,
                                     data.height,
                                     WINDOW_FORMAT_RGBA_8888);

    ANativeWindow_lock(view, &windowBuffer, NULL);

    if(!view){
        XLOGE("not have view");
        return;
    }

    av_image_fill_arrays(data.dFrameRGBA->data,
                         data.dFrameRGBA->linesize,
                         (const uint8_t *) windowBuffer.bits,
                         AV_PIX_FMT_RGBA,
                         data.width,
                         data.height,
                         1);

    //YUV->RGBA_8888
    switch (data.format){
        case AV_PIX_FMT_YUVJ420P:
        case AV_PIX_FMT_YUV420P:
            libyuv::I420ToARGB(data.dFrame->data[0], data.dFrame->linesize[0],
                               data.dFrame->data[2], data.dFrame->linesize[2],
                               data.dFrame->data[1], data.dFrame->linesize[1],
                               data.dFrameRGBA->data[0], data.dFrameRGBA->linesize[0],
                               data.width, data.height);
            break;
        case AV_PIX_FMT_YUVJ422P:
        case AV_PIX_FMT_YUV422P:
        case AV_PIX_FMT_YUYV422:
            libyuv::I422ToARGB(data.dFrame->data[0], data.dFrame->linesize[0],
                               data.dFrame->data[2], data.dFrame->linesize[2],
                               data.dFrame->data[1], data.dFrame->linesize[1],
                               data.dFrameRGBA->data[0], data.dFrameRGBA->linesize[0],
                               data.width, data.height);
            break;
        case AV_PIX_FMT_YUV444P:
            libyuv::I444ToARGB(data.dFrame->data[0], data.dFrame->linesize[0],
                               data.dFrame->data[2], data.dFrame->linesize[2],
                               data.dFrame->data[1], data.dFrame->linesize[1],
                               data.dFrameRGBA->data[0], data.dFrameRGBA->linesize[0],
                               data.width, data.height);
            break;
        case AV_PIX_FMT_NV12:
            libyuv::NV12ToARGB(data.dFrame->data[0], data.dFrame->linesize[0],
                               data.dFrame->data[2], data.dFrame->linesize[2],
                               data.dFrameRGBA->data[0], data.dFrameRGBA->linesize[0],
                               data.width, data.height);
            break;
        case AV_PIX_FMT_NV21:
            libyuv::NV21ToARGB(data.dFrame->data[0], data.dFrame->linesize[0],
                               data.dFrame->data[2], data.dFrame->linesize[2],
                               data.dFrameRGBA->data[0], data.dFrameRGBA->linesize[0],
                               data.width, data.height);
            break;
        default:
            break;
    }
    ANativeWindow_unlockAndPost(view);


    //通知UI刷新进度条
    //
    jclass cls;
    jmethodID mid;
    JNIEnv *env;
    if(AndroidHelper::getInstance()->GetVM()->AttachCurrentThread(&env, NULL) != JNI_OK)
    {
        XLOGE("%s: AttachCurrentThread() failed", __FUNCTION__);
        return;
    }
    cls = env->GetObjectClass(nativelib::GetVideoCls());
    if(cls == NULL)
    {
        XLOGE("FindClass() Error.....");
        return;
    }
    long time = data.dFrame->best_effort_timestamp;
    mid = env->GetStaticMethodID(cls, "changeSeek", "(J)V");
    env->CallStaticVoidMethod(cls, mid , time);
    AndroidHelper::getInstance()->GetVM()->DetachCurrentThread();
}
