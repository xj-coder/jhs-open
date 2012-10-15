/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 */
Ext.app.Module = function(config){
    Ext.apply(this, config);
    Ext.app.Module.superclass.constructor.call(this);
    this.init();
	this.id
}

Ext.extend(Ext.app.Module, Ext.util.Observable, {
    init : Ext.emptyFn,
	tile : Ext.emptyFn
});
