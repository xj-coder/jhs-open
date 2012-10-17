DCC.util.ModuleLoader = function () {
    var modules = new Array();
    var jsLoader = new DCC.util.JSLoader();
    var cssLoader = new DCC.util.CSSLoader();

    for (var i = 0; i < moduleJson.getCount(); i++) {
        var moduleJsonItem = moduleJson.getAt(i);
        var module = new Object();
        module.id = moduleJsonItem.get('id');
        module.type = moduleJsonItem.get('type');
        module.js_path = moduleJsonItem.get('js_path');
        module.page_path = moduleJsonItem.get('page_path');
        module.css_path = moduleJsonItem.get('css_path');
        module.obj = moduleJsonItem.get('obj');
        module.lazy_load = moduleJsonItem.get('lazy_load');
        module.loaded = false;
        modules.push(module);

        if (!module.lazy_load) {
            this.getModule(module.id);
        }
    }

    this.getModule = function (id) {
        for (var i = 0; i < modules.length; i++) {
            var module = modules[i];
            if (module.id == id) {
                if (module.loaded) {
                    var fun = (new Function('return new ' + module.obj + ';'))();
                    return fun;
                } else {
                    cssLoader.loadCSS(module.id, module.css_path);
                    jsLoader.loadJS(module.id, module.js_path);
                    var fun = (new Function('return new ' + module.obj + ';'))();
                    module.loaded = true;
                    return fun;
                }
            }
        }
    };
}
