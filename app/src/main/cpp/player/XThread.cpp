


//
// Created by Administrator on 2018-03-01.
//

#include "XThread.h"
#include "XLog.h"
#include "IPlayerPorxy.h"
#include "../jnihelper/AndroidHelper.h"

#include <thread>
using namespace std;
void XSleep(int mis)
{
    chrono::milliseconds du(mis);
    this_thread::sleep_for(du);
}

void XThread::SetPause(bool isP)
{
    isPause = isP;
    //等待100毫秒
    for(int i = 0; i < 10; i++)
    {
        if(isPausing == isP)
        {
            break;
        }
        XSleep(10);
    }

}
//启动线程
bool XThread::Start()
{
    isExit = false;
    isPause = false;
    thread th(&XThread::ThreadMain,this);
    th.detach();
    return true;
}
void XThread::ThreadMain()
{
    isRuning = true;
    XLOGI("线程函数进入");
    Main();
    XLOGI("线程函数退出");
    isRuning = false;
}

JNIEnv * XThread::GetEnv() {
    if (env == NULL){
        int r = AndroidHelper::getInstance()->GetVM()->GetEnv((void**)&env,JNI_VERSION_1_6);
        if (r < 0){
            r = AndroidHelper::getInstance()->GetVM()->AttachCurrentThread(&env, NULL);
            if (r <  0){
            }
        }
    }
    return env;
}

//通过控制isExit安全停止线程（不一定成功）
void XThread::Stop()
{
    XLOGI("Stop 停止线程begin!");
    isExit = true;
    for(int i = 0; i < 100; i++)
    {
        if(!isRuning)
        {
            XLOGI("Stop 停止线程成功!");
            return;
        }
        XSleep(1);
    }
    XLOGI("Stop 停止线程超时!  %d", index);

}