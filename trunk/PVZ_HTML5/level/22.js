oS.Init({PName:[oPeashooter, oSunFlower, oCherryBomb, oWallNut, oPotatoMine, oSnowPea, oChomper, oRepeater, oPuffShroom, oSunShroom, oFumeShroom, oGraveBuster, oHypnoShroom, oScaredyShroom, oIceShroom, oDoomShroom, oLilyPad, oSquash], ZName:[oZombie, oZombie2, oZombie3, oDuckyTubeZombie1, oDuckyTubeZombie2, oDuckyTubeZombie3, oConeheadZombie, oNewspaperZombie, oFootballZombie, oBucketheadZombie], PicArr:function () {
    var a = oThreepeater.prototype, b = a.PicArr;
    return["images/interface/background3.jpg", b[a.CardGif], b[a.NormalGif]]
}(), Coord:2, SunNum:50, LF:[0, 1, 1, 2, 2, 1, 1], backgroundImage:"images/interface/background3.jpg", CanSelectCard:1, LevelName:"关卡 3-2", LargeWaveFlag:{10:$("imgFlag3"), 20:$("imgFlag1")}, UserDefinedFlagFunc:function (a) {
    oP.FlagNum == oP.FlagZombies && oP.SetTimeoutWaterZombie(6, 9, 3, [oDuckyTubeZombie1, oDuckyTubeZombie2, oDuckyTubeZombie3])
}, StartGameMusic:"Kitanai Sekai.swf"}, {AZ:[
    [oZombie, 3, 1],
    [oZombie2, 1, 1],
    [oZombie3, 1, 1],
    [oDuckyTubeZombie1, 1, 6, [6, 7, 8, 10, 19, 20]],
    [oDuckyTubeZombie2, 1, 6, [10, 20]],
    [oDuckyTubeZombie3, 1, 6, [10, 20]],
    [oConeheadZombie, 2, 1],
    [oBucketheadZombie, 1, 1],
    [oFootballZombie, 1, 1],
    [oNewspaperZombie, 1, 1]
], FlagNum:20, FlagToSumNum:{a1:[3, 5, 9, 10, 13, 15, 19], a2:[1, 2, 3, 10, 4, 5, 6, 15]}, FlagToMonitor:{9:[ShowLargeWave, 0], 19:[ShowFinalWave, 0]}, FlagToEnd:function () {
    NewImg("imgSF", "images/Card/Plants/Threepeater.png", "left:627px;top:325px", EDAll, {onclick:function () {
        SelectModal(23)
    }});
    NewImg("PointerUD", "images/interface/PointerDown.gif", "top:290px;left:636px", EDAll)
}});