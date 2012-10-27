// JavaScript Document
/*!
 * Ext JS Library 3.2.0
 * Copyright(c)
 */

//global variable
var DEFAULT_JS_EDITOR_ID = "js-editor";
var DEFAULT_JS_EDITOR_WIN_ID = "js-editor-win";
var DEFAULT_ROBOT_JS_EDITOR_ID = 'robot-editor';
var DEFAULT_ROBOT_JS_EDITOR_WIN_ID = 'robot-editor-win';
var DEFAULT_ROBOT_ID = 'david-robot';
var DEFAULT_ROBOT_WIN_ID = 'david-robot-win';

var ModuleLoader = null;
var CodeLoader = null;
var MainApp = null;
//json data
{
    function loaded() {
        return robotCodeJson.getCount()>0 && gameLauncherJson.getCount()>0 && moduleJson.getCount()>0 && shortcutJson.getCount()>0;
    }

    function load(json) {
        json.load({
            callback:function () {
                if (loaded()) {
                    beginApp();
                }
            }
        });
    }

    var robotCodeJson = new Ext.data.JsonStore({
        url:'/data/robot_code/david/code.json',
        // reader configs
        root:'codes',
        fields:['game_id', 'game_name', 'code_file_path', 'code_file_name', 'code_name']
    });

    var shortcutJson = new Ext.data.JsonStore({
        url:'/data/main/shortcut.json',
        // reader configs
        root:'shortcuts',
        fields:['id', 'name', 'img']
    });

    var gameLauncherJson = new Ext.data.JsonStore({
        url:'/data/main/launcher.json',
        // reader configs
        root:'games',
        fields:['id', 'text', 'iconCls']
    });
    var moduleJson = new Ext.data.JsonStore({
        url:'/data/main/model.json',
        // reader configs
        root:'models',
        fields:['id', 'type', 'js_path', 'page_path', 'css_path', 'cls', 'lazy_load']
    });

    load(robotCodeJson);
    load(shortcutJson);
    load(gameLauncherJson);
    load(moduleJson);
}
//json data end

function beginApp() {
    ModuleLoader = new DCC.util.ModuleLoader();
    CodeLoader = new DCC.util.CodeLoader();
    JSLoader = new DCC.util.JSLoader();
    CSSLoader = new DCC.util.CSSLoader();

    MainApp = new Ext.app.App({
        init:function () {
            Ext.QuickTips.init();
        },

        // config for the start menu
        getStartConfig:function () {
            return {
                title:'Guest',
                //iconCls: 'Guest',
                toolItems:[
                    {
                        text:'Login',
                        iconCls:'logout',
                        scope:this
                    }
                ]
            };
        }
    });
}

