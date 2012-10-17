/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.app.App = function (cfg) {
    Ext.apply(this, cfg);
    this.addEvents({
        'ready':true,
        'beforeunload':true
    });
    Ext.onReady(this.initApp, this);
};

Ext.extend(Ext.app.App, Ext.util.Observable, {
    isReady:false,
    startMenu:null,
    //modulesLoader:new DCC.util.ModuleLoader(),
    modules:new Array(),
    quickStartButtons:[],
    robotQuickStartButtons:[],

    getStartConfig:Ext.emptyFn(),

    initApp:function () {
        this.startConfig = this.startConfig || this.getStartConfig();

        this.desktop = new Ext.Desktop(this);

        this.launcher = this.desktop.taskBar.startMenu;

        this.init();

        this.initQuickStartButton();

        this.initRobotQuickStartButton();

        this.initLauncher();

        Ext.EventManager.on(window, 'beforeunload', this.onUnload, this);
        this.fireEvent('ready', this);
        this.isReady = true;
    },

    init:Ext.emptyFn(),

    getStaticModules:Ext.emptyFn,

    initQuickStartButton:function () {
        var showDesktop = this.desktop.taskBar.quickStartPanel.add({
            handler:function () {
                this.desktop.getManager().each(function (win) {
                    if (!win.minimized) {
                        win.minimized = true;
                        win.hide();
                    }
                }, this)
            },
            iconCls:'showdesktop-quick-start-button',
            scope:this,
            tooltip:'Show Desktop'
        });
        var showJSEditor = this.desktop.taskBar.quickStartPanel.add({
            handler:function () {
                var editorWin = MainApp.getDesktop().getWindow(DEFAULT_JS_EDITOR_WIN_ID);
                var editor = ModuleLoader.getModule(DEFAULT_JS_EDITOR_ID);
                if (editorWin) {
                    if (editorWin.minimized) {
                        editorWin.show();
                        editorWin.minimized = false;
                    } else {
                        editorWin.hide();
                        editorWin.minimized = true;
                    }
                } else {
                    editor.createWindow();
                    var editorWin = MainApp.getDesktop().getWindow(DEFAULT_JS_EDITOR_WIN_ID);
                    editorWin.minimized = false;
                }
            },
            iconCls:'js-quick-start-button',
            scope:this,
            tooltip:'Show JSEditor'
        });
        this.quickStartButtons['showDesktop'] = showDesktop;
        this.quickStartButtons['showJSEditor'] = showJSEditor;
    },

    initRobotQuickStartButton:function () {
        var david = this.desktop.taskBar.quickStartPanel_R.add({
            id:'david-robot-win-button',
            handler:function (btn) {
                var robotWin = MainApp.getDesktop().getWindow(DEFAULT_ROBOT_WIN_ID);
                var robot = ModuleLoader.getModule(DEFAULT_ROBOT_ID);
                if (robotWin) {
                    if (robotWin.minimized) {
                        robotWin.show();
                        robotWin.minimized = false;
                    } else {
                        robotWin.hide();
                        robotWin.minimized = true;
                    }
                } else {
                    robot.createWindow();
                    // var robotWin = MainApp.getDesktop().getWindow(DEFAULT_ROBOT_WIN_ID);
                    // robotWin.minimized = false;
                }
                //robotWin.addListener('beforeshow',robot.showE);
            },
            iconCls:'david-robot-quick-start-button',
            scope:this,
            tooltip:'David Robot',
            tooltipType:'title'
        });
        this.robotQuickStartButtons['david'] = david;
    },

    initLauncher:function () {
        //Game
        var gameMenu = new Ext.menu.Menu({
            id:'ux-start-game-menu',
            iconCls:'icon-ux-start-game-menu'
        });
        for (var i = 0, len = gameLauncherJson.getCount(); i < len; i++) {
            var gameJsonItem = gameLauncherJson.getAt(i);
            gameMenu.add({
                id:gameJsonItem.get('id'),
                text:gameJsonItem.get('text'),
                iconCls:gameJsonItem.get('iconCls'),
                handler:this.doClick,
                scope:this
            });
        }
        var gameItem = new Ext.menu.Item({
            id:'ux-start-game-item',
            iconCls:'icon-ux-start-game-item',
            text:'Game',
            handler:function () {
                return false;
            },
            menu:gameMenu
        });
        this.launcher.add(gameItem);
    },

    onReady:function (fn, scope) {
        if (!this.isReady) {
            this.on('ready', fn, scope);
        } else {
            fn.call(scope, this);
        }
    },

    getDesktop:function () {
        return this.desktop;
    },

    onUnload:function (e) {
        if (this.fireEvent('beforeunload', this) === false) {
            e.stopEvent();
        }
    },
    doClick:function (t) {
        var win = MainApp.getDesktop().getWindow(t.id.replace('-start', ''));
        if (!win) {
            var module = ModuleLoader.getModule(t.id.replace('-start', ''));
            if (module) {
                module.createWindow();
                win = MainApp.getDesktop().getWindow(t.id.replace('-start', ''));
                //if GameModule then do something
                if (module.id.substring(module.id.length - 4, module.id.length) == 'game') {
                    var editorWin = MainApp.getDesktop().getWindow('js-win-editor');
                    var editor = ModuleLoader.getModule(DEFAULT_JS_EDITOR_ID);
                    if (editorWin) {
                        if (!editor.getEditorTab(module.id))
                            editor.addEditorTab(module.id);
                    } else {
                        editor.createWindow();
                        editor.addEditorTab(module.id);
                    }
                    win.addListener('show', module.showE);
                    win.addListener('resize', module.resizeE);
                    win.addListener('move', module.moveE);
                    win.addListener('activate', module.moveE);
                    module.addDefaultToolBar();
                }
            }
        }
    }
});
