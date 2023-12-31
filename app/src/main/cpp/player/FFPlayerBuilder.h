


//
// Created by Administrator on 2018-03-07.
//

#ifndef XPLAY_FFPLAYERBUILDER_H
#define XPLAY_FFPLAYERBUILDER_H

#include "IPlayerBuilder.h"
#include "FFdecode.h"

class FFPlayerBuilder:public IPlayerBuilder
{
public:
    static void InitHard(void *vm);
    static FFPlayerBuilder *Get()
    {
        static FFPlayerBuilder ff;
        return &ff;
    }

    virtual FFDecode *GetFFDecode();
protected:
    FFPlayerBuilder(){};
    virtual IDemux *CreateDemux();
    virtual FFDecode *CreateDecode();
    virtual IResample *CreateResample();
    virtual IVideoView *CreateVideoView();
    virtual IAudioPlay *CreateAudioPlay();
    virtual IPlayer *CreatePlayer(unsigned char index=0);

    FFDecode* ffDecode;
};


#endif //XPLAY_FFPLAYERBUILDER_H
