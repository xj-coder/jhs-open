oS.Init({PName:[oPeashooter, oCherryBomb, oWallNut, oPotatoMine, oSnowPea, oChomper, oRepeater], ZName:[oZombie, oZombie2, oZombie3, oConeheadZombie, oPoleVaultingZombie, oBucketheadZombie], PicArr:["images/interface/background1.jpg", "images/interface/trophy.png"], backgroundImage:"images/interface/background1.jpg", CanSelectCard:0, LevelName:"�ؿ� 1-10", LargeWaveFlag:{10:$("imgFlag3"), 20:$("imgFlag1")}, StaticCard:0, StartGame:function () {
    !oS.Silence && NewMusic("UraniwaNi.swf");
    SetVisible($("tdShovel"), $("dFlagMeter"), $("dTop"));
    SetHidden($("dSunNum"));
    oS.InitLawnMower();
    PrepareGrowPlants(function () {
        oP.Monitor({f:function () {
            (function () {
                var a = ArCard.length;
                if (a < 10) {
                    var c = oS.PName, b = Math.floor(Math.random() * c.length), e = c[b], d = e.prototype, f = "dCard" + Math.random();
                    ArCard[a] = {DID:f, PName:e, PixelTop:600};
                    NewImg(f, d.PicArr[d.CardGif], "top:600px;cursor:pointer", $("dCardList"), {onmouseover:function (g) {
                        ViewPlantTitle(GetChoseCard(f), g)
                    }, onmouseout:function () {
                        SetHidden($("dTitle"))
                    }, onclick:function (g) {
                        ChosePlant(g, oS.ChoseCard, f)
                    }})
                }
                oSym.addTask(600, arguments.callee, [])
            })();
            (function () {
                var b = ArCard.length, a, c;
                while (b--) {
                    (c = (a = ArCard[b]).PixelTop) > 60 * b && ($(a.DID).style.top = (a.PixelTop = c - 1) + "px")
                }
                oSym.addTask(5, arguments.callee, [])
            })()
        }, ar:[]});
        oP.AddZombiesFlag();
        SetVisible($("dFlagMeterContent"))
    })
}}, {AZ:[
    [oZombie, 1, 1],
    [oZombie2, 1, 1],
    [oZombie3, 1, 1],
    [oConeheadZombie, 5, 1],
    [oPoleVaultingZombie, 2, 1],
    [oBucketheadZombie, 5, 1]
], FlagNum:20, FlagToSumNum:{a1:[3, 5, 9, 10, 13, 15, 19], a2:[3, 6, 12, 20, 24, 36, 48, 60]}, FlagToMonitor:{9:[ShowLargeWave, 0], 19:[ShowFinalWave, 0]}, FlagToEnd:function () {
    NewImg("imgSF", "images/interface/trophy.png", "left:260px;top:233px", EDAll, {onclick:function () {
        SelectModal(11)
    }});
    NewImg("PointerUD", "images/interface/PointerDown.gif", "top:198px;left:269px", EDAll)
}}, {GetChoseCard:function (b) {
    var a = ArCard.length;
    while (a--) {
        ArCard[a].DID == b && (oS.ChoseCard = a, a = 0)
    }
    return oS.ChoseCard
}, ChosePlant:function (a, b) {
    a = window.event || a;
    var f = ArCard[oS.ChoseCard], e = a.clientX + EBody.scrollLeft || EElement.scrollLeft, d = a.clientY + EBody.scrollTop || EElement.scrollTop, c = f.PName.prototype;
    oS.Chose = 1;
    EditImg((EditImg($Pn[c.EName].childNodes[1].cloneNode(false), "MovePlant", "", {left:e - 0.5 * (c.beAttackedPointL + c.beAttackedPointR) + "px", top:d + 20 - c.height + "px", zIndex:254}, EDAll)).cloneNode(false), "MovePlantAlpha", "", {visibility:"hidden", filter:"alpha(opacity=40)", opacity:0.4, zIndex:30}, EDAll);
    SetAlpha($(f.DID), 50, 0.5);
    SetHidden($("dTitle"));
    GroundOnmousemove = GroundOnmousemove1
}, CancelPlant:function () {
    ClearChild($("MovePlant"), $("MovePlantAlpha"));
    oS.Chose = 0;
    SetAlpha($(ArCard[oS.ChoseCard].DID), 100, 1);
    oS.ChoseCard = "";
    GroundOnmousemove = function () {
    }
}, GrowPlant:function (k, c, b, f, a) {
    var i = oS.ChoseCard, g = ArCard[i], h = g.PName, j = h.prototype, d = g.DID, e;
    j.CanGrow(k, f, a) && function () {
        (new h).Birth(c, b, f, a, k);
        oSym.addTask(20, SetNone, [SetStyle($("imgGrowSoil"), {left:c - 30 + "px", top:b - 40 + "px", zIndex:3 * f, visibility:"visible"})]);
        ClearChild($("MovePlant"), $("MovePlantAlpha"));
        $("dCardList").removeChild(e = $(d));
        e = null;
        ArCard.splice(i, 1);
        oS.ChoseCard = "";
        oS.Chose = 0;
        GroundOnmousemove = function () {
        }
    }()
}, ViewPlantTitle:function (a) {
    var c = $("dTitle"), b = ArCard[a].PName.prototype;
    c.innerHTML = b.CName + "<br>" + b.Tooltip;
    SetStyle(c, {top:60 * a + "px", left:"100px"})
}});