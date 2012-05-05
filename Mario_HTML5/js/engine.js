/**
 * 游戏引擎
 * @author JHS注释
 */

var Engine = {
    //游戏画板
    GameCanvas:function () {
        this.BackBufferContext2D = null;      //缓存2d上下文
        this.BackBuffer = null;                //缓存画板
        this.Context2D = null;                 //实际2d上下文
        this.Canvas = null;                    //实际画板
    }
};

Engine.GameCanvas.prototype = {
    /**
     * 初始化
     */
    Initialize:function (a, b, c) {
        this.Canvas = document.getElementById(a);
        this.Context2D = this.Canvas.getContext("2d");
        this.BackBuffer = document.createElement("canvas");
        this.BackBuffer.width = b;
        this.BackBuffer.height = c;
        this.BackBufferContext2D = this.BackBuffer.getContext("2d")
    },

    /**
     * 绘画开始,清空"缓存2d上下文"和"实际2d上下文"
     */
    BeginDraw:function () {
        this.BackBufferContext2D.clearRect(0, 0, this.BackBuffer.width, this.BackBuffer.height);
        this.Context2D.clearRect(0, 0, this.Canvas.width, this.Canvas.height)
    },

    /**
     * 绘画结束,将缓存画板画到实际画板中
     */
    EndDraw:function () {
        this.Context2D.drawImage(this.BackBuffer,
            0, 0, this.BackBuffer.width, this.BackBuffer.height, 0, 0, this.Canvas.width, this.Canvas.height)
    }
};

//按键值
//Engine.Keys = {A:65, B:66, C:67, D:68, E:69, F:70, G:71, H:72, I:73, J:74, K:75, L:76, M:77, N:78, O:79, P:80, Q:81, R:82, /* S:83*/S:229, T:84, U:85, V:86, W:87, X:88, Y:89, Z:80, Left:37, Up:38, Right:39, Down:40};
Engine.Keys = {A:65, B:66, C:67, D:68, E:69, F:70, G:71, H:72, I:73, J:74, K:75, L:76, M:77, N:78, O:79, P:80, Q:81, R:82,S:83, T:84, U:85, V:86, W:87, X:88, Y:89, Z:80, Left:37, Up:38, Right:39, Down:40};

/**
 * 按键处理
 */
Engine.KeyboardInput = {
    Pressed:[],

    /**
     * 初始化注册键盘事件
     */
    Initialize:function () {
        var _this = this;
        document.onkeydown = function (event) {
            _this.KeyDownEvent(event);
        };
        document.onkeyup = function (event) {
            _this.KeyUpEvent(event);
        }
    },

    /**
     * 判断按键按下
     */
    IsKeyDown:function (event) {
        if (this.Pressed[event] != null)
            return this.Pressed[event];
        return!1
    },

    /**
     * 按键按下
     */
    KeyDownEvent:function (event) {
        this.Pressed[event.keyCode] = true;
        this.PreventScrolling(event)
    },

    /**
     * 按键松开
     */
    KeyUpEvent:function (event) {
        this.Pressed[event.keyCode] = false;
        this.PreventScrolling(event)
    },

    /**
     *
     */
    PreventScrolling:function (event) {
        if (event.keyCode >= 37 && event.keyCode <= 40) {
            event.preventDefault();
        }
    }
};

/**
 * 资源处理
 */
