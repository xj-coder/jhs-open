// JavaScript Document
DCC.game.Mini=Ext.extend(Ext.app.GameModule,{
	id:'mini-win-game',
	//name:'Mini',
	rank : 0,
	_size : 20,
	numArray : [50,120,180],
	widthArray : [15,25,30],
	heightArray : [15,25,30],
    init : function(){
		this.rank = 0;
		this.initGame();
    },
	initGame : function(){
		this.mm = new Array(this.widthArray[this.rank]*this.heightArray[this.rank]);//-1-->has mini;0~~other:��ʾ
		this._mm = new Array(this.widthArray[this.rank]*this.heightArray[this.rank]);//0-->not click;1-->clicked;2-->flat
		this._html = document.createElement('div');
		var _div = document.createElement('div');
		_div.setAttribute('align','center');
		_div.setAttribute('id','mini-panel-div');		
		this._html.appendChild(_div);

		for(var k=0;k<this.mm.length;k++){
			this.mm[k]=0;	
			this._mm[k]=0;
		}
		for(var k=0;k<this.numArray[this.rank];k++){
			var n = Math.round(Math.random()*this.widthArray[this.rank]*this.heightArray[this.rank]);
			if(this.mm[n]== -1){
				k--;
				continue;
			}
			this.mm[n] = -1;
			n+1>=0&&n+1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n+1]!=-1?this.mm[n+1]++:0;
			n-1>=0&&n-1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n-1]!=-1?this.mm[n-1]++:0;
			
			n+this.widthArray[this.rank]+1>=0&&n+this.widthArray[this.rank]+1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n+this.widthArray[this.rank]+1]!=-1?this.mm[n+this.widthArray[this.rank]+1]++:0;
			n+this.widthArray[this.rank]>=0&&n+this.widthArray[this.rank]<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n+this.widthArray[this.rank]]!=-1?this.mm[n+this.widthArray[this.rank]]++:0;
			n+this.widthArray[this.rank]-1>=0&&n+this.widthArray[this.rank]-1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n+this.widthArray[this.rank]-1]!=-1?this.mm[n+this.widthArray[this.rank]-1]++:0;
			
			n-this.widthArray[this.rank]+1>=0&&n-this.widthArray[this.rank]+1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n-this.widthArray[this.rank]+1]!=-1?this.mm[n-this.widthArray[this.rank]+1]++:0;
			n-this.widthArray[this.rank]>=0&&n-this.widthArray[this.rank]<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n-this.widthArray[this.rank]]!=-1?this.mm[n-this.widthArray[this.rank]]++:0;
			n-this.widthArray[this.rank]-1>=0&&n-this.widthArray[this.rank]-1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n-this.widthArray[this.rank]-1]!=-1?this.mm[n-this.widthArray[this.rank]-1]++:0;
			if(n%this.widthArray[this.rank]==0){
				n-1>=0&&n-1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n-1]!=-1?this.mm[n-1]--:0;
				n-this.widthArray[this.rank]-1>=0&&n-this.widthArray[this.rank]-1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n-this.widthArray[this.rank]-1]!=-1?this.mm[n-this.widthArray[this.rank]-1]--:0;
				n+this.widthArray[this.rank]-1>=0&&n+this.widthArray[this.rank]-1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n+this.widthArray[this.rank]-1]!=-1?this.mm[n+this.widthArray[this.rank]-1]--:0;
			}
			if((n+1)%this.widthArray[this.rank]==0){
				n+1>=0&&n+1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n+1]!=-1?this.mm[n+1]--:0;
				n-this.widthArray[this.rank]+1>=0&&n-this.widthArray[this.rank]+1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n-this.widthArray[this.rank]+1]!=-1?this.mm[n-this.widthArray[this.rank]+1]--:0;
				n+this.widthArray[this.rank]+1>=0&&n+this.widthArray[this.rank]+1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this.mm[n+this.widthArray[this.rank]+1]!=-1?this.mm[n+this.widthArray[this.rank]+1]--:0;
			}
		}
		for(var i=0;i<this.heightArray[this.rank];i++){
			for(var j=0;j<this.widthArray[this.rank];j++){
				var _BTN = document.createElement('input');
				_BTN.type = 'button';
				_BTN.className = 'mini-basic-button';
				_BTN.setAttribute('value','');
				_BTN.style.width = this._size;
				_BTN.style.height = this._size;
				_BTN.id = 'mini-basic-button-'+(i*this.widthArray[this.rank]+j);
				_div.appendChild(_BTN);
			}	
			_div.appendChild(document.createElement('br'));
		}
	},//initGame end
    createWindow : function(){
        var _win = this.getWindow();
        if(!_win){
            _win = this.getDesktop().createWindow({
                id: 'mini-win-game',
                title: 'Mini Window',
                width:(this._size+1.5)*this.widthArray[this.rank],
                height:(this._size+1.5)*this.heightArray[this.rank]+80-this.heightArray[this.rank],
                iconCls: 'icon-mini-win',
                shim:false,
                animCollapse:true,
                constrainHeader:true,
				maximizable:false,
				resizable:false,
				
                layout:'fit',
                border:false,
				
				tbar:[{
					xtype:'splitbutton',
					text:'Simple',
					tooltip:'Game Rank',
					iconCls:'mini-win-rank0',
					menu:[{
						xtype:'btnIcon',
						text:'Simple',
						iconCls:'mini-win-rank0',
						listeners :{
							click : function(t){
								this.restart();
								/*var win = t.getWindow();
								var module = t.getThis();
								module.rank = 0;
								win.getTopToolbar().items.itemAt(0).setText('Simple');
								win.getTopToolbar().items.itemAt(0).setIconClass('mini-win-rank0');
								win.setWidth((module._size+1.5)*module.widthArray[module.rank]);
								win.setHeight((module._size+1.5)*module.heightArray[module.rank]+50-module.heightArray[module.rank]);
								module.initGame();
								win.body.update(module._html.innerHTML);*/		
							}.createDelegate(this,[this.getThis()])
						}
					},{
						xtype:'btnIcon',
						text:'Medium',
						iconCls:'mini-win-rank1',
						listeners :{
							click:function(t){
								this.restart();
								/*var win = t.getWindow();
								var module = t.getThis();
								module.rank = 1;
								win.getTopToolbar().items.itemAt(0).setText('Medium');
								win.getTopToolbar().items.itemAt(0).setIconClass('mini-win-rank1');
								win.setWidth((module._size+1.5)*module.widthArray[module.rank]);
								win.setHeight((module._size+1.5)*module.heightArray[module.rank]+50-module.heightArray[module.rank]);
								module.initGame();
								win.body.update(module._html.innerHTML);*/		
							}.createDelegate(this,[this.getThis()])
						}
					},{
						xtype:'btnIcon',
						text:'Heavy',
						iconCls:'mini-win-rank2',
						listeners :{
							click:function(t){
								this.restart();
								/*var win = t.getWindow();
								var module = t.getThis();
								module.rank = 2;
								win.getTopToolbar().items.itemAt(0).setText('Heavy');
								win.getTopToolbar().items.itemAt(0).setIconClass('mini-win-rank2');
								win.setWidth((module._size+1.5)*module.widthArray[module.rank]);
								win.setHeight((module._size+1.5)*module.heightArray[module.rank]+50-module.heightArray[module.rank]);
								module.initGame();
								win.body.update(module._html.innerHTML);*/		
							}.createDelegate(this,[this.getThis()])
						}
					}],
					listeners :{
						click:function(t){
							var win = t.getWindow();
							var module = t.getThis();
							var _text = win.getTopToolbar().items.itemAt(0).getText();
							if(_text == "Simple"){
								module.rank=0;
							}else if(_text == "Medium"){
								module.rank=1;
							}else if(_text == "Heavy"){
								module.rank=2;
							}
							win.setWidth ((module._size+1.5)*module.widthArray[module.rank]);
							win.setHeight((module._size+1.5)*module.heightArray[module.rank]+50-module.heightArray[module.rank]);
							module.initGame();
							win.body.update(module._html.innerHTML);
						}.createDelegate(this,[this.getThis()])
					}
				},{
					xtype:'button',
					tooltip:'Descreption',
					iconCls:'mini-win-descreption-tbar',
					listeners :{
						click:function(t){
							new Ext.Window({
								title:'Mini Descreption',
								id:'Mini Descreption',
								width:500,
								height:400,
								manager:t.getWindowManager(),
								minimizable: true,
								maximizable: true,
								autoScroll: true,
								autoLoad: {url: 'page/game/mini/descreption.html'}
							}).show();
						}.createDelegate(this,[this.getThis()])
					}
				}],
				html:this._html.innerHTML
            });//createWindow end
        }
		_win.setWidth ((this._size+1.5)*this.widthArray[this.rank]);
		_win.setHeight((this._size+1.5)*this.heightArray[this.rank]+50-this.heightArray[this.rank]);				 //_win.setPagePosition(((Ext.get('x-desktop').getWidth()-_win.getWidth())/2),((Ext.get('x-desktop').getHeight()-_win.getHeight())/2));
        _win.show();
    },
	restart:function(){
			var win = this.getWindow();
			var module = this.getThis();
			var _text = win.getTopToolbar().items.itemAt(0).getText();
			if(_text == "Simple"){
				module.rank=0;
			}else if(_text == "Medium"){
				module.rank=1;
			}else if(_text == "Heavy"){
				module.rank=2;
			}
			win.setWidth ((module._size+1.5)*module.widthArray[module.rank]);
			win.setHeight((module._size+1.5)*module.heightArray[module.rank]+50-module.heightArray[module.rank]);
			module.initGame();
			win.body.update(module._html.innerHTML);	
	},
	/**
	  *return -2--->not click
	  *return -3--->has flat
	  *return -1--->has mini
	  *return 0 or other --->��ʾ��Ϣ
	  *
	**/
	getMini:function(x){
		if(this._mm[x]==0){
			return -2;
		}else if(this._mm[x]==2){
			return -3
		}else{
			return this.mm[x];	
		}
	},
	
	clickMini:function(x){
		this._mm[x] = 1;
		if(this.mm[x]==-1){
			this.showAll();
			DCC.widget.show({title:'Death',msg:"Oh...Has Mini....You death",buttons: Ext.Msg.YES});
		}else{
			if(this.mm[x]==0){
				Ext.getDom('mini-basic-button-'+x).setAttribute('value'," ");
				Ext.fly('mini-basic-button-'+x).addClass('mini-basic-button-clicked');
				this.clickAll(x);
			}else{
				Ext.getDom('mini-basic-button-'+x).setAttribute('value',this.mm[x]);
				Ext.fly('mini-basic-button-'+x).addClass('mini-basic-button-clicked');
			}
		}
		if(this.isWin())DCC.widget.show({title:'win',msg:"Yes...You are win",buttons: Ext.Msg.YES});
	},
	
	flat:function(x){
		if(this._mm[x] == 0){
			this._mm[x] = 2;
			Ext.getDom('mini-basic-button-'+x).setAttribute('value',"F");
		}
	},
	
	isWin:function(){
		for(var i=0;i<this.mm.length;i++){
			if(this.mm[i]!=-1){
				if(this._mm[i]==0)return false;
			}	
		}
		return true;
	},
	
	clickAll:function(x){
		if(x+1>=0&&x+1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this._mm[x+1]==0&&(x+1)%this.widthArray[this.rank]!=0){
			if(this.mm[x+1]==0){
				Ext.getDom('mini-basic-button-'+(x+1)).setAttribute('value',"");
				Ext.fly('mini-basic-button-'+(x+1)).addClass('mini-basic-button-clicked');
			}else{
				Ext.getDom('mini-basic-button-'+(x+1)).setAttribute('value',this.mm[x+1]);
				Ext.fly('mini-basic-button-'+(x+1)).addClass('mini-basic-button-clicked');
			}
			this._mm[x+1]=1;
			if(this.mm[x+1]==0)this.clickAll(x+1);
		}
		if(x-1>=0&&x-1<this.widthArray[this.rank]*this.heightArray[this.rank]&&this._mm[x-1]==0&&(x)%this.widthArray[this.rank]!=0){
			if(this.mm[x-1]==0){
				Ext.getDom('mini-basic-button-'+(x-1)).setAttribute('value',"");
				Ext.fly('mini-basic-button-'+(x-1)).addClass('mini-basic-button-clicked');
			}else{
				Ext.getDom('mini-basic-button-'+(x-1)).setAttribute('value',this.mm[x-1]);
				Ext.fly('mini-basic-button-'+(x-1)).addClass('mini-basic-button-clicked');
			}
			this._mm[x-1]=1;
			if(this.mm[x-1]==0)this.clickAll(x-1);
		}
		if(x-this.widthArray[this.rank]>=0&&x-this.widthArray[this.rank]<this.widthArray[this.rank]*this.heightArray[this.rank]&&this._mm[x-this.widthArray[this.rank]]==0){
			if(this.mm[x-this.widthArray[this.rank]]==0){
				Ext.getDom('mini-basic-button-'+(x-this.widthArray[this.rank])).setAttribute('value',"");
				Ext.fly('mini-basic-button-'+(x-this.widthArray[this.rank])).addClass('mini-basic-button-clicked');
			}else{
				Ext.getDom('mini-basic-button-'+(x-this.widthArray[this.rank])).setAttribute('value',this.mm[x-this.widthArray[this.rank]]);
				Ext.fly('mini-basic-button-'+(x-this.widthArray[this.rank])).addClass('mini-basic-button-clicked');
			}
			this._mm[x-this.widthArray[this.rank]]=1;
			if(this.mm[x-this.widthArray[this.rank]]==0)this.clickAll(x-this.widthArray[this.rank]);
		}
		if(x+this.widthArray[this.rank]>=0&&x+this.widthArray[this.rank]<this.widthArray[this.rank]*this.heightArray[this.rank]&&this._mm[x+this.widthArray[this.rank]]==0){
			if(this.mm[x+this.widthArray[this.rank]]==0){
				Ext.getDom('mini-basic-button-'+(x+this.widthArray[this.rank])).setAttribute('value',"");
				Ext.fly('mini-basic-button-'+(x+this.widthArray[this.rank])).addClass('mini-basic-button-clicked');
			}else{
				Ext.getDom('mini-basic-button-'+(x+this.widthArray[this.rank])).setAttribute('value',this.mm[x+this.widthArray[this.rank]]);
				Ext.fly('mini-basic-button-'+(x+this.widthArray[this.rank])).addClass('mini-basic-button-clicked');
			}
			this._mm[x+this.widthArray[this.rank]]=1;
			if(this.mm[x+this.widthArray[this.rank]]==0)this.clickAll(x+this.widthArray[this.rank]);
		}
	},//clickAll end
	
	showAll:function(){
		for(var i=0;i<this.mm.length;i++){
			if(this.mm[i]==0){
				Ext.getDom('mini-basic-button-'+i).setAttribute('value',"");
				Ext.fly('mini-basic-button-'+i).addClass('mini-basic-button-clicked');
			}else if(this.mm[i]==-1){
				Ext.getDom('mini-basic-button-'+i).setAttribute('value',"");
				Ext.fly('mini-basic-button-'+i).addClass('mini-basic-button-clicked-hasmini');
			}else{
				Ext.getDom('mini-basic-button-'+i).setAttribute('value',this.mm[i]);
				Ext.fly('mini-basic-button-'+i).addClass('mini-basic-button-clicked');
			}
		}
	},

    getSize:function(){
		return this._size;
	},

    registMethod : function(){
		return [
			['clickMini','click'],
			['getMini','get'],
			['flat','flat'],
			['showAll','showAll'],
			['restart','restart'],
			['getSize','getSize']
		];
	},
	registWinMethod:function(){
		return 'isWin';	
	}
});