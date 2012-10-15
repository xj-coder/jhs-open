DCC.game.Snakes=Ext.extend(Ext.app.GameModule,{
    id:'snakes-win-game',
	
	HTML: null,
	tbl:null,
	/**
	* body: 蛇身，数组放蛇的每一节，
	* 数据结构{x:x0, y:y0, color:color0},
	* x,y表示坐标,color表示颜色
	**/
	body: [],
	
	/**
	*map:当前地图,
	*0-->空地,1-->蛇身,2-->食物,3-->障碍
	*/
	map:[],
	//障碍
	wall:[
		  [[0,0]],
		  [[0,0],[0,1],[0,2],[0,3]],
		  [[0,0],[1,1],[1,2],[1,3]],
		  [[0,0],[1,0],[2,0],[3,0]],
		  [[0,0],[0,1],[1,1],[1,2],[1,3],[0,3]]
	],
	colors : ['red','orange','yellow','green','blue','purple'],
	wallNum:50,
	footNum:150,
	//当前移动的方向,取值0,1,2,3, 分别表示向上,右,下,左, 按键盘方向键可以改变它
	direction: 0,
	//行数
	rowCount: 45,
	//列数
	colCount: 45,
	//初始化
	
	winNum : 100,
	_isStop : false,
	_isWin : false,
	
	init:function(){
		this.initUI();
	},
	initGame:function(){
		this._isStop = false;
		this._isWin = false;
		//构造table
		for(var row=0;row<this.rowCount;row++){
			var tr=this.tbl.insertRow(-1);
			for(var col=0;col<this.colCount;col++) {
				var td=tr.insertCell(-1);
				td.className = 'snakes-win-td';
			}
		}
		//初始化蛇身
		this.body= [];
		//初始化map
		for(var row=0;row<this.rowCount;row++){
			for(var col=0;col<this.colCount;col++) {
				this.map[row*this.rowCount+col] = 0;
			}
		}
		//产生初始移动方向
		this.direction = Math.floor(Math.random()*4);
		
		//产生障碍
		for(var i=0; i<this.wallNum; i++){
			x = Math.floor(Math.random()*this.colCount);
			y = Math.floor(Math.random()*this.rowCount);
			indexWall = Math.floor(Math.random()*this.wall.length);
			for(var j=0;j<this.wall[indexWall].length;j++){
				if(x+this.wall[indexWall][j][0]>=this.colCount || y+this.wall[indexWall][j][1]>=this.rowCount){
					indexWall = Math.floor(Math.random()*this.wall.length);
					j=-1;
					continue;
				}
			}
			for(var j=0;j<this.wall[indexWall].length;j++){
				_x = x + this.wall[indexWall][j][0];
				_y = y + this.wall[indexWall][j][1];
				if(!this.isCellFilled(_x,_y)){
					this.tbl.rows[_y].cells[_x].style.backgroundColor = '#aaaaaa';
					this.map[_y*this.colCount+_x] = 3;
				}
			}
		}
		
		//产生食物点
		for(var i=0; i<this.footNum; i++){
			x = Math.floor(Math.random()*this.colCount);
			y = Math.floor(Math.random()*this.rowCount);
			colorIndex = Math.floor(Math.random()*this.colors.length);
			if(!this.isCellFilled(x,y)){
				this.tbl.rows[y].cells[x].style.backgroundColor = this.colors[colorIndex];
				this.map[y*this.colCount+x] = 2;
			}
		}
		//产生蛇头
		while(true){
			x = Math.floor(Math.random()*this.colCount);
			y = Math.floor(Math.random()*this.rowCount);
			if(!this.isCellFilled(x,y)){
				this.tbl.rows[y].cells[x].style.backgroundColor = "black";
				this.body.push({x:x,y:y,color:'black'});
				this.map[y*this.colCount+x] = 1;
				break;
			}
		}
	},
	initUI: function(){
		this.HTML = document.createElement('div');
		this.tbl = document.createElement('table');
		this.tbl.id = 'snakes-win-table';
		this.tbl.className = 'snakes-win-table';
		this.tbl.border = 1;
		this.tbl.cellspacing = 0;
		this.tbl.cellpadding = 0;

		/*
		//构造table
		for(var row=0;row<this.rowCount;row++){
			var tr=this.tbl.insertRow(-1);
			for(var col=0;col<this.colCount;col++) {
				var td=tr.insertCell(-1);
				td.className = 'snakes-win-td';
			}
		}*/
		this.HTML.appendChild(this.tbl);
	},
	createWindow:function(){
		var _win = this.getWindow();
		if(!_win){
			 _win = this.getDesktop().createWindow({
				id: 'snakes-win-game',
				title: 'Snakes Window',
				width:530,
				height:530,
				iconCls: 'icon-snakes-win',
				shim:false,
				animCollapse:true,
				constrainHeader:true,
				maximizable:false,
				resizable:false,
				
				layout:'fit',
				border:false,
				tbar:[{
					xtype:'button',
					tooltip:'Restart',
					iconCls:'snakes-win-restart-tbar',
					listeners :{
						click:this.restart.createDelegate(this)
					}
				},{
					xtype:'button',
					tooltip:'Descreption',
					iconCls:'snakes-win-descreption-tbar',
					listeners :{
						click:function(t){
							new Ext.Window({
								title:'Snakes Descreption',
								id:'Snakes Descreption',
								width:500,
								height:400,
								manager:t.getWindowManager(),
								minimizable: true,
								maximizable: true,
								autoScroll: true,
								autoLoad: {url: 'page/game/snakes/descreption.html'}
							}).show();
						}.createDelegate(this,[this.getThis()])
					}
				}],
				html:this.HTML.innerHTML
			})
		}
		_win.show();
		this.tbl = Ext.getDom('snakes-win-table');
		this.initGame();
	},//createWindow end
	//移动
	move: function(s){
		var _s = 0;
		while(_s<s && !this._isStop && !this._isWin){
			this.erase();
			this.moveOneStep();
			this.paint();
			_s++;
		}
	},
	//移动一节身体
	moveOneStep: function(){
		if(this.checkNextStep()==-1){
			this._isStop = true;
			DCC.widget.show({title:'END',msg:'Game over!\nPress Restart to continue.',buttons: Ext.Msg.OK});
			return;
		}
		if(this.checkNextStep()==1){
			var _point = this.getNextPos();
			var _x = _point.x;
			var _y = _point.y;
			var _color = this.getColor(_x,_y);
			this.body.unshift({x:_x,y:_y,color:_color});
			if(this.body.length == this.winNum){
				this._isWin = true;
				return;
			}
			//因为吃了一个食物，所以再产生一个食物
			this.map[y*this.colCount+x] = 1;
			this.generateDood();
			return;
		}
		//window.status = this.toString();
		var point = this.getNextPos();
		//保留第一节的颜色
		var color = this.body[0].color;
		//颜色向前移动
		for(var i=0; i<this.body.length-1; i++){
			this.body[i].color = this.body[i+1].color;
		}
		//蛇尾减一节， 蛇尾加一节，呈现蛇前进的效果
		var end = this.body.pop();
		this.map[end.y*this.colCount+end.x] = 0;
		this.body.unshift({x:point.x,y:point.y,color:color});
		this.map[point.y*this.colCount+point.x] = 1;
		//window.status = this.toString();
	},
	
	//探寻下一步将走到什么地方
	getNextPos: function(){
		var x = this.body[0].x;
		var y = this.body[0].y;
		var color = this.body[0].color;
		//向上
		if(this.direction==0){
			y--;
		}
		//向右
		else if(this.direction==1){
			x++;
		}
		//向下
		else if(this.direction==2){
			y++;
		}
		//向左
		else{
			x--;
		}
		//返回一个坐标
		return {x:x,y:y};
	},
	//检查将要移动到的下一步是什么
	checkNextStep: function(){
		var point = this.getNextPos();
		var x = point.x;
		var y = point.y;
		if(x<0||x>=this.colCount||y<0||y>=this.rowCount){
			return -1;//触边界，游戏结束
		}
		if(this.map[y*this.colCount+x] == 3){
			return -1;//碰到障碍,游戏结束
		}
		for(var i=0; i<this.body.length; i++){
			if(this.body[i].x==x&&this.body[i].y==y){
				return -1;//碰到自己的身体,游戏结束
			}
		}
		if(this.isCellFilled(x,y)){
			return 1;//有东西
		}
		return 0;//空地
	},
	//擦除蛇身
	erase: function(){
		for(var i=0; i<this.body.length; i++){
			this.eraseDot(this.body[i].x, this.body[i].y);
		}
	},
	//绘制蛇身
	paint: function(){
		for(var i=0; i<this.body.length; i++){
			this.paintDot(this.body[i].x, this.body[i].y,this.body[i].color);
		}
	},
	//擦除一点
	eraseDot: function(x,y){
		this.tbl.rows[y].cells[x].style.backgroundColor = "";
	},
	//绘制一点
	paintDot: function(x,y,color){
		this.tbl.rows[y].cells[x].style.backgroundColor = color;
	},
	//得到一个坐标上的颜色
	getColor: function(x,y){
		return this.tbl.rows[y].cells[x].style.backgroundColor;
	},
	//用于调试
	toString: function(){
		var str = "";
		for(var i=0; i<this.body.length; i++){
			str += "x:" + this.body[i].x + " y:" + this.body[i].y + " color:" + this.body[i].color + " - ";
		}
		return str;
	},
	//检查一个坐标点有没有被填充
	isCellFilled: function(x,y){
		if(this.tbl.rows[y].cells[x].style.backgroundColor == ""){
			return false;
		}
		return true;
	},
	//重新开始
	restart: function(){
		for(var i=0; i<this.rowCount;i++){
				this.tbl.deleteRow(0);
		}
		this.initGame();
	},
	//产生食物。
	generateDood: function(){
		var x = Math.floor(Math.random()*this.colCount);
		var y = Math.floor(Math.random()*this.rowCount);
		var colorIndex = Math.floor(Math.random()*7);
		if(!this.isCellFilled(x,y)){
			this.tbl.rows[y].cells[x].style.backgroundColor = this.colors[colorIndex];
			this.map[y*this.colCount+x] = 2;
		}
	},
	//当前移动的方向,取值0,1,2,3, 分别表示向上,右,下,左, 按键盘方向键可以改变它
	//direction: 0,
	M_left:function(s){
		this.direction = 3;
		this.move(s);
	},
	M_right:function(s){
		this.direction = 1;
		this.move(s);
	},
	M_up:function(s){
		this.direction = 0;
		this.move(s);
	},
	M_down:function(s){
		this.direction = 2;
		this.move(s);
	},
	M_getMap:function(){
		return this.map;	
	},
	M_getRowCount:function(){
		return this.rowCount;
	},
	M_getColCount:function(){
		return this.colCount;
	},
	M_getSnakes:function(){
		return this.body;	
	},
	M_getDir:function(){
		return direction;
	},
	canContinue:function(){
		return true;	
	},
	isStop:function(){
		return this._isStop;	
	},
	isWin:function(){
		return this._isWin;
	},
	registMethod : function(){
		return [
			['M_left','left'],
			['M_right','right'],
			['M_up','up'],
			['M_down','down'],
			['M_getMap','getMap'],
			['M_getRowCount','getRowCount'],
			['M_getColCount','getColCount'],
			['M_getSnakes','getSnakes'],
			['M_getDir','getDir']
		];
	},
	registDefaultMethod:function(){
		return 'up(1)';
	},
	//registBeginMethod:function(){
	//	return 'restart';	
	//},
	registWinMethod:function(){
		return 'isWin';	
	},
	registLoopMethod:function(){
		return 'canContinue';
	},
	registStopMethod:function(){
		return 'isStop';	
	},
	getLoopTime:function(){
		return 1000;	
	}
})