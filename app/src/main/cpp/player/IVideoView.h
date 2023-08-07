
#ifndef XPLAY_IVIDEOVIEW_H
#define XPLAY_IVIDEOVIEW_H


#include <android/native_window.h>
#include "XData.h"
#include "IObserver.h"

class IVideoView:public IObserver
{
public:
    virtual void SetRender(ANativeWindow *win) = 0;
    virtual void Render(XData data) = 0;
    virtual void Update(XData data);
    virtual void Close() = 0;
};


#endif //XPLAY_IVIDEOVIEW_H
