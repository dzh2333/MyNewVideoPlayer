


//
// Created by Administrator on 2018-03-07.
//

#include <jni.h>
#include <thread>
#include "IPlayerPorxy.h"
#include "FFPlayerBuilder.h"
#include "XLog.h"
#include "../nativelib.h"
#include "../jnihelper/AndroidHelper.h"

void IPlayerPorxy::Close()
{
    mux.lock();
    if(player){
        player->Close();
    }
    mux.unlock();
}

void IPlayerPorxy::Init(void *vm)
{
    mux.lock();
    if(vm)
    {
        FFPlayerBuilder::InitHard(vm);
    }
    if(!player){
        player = FFPlayerBuilder::Get()->BuilderPlayer();
    }

    mux.unlock();
}

//播放结束
void IPlayerPorxy::Over() {
    mux.lock();
    isPause = true;

    jclass cls;
    jmethodID mid;
    JNIEnv *env;
    //Attach主线程
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
    //再获得类中的方法
    mid = env->GetStaticMethodID(cls, "playOver", "()V");
    env->CallStaticVoidMethod(cls, mid , NULL);

    AndroidHelper::getInstance()->GetVM()->DetachCurrentThread();

    mux.unlock();
}

void IPlayerPorxy::Stop() {
    mux.lock();
    if(player){
        player->Close();
    }

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
    mid = env->GetStaticMethodID(cls, "stopCallBack", "()V");
    env->CallStaticVoidMethod(cls, mid , NULL);

    AndroidHelper::getInstance()->GetVM()->DetachCurrentThread();
    mux.unlock();
}

//获取当前的播放进度 0.0 ~ 1.0
double IPlayerPorxy::PlayPos()
{
    double pos = 0.0;
    mux.lock();
    if(player)
    {
        pos = player->PlayPos();
    }
    mux.unlock();
    //改变外部进度
    return pos;
}

bool IPlayerPorxy::IsPause()
{
    bool re = false;
    mux.lock();
    if(player)
        re = player->IsPause();
    mux.unlock();
    return re;
}

void IPlayerPorxy::SetPause(bool isP)
{
    mux.lock();
    if(player)
        player->SetPause(isP);
    mux.unlock();
}

bool IPlayerPorxy::Seek(double pos)
{
    bool re = false;
    mux.lock();
    if(player)
    {
        re = player->Seek(pos);
    }
    mux.unlock();
    XLOGI("Seek Over");
    return re;
}

bool IPlayerPorxy::Open(const char *path)
{
    bool re = false;
    mux.lock();
    if(player)
    {
        player->isHardDecode = isHardDecode;
        re = player->Open(path);
    }
    SetReadFrameOver(false);
    mux.unlock();
    return re;
}

bool IPlayerPorxy::Start()
{
    bool re = false;
    mux.lock();
    if(player){
        re = player->Start();
    }
    mux.unlock();
    return re;
}

void IPlayerPorxy::InitView(ANativeWindow *win)
{
    mux.lock();
    if(player)
        player->InitView(win);
    mux.unlock();
}



void IPlayerPorxy::setHeight(int height) {
    this->height = height;
}

void IPlayerPorxy::setWidth(int width) {
    mux.lock();
    this->width = width;
    mux.unlock();
}

int IPlayerPorxy::getHeight() {
    return height;
}

int IPlayerPorxy::getWidth() {
    return width;
}

void IPlayerPorxy::setCanPlayging(bool canPlaying) {
    this->canPlaying = canPlaying;
}

bool IPlayerPorxy::getCanPlaying() {
    return this->canPlaying;
}

void IPlayerPorxy::setPlayMode(int playMode) {
    this->playMode = playMode;
}

int IPlayerPorxy::getPlayMode() {
    return playMode;
}

bool IPlayerPorxy::GetReadFrameOver() {
    return readFrameOver;
}

void IPlayerPorxy::SetReadFrameOver(bool over) {
    this->readFrameOver = over;
}