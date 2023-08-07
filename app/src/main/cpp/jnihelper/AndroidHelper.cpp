//
// Created by l on 2020/8/15.
//

#include "AndroidHelper.h"
#include "../player/XLog.h"

JavaVM * AndroidHelper::GetVM() {
    return _vm;
}

void AndroidHelper::SetVM(JavaVM * vm) {
    _vm = vm;
}