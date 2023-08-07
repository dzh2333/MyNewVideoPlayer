


//
// Created by Administrator on 2018-03-04.
//

#ifndef XPLAY_GLVIDEOVIEW_H
#define XPLAY_GLVIDEOVIEW_H


#include "XData.h"
#include "IVideoView.h"

class XTexture;

class GLVideoView: public IVideoView {
public:
    virtual void SetRender(ANativeWindow *win);
    virtual void Render(XData data);
    virtual void Close();
protected:
    ANativeWindow *view = 0;
    XTexture *txt = 0;
    std::mutex mux;
    struct SwsContext* sws_ctx;
    ANativeWindow_Buffer windowBuffer;
};


#endif //XPLAY_GLVIDEOVIEW_H
