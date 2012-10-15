// JavaScript Document
/*!
 * Ext JS Library 3.2.0
 * Copyright(c)
 */

//global variable
// main desktop configuration
var MyDesktop = new Ext.app.App({
	init :function(){
		Ext.QuickTips.init();
	},

    // config for the start menu
    getStartConfig : function(){
        return {
            title: 'Guest',
            //iconCls: 'Guest',
            toolItems: [{
                text:'Login',
                iconCls:'logout',
                scope:this
            }]
        };
    }
});