Engine.Resources = {
    Images:{}, //图片资源
    Sounds:{}, //声音资源

    /**
     * 销毁所有图片和声音资源
     */
    Destroy:function () {
        delete this.Images;
        delete this.Sounds;
        return this
    },

    /**
     * 添加单个图片资源
     * @param name 图片索引名
     * @param src   图片文件
     */
    AddImage:function (name, src) {
        var tmp = new Image;
        this.Images[name] = tmp;
        tmp.src = src;
        return this
    },

    /**
     * 添加多个图片资源
     * @param imgList 图片列表,每个图片元素必须有name和src属性
     */
    AddImages:function (imgList) {
        for (var i = 0; i < imgList.length; i++) {
            this.AddImage(imgList[i].name, imgList[i].src);
        }
        return this
    },

    /**
     * 清空所有图片资源
     */
    ClearImages:function () {
        delete this.Images;
        this.Images = {};
        return this
    },

    /**
     * 删除指定图片
     * @param name 图片索引名
     */
    RemoveImage:function (name) {
        delete this.Images[name];
        return this
    },

    /**
     * 添加声音资源
     * @param name 声音索引名
     * @param src 声音文件
     * @param count 复制次数
     */
    AddSound:function (name, src, count) {
        this.Sounds[name] = [];
        this.Sounds[name].index = 0;

        if (!count) {
            count = 3;
        }

        for (var i = 0; i < count; i++) {
            this.Sounds[name][i] = new Audio(src);
        }
        return this
    },

    /**
     * 清空所有声音资源
     */
    ClearSounds:function () {
        delete this.Sounds;
        this.Sounds = {};
        return this
    },

    /**
     * 删除指定声音资源
     *
     * @param name 索引名
     */
    RemoveSound:function (name) {
        delete this.Sounds[name];
        return this
    },

    /**
     * 播放声音
     * @param name 声音索引名
     * @param isLoop
     * @return 下一次播放位置
     */
    PlaySound:function (name, isLoop) {
        if (this.Sounds[name].index >= this.Sounds[name].length) {
            this.Sounds[name].index = 0;
        }

        //isLoop && this.Sounds[name][this.Sounds[name].index].addEventListener("ended", this.LoopCallback, !1);

        if (isLoop) {
            this.StartLoop(name, this.Sounds[name].index);
        }

        this.Sounds[name][this.Sounds[name].index++].play();

        return this.Sounds[name].index
    },

    /**
     * 暂停指定位置的声音播放
     * @param name 声音索引名
     * @param index 播放位置
     * @return {*}
     * @constructor
     */
    PauseChannel:function (name, index) {
//        this.Sounds[name][index].paused ||this.Sounds[name][index].pause();

        if (!this.Sounds[name][index].paused) {
            this.Sounds[name][index].pause();
        }

        return this
    },

    /**
     * 暂停声音播放
     * @param name 声音索引名
     * @return {*}
     * @constructor
     */
    PauseSound:function (name) {
        for (var i = 0; i < this.Sounds[name].length; i++) {
//            this.Sounds[name][i].paused || this.Sounds[name][i].pause();
            if (!this.Sounds[name][i].paused) {
                this.Sounds[name][i].pause();
            }
        }
        return this
    },

    /**
     * 停止播放指定位置的声音
     * @param name 声音索引名
     * @param index 播放位置
     * @return {*}
     * @constructor
     */
    ResetChannel:function (name, index) {
        this.Sounds[name][index].currentTime = 0;
        this.StopLoop(name, index);
        return this
    },

    /**
     * 停止播放声音
     * @param name 声音索引名
     * @return {*}
     * @constructor
     */
    ResetSound:function (name) {
        for (var i = 0; i < this.Sounds[name].length; i++) {
            this.Sounds[name].currentTime = 0;
            this.StopLoop(name, i);
        }
        return this
    },

    /**
     * 移除播放结束事件
     * @param name
     * @param index
     * @constructor
     */
    StopLoop:function (name, index) {
        this.Sounds[name][index].removeEventListener("ended", this.LoopCallback, !1)
    },

    /**
     * 注册播放结束事件
     * @param name
     * @param index
     * @constructor
     */
    StartLoop:function (name, index) {
        this.Sounds[name][index].addEventListener("ended", this.LoopCallback, !1)
    },

    /**
     * 循环播放回调函数
     * @constructor
     */
    LoopCallback:function () {
        this.currentTime = -1;
        this.play()
    }
};

/**
 * 可绘制对象基本
 */
Engine.Drawable = function () {
    this.ZOrder = 0
};
Engine.Drawable.prototype = {
    Draw:function () {
    }
};

/**
 * 游戏状态上下文
 * @param state
 * @constructor
 */
Engine.GameStateContext = function (state) {
    this.State = null;
    if (state != null) {
        this.State = state;
        this.State.Enter()
    }
};
Engine.GameStateContext.prototype = {
    /**
     * 游戏状态改变
     */
    ChangeState:function (state) {
//        this.State != null && this.State.Exit();
        if (this.State != null) {
            this.State.Exit();
        }
        this.State = state;
        this.State.Enter()
    },

    /**
     * 游戏状态更新
     */
    Update:function (state) {
        this.State.CheckForChange(this);
        this.State.Update(state)
    },

    /**
     * 显示游戏
     */
    Draw:function (state) {
        this.State.Draw(state)
    }
};

/**
 * 游戏状态
 */
Engine.GameState = function () {
};
Engine.GameState.prototype = {
    /**
     * 开始
     */
    Enter:function () {
    },

    /**
     * 结束
     */
    Exit:function () {
    },

    /**
     * 更新
     */
    Update:function () {
    },

    /**
     * 显示
     */
    Draw:function () {
    },

    /**
     * 验证
     */
    CheckForChange:function () {
    }
};

/**
 * 游戏时钟
 */
Engine.GameTimer = function () {
    this.FramesPerSecond = 1E3 / 30;
    this.LastTime = 0;
    this.UpdateObject = null;
    this.IntervalFunc = null
};
Engine.GameTimer.prototype = {
    Start:function () {
        this.LastTime = (new Date).getTime();
        var _this = this;
        this.IntervalFunc = setInterval(function () {
            _this.Tick()
        }, this.FramesPerSecond)
    },
    Tick:function () {
        if (this.UpdateObject != null) {
            var time = (new Date).getTime();
            var offset = (time - this.LastTime) / 1E3;
            this.LastTime = time;
            this.UpdateObject.Update(offset)
        }
    },
    Stop:function () {
        clearInterval(this.IntervalFunc)
    }
};

