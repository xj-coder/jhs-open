DCC.widget.ButtonCanChangeIcon = Ext.extend(Ext.menu.Item,{
	setIconClass : function(cls){
		if(this.el){
			this.btnEl.dom.className = '';
			this.btnEl.addClass(['x-btn-text', cls || '']);
		}
		//return this;
	}
});
Ext.reg('btnIcon', DCC.widget.ButtonCanChangeIcon);