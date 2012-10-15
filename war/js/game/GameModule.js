// JavaScript Document
Ext.app.GameModule = function(config){
    Ext.apply(this, config);
    Ext.app.GameModule.superclass.constructor.call(this);
    this.init();
}

Ext.extend(Ext.app.GameModule, Ext.util.Observable, {
    init : Ext.emptyFn,
	restart:Ext.emptyFn,
	registDefaultMethod :Ext.emptyFn,
	defaultMothed :Ext.emptyFn,
	registBeginMethod :Ext.emptyFn,
	registLoopMethod :Ext.emptyFn,
	getLoopTime :Ext.emptyFn,
	registStopMethod :Ext.emptyFn,
	registWinMethod :Ext.emptyFn,
	getThis : function(){
		return module = MyDesktop.getModule(this.id,false);	
	},
	getWindow : function(){
		return this.getDesktop().getWindow(this.id);
	},
	getDesktop: function(){
		return MyDesktop.getDesktop();
	},
	getWindowManager:function(){
		return this.getDesktop	().getManager()
	},
	moveE : function(win) {
		var availWidth = Ext.get('x-desktop').getWidth(true);
		var availHeight = Ext.get('x-desktop').getHeight(true);
        var editor = MyDesktop.getDesktop().getWindow('js-win-editor');
		if (win&&editor) {
			//if(editor.hidden) editor.show();
			var w = win.getWidth();
			var x = win.x;
        	var y = win.y;
			//y+(editor.getHeight()==0?editor.height:editor.getHeight())>availHeight?y=availHeight-(editor.getHeight()==0?editor.height:editor.getHeight()):y;
			editor.setPosition(x+w,y)
			MyDesktop.getModule('js-win-editor').setActive(win.id);
			editor.toFront()
			win.un('activate',module.moveE);
			win.toFront();
			win.addListener('activate',module.moveE);
		}
    },
	
	showE : function(win) {
		var editorWin = MyDesktop.getDesktop().getWindow('js-win-editor');
		if(editorWin.minimized){
			editorWin.minimized = false;
			editorWin.show();
		}
        this.fireEvent('resize',win);
    },
	
	resizeE : function(win) {
        var availWidth = Ext.get('x-desktop').getWidth(true);
		var availHeight = Ext.get('x-desktop').getHeight(true);
        var x = 0;
        var y = 0;
        var editor = MyDesktop.getDesktop().getWindow('js-win-editor');
		if (win&&editor) {
			//if(editor.hidden) editor.show();
			var w = win.getWidth();
			var h = win.getHeight();
			var e_w = editor.getWidth()==0?editor.width:editor.getWidth();
			var e_h = editor.getHeight()==0?editor.height:editor.getHeight();
			
			x = Math.max(0,(availWidth - w -e_w) / 2);
			y = Math.max(0,(availHeight - Math.max(h,e_h)) / 2);
			//e_y = (availHeight - e_h) / 2;

			win.setPosition(x, y);
			editor.setPosition(x+w,y)
		}
    },
	
	addDefaultToolBar:function(){
		var win = this.getWindow();
		var topTool = win.getTopToolbar();
		var showEditorBar = new Ext.Button({
			toolTip:'showEditor',
			iconCls:'all-game-bar-showeditor',
			listeners:{
				click:function(win,t){
					var editorWin = MyDesktop.getDesktop().getWindow('js-win-editor');
					var editor = MyDesktop.getModule('js-win-editor',true);
					if(editorWin){
						if(!editor.getEditorTab(win.id))
							editor.addEditorTab(win.id);	
					}else{
						editor.createWindow();
						editor.addEditorTab(win.id);
						if(!win.hasListener('show')&&!win.hasListener('move')&&!win.hasListener('resize')){
							win.addListener('show',t.showE);
							win.addListener('resize',t.resizeE);
							win.addListener('move',t.moveE);	
						}
					}
					if(editorWin.hidden)editorWin.show();
					win.fireEvent('move',win);
				}.createDelegate(followBar,[this.getWindow(),this.getThis()])
			}
		});
		var followBar = new Ext.Button({
			id:'game-bar-follow-'+this.getThis().id,
			toolTip:'follow',
			iconCls:'all-game-bar-follow',
			listeners:{
				click:function(win,t){
					var b = win.getTopToolbar().get('game-bar-follow-'+t.id);
					b.iconCls=='all-game-bar-follow'?b.setIconClass('all-game-bar-follow-un'):b.setIconClass('all-game-bar-follow');
					if(win.hasListener('show')&&win.hasListener('move')&&win.hasListener('resize')&&win.hasListener('activate')){;
						win.removeListener('show',t.showE);
						win.removeListener('resize',t.resizeE);
						win.removeListener('move',t.moveE);
						win.removeListener('activate',module.moveE);
					}else{
						win.addListener('show',t.showE);
						win.addListener('resize',t.resizeE);
						win.addListener('move',t.moveE);	
						win.addListener('activate',module.moveE);
						win.fireEvent('move',win);
					}
				}.createDelegate(followBar,[this.getWindow(),this.getThis()])
			}
		});
		if(topTool){
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