
#include "IDemux.h"
#include "XLog.h"
#include "IPlayerPorxy.h"

//读取数据
void IDemux::Main()
{
    while(!isExit)
    {
        if(IsPause())
        {
            XSleep(2);
            continue;
        }
        XData d = Read();
        XLOGI("run d.size is %f", d.size);
        if(d.size > 0){
            Notify(d);
            IPlayerPorxy::Get()->SetReadFrameOver(false);
        }else{
            XSleep(2);
            double pos = IPlayerPorxy::Get()->PlayPos();
            if (pos > 0.99){
                IPlayerPorxy::Get()->SetReadFrameOver(true);
            }
        }
    }
}