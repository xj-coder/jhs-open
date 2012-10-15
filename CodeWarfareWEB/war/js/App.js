/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.app.App = function(cfg){
    Ext.apply(this, cfg);
    this.addEvents({
        'ready' : true,
        'beforeunload' : true
    });

    Ext.onReady(this.initApp, this);
};

Ext.extend(Ext.app.App, Ext.util.Observable, {
    isReady: false,
    startMenu: null,
	modulesLoader:new DCC.util.ModuleLoader(),
    modules: new Array(),
	quickStartButtons:[],
	robotQuickStartButtons:[],

    getStartConfig : Ext.emptyFn(),

    initApp : function(){
    	this.startConfig = this.startConfig || this.getStartConfig();

        this.desktop = new Ext.Desktop(this);

		this.launcher = this.desktop.taskbar.startMenu;

		this.init();

        this.initQuickStartButton();

        this.initRobotQuickStartButton();

        this.initLaucher();

        Ext.EventManager.on(window, 'beforeunload', this.onUnload, this);
		this.fireEvent('ready', this);
        this.isReady = true;
    },

    init : Ext.emptyFn(),

	getStaticModules : Ext.emptyFn,

	initQuickStartButton : function(){
		var showDesktop = this.desktop.taskbar.quickStartPanel.add({
				handler:  function(){
					this.desktop.getManager().each(function(win) {
						if(!win.minimized){
							win.minimized = true;
							win.hide();
						}
					}, this)
				},
				iconCls: 'showdesktop-quick-start-button',
				scope: this,
				tooltip: 'Show Desktop'
		});
		var showJSEditor = this.desktop.taskbar.quickStartPanel.add({
				handler: function(){
					var editorWin = MyDesktop.getDesktop().getWindow('js-win-editor');
					var editor = MyDesktop.getModule('js-win-editor',true);
					if(editorWin){
						if(editorWin.minimized){
							editorWin.show();
							editorWin.minimized = false;
						}else{
							editorWin.hide();
							editorWin.minimized = true;
						}
					}else{
						editor.createWindow();
						var editorWin = MyDesktop.getDesktop().getWindow('js-win-editor');
						editorWin.minimized = false;
					}
				},
				iconCls: 'js-quick-start-button',
				scope: this,
				tooltip: 'Show JSEditor'
		});
		this.quickStartButtons['showDesktop'] = showDesktop;
		this.quickStartButtons['showJSEditor'] = showJSEditor;
	},

	initRobotQuickStartButton : function(){
		var david = this.desktop.taskbar.quickStartPanel_R.add({
				id:'david-robot-win-button',
				handler:  function(btn){
					var robotWin = MyDesktop.getDesktop().getWindow(btn.id.replace('-button',''));
					var robot = MyDesktop.getModule(btn.id.replace('-button',''),true);
					if(robotWin){
						if(robotWin.minimized){
							robotWin.show();
							robotWin.minimized = false;
						}else{
							robotWin.hide();
							robotWin.minimized = true;
						}
					}else{
						robot.createWindow();
						var robotWin = MyDesktop.getDesktop().getWindow(btn.id.replace('-button',''));
						robotWin.minimized = false;
					}
					//robotWin.addListener('beforeshow',robot.showE);
				},
				iconCls: 'david-robot-quick-start-button',
				scope: this,
				tooltip: 'David Robot',
				tooltipType:'title'
		});
		this.robotQuickStartButtons['david'] = david;
	},

    initLaucher : function(){
		var json = new Ext.data.Store({
		reader:new Ext.data.ArrayReader({},[
		   {name:'top'},
		   {name:'util'},
		   {name:'game'}
		]),
			data:DCC.data.Launcher
		});
		var topjson = new Ext.data.Store({
		reader:new Ext.data.ArrayReader({},[
		   {name:'id'},
		   {name:'name'},
		   {name:'iconCls'}
		]),
			data:json.getAt(0).get('top')
		});
		var utilJson = new Ext.data.Store({
		reader:new Ext.data.ArrayReader({},[
		   {name:'id'},
		   {name:'name'},
		   {name:'iconCls'}
		]),
			data:json.getAt(0).get('util')
		});
		var gameJson = new Ext.data.Store({
		reader:new Ext.data.ArrayReader({},[
		   {name:'id'},
		   {name:'name'},
		   {name:'iconCls'}
		]),
			data:json.getAt(0).get('game')
		});
		//Top
		for(var i = 0, len = topjson.getCount(); i < len; i++){
            this.launcher.add({
				id:topjson.getAt(i).get('id'),
				text:topjson.getAt(i).get('name'),
				iconCls:topjson.getAt(i).get('iconCls'),
				handler:this.doClick,
				scope: this
			});
        }
		//Util
		var utilMenu = new Ext.menu.Menu({
			id:'ux-start-util-menu',
			iconCls:'icon-ux-start-util-menu'
		});
		for(var i = 0, len = utilJson.getCount(); i < len; i++){
            utilMenu.add({
				id:utilJson.getAt(i).get('id'),
				text:utilJson.getAt(i).get('name'),
				iconCls:utilJson.getAt(i).get('iconCls'),
				handler:this.doClick,
				scope: this
			});
        }
		var utilItem = new Ext.menu.Item({
			id:'ux-start-util-item',
			iconCls:'icon-ux-start-util-item',
			text:'util',
			handler:function (){
				return false;
			},
			menu:utilMenu
		});
		//Game
		var gameMenu = new Ext.menu.Menu({
			id:'ux-start-game-menu',
			iconCls:'icon-ux-start-game-menu'
		});
		for(var i = 0, len = gameJson.getCount(); i < len; i++){
            gameMenu.add({
				id:gameJson.getAt(i).get('id'),
				text:gameJson.getAt(i).get('name'),
				iconCls:gameJson.getAt(i).get('iconCls'),
				handler:this.doClick,
				scope: this
			});
        }
		var gameItem = new Ext.menu.Item({
			id:'ux-start-game-item',
			iconCls:'icon-ux-start-game-item',
			text:'Game',
			handler:function (){
				return false;
			},
			menu:gameMenu
		});
		//
		this.launcher.add('-')
		//this.launcher.add(utilItem);
		this.launcher.add(gameItem);
    },//initLaucher end

	getModules: function(){
		return this.modules;
	},
	getModule: function(id,load){
		for(var i=0;i<this.modules.length;i++){
			if(this.modules[i]&&this.modules[i].id==id)return this.modules[i]
		}
		if(load)
		    return this.loadModule(id);
	},
	removeModule : function(module){
		this.modules.remove(module);
	},

    loadModule : function(id){
		_module = this.modulesLoader.loadModule(id);
		this.modules.push(_module)
        return _module;
    },

    onReady : function(fn, scope){
        if(!this.isReady){
            this.on('ready', fn, scope);
        }else{
            fn.call(scope, this);
        }
    },

    getDesktop : function(){
        return this.desktop;
    },

    onUnload : function(e){
        if(this.fireEvent('beforeunload', this) === false){
            e.stopEvent();
        }
    },
	doClick : function(t){
		var win = MyDesktop.getDesktop().getWindow(t.id.replace('-start',''));
		if(!win){
			var module = MyDesktop.getModule(t.id.replace('-start',''),true);
			if(module){
				module.createWindow();
				win = MyDesktop.getDesktop().getWindow(t.id.replace('-start',''));
				//if GameModule then do something
				if(module.id.substring(module.id.length-4,module.id.length)=='game'){
					var editorWin = MyDesktop.getDesktop().getWindow('js-win-editor');
					var editor = MyDesktop.getModule('js-win-editor',true);
					if(editorWin){
						if(!editor.getEditorTab(module.id))
							editor.addEditorTab(module.id);
					}else{
						editor.createWindow();
						editor.addEditorTab(module.id);
					}
					win.addListener('show',module.showE);
					win.addListener('resize',module.resizeE);
					win.addListener('move',module.moveE);
					win.addListener('activate',module.moveE);
					module.addDefaultToolBar();
				}
			}
		}
	}
});
