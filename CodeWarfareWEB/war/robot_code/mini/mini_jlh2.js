var currentNum = 0;
var isChanged = 2;
var loopCount = 0;

function goFlat(x){
	/*标记为炸弹*/
	if(get(x) == -1){
		flat(x);
		currentNum++;
		isChanged = 2;
	}
}

function doClick(x){
	/*点击*/
	if(get(x) == -1){
		click(x);
		isChanged = 2;
	}
}

function deal(x){
	/*处理第x个位置*/

	var unclickNum = getUnclicks(x).length;
	var bombsNum = getBombs(x).length;
	
	if(get(x) == 0){
		if(unclickNum){
				for(var i = 0; i < getUnclicks(x).length; i++){
					doClick(getUnclicks(x)[0]);
				}		
			}
	}
	/*开始处理获得的数字为1~8时的情况*/
	for(var m = 1; m <= 8; m++){
		if(get(x) == m){
			if(bombsNum == m){
				/*若已经标记的炸弹数就为当前数字，则将所有未点击的全部点击*/
				if(unclickNum){
					for(var i = 0; i < getUnclicks(x).length; i++){
						doClick(getUnclicks(x)[0]);
					}	
				}
			}else if(bombsNum + unclickNum == m){
				/*若已标记炸弹数加上未点击数刚好为当前数字，则将所有未点击的标为炸弹*/
				for(var i = 0; i < getUnclicks(x).length; i++){
					goFlat(getUnclicks(x)[0]);
				}
			}
		}
	}
}

function getBombs(x){
	/*获得已经标记炸弹的集合*/
	var arr = getSurround(x);
	var bombs = [];
	var i = 0;
	
	for(var j = 0; j < arr.length; j++){
		if(get(arr[j])== -2){
			bombs[i] = arr[j];
			i++;
		}
	}
	return bombs;
}

function getSurround(x){
	/*获得周围8个位置的集合*/
	var arr = [];
	var i = 0;
	var width = size();
	if(x > width - 1 ){
		arr[i] = x - width;
		i++;
	}
	if(x < width *(width-1) - 1){
		arr[i] = x + width;
		i++;
	}
	if(x % width != 0){
		arr[i] = x - 1;
		i++;
	}
	if(x % width != width-1){
		arr[i] = x + 1;
		i++;
	}
	if(x > width - 1 && x % width != 0){
		arr[i] = x - width-1;
		i++;
	}
	if(x > width-1 && x % width != width-1){
		arr[i] = x - width+1;
		i++;
	}
	if(x < width *(width-1) - 1 && x % width != 0){
		arr[i] = x + width-1;
		i++;
	}
	if(x < width *(width-1) - 1 && x % width != width-1){
		arr[i] = x + width+1;
		i++;
	}
	return arr;
}

function getUnclicks(x){
	/*获得周围未点击且未标记的集合*/
	var arr = getSurround(x);
	var doubts = [];
	var i = 0;
	
	for(var j = 0; j < arr.length; j++){
		if(get(arr[j])== -1 && get(arr[j]) != -2){
			doubts[i] = arr[j];
			i++;
		}
	}
	return doubts;
}

function action(args){
	/*入口函数*/
	var random = parseInt(Math.random() *(size() * size() -1));
	var num = miniCount();

	click(random);
	/*最大循环10次，以防止点到炸弹是出现死循环*/
	while(currentNum < num && loopCount < 10){
	
		do{
			for(var x = 0; x < size() * size() -1; x++){
				deal(x);
			}
			isChanged --;
		}while(isChanged > 0);
		/*手动点击开始*/
		var allUnclicks = [];
		var count = 0;
		var findUnclick = false;
		/*从第一个未点击的位置开始，向后面找连续未点击的块，然后点击块中间的位置*/
		for(var x = 0; x < size() * size() -1; x++){
			if(get(x) == -1){
				allUnclicks[count] = x;
				count++;
				findUnclick = true;
			}else if(findUnclick){
				var y = parseInt(count / 2);
				/*alert("要点击第" + parseInt(y / size()) + 1 + "行，第" 
				+ (y % size()) + 1 + "列" );*/
				doClick(allUnclicks[y]);
				break;
			}
		}
		
		/*手动点击结束*/
		
		loopCount++;
	}	
	/*多循环一次，以解决标记完最后一个地雷后直接退出循环导致剩下一两个未点。*/
	for(var x = 0; x < size() * size() -1; x++){
		deal(x);
	}
	/*alert("总雷数：" + num + "个，已找到：" + currentNum + "个。");*/
}