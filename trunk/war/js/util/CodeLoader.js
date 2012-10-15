// JavaScript Document
DCC.util.CodeLoader=function(){
	this.init=function(opt){};
	
	//获取XMLHttpRequest对象(提供客户端同http服务器通讯的协议)
	this.getXmlHttpRequest=function (){ 
		if ( window.XMLHttpRequest ) // 除了IE外的其它浏览器
			   return new XMLHttpRequest() ; 
		else if ( window.ActiveXObject ) // IE 
			try{
                return new window.ActiveXObject("Msxml2.XMLHTTP");//IE的老版本
            }catch(e){}
            try{
                return new window.ActiveXObject("Microsoft.XMLHTTP");//IE的新版本
            }catch(e){}           

	
	};

	//同步加载
	this.loadCode=function(url){
		if(!url)return;
		
		var oXmlHttp = this.getXmlHttpRequest() ; 
			
		oXmlHttp.onreadystatechange = function(){
		//其实当在第二次调用导入js时,因为在浏览器当中存在这个*.js文件了,它就不在访问服务器,
		//也就不在执行这个方法了,这个方法也只有设置成异步时才用到	 
			if ( oXmlHttp.readyState == 4 ){  //当执行完成以后(返回了响应)所要执行的
				if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ){//200有读取对应的url文件,404表示不存在这个文件				
				}else{ 
					alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ; 
				}
			}
		}	 
		//1.True 表示脚本会在 send() 方法之后继续执行，而不等待来自服务器的响应,
		//并且在open()方法当中有调用到onreadystatechange()这个方法。通过把该参数设置为 "false"，
		//可以省去额外的 onreadystatechange 代码,它表示服务器返回响应后才执行send()后面的方法.
		//2.同步执行oXmlHttp.send()方法后oXmlHttp.responseText有返回对应的内容,而异步还是为空,
		//只有在oXmlHttp.readyState == 4时才有内容,反正同步的在oXmlHttp.send()后的操作就相当于
		//oXmlHttp.readyState == 4下的操作,它相当于只有了这一种状态.
		oXmlHttp.open('GET', url, false);  
		oXmlHttp.send(null); 
		
		return oXmlHttp.responseText;
	};
}// JavaScript Document