// JavaScript Document
DCC.util.CodeLoader=function(){
	this.init=function(opt){};
	
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

	//ͬ������
	this.loadCode=function(url){
		if(!url)return;
		
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
		
		return oXmlHttp.responseText;
	};
}// JavaScript Document