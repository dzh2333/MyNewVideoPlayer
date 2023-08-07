


//
// Created by Administrator on 2018-03-01.
//

#ifndef XPLAY_XTHREAD_H
#define XPLAY_XTHREAD_H

#include <jni.h>

//sleep 毫秒
void XSleep(int mis);

//c++ 11 线程库
class XThread
{
public:
    //启动线程
    virtual bool Start();

    virtual void Stop();

    virtual void SetPause(bool isP);

    virtual bool IsPause()
    {
        isPausing = isPause;
        return isPause;
    }

    //入口主函数
    virtual void Main() {}

    virtual JNIEnv* GetEnv();
protected:
    bool isExit = false;
    bool isRuning = false;
    bool isPause = false;
    bool isPausing = false;
    int index = 0;
    JNIEnv * env = NULL;
private:
    void ThreadMain();

};


#endif //XPLAY_XTHREAD_H
