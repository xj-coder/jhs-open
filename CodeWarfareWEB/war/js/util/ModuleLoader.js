DCC.util.ModuleLoader = function () {
    var modules = new Array();

    for (var i = 0; i < moduleJson.getCount(); i++) {
        var moduleJsonItem = moduleJson.getAt(i);
        var module = new Object();
        module.id = moduleJsonItem.get('id');
        module.type = moduleJsonItem.get('type');
        module.js_path = moduleJsonItem.get('js_path');
        module.page_path = moduleJsonItem.get('page_path');
        module.css_path = moduleJsonItem.get('css_path');
        module.cls = moduleJsonItem.get('cls');
        module.lazy_load = moduleJsonItem.get('lazy_load');
        module.loaded = false;
        modules.push(module);

        if (!module.lazy_load) {
            this.getModuleInstance(module.id);
        }
    }

    this.getModuleInstance = function (id) {
       return this.getModule(id).instance;
    };

    this.getModule  = function (id) {
        for (var i = 0; i < modules.length; i++) {
            var module = modules[i];
            if (module.id == id) {
                if (module.loaded) {
                    return module;
                } else {
                    CSSLoader.loadCSS(module.id, module.css_path);
                    JSLoader.loadJS(module.id, module.js_path);
                    module.instance = (new Function('return new ' + module.cls + ';'))();
                    module.loaded = true;
                    return module;
                }
            }
        }
    };
}