Engine.Camera = function () {
    this.Y = this.X = 0
};

Engine.DrawableManager = function () {
    this.Unsorted = !0;
    this.Objects = []
};
Engine.DrawableManager.prototype = {
    Add:function (a) {
        this.Objects.push(a);
        this.Unsorted = !0
    },
    AddRange:function (a) {
        this.Objects = this.Objects.concat(a);
        this.Unsorted = !0
    },
    Clear:function () {
        this.Objects.splice(0, this.Objects.length)
    },
    Contains:function (a) {
        for (var b = this.Objects.length; b--;) {
            if (this.Objects[b] === a) {
                return!0;
            }
        }
        return!1
    },
    Remove:function (a) {
        this.Objects.splice(this.Objects.indexOf(a), 1)
    },
    RemoveAt:function (a) {
        this.Objects.splice(a, 1)
    },
    RemoveRange:function (a, b) {
        this.Objects.splice(a, b)
    },
    RemoveList:function (a) {
        for (var b = 0, c = 0, c = 0; c < a.length;) {
            for (b = 0; b < this.Objects.length; b++)if (this.Objects[b] === a[c]) {
                this.Objects.splice(b, 1);
                a.splice(c, 1);
                c--;
                break;
            }
        }
    },
    Update:function (a) {
        for (var b = 0, b = 0; b < this.Objects.length; b++) {
//            this.Objects[b].Update && this.Objects[b].Update(a)
            if (this.Objects[b].Update) {
                this.Objects[b].Update(a);
            }
        }
    },
    Draw:function (a, b) {
        if (this.Unsorted) {
            this.Unsorted = !1;
            this.Objects.sort(function (a, b) {
                return a.ZOrder - b.ZOrder;
            });
        }
        for (var c = 0, c = 0; c < this.Objects.length; c++) {
//            this.Objects[c].Draw && this.Objects[c].Draw(a, b);
            if (this.Objects[c].Draw) {
                this.Objects[c].Draw(a, b);
            }
        }
    }
};

/**
 * 图片精灵
 */
Engine.Sprite = function () {
    this.Y = this.X = 0;
    this.Image = null
};
Engine.Sprite.prototype = new Engine.Drawable;
Engine.Sprite.prototype.Draw = function (a, b) {
    a.drawImage(this.Image, this.X - b.X, this.Y - b.Y)
};

/**
 * 文字精灵
 */
Engine.SpriteFont = function (strings, img, width, height, letters) {
    this.Image = img;
    this.Letters = letters;
    this.LetterWidth = width;
    this.LetterHeight = height;
    this.Strings = strings
};
Engine.SpriteFont.prototype = new Engine.Drawable;
Engine.SpriteFont.prototype.Draw = function (a) {
    for (var b = 0; b < this.Strings.length; b++) {
        var c = this.Strings[b];
        for (var d = 0; d < c.String.length; d++) {
            var e = c.String.charCodeAt(d);
            a.drawImage(this.Image, this.Letters[e].X, this.Letters[e].Y, this.LetterWidth, this.LetterHeight, c.X + this.LetterWidth * (d + 1), c.Y, this.LetterWidth, this.LetterHeight);
        }
    }
};

/**
 * 窗体精灵
 */
Engine.FrameSprite = function () {
    this.FrameHeight = this.FrameWidth = this.FrameY = this.FrameX = 0
};
Engine.FrameSprite.prototype = new Engine.Sprite;
Engine.FrameSprite.prototype.Draw = function (a, b) {
    a.drawImage(this.Image, this.FrameX, this.FrameY, this.FrameWidth, this.FrameHeight, this.X - b.X, this.Y - b.Y, this.FrameWidth, this.FrameHeight)
};

/**
 * 动画序列
 */
Engine.AnimationSequence = function (a, b, c, d) {
    this.StartRow = a;
    this.StartColumn = b;
    this.EndRow = c;
    this.EndColumn = d;
    this.SingleFrame = !1;
    if (this.StartRow == this.EndRow && this.StartColumn == this.EndColumn) {
        this.SingleFrame = !0;
    }
};
/**
 * 动画精灵
 */
