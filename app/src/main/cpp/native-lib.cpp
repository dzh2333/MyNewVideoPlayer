#include <jni.h>
#include <string>
#include <android/log.h>
#include <android/looper.h>
#include <android/native_window_jni.h>
#include <android/native_window.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>
#include <thread>
#include "player/IDemux.h"
#include "player/FFDemux.h"
#include "player/IPlayerPorxy.h"
#include "player/XLog.h"
#include "jnihelper/StrHelper.cpp"
#include "jnihelper/AndroidHelper.h"
#include "nativelib.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_mark_myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


JavaVM * g_vm = NULL;
jobject g_obj = NULL;

extern "C"{
jint playVideo(JNIEnv *env, jobject instance, jstring str, jobject view, int playMode){
    if (g_obj != NULL){
        env->DeleteGlobalRef(g_obj);
    }
    g_obj = env->NewGlobalRef(view);
    IPlayerPorxy::Get()->setPlayMode(playMode);
    IPlayerPorxy::Get()->setHeight(0);
    IPlayerPorxy::Get()->setWidth(0);
    IPlayerPorxy::Get()->Open(jstringtochar(env, str));
    return 0;
}

jint InitView(JNIEnv *env, jobject instance,jobject view){
    ANativeWindow *win = ANativeWindow_fromSurface(env, view);
    IPlayerPorxy::Get()->InitView(win);
    return 0;
}

jint start(JNIEnv *env, jobject instance){
    IPlayerPorxy::Get()->Start();
    IPlayerPorxy::Get()->SetPause(false);
    return 0;
}

jobject nativelib::GetVideoCls() {
    return g_obj;
}

jboolean getPauseStatus(JNIEnv *env, jobject instance){
    if(IPlayerPorxy::Get()->IsPause()){
        return true;
    } else{
        return false;
    }
}

jboolean onRelease(JNIEnv *env, jobject instance){
    IPlayerPorxy::Get()->Close();
    IPlayerPorxy::Get()->InitView(NULL);
    env->DeleteGlobalRef(g_obj);
    g_obj = NULL;
}

jint stopVideo(JNIEnv *env, jobject instance){
    IPlayerPorxy::Get()->Close();
    return 0;
}

void changeSeek(JNIEnv *env, jobject instance, double pos){
    IPlayerPorxy::Get()->Seek(pos);
}

void changeURL(JNIEnv *env, jobject instance, jstring url){
    IPlayerPorxy::Get()->Close();
    IPlayerPorxy::Get()->setHeight(0);
    IPlayerPorxy::Get()->setWidth(0);
    IPlayerPorxy::Get()->SetPause(false);
    IPlayerPorxy::Get()->Open(jstringtochar(env, url));
    IPlayerPorxy::Get()->Start();
}

jstring getSupportInfo(JNIEnv *env,jobject instance){

}

void setPause(JNIEnv *env, jobject instance, bool pause){
    if (IPlayerPorxy::Get()->IsPause()){
        if (pause){
            return;
        } else{
            IPlayerPorxy::Get()->SetPause(false);
        }
    } else{
        if (pause){
            IPlayerPorxy::Get()->SetPause(true);
        }
    }
}
}

static const JNINativeMethod method[] = {
        {"openUrl","(Ljava/lang/String;Ljava/lang/Object;I)I", (void*)playVideo},
        {"InitView","(Ljava/lang/Object;)I", (void*)InitView},
        {"start","()I", (void*) start},
        {"getPauseStatus","()Z", (void*)getPauseStatus},
        {"onRelease","()Z", (void*)onRelease},
        {"stopVideo","()V", (void*)stopVideo},
        {"seekTo","(D)V", (void*)changeSeek},
        {"changeURL","(Ljava/lang/String;)V", (void*)changeURL},
        {"setPause","(Z)V", (void*)setPause},
        {"getSupportProcel","()Ljava/lang/String;", (void*)getSupportInfo}
};

static const char *mClassName = "com/mark/myapplication/player/core/PlayerCore";

int JNI_OnLoad(JavaVM *vm,void  *re){
    g_vm = vm;
    IPlayerPorxy::Get()->Init(vm);
    AndroidHelper::getInstance()->SetVM(g_vm);
    //获得JNIEnv
    JNIEnv *env = 0;
    int r = vm->GetEnv((void**)&env,JNI_VERSION_1_6);
    //小于0失败，等于0成功
    if(r != JNI_OK){
        return -1;
    }

    //获得class对象
    jclass jcls =  env->FindClass(mClassName);
    //动态注册
    env->RegisterNatives(jcls,method, sizeof(method)/ sizeof(JNINativeMethod));
    return JNI_VERSION_1_6;
}



