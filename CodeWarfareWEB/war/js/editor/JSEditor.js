// JavaScript Document
DCC.editor.JSEditor =  Ext.extend(DCC.editor.EditorModule, {
    id:'js-win-editor',
	target:'',
	editorPanel : new Ext.TabPanel({
		id:'js-panel-editor',
		activeTab:0,
		enableTabScroll:true
	}),
	
    init : function(){
		function beforeRemoveTabEvent(tabPanel, tab) {
			var gameWin = MyDesktop.getDesktop().getWindow(tab.title);
			var editorWin= MyDesktop.getDesktop().getWindow('js-win-editor');
			if(!gameWin) return true;
			DCC.widget.show({
				title: 'Are you sure close',
				msg: 'If you close this tab ,<br/>the game called ' + tab.title + ' will be closed at the same time.',
				buttons: Ext.Msg.YESNO,
				icon: Ext.Msg.QUESTION,
				fn: function(btn) {
					if (btn == 'yes') {
						if(gameWin){
							gameWin.close();
						}
						tabPanel.un('beforeremove', beforeRemoveTabEvent);
						tabPanel.remove(tab);
						tabPanel.addListener('beforeremove', beforeRemoveTabEvent, tabPanel);
					}
				}
			});
			//DCC.widget.topAlert('','');
			//Ext.MessageBox.prompt('','');
			return false;    
		}
		function afterRemoveTabEvent(tabPanel, tab){
			if(tabPanel.items.length == 0) {
				var editorWin = MyDesktop.getDesktop().getWindow('js-win-editor');
				editorWin.hide();
				editorWin.minimized = true;
			};
		}
		this.editorPanel = new Ext.TabPanel({
			id:'editor-panel',
			activeTab:0,
			enableTabScroll:true,
			listeners: {
				tabchange:function(tabPanel, tab){
					if(!tab)return;
					var gameWin = MyDesktop.getDesktop().getWindow(tab.title);	
					gameWin.toFront();
					gameWin.fireEvent('move');
				},
				beforeremove:beforeRemoveTabEvent,
				remove:afterRemoveTabEvent
			},
			plugins: new Ext.ux.TabCloseMenu()
		})
    },//init end
	
  	createWindow : function(){
		var desktop = MyDesktop.getDesktop();
        var win = desktop.getWindow('js-win-editor');
		if(!win){
			win =desktop.createWindow({
				id: 'js-win-editor',
                title: 'Code Editor Window',
                width:500,
                height:500,
                iconCls: 'icon-editor-win',
                shim:false,
                animCollapse:true,
                constrainHeader:true,
				maximizable:false,
				minimizable: true,
				closable:false,
				resizable:true,
				modal:false,
				
				layout:'fit',
				tbar:[{
					cls : "x-btn-icon",
					iconCls : "icon-editor-loadsource",
					handler : this.loadSource,
					tooltip : "Load your source from local or web space",
					scope : this
				},{
					cls : "x-btn-icon",
					iconCls : "icon-editor-savesource",
					handler : this.saveSource,
					tooltip : "Save your source to local or web space",
					scope : this
				},{
					cls : "x-btn-icon",
					iconCls : "icon-editor-sharesource",
					handler : this.shareSource,
					tooltip : "Share your source to bbs",
					scope : this
				},{
					cls : "x-btn-icon",
					iconCls : "icon-editor-runsource",
					handler : this.runSource,
					tooltip : "Run your source",
					scope : this
				}],
				items:this.editorPanel
			});
		}//if end
		win.show();
	}//createWindow end
});