Engine.AnimatedSprite = function () {
    this.LastElapsed = 0;
    this.FramesPerSecond = 0.05;
    this.CurrentSequence = null;
    this.Looping = this.Playing = !1;
    this.Columns = this.Rows = 0;
    this.Sequences = {}
};
Engine.AnimatedSprite.prototype = new Engine.FrameSprite;
Engine.AnimatedSprite.prototype.Update = function (a) {
    if (!this.CurrentSequence.SingleFrame && this.Playing && (this.LastElapsed -= a, !(this.LastElapsed > 0))) {
        this.LastElapsed = this.FramesPerSecond;
        this.FrameX += this.FrameWidth;
        if (this.FrameX > this.Image.width - this.FrameWidth && (this.FrameX = 0, this.FrameY += this.FrameHeight, this.FrameY > this.Image.height - this.FrameHeight)) {
            this.FrameY = 0;
        }
        a = !1;
//        this.FrameX > this.CurrentSequence.EndColumn * this.FrameWidth && this.FrameY == this.CurrentSequence.EndRow * this.FrameHeight ? a = !0 : this.FrameX ==
//            0 && this.FrameY > this.CurrentSequence.EndRow * this.FrameHeight && (a = !0);
        if (this.FrameX > this.CurrentSequence.EndColumn * this.FrameWidth) {
            if (this.FrameY == this.CurrentSequence.EndRow * this.FrameHeight) {
                a = !0
            } else {
                if (this.FrameX == 0) {
                    if (this.FrameY > this.CurrentSequence.EndRow * this.FrameHeight) {
                        a = !0;
                    }
                }
            }
        }
        if (a) {
            this.Looping ? (this.FrameX = this.CurrentSequence.StartColumn * this.FrameWidth, this.FrameY = this.CurrentSequence.StartRow * this.FrameHeight) : this.Playing = !1
        }
    }
};
Engine.AnimatedSprite.prototype.PlaySequence = function (a, b) {
    this.Playing = !0;
    this.Looping = b;
    this.CurrentSequence = this.Sequences["seq_" + a];
    this.FrameX = this.CurrentSequence.StartColumn * this.FrameWidth;
    this.FrameY = this.CurrentSequence.StartRow * this.FrameHeight
};
Engine.AnimatedSprite.prototype.StopLooping = function () {
    this.Looping = !1
};
Engine.AnimatedSprite.prototype.StopPlaying = function () {
    this.Playing = !1
};
Engine.AnimatedSprite.prototype.SetFrameWidth = function (a) {
    this.FrameWidth = a;
    this.Rows = this.Image.width / this.FrameWidth
};
Engine.AnimatedSprite.prototype.SetFrameHeight = function (a) {
    this.FrameHeight = a;
    this.Columns = this.Image.height / this.FrameHeight
};
Engine.AnimatedSprite.prototype.SetColumnCount = function (a) {
    this.FrameWidth = this.Image.width / a;
    this.Columns = a
};
Engine.AnimatedSprite.prototype.SetRowCount = function (a) {
    this.FrameHeight = this.Image.height / a;
    this.Rows = a
};
Engine.AnimatedSprite.prototype.AddExistingSequence = function (a, b) {
    this.Sequences["seq_" + a] = b
};
Engine.AnimatedSprite.prototype.AddNewSequence = function (a, b, c, d, e) {
    this.Sequences["seq_" + a] = new Engine.AnimationSequence(b, c, d, e)
};
Engine.AnimatedSprite.prototype.DeleteSequence = function (a) {
    this.Sequences["seq_" + a] != null && delete this.Sequences["seq_" + a]
};
Engine.AnimatedSprite.prototype.ClearSequences = function () {
    delete this.Sequences;
    this.Sequences = {}
};

/**
 * 碰撞
 * @param a
 * @param b
 * @param c
 * @param d
 * @constructor
 */
Engine.Collideable = function (a, b, c, d) {
    this.Base = a;
    this.X = a.X;
    this.Y = a.Y;
    this.Width = b;
    this.Height = c;
    this.CollisionEvent = d != null ? d : function () {
    }
};
Engine.Collideable.prototype = {
    Update:function () {
        this.X = this.Base.X;
        this.Y = this.Base.Y
    },
    CheckCollision:function (a) {
        if (!(this.Y + this.Height < a.Y) && !(this.Y > a.Y + a.Height) && !(this.X + this.Width < a.X) && !(this.X > a.X + a.Width)) {
            this.CollisionEvent(a);
            a.CollisionEvent(this);
        }
    }
};

/**
 * 入口
 */
Engine.Application = function () {
    this.stateContext = this.timer = this.canvas = null
};
Engine.Application.prototype = {
    Update:function (a) {
        this.stateContext.Update(a);
        this.canvas.BeginDraw();
        this.stateContext.Draw(this.canvas.BackBufferContext2D);
        this.canvas.EndDraw()
    },
    Initialize:function (a, b, c) {
        this.canvas = new Engine.GameCanvas;
        this.timer = new Engine.GameTimer;
        Engine.KeyboardInput.Initialize();
        this.canvas.Initialize("canvas", b, c);
        this.timer.UpdateObject = this;
        this.stateContext = new Engine.GameStateContext(a);
        this.timer.Start()
    }
};