oS.Init({PName:[oPeashooter, oSunFlower, oCherryBomb, oWallNut, oPotatoMine, oSnowPea, oChomper, oRepeater, oPuffShroom], ZName:[oZombie, oZombie2, oZombie3, oNewspaperZombie], PicArr:function () {
    var a = oSunShroom.prototype, b = a.PicArr;
    return["images/interface/background2.jpg", "images/interface/Tombstones.png", "images/interface/Tombstone_mounds.png", b[a.CardGif], b[a.NormalGif]]
}(), backgroundImage:"images/interface/background2.jpg", CanSelectCard:1, DKind:0, SunNum:50, LevelName:"关卡 2-1", LargeWaveFlag:{10:$("imgFlag1")}, Monitor:{f:AppearTombstones, ar:[7, 9, 4]}, UserDefinedFlagFunc:function (a) {
    oP.FlagNum == oP.FlagZombies && oP.SetTimeoutTomZombie([oZombie])
}, StartGameMusic:"Ultimate battle.swf"}, {AZ:[
    [oZombie, 3, 1],
    [oZombie2, 2, 1],
    [oZombie3, 2, 1],
    [oNewspaperZombie, 2, 1]
], FlagNum:10, FlagToSumNum:{a1:[3, 5, 9], a2:[1, 2, 3, 15]}, FlagToMonitor:{10:[ShowFinalWave, 0]}, FlagToEnd:function () {
    NewImg("imgSF", "images/Card/Plants/SunShroom.png", "left:667px;top:220px", EDAll, {onclick:function () {
        SelectModal(12)
    }});
    NewImg("PointerUD", "images/interface/PointerDown.gif", "top:185px;left:676px", EDAll)
}});