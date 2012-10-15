// JavaScript Document
DCC.util.CSSLoader=function(){
	this.init=function(opt){};
	this.CSS=new Array();
	
	//µº»ÎCSSƒ⁄»›
	this.loadCSS=function (name,url) {
		if(!url)return;
		var _css=document.createElement("link");
		_css.type="text/css";
		_css.rel="stylesheet";
		_css.href = url;
		var st=new Object();
		st.name=name;
		st.loaded=true;
		this.CSS.push(st);
		document.getElementsByTagName("head")[0].appendChild(_css);
    };
}