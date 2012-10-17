/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.Desktop = function (app) {
    this.taskBar = new Ext.ux.TaskBar(app);
    this.xTickSize = this.yTickSize = 1;

    var desktopEl = Ext.get('x-desktop');
    var taskBarEl = Ext.get('ux-taskbar');
    var shortcuts = Ext.get('x-shortcuts');

    var windows = new Ext.WindowGroup();
    var activeWindow;

    Ext.EventManager.onWindowResize(layout);
    layout();

    function minimizeWin(win) {
        win.minimized = true;
        win.hide();
    }

    function markActive(win) {
        if (activeWindow && activeWindow != win) {
            markInactive(activeWindow);
        }
        this.taskBar.setActiveButton(win.taskButton);
        activeWindow = win;
        Ext.fly(win.taskButton.el).addClass('active-win');
        win.minimized = false;
    }

    function markInactive(win) {
        if (win == activeWindow) {
            activeWindow = null;
            Ext.fly(win.taskButton.el).removeClass('active-win');
        }
    }

    function removeWin(win) {
        this.taskBar.removeTaskButton(win.taskButton);
        layout();
    }

    function layout() {
        desktopEl.setHeight(Ext.lib.Dom.getViewHeight() - taskBarEl.getHeight());
    }

    this.createWindow = function (config, cls) {
        var win = new (cls || Ext.Window)(
            Ext.applyIf(config || {}, {
                renderTo:desktopEl,
                manager:windows,
                minimizable:true,
                maximizable:true
            })
        );
        win.dd.xTicke = this.xTickSize;
        win.dd.yTick = this.yTickSize;
        //win.resizer.widthIncrement = this.xTickSize;
        //win.resizer.heightIncrement = this.yTickSize;
        win.render(desktopEl);

        //js module
        if (win.id == 'js-win-editor') {
            win.animateTarget = app.quickStartButtons['showJSEditor'];
            win.on({
                'minimize':{
                    fn:minimizeWin
                }
            });
            layout();
            return win;
        }
        //robot module
        if (win.id.indexOf('robot-win') != -1) {
            win.animateTarget = app.robotQuickStartButtons[win.id.substring(0, win.id.indexOf('-'))];
            win.on({
                'minimize':{
                    fn:minimizeWin
                }
            });
            layout();
            return win;
        }
        //other module
        win.taskButton = this.taskBar.addTaskButton(win);

        win.animateTarget = win.taskButton.el;

        win.on({
            'activate':{
                fn:markActive
            },
            'beforeshow':{
                fn:markActive
            },
            'deactivate':{
                fn:markInactive
            },
            'minimize':{
                fn:minimizeWin
            },
            'close':{
                fn:removeWin
            }
        });
        layout();
        return win;
    };//create end

    this.getManager = function () {
        return windows;
    };

    this.getWindow = function (id) {
        return windows.get(id);
    };

    this.getWinWidth = function () {
        var width = Ext.lib.Dom.getViewWidth();
        return width < 200 ? 200 : width;
    };

    this.getWinHeight = function () {
        var height = (Ext.lib.Dom.getViewHeight() - taskBarEl.getHeight());
        return height < 100 ? 100 : height;
    };

    this.getWinX = function (width) {
        return (Ext.lib.Dom.getViewWidth() - width) / 2
    };

    this.getWinY = function (height) {
        return (Ext.lib.Dom.getViewHeight() - taskBarEl.getHeight() - height) / 2;
    };

    this.setTickSize = function (xTickSize, yTickSize) {
        this.xTickSize = xTickSize;
        if (arguments.length == 1) {
            this.yTickSize = xTickSize;
        } else {
            this.yTickSize = yTickSize;
        }
        windows.each(function (win) {
            win.dd.xTickSize = this.xTickSize;
            win.dd.yTickSize = this.yTickSize;
            win.resizer.widthIncrement = this.xTickSize;
            win.resizer.heightIncrement = this.yTickSize;
        }, this);
    };

    this.cascade = function () {
        var x = 0, y = 0;
        windows.each(function (win) {
            if (win.isVisible() && !win.maximized) {
                win.setPosition(x, y);
                x += 20;
                y += 20;
            }
        }, this);
    };

    desktopEl.on('contextmenu', function (e) {
        e.stopEvent();
        //this.contextMenu.showAt(e.getXY());
    }, this);

    // add shortcuts click event
    if (shortcuts) {
        //shortcuts.insertHtml();
        shortcuts.on('click', function (e, t) {
            if (t = e.getTarget('dt', shortcuts)) {
                e.stopEvent();
                MainApp.getDesktop().openGameModule(t.id.replace('-shortcut', ''), true, 'game')
            }
        });
    }//end add shortcuts clock event

    this.openGameModule = function (id, openEditor, editorType, codeFile) {
        var win = MainApp.getDesktop().getWindow(id);
        var module = ModuleLoader.getModule(id);
        var editorWin = null;
        var editor = null;
        if (!win) {
            if (module) {
                module.createWindow();
                win = MainApp.getDesktop().getWindow(id);
                module.addDefaultToolBar();
            }
        }
        if (openEditor) {
            if (editorType == 'game') {
                editorWin = MainApp.getDesktop().getWindow(DEFAULT_JS_EDITOR_WIN_ID);
                editor = ModuleLoader.getModule(DEFAULT_JS_EDITOR_ID);
                if (editorWin) {
                    if (editorWin.minimized) {
                        editorWin.show();
                    }
                    if (!editor.getEditorTab(module.id))
                        editor.addEditorTab(module.id);
                } else {
                    editor.createWindow();
                    editor.addEditorTab(module.id);
                }
                if (codeFile)
                    editor.loadSource();
                win.addListener('show', module.showE);
                win.addListener('resize', module.resizeE);
                win.addListener('move', module.moveE);
                win.addListener('activate', module.moveE);
            } else if (editorType == 'editor') {
                editorWin = MainApp.getDesktop().getWindow(DEFAULT_ROBOT_JS_EDITOR_WIN_ID);
                editor = ModuleLoader.getModule(DEFAULT_ROBOT_JS_EDITOR_ID);
                if (editorWin) {
                    if (editorWin.minimized) {
                        editorWin.show();
                    }
                    if (!editor.getEditorTab(module.id))
                        editor.addEditorTab(module.id);
                } else {
                    editor.createWindow();
                    editor.addEditorTab(module.id);
                }
                if (codeFile)
                    editor.loadSource(CodeLoader.loadCode(codeFile))
            }
        }
    }
};
