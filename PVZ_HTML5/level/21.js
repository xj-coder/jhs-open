oS.Init({PName:[oPeashooter, oSunFlower, oCherryBomb, oWallNut, oPotatoMine, oSnowPea, oChomper, oRepeater, oPuffShroom, oSunShroom, oFumeShroom, oGraveBuster, oHypnoShroom, oScaredyShroom, oIceShroom, oDoomShroom, oLilyPad], ZName:[oZombie, oZombie2, oZombie3, oDuckyTubeZombie1, oConeheadZombie], PicArr:function () {
    var a = oSquash.prototype, b = a.PicArr;
    return["images/interface/background3.jpg", "images/interface/Dave.gif", "images/interface/Dave2.gif", b[a.CardGif], b[a.NormalGif]]
}(), Coord:2, SunNum:50, LF:[0, 1, 1, 2, 2, 1, 1], backgroundImage:"images/interface/background3.jpg", CanSelectCard:1, LevelName:"关卡 3-1", LargeWaveFlag:{10:$("imgFlag1")}, UserDefinedFlagFunc:function (a) {
    oP.FlagNum == oP.FlagZombies && oP.SetTimeoutWaterZombie(6, 9, 2, [oDuckyTubeZombie1])
}, StartGameMusic:"Kitanai Sekai.swf", LoadAccess:function (a) {
    NewImg("dDave", "images/interface/Dave.gif", "left:0;top:81px", EDAll);
    NewEle("DivTeach", "div", 0, 0, EDAll);
    (function (d) {
        var b = arguments.callee, c = $("DivTeach");
        switch (d) {
            case 0:
                innerText(c, "看来僵尸们要放弃进攻你的前院了。(点击继续)");
                c.onclick = function () {
                    oSym.addTask(10, b, [1])
                };
                break;
            case 1:
                innerText(c, "现在他们想试试你的后院。(点击继续)");
                c.onclick = function () {
                    oSym.addTask(10, b, [2])
                };
                break;
            case 2:
                innerText(c, "但最重要的是你不能使用你的蘑菇了！(点击继续)");
                c.onclick = function () {
                    oSym.addTask(10, b, [3])
                };
                break;
            case 3:
                innerText(c, "因为他们白天时要睡觉！(点击继续)");
                c.onclick = function () {
                    oSym.addTask(10, b, [4])
                };
                break;
            case 4:
                innerText(c, "这难道不是花花公子的作风吗？(点击继续)");
                c.onclick = function () {
                    oSym.addTask(10, b, [5])
                };
                break;
            case 5:
                $("dDave").src = "images/interface/Dave2.gif";
                ClearChild($("DivTeach"));
                oSym.addTask(50, function () {
                    ClearChild($("dDave"));
                    a(0)
                }, [])
        }
    })(0)
}}, {AZ:[
    [oZombie, 3, 1],
    [oZombie2, 2, 1],
    [oZombie3, 3, 1],
    [oDuckyTubeZombie1, 1, 6, [6, 7, 8, 10]],
    [oConeheadZombie, 2, 1]
], FlagNum:10, FlagToSumNum:{a1:[3, 5, 9], a2:[1, 2, 3, 10]}, FlagToMonitor:{9:[ShowFinalWave, 0]}, FlagToEnd:function () {
    NewImg("imgSF", "images/Card/Plants/Squash.png", "left:827px;top:525px", EDAll, {onclick:function () {
        SelectModal(22)
    }});
    NewImg("PointerUD", "images/interface/PointerDown.gif", "top:490px;left:836px", EDAll)
}});