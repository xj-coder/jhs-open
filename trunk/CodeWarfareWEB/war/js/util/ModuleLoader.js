// JavaScript Document
DCC.util.ModuleLoader = function () {
    this.init = function (opt) {
    };

    var modules = new Array();
    var jsLoader = new DCC.util.JSLoader();
    var cssLoader = new DCC.util.CSSLoader();
    var json = new Ext.data.Store({
        reader:new Ext.data.ArrayReader({}, [
            {name:'name'},
            {name:'js_path'},
            {name:'obj'},
            {name:'css_path'}
        ]),
        //url:'/data/modules.json'
        data:DCC.data.Modules
        //autoLoad:true
    });
    /*var json = new Ext.data.JsonStore({
     url:'/data/modules.json',
     root:'modules',
     fields:['name','path','class']
     });*/

    this.loadModule = function (name) {
        for (var i = 0; i < modules.length; i++) {
            if (modules[i].loaded && modules[i].name == name) {
                var fun = (new Function('return new ' + modules[i].obj + ';'))();
                return fun;
            }
        }
        for (var i = 0; i < json.getCount(); i++) {
            if (json.getAt(i).get('name') == name) {
                var obj = new Object();
                obj.name = json.getAt(i).get('name');
                obj.js_path = json.getAt(i).get('js_path');
                obj.obj = json.getAt(i).get('obj');
                obj.css_path = json.getAt(i).get('css_path');
                obj.loaded = true;
                modules.push(obj);
                cssLoader.loadCSS(obj.name, obj.css_path);
                jsLoader.loadJS(obj.name, obj.js_path);
                var fun = (new Function('return new ' + obj.obj + ';'))();
                return fun;
            }
        }
    };

    this.uploadModule = function (name) {
        for (var i = 0; i < this.modules.length; i++) {
            if (modules[i].name == name) {
                //modules[i].loaded = false;
                //jsLoader.uploadJS(modules[i].js_path);
                //cssLodaer.uploadCSS(obj.name,obj.css_path);
                return true;
            }
        }
        return false;
    };
}
