// JavaScript Document
Ext.app.GameModule = function (config) {
    Ext.apply(this, config);
    Ext.app.GameModule.superclass.constructor.call(this);
    this.init();
}

Ext.extend(Ext.app.GameModule, Ext.util.Observable, {
    id:null,
    init:Ext.emptyFn,
    restart:Ext.emptyFn,

    registDefaultMethod:Ext.emptyFn,
    defaultMothed:Ext.emptyFn,
    registBeginMethod:Ext.emptyFn,
    registLoopMethod:Ext.emptyFn,
    getLoopTime:Ext.emptyFn,
    registStopMethod:Ext.emptyFn,
    registWinMethod:Ext.emptyFn,

    getWindow:function () {
        return this.getDesktop().getWindow(this.id + "-win");
    },
    getDesktop:function () {
        return MainApp.getDesktop();
    },
    getWindowManager:function () {
        return this.getDesktop().getManager()
    },

    moveE:function (win) {
        //var availWidth = Ext.get('x-desktop').getWidth(true);
        //var availHeight = Ext.get('x-desktop').getHeight(true);
        var editorWin = MainApp.getDesktop().getWindow(DEFAULT_JS_EDITOR_WIN_ID);
        if (win && editorWin) {
            //if(editor.hidden) editor.show();
            var w = win.getWidth();
            var x = win.x;
            var y = win.y;
            //y+(editor.getHeight()==0?editor.height:editor.getHeight())>availHeight?y=availHeight-(editor.getHeight()==0?editor.height:editor.getHeight()):y;
            editorWin.setPosition(x + w, y)
            editorWin.toFront()

//            var module = ModuleLoader.getModuleInstance(DEFAULT_JS_EDITOR_ID);
//            win.un('activate', module.moveE);
//            module.setActive(win.id);
//            win.toFront();
//            win.addListener('activate', module.moveE);
        }
    },

    showE:function (win) {
        var editorWin = MainApp.getDesktop().getWindow(DEFAULT_JS_EDITOR_WIN_ID);
        if (editorWin.minimized) {
            editorWin.minimized = false;
            editorWin.show();
        }
        this.fireEvent('resize', win);
    },

    resizeE:function (win) {
        var availWidth = Ext.get('x-desktop').getWidth(true);
        var availHeight = Ext.get('x-desktop').getHeight(true);
        var x = 0;
        var y = 0;
        var editorWin = MainApp.getDesktop().getWindow(DEFAULT_JS_EDITOR_WIN_ID);
        if (win && editorWin) {
            //if(editor.hidden) editor.show();
            var w = win.getWidth();
            var h = win.getHeight();
            var e_w = editorWin.getWidth() == 0 ? editorWin.width : editorWin.getWidth();
            var e_h = editorWin.getHeight() == 0 ? editorWin.height : editorWin.getHeight();

            x = Math.max(0, (availWidth - w - e_w) / 2);
            y = Math.max(0, (availHeight - Math.max(h, e_h)) / 2);
            //e_y = (availHeight - e_h) / 2;

            win.setPosition(x, y);
            editorWin.setPosition(x + w, y)
        }
    },

    addDefaultToolBar:function () {
        var win = this.getWindow();
        var topTool = win.getTopToolbar();
        var showEditorBar = new Ext.Button({
            toolTip:'showEditor',
            iconCls:'all-game-bar-showeditor',
            listeners:{
                click:function () {
                    var win = this.getWindow();
                    var editorWin = MainApp.getDesktop().getWindow(DEFAULT_JS_EDITOR_WIN_ID);
                    var editor = ModuleLoader.getModuleInstance(DEFAULT_JS_EDITOR_ID);
                    if (editorWin) {
                        if (!editor.getEditorTab(this.id))
                            editor.addEditorTab(this.id);
                    } else {
                        editor.createWindow();
                        editor.addEditorTab(this.id);
                        if (!win.hasListener('show') && !win.hasListener('move') && !win.hasListener('resize')) {
                            win.addListener('show', this.showE);
                            win.addListener('resize', this.resizeE);
                            win.addListener('move', this.moveE);
                        }
                    }
                    if (editorWin.hidden)editorWin.show();
                        win.fireEvent('move', win);
                },
                //}.createDelegate(followBar, [this.getWindow(), this.getThis()]),
                scope:this
            }
        });

        var followBar = new Ext.Button({
            id:'game-bar-follow-' + this.id,
            toolTip:'follow',
            iconCls:'all-game-bar-follow',
            listeners:{
                click:function () {
                    var win = this.getWindow();
                    var b = win.getTopToolbar().get('game-bar-follow-' + this.id);
                    b.iconCls == 'all-game-bar-follow' ? b.setIconClass('all-game-bar-follow-un') : b.setIconClass('all-game-bar-follow');
                    if (win.hasListener('show') && win.hasListener('move') && win.hasListener('resize') && win.hasListener('activate')) {
                        win.removeListener('show', this.showE);
                        win.removeListener('resize', this.resizeE);
                        win.removeListener('move', this.moveE);
                        win.removeListener('activate', this.moveE);
                    } else {
                        win.addListener('show', this.showE);
                        win.addListener('resize', this.resizeE);
                        win.addListener('move', this.moveE);
                        win.addListener('activate', this.moveE);
                        win.fireEvent('move', win);
                    }
                },
                //}.createDelegate(followBar, [this.getWindow(), this.getThis()]),
                scope:this
            }
        });

        if (topTool) {
            topTool.addSpacer();
            topTool.addSeparator();
            topTool.addSpacer();
            topTool.addItem(followBar);
            topTool.addSpacer();
            topTool.addSeparator();
            topTool.addSpacer();
            topTool.addItem(showEditorBar);
        }
        win.doLayout();
    }
});
