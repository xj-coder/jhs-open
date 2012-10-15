var flag=0;
function addPanel()				  //画一个15行，15列的棋盘
{
	for(i=1;i<16;i++)
		insertRow(i);
	for(i=1;i<16;i++)
		insertCell(i);
}
function insertRow(rowCount)      //向table插入一行
{
	var myTable=document.getElementById('table1');
	var row=myTable.insertRow();  
	row.setAttribute('id',rowCount);
}
function insertCell(cellCount)    //向每一行插入一列到最后
{
	var rows = document.getElementsByTagName('TR');
	for(var no=0;no<rows.length;no++){
		var cell = rows[no].insertCell(-1);
		cell.setAttribute("id",rows[no].getAttribute('id')*100+cellCount);
		setCellPanelInnerHTML(cell);
		cell.setAttribute("name","noChess");
		setCellOnClick(cell);
	}	
}
function setCellPanelInnerHTML(cell)    //为cell设置背景
{
	var temp=cell.getAttribute("id");
	cell.innerHTML="<img src='img/m.jpg'>";				//从普遍到特殊
	if(temp%100==1) cell.innerHTML="<img src='img/l.jpg'>";
	if(temp%100==15) cell.innerHTML="<img src='img/r.jpg'>";
	if(temp/100<2) cell.innerHTML="<img src='img/t.jpg'>";
	if(temp/100>15) cell.innerHTML="<img src='img/b.jpg'>";
	if(temp%100==1 && temp/100<2) cell.innerHTML="<img src='img/lt.jpg'>";
	if(temp%100==15 && temp/100<2) cell.innerHTML="<img src='img/rt.jpg'>";
	if(temp%100==1 && temp/100>15) cell.innerHTML="<img src='img/lb.jpg'>";
	if(temp%100==15 && temp/100>15) cell.innerHTML="<img src='img/rb.jpg'>";	
}
function setCellOnClick(cell)  //为 cell 加 onclick() 方法调用
{
	cell.onclick=function(){
		if(cell.getAttribute("name")=="noChess"){
			if(flag%2==0){
				cell.innerHTML="<img src='img/black.jpg'>";
				cell.setAttribute("name","black");
				}
			else{
				cell.innerHTML="<img src='img/white.jpg'>";
				cell.setAttribute("name","white");
				}
			flag++;
			isAnyoneWin(cell,flag);
		}
	};
}
function isAnyoneWin(cell,flag)  //判断输赢的方法
{
	temp=checkChessMap("(x+i)+(y+i)*100","",cell);
	temp=checkChessMap("(x+i)+y*100",temp,cell);
	temp=checkChessMap("(x+i)+(y-i)*100",temp,cell);
	temp=checkChessMap("x+(y+i)*100",temp,cell);
	if(temp.indexOf("11111")!=-1){
		alert("经过"+flag+"手,"+(cell.getAttribute("name")=="white"?"白":"黑")+"棋胜!");
		reStartGame();
	}
}
function checkChessMap(evalStr,temp,cell)
{	
	var val=cell.getAttribute("id");						 //得到一个整数
	x=val%100;												 //重新组合时，X要放在Y的后面
	y=val/100-(val/100)%1;
	for(i=-4;i<5;i++){
		var pointValue=eval(evalStr);						 //这个方法很怪，从外面传字符串，在这里计算值
		if(pointValue%100<1 || pointValue%100>15) continue;  //如果不在棋盘中间，继续查询下一个
		if(pointValue/100-(pointValue/100)%1<1 || pointValue/100-(pointValue/100)%1>15) continue;
		var xxx=document.getElementById( pointValue );		 //从上面传过来的条件查询
		if(xxx.getAttribute("name")==cell.getAttribute("name"))	temp+="1";
		else temp+="0";
	}
	return temp+=",";
}
function reStartGame()
{
	flag=0;   //下的手数重置
	var cells=document.getElementsByTagName("TD");
	for(var no=0;no<cells.length;no++){
		setCellPanelInnerHTML(cells[no]);    
		cells[no].setAttribute("name","noChess");  //cell的状态重置
	}	
}
document.onreadystatechange=startGame;  //当状态改变时
function startGame()
{
	if(document.readyState=="complete")
	try{
		addPanel();
	}
	catch(e){alert("脚本出错，请确认！");}
}