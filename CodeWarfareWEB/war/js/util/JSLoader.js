// JavaScript Document
DCC.util.JSLoader=function(){
	this.init=function(opt){};
	this.JS=new Array();
	//��ȡXMLHttpRequest����(�ṩ�ͻ���ͬhttp������ͨѶ��Э��)
	this.getXmlHttpRequest=function (){
		if ( window.XMLHttpRequest ) // ����IE������������
			   return new XMLHttpRequest() ;
		else if ( window.ActiveXObject ) // IE
			try{
                return new window.ActiveXObject("Msxml2.XMLHTTP");//IE���ϰ汾
            }catch(e){}
            try{
                return new window.ActiveXObject("Microsoft.XMLHTTP");//IE���°汾
            }catch(e){}
	};
	//��������
	this.includeJsText=function (name,jsText) {
		var _script=document.createElement("script");
		_script.type="text/javascript";
		_script.text = jsText;
		var st=new Object();
		st.name=name;
		st.loaded=true;
		this.JS.push(st);
		document.getElementsByTagName("head")[0].appendChild(_script);
    };

	//ͬ������
	this.loadJS=function(name, url){
		if(!url)return;
		for(var i=0;i<this.JS.length;i++)
			if(this.JS[i].name==name)return;

		var oXmlHttp = this.getXmlHttpRequest() ;

		oXmlHttp.onreadystatechange = function(){
		//��ʵ���ڵڶ��ε��õ���jsʱ,��Ϊ����������д������*.js�ļ���,���Ͳ��ڷ��ʷ�����,
		//Ҳ�Ͳ���ִ�����������,�������Ҳֻ�����ó��첽ʱ���õ�
			if ( oXmlHttp.readyState == 4 ){  //��ִ������Ժ�(��������Ӧ)��Ҫִ�е�
				if ( oXmlHttp.status == 200 || oXmlHttp.status == 304 ){//200�ж�ȡ��Ӧ��url�ļ�,404��ʾ����������ļ�
				}else{
					alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
				}
			}
		}
		//1.True ��ʾ�ű����� send() ����֮�����ִ�У������ȴ����Է���������Ӧ,
		//������open()���������е��õ�onreadystatechange()���������ͨ���Ѹò�������Ϊ "false"��
		//����ʡȥ����� onreadystatechange ����,����ʾ������������Ӧ���ִ��send()����ķ���.
		//2.ͬ��ִ��oXmlHttp.send()������oXmlHttp.responseText�з��ض�Ӧ������,���첽����Ϊ��,
		//ֻ����oXmlHttp.readyState == 4ʱ��������,����ͬ������oXmlHttp.send()��Ĳ������൱��
		//oXmlHttp.readyState == 4�µĲ���,���൱��ֻ������һ��״̬.
		oXmlHttp.open('GET', url, false);
		oXmlHttp.send(null);
		this.includeJsText(name,oXmlHttp.responseText);
	};

    this.includeUserJsText = function(source)
    {
        //document.getElementById("UserJSCode").text = source;
        //document.removeElement(document.getElementById("UserJSCode"));
        document.getElementsByTagName("head")[0].removeChild(document.getElementById("UserJSCode"));
        var _script=document.createElement("script");
        _script.type="text/javascript";
        _script.text = source;
        _script.id = "UserJSCode";
        document.getElementsByTagName("head")[0].appendChild(_script);
    };
}
