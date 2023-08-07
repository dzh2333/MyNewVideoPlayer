//
// Created by l on 2020/8/15.
//

#ifndef CYBERPUNKPLAYER_ANDROIDHELPER_H
#define CYBERPUNKPLAYER_ANDROIDHELPER_H


#include <jni.h>

class AndroidHelper {
public:
    static AndroidHelper * getInstance(){
        static AndroidHelper an;
        return &an;
    };
    virtual JavaVM* GetVM();
    virtual void SetVM(JavaVM * vm);
protected:
    JavaVM * _vm;

};


#endif //CYBERPUNKPLAYER_ANDROIDHELPER_H
