


//
// Created by Administrator on 2018-03-07.
//

#ifndef XPLAY_IPLAYERPORXY_H
#define XPLAY_IPLAYERPORXY_H


#include "IPlayer.h"
#include <mutex>
class IPlayerPorxy: public IPlayer
{
public:
    static IPlayerPorxy*Get()
    {
        static IPlayerPorxy px;
        return &px;
    }
    void Init(void *vm = 0);

    virtual bool Open(const char *path);
    virtual bool Seek(double pos);
    virtual void Close();
    virtual bool Start();
    virtual void InitView(ANativeWindow *win);
    virtual void SetPause(bool isP);
    virtual bool IsPause();
    virtual void Stop();
    //获取当前的播放进度 0.0 ~ 1.0
    virtual double PlayPos();
    virtual void Over();

    virtual int getWidth();
    virtual void setWidth(int width);

    virtual int getHeight();
    virtual void setHeight(int height);

    virtual bool getCanPlaying();
    virtual void setCanPlayging(bool canPlaying);
    virtual void setPlayMode(int playMode);
    virtual int getPlayMode();

    virtual void SetReadFrameOver(bool over);
    virtual bool GetReadFrameOver();
protected:
    IPlayerPorxy(){}
    IPlayer *player = 0;
    std::mutex mux;

    int playMode = 0;

    int width = 0;
    int height = 0;
    bool canPlaying = false;
    bool readFrameOver = false;
};


#endif //XPLAY_IPLAYERPORXY_H
