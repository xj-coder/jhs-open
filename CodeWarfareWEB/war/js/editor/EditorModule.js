// JavaScript Document
DCC.editor.EditorModule = function(config){
    Ext.apply(this, config);
    DCC.editor.EditorModule.superclass.constructor.call(this);
    this.init();
	this.id;
}
Ext.extend(DCC.editor.EditorModule, Ext.util.Observable, {
	init : Ext.emptyFn,
	setTarget : function(target){
		this.target = target;	
	},
	
	getTarget : function(){
		return target;	
	},
	
	loadSource : function(source) {
		var desktop = MyDesktop.getDesktop();
        var win = desktop.getWindow(this.id);
		var editorPanel = win.items.itemAt(0).getActiveTab();
		if(!editorPanel){
			DCC.widget.alert('Message','Not any code of game');
			return;
		}
		var editor = editorPanel.items.itemAt(0);
		editor.setSource(source);
	},//load end
	
	saveSource : function() {
		var editor = this;
	   
	},//save end
	
	shareSource : function(){
		var editor ;
		
	},//share end
	runSource : function(){
		var desktop = MyDesktop.getDesktop();
        var win = desktop.getWindow(this.id);
		var editorPanel = win.items.itemAt(0).getActiveTab();
		if(!editorPanel){
			DCC.widget.alert('Message','Not any code of game');
			return;
		}
		var editor = editorPanel.items.itemAt(0);
		var src = editor.getSource();
//alert(src);
		if(editor.target){
			var module = MyDesktop.getModule(editor.target,false);
			if(module){
				
				var json = new Ext.data.Store({
					reader:new Ext.data.ArrayReader({},[
					   {name:'method'},
					   {name:'mapping'}
					]),
					data:module.registMethod()
					//autoLoad:true
				});
				if(!src && module.registDefaultMethod()){
					src=module.registDefaultMethod();
				}
				src = src.replace(new RegExp('this.','g'),'MyDesktop.getModule(\''+module.id+'\').')
				for(var i=0;i<json.getCount();i++){																																																																																																														
					src = src.replace(new RegExp(json.getAt(i).get('mapping')+'[\(]','g'),'MyDesktop.getModule(\''+module.id+'\').'+json.getAt(i).get('method')+'(')
				}
				
				var fn = new Function(src);
				var times = 0;
				var beginMethod = module.registBeginMethod();
				var loopMethod = module.registLoopMethod();
				var stopMethod = module.registStopMethod();
				var winMethod = module.registWinMethod();
				
				if(winMethod){
					winMethod = 'return MyDesktop.getModule(\''+module.id+'\').'+winMethod+'()';
					winMethod = new Function(winMethod);
				}
				
				if(stopMethod){
					stopMethod = 'return MyDesktop.getModule(\''+module.id+'\').'+stopMethod+'()';
					stopMethod = new Function(stopMethod);
				}
				
				if(beginMethod){
					beginMethod = 'MyDesktop.getModule(\''+module.id+'\').'+beginMethod+'()';
					new Function(beginMethod)();
				}
				
				if(loopMethod){
					loopMethod = 'return MyDesktop.getModule(\''+module.id+'\').'+loopMethod+'()';
					loopMethod = new Function(loopMethod);
					var runner = new Ext.util.TaskRunner();
					runner.start({
						run:function(){
							if(stopMethod()){
								runner.stopAll();//(runner);
								return;
							}
							if(loopMethod()){
								try{
									var d = new Date();
									fn();
									times = times + ((new Date())-d);
								}catch(err){
									DCC.widget.show({title:'Error',msg:err,buttons: Ext.Msg.OK});	
								}
							}
							if(winMethod()){
								runner.stopAll();//(runner);
								DCC.widget.show({title:'End',msg:'use time: '+times+'ms',buttons: Ext.Msg.OK});	
							}
						},
						interval:module.getLoopTime()
					});
				}else{
					try{
						var d = new Date();
						fn();
						times = times + ((new Date())-d);
						if(winMethod()){
							runner.stopAll();//(runner);
							DCC.widget.show({title:'End',msg:'use time: '+times+'ms',buttons: Ext.Msg.OK});	
						}
					}catch(err){
						DCC.widget.show({title:'End',msg:'Use time: '+times+'ms',buttons: Ext.Msg.OK});
					}
				}
			}
		}
	},//run end
	
	help : function(){
		var editor ;
	},//help end
	
	addEditorTab : function(target){
		var tabEditor = new DCJSEditor();
		tabEditor.setTarget(target);
		var tabPanel = new Ext.Panel({
			title:target,
			header:false,
			border:false,
			layout:'fit',
			closable:true,
			items:tabEditor
		});
		this.editorPanel.add(tabPanel);
		this.editorPanel.setActiveTab(tabPanel);
		this.editorPanel.doLayout();
	},//addEditorTab end
	
	getEditorTab : function(id){
		var items = this.editorPanel.items;
		for(var i=0;i<items.length;i++){
			if(items.itemAt(i).title == id) return items.itemAt(i);
		}
	},
	
	setActive : function(id){
		var items = this.editorPanel.items;
		for(var i=0;i<items.length;i++){
			if(items.itemAt(i).title == id){
				this.editorPanel.setActiveTab(items.itemAt(i));
				break;
			}
		}
	},
	
	closeAllTab : function(){
		var items = this.editorPanel.items;
		for(var i=0;i<items.length;i++){
			this.editorPanel.remove(items.itemAt(i));
		}
	}
});