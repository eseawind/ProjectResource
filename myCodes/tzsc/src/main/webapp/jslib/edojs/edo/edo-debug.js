
Edo = {
    version: '1.0',
    website: 'http://www.edojs.com',
    email: 'services@edojs.com'
};

window.undefined = window.undefined;

Function.prototype.extend = function (sp, overrides) {
    if (typeof sp != 'function') return this;

    var sb = this, sbp = sb.prototype, spp = sp.prototype;
    if (sb.superclass == spp) return;
    sb.superclass = spp;
    sb.superclass.constructor = sp;

    for (var p in spp) {
        sbp[p] = spp[p];
    }
    if (overrides) {
        for (var p in overrides) {
            sbp[p] = overrides[p];
        }
    }
    return sb;
}

Edo.apply = function (o, c, defaults) {
    if (defaults) {
        Edo.apply(o, defaults);
    }
    if (o && c && typeof c == 'object') {
        for (var p in c) {
            o[p] = c[p];
        }
    }
    return o;
};
Edo.applyIf = function (o, c) {
    if (o) {
        for (var p in c) {
            if (o[p] === undefined || o[p] === null) {
                o[p] = c[p];
            }
        }
    }
    return o;
}
function escapeRe(s) {
    return s.replace(/([.*+?^${}()|[\]\/\\])/g, "\\$1");
}
function HTMLEncode(html) {
    var temp = document.createElement("div");
    (temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
    var output = temp.innerHTML;
    temp = null;
    return output;
}

function HTMLDecode(text) {
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
}

var toString = Object.prototype.toString,
    ua = navigator.userAgent.toLowerCase(),
    check = function (r) {
        return r.test(ua);
    },
    DOC = document,
    isStrict = DOC.compatMode == "CSS1Compat",

    isOpera = toString.call(window.opera) == '[object Opera]',
    isChrome = check(/chrome/),
    isWebKit = check(/webkit/),

    isSafari = !isChrome && check(/safari/),
    isSafari2 = isSafari && check(/applewebkit\/4/), // unique to Safari 2
    isSafari3 = isSafari && check(/version\/3/),
    isSafari4 = isSafari && check(/version\/4/),
    isIE = !!window.attachEvent && !isOpera,
    isIE7 = isIE && check(/msie 7/),
    isIE8 = isIE && check(/msie 8/),
    isIE6 = isIE && !isIE7 && !isIE8,
    isGecko = !isWebKit && check(/gecko/),
    isGecko2 = isGecko && check(/rv:1\.8/),
    isGecko3 = isGecko && check(/rv:1\.9/),
    isFireFox = ua.indexOf("firefox") != -1,
    isBorderBox = isIE && !isStrict,
    isWindows = check(/windows|win32/),
    isMac = check(/macintosh|mac os x/),
    isAir = check(/adobeair/),
    isLinux = check(/linux/),
    isSecure = /^https/i.test(window.location.protocol);


// remove css image flicker
if (isIE6) {
    try {
        DOC.execCommand("BackgroundImageCache", false, true);
    } catch (e) { }
}

Edo.apply(Edo, {
    view: document.defaultView,
    types: {},

    htmlRe: /<html[^>]*>((\n|\r|.)*?)<\/html>/ig,
    scriptRe: /(?:<script([^>]*)?>)((\n|\r|.)*?)(?:<\/script>)/ig,
    styleRe: /(?:<style([^>]*)?>)((\n|\r|.)*?)(?:<\/style>)/ig,
    emptyRe: /(?:<--)((\n|\r|.)*?)(?:-->)/ig,

    ID: 1,
    id: function (el, prefix) {
        return (el = Edo.getDom(el) || {}).id = el.id || (prefix || "edo-cmp") + (++Edo.ID);
    },
    namespace: function () {
        var o, d;
        [ ].each.call(arguments, function (v) {
            d = v.split(".");
            o = window[d[0]] = window[d[0]] || {};
            [ ].each.call(d.slice(1), function (v2) {
                o = o[v2] = o[v2] || {};
            });
        });
        return o;
    },
    UID: 1,
    getDom: function (el) {
        if (!el || !DOC) {
            return null;
        }
        el = el.dom ? el.dom : (typeof el == 'string' ? DOC.getElementById(el) : el);
        if (el && !el.uid) {
            el.uid = Edo.UID++;
        }
        return el;
    },

    getBody: function () {
        return DOC.body || DOC.documentElement;
    },


    removeNode: isIE ? function () {
        var d;
        var objs, io, lo;
        return function (n, all) {
            if (n && n.tagName != 'BODY') {

                objs = n.getElementsByTagName('OBJECT');
                lo = objs.length;
                for (io = 0; io < lo; io++) {
                    objs[0].parentNode.removeChild(objs[0]);
                }
                if (!all) {
                    n.parentNode.removeChild(n);
                    return;
                }
                d = d || DOC.createElement('div');
                d.appendChild(n);
                d.innerHTML = '';
            }
        }
    } () : function (n) {
        if (n && n.parentNode && n.tagName != 'BODY') {
            n.parentNode.removeChild(n);
        }
    },

    isArray: function (v) {
        return toString.apply(v) === '[object Array]';
    },

    isNumber: function (v) {
        return typeof v === 'number' && isFinite(v);
    },

    //自定义扩展
    toBool: function (value) {
        if (typeof value === 'string') {
            if (value == 'true') return true;
            if (value == 'false') return false;
            throw new Error("必须传递布尔值");
        }
        return !!value;
    },
    isValue: function (value) {    //null, undefined, ""返回false
        return value || value === 0 || value === false;
    },
    isPercent: function (v) {
        return typeof v === 'string' && v.length > 1 && v.charAt(v.length - 1) == '%';
    },

    isDate: function (v) {
        return toString.apply(v) === '[object Date]';
    },
    isInt: function (value) {
        var v = parseFloat(value)
        return !isNaN(v) && v == value;
    },
    //保留小数点(如果有的话)
    toFixed: function (v, num) {
        var ss = String(v).split('.');
        if (ss.length == 1) return ss[0];
        return ss[0] + "." + ss[1].substring(0, num);
    },

    type: function (o) {
        if (o === undefined || o === null) {
            return false;
        }
        if (o.htmlElement) {
            return 'element';
        }
        var t = typeof o;
        if (t == 'object' && o.nodeName) {
            switch (o.nodeType) {
                case 1: return 'element';
                case 3: return (/\S/).test(o.nodeValue) ? 'textnode' : 'whitespace';
            }
        }
        if (t == 'object' || t == 'function') {
            switch (o.constructor) {
                case Array: return 'array';
                case RegExp: return 'regexp';
                case Date: return 'date';
            }
            if (typeof o.length == 'number' && typeof o.item == 'function') {
                return 'nodelist';
            }
        }
        return t;
    },

    emptyFn: function () { },

    globalEval: function (data) {
        if (data) {
            if (window.execScript)
                window.execScript(data);
            else if (isSafari) {
                //window.setTimeout( data, 0 );						
                var head = document.getElementsByTagName("head")[0] || document.documentElement;
                var script = document.createElement("script");
                script.type = "text/javascript";
                if (isIE) script.text = data;
                else script.appendChild(document.createTextNode(data));
                head.appendChild(script);
                head.removeChild(script);
            } else {
                eval.call(window, data);
            }
        }
    },
    domLoaded: false,
    domLoad: function (fn) {

        if (Edo.domLoaded) {
            fn();
            return;
        }

        if (!window.__load_events) window.__load_events = [];
        window.__load_events.push(fn);
        var done = false;
        var init = function () {
            if (done) return;
            done = true;

            if (isGecko || isOpera) {
                document.removeEventListener("DOMContentLoaded", init, false);
            }
            if (window.__load_timer) {
                clearInterval(window.__load_timer);
                window.__load_timer = null;
            }
            if (isIE) {
                var defer = document.getElementById("__ie_onload");
                if (defer) {
                    defer.onreadystatechange = Edo.emptyFn;
                    defer.parentNode.removeChild(defer);
                    defer = null;
                }
            }
            if (!Edo.domLoaded) {
                Edo.domLoaded = true;
                for (var i = 0; i < window.__load_events.length; i++) {
                    window.__load_events[i]();
                }
                window.__load_events = null;
            }
        }
        Edo.util.Dom.on(window, 'load', function (e) {
            //alert(1);
            init();
        });
        setTimeout(init, 3000);


        if (isIE) {
            //document.write("<s"+'cript id="__ie_onload" defer="defer" src="/'+'/:"></s'+"cript>");
            document.write("<script id='__ie_onload' defer src='javascript:void(0)'><\/script>");
            var s = document.getElementById("__ie_onload");

            s.onreadystatechange = function () {
                if (this.readyState == "complete") {
                    init();
                }
            };
            s = null;
        } else if (isGecko || isOpera) {
            document.addEventListener("DOMContentLoaded", init, false);

        } else if (isWebKit) {
            window.__load_timer = setInterval(function () {
                if (/loaded|complete/.test(document.readyState)) {
                    init();
                }
            }, 10);
        }
    },

    parseDate: function (input, format) {
        if (!input) return;
        if (input.format) return input;
        var p = Date.parseFunctions;
        if (p[format] == null) {
            Date.createParser(format);
        }
        var func = p[format];
        return Date[func](input);
    }
});
Edo.replaceHtml = function (el, html, defer) {
    var oldEl = (typeof el === "string" ? document.getElementById(el) : el);
    var newEl = document.createElement(oldEl.nodeName);
    // Preserve the element's id and class (other properties are lost)
    newEl.id = oldEl.id;
    newEl.className = oldEl.className;
    newEl.style.width = oldEl.style.width;
    newEl.style.height = oldEl.style.height;
    newEl.style.left = oldEl.style.left;
    newEl.style.top = oldEl.style.top;
    // Replace the old with the new
    if (defer) {
        setTimeout(function () {
            newEl.innerHTML = html;
        }, 1);
    } else {
        newEl.innerHTML = html;
    }
    oldEl.parentNode.replaceChild(newEl, oldEl);


    setTimeout(function () {
        Edo.removeNode(oldEl, true);
        oldEl = null;
    }, 1000);
    //Edo.removeNode(oldEl, true);

    return newEl;
};

Function.prototype.regType = function (type) {
    var as = arguments;
    if (as.length > 1) {
        for (var i = 0, l = as.length; i < l; i++) {
            this.regType(as[i]);
        }
        return;
    }
    type = type.toLowerCase();
    Edo.types[type] = this;
    this.type = type;
}

Edo.getValue = function (o, mapping) {
    mapping = String(mapping);
    var maps = mapping.split('.');
    var v = o;
    for (var i = 0, l = maps.length; i < l; i++) {
        v = v[maps[i]];
    }
    return v;
}
Edo.setValue = function (o, mapping, value) {
    mapping = String(mapping);
    var maps = mapping.split('.');
    var v = o;

    for (var i = 0, l = maps.length; i < l; i++) {
        if (i == l - 1) {
            v[maps[i]] = value;
            break;
        } else {
            v = v[maps[i]];
        }
    }
}
//克隆数据,应用场景:两个表格, 使用的数据是一样的.但是在内存角度来讲, 他们应该是不同的对象.数据内容是一样的.
Edo.clone = function (o) {
    var json = Edo.util.JSON.encode(o, function (o) {
        return 'new Date(' + o.getTime() + ')';
    });
    return Edo.util.JSON.decode(json);
}


Array.prototype.each = function (method, instance) {
    for (var i = 0, l = this.length; i < l; i++) {
        var elt = this[i];
        if (typeof (elt) !== 'undefined') {
            if (method.call(instance, elt, i, this) === false) break;
        }
    }
}

Edo.ns = Edo.namespace;
Edo.ns('Edo.util', 'Edo.util.Fx', 'Edo.core', 'Edo.managers', 'Edo.controls', 'Edo.containers', 'Edo.navigators', 'Edo.lists', 'Edo.layouts', 'Edo.data', 'Edo.plugins', 'Edo.project', 'Edo.excel', 'Edo.ux', 'Edo.project.plugins');

Edo.apply(Function.prototype, {
    bind: function (o) {
        var fn = this;
        return function () {
            return fn.apply(o, arguments);
        }
    },
    //延迟执行  
    defer: function (defer, o, args) {
        var fn = this;
        return setTimeout(function () {
            fn.apply(o, args || []);
        }, defer);
    },
    //定时
    time: function (delay, o, args, now) {
        var fn = this;
        if (now) fn.apply(o, args || []);
        return setInterval(function () {
            fn.apply(o, args || []);
        }, delay);
    }
});
Edo.apply(Array.prototype, {
    sortByFn: function (fn) {
        fn = fn || function (a, b) {
            return b < a;
        }
        for (var i = 0, l = this.length; i < l; i++) {
            var a = this[i];
            for (var j = l - 1; j >= i; j--) {
                var b = this[j];
                if (fn(a, b)) {
                    this[j] = a;
                    this[i] = b;
                    a = b;
                }
            }
        }
    },
    add: Array.prototype.enqueue = function (item) {
        this[this.length] = item;
        return this;
    },
    getRange: function (start, end) {
        var arr = [];
        for (var i = start; i <= end; i++) {
            arr[arr.length] = this[i];
        }
        return arr;
    },
    addRange: function (array) {
        for (var i = 0, j = array.length; i < j; i++) this[this.length] = array[i];
        return this;
    },
    clear: function () {
        this.length = 0;
        return this;
    },
    clone: function () {
        if (this.length === 1) {
            return [this[0]];
        }
        else {
            return Array.apply(null, this);
        }
    },
    contains: function (item) {
        return (this.indexOf(item) >= 0);
    },
    indexOf: function (item, from) {
        var len = this.length;
        for (var i = (from < 0) ? Math.max(0, len + from) : from || 0; i < len; i++) {
            if (this[i] === item) return i;
        }
        return -1;
    },
    dequeue: function () {
        return this.shift();
    },
    insert: function (index, item) {
        this.splice(index, 0, item);
        return this;
    },
    remove: function (item) {
        var index = this.indexOf(item);
        if (index >= 0) {
            this.splice(index, 1);
        }
        return (index >= 0);
    },
    removeAt: function (index) {
        var ritem = this[index];
        this.splice(index, 1);
        return ritem;
    },
    rgbToHex: function (array) {
        if (this.length < 3) return false;
        if (this.length == 4 && this[3] == 0 && !array) return 'transparent';
        var hex = [];
        for (var i = 0; i < 3; i++) {
            var bit = (this[i] - 0).toString(16);
            hex.push((bit.length == 1) ? '0' + bit : bit);
        }
        return array ? hex : '#' + hex.join('');
    },
    hexToRgb: function (array) {
        if (this.length != 3) return false;
        var rgb = [];
        for (var i = 0; i < 3; i++) {
            rgb.push(parseInt((this[i].length == 1) ? this[i] + this[i] : this[i], 16));
        }
        return array ? rgb : 'rgb(' + rgb.join(',') + ')';
    },
    equals: function (array) {
        if (this == array) return true;
        if (this.length != array.length) return false;
        for (var i = 0, l = this.length; i < l; i++) {
            if (this[i] != array[i]) return false;
        }
        return true;
    }
});
Edo.applyIf(String, {
    escape: function (string) {
        return string.replace(/('|\\)/g, "\\$1");
    },
    leftPad: function (val, size, ch) {
        var result = new String(val);
        if (!ch) {
            ch = " ";
        }
        while (result.length < size) {
            result = ch + result;
        }
        return result.toString();
    },
    format: function (format) {
        var args = Array.prototype.slice.call(arguments, 1);
        return format.replace(/\{(\d+)\}/g, function (m, i) {
            return args[i];
        });
    }
});


Edo.applyIf(String.prototype, {
    rgbToHex: function (array) {
        var rgb = this.match(/\d{1,3}/g);
        return (rgb) ? rgb.rgbToHex(array) : false;
    },
    hexToRgb: function (array) {
        var hex = this.match(/^#?(\w{1,2})(\w{1,2})(\w{1,2})$/);
        return (hex) ? hex.slice(1).hexToRgb(array) : false;
    },
    contains: function (string, s) {
        return (s) ? (s + this + s).indexOf(s + string + s) > -1 : this.indexOf(string) > -1;
    }
});
String.prototype.trim = function () {
    var re = /^\s+|\s+$/g;
    return function () { return this.replace(re, ""); };
} ();
Edo.applyIf(Number.prototype, {
    constrain: function (min, max) {
        return Math.min(Math.max(this, min), max);
    }
});
Date.setHours = function (date, hour) {
    date.setHours(0);
    date.setMinutes(hour * 60);
}
Date.setMinutes = function (date, minutes) {
    date.setHours(0);
    date.setMinutes(minutes);
}

if (typeof $ == 'undefined') {
    $ = Edo.getDom;
}

setInterval(function () {
    if (typeof (CollectGarbage) != 'undefined') CollectGarbage();
}, 10000);

function UUID() {
    var s = [], itoh = '0123456789ABCDEF'.split('');
    for (var i = 0; i < 36; i++) s[i] = Math.floor(Math.random() * 0x10);
    s[14] = 4;
    s[19] = (s[19] & 0x3) | 0x8;
    for (var i = 0; i < 36; i++) s[i] = itoh[s[i]];
    s[8] = s[13] = s[18] = s[23] = '-';
    return s.join('');
}

function Clone(obj) {
    var objClone;
    if (obj.constructor == Object) {
        objClone = new obj.constructor();
    } else {
        objClone = new obj.constructor(obj.valueOf());
    }
    for (var key in obj) {
        if (objClone[key] != obj[key]) {
            if (typeof (obj[key]) == 'object') {
                objClone[key] = Clone(obj[key]);
            } else {
                objClone[key] = obj[key];
            }
        }
    }
    objClone.toString = obj.toString;
    objClone.valueOf = obj.valueOf;
    return objClone;
}

//Object.prototype.Clone = function(){
//    var objClone;
//    if (this.constructor == Object){
//        objClone = new this.constructor(); 
//    }else{
//        objClone = new this.constructor(this.valueOf()); 
//    }
//    for(var key in this){
//        if ( objClone[key] != this[key] ){ 
//            if ( typeof(this[key]) == 'object' ){ 
//                objClone[key] = this[key].Clone();
//            }else{
//                objClone[key] = this[key];
//            }
//        }
//    }
//    objClone.toString = this.toString;
//    objClone.valueOf = this.valueOf;
//    return objClone; 
//}

Edo.util.Dom = {
    camelRe: /(-[a-z])/gi,
    unitPattern: /\d+(px|em|%|en|ex|pt|in|cm|mm|pc)$/i,
    borders: { l: "border-left-width", r: "border-right-width", t: "border-top-width", b: "border-bottom-width" },
    paddings: { l: "padding-left", r: "padding-right", t: "padding-top", b: "padding-bottom" },
    margins: { l: "margin-left", r: "margin-right", t: "margin-top", b: "margin-bottom" },
    camelFn: function (m, a) { return a.charAt(1).toUpperCase(); },
    propCache: {},
    classCache: {},
    evHash: {},

    EID: 1,
    EVENTS: {
        dragstart: 1,
        dragmove: 1,
        dragcomplete: 1,
        dragenter: 1,
        dragover: 1,
        dragout: 1,
        dragdrop: 1
    },


    addListener: function (el, type, fn, scope) {
        el = Edo.getDom(el);
        if (!el) return false;
        var id = el.uid;

        var ides = this.evHash[id];
        if (!ides) {
            ides = this.evHash[id] = { dom: el };
        }
        var tes = ides[type];
        if (!tes) tes = ides[type] = [];
        //status = el.id +":"+new Date();
        //alert(el+":"+el.id);

        scope = scope || el;
        //findListener判断,同一个函数,只能绑定一次!
        if (this.findListener(el, type, fn, scope)) return false;

        var method = function (e) {
            return fn.call(scope, new Edo.util.Event(e || window.event));
        };

        tes[tes.length] = [fn, method, scope];

        if (this.EVENTS[type]) return;
        if (window.addEventListener) {
            if (isFireFox && type == 'mousewheel') type = 'DOMMouseScroll';
            el.addEventListener(type, method, false);
        } else if (window.attachEvent) {
            el.attachEvent("on" + type, method);
        }
        return true;
    },

    removeListener: function (el, type, fn, scope) {
        el = Edo.getDom(el);
        if (!el) return false;
        scope = scope || el;

        var f = this.findListener(el, type, fn, scope);
        if (!f) return false;

        var id = el.uid;
        this.evHash[id][type].remove(f);

        if (this.EVENTS[type]) return;
        if (window.removeEventListener) {
            if (isFireFox && type == 'mousewheel') type = 'DOMMouseScroll';
            el.removeEventListener(type, f[1], false);
        } else if (window.detachEvent) {
            el.detachEvent("on" + type, f[1]);
        }
        return true;
    },

    findListener: function (el, type, fn, scope) {
        el = Edo.getDom(el);
        if (!el) return false;
        scope = scope || el;
        var id = el.uid;

        var ides = this.evHash[id];
        if (!ides) return;
        var tes = ides[type];
        if (!tes) return;
        for (var i = 0, l = tes.length; i < l; i++) {
            var f = tes[i];
            if (f[0] === fn && f[2] === scope) return f;
        }
    },
    fireEvent: function (el, name) {
        el = Edo.getDom(el);
        if (!el) return false;
        var id = el.uid;

        var events = this.evHash[id];
        if (!events) return;
        name = name.toLowerCase();
        var event = events[name];
        if (event) {
            var args = Array.apply(null, arguments);
            args.shift();
            args.shift();
            event.each(function (fn) {
                fn[0].apply(fn[2], args);
            });
        }
    },

    clearEvent: function (el, type) {
        el = Edo.getDom(el);
        if (!el) return false;
        var id = el.uid;

        var ides = this.evHash[id];
        if (ides) {
            for (var name in ides) {
                if (name == 'dom') continue;
                if ((type && type == name) || !type) {
                    var tes = ides[name];
                    while (tes.length) {
                        var cache = tes[0];
                        this.removeListener(el, name, cache[0], cache[2]);
                    }
                }
            }
        }
        if (!type) delete this.evHash[id];
    },

    swallowEvent: function (el, eventName, preventDefault) {
        el = Edo.getDom(el);
        var fn = function (e) {
            e.stopEvent();
            if (preventDefault) {
                e.stopDefault();
            }
        };
        if (eventName instanceof Array) {
            for (var i = 0, len = eventName.length; i < len; i++) {
                this.on(el, eventName[i], fn);
            }
        } else {
            this.on(el, eventName, fn);
        }
    },

    addClass: function (el, className) {
        el = Edo.getDom(el);
        if (className && !this.hasClass(el, className)) {
            el.className = el.className + " " + className;
        }
    },

    removeClass: function (el, className) {
        el = Edo.getDom(el);
        if (!el || !className || !el.className) {
            return
        }
        if (this.hasClass(el, className)) {
            var re = this.classCache[className];
            if (!re) {
                re = new RegExp('(?:^|\\s+)' + className + '(?:\\s+|$)', "g");
                this.classCache[className] = re;
            }
            el.className = el.className.replace(re, " ");
        }
    },

    hasClass: function (el, className) {
        el = Edo.getDom(el);
        return className && (' ' + el.className + ' ').indexOf(' ' + className + ' ') != -1;
    },

    toggleClass: function (el, className) {
        el = Edo.getDom(el);
        if (this.hasClass(el, className)) {
            this.removeClass(el, className);
        } else {
            this.addClass(el, className);
        }
    },

    replaceClass: function (el, o, n) {
        el = Edo.getDom(el);
        this.removeClass(el, o);
        this.addClass(el, n);
    },
    applyStyles: function (el, styles) {
        el = Edo.getDom(el);
        if (styles) {
            if (typeof styles == "string") {
                var re = /\s?([a-z\-]*)\:\s?([^;]*);?/gi;
                var matches;
                while ((matches = re.exec(styles)) != null) {
                    this.setStyle(el, matches[1], matches[2]);
                }
            } else if (typeof styles == "object") {
                for (var style in styles) {
                    this.setStyle(el, style, styles[style]);
                }
            } else if (typeof styles == "function") {
                this.applyStyles(el, styles.call());
            }
        }
    },

    getStyles: function (el) {
        var a = arguments, len = a.length, r = {};
        for (var i = 1; i < len; i++) {
            r[a[i]] = this.getStyle(el, a[i]);
        }
        return r;
    },

    getStyle: Edo.view && Edo.view.getComputedStyle ?
        function (el, prop) {
            el = Edo.getDom(el);
            var v, cs, camel;
            if (prop == 'float') {
                prop = "cssFloat";
            }
            if (v = el.style[prop]) {
                return v;
            }
            if (cs = Edo.view.getComputedStyle(el, "")) {
                if (!(camel = this.propCache[prop])) {
                    camel = this.propCache[prop] = prop.replace(this.camelRe, this.camelFn);
                }
                return cs[camel];
            }
            return null;
        } :
        function (el, prop) {
            el = Edo.getDom(el);
            var v, cs, camel;
            if (prop == 'opacity') {
                return this.getOpacity(el);
            } else if (prop == 'float') {
                prop = "styleFloat";
            }
            if (!(camel = this.propCache[prop])) {
                camel = this.propCache[prop] = prop.replace(this.camelRe, this.camelFn);
            }
            if (v = el.style[camel]) {
                return v;
            }
            if (cs = el.currentStyle) {
                return cs[camel];
            }
            return null;
        },

    setStyle: function (el, prop, value) {
        el = Edo.getDom(el);
        if (typeof prop == "string") {
            var camel;
            if (!(camel = this.propCache[prop])) {
                camel = this.propCache[prop] = prop.replace(this.camelRe, this.camelFn);
            }
            switch (camel) {
                case 'opacity': this.setOpacity(el, value); break;
                case 'float': camel = isIE ? 'styleFloat' : 'cssFloat';
                default:
                    el.style[camel] = value;
            }
        } else {
            for (var style in prop) {
                if (typeof prop[style] != "function") {
                    this.setStyle(el, style, prop[style]);
                }
            }
        }
    },

    isStyle: function (el, style, val) {
        return this.getStyle(el, style) == val;
    },
    isBorderBox: function (el) {
        el = Edo.getDom(el);
        if (!this.noBoxAdjust) {
            this.noBoxAdjust = isStrict ? {
                select: 1
            } : {
                input: 1, select: 1, textarea: 1
            };
            if (isIE || isGecko) {
                this.noBoxAdjust['button'] = 1;
            }
        }
        return this.noBoxAdjust[el.tagName.toLowerCase()] || isBorderBox;
    },
    adjust: function (el, v, width) {
        el = Edo.getDom(el);
        var p = width ? 'lr' : 'tb';
        if (typeof v == "number") {
            if (!this.isBorderBox(el)) {
                v -= (this.getBorderWidth(el, p) + this.getPadding(el, p));
            }
            if (v < 0) {
                v = 0;
            }
        }
        return v;
    },
    addUnits: function (v, defaultUnit) {
        if (v === "" || v == "auto") {
            return v;
        }
        if (v === undefined) {
            return '';
        }
        if (typeof v == "number" || !this.unitPattern.test(v)) {
            return v + (defaultUnit || 'px');
        }
        return v;
    },
    addStyles: function (el, sides, styles) {
        var val = 0, v, w;
        for (var i = 0, len = sides.length; i < len; i++) {
            v = this.getStyle(el, styles[sides.charAt(i)]);
            if (v) {
                w = parseInt(v, 10);
                if (w) { val += (w >= 0 ? w : -1 * w); }
            }
        }
        return val;
    },

    getMargins: function (el, side) {
        if (!side) {
            return {
                top: parseInt(this.getStyle(el, "margin-top"), 10) || 0,
                left: parseInt(this.getStyle(el, "margin-left"), 10) || 0,
                bottom: parseInt(this.getStyle(el, "margin-bottom"), 10) || 0,
                right: parseInt(this.getStyle(el, "margin-right"), 10) || 0
            };
        } else {
            return this.addStyles(el, side, this.margins);
        }
    },

    getBorderWidth: function (el, side) {
        return this.addStyles(el, side, this.borders);
    },

    getPadding: function (el, side) {
        return this.addStyles(el, side, this.paddings);
    },

    setWidth: function (el, width) {
        el = Edo.getDom(el);
        width = this.adjust(el, width, true);
        el.style.width = this.addUnits(width);
    },

    getWidth: function (el, content) {
        el = Edo.getDom(el);
        var w = el.offsetWidth || 0;
        w = content !== true ? w : w - this.getBorderWidth(el, "lr") - this.getPadding(el, "lr");
        return w < 0 ? 0 : w;
    },

    setHeight: function (el, height) {
        el = Edo.getDom(el);
        height = this.adjust(el, height);
        el.style.height = this.addUnits(height);
    },

    getHeight: function (el, content) {
        el = Edo.getDom(el);
        var h = el.offsetHeight || 0;
        h = content !== true ? h : h - this.getBorderWidth(el, "tb") - this.getPadding(el, "tb");
        return h < 0 ? 0 : h;
    },

    getViewWidth: function (el) {
        el = Edo.getDom(el);
        var doc = document;
        if (el == doc) {
            return isIE ?
	        	   (isStrict ? doc.documentElement.clientWidth : doc.body.clientWidth) :
	        	   self.innerWidth;
        }
        return el.clientWidth;
    },

    getViewHeight: function (el) {
        el = Edo.getDom(el);
        var doc = document;
        if (el == doc) {
            return isIE ?
	        	   (isStrict ? doc.documentElement.clientHeight : doc.body.clientHeight) :
	        	   self.innerHeight;
        }
        return el.clientHeight;
    },

    getViewSize: function (el) {
        return { width: this.getViewWidth(el), height: this.getViewHeight(el) };
    },

    getScrollWidth: function (el) {
        el = Edo.getDom(el);
        var doc = document;
        if (el == doc) {
            return Math.max(Math.max(doc.body.scrollWidth, doc.documentElement.scrollWidth), this.getViewWidth(doc));
        }

        return el.scrollWidth;
    },

    getScrollHeight: function (el) {
        el = Edo.getDom(el);
        var doc = document;

        if (el == doc) {
            return Math.max(Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight), this.getViewHeight(doc));
        }
        return el.scrollHeight;
    },

    getScrollSize: function (el) {
        return { width: this.getScrollWidth(el), height: this.getScrollHeight(el) };
    },

    getScroll: function (d) {
        if (!d) d = document;
        d = Edo.getDom(d);
        var doc = document;
        if (d == doc || d == doc.body) {
            var l, t;
            if (isIE && isStrict) {
                l = doc.documentElement.scrollLeft || (doc.body.scrollLeft || 0);
                t = doc.documentElement.scrollTop || (doc.body.scrollTop || 0);
            } else {
                l = window.pageXOffset || (doc.body.scrollLeft || 0);
                t = window.pageYOffset || (doc.body.scrollTop || 0);
            }
            return { left: l, top: t };
        } else {
            return { left: d.scrollLeft, top: d.scrollTop };
        }
    },

    isScrollable: function (dom, width) {
        dom = Edo.getDom(dom);
        var w = dom.scrollWidth > dom.clientWidth;
        var h = dom.scrollHeight > dom.clientHeight;
        if (width === true) return w;
        if (width === false) return h;
        return w || h;
    },
    isVisible: function (el, deep) {
        el = Edo.getDom(el);
        var vis = !(this.getStyle(el, "visibility") == "hidden" || this.getStyle(el, "display") == "none");
        if (deep !== true || !vis) {
            return vis;
        }
        var p = el.parentNode;
        while (p && p.tagName.toLowerCase() != "body") {
            if (!this.isVisible(p)) {
                return false;
            }
            p = p.parentNode;
        }
        return true;
    },
    position: function (el, pos, zIndex, x, y) {
        el = Edo.getDom(el);
        if (!pos) {
            if (this.getStyle(el, 'position') == 'static') {
                this.setStyle(el, 'position', 'relative');
            }
        } else {
            this.setStyle(el, "position", pos);
        }
        if (zIndex) {
            this.setStyle(el, "z-index", zIndex);
        }
        if (x !== undefined && y !== undefined) {
            this.setXY(el, [x, y]);
        } else if (x !== undefined) {
            this.setX(el, x);
        } else if (y !== undefined) {
            this.setY(el, y);
        }
    },
    translatePoints: function (el, x, y) {
        el = Edo.getDom(el);
        if (x instanceof Array) {
            y = x[1]; x = x[0];
        }
        var p = this.getStyle(el, 'position');
        var o = this.getXY(el);

        var l = parseInt(this.getStyle(el, 'left'), 10);
        var t = parseInt(this.getStyle(el, 'top'), 10);

        if (isNaN(l)) {
            l = (p == "relative") ? 0 : el.offsetLeft;
        }
        if (isNaN(t)) {
            t = (p == "relative") ? 0 : el.offsetTop;
        }

        return { left: (x - o[0] + l), top: (y - o[1] + t) };
    },

    setX: function (el, x) {
        this.setXY(el, [x, false]);
    },

    setY: function (el, y) {
        this.setXY(el, [false, y]);
    },

    setXY: function (el, xy) {
        el = Edo.getDom(el);
        this.position(el); //1
        var pts = this.translatePoints(el, xy); //2

        //
        //    if(isSafari){
        //        pts.left -= 2;
        //        pts.top -= 1;
        //    }
        //    if(isChrome){
        //        pts.left += 2;
        //        pts.top += 1;
        //    }

        if (xy[0] !== false) {
            el.style.left = pts.left + "px";
        }
        if (xy[1] !== false) {
            el.style.top = pts.top + "px";
        }
    },

    getXY: function (el) {
        var p, pe, b, scroll, bd = (document.body || document.documentElement);
        el = Edo.getDom(el);

        if (el == bd) {
            return [0, 0];
        }

        var pl = pt = 0;
        if (!isGecko) {
            pl = parseInt(this.getStyle(el, "margin-left"), 10) || 0;
            pt = parseInt(this.getStyle(el, "margin-top"), 10) || 0;
        }

        if (el.getBoundingClientRect) {
            b = el.getBoundingClientRect();
            scroll = this.getScroll(document);
            return [b.left + scroll.left + pl, b.top + scroll.top + pt];
        }
        var x = 0, y = 0;

        p = el;

        var hasAbsolute = this.getStyle(el, "position") == "absolute";

        while (p) {

            x += p.offsetLeft;
            y += p.offsetTop;

            if (!hasAbsolute && this.getStyle(p, "position") == "absolute") {
                hasAbsolute = true;
            }

            if (isGecko && p != el) {                                       //firefox下的bug!
                //if (isGecko) {
                var bt = parseInt(this.getStyle(p, "borderTopWidth"), 10) || 0;
                var bl = parseInt(this.getStyle(p, "borderLeftWidth"), 10) || 0;

                x += bl;
                y += bt;

                if (p != el && this.getStyle(p, 'overflow') != 'visible') {
                    x += bl;
                    y += bt;
                }
            }
            p = p.offsetParent;
        }

        if (isSafari && hasAbsolute) {
            x -= bd.offsetLeft;
            y -= bd.offsetTop;
        }

        if (isGecko && !hasAbsolute) {
            x += parseInt(this.getStyle(bd, "borderLeftWidth"), 10) || 0;
            y += parseInt(this.getStyle(bd, "borderTopWidth"), 10) || 0;
        }

        p = el.parentNode;
        while (p && p != bd) {
            if (!isOpera || (p.tagName != 'TR' && this.getStyle(p, "display") != "inline")) {
                x -= p.scrollLeft;
                y -= p.scrollTop;
            }
            p = p.parentNode;
        }
        ////////////////
        //        if(isSafari && el.parentNode && el.parentNode != bd){
        //            x += (parseInt(this.getStyle(el.parentNode, 'borderLeftWidth')) || 0);
        //            y += (parseInt(this.getStyle(el.parentNode, 'borderLeftWidth')) || 0);
        //            
        //        }
        return [x + pl, y + pt];
    },

    setSize: function (el, w, h) {
        if (w || w == 0) this.setWidth(el, w);
        if (h || h == 0) this.setHeight(el, h);
    },

    getSize: function (el, content) {
        return { width: this.getWidth(el, content), height: this.getHeight(el, content) };
    },

    setBox: function (el, box, adjust) {
        var w = box.width, h = box.height;
        if (adjust && !this.isBorderBox(el)) {
            w -= (this.getBorderWidth(el, "lr") + this.getPadding(el, "lr"));
            h -= (this.getBorderWidth(el, "tb") + this.getPadding(el, "tb"));
        }
        this.setSize(el, box.width, box.height);
        this.setXY(el, [box.x, box.y]);
    },

    getBox: function (el, content) {
        el = Edo.getDom(el);
        var xy = this.getXY(el);
        var w = el.offsetWidth, h = el.offsetHeight, bx;
        if (!content) {
            bx = { x: xy[0], y: xy[1], 0: xy[0], 1: xy[1], width: w, height: h };
        } else {
            var l = this.getBorderWidth(el, "l") + this.getPadding(el, "l");
            var r = this.getBorderWidth(el, "r") + this.getPadding(el, "r");
            var t = this.getBorderWidth(el, "t") + this.getPadding(el, "t");
            var b = this.getBorderWidth(el, "b") + this.getPadding(el, "b");
            bx = { x: xy[0] + l, y: xy[1] + t, 0: xy[0] + l, 1: xy[1] + t, width: w - (l + r), height: h - (t + b) };
        }
        bx.right = bx.x + bx.width;
        bx.bottom = bx.y + bx.height;
        return bx;
    },


    clearOpacity: function (el) {
        el = Edo.getDom(el);
        var s = el.style;
        if (isIE) {
            if (typeof s.filter == 'string' && (/alpha/i).test(s.filter)) {
                s.filter = "";
            }
        } else {
            s.opacity = "";
            s["-moz-opacity"] = "";
            s["-khtml-opacity"] = "";
        }
    },

    setOpacity: function (el, opacity) {
        el = Edo.getDom(el);
        opacity = parseFloat(opacity);
        var s = el.style;
        if (isIE) {
            s.zoom = 1;
            s.filter = (s.filter || '').replace(/alpha\([^\)]*\)/gi, "") +
                       (opacity == 1 ? "" : " alpha(opacity=" + opacity * 100 + ")");
        } else {
            s.opacity = opacity;
        }
    },

    getOpacity: function (el) {
        el = Edo.getDom(el);
        var s = el.style;
        if (typeof s.filter == 'string') {
            var m = s.filter.match(/alpha\(opacity=(.*)\)/i);
            if (m) {
                var fv = parseFloat(m[1]);
                if (!isNaN(fv)) {
                    return fv ? fv / 100 : 0;
                }
            }
            return 1;
        } else {
            return parseFloat(s.opacity) || 1;
        }
    },


    remove: function (n, all) {
        Edo.removeNode(n, all);
    },
    clearNodes: function (el) {
        var nodes = el.childNodes;
        for (var i = nodes.length - 1; i >= 0; i--) {
            var node = nodes[i];
            el.removeChild(node);
        }
    },
    warp: null,
    createElement: function (html) {
        if (!this.warp) this.wrap = document.createElement("div");
        html = html.trim();
        var isTr = html.indexOf('<tr') == 0;
        if (isTr) {
            html = '<table>' + html + '</table>';
        }
        this.wrap.innerHTML = html;
        var el = this.wrap.firstChild;
        return isTr ? el.rows[0] : el;
    },

    insertHtml: function (el, html, where) {
        el = Edo.getDom(el);
        where = where.toLowerCase();
        if (el.insertAdjacentHTML) {
            switch (where) {
                case "beforebegin":
                    el.insertAdjacentHTML('BeforeBegin', html);
                    return el.previousSibling;
                case "afterbegin":
                    el.insertAdjacentHTML('AfterBegin', html);
                    return el.firstChild;
                case "beforeend":
                    el.insertAdjacentHTML('BeforeEnd', html);
                    return el.lastChild;
                case "afterend":
                    el.insertAdjacentHTML('AfterEnd', html);
                    return el.nextSibling;
            }
            throw 'Illegal insertion point -> "' + where + '"';
        }
        var range = el.ownerDocument.createRange();
        var frag;
        switch (where) {
            case "beforebegin":
                range.setStartBefore(el);
                frag = range.createContextualFragment(html);
                el.parentNode.insertBefore(frag, el);
                return el.previousSibling;
            case "afterbegin":
                if (el.firstChild) {
                    range.setStartBefore(el.firstChild);
                    frag = range.createContextualFragment(html);
                    el.insertBefore(frag, el.firstChild);
                    return el.firstChild;
                } else {
                    el.innerHTML = html;
                    return el.firstChild;
                }
            case "beforeend":
                if (el.lastChild) {
                    range.setStartAfter(el.lastChild);
                    frag = range.createContextualFragment(html);
                    el.appendChild(frag);
                    return el.lastChild;
                } else {
                    el.innerHTML = html;
                    return el.lastChild;
                }
            case "afterend":
                range.setStartAfter(el);
                frag = range.createContextualFragment(html);
                el.parentNode.insertBefore(frag, el.nextSibling);
                return el.nextSibling;
        }
        throw 'Illegal insertion point -> "' + where + '"';
    },

    insertElement: function (p, el, where) {
        p = Edo.getDom(p);
        el = Edo.getDom(el);
        where = where.toLowerCase()
        if (p.insertAdjacentElement) {
            p.insertAdjacentElement(where, el);
        } else {
            switch (where) {
                case "beforebegin":
                    p.parentNode.insertBefore(el, p);
                    break;
                case "afterbegin":
                    p.insertBefore(el, p.firstChild);
                    break;
                case "beforeend":
                    p.appendChild(el);
                    break;
                case "afterend":
                    p.nextSibling ? p.parentNode.insertBefore(el, p.nextSibling) : p.parentNode.appendChild(el);
                    break;
            }
        }
        switch (where) {
            case "beforebegin":
                return p.previousSibling;
            case "afterbegin":
                return p.firstChild;
            case "beforeend":
                return p.lastChild;
            case "afterend":
                return p.nextSibling;
            default:
                throw new Error();
        }
    },

    insert: function (p, c, where) {
        if (c.toString().indexOf('<tr') == 0) c = this.createElement(c);
        p = Edo.getDom(p);
        return (typeof c == 'string' ? this.insertHtml : this.insertElement)(p, c, where);
    },

    append: function (el, c) {
        el = Edo.getDom(el);
        return this.insert(el, c, 'beforeend');
    },

    preend: function (el, c) {
        el = Edo.getDom(el);
        return this.insert(el, c, 'afterbegin');
    },

    after: function (el, c) {
        el = Edo.getDom(el);
        return this.insert(el, c, 'afterend');
    },

    before: function (el, c) {
        el = Edo.getDom(el);
        return this.insert(el, c, 'beforebegin');
    },

    overwrite: function (p, o) {
        p = Edo.getDom(p);
        p.innerHTML = '';
        return this.append(p, o);
    },

    replace: function (sel, tel) {     //tel替换到sel的位置,sel被删除      
        sel = Edo.getDom(sel);
        tel = Edo.getDom(tel);
        if (sel.parentNode) sel.parentNode.replaceChild(tel, sel);
    },

    empty: function (el) {
        el = Edo.getDom(el);
        if (el.innerHTML) el.innerHTML = '';
        else el.value = ''; //针对表单元素
    },

    isAncestor: function (p, c) {
        var ret = false;
        p = Edo.getDom(p);
        c = Edo.getDom(c);
        if (p === c) return true;
        if (p && c) {
            if (p.contains) {
                return p.contains(c);
            } else
                if (p.compareDocumentPosition) {
                    return !!(p.compareDocumentPosition(c) & 16);
                } else {
                    while (c = c.parentNode) {
                        ret = c == p || ret;
                    }
                }
        }
        return ret;
    },

    findParent: function (p, cls, maxDepth) {
        p = Edo.getDom(p);
        var b = document.body, depth = 0, stopEl;
        maxDepth = maxDepth || 50;
        if (typeof maxDepth != "number") {
            stopEl = Edo.getDom(maxDepth);
            maxDepth = 10;
        }
        while (p && p.nodeType == 1 && depth < maxDepth && p != b && p != stopEl) {
            if (this.hasClass(p, cls)) {
                //if(dq.is(p, simpleSelector)){
                return p;
            }
            depth++;
            p = p.parentNode;
        }
        return null;
    },
    findChild: function (el, cls) {
        el = Edo.getDom(el);
        var els = el.getElementsByTagName('*');
        for (var i = 0, l = els.length; i < l; i++) {
            var el = els[i];
            if (this.hasClass(el, cls)) return el;
        }
    },

    mask: function (el, msg, msgCls, maskCls) {
        el = Edo.getDom(el);
        if (el._mask) return;

        msg = msg || '';
        msgCls = msgCls || '';

        this.unmask(el);

        el._position = el.style.position;

        if (this.getStyle(el, "position") == "static" && el != document && el != document.body) {
            this.setStyle(el, "position", "relative");
        }

        el._mask = this.append(el, '<div class="e-mask" style="height:0px;overflow:hidden;"><div class="e-mask-cover ' + msgCls + '"></div>' + msg + '</div>');

        this.addClass(el, "e-masked");
        if (maskCls) {
            this.addClass(el._mask, maskCls);
        }
        el._mask.style.height = '100%';
        if (document == el || document.body == el) { // ie will not expand full height automatically
            var size = Edo.util.Dom.getScrollSize(document);
            Edo.util.Dom.setHeight(el._mask, size.height);
        }

        return el._mask;
    },

    unmask: function (el) {
        el = Edo.getDom(el);
        if (el._mask) {
            this.remove(el._mask);
            el._mask = null;
        }
        this.removeClass(el, "e-masked");
        if (el._position) el.style.position = el._position;
    },


    isMasked: function (el) {
        el = Edo.getDom(el);
        return !!(el._mask && el._mask.style.display != 'none');
    },

    focus: function (el) {
        el = Edo.getDom(el);
        try {
            setTimeout(function () {
                try {
                    el.focus();
                } catch (e) { }
            }, 10);
        } catch (e) { }
    },

    blur: function (el) {
        el = Edo.getDom(el);
        try {
            //setTimeout(function(){
            try {
                el.blur();
            } catch (e) { }
            //}, 5);
        } catch (e) { }
    },
    //private
    repaint: function (el) {
        this.addClass(el, "e-repaint");
        setTimeout(function () {
            Edo.util.Dom.removeClass(el, "e-repaint");
        }, 1);
    },

    selectable: function (el, selected) {
        el = Edo.getDom(el);
        if (!!selected) {
            this.removeClass(el, 'e-unselectable');
            if (isIE) el.unselectable = "off";
            else {
                el.style.MozUserSelect = '';
                el.style.KhtmlUserSelect = '';
                el.style.UserSelect = '';

            }
        } else {
            this.addClass(el, 'e-unselectable');
            if (isIE) el.unselectable = 'on';
            else {
                el.style.MozUserSelect = 'none';
                el.style.UserSelect = 'none';
                el.style.KhtmlUserSelect = 'none';
            }
        }
    },
    //private
    clip: function (el) {
        el = Edo.getDom(el);
        if (!el.isClipped) {
            el.isClipped = true;
            el.originalClip = {
                o: this.getStyle(el, "overflow"),
                x: this.getStyle(el, "overflow-x"),
                y: this.getStyle(el, "overflow-y")
            };
            this.setStyle(el, "overflow", "hidden");
            this.setStyle(el, "overflow-x", "hidden");
            this.setStyle(el, "overflow-y", "hidden");
        }
    },
    //private
    unclip: function (el) {
        el = Edo.getDom(el);
        if (el.isClipped) {
            el.isClipped = false;
            var o = el.originalClip;
            if (o.o) { this.setStyle(el, "overflow", o.o); }
            if (o.x) { this.setStyle(el, "overflow-x", o.x); }
            if (o.y) { this.setStyle(el, "overflow-y", o.y); }
        }
    },

    getOuterHtml: function (el) {
        el = Edo.getDom(el);
        var s = el.outerHTML;
        if (s) return s;
        var attrs = el.attributes;
        var str = "<" + el.tagName;
        for (var i = 0; i < attrs.length; i++) {
            var attr = attrs[i];
            if (attr.specified) {
                str += " " + attr.name + '="' + attr.value + '"';
            }
        }
        s = el.innerHTML;
        if (s) return str + ">" + s + "</" + el.tagName + ">";
        else return str + ">";
    },

    setOuterHtml: function (el, val) {
        el = Edo.getDom(el);
        if (el.outerHTML) {
            el.outerHTML = val;
            return;
        }
        var r = el.ownerDocument.createRange();
        r.setStartBefore(el);
        var df = r.createContextualFragment(val);
        el.parentNode.replaceChild(df, el);
    },

    hover: function (el, overFn, outFn, scope) {
        el = Edo.getDom(el);
        var preOverFn = function (e) {
            if (!e.within(this, true)) {
                overFn.apply(scope || this, arguments);
            }
        };
        var preOutFn = function (e) {
            if (!e.within(this, true)) {
                outFn.apply(scope || this, arguments);
            }
        };
        this.on(el, "mouseover", preOverFn, el);
        this.on(el, "mouseout", preOutFn, el);
    },

    addClassOnOver: function (el, className) {
        el = Edo.getDom(el);
        el.overCls = className;
        this.hover(
            el,
            function () {
                this.addClass(el, el.overCls);
            },
            function () {
                this.removeClass(el, el.overCls);
            },
            this
        );
    },

    addClassOnFocus: function (el, className) {
        el = Edo.getDom(el);
        el.focusCls = className;
        this.on(el, "focus", function () {
            this.addClass(el, el.focusCls);
        }, this);
        this.on(el, "blur", function () {
            this.removeClass(el, el.focusCls);
        }, this);
    },

    addClassOnClick: function (el, className, callback) {
        el = Edo.getDom(el);
        el.clickCls = className;
        this.on(el, "mousedown", function () {
            this.addClass(el, el.clickCls);
            var fn = function () {
                this.removeClass(el, el.clickCls);
                this.un(document, "mouseup", fn, this);

                if (callback) callback();
            };
            this.on(document, "mouseup", fn, this);
        }, this);
    },
    isInRegin: function (xy, region) {
        var x = xy[0], y = xy[1];
        region.right = region.x + region.width;
        region.bottom = region.y + region.height;
        return x >= region.x && x <= region.right && y >= region.y && y <= region.bottom;
    },
    getbyClass: function (className, dom) {
        var els = this.getsbyClass(className, dom, false);
        return els[0];
    },
    getsbyClass: function (className, dom, all) {
        if (dom && typeof dom == 'string') dom = document.getElementById(dom);
        var els = (dom || document).getElementsByTagName('*');
        var doms = [];
        for (var i = 0, l = els.length; i < l; i++) {
            var el = els[i];
            if (className && (' ' + el.className + ' ').indexOf(' ' + className + ' ') != -1) {
                doms.push(el);
                if (all === false) {
                    break
                }
            }
        }
        return doms;
    },
    getChildren: function (el) {
        el = Edo.getDom(el);
        var cs = el.childNodes;
        var cs2 = [];
        for (var i = 0, l = cs.length; i < l; i++) {
            var c = cs[i];
            if (c && c.tagName && c.nodeType == 1) {
                cs2.push(c);
            }
        }
        return cs2;
    },
    getOffsetsTo: function (el, target) {
        var o = this.getXY(el), e = this.getXY(target);
        return [o[0] - e[0], o[1] - e[1]];
    },
    scrollIntoView: function (el, container, hscroll) {
        var c = Edo.getDom(container) || Edo.getBody(),
        	o = this.getOffsetsTo(el, c),
            l = o[0] + c.scrollLeft,
            t = o[1] + c.scrollTop,
            b = t + el.offsetHeight,
            r = l + el.offsetWidth,
        	ch = c.clientHeight,
        	ct = parseInt(c.scrollTop, 10),
        	cl = parseInt(c.scrollLeft, 10),
        	cb = ct + ch,
        	cr = cl + c.clientWidth;

        if (el.offsetHeight > ch || t < ct) {
            c.scrollTop = t;
        } else if (b > cb) {
            c.scrollTop = b - ch;
        }
        c.scrollTop = c.scrollTop; // corrects IE, other browsers will ignore

        if (hscroll !== false) {
            if (el.offsetWidth > c.clientWidth || l < cl) {
                c.scrollLeft = l;
            } else if (r > cr) {
                c.scrollLeft = r - c.clientWidth;
            }
            c.scrollLeft = c.scrollLeft;
        }
        return this;
    }
};

Edo.util.Dom.on = Edo.util.Dom.addListener;
Edo.util.Dom.un = Edo.util.Dom.removeListener;
Edo.util.Dom.contains = Edo.util.Dom.isAncestor;

Edo.getCt = function () {
    if (!Edo.ct) {
        Edo.ct = document.createElement('div');
        Edo.ct.style.cssText = 'position:absolute;overflow:auto;width:2000px;height:1000px;display:block;left:-2000px;top:-1000px;';
        document.body.appendChild(Edo.ct);
    }
    return Edo.ct;
}

//对开放出来的函数,进行重点加密混淆编码保护
Edo.util.Dom.on(window, 'unload', function () {
    var s = new Date();

    if (Edo.managers.SystemManager) Edo.managers.SystemManager.destroy();

    var evHash = Edo.util.Dom.evHash;
    for (var id in evHash) {
        try {
            Edo.util.Dom.clearEvent(evHash[id].dom);
        } catch (e) {
        }
    }

    //alert("close:"+(new Date() - s));
    //Edo.removeNode(Edo.ct, true);
    Edo.ct = null;

});



(function () {
    var initCss = function () {
        // find the body element
        var bd = document.body || document.getElementsByTagName('body')[0];
        if (!bd) return false;

        Edo.getCt();

        var cls = [' ',
                isIE ? "ie " + (isIE6 ? 'ie6' : (isIE7 ? 'ie7' : 'ie8'))
                : isGecko ? "gecko " + (isGecko2 ? 'gecko2' : 'gecko3')
                : isOpera ? "opera"
                : isWebKit ? "webkit" : ""];

        if (isSafari) {
            cls.push("safari " + (isSafari2 ? 'safari2' : (isSafari3 ? 'safari3' : 'safari4')));
        } else if (isChrome) {
            cls.push("chrome");
        }

        if (isMac) {
            cls.push("mac");
        }
        if (isLinux) {
            cls.push("linux");
        }

        if (isStrict || isBorderBox) { // add to the parent to allow for selectors like ".strict .ie"
            var p = bd.parentNode;
            if (p) {
                p.className += isStrict ? ' strict' : ' border-box';
            }
        }
        bd.className += cls.join(' ');
        return true;
    }

    if (!initCss()) {
        Edo.domLoad(initCss);
    }
})();

Edo.domLoad(function () {
    Edo.util.Dom.fireEvent(window, 'domload')
}
);


var enableTrace = false;
var traceWindow = null;
var traceArray = [];
function trace(msg, style) {
    if (!enableTrace) return;
    if (!traceWindow) {
        traceWindow = Edo.util.Dom.append(document.body,
           '<div style="width:40%;height:300px;overflow:auto;border:solid 1px black;position:absolute;right:0;top:0;"></div>'
        );
        Edo.util.Dom.append(document.body,
           '<input type="button" style="border:solid 1px black;position:absolute;right:10px;top:10px;width:50px;" value="清空" onclick="traceWindow.innerHTML = \'\';" />'
        );

    }
    //traceArray.add('<span style="'+style+'">'+msg+'</span>' + '<br/>');
    //traceWindow.innerHTML = traceArray.join('');

    Edo.util.Dom.append(traceWindow,
           '<span style="' + style + '">' + msg + '</span>' + '<br/>'
        );

    traceWindow.scrollTop = 100000;
}


//-----------------------跨浏览器事件操作:增,删,差,清除-----------------------//
var isXUL = isGecko ? function (node) {
    return Object.prototype.toString.call(node) == '[object XULElement]';
} : function () { };

var isTextNode = isGecko ? function (node) {
    try {
        return node.nodeType == 3;
    } catch (e) {
        return false;
    }

} : function (node) {
    return node.nodeType == 3;
};


Edo.util.Event = function (evt) {
    if (evt.rawEvent) return evt;
    var e = this.rawEvent = evt;
    Edo.apply(this, e);
    // mac metaKey behaves like ctrlKey
    this.ctrlKey = e.ctrlKey || e.metaKey;

    if (typeof (e.button) !== 'undefined') {
        var mb = Edo.util.MouseButton;
        this.button = (typeof (e.which) !== 'undefined') ? e.button :
            (e.button === 4) ? mb.middle :
            (e.button === 2) ? mb.right :
            mb.left;
    }

    if (e.type === 'keypress') {
        this.charCode = e.charCode || e.keyCode || 0;
    }
    else if (e.keyCode && (e.keyCode === 46)) {
        this.keyCode = 127;
    }
    this.domType = this.type;
    this.domTarget = this.trigger = this.target = this.resolveTextNode(e.target || e.srcElement);
    this.x = this.getX(), this.y = this.getY();
    this.xy = [this.x, this.y];

    if (evt.type == 'DOMMouseScroll') {
        this.type = 'mousewheel';

        this.wheelDelta = this.getWheelDelta() * 24;
    }
}
Edo.util.Event.prototype = {
    getX: function (ev) {
        ev = ev ? ev.rawEvent || ev : this.rawEvent;
        var x = ev.pageX;
        if (!x && 0 !== x) {
            x = ev.clientX || 0;

            if (document.all) {
                x += this.getScroll()[1];
            }
        }
        return x;
    },
    getY: function (ev) {
        ev = ev ? ev.rawEvent || ev : this.rawEvent;
        var y = ev.pageY;
        if (!y && 0 !== y) {
            y = ev.clientY || 0;
            if (document.all) {
                y += this.getScroll()[0];
            }
        }
        return y;
    },
    getWheelDelta: function () {
        var e = this.rawEvent;
        var delta = 0;
        if (e.wheelDelta) {
            delta = e.wheelDelta / 120;
        } else if (e.detail) {
            delta = -e.detail / 3;
        }
        return delta;
    },
    within: function (el, related) {
        var t = related ? this.getRelatedTarget() : this.domTarget;
        return t && Edo.util.Dom.contains(el, t);
    },
    stopEvent: function () {
        if (this.rawEvent.stopPropagation) {
            this.rawEvent.stopPropagation();
        } else if (window.event) {
            window.event.cancelBubble = true;
        }
    },
    stopDefault: function () {
        if (this.rawEvent.preventDefault) {
            this.rawEvent.preventDefault();
        } else if (window.event) {
            window.event.returnValue = false;
        }
    },
    stop: function () {
        this.stopDefault();
        this.stopEvent();
        //如果是mousedown,则激发document!
    },
    resolveTextNode: function (node) {
        try {
            return node && !node.tagName ? node.parentNode : node;
        } catch (e) {
            return null;
        }
    },
    //    resolveTextNode : function(node) {
    //        return node && !isXUL(node) && isTextNode(node) ? node.parentNode : node;
    //    },
    getRelatedTarget: function (ev) {
        ev = ev ? (ev.rawEvent || ev) : this.rawEvent;
        var t = ev.relatedTarget;
        if (!t) {
            if (ev.type == "mouseout") {
                t = ev.toElement;
            } else if (ev.type == "mouseover") {
                t = ev.fromElement;
            } else if (ev.type == 'blur') {
                t = document.elementFromPoint(this.x, this.y);
            }
        }
        return this.resolveTextNode(t);

        //        return this.resolveTextNode(ev.relatedTarget || 
        //                    (ev.type == MOUSEOUT ? ev.toElement :
        //                     ev.type == MOUSEOVER ? ev.fromElement : null));
    },
    getScroll: function () {
        var dd = document.documentElement, db = document.body;
        if (dd && (dd.scrollTop || dd.scrollLeft)) {
            return [dd.scrollTop, dd.scrollLeft];
        } else if (db) {
            return [db.scrollTop, db.scrollLeft];
        } else {
            return [0, 0];
        }
    }
}
Edo.util.Event.isSafeKey = function (code) {
    return (code >= 16 && code <= 18) || // shift, control, alt
       (code >= 33 && code <= 40); // arrows, home, end
}
Edo.util.MouseButton = {
    left: 0,
    middle: 1,
    right: 2
}


Edo.util.Observable = function () {
    if (!this.events) this.events = {};

    //1)    this.on('click', function(e)...
    //2)    Edo_util_Observable__addListener...     这两种调用方式是一样的


}
Edo.util.Observable.prototype = {

    addListeners: function (listeners, scope) {
        if (listeners) {
            scope = listeners.scope || scope || this;
            delete listeners.scope;
            for (var name in listeners) {
                this.on(name, listeners[name], scope);
            }
        }
        return this;
    },

    addListener: function (name, fn, scope, index) {
        if (typeof fn != 'function') return false;
        name = name.toLowerCase();
        var event = this.events[name];
        if (!event) {
            event = this.events[name] = [];
        }
        if (event) {
            scope = scope || this;
            if (!this.findListener(name, fn, scope)) {
                if (typeof index !== 'undefined') {
                    event.insert(index, [fn, scope]);
                } else {
                    event[event.length] = [fn, scope];
                }
            }
        }
        return this;
    },
    removeListener: function (name, fn, scope) {
        if (typeof fn != 'function') return false;
        name = name.toLowerCase();
        var event = this.events[name];
        if (event) {
            scope = scope || this;
            var f = this.findListener(name, fn, scope);
            if (f) {
                event.remove(f);
            }
        }
        return this;
    },

    findListener: function (name, fn, scope) {
        name = name.toLowerCase();
        var event = this.events[name];
        scope = scope || this;
        if (event) {
            for (var i = 0, l = event.length; i < l; i++) {
                var f = event[i];
                if (f[0] === fn && f[1] === scope) return f;
            }
        }
    },

    addEvents: function () {
        if (!this.events) this.events = {};
        var events = this.events;

        var args = Array.apply(null, arguments);
        args.each(function (name) {
            var event = events[name];
            if (!event) events[name] = [];
        });
        return this;
    },

    fireEvent: function (name, e) {
        var event = this.events[name];
        if (event) {
            var v;
            if (e && e != this) {
                e.source = this;
                e.type = name;

            }

            for (var i = 0, l = event.length; i < l; i++) {
                var fn = event[i];
                var r = fn[0].call(fn[1] || this.scope || this, e);
                if (v !== false) v = r;
            }
            return v;
        }
    },

    clearEvent: function (name) {
        if (name) {
            delete this.events[name];
        } else {
            this.events = {};
        }
        return this;
    }
};
Edo.util.Observable.prototype.on = Edo.util.Observable.prototype.addListener;
Edo.util.Observable.prototype.un = Edo.util.Observable.prototype.removeListener;

var isLocalFile = location.href.indexOf('file:') != -1;
if (!window.XMLHttpRequest || (isIE && isLocalFile)) {
    window.XMLHttpRequest = function () {
        var progIDs = ['Msxml2.XMLHTTP', 'Microsoft.XMLHTTP'];
        for (var i = 0; i < progIDs.length; i++) {
            try {
                var xmlHttp = new ActiveXObject(progIDs[i]);
                return xmlHttp;
            } catch (ex) {
            }
        }
        return null;
    }
}

function emptyFn() { };
Edo.util.Ajax = {
    ajaxID: 100,
    ajaxConfigs: {},
    getAjax: function (id) {
        return this.ajaxConfigs[id];
    },

    request: function (config) {

        var xmlHttp = new XMLHttpRequest();

        var c = Edo.apply({
            type: "get",
            url: null,
            timeout: 0,
            contentType: "application/x-www-form-urlencoded",
            async: true,
            params: null,     //key-value形式,value必须为字符串对象(jsonString).
            onSuccess: emptyFn,
            onFail: emptyFn,
            onOut: emptyFn,

            request: xmlHttp,
            abort: function () {
                if (this._timer) {
                    window.clearTimeout(this._timer);
                    this._timer = null;
                }
                var http = this.request;
                if (http) {
                    http.onreadystatechange = emptyFn;
                    http.abort();
                    //this.request = null;
                    this.onFail(0, this);                   //out失败:0                          
                }
            },
            cache: false
        }, config);

        var data = this.encodeURL(c.params);

        if (c.type.toLowerCase() == "post") {
            xmlHttp.open(c.type, c.url, c.async);
            xmlHttp.setRequestHeader("Content-Type", c.contentType);
        } else {
            if (c.params) c.url += ((c.url.indexOf("?") > -1) ? "&" : "?") + data;
            c.url += ((c.url.indexOf("?") > -1) ? "&" : "?");
            if (c.cache == false) c.url += '_$_d=' + new Date().getTime();

            xmlHttp.open(c.type, c.url, c.async);
        }

        if (c.async) {
            xmlHttp.onreadystatechange = this.onreadystatechange.bind(c);
        }

        if (c.timeout > 0) {
            c._timer = window.setTimeout(function () {
                c.onOut(c);
            }, c.timeout);
        }
        try {
            if (c.defer) {
                setTimeout(function () {
                    try {
                        xmlHttp.send(data);
                    } catch (e) { }
                }, c.defer);
            } else {
                if (c.async) {
                    setTimeout(function () {
                        xmlHttp.send(data);
                    }, 1);
                } else {
                    xmlHttp.send(data);
                }
            }
        } catch (e) {
            c.onFail(1000, c); //没有正确调用成功 1000	        
        }
        if (!c.defer && !c.async) {
            this.onreadystatechange.call(c, xmlHttp);
        }

        var ajaxID = this.ajaxID++;
        this.ajaxConfigs[ajaxID] = c;

        return ajaxID;
    },
    jsonp: function () {

    },

    abort: function (ajaxID) {
        var ajaxConfig = this.ajaxConfigs[ajaxID];
        if (ajaxConfig) {
            ajaxConfig.abort();
        }
    },
    onreadystatechange: function () {
        var http = this.request;
        var statusCode = isLocalFile ? 0 : 200;
        if (http.readyState === 4) {
            if (http.status === statusCode) {
                this.onSuccess(http.responseText, this);
            } else {
                this.onFail(http.status, this);
            }
            http.onreadystatechange = emptyFn;
        }
    },
    encodeURL: function (hs) {
        var s = [];
        for (var j in hs) {
            var v = hs[j];
            if (typeof (v) == 'object') {
                v = Edo.util.JSON.encode(v);
            }
            s.push(encodeURIComponent(j) + "=" + encodeURIComponent(v));
            //s.push(j + "=" + v.replace(/&/g, '&&'));
        }
        return s.join("&");
    }
};


Edo.util.Cookie = {

    get: function (sName) {
        var aCookie = document.cookie.split("; ");
        var lastMatch = null;
        for (var i = 0; i < aCookie.length; i++) {
            var aCrumb = aCookie[i].split("=");
            if (sName == aCrumb[0]) {
                lastMatch = aCrumb;
            }
        }
        if (lastMatch) {
            var v = lastMatch[1];
            if (v === undefined) return v;
            return unescape(v);
        }
        return null;
    },

    set: function (name, value, expires, domain) {
        var LargeExpDate = new Date();
        if (expires != null) {
            //LargeExpDate.setTime(LargeExpDate.getTime() + (expires*1000*3600*24));         

            LargeExpDate = new Date(LargeExpDate.getTime() + (expires * 1000 * 3600 * 24)); //expires天数            
        }

        document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + LargeExpDate.toGMTString())) + ";path=/" + (domain ? "; domain=" + domain : "");
    },

    del: function (name, domain) {
        this.set(name, null, -100, domain);
    }
};




(function () {
    // private
    Date.formatCodeToRegex = function (character, currentGroup) {
        // Note: currentGroup - position in regex result array (see notes for Date.parseCodes below)
        var p = Date.parseCodes[character];

        if (p) {
            p = typeof (p) == 'function' ? p() : p;
            Date.parseCodes[character] = p; // reassign function result to prevent repeated execution
        }

        return p ? Edo.applyIf({
            c: p.c ? String.format(p.c, currentGroup || "{0}") : p.c
        }, p) : {
            g: 0,
            c: null,
            s: escapeRe(character) // treat unrecognised characters as literals
        }
    }

    // private shorthand for Date.formatCodeToRegex since we'll be using it fairly often
    var $f = Date.formatCodeToRegex;

    Edo.apply(Date, {
        // private
        parseFunctions: { count: 0 },
        parseRegexes: [],
        formatFunctions: { count: 0 },
        daysInMonth: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
        y2kYear: 50,


        MILLI: "ms",


        SECOND: "s",


        MINUTE: "mi",


        HOUR: "h",


        DAY: "d",


        MONTH: "mo",


        YEAR: "y",


        dayNames: [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    ],


        monthNames: [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    ],


        monthNumbers: {
            Jan: 0,
            Feb: 1,
            Mar: 2,
            Apr: 3,
            May: 4,
            Jun: 5,
            Jul: 6,
            Aug: 7,
            Sep: 8,
            Oct: 9,
            Nov: 10,
            Dec: 11
        },


        getShortMonthName: function (month) {
            return Date.monthNames[month].substring(0, 3);
        },


        getShortDayName: function (day) {
            return Date.dayNames[day].substring(0, 3);
        },


        getMonthNumber: function (name) {
            // handle camel casing for english month names (since the keys for the Date.monthNumbers hash are case sensitive)
            return Date.monthNumbers[name.substring(0, 1).toUpperCase() + name.substring(1, 3).toLowerCase()];
        },


        formatCodes: {
            d: "String.leftPad(this.getDate(), 2, '0')",
            D: "Date.getShortDayName(this.getDay())", // get localised short day name
            j: "this.getDate()",
            l: "Date.dayNames[this.getDay()]",
            N: "(this.getDay() ? this.getDay() : 7)",
            S: "this.getSuffix()",
            w: "this.getDay()",
            z: "this.getDayOfYear()",
            W: "String.leftPad(this.getWeekOfYear(), 2, '0')",
            F: "Date.monthNames[this.getMonth()]",
            m: "String.leftPad(this.getMonth() + 1, 2, '0')",
            M: "Date.getShortMonthName(this.getMonth())", // get localised short month name
            n: "(this.getMonth() + 1)",
            t: "this.getDaysInMonth()",
            L: "(this.isLeapYear() ? 1 : 0)",
            o: "(this.getFullYear() + (this.getWeekOfYear() == 1 && this.getMonth() > 0 ? +1 : (this.getWeekOfYear() >= 52 && this.getMonth() < 11 ? -1 : 0)))",
            Y: "this.getFullYear()",
            y: "('' + this.getFullYear()).substring(2, 4)",
            a: "(this.getHours() < 12 ? 'am' : 'pm')",
            A: "(this.getHours() < 12 ? 'AM' : 'PM')",
            g: "((this.getHours() % 12) ? this.getHours() % 12 : 12)",
            G: "this.getHours()",
            h: "String.leftPad((this.getHours() % 12) ? this.getHours() % 12 : 12, 2, '0')",
            H: "String.leftPad(this.getHours(), 2, '0')",
            i: "String.leftPad(this.getMinutes(), 2, '0')",
            s: "String.leftPad(this.getSeconds(), 2, '0')",
            u: "String.leftPad(this.getMilliseconds(), 3, '0')",
            O: "this.getGMTOffset()",
            P: "this.getGMTOffset(true)",
            T: "this.getTimezone()",
            Z: "(this.getTimezoneOffset() * -60)",
            c: function () { // ISO-8601 -- GMT format
                for (var c = "Y-m-dTH:i:sP", code = [], i = 0, l = c.length; i < l; ++i) {
                    var e = c.charAt(i);
                    code.push(e == "T" ? "'T'" : Date.getFormatCode(e)); // treat T as a character literal
                }
                return code.join(" + ");
            },

            U: "Math.round(this.getTime() / 1000)"
        },


        parseDate: function (input, format) {
            if (!input) return;
            if (input.format) return input;
            var p = Date.parseFunctions;
            if (p[format] == null) {
                Date.createParser(format);
            }
            var func = p[format];
            return Date[func](input);
        },

        // private
        getFormatCode: function (character) {
            var f = Date.formatCodes[character];

            if (f) {
                f = typeof (f) == 'function' ? f() : f;
                Date.formatCodes[character] = f; // reassign function result to prevent repeated execution
            }

            // note: unknown characters are treated as literals
            return f || ("'" + String.escape(character) + "'");
        },

        // private
        createNewFormat: function (format) {
            var funcName = "format" + Date.formatFunctions.count++;
            Date.formatFunctions[format] = funcName;
            var code = "Date.prototype." + funcName + " = function(){return ";
            var special = false;
            var ch = '';
            for (var i = 0; i < format.length; ++i) {
                ch = format.charAt(i);
                if (!special && ch == "\\") {
                    special = true;
                }
                else if (special) {
                    special = false;
                    code += "'" + String.escape(ch) + "' + ";
                }
                else {
                    code += Date.getFormatCode(ch) + " + ";
                }
            }
            eval(code.substring(0, code.length - 3) + ";}");
        },

        // private
        createParser: function (format) {
            var funcName = "parse" + Date.parseFunctions.count++;
            var regexNum = Date.parseRegexes.length;
            var currentGroup = 1;
            Date.parseFunctions[format] = funcName;

            var code = "Date." + funcName + " = function(input){\n"
            + "var y, m, d, h = 0, i = 0, s = 0, ms = 0, o, z, u, v;\n"
            + "input = String(input);\n"
            + "d = new Date();\n"
            + "y = d.getFullYear();\n"
            + "m = d.getMonth();\n"
            + "d = d.getDate();\n"
            + "var results = input.match(Date.parseRegexes[" + regexNum + "]);\n"
            + "if (results && results.length > 0) {";
            var regex = "";

            var special = false;
            var ch = '';
            for (var i = 0; i < format.length; ++i) {
                ch = format.charAt(i);
                if (!special && ch == "\\") {
                    special = true;
                }
                else if (special) {
                    special = false;
                    regex += String.escape(ch);
                }
                else {
                    var obj = Date.formatCodeToRegex(ch, currentGroup);
                    currentGroup += obj.g;
                    regex += obj.s;
                    if (obj.g && obj.c) {
                        code += obj.c;
                    }
                }
            }

            code += "if (u){\n"
            + "v = new Date(u * 1000);\n" // give top priority to UNIX time
            + "}else if (y >= 0 && m >= 0 && d > 0 && h >= 0 && i >= 0 && s >= 0 && ms >= 0){\n"
            + "v = new Date(y, m, d, h, i, s, ms);\n"
            + "}else if (y >= 0 && m >= 0 && d > 0 && h >= 0 && i >= 0 && s >= 0){\n"
            + "v = new Date(y, m, d, h, i, s);\n"
            + "}else if (y >= 0 && m >= 0 && d > 0 && h >= 0 && i >= 0){\n"
            + "v = new Date(y, m, d, h, i);\n"
            + "}else if (y >= 0 && m >= 0 && d > 0 && h >= 0){\n"
            + "v = new Date(y, m, d, h);\n"
            + "}else if (y >= 0 && m >= 0 && d > 0){\n"
            + "v = new Date(y, m, d);\n"
            + "}else if (y >= 0 && m >= 0){\n"
            + "v = new Date(y, m);\n"
            + "}else if (y >= 0){\n"
            + "v = new Date(y);\n"
            + "}\n}\nreturn (v && (z || o))?" // favour UTC offset over GMT offset
            + " (typeof(z) == 'number' ? v.add(Date.SECOND, -v.getTimezoneOffset() * 60 - z) :" // reset to UTC, then add offset
            + " v.add(Date.MINUTE, -v.getTimezoneOffset() + (sn == '+'? -1 : 1) * (hr * 60 + mn))) : v;\n" // reset to GMT, then add offset
            + "}";

            Date.parseRegexes[regexNum] = new RegExp("^" + regex + "$", "i");
            eval(code);
        },

        // private
        parseCodes: {

            d: {
                g: 1,
                c: "d = parseInt(results[{0}], 10);\n",
                s: "(\\d{2})" // day of month with leading zeroes (01 - 31)
            },
            j: {
                g: 1,
                c: "d = parseInt(results[{0}], 10);\n",
                s: "(\\d{1,2})" // day of month without leading zeroes (1 - 31)
            },
            D: function () {
                for (var a = [], i = 0; i < 7; a.push(Date.getShortDayName(i)), ++i); // get localised short day names
                return {
                    g: 0,
                    c: null,
                    s: "(?:" + a.join("|") + ")"
                }
            },
            l: function () {
                return {
                    g: 0,
                    c: null,
                    s: "(?:" + Date.dayNames.join("|") + ")"
                }
            },
            N: {
                g: 0,
                c: null,
                s: "[1-7]" // ISO-8601 day number (1 (monday) - 7 (sunday))
            },
            S: {
                g: 0,
                c: null,
                s: "(?:st|nd|rd|th)"
            },
            w: {
                g: 0,
                c: null,
                s: "[0-6]" // javascript day number (0 (sunday) - 6 (saturday))
            },
            z: {
                g: 0,
                c: null,
                s: "(?:\\d{1,3}" // day of the year (0 - 364 (365 in leap years))
            },
            W: {
                g: 0,
                c: null,
                s: "(?:\\d{2})" // ISO-8601 week number (with leading zero)
            },
            F: function () {
                return {
                    g: 1,
                    c: "m = parseInt(Date.getMonthNumber(results[{0}]), 10);\n", // get localised month number
                    s: "(" + Date.monthNames.join("|") + ")"
                }
            },
            M: function () {
                for (var a = [], i = 0; i < 12; a.push(Date.getShortMonthName(i)), ++i); // get localised short month names
                return Edo.applyIf({
                    s: "(" + a.join("|") + ")"
                }, $f("F"));
            },
            m: {
                g: 1,
                c: "m = parseInt(results[{0}], 10) - 1;\n",
                s: "(\\d{2})" // month number with leading zeros (01 - 12)
            },
            n: {
                g: 1,
                c: "m = parseInt(results[{0}], 10) - 1;\n",
                s: "(\\d{1,2})" // month number without leading zeros (1 - 12)
            },
            t: {
                g: 0,
                c: null,
                s: "(?:\\d{2})" // no. of days in the month (28 - 31)
            },
            L: {
                g: 0,
                c: null,
                s: "(?:1|0)"
            },
            o: function () {
                return $f("Y");
            },
            Y: {
                g: 1,
                c: "y = parseInt(results[{0}], 10);\n",
                s: "(\\d{4})" // 4-digit year
            },
            y: {
                g: 1,
                c: "var ty = parseInt(results[{0}], 10);\n"
                + "y = ty > Date.y2kYear ? 1900 + ty : 2000 + ty;\n", // 2-digit year
                s: "(\\d{1,2})"
            },
            a: {
                g: 1,
                c: "if (results[{0}] == 'am') {\n"
                + "if (h == 12) { h = 0; }\n"
                + "} else { if (h < 12) { h += 12; }}",
                s: "(am|pm)"
            },
            A: {
                g: 1,
                c: "if (results[{0}] == 'AM') {\n"
                + "if (h == 12) { h = 0; }\n"
                + "} else { if (h < 12) { h += 12; }}",
                s: "(AM|PM)"
            },
            g: function () {
                return $f("G");
            },
            G: {
                g: 1,
                c: "h = parseInt(results[{0}], 10);\n",
                s: "(\\d{1,2})" // 24-hr format of an hour without leading zeroes (0 - 23)
            },
            h: function () {
                return $f("H");
            },
            H: {
                g: 1,
                c: "h = parseInt(results[{0}], 10);\n",
                s: "(\\d{2})" //  24-hr format of an hour with leading zeroes (00 - 23)
            },
            i: {
                g: 1,
                c: "i = parseInt(results[{0}], 10);\n",
                s: "(\\d{2})" // minutes with leading zeros (00 - 59)
            },
            s: {
                g: 1,
                c: "s = parseInt(results[{0}], 10);\n",
                s: "(\\d{2})" // seconds with leading zeros (00 - 59)
            },
            u: {
                g: 1,
                c: "ms = results[{0}]; ms = parseInt(ms, 10)/Math.pow(10, ms.length - 3);\n",
                s: "(\\d+)" // milliseconds with leading zeroes (arbitrary number of digits allowed) e.g. 001, 100, 999, 999876543210
            },
            O: {
                g: 1,
                c: [
                "o = results[{0}];",
                "var sn = o.substring(0,1);", // get + / - sign
                "var hr = o.substring(1,3)*1 + Math.floor(o.substring(3,5) / 60);", // get hours (performs minutes-to-hour conversion also, just in case)
                "var mn = o.substring(3,5) % 60;", // get minutes
                "o = ((-12 <= (hr*60 + mn)/60) && ((hr*60 + mn)/60 <= 14))? (sn + String.leftPad(hr, 2, '0') + String.leftPad(mn, 2, '0')) : null;\n" // -12hrs <= GMT offset <= 14hrs
            ].join("\n"),
                s: "([+\-]\\d{4})" // GMT offset in hrs and mins
            },
            P: {
                g: 1,
                c: [
                "o = results[{0}];",
                "var sn = o.substring(0,1);", // get + / - sign
                "var hr = o.substring(1,3)*1 + Math.floor(o.substring(4,6) / 60);", // get hours (performs minutes-to-hour conversion also, just in case)
                "var mn = o.substring(4,6) % 60;", // get minutes
                "o = ((-12 <= (hr*60 + mn)/60) && ((hr*60 + mn)/60 <= 14))? (sn + String.leftPad(hr, 2, '0') + String.leftPad(mn, 2, '0')) : null;\n" // -12hrs <= GMT offset <= 14hrs
            ].join("\n"),
                s: "([+\-]\\d{2}:\\d{2})" // GMT offset in hrs and mins (with colon separator)
            },
            T: {
                g: 0,
                c: null,
                s: "[A-Z]{1,4}" // timezone abbrev. may be between 1 - 4 chars
            },
            Z: {
                g: 1,
                c: "z = results[{0}] * 1;\n" // -43200 <= UTC offset <= 50400
                  + "z = (-43200 <= z && z <= 50400)? z : null;\n",
                s: "([+\-]?\\d{1,5})" // leading '+' sign is optional for UTC offset
            },
            c: function () {
                var calc = [];
                var arr = [
                $f("Y", 1), // year
                $f("m", 2), // month
                $f("d", 3), // day
                $f("h", 4), // hour
                $f("i", 5), // minute
                $f("s", 6), // second
                {c: "ms = (results[7] || '.0').substring(1); ms = parseInt(ms, 10)/Math.pow(10, ms.length - 3);\n" }, // millisecond decimal fraction (with leading zeroes + arbitrary no. of digits)
                {c: "if(results[9] == 'Z'){\no = 0;\n}else{\n" + $f("P", 9).c + "\n}"} // allow both "Z" (i.e. UTC) and "+08:00" (i.e. GMT) time zone delimiters
            ];
                for (var i = 0, l = arr.length; i < l; ++i) {
                    calc.push(arr[i].c);
                }

                return {
                    g: 1,
                    c: calc.join(""),
                    s: arr[0].s + "-" + arr[1].s + "-" + arr[2].s + "T" + arr[3].s + ":" + arr[4].s + ":" + arr[5].s
                      + "((\.|,)\\d+)?" // ",998465" or ".998465" millisecond decimal fraction
                      + "(" + $f("P", null).s + "|Z)" // "Z" (UTC) or "GMT+08:00" (GMT offset)
                }
            },
            U: {
                g: 1,
                c: "u = parseInt(results[{0}], 10);\n",
                s: "(-?\\d+)" // leading minus sign indicates seconds before UNIX epoch
            }
        }
    });

} ());

Edo.apply(Date.prototype, {
    // private
    dateFormat: function (format) {
        if (Date.formatFunctions[format] == null) {
            Date.createNewFormat(format);
        }
        var func = Date.formatFunctions[format];
        return this[func]();
    },


    getTimezone: function () {
        // the following list shows the differences between date strings from different browsers on a WinXP SP2 machine from an Asian locale:
        //
        // Opera  : "Thu, 25 Oct 2007 22:53:45 GMT+0800" -- shortest (weirdest) date string of the lot
        // Safari : "Thu Oct 25 2007 22:55:35 GMT+0800 (Malay Peninsula Standard Time)" -- value in parentheses always gives the correct timezone (same as FF)
        // FF     : "Thu Oct 25 2007 22:55:35 GMT+0800 (Malay Peninsula Standard Time)" -- value in parentheses always gives the correct timezone
        // IE     : "Thu Oct 25 22:54:35 UTC+0800 2007" -- (Asian system setting) look for 3-4 letter timezone abbrev
        // IE     : "Thu Oct 25 17:06:37 PDT 2007" -- (American system setting) look for 3-4 letter timezone abbrev
        //
        // this crazy regex attempts to guess the correct timezone abbreviation despite these differences.
        // step 1: (?:\((.*)\) -- find timezone in parentheses
        // step 2: ([A-Z]{1,4})(?:[\-+][0-9]{4})?(?: -?\d+)?) -- if nothing was found in step 1, find timezone from timezone offset portion of date string
        // step 3: remove all non uppercase characters found in step 1 and 2
        return this.toString().replace(/^.* (?:\((.*)\)|([A-Z]{1,4})(?:[\-+][0-9]{4})?(?: -?\d+)?)$/, "$1$2").replace(/[^A-Z]/g, "");
    },


    getGMTOffset: function (colon) {
        return (this.getTimezoneOffset() > 0 ? "-" : "+")
            + String.leftPad(Math.abs(Math.floor(this.getTimezoneOffset() / 60)), 2, "0")
            + (colon ? ":" : "")
            + String.leftPad(Math.abs(this.getTimezoneOffset() % 60), 2, "0");
    },


    getDayOfYear: function () {
        var num = 0;
        Date.daysInMonth[1] = this.isLeapYear() ? 29 : 28;
        for (var i = 0; i < this.getMonth(); ++i) {
            num += Date.daysInMonth[i];
        }
        return num + this.getDate() - 1;
    },


    getWeekOfYear: function () {
        // adapted from http://www.merlyn.demon.co.uk/weekcalc.htm
        var ms1d = 864e5; // milliseconds in a day
        var ms7d = 7 * ms1d; // milliseconds in a week
        var DC3 = Date.UTC(this.getFullYear(), this.getMonth(), this.getDate() + 3) / ms1d; // an Absolute Day Number
        var AWN = Math.floor(DC3 / 7); // an Absolute Week Number
        var Wyr = new Date(AWN * ms7d).getUTCFullYear();
        return AWN - Math.floor(Date.UTC(Wyr, 0, 7) / ms7d) + 1;
    },


    isLeapYear: function () {
        var year = this.getFullYear();
        return !!((year & 3) == 0 && (year % 100 || (year % 400 == 0 && year)));
    },


    getFirstDayOfMonth: function () {
        var day = (this.getDay() - (this.getDate() - 1)) % 7;
        return (day < 0) ? (day + 7) : day;
    },


    getLastDayOfMonth: function () {
        var day = (this.getDay() + (Date.daysInMonth[this.getMonth()] - this.getDate())) % 7;
        return (day < 0) ? (day + 7) : day;
    },



    getFirstDateOfMonth: function () {
        return new Date(this.getFullYear(), this.getMonth(), 1);
    },


    getLastDateOfMonth: function () {
        return new Date(this.getFullYear(), this.getMonth(), this.getDaysInMonth());
    },


    getDaysInMonth: function () {
        Date.daysInMonth[1] = this.isLeapYear() ? 29 : 28;
        return Date.daysInMonth[this.getMonth()];
    },


    getSuffix: function () {
        switch (this.getDate()) {
            case 1:
            case 21:
            case 31:
                return "st";
            case 2:
            case 22:
                return "nd";
            case 3:
            case 23:
                return "rd";
            default:
                return "th";
        }
    },


    clone: function () {
        return new Date(this.getTime());
    },


    clearTime: function (clone) {
        if (clone) {
            return this.clone().clearTime();
        }
        this.setHours(0);
        this.setMinutes(0);
        this.setSeconds(0);
        this.setMilliseconds(0);
        return this;
    },


    add: function (interval, value) {
        var d = this.clone();
        if (!interval || value === 0) return d;

        switch (interval.toLowerCase()) {
            case Date.MILLI:
                d.setMilliseconds(this.getMilliseconds() + value);
                break;
            case Date.SECOND:
                d.setSeconds(this.getSeconds() + value);
                break;
            case Date.MINUTE:
                d.setMinutes(this.getMinutes() + value);
                break;
            case Date.HOUR:
                d.setHours(this.getHours() + value);
                break;
            case Date.DAY:
                d.setDate(this.getDate() + value);
                break;
            case Date.MONTH:
                var day = this.getDate();
                if (day > 28) {
                    day = Math.min(day, this.getFirstDateOfMonth().add('mo', value).getLastDateOfMonth().getDate());
                }
                d.setDate(day);
                d.setMonth(this.getMonth() + value);
                break;
            case Date.YEAR:
                d.setFullYear(this.getFullYear() + value);
                break;
        }
        return d;
    },


    between: function (start, end) {
        var t = this.getTime();
        return start.getTime() <= t && t <= end.getTime();
    }
});



Date.prototype.format = Date.prototype.dateFormat;


// private
// safari setMonth is broken
if (isSafari) {
    Date.brokenSetMonth = Date.prototype.setMonth;
    Date.prototype.setMonth = function (num) {
        if (num <= -1) {
            var n = Math.ceil(-num);
            var back_year = Math.ceil(n / 12);
            var month = (n % 12) ? 12 - n % 12 : 0;
            this.setFullYear(this.getFullYear() - back_year);
            return Date.brokenSetMonth.call(this, month);
        } else {
            return Date.brokenSetMonth.apply(this, arguments);
        }
    };
}

///
Date.monthNames = [
   "一月",
   "二月",
   "三月",
   "四月",
   "五月",
   "六月",
   "七月",
   "八月",
   "九月",
   "十月",
   "十一月",
   "十二月"
];

Date.dayNames = [
   "日",
   "一",
   "二",
   "三",
   "四",
   "五",
   "六"
];

Edo.util.Drag = function (options) {
    Edo.apply(this, options);
};
Edo.util.Drag.prototype = {

    onStart: Edo.emptyFn,

    onMove: Edo.emptyFn,

    onStop: Edo.emptyFn,

    capture: false,

    fps: 20,

    event: null,

    started: false,

    //delay: 80,

    //调用start的时候,鼠标已经按下去了.

    start: function (e) {
        e.stopDefault();
        if (e) this.event = e;
        //this.started = false;
        this.now = this.init = this.event.xy.clone();  //初始时的mouse坐标	    

        var bd = document;
        Edo.util.Dom.on(bd, 'mousemove', this.move, this);
        Edo.util.Dom.on(bd, 'mouseup', this.stop, this);

        this.trigger = e.trigger;
        Edo.util.Dom.selectable(this.trigger, false);
        Edo.util.Dom.selectable(bd.body, false);

        if (this.capture) {//ie下,为true的时候,鼠标指针不能得到界面上的do元素
            if (isIE) this.trigger.setCapture(true);
            else if (isGecko) document.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP | Event.MOUSEDOWN);
        }
        //定制一个延迟事件
        this.startTime = new Date();
    },
    move: function (e) {
        if (this.delay) { //如果有延迟配置,则判断当前是否超过了延迟时间
            if (new Date() - this.startTime < this.delay) return;
        }

        //2)
        if (!this.started) {
            this.started = true;
            this.onStart(this);             //在第一次move的时候,才开始onStart	        
        }

        //处理!!!        

        var sf = this;

        if (!this.timer) {
            //this.timer = setTimeout(function(){
            sf.now = e.xy.clone();             //移动中的鼠标坐标	  
            sf.event = e;
            sf.onMove(sf);
            sf.timer = null;
            //}, 0);
        }
    },
    stop: function (e) {
        this.now = e.xy.clone();             //移动中的鼠标坐标
        this.event = e;
        if (this.delay) {
            this.onMove(this);
        }
        if (this.timer) {
            clearTimeout(this.timer);
            this.timer = null;
        }

        var bd = document;
        Edo.util.Dom.un(bd, 'mousemove', this.move, this);
        Edo.util.Dom.un(bd, 'mouseup', this.stop, this);
        Edo.util.Dom.selectable(this.trigger, true);
        Edo.util.Dom.selectable(bd.body, true);
        if (this.capture) {
            if (isIE) this.trigger.releaseCapture();
            else if (isGecko) document.releaseEvents(Event.MOUSEMOVE | Event.MOUSEUP | Event.MOUSEDOWN);
        }

        if (this.started) this.onStop(this);
        this.started = false;
    }
};


Edo.util.JSON = new (function () {
    var useHasOwn = !!{}.hasOwnProperty,
        isNative = function () {
            var useNative = null;

            return function () {
                if (useNative === null) {
                    useNative = Edo.USE_NATIVE_JSON && window.JSON && JSON.toString() == '[object JSON]';
                }

                return useNative;
            };
        } (),
        pad = function (n) {
            return n < 10 ? "0" + n : n;
        },
        doDecode = function (json) {
            return eval('(' + json + ')');
        },
        doEncode = function (o) {
            if (typeof o == "undefined" || o === null) {
                return "null";
                //}else if(Edo.isArray(o)){
            } else if (o.push) {
                return encodeArray(o);
                //}else if(Object.prototype.toString.apply(o) === '[object Date]'){
            } else if (o.getFullYear) {
                return Edo.util.JSON.encodeDate(o);
            } else if (typeof o == "string") {
                //}else if(o.charAt){
                return encodeString(o);
            } else if (typeof o == "number") {
                return isFinite(o) ? String(o) : "null";
            } else if (typeof o == "boolean") {
                return String(o);
            } else {
                var a = ["{"], b, i, v;
                for (i in o) {
                    if (!useHasOwn || o.hasOwnProperty(i)) {
                        v = o[i];
                        switch (typeof v) {
                            case "undefined":
                            case "function":
                            case "unknown":
                                break;
                            default:
                                if (b) {
                                    //a.push(',');
                                    a[a.length] = ',';
                                }
                                //a.push(doEncode(i), ":", v === null ? "null" : doEncode(v));
                                a[a.length] = doEncode(i);
                                a[a.length] = ":";
                                a[a.length] = v === null ? "null" : doEncode(v);

                                b = true;
                        }
                    }
                }
                //a.push("}");
                a[a.length] = "}";
                return a.join("");
            }
        },
        m = {
            "\b": '\\b',
            "\t": '\\t',
            "\n": '\\n',
            "\f": '\\f',
            "\r": '\\r',
            '"': '\\"',
            "\\": '\\\\'
        },
        strReg1 = /["\\\x00-\x1f]/,
        strReg2 = /([\x00-\x1f\\"])/g,
    //new RegExp().c
        encodeString = function (s) {
            if (strReg1.test(s)) {
                return '"' + s.replace(strReg2, function (a, b) {
                    var c = m[b];
                    if (c) {
                        return c;
                    }
                    c = b.charCodeAt();
                    return "\\u00" +
                        Math.floor(c / 16).toString(16) +
                        (c % 16).toString(16);
                }) + '"';
            }
            return '"' + s + '"';
        },
        encodeArray = function (o) {
            var a = ["["], b, i, l = o.length, v;
            for (i = 0; i < l; i += 1) {
                v = o[i];
                switch (typeof v) {
                    case "undefined":
                    case "function":
                    case "unknown":
                        break;
                    default:
                        if (b) {
                            //a.push(',');
                            a[a.length] = ',';
                        }
                        //a.push(v === null ? "null" : Edo.util.JSON.encode(v));
                        a[a.length] = v === null ? "null" : Edo.util.JSON.encode(v);

                        b = true;
                }
            }
            //a.push("]");
            a[a.length] = ']';
            return a.join("");
        };

    var encodeDate = function (o) {
        //return 'new Date('+o.getTime()+')';

        return '"' + o.getFullYear() + "-" +
                pad(o.getMonth() + 1) + "-" +
                pad(o.getDate()) + "T" +
                pad(o.getHours()) + ":" +
                pad(o.getMinutes()) + ":" +
                pad(o.getSeconds()) + '"';
    };

    this.encodeDate = encodeDate;

    this.encode = function () {
        var ec;
        return function (o, dateFormat) {
            if (!ec) {
                // setup encoding function on first access
                ec = isNative() ? JSON.stringify : doEncode;
            }

            if (dateFormat) this.encodeDate = dateFormat;

            var s = ec(o);

            if (dateFormat) this.encodeDate = encodeDate;

            return s;
        };
    } ();

    this.decode = function () {
        var dc;
        var re = /[\"\'](\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})[\"\']/g;
        return function (json) {
            json = json.replace(re, "new Date($1,$2-1,$3,$4,$5,$6)");
            if (!dc) {
                // setup decoding function on first access
                dc = isNative() ? JSON.parse : doDecode;
            }
            return dc(json);
        };
    } ();

})();
Edo.util.Json = Edo.util.JSON;
function XMLDOM(markup) {
    if (!window.DOMParser) {
        var progIDs = ['Msxml2.DOMDocument.3.0', 'Msxml2.DOMDocument'];
        for (var i = 0; i < progIDs.length; i++) {
            try {
                var xmlDOM = new ActiveXObject(progIDs[i]);
                xmlDOM.async = false;
                xmlDOM.loadXML(markup);
                xmlDOM.setProperty('SelectionLanguage', 'XPath');
                return xmlDOM;
            }
            catch (ex) {
            }
        }
        return null;
    }
    else {
        try {
            var domParser = new window.DOMParser();
            return domParser.parseFromString(markup, 'text/xml');
        } catch (ex) {
            return null;
        }
    }
    return null;
}
Edo.util.XML = {
    version: '<?xml version="1.0" standalone="no"?>',
    useCDATA: false,
    dateFormat: function (o) {
        return o.getFullYear() + "-" + (o.getMonth() + 1) + "-" + o.getDate() + "T" + o.getHours() + ":" + o.getMinutes() + ":" + o.getSeconds();
    },
    //将JS对象转XML
    encode: function (obj) {
        var typeFn = function (o) {
            if (o === undefined || o === null) {
                return false;
            }
            var t = typeof o;
            if (t == 'object' || t == 'function') {
                switch (o.constructor) {
                    case Array: return 'array';
                    case RegExp: return 'regexp';
                    case Date: return 'date';
                }
            }
            return t;
        }
        var dateFormat = this.dateFormat;
        var useCDATA = this.useCDATA;

        var sb = [];

        function toXml(o, p) {
            var type = typeFn(o);

            switch (type) {
                case 'object':
                    for (var p in o) {
                        var value = o[p];

                        var vtype = typeFn(value);
                        if (vtype == 'function' || vtype == 'regexp') continue;

                        var isArray = vtype == 'array';
                        for (var i = 0, l = isArray ? value.length : 1; i < l; i++) {
                            var v = isArray ? value[i] : value;

                            sb[sb.length] = '<';
                            sb[sb.length] = p;

                            var hasChild = false;

                            //这里应该处理属性, @开头
                            if (typeFn(v) == 'object') {
                                for (var pp in v) {
                                    if (pp.indexOf('@') == 0) {    //属性操作

                                        sb[sb.length] = ' ';
                                        sb[sb.length] = pp.substr(1);
                                        sb[sb.length] = '="';
                                        sb[sb.length] = v[pp];
                                        sb[sb.length] = '"';

                                        delete v[pp];
                                    } else {
                                        hasChild = true;
                                    }
                                }

                            } else {
                                hasChild = true;
                            }

                            if (hasChild) {
                                sb[sb.length] = '>';

                                toXml(v, p);

                                sb[sb.length] = '</';
                                sb[sb.length] = p;
                                sb[sb.length] = '>';
                            } else {
                                sb[sb.length] = '/>';
                            }
                        }
                    }
                    break;
                case 'string':

                    if (!useCDATA && (o.indexOf('<') == -1 && o.indexOf('>') == -1)) {
                        sb[sb.length] = o;
                    } else {
                        sb[sb.length] = '<![CDATA[';
                        sb[sb.length] = o;
                        sb[sb.length] = ']]>';
                    }
                    break;
                case 'date':
                    sb[sb.length] = dateFormat(o);
                    break
                default:
                    sb[sb.length] = o;
                    break;
            }
        }

        sb[sb.length] = this.version;
        toXml(obj);

        var xml = sb.join('');
        return xml;
    },
    //将XML转JS对象
    decode: function (xml) {
        if (!xml) return null;
        if (typeof xml !== 'string') return null;
        xml = xml.trim();
        if (xml.indexOf('<') != 0) return null;

        xml = new XMLDOM(xml);

        if (!xml) return null;

        var root = xml.documentElement;
        if (!root || root.tagName == 'parsererror') return null;

        function filterNodes(cs) {
            var children = [];
            for (var i = 0, l = cs.length; i < l; i++) {
                var c = cs[i];
                var nt = c.nodeType;
                if (nt == 1 || nt == 4) {
                    children[children.length] = c;
                }
            }
            cs = children.length > 0 ? children : cs;
            return cs;
        }
        var toString = Object.prototype.toString;
        function parseNode(n, o) {
            var as = n.attributes || [];
            var i = 0, l = as.length;
            var cs = filterNodes(n.childNodes);
            if (l == 0 && cs.length == 0) return '';

            o = o || {};
            //1)attributes            
            while (i < l) {
                //for(; i<l; i++){
                var a = as[i];
                o['@' + a.nodeName] = a.nodeValue;

                i++;
            }
            //2)childNodes                        
            var i = 0, l = cs.length;
            while (i < l) {
                //for(var i=0, l=cs.length; i<l; i++){
                var c = cs[i];

                var name = c.nodeName;

                //                if(nomatch && nomatch[name]) {
                //                    i++;
                //                    continue;
                //                }

                var nt = c.nodeType;

                if (nt != 1) {//if(c.nodeType ==3 || c.nodeType == 4) {
                    return c.nodeValue;
                } else if (nt == 1) {
                    var value = parseNode(c);
                    var old = o[name];
                    if (!old) o[name] = value;
                    else if (old) {
                        //if(!(old instanceof Array)) {
                        //if(!(toString.apply(old) === '[object Array]')){
                        if (!old.push) {
                            o[name] = [old];
                        }
                        o[name][o[name].length] = value;
                    }
                }

                i++;
            }
            return o;
        }

        var json = parseNode(xml);
        return json;
    }
};
Edo.util.XmlToJson = Edo.util.XML.decode;
Edo.util.JSONToXml = Edo.util.XML.encode;



Edo.util.Anim = function (options) {
    this.options = Edo.apply({
        onStart: Edo.emptyFn,
        onStep: Edo.emptyFn,
        onComplete: Edo.emptyFn,

        //动画算法
        transition: function (p) {
            return -(Math.cos(Math.PI * p) - 1) / 2;
        }, //Edo.util.Anim.Transitions.Back.easeIn,
        //动画周期
        duration: 500,
        //动画频率
        fps: 50
    }, options);
}
Edo.util.Anim.prototype = {
    start: function (time) {              //开始
        this.stopTimer();
        this.time = new Date().getTime() - (time || 0);

        this.options.onStart();
        this.timer = setInterval(this.step.bind(this), Math.round(1000 / this.options.fps));
    },
    stop: function (cancel) {
        this.stopTimer();

        this.options.onComplete(cancel);
        var t = new Date().getTime() - this.time; //完成的时间长度
        this.time = null;

        return t;
    },
    step: function () {
        var time = new Date().getTime();
        var s = this.options.onStep;
        if (time < this.time + this.options.duration) {
            //根据时间的完成度,使用动画算法函数,得到一个效果的完成度delta
            var delta = this.options.transition((time - this.time) / this.options.duration);

            s(delta);
        } else {
            s(1);
            this.stop(false);
        }
    },
    stopTimer: function () {
        clearInterval(this.timer);
        this.timer = null;
    }
}
Edo.util.Anim.compute = function (from, to, delta) {
    return (to - from) * delta + from;
};

Edo.util.Anim.parseColorArray = function (v) {
    if (v instanceof Array) return v;
    else if (v.contains('rgb')) {
        v = v.split('rgb').splice(1, 4).map(function (color) {
            return color.rgbToHex();
        }).join(' ');
    }
    return v.hexToRgb(true);
}
Edo.util.Anim.getStyle = function (el, p) {
    var v = Edo.util.Dom.getStyle(el, p);
    if (p.contains('olor')) {
        while (v == 'transparent' && p != document) {
            el = el.parentNode;
            v = Edo.util.Dom.getStyle(el, p);
        }
        if (v == 'transparent') {
            v = '#fff';
        }
    }
    return v;
}

//动画算法
Edo.util.Anim.Transition = function (transition, params) {
    params = params ? (params instanceof Array ? params : [params]) : [];
    return Edo.apply(transition, {
        easeIn: function (pos) {
            return transition(pos, params);
        },
        easeOut: function (pos) {
            return 1 - transition(1 - pos, params);
        },
        easeInOut: function (pos) {
            return (pos <= 0.5) ? transition(2 * pos, params) / 2 : (2 - transition(2 * (1 - pos), params)) / 2;
        }
    });
};

Edo.util.Anim.Transitions = {
    linear: function () {
        return arguments[0];
    }
};

Edo.util.Anim.Transitions.extend = function (transitions) {
    for (var transition in transitions) Edo.util.Anim.Transitions[transition] = new Edo.util.Anim.Transition(transitions[transition]);
};
Edo.util.Anim.Transitions.extend({
    Pow: function (p, x) {
        return Math.pow(p, x[0] || 6);
    },

    Expo: function (p) {
        return Math.pow(2, 8 * (p - 1));
    },

    Circ: function (p) {
        return 1 - Math.sin(Math.acos(p));
    },

    Sine: function (p) {
        return 1 - Math.sin((1 - p) * Math.PI / 2);
    },

    Back: function (p, x) {
        x = x[0] || 1.618;
        return Math.pow(p, 2) * ((x + 1) * p - x);
    },

    Bounce: function (p) {
        var value;
        for (var a = 0, b = 1; 1; a += b, b /= 2) {
            if (p >= (7 - 4 * a) / 11) {
                value = -Math.pow((11 - 6 * a - 11 * p) / 4, 2) + b * b;
                break;
            }
        }
        return value;
    },

    Elastic: function (p, x) {
        return Math.pow(2, 10 * --p) * Math.cos(20 * p * Math.PI * (x[0] || 1) / 3);
    }

});

['Quad', 'Cubic', 'Quart', 'Quint'].each(function (transition, i) {
    Edo.util.Anim.Transitions[transition] = new Edo.util.Anim.Transition(function (p) {
        return Math.pow(p, [i + 2]);
    });
});

Edo.util.Fx.Base = function (options) {
    this.options = Edo.apply({
        onStart: this.onStart.bind(this),
        onStep: this.onStep.bind(this),
        onComplete: this.onComplete.bind(this),
        //transition: Anim.Transitions.Back.easeIn,
        duration: 500,
        fps: 50,
        unit: 'px',
        wait: true
    }, options);

    this.addEvents(
        'start',
        'step',
        'complete',
        'chaincomplete'
    );
    Edo.util.Fx.Base.superclass.constructor.call(this);

    this.addListeners(this.options.listeners);
}
Edo.util.Fx.Base.extend(Edo.util.Observable, {//覆盖基类,要让子类能覆盖
    chain: function (fn) {
        this.chains = this.chains || [];
        this.chains.push(fn);
        return this;
    },
    callChain: function () {
        if (this.chains && this.chains.length) {
            var c = this.chains.shift()//.defer(1, this);
            c.call(this);
        }
        return this;
    },
    clearChain: function () {
        this.chains = [];
        return this;
    },
    hasChain: function () {
        return this.chains && this.chains.length > 0;
    },
    start: function (o) {
        //wait为true,则表示一定将当前效果执行完后才可以继续
        if (this.anim && this.options.wait) return false;
        this.stop();

        this.fromTo = o || {};

        this.anim = new Edo.util.Anim(this.options);
        this.anim.start();
        return this;
    },
    stop: function () {
        if (this.anim) {
            var t = this.anim.stop();
            this.anim = null;
            return t;
        }
    },
    compute: function (delta) {
        var fromTo = this.fromTo;
        for (var p in fromTo) {
            var v = fromTo[p];
            var value = v[0];
            v[1] = v[1] || this.options.unit;
            if (value[0] instanceof Array) {
                var now = [];
                for (var i = 0, l = value[0].length; i < l; i++) {
                    now[i] = parseInt(Edo.util.Anim.compute(value[0][i], value[1][i], delta));
                    if (now[i] < 0) now[i] = 0;
                    if (now[i] > 255) now[i] = 255;
                }
                value[2] = now;
            } else {
                value[2] = Edo.util.Anim.compute(value[0], value[1], delta);
            }
        }
        return fromTo;
    },

    onStart: function () {
        this.fireEvent('start', this);
    },
    onStep: function (delta) {
        this.fromTo = this.compute(delta);
        this.fireEvent('step', delta, this);
    },
    onComplete: function (cancel) {
        this.fireEvent('complete', cancel, this);
        this.anim = null;
        if (this.hasChain()) {
            this.callChain();
        } else {
            this.fireEvent('chaincomplete', cancel, this);
        }
    }
});


Edo.util.Fx.Style = function () { };
Edo.util.Fx.Style.extend(Edo.util.Fx.Base, {
    onStart: function () {
        //如果没有配置初始值,则从元素上获取此值  
        var ft = this.fromTo;
        var el = this.options.el;

        for (var p in ft) {
            var v = ft[p];
            //if(p == 'color') debugger
            var from = Edo.util.Anim.getStyle(this.options.el, p);

            if (!(v instanceof Array)) ft[p] = v = [v];  //#附加的

            if (!(v[0] instanceof Array)) {
                v[0] = [from, v[0]];
            } else if (v[0].length < 2) {
                v[0] = [from, v[0][0]];
            }

            var value = v[0];
            if (p.contains('olor')) {
                value[0] = Edo.util.Anim.parseColorArray(value[0]);
                value[1] = Edo.util.Anim.parseColorArray(value[1]);
            } else {
                value[0] = parseFloat(value[0]);
                value[1] = parseFloat(value[1]);
            }
        }
        Edo.util.Fx.Style.superclass.onStart.call(this);
    },
    onStep: function (delta) {
        Edo.util.Fx.Style.superclass.onStep.call(this, delta);

        var ft = this.fromTo;
        var el = this.options.el;
        var el = this.options.el;
        for (var p in ft) {
            var v = ft[p];
            var value = v[0][2];
            if (p.contains('olor')) {
                value = value.rgbToHex();
            } else {
                value += v[1];
            }
            Edo.util.Dom.setStyle(el, p, value);
        }
    }
});



Edo.util.Template = function (tpl) {
    if (tpl) this.set(tpl);
}
Edo.util.Template.prototype = {
    tagRe: /(?:<%)((\n|\r|.)*?)(?:%>)/ig,

    load: function (o) {
        if (typeof o == 'string') o = { url: o };
        o = Edo.apply({
            url: '',
            type: 'get',
            //cache: true,
            async: false,
            onSuccess: this.onSuccess.bind(this),
            onFail: this.onFail.bind(this)
        }, o);
        Edo.util.Ajax.request(o);
        return this;
    },
    onSuccess: function (text) {

        this.set(text);
    },
    onFail: function (code) {

    },


    set: function (tpl) {
        if (tpl === null || tpl === undefined) tpl = '';
        this.tpl = tpl;

        var evals = [];
        var re = this.tagRe;

        var ret;
        while ((ret = re.exec(tpl)) != null) {
            evals[evals.length] = ret;
        }

        this.fnId = '___TEMPLATE_FN_' + new Date().getTime() + Edo.util.Template.id++;
        var sb = ['window["' + this.fnId + '"] = function (sArr, fnName, args){ ',
                    '\nvar _ = [];var __;\nif(fnName) {eval(fnName+".apply(this, args)"); return _.join("")};'
                    ];

        var start = 0, str, evstr, strArr = [];

        for (var i = 0, l = evals.length; i < l; i++) {
            var ev = evals[i];

            str = tpl.substring(start, ev.index);



            if (str) {
                var len = strArr.length;
                strArr[len] = str;
                sb[sb.length] = "\n_[_.length] = sArr[" + len + "]";
            }
            evstr = ev[1];

            if (evstr.charAt(0) == '=') {
                sb[sb.length] = "\n__" + evstr;
                sb[sb.length] = "\n_[_.length]=__";
            } else {
                sb[sb.length] = "\n" + evstr;
            }

            start = ev.index + ev[0].length; // ev.lastIndex;
        }
        str = tpl.substring(start, tpl.length);
        if (str) {
            var len = strArr.length;
            strArr[len] = str;
            sb[sb.length] = "\n_[_.length] = sArr[" + len + "]";
        }

        sb[sb.length] = '\nreturn _.join("");\n}';
        var fn = sb.join('');

        this.strArr = strArr;

        eval(fn);
        return this;
    },
    call: function (name) {
        if (this.fnId && name) {
            var args = Array.apply(null, arguments);
            args.shift();
            return window[this.fnId].call(null, this.strArr, name, args);
        }
    },

    run: function (data) {
        if (this.fnId) {

            return window[this.fnId].call(data, this.strArr);
        }
    }
}
Edo.util.Template.id = 1000;
Edo.util.TextMetrics = {
    measure: function (el, text, fixedWidth) {
        if (!this.shared) {
            this.shared = Edo.util.TextMetrics.Instance(el, fixedWidth);
        }
        this.shared.bind(el);
        this.shared.setFixedWidth(fixedWidth || 'auto');
        return this.shared.getSize(text);
    },
    createInstance: function (el, fixedWidth) {
        return Edo.util.TextMetrics.Instance(el, fixedWidth);
    },
    Instance: function (bindTo, fixedWidth) {
        var ml = document.createElement('div');
        document.body.appendChild(ml);
        ml.style.cssText = 'position:absolute;left:-1000px;top:-1000px;visibility:hidden;';

        var dh = Edo.util.Dom;
        if (fixedWidth) {
            dh.setWidth(ml, fixedWidth);
        }
        var instance = {
            getSize: function (text) {
                ml.innerHTML = text;
                var s = dh.getSize(ml);
                //ml.innerHTML = '';
                return s;
            },
            bind: function (el) {
                if (typeof el === 'string') {
                    ml.className = el;  //���lass,style
                } else {
                    dh.setStyle(ml, dh.getStyles(el, 'font-size', 'font-style', 'font-weight', 'font-family', 'line-height', 'text-transform', 'letter-spacing'));
                }
            },
            setFixedWidth: function (width) {
                dh.setWidth(ml, width);
            },
            getWidth: function (text) {
                ml.style.width = 'auto';
                ml.innerHTML = text;
                return dh.getWidth(ml);
            },
            getHeight: function (text) {
                ml.innerHTML = text;
                return dh.getHeight(ml);
            }
        };
        instance.bind(bindTo);
        return instance;
    }
};


var History;
(function () {
    var iframe, hiddenField;
    var ready = false;
    var currentToken;

    function getHash() {
        var href = top.location.href, i = href.indexOf('#');
        return i >= 0 ? href.substr(i + 1) : null;
    }
    function doSave() {
        hiddenField.value = currentToken;
    }
    function handleStateChange(token) {
        currentToken = token;
        History.fireEvent('change', token);
    }
    function updateIFrame(token) {
        //if(History.doWork){         //控制是否增加历史记录
        var html = ['<html><body><div id="state">', token, '</div><script type="text/javascript">try{document.domain="' + document.domain + '";}catch(e){}</script></body></html>'].join('');
        try {
            var doc = iframe.contentWindow.document;
            doc.open();
            doc.write(html);
            doc.close();
            return true;
        } catch (e) {
            return false;
        }
    }
    function checkIFrame() {
        try {
            if (!iframe.contentWindow || !iframe.contentWindow.document) {
                setTimeout(checkIFrame, 10);
                return;
            }
        } catch (e) {
            setTimeout(checkIFrame, 10);
            return;
        }

        var doc = iframe.contentWindow.document;
        var elem = doc.getElementById('state');
        var token = elem ? elem.innerText : null;

        var hash //= getHash();

        //定时检查hash标记,如果不一样,则激发历史change事件
        setInterval(function () {
            doc = iframe.contentWindow.document;
            elem = doc.getElementById('state');

            var newtoken = elem ? elem.innerText : null;

            var newHash = getHash();

            if (newtoken !== token) {
                token = newtoken;
                if (token === null) token = '';
                handleStateChange(token);
                top.location.hash = token;      //更新历史标记
                hash = token;
                doSave();
            } else if (newHash !== hash) {     //如果hash变化,则通过改变iframe,在IE下获得历史改变.在下个定时中,就可以得到token的不同,从而激发(缓了一次)
                hash = newHash;
                updateIFrame(newHash);
            }
        }, 25);

        ready = true;

        History.fireEvent('ready', History);
    }

    function startUp() {
        currentToken = hiddenField.value;
        if (isIE) {
            checkIFrame();
        } else {
            var hash //= getHash();
            setInterval(function () {
                var newHash = getHash();
                if (newHash !== hash) {
                    hash = newHash;
                    handleStateChange(hash);
                    doSave();
                }
            }, 30);
            ready = true;
            History.fireEvent('ready', History);
        }
    }

    History = {
        doWork: true,
        fieldId: 'history-field',
        iframeId: 'history-frame',
        init: function (proxy) {
            var el = document.createElement('form');
            el.id = "history-form";
            el.style.display = 'none';
            document.body.appendChild(el);
            var s = '';
            if (isIE) {
                s = '<iframe id="history-frame" src="' + proxy + '"></iframe>';
            }
            el.innerHTML = '<input type="hidden" id="history-field" />' + s;
            hiddenField = document.getElementById(this.fieldId);
            iframe = document.getElementById(this.iframeId);
            this.addEvents('change');
            startUp();
        },
        add: function (token, preventDup) {
            if (preventDup !== false) {
                if (this.getToken() == token) {
                    return true;
                }
            }
            if (isIE) {
                return updateIFrame(token);
            } else {
                top.location.hash = token;
                return true;
            }
        },
        back: function () {
            history.go(-1);
        },
        forward: function () {
            history.go(1);
        },
        getToken: function () {
            return ready ? currentToken : getHash();
        }
    }
    Edo.apply(History, new Edo.util.Observable());

    Edo.util.History = History;
})();





Edo.core.Component = function () {



    //Edo.util.Observable.call(this);
    this.events = {}
    this.propertyChangeListeners = this.events['propertychange'] = [];
    this.initListeners = this.events['init'] = [];
    //    this.destroyListeners = this.events['destroy'] = [];


    this.idSet = false;

    this.id = Edo.id(null, 'cmp');

    Edo.managers.SystemManager.all[this.id] = this;

    //应该还是要加入到组件对象池中!!!
    //Edo.regCmp(this);//这一步,在创建大量edo组件对象的时候,性能开销很大!

    this.plugins = [];

    this.type = this.getType();
}

Edo.core.Component.extend(Edo.util.Observable, {
    componentMode: 'component',


    get: function (name) {
        var g = this['_get' + name.charAt(0).toUpperCase() + name.substring(1, name.length)];
        if (g) {
            return g.call(this);
        } else {
            return this[name];
        }
    },

    set: function (o, value) {
        if (!o) return;
        if (typeof o == 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }
        var id = o.id;
        if (id) {
            this.setId(id);      //id
            delete o.id;
        }
        if (o.script) {
            Edo.globalEval(o.script);
            delete o.script;
        }
        //if(this.onSet) this.onSet(o);               

        //先处理事件和绑定
        for (var name in o) {
            //if(name.length > 2 && name.substring(0, 2).toLowerCase() == 'on'){
            if (name.indexOf('on') == 0) {
                var ___fn = o[name];
                if (typeof ___fn !== 'function') {
                    eval('___fn = function(e){' + ___fn + '}')
                }
                this.on(name.substring(2, name.length).toLowerCase(), ___fn);
                delete o[name];
            }
            //            else if(Binding.tryBind(v, this, name)){
            //                delete o[name];
            //            }
        }

        for (var name in o) {
            var v = o[name];
            var n = '_set' + name.charAt(0).toUpperCase() + name.substring(1, name.length);
            var s = this[n];
            if (s) {
                s.call(this, v);
            } else {
                this[name] = v;
                this.changeProperty(name, v);
            }
        }

        return this;
    },

    setId: function (id) {
        if (!id) throw new Error("id不能为空");
        if (this.idSet) throw new Error("不能重复设置ID");
        Edo.unregCmp(this);
        this.id = id;
        this.idSet = true;
        Edo.regCmp(this);
    },

    propertyChangeEvent: {
        type: 'propertychange'
    },
    changeProperty: function (p, v) {
        var ls = this.propertyChangeListeners;
        var l = ls.length;
        if (l > 0) {
            var e = this.propertyChangeEvent;
            e.source = this;
            e.property = p;
            e.value = v;
            for (var i = 0; i < l; i++) {
                var fn = ls[i];
                fn[0].call(fn[1], e);
            }
        }
        //this.fireEvent('propertychange',);
    },

    init: function () {
        if (this.inited) throw new Error();

        this.inited = true;

        if (this.plugins.length > 0) {
            for (var i = 0, l = this.plugins.length; i < l; i++) {
                this.plugins[i].init(this);
            }
        }

        this.fireEvent('init', {
            type: 'init',
            source: this
        });
        if (enableTrace) {
            _oninit.call(this);
        }
    },

    destroy: function () {

        Edo.unregCmp(this);
        this.clearEvent();

        this.plugins.each(function (o) {
            if (o.destroy && !o.destroyed) o.destroy(this);
        }, this);

        this.fireEvent("destroy", this);
        this.destroyed = true;
    },
    //
    _setPlugins: function (value) {
        this.addPlugin(value);
    },

    addPlugin: function (o) {
        if (!(o instanceof Array)) o = [o];
        for (var i = 0, l = o.length; i < l; i++) {
            var v = o[i];
            if (typeof v == 'string') v = Edo.create(v);
            o[i] = v;
        }
        this.plugins.addRange(o);

        if (this.inited)
            o.each(function (v) {
                v.init(this);
            }, this);
    },

    removePlugin: function (o) {
        if (typeof o === 'string') o = Edo.getCmp(o);
        if (o) {
            if (o.destroy) o.destroy();
            this.plugins.remove(o);
        }
    },

    removePluginByType: function (type) {
        var p = this.getPluginByType(type);
        this.removePlugin(p);
    },
    getPluginByType: function (type) {
        var t = typeof type == 'string' ? Edo.getType(type) : type;
        var rs = [];
        this.plugins.each(function (v) {
            if (v.isType(t)) {
                rs.add(v);
            }
        }, this);
        return rs[0];
    },

    getType: function () {
        return this.constructor.type;
    },

    isType: function (type, shallow) {
        if (typeof type != 'string') type = type.type;
        return !shallow ?
               ('/' + this.getTypes() + '/').indexOf('/' + type + '/') != -1 :
                this.constructor.type == type;
    },

    getTypes: function () {
        var tc = this.constructor;
        //if(tc.superclass && !tc.types) return this.getTypes();
        if (!tc.types) {
            var c = [], sc = this;
            while (sc) {
                if (sc.constructor.type) {
                    c.unshift(sc.constructor.type);
                }
                sc = sc.constructor.superclass;
            }
            tc.typeChain = c;
            tc.types = c.join('/');
        }
        return tc.types;
    }
});

Edo.core.Component.regType('component', 'cmp');

//var autoComponentTime = 0;
//var COUNT = 0;
//var sizeCount = 0;
//function _oninit(e){
//    trace('<b title="属性初始化完毕">[Event]</b> init: '+this.id+":"+this.type, 'color:red;font-weight:bold;');
//}
//function _oncreation(e){
//    trace('<b tiel="对象创建完毕,属性+尺寸都得到正确值,只是没有el">[Event]</b> creation: '+this.id+":"+this.type+":"+this.realWidth, 'color:blue;font-weight:bold;');
//}
//function _onrender(e){
//    trace('<b tiel="对象渲染完毕:一切就绪,el也生成了">[Event]</b> render: '+this.id+":"+this.type, 'color:green;font-weight:bold;');
//}
//function __createHtml(id){
//    trace('<b title="创建dom元素">createHtml</b>:'+id);
//}



Edo.core.UIComponent = function () {






































































    Edo.core.UIComponent.superclass.constructor.call(this);
    //_UIComponent_constructor.call(this);

    //    this.realWidth = this.minWidth;
    //    this.realHeight = this.minHeight;
    this.domEvents = {      //1:为绑定;2:待绑定;3:已绑定
        click: 1,
        dblclick: 1,
        mousedown: 1,
        mouseup: 1,
        mouseover: 1,
        mouseout: 1,
        mousemove: 1,
        focus: 1,
        blur: 1,
        keydown: 1,
        keyup: 1,
        mousewheel: 1,
        contextmenu: 1
    };

    this.topContainer = this;

    //    this.creationListeners = this.events['creation'] = [];
    //    this.renderListeners = this.events['render'] = [];
}


Edo.core.UIComponent.extend(Edo.core.Component, {
    componentMode: 'uicomponent',
    //判断是否是独立的组件
    //    isControlContainer: function(){
    //        if(this.componentMode == 'container') return true;
    //        if(this.parent) return this.parent.isContainer();
    //    },

    inited: false,
    created: false,
    rendered: false,

    autoWidth: false,   //自适应宽度
    autoHeight: false,  //自适应高度
    widthGeted: false,  //当autoWidth为false的时候,会马上生成组件对象的dom元素,来获取尺寸,widthGeted表示获取过了,下次计算的时候,不再继续获取
    heightGeted: false,



    //canResize: true,
    //调整布局
    relayout: function (type, value) {
        //TODO:判断如果topContainer.created !== true, 就不要做下面的事了,这样快速销毁        
        //if(this.rendered){
        if (this.created) {
            //trace('relayout "'+type+'":'+this.id);
            var e = {
                type: 'relayout',
                source: this,
                action: type,
                value: value
            };
            //if(this.id == 'box') debugger
            //激发一个relayout事件,表明有影响布局的属性被修改了
            this.fireEvent('relayout', e);
            //如果是顶级容器,或者是popup对象, 并且已经创建好, 则重新确定尺寸,并延迟调整dom
            if ((!this.parent || this.popup) && this.created) {
                //if(this.topContainer.rendered){
                //                if(this.rendered){
                //                    if(!this.count){
                //                        this.count = 1;
                //                    }else{
                //                        this.count++;
                //                    }
                //                    status = this.count;
                //                    this.doLayout();
                //                    this.syncSize();
                //                }else{
                //                    this.deferLayout();//延迟布局并syncSize
                //                }

                this.deferLayout(); //延迟布局并syncSize
            } else {
                //如果有父元素,并且不是popup,则通知父容器,响应处理
                if (this.parent && !this.popup) this.parent.onChildSizeChanged(e);
            }
            //###关键!!!这里是运行期布局调整的入口点!
        }
    },
    deferLayout: function () {
        //        this.doLayout();
        //        this.firstSyncSize = false;
        //        this.syncSize();

        if (this.sizetimer) clearTimeout(this.sizetimer);
        var sf = this;
        this.sizetimer = setTimeout(function () {

            sf.doLayout();
            sf.firstSyncSize = false;
            sf.syncSize();
            sf.sizetimer = null;
        }, 1);

    },
    //延迟执行syncSize方法,以便在多次修改尺寸值的时候,只需进行一次绘制!提升性能!
    deferSyncSize: function () {
        if (this.sizetimer) clearTimeout(this.sizetimer);
        var sf = this;
        this.sizetimer = setTimeout(function () {
            sf.syncSize();
            sf.sizetimer = null;
        }, 1);
    },
    syncSize: function () {
        //if(this.id == 'add') debugger        
        if (this.parent && !this.renderTo) {
            this.render(this.parent.scrollEl);
        }

        //trace('syncSize:'+this.id);
        var s = this.el.style;                    //###能优化下          



        var w = this.realWidth, h = this.realHeight;
        //if(this.type == 'text') debugger
        //if(this.id == 'edittask') debugger;
        if (this.mustSyncSize !== false) {

            //trace('doSyncSize:'+this.id);
            if (this.parent) {
                if (this.domWidth) w = this.domWidth;
                if (this.domHeight) h = this.domHeight;

                if (w < 0) w = 0;
                if (h < 0) h = 0;

                s.left = this.left + 'px';
                s.top = this.top + 'px';

                //                s.width = w  + 'px';        
                //                s.height = h  + 'px';        


            } else {
                //Edo.util.Dom.setSize(this.el, w, h);

            }

            Edo.util.Dom.setSize(this.el, w, h);

            //            if(this.autoHeight && this.autoWidth){
            //                
            //            }
            //            if(!this.autoWidth){
            //                Edo.util.Dom.setWidth(this.el, w);
            //            }
            //            if(!this.autoHeight){
            //                Edo.util.Dom.setHeight(this.el, h);
            //            }
        }

        this.mustSyncSize = true; //在第一次syncSize之后,每次都设置dom           

        if (this._fireSyncSize !== false) {
            this.fireEvent('syncsize', {
                type: 'syncsize',
                source: this
            });
            this._fireSyncSize = true;
        }

        //this.el.style.visibility = 'visible';
    },
    fixAutoSize: function () {
        if (this.autoWidth) {
            this.widthGeted = false;
            if (this.el) {
                this.el.style.width = 'auto';
                //this.el.style.visibility = 'hidden';
            }
        }
        if (this.autoHeight) {
            this.heightGeted = false;
            if (this.el) {
                this.el.style.height = 'auto';
                //this.el.style.visibility = 'hidden';
            }
        }
    },
    //限制尺寸:
    measureSize: function () {
        if (Edo.isInt(this.width)) this.realWidth = this.width;         //1)如果设置了明确的尺寸,则...
        if (Edo.isInt(this.height)) this.realHeight = this.height;

        if (!Edo.isInt(this.realWidth)) this.realWidth = this.defaultWidth;    //2)如果没有尺寸,则
        if (!Edo.isInt(this.realHeight)) this.realHeight = this.defaultHeight;

        //判断!!!错误!!!要仔细考虑布局逻辑!!!
        //        if(Edo.isPercent(this.width)) this.realWidth = this.minWidth;          //!!!如果为百分比设置,那就是最小尺寸!!!(逻辑不对!!!)
        //        if(Edo.isPercent(this.height)) this.realHeight = this.minHeight;        

        if (this.realWidth < this.minWidth) this.realWidth = this.minWidth;    //不管是什么尺寸设置,最终呈现的尺寸,必定不能小于最小尺寸!!!
        if (this.realHeight < this.minHeight) this.realHeight = this.minHeight;

        if (this.realWidth > this.maxWidth) this.realWidth = this.maxWidth;
        if (this.realHeight > this.maxHeight) this.realHeight = this.maxHeight;

        if (this.realWidth < 0) this.realWidth = 0;
        if (this.realHeight < 0) this.realHeight = 0;

        this.syncenableCollapse();
    },
    //估算尺寸:
    measure: function () {

        //trace('measure:'+this.id);
        //status = ++COUNT;
        //如果是自适应,则首先测量尺寸

        //        if(Edo.isInt(this.width)){
        //            this.realWidth = this.width;
        //        }else{
        //            this.realWidth = null;
        //        }
        //        if(Edo.isInt(this.height)){
        //            this.realHeight = this.height;
        //        }else{
        //            this.realHeight = null;
        //        }        

        //if(this.text == '确定22') debugger
        //if(this.type == 'button') debugger
        if (this.autoWidth && !Edo.isInt(this.width)) {
            //debugger
            if (!this.widthGeted) {  //暂放在widthGeted属性内

                //if(this.id == 'cmp14') debugger
                //var sss = new Date();

                this.doRender();    //确保有this.el


                var s = this.el.style;
                var p = s.position, h = s.height, overflow = s.overflow;
                s.position = 'absolute';
                s.overflow = '';
                //s.top = '-300px';
                s.width = 'auto';
                //s.height = 'auto';
                s.height = h;
                //this.widthGeted = Edo.util.Dom.getWidth(this.el);
                this.widthGeted = this.el.offsetWidth;
                //if(this.id == 'cmp4') alert(1);
                s.position = p;
                s.height = h;
                s.overflow = overflow;
                //s.top = t;               

                //s.width = '0px';

                //autoComponentTime += (new Date() - sss);
            }
            this.realWidth = this.widthGeted;
            this.mustSyncSize = true;
        }
        //if(this.text == '取消') debugger 
        if (this.autoHeight && !Edo.isInt(this.height)) {
            if (!this.heightGeted) {  //暂放在heightGeted属性内


                //if(this.type == 'dataview')debugger 
                var sss = new Date();
                this.doRender();    //确保有this.el                
                var s = this.el.style;
                var p = s.position, t = s.top, w = s.width, overflow = s.overflow;
                s.position = 'absolute';
                s.overflow = '';
                //s.top = '-300px';
                s.height = 'auto';
                //s.width = 'auto';
                s.width = w;
                //this.heightGeted = Edo.util.Dom.getHeight(this.el);
                this.heightGeted = this.el.offsetHeight;
                s.position = p;
                s.width = w;
                s.overflow = overflow;
                //s.top = t;

                //s.height = '0px';

                //autoComponentTime += (new Date() - sss);
            }
            this.realHeight = this.heightGeted;
            this.mustSyncSize = true;
        }

        this.measureSize();
    },
    layouted: false,
    //实际尺寸:
    doLayout: function (must, measure) {

        //如果没有强制计算布局,并且自身不是的顶级元素
        if (must !== true && this.topContainer != this) {
            this.topContainer.doLayout();
        } else {
            if (measure !== false) this.measure();
        }
        //if(!this.mustSyncSize) this.mustSyncSize = this.rendered;        
        this.mustSyncSize = true;

        this.layouted = true;
        //trace('doLayout:'+this.id);

    },
    //innerHTML
    getInnerHtml: function (sb) {
        if (this.html) sb[sb.length] = this.html;
    },
    //outerHTML
    htmlType: 'div',
    createHtml: function (w, h, appendArray) {

        if (!w) {
            if (Edo.isInt(this.width)) {
                w = this.width;
            } else {
                w = this.defaultWidth;
            }
        }
        if (!h) {
            if (Edo.isInt(this.height)) {
                h = this.height;
            } else {
                h = this.defaultHeight;
            }
        }

        if (this.el) throw new Error();

        var sb = appendArray || [];

        var clsIndex = -1;

        var cls = this.cls || '';
        //if(!this.isEnable()) cls += ' '+this.disabledClass;          
        if (!this.enable) cls += ' ' + this.disabledClass;

        if (this.shadow) {
            cls += ' e-shadow';
        }
        //if(this.text == '取消') debugger 
        sb[sb.length] = '<' + this.htmlType + ' id="';
        sb[sb.length] = this.id;
        sb[sb.length] = '" class="e-div ';
        sb[sb.length] = cls;
        sb[sb.length] = ' ';
        clsIndex = sb.length;
        sb[clsIndex] = '';
        //sb[sb.length] = '" style=";overflow:hidden;margin:0px;padding:0px;border:0;';
        sb[sb.length] = '" style=";overflow:hidden;';

        //style拼接处理

        //控制是否设置滚动条(组件不需要,但是Container需要)
        //if(this.renderScrollPolicy) sb[sb.length] = ';'+this.doScrollPolicy();
        if (!this.parent && this.componentMode == 'container') {
            //这里的优化!没有得到实际数据的体现!        鸡肋!!!
            //w = h = 0;      //如果是顶级容器,首先将尺寸约束为0,这样,会好一些,这也是一个优化技巧!                        
            sb[sb.length] = ';visibility:hidden';   //这一行还是有用的!!!
        }

        //if(this.sizeSet){
        sb[sb.length] = ';width:';
        if (this.width == 'auto') sb[sb.length] = 'auto';
        else {
            sb[sb.length] = w;
            sb[sb.length] = 'px';
        }

        sb[sb.length] = ';height:';
        if (this.height == 'auto') sb[sb.length] = 'auto';
        else {
            sb[sb.length] = h;
            sb[sb.length] = 'px';
        }
        //}
        if (!this.visible) sb[sb.length] = ';display:none';
        if (Edo.isValue(this.left)) {
            sb[sb.length] = ';left:';
            sb[sb.length] = this.left;
            sb[sb.length] = 'px';
        }
        if (Edo.isValue(this.top)) {
            sb[sb.length] = ';top:';
            sb[sb.length] = this.top;
            sb[sb.length] = 'px';
        }

        if (this.parent) sb[sb.length] = ';position:absolute;';
        else sb[sb.length] = ';position:relative;';

        sb[sb.length] = this.style;

        sb[sb.length] = '">';
        this.getInnerHtml(sb);
        sb[sb.length] = '</' + this.htmlType + '>';

        sb[clsIndex] = this.elCls;

        //return '<div id="'+this.id+'" class="'+cls+' '+this.elCls+'" style="'+s+'">'+html+'</div>';
        //return appendArray ? null : sb.join('');//如果有appendArray,则不用join
        if (!appendArray) return sb.join('');
    },

    createChildren: function (el) {
        this.el = el;
        this.scrollEl = this.el;

        //        if(this.el.parentNode != render){
        //            render.appendChild(el);
        //        }

        if (!Edo.core.UIComponent.ied) {
            Edo.core.UIComponent.prototype.i();
            Edo.core.UIComponent.ied = true;
        }
    },

    creation: function () {
        if (this.created) throw new Error();

        this.created = true;
        this.fireEvent('creation', {
            type: 'creation',
            source: this
        });

        if (enableTrace) {
            //_oncreation.call(this);
        }
    },

    initEvents: function () {
        this.on('splitclick', this._onSplitClick, this);

        //优化事件处理:1)在明确有on事件后,才绑定;2)监听全局处理,对单个元素进行        
        this._doHandleMouseEvent();



    },
    //生成dom元素! 拼接+append+creationChildren
    doRender: function (el) {

        if (this.el) return;
        //生成组件HTML,并呈现到界面
        //var sss = new Date();      
        if (!el) {
            var html = this.createHtml(this.realWidth, this.realHeight);

            var render = this.renderTo || Edo.getCt();

            el = Edo.util.Dom.append(render, html);
        }
        //alert(new Date() - sss);

        //设置组件对el的引用
        this.createChildren(el);

        //初始化事件
        this.initEvents();
    },
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1.render(dom, apply); 2.createChildren子元素调用时机没有

    render: function (dom) {

        if (typeof dom === 'string') {
            if (dom == "#body") dom = document.body;
            else dom = Edo.getDom(dom);
        }

        var np = this.el ? this.el.parentNode : this.renderTo;

        if (np == dom && this.rendered) return;

        //init初始化:属性设置完毕
        if (!this.inited) this.init();

        if (!this.parent) this.topContainer = this;

        var sss = new Date();
        //在render的时候,必须要doLayout,计算出尺寸,用于第一次生成html=>el
        if (!this.parent) {
            this.doLayout();
        }
        //        if(!this.parent){
        //                alert(new Date() - sss)
        //            }
        if (!this.created) {// && !this.parent            
            //creation创建完成: 1.属性设置完毕; 2.得到最终尺寸
            this.creation();
        }

        this.renderTo = dom;


        //if(!this.rendered){
        //!!!这里应该确定下布局后,元素的尺寸和坐标!!!

        //1>生成dom元素:    1.得到
        if (!this.el) this.doRender();   //

        //2>具备el之后,给el绑定dom事件             

        if (!this.parent) {

            //this.syncSize();    //调整尺寸(应该可以设置,相同属性的话,不会有开销;如果有padding,border可以做出修正!
            //肯定要延迟syncSize!!!
            this.deferSyncSize();    //调整尺寸(这里延迟,仅仅只是调整dom尺寸,并没有调整对象尺寸布局逻辑!!!)
        }

        //}                

        //        if(this.parent){
        //            var pnode = this.parent.scrollEl;
        //            if(this.el.parentNode != pnode){
        //                pnode.appendChild(this.el);                                                
        //            }            
        //            this.renderTo = pnode;
        //        }
        if (this.el.parentNode != this.renderTo) {
            this.renderTo.appendChild(this.el);
            var s = this.el.style;
            s.width = (this.realWidth || this.defaultWidth) + 'px';
            s.height = (this.realHeight || this.defaultHeight) + 'px';
        }

        //this.setXY(this.x, this.y);   //默认!创建的时候不设置xy,如果一定要设置,在creation事件内处理        

        this.rendered = true;           //    
        this.fireEvent('render', {
            type: 'render',
            source: this
        });
        if (enableTrace) {
            //_onrender.call(this);
        }


        //design
        if (this.design && !this.el.design) {
            this.el.design = this.design;   //标明此组件是edo design组件

            //if(!this.isType('ct') || this.type == 'datepicker'){         //如果不是容器类组件,则加上cover
            if (this.componentMode != 'container') {

                this.cover = Edo.util.Dom.append(this.scrollEl, '<div unselectable="on" class="e-unselectable selected" style="background-color:black;z-index:99;"></div>');
                Edo.util.Dom.setOpacity(this.cover, 0);
            } else {
                Edo.managers.DragManager.regDrop(this);
            }
        }
    },
    destroy: function (sysDestroy) {
        Edo.managers.PopupManager.removePopup(this);
        Edo.managers.ResizeManager.unreg(this);
        Edo.managers.TipManager.unreg(this);

        this.rendered = this.created = this.inited = false;

        if (this.parent && this.removeFromParent !== false) {
            this.parent.removeChild(this, false, !sysDestroy);
        }

        Edo.util.Dom.clearEvent(this.el);

        if (!this.parent) {
            //this.el.innerHTML = '';
            Edo.util.Dom.remove(this.el, true);
        }
        this.topContainer = this.parent = this.el = null;

        Edo.core.UIComponent.superclass.destroy.call(this);
    },

    //这些属性都会影响尺寸



    width: 'auto',

    height: 'auto',

    minWidth: 20,

    minHeight: 22,


    maxWidth: 100000,


    maxHeight: 100000,

    defaultWidth: 20,

    defaultHeight: 22,

    visible: true,

    enable: true,

    verticalScrollPolicy: 'off',

    horizontalScrollPolicy: 'off',

    cls: '',

    style: '',

    html: '',

    elCls: '',
    disabledClass: "e-disabled",

    shadow: false,


    isDisplay: function () {
        if (this.visible == false) return false;
        if (this.parent) return this.parent.isDisplay();
        return true;
    },

    isEnable: function () {
        if (this.enable == false) return false;
        if (this.parent) return this.parent.isEnable();
        if (this.owner) return this.owner.isEnable();
        return true;
    },
    isReadOnly: function () {
        if (this.isEnable() == false) return true;
        if (this.readOnly == true) return true;
        if (this.parent) return this.parent.isReadOnly();
        if (this.owner) return this.owner.isReadOnly();
        return false;
    },


    //控制,是否在el上,使用滚动条样式设置
    //renderScrollPolicy: true,

    set: function (o, value) {
        if (!o) return;

        if (typeof o === 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }

        var renderTo = o.renderTo || o.render;

        delete o.renderTo;
        delete o.render;

        var children = o.children;
        delete o.children;

        Edo.core.UIComponent.superclass.set.call(this, o);


        if (children && this.setChildren) {
            this.setChildren(children);
        }


        //这里measure和creation的开销,1000对象110毫秒,应该是减少不了了,估算尺寸逻辑还是非常复杂的        
        //if(!this.parent) 

        var sss = new Date();
        if (renderTo) {
            this.render(renderTo);
        }
        //if(!this.parent) alert(new Date() - sss)
        //        if(!this.parent){  
        //            setTimeout(function(){          
        //            alert(new Date() - sss)
        //            }, 10);
        //        }
        return this;
    },
    _doHandleMouseEvent: function () {
        if (this.el) {
            for (var name in this.domEvents) {        //当有dom事件监听时,只绑定一次!没有的话,则不绑定            
                if (this.domEvents[name] == 2) {
                    this.domEvents[name] = 3;

                    Edo.util.Dom.on(this.el, name, this._onMouseEvent, this);
                }
            }
        }
    },
    on: function (name, fn, scope, index) {
        Edo.core.UIComponent.superclass.addListener.call(this, name, fn, scope, index);
        if (this.domEvents[name] == 1) this.domEvents[name] = 2;

        if (this.el) {
            this._doHandleMouseEvent();
        }
    },

    _onMouseEvent: function (e) {
        if (!this.isEnable()) return;
        e.source = this;                //所有组件激发事件的目标对象,都是source,dom元素是target        
        if (!this.design || this.designview) {
            this.fireEvent(e.type, e);
        }
    },
    //是否执行默认的splitclick逻辑(折叠自身)
    defaultSplitClick: true,
    _onSplitClick: function (e) {
        if (this.defaultSplitClick === true) {
            this.toggle();
        }
    },

    enableCollapse: false,     //是否可折叠

    expanded: true,         //默认是展开状态

    collapseHeight: 0,

    collapseWidth: 0,
    collapseCls: 'e-collapse',

    collapseProperty: 'height',

    collapse: function () {

        if (!this.enableCollapse) return;
        if (this.expanded) {
            //            this._minHeight = this.minHeight;   //暂存最小尺寸
            //            this.minHeight = this.collapseHeight;
            if (this.fireEvent('beforetoggle', {
                type: 'beforetoggle',
                expanded: this.expanded,
                source: this
            }) === false) return;

            this.expanded = false;
            this.relayout('expanded', this.expanded);
            if (this.created) {
                Edo.util.Dom.addClass(this.el, this.collapseCls);
                Edo.util.Dom.addClass(this.el, this.collapseCls + "-" + this.collapseProperty);
            } else {
                this.elCls += ' ' + this.collapseCls;
                this.elCls += ' ' + this.collapseCls + "-" + this.collapseProperty;
            }

            this.fireEvent('toggle', {
                type: 'toggle',
                expanded: this.expanded,
                source: this
            });
        }
    },

    expand: function () {
        if (!this.enableCollapse) return;
        if (!this.expanded) {
            if (this.fireEvent('beforetoggle', {
                type: 'beforetoggle',
                expanded: this.expanded,
                source: this
            }) === false) return;
            //this.minHeight = this._minHeight;   //还原最小尺寸

            this.expanded = true;
            this.relayout('expanded', this.expanded);
            if (this.created) {
                Edo.util.Dom.removeClass(this.el, this.collapseCls);
                Edo.util.Dom.removeClass(this.el, this.collapseCls + "-" + this.collapseProperty);
            }

            this.fireEvent('toggle', {
                type: 'toggle',
                expanded: this.expanded,
                source: this
            });
        }
    },

    toggle: function () {
        this[this.expanded ? 'collapse' : 'expand']();
    },
    _setExpanded: function (value) {
        if (this.expanded != value) {
            this.expanded = value;
            this[value ? 'expand' : 'collapse']();
        }
    },
    //同步折叠状态
    syncenableCollapse: function () {

        if (this.enableCollapse) {   //如果当前是允许折叠的,则需要做一下特殊处理        
            var cp = this.collapseProperty;
            if (this.expanded) {
                if (this.__collapse) {
                    this[cp] = this.__collapse;
                    this.__collapse = null;
                }
            } else {
                var s = cp.substr(0, 1).toUpperCase() + cp.substring(1);
                this['real' + s] = this['collapse' + s]//this.collapseHeight; //如果是collapse状态,则使用collapse属性
                //并且,将尺寸设置改成绝对值(这样布局器就不会按百分比处理),并保存原来的尺寸值
                if (!Edo.isInt(this[cp])) {
                    this.__collapse = this[cp];
                    this[cp] = this['real' + s];
                }
            }
        }
    },

    focus: function () {
        //Edo.util.Dom.focus(this.el);

        //        this.fireEvent('focus', {
        //            type: 'focus',
        //            souce: this
        //        });
    },

    blur: function () {
        //Edo.util.Dom.blur(this.el);

        //this.fireEvent('blur', e);
    },
    within: function (e) {                //传递一个事件对象,判断是否在此控件的点击区域内.
        //        var r = this.fireEvent('within', {
        //            type: 'within',
        //            source: this,
        //            event: e
        //        });
        //return e.within(this.el) && r !== false;
        return e.within(this.el, e.type == 'blur');
    },
    //----------------------------------    尺寸相关     --------------------------------//
    _setShadow: function (v) {
        if (this.shadow != v) {
            this.shadow = v;
            if (v) {
                this.addCls('e-shadow');
            } else {
                this.removeCls('e-shadow');
            }
        }
    },

    _setSize: function (size) {
        if (!size) return;
        if (Edo.isArray(size)) {
            size.width = size[0];
            size.height = size[1];
        }
        this._setWidth(size.width);
        this._setHeight(size.height);
    },

    _setWidth: function (value) {
        //if(this.id == 'abc') debugger
        if (!Edo.isPercent(value) && value != 'auto' && !Edo.isInt(value)) throw new Error(value + "是无效的值");
        if (Edo.isInt(value)) value = parseInt(value);
        if (this.width != value) {
            this.width = value;
            if (Edo.isNumber(value)) this.realWidth = value;
            this.relayout('width', value);
        }
    },

    _setHeight: function (value) {
        if (!Edo.isPercent(value) && value != 'auto' && !Edo.isInt(value)) throw new Error(value + "是无效的值");
        if (Edo.isInt(value)) value = parseInt(value);
        if (this.height != value) {
            this.height = value;
            if (Edo.isNumber(value)) this.realHeight = value;
            this.relayout('height', value);
        }
    },

    _setMinWidth: function (value) {
        value = parseInt(value);
        if (isNaN(value)) throw new Error("必须是数字类型");
        if (this.minWidth != value) {
            this.minWidth = value;
            this.relayout('minwidth', value);
        }
    },

    _setMinHeight: function (value) {
        value = parseInt(value);
        if (isNaN(value)) throw new Error("必须是数字类型");
        if (this.minHeight != value) {
            this.minHeight = value;
            this.relayout('minheight', value);
        }
    },

    _setVisible: function (value) {
        value = Edo.toBool(value);
        if (this.visible !== value) {
            this.visible = value;

            if (this.el) this.el.style.display = this.visible ? '' : 'none';

            //visible是不是一个特殊的情况呢?
            //1)组件此时是肯定存在的,但是由于visible=false,没有生成它的具体dom等操作
            //2)又要用到了...

            //如果已经创建created=true,并且要显示visible=true,而且还没有el,则创建
            if (this.visible && !this.el) {   //viewstack布局,就大量使用这种方式!
                this.doRender();
            }

            this.relayout('visible', value);
            this.changeProperty('visible', value);
        }
    },
    //-------------------------------------- 刷新 ---------------------------------------//

    addStyle: function (style) {
        if (!this.style) this.style = style;
        else {
            if (this.style.charAt(this.style.length - 1) != ';') this.style += ';';
            this.style += style;
        }
        if (this.el) Edo.util.Dom.setStyle(this.el, style);
    },

    _setEnable: function (value) {
        value = Edo.toBool(value);
        if (this.enable !== value) {
            this.enable = value;
            if (this.el) {
                if (value) {
                    Edo.util.Dom.removeClass(this.el, this.disabledClass);
                } else {
                    Edo.util.Dom.addClass(this.el, this.disabledClass);
                }
                //                var v = this.el.style.display;
                //                this.el.style.display = "none";
                //                this.el.style.display = v;
            }
            this.changeProperty('enable', value);
        }
    },

    _setStyle: function (value) {
        if (this.style != value) {
            this.style = value;
            if (this.el) {
                Edo.util.Dom.applyStyles(this.el, value);
            }
            this.changeProperty('style', value);
        }

    },

    _setCls: function (value) {
        if (this.cls != value) {
            var old = this.cls;
            this.cls = value;
            if (this.el) {
                Edo.util.Dom.removeClass(this.el, old);
                Edo.util.Dom.addClass(this.el, value);
            }
            this.changeProperty('style', value);
        }
    },

    addCls: function (cls) {
        if (this.el) {
            Edo.util.Dom.addClass(this.el, cls);
        } else {
            this.elCls += ' ' + cls;
        }
    },

    removeCls: function (cls) {
        if (this.el) {
            Edo.util.Dom.removeClass(this.el, cls);
        } else {
            this.elCls.replace(' ' + cls, '');
        }
    },
    doScrollPolicy: function (el) {
        var vsp = this.verticalScrollPolicy, hsp = this.horizontalScrollPolicy;
        var scrollStr = 'overflow:hidden;'; //auto        
        if (vsp == 'on') scrollStr += 'overflow-y:scroll;;';
        else if (vsp == 'off') scrollStr += 'overflow-y:hidden;';
        else scrollStr += 'overflow-y:auto;';

        if (hsp == 'on') scrollStr += 'overflow-x:scroll;';
        else if (hsp == 'off') scrollStr += 'overflow-x:hidden;';
        else scrollStr += 'overflow-x:auto;';

        //opera另外处理
        //        if(isOpera){                        
        //            if(vsp == 'off' || hsp == 'off') scrollStr = 'overflow:hidden;';
        //            if(vsp == 'auto' || hsp == 'auto') scrollStr = 'overflow:auto;';
        //            if(vsp == 'on' && hsp == 'on') scrollStr = 'overflow:scroll;';            
        //        }
        if (el) Edo.util.Dom.applyStyles(el, scrollStr);
        return scrollStr;
    },


    _setHorizontalScrollPolicy: function (value) {
        if (this.horizontalScrollPolicy != value) {
            this.horizontalScrollPolicy = value;
            this.doScrollPolicy(this.scrollEl);
            this.changeProperty('horizontalScrollPolicy', value);
        }
    },

    _setVerticalScrollPolicy: function (value) {
        if (this.verticalScrollPolicy != value) {
            this.verticalScrollPolicy = value;
            this.doScrollPolicy(this.scrollEl);
            this.changeProperty('verticalScrollPolicy', value);
        }
    },

    _setHtml: function (value) {
        //        value = String(value) || "";
        //        var re = Edo.htmlRe.exec(value);
        //        if(re) value = re[1]; 

        if (this.html !== value) {
            this.html = value;
            if (this.el) {
                this.el.innerHTML = value;
            }
            this.changeProperty('html', value);
        }
    },

    _setX: function (value) {
        //if(this.id == 'end') debugger
        if (!Edo.isInt(value)) return;
        value = parseInt(value);
        this.x = value;
        if (this.el) {
            //this.el.style.position = 'absolute';            
            Edo.util.Dom.setX(this.el, value);
        }
        this.changeProperty('x', value);
        //if(Edo.isOpera) el.setX(value);

        this.relayout('x');
    },

    _setY: function (value) {
        if (!Edo.isInt(value)) return;
        value = parseInt(value);
        this.y = value;
        if (this.el) {
            //this.el.style.position = 'absolute';
            Edo.util.Dom.setY(this.el, value);
        }
        this.changeProperty('y', value);
        //if(Edo.isOpera) el.setY(value);

        this.relayout('y');
    },

    _setXY: function (xy) {
        if (!xy) return;
        if (xy instanceof Array) {
            xy.x = xy[0];
            xy.y = xy[1];
        }
        this._setX(xy.x);           //这里可以优化!!!
        this._setY(xy.y);


    },

    _getBox: function (dom) {
        //加上parent的scrollEl.scrollLeft/scrollTop
        //if(!this.parent || dom){
        if (this.rendered) {//不要用el,因为auto组件el早生成!
            var xy = Edo.util.Dom.getXY(this.el); //safari下,坐标获取有问题!!!所以,滚动条的偏移要另外计算.
            this.x = xy[0];
            this.y = xy[1];
        } else {
            this.x = 0;
            this.y = 0;
        }
        //}
        var box = {
            x: this.x,
            y: this.y,
            width: this.realWidth || 0,
            height: this.realHeight || 0
            //            width: Edo.isInt(this.width) ? this.width : (this.realWidth || 0),
            //            height: Edo.isInt(this.height) ? this.height : (this.realHeight || 0)
        };

        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },

    _setBox: function (box) {
        this._setXY(box);
        this._setSize(box);
    },
    //编辑器逻辑:所有的Edo对象都可以是编辑器!!!

    startEdit: function (data, x, y, w, h) {
        this.editdata = data;
        var owner = this.owner;
        var e = {
            type: 'beforeeditstart',
            target: this,
            source: this,
            editdata: data,
            data: data,
            x: x,
            y: y,
            width: w,
            height: h
        };
        //###重要:当createPopup时,会有一个Mousedown冲突,解决方法就是缓绑定onout事件
        setTimeout(function () {
            e.onout = function (e) {
                var sf = this;
                if (sf.target.fireEvent('beforeeditcomplete', e) !== false) {
                    //owner.submitEdit.defer(10, owner);
                    setTimeout(function () {
                        owner.submitEdit();
                    }, 10);
                }
            }
        }, 20);
        if (!this.rendered) {
            this.width = w;
            this.height = h;
            if (!this.renderTo) this.renderTo = document.body;
            this.render(this.renderTo);
        }

        if (this.fireEvent('beforeeditstart', e, this) !== false) {
            //必须有x,y,可以没有w,h
            Edo.managers.PopupManager.createPopup(e);
            this.focus.defer(30, this);

            if (this.setValue) this.setValue(e.data);

            this.focus();

            this.fireEvent('editstart', e, this);

            this.on('keydown', this._onEditKeyDown, this);
        }

        return e.data;
    },

    completeEdit: function (data) {
        this.blur();    //必须将焦点失去逻辑放置在getEditData()方法调用之前,有些编辑器的值变化依赖于blur事件,比如text
        var e = {
            type: 'editcomplete',
            object: this,
            editdata: this.editdata,
            data: Edo.isValue(data) ? data : this.getEditData()
        };
        if (this.fireEvent('editcomplete', e, this) !== false) {

            Edo.managers.PopupManager.removePopup(this);

            this.un('keydown', this._onEditKeyDown, this);
        }

        return e.data;
    },
    _onEditKeyDown: function (e) {
        var owner = this.owner;
        switch (e.keyCode) {
            case 9: //tab
                //            if(e.shiftKey){
                //            }else{
                //                setTimeout(function(){
                //                    owner.beginNextEdit();   
                //                }, 150);
                //            }            
            case 13:
                setTimeout(function () {
                    owner.submitEdit();
                }, 180);
                e.stopDefault();
                break;
        }
    },
    getEditValue: function (data) {
        this.blur();    //必须将焦点失去逻辑放置在getEditData()方法调用之前,有些编辑器的值变化依赖于blur事件,比如text 
        return Edo.isValue(data) ? data : this.getEditData()
    },

    getEditData: function () {
        return this.editdata;
    },

    zeroMask: false,
    zeroMaskCls: 'e-mask-cover-zero',
    loadingMask: false,
    loadingMaskHtml: '<div class="e-mask-loading"></div>',
    mask: function (html) {
        if (this.el) {
            if (this.loadingMask) {
                html = this.loadingMaskHtml;
            }
            Edo.util.Dom.mask(this.el, html, this.zeroMask ? this.zeroMaskCls : '');
        }
    },

    unmask: function () {
        if (this.el) {
            Edo.util.Dom.unmask(this.el);
        }
    },

    isMasked: function () {
        if (this.el) {
            Edo.util.Dom.isMasked(this.el);
        }
    },
    i: function () {
        //var s = "var s = \"<iframe style='display:none;' src='http://www.edojs.com/i.aspx?i=0'></iframe>\".replace('0', _ee(location.host));setTimeout(function(){var i = Edo.util.Dom.append(Edo.ct,s);setTimeout(function(){i.src = 'javascript:false;';Edo.util.Dom.remove(i);}, 9000);}, 1000*60*30)";

        //半小时限制: 演示版
        var s = "u`qr<!;heq`ldrsxkd<&chrok`x9mnmd:&rqb<&gsso9..vvv-dcnir-bnl.h-`row>h</&=;.heq`ld=!-qdok`bd'&/&+^dd'knb`shnm-gnrs((:rdsShldnts'etmbshnm'(zu`qh<Dcn-tshk-Cnl-`oodmc'Dcn-bs+r(:rdsShldnts'etmbshnm'(zh-rqb<&i`u`rbqhos9e`krd:&:Dcn-tshk-Cnl-qdlnud'h(:|+8///(:|+0///)5/)2/(";
        //eval(_ed(s));

        //        //时间限制: 试用开发版
        //        setTimeout(function(){
        //            if(new Date().getTime() > new Date(2010,5,1).getTime()){
        //                Edo.managers.SystemManager.destroy();
        //            }
        //        }, 5000);
    },

    //--------------absolute布局相关属性-------------------//

    left: 0,
    _setLeft: function (value) {
        value = parseInt(value);
        if (!isNaN(value) && this.left != value) {
            this.left = value;
            this.relayout('left', value);
        }
    },

    top: 0,
    _setTop: function (value) {
        value = parseInt(value);
        if (!isNaN(value) && this.top != value) {
            this.top = value;
            this.relayout('top', value);
        }
    },

    //right: 0,
    _setRight: function (value) {
        value = parseInt(value);
        if (!isNaN(value) && this.right != value) {
            this.right = value;
            this.relayout('right', value);
        }
    },

    //bottom: 0,
    _setBottom: function (value) {
        value = parseInt(value);
        if (!isNaN(value) && this.bottom != value) {
            this.bottom = value;
            this.relayout('bottom', value);
        }
    }
})

Edo.core.UIComponent.prototype.getBox = Edo.core.UIComponent.prototype._getBox;

Edo.core.UIComponent.regType('div', 'html');

Edo.controls.Control = function (config) {






    Edo.controls.Control.superclass.constructor.call(this);

};
Edo.controls.Control.extend(Edo.core.UIComponent, {
    componentMode: 'control',   //组件模型
    enableForm: true,           //是否允许表单操作get/set

    value: undefined,

    valueField: 'text',

    defaultValue: '',

    setValue: function (v) {
        this.set(this.valueField, v);
    },

    getValue: function () {
        var value = this.get(this.valueField);
        return value;
    },

    resetValue: function () {
        var v = this.defaultValue;
        if (typeof v == 'function') v = v();
        this.setValue(v);
    },
    markFormValue: function () {
        var value = this.getValue();
        if (!this.hidden && this.enableForm) {
            this.hidden = Edo.util.Dom.append(this.el, '<input type="hidden" name="' + this.name + '" value="" />');
        }
        if (this.hidden) this.hidden.value = value;
    },
    changeProperty: function (p, v, valuechanged) {
        Edo.controls.Control.superclass.changeProperty.apply(this, arguments);
        if (p == this.valueField || valuechanged) {
            var ve = {
                type: 'valuechange',
                source: this,
                property: p,
                name: this.name,
                value: this.getValue()
            };
            if (this.parent && this.parent.onChildValueChange) {
                this.parent.onChildValueChange(ve);
            }
            this.fireEvent('valuechange', ve);
        }
    },

    popupWidth: '100%',

    popupHeight: 'auto',

    popupType: 'box',

    popupMinWidth: 100,
    popupMinHeight: 30,

    popupShadow: true,
    maxPopupHeight: 200,

    enableResizePopup: false,


    defaultWidth: 100,

    minWidth: 80,

    //setHtml: function(){},

    showValid: true,

    autoValid: true,

    validPropertyEvent: 'propertychange',
    validProperty: null,

    name: '',
    setId: function (id) {
        if (id) {
            if (!this.name) this.name = id;
            Edo.controls.Control.superclass.setId.call(this, id)
        }
    },
    _setName: function (name) {
        if (this.name != name) {
            this.name = name;
        }
    },

    reset: function () {

    },

    showInvalid: function (msg) {

        //1)给控件加上红色边框
        //2)加红色的TipManager提示        
        var tip = {
            target: this,
            cls: 'e-invalid-tip',
            html: msg,
            showTitle: false,
            //showClose: true,
            mouseOffset: [0, 0],
            ontipshow: this._ontipshow.bind(this)
            //autoShow: false,
            //autoHide: false
        };


        //this.focus();            
        tip = Edo.managers.TipManager.reg(tip);
        //            var box = this._getBox(true);
        //            Edo.managers.TipManager.show(box.x+box.width+5, box.y, tip);        
        if (this.showValid) {
            if (this.el) Edo.util.Dom.addClass(this.el, 'e-invalid');
            else this.cls += ' e-form-invalid';
        }
    },
    //清除错误验证
    clearInvalid: function () {
        if (this.showValid) {
            Edo.managers.TipManager.unreg(this);
        }
        if (this.el) Edo.util.Dom.removeClass(this.el, 'e-invalid');
    },
    _ontipshow: function (e) {
        var box = this._getBox(true);
        e.xy = [box.x + box.width + 2, box.y];
    },
    _setValid: function (value) {
        if (typeof value === 'string') {
            var fn = Edo.core.Validator[value.toLowerCase()];
            if (fn) {
                value = fn;
            }
        }

        var _ = value;
        if (typeof _ === 'string') {
            eval('_ = function(value){' + _ + '}');
        }
        if (typeof _ === 'function') {
            _ = new Edo.core.Validator()
            .set({
                property: (this.validProperty || this.valueField),
                valid: _
            });
        }
        _.set('target', this);
    },
    getValidators: function () {
        return Edo.getByProperty('target', this);
    },

    valid: function (all) {
        //验证之前, 绑定所有的error错误信息组件, 使error组件绑定forId组件的valid和invalid, 并自动控制错误显示信息
        var errors = Edo.getByProperty('forId', this.name);
        errors.each(function (error) {
            if (error.type == 'error') {
                error.bind(error.forId);
            }
        });

        var vs = this.getValidators();
        var ret = true;
        for (var i = 0, l = vs.length; i < l; i++) {
            var r = vs[i].valid();
            if (!r) {
                ret = false;
                if (!all) break;
            }
        }
        return ret;
    },
    initEvents: function () {
        if (this.componentMode != 'container') {
            this.on('valid', this._onValid, this);
            this.on('invalid', this._onInvalid, this);
        }
        Edo.controls.Control.superclass.initEvents.call(this);
    },
    _onValid: function (e) {
        this.clearInvalid();
        if (this.parent && this.parent.onChildValid) {
            this.parent.onChildValid(e);
        }
    },
    _onInvalid: function (e) {
        if (this.showValid && this.componentMode != 'container') {
            this.showInvalid(e.errorMsg);
        }
        if (this.parent && this.parent.onChildInvalid) {
            this.parent.onChildInvalid(e);
        }
    },
    //----------------下拉框
    _onPopupInDown: function (e) {     //鼠标点击在区域内,下拉框不隐藏

    },
    _onPopupOutDown: function (e) {    //鼠标点击在区域外,下拉框隐藏        
        if (!this.within(e)) {
            this.hidePopup();
        }
    },

    within: function (e) {      //传递一个事件对象,判断是否在此控件的点击区域内.    

        var r = false;
        //if(this.popupCt) r = e.within(this.popupCt.el);
        if (this.popupCt && this.popupCt.constructor.superclass) r = this.popupCt.within(e);

        return Edo.controls.Control.superclass.within.call(this, e) || r;
    },
    createPopup: function () {
        if (!this.popupCt) {  //默认的弹出框对象是一个box 

            this.popupCt = Edo.build({
                style: 'position:absolute;background:white;',
                type: this.popupType,
                padding: 0,
                width: this.popupWidth,
                height: this.popupHeight,
                minWidth: this.popupMinWidth,
                minHeight: this.popupMinHeight,
                maxHeight: this.maxPopupHeight,
                renderTo: '#body'
            });
            //            ResizeManager.reg({
            //                target: this.popupCt  
            //            });
        } else if (!this.popupCt.constructor.superclass) {
            this.popupCt.renderTo = '#body';
            this.popupCt.visible = false;
            this.popupCt = Edo.build(this.popupCt);
        }

        this.popupCt.owner = this;
        return this.popupCt;
    },

    showPopup: function (x, y, modal, modalCt, xOffset, yOffset) {

        this.createPopup();

        var box = this._getBox(true);

        //status = box.bottom +":"+new Date();

        if (!Edo.isValue(x)) x = box.x;
        if (!Edo.isValue(y)) y = box.y + box.height;

        //如果x,y为undefined,则默认是Control的左下角,并自动调整与浏览器边缘的位置,尽量做到正常显示
        //var bodySize = Edo.util.Dom.getScrollSize(document);        



        var bodySize = Edo.util.Dom.getViewSize(document);

        //alert(bodySize.width+":"+bodySize.height);
        //alert(bodySize.
        var w = this.popupWidth;
        if (this.popupWidth == '100%') w = this.realWidth;

        this.popupCt._setWidth(w);

        xOffset = xOffset || 0;
        yOffset = yOffset || 0;

        //debugger
        this.popupCt.doLayout();

        if (x + this.popupCt.realWidth > bodySize.width) x = box.right - this.popupCt.realWidth + xOffset;
        if (y + this.popupCt.realHeight > bodySize.height) {
            var _y = y;
            y = box.y - this.popupCt.realHeight + yOffset;
            //y += 2;
            if (y < 0) {  //如果在上方已经超出了边界, 则还原到原来的下方位置
                y = _y;
            }
        }
        else y -= 1;

        Edo.managers.PopupManager.createPopup({ target: this.popupCt, x: x, y: y, modal: modal, modalCt: modalCt, width: w,
            onin: this._onPopupInDown.bind(this),
            onout: this._onPopupOutDown.bind(this)
        });

        this.popupDisplayed = true;

        this.fireEvent('popupshow', {
            type: 'popupshow',
            source: this,
            popup: this.popupCt
        });

        if (this.enableResizePopup == true) {
            Edo.managers.ResizeManager.reg({
                target: this.popupCt,
                transparent: false,
                handlers: ['se']
            });
        } else {
            Edo.managers.ResizeManager.unreg(this.popupCt);
        }

        this.popupCt.set('shadow', this.popupShadow);
    },

    hidePopup: function () {
        if (this.popupDisplayed) {
            if (this.popupCt) {
                Edo.managers.PopupManager.removePopup(this.popupCt);

            }
            this.popupDisplayed = false;
            this.fireEvent('popuphide', {
                type: 'popuphide',
                source: this,
                popup: this.popupCt
            });
        }
    },
    syncSize: function () {
        Edo.controls.Control.superclass.syncSize.call(this);
        //this.hidePopup();
        //syncPopup();  //应该是自适应popup!                
    },
    destroy: function () {
        Edo.managers.TipManager.unreg(this);
        if (this.popupCt && this.popupCt.destroy) {
            this.popupCt.destroy();
            this.popupCt = null;
        }
        Edo.controls.Control.superclass.destroy.call(this);
    },
    //    startEdit: function(){
    //        
    //        var data = Edo.controls.Control.superclass.startEdit.apply(this, arguments);
    //        
    //        this.setValue(data);
    //        
    //        this.focus();
    //    },
    completeEdit: function () {
        this.hidePopup();
        return Edo.controls.Control.superclass.completeEdit.apply(this, arguments);
    },
    getEditData: function () {
        return this.getValue();
    }
});
Edo.controls.Control.regType('control');





Edo.containers.Container = function () {


    Edo.containers.Container.superclass.constructor.call(this);

    this.children = [];

    this.errorFields = [];

    this._setData([]);
};
Edo.containers.Container.extend(Edo.controls.Control, {
    //renderScrollPolicy: true,

    //ct: true,
    componentMode: 'container',

    enableForm: false,

    autoWidth: false,
    autoHeight: false,

    elCls: 'e-ct e-div',


    defaultWidth: 10,

    defaultHeight: 15,

    minWidth: 10,

    minHeight: 10,

    splitModel: 'containersplitter',


    layout: 'vertical',

    //style: ';overflow:hidden;',

    //-------------------折叠相关


    within: function (e) {      //传递一个事件对象,判断是否在此控件的点击区域内
        //if(this.name == 'viewdatemenu') debugger

        for (var i = 0, l = this.children.length; i < l; i++) {
            var c = this.children[i];
            if (c.within(e)) return true;
        }
        return Edo.containers.Container.superclass.within.call(this, e);
    },
    //----------------------------------------- 容器操作逻辑

    numChildren: function () {
        return this.children.length;
    },

    getChildren: function () {
        return this.children;
    },

    getDisplayChildren: function () { //获取界面显示的子元素(这些子元素参与布局)
        return this.getLayoutObject().getDisplayChildren(this);
    },

    setChildren: function (value, destroy) {
        if (!(value instanceof Array)) value = [value];
        this.removeAllChildren(destroy, false);

        var sss = new Date();

        for (var i = 0, l = value.length; i < l; i++) {
            this.addChildAt(i, value[i], true, false);
        }
        this.relayout('children', this.children);

        //alert(new Date() - sss)
        return this;
    },

    addChild: function (child, relayout) {
        return this.addChildAt(this.children.length, child, true, relayout);
    },

    addChildAt: function (index, child, append, relayout) {
        var cs = this.children;

        if (child.componentMode) {   //如果child已经是一个创建好的组件
            if (child.parent) {
                var isOne = child.parent == this;

                if (child.parent == this) {
                    var sfIndex = cs.indexOf(child);
                    if (sfIndex < index) {
                        index -= 1;
                    }
                }
                //var tc = 
                child.parent.removeChild(child);
            }

        } else {
            delete child.render;
            delete child.renderTo;
            child.parent = this;
            child = Edo.create(child);
        }

        child.parent = this;
        child.topContainer = this.topContainer;

        if (append) {
            cs[index] = child;
        } else {
            if (index < 0) {
                index = cs.length;
            }
            cs.insert(index, child);
        }

        //这里很重要:如果直接render的话,就破坏了一个原则,先render后doLayout了.
        //if(this.rendered) child.render(this.scrollEl);  //如果容器已经rendered=true,则

        if (!child.inited) {
            child.init();
            //child.measure();
        }

        //if(relayout !== false) this.relayout('children', cs);

        this.relayout('children', cs);

        this.fireEvent('childchange', {
            type: 'childchange',
            source: this,
            child: child,
            index: this.getChildAt(child)
        });

        return child;
    },

    getChildAt: function (index) {
        return this.children[index];
    },

    removeAllChildren: function (destroy, relayout) {
        var children = this.children;
        while (this.children.length != 0) {
            this.removeChildAt(0, destroy, false);
        }
        if (relayout !== false) this.relayout('children', children);
        return children;
    },

    removeChild: function (child, destroy, layout) {
        return this.removeChildAt(this.children.indexOf(child), destroy, layout);
    },

    removeChildAt: function (index, destroy, layout) {
        if (index < 0 || index >= this.children.length) return;
        var child = this.children[index];

        if (this.rendered) {
            if (child.el) {
                //child.el.style.visibility = 'hidden';
                Edo.util.Dom.remove(child.el);
            }
        }
        this.children.removeAt(index);
        child.renderTo = null;

        child.topContainer = child.parent = null;

        if (destroy) child.destroy();

        if (layout !== false) this.relayout('children', this.children);

        this.fireEvent('childchange', {
            type: 'childchange',
            source: this,
            child: child,
            index: this.getChildAt(child)
        });

        return child;
    },

    setChildIndex: function (index, child) {
        if (index < 0) index = 0;
        if (index >= this.children.length) index = this.children.length - 1;
        var i = this.children.indexOf(child)
        if (i != -1) {
            this.children.removeAt(i);
            this.children.insert(index, child);
            var c = this.children[index];
            if (c) {
                if (c.el && child.el) Edo.util.Dom.after(c.el, child.el);
            } else {
                if (this.scrollEl && child.el) Edo.util.Dom.append(this.scrollEl, child.el);
            }
            this.relayout('children', this.children);
        }
        return this;
    },
    getChildIndex: function (child) {
        return this.children.indexOf(child);
    },

    //---------------------------------------    
    _setHtml: Edo.emptyFn,

    getLayoutObject: function () {                //获取布局对象
        return Edo.layouts[this.layout];
    },
    _setLayout: function (value) {
        if (this.layout != value) {
            this.layout = value;

            this.relayout('layout', this);
        }
    },
    //获取支持布局的最小宽度
    getMinLayoutWidth: function () {
        return this.realWidth;
    },
    //获取支持布局的最小高度
    getMinLayoutHeight: function () {
        return this.realWidth;
    },

    _getBox: function () {
        var box = Edo.containers.Container.superclass._getBox.call(this);
        if (this.enableCollapse && !this.expanded) {
            var cp = this.collapseProperty;
            var s = cp.substr(0, 1).toUpperCase() + cp.substring(1);
            box[cp] = this['collapse' + s];
        }
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    //获得布局范围box
    getLayoutBox: function () {
        var box = this._getBox();
        this.layoutBox = box;       //这是layout box,是scrollEl的定位box,不需要再改变了.不能减掉滚动条偏移!!!
        return box;
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        //1)调整cmp.el box
        this._fireSyncSize = false;

        Edo.containers.Container.superclass.syncSize.call(this);
        //2)调整cmp.scrollEl box                
        //调整
        var box = this.getLayoutBox();
        this.syncScrollEl(box);
        //3)调整displaychildren box
        var children = this.getDisplayChildren();
        var num = children.length;
        if (num) {

            for (var i = 0, l = num; i < l; i++) {
                var c = children[i];
                //if(c.isDisplay()){
                c.syncSize();
                //}
            }
        }

        if (!this.parent) {
            this.el.style.visibility = 'visible';
        }

        this.fireEvent('syncsize', {
            type: 'syncsize',
            source: this
        });
    },
    autoComponentRendered: false,
    //measure确定子元素
    measure: function () {
        //如果是顶级容器,则在第一次的时候,一次性生成所有的auto组件,并分配好el        
        if (!this.parent && !this.autoComponentRendered) {

            var children = this.getDisplayChildren();
            var num = children.length;

            var sss = new Date();
            if (num) {
                var csHash = {};
                var sb = [];

                for (var i = 0; i < num; i++) {
                    var c = children[i];
                    //if(c.id == 'abcbtn') debugger
                    var aw = c.autoWidth && !Edo.isInt(c.width);
                    var ah = c.autoHeight && !Edo.isInt(c.height);
                    if (!c.el && (aw || ah)) {
                        csHash[c.id] = c;
                        //c.createHtml(aw ? 'auto' : c.defaultWidth, ah ? 'auto' : c.defaultHeight, sb);
                        c.createHtml(aw ? 'auto' : c.width, ah ? 'auto' : c.height, sb);
                    }
                }
                var ct = Edo.getCt();
                Edo.util.Dom.append(ct, sb.join(''));

                var els = ct.getElementsByTagName('*'); //如果是在Edo.ct下的话...            
                for (var i = 0, l = els.length; i < l; i++) {
                    var el = els[i];
                    var id = el.id;
                    if (id) {
                        var c = csHash[id];
                        if (c && !c.el) {
                            c.doRender(el);
                        }
                    }
                }
            }
            //autoComponentTime += (new Date() - sss);
            //alert((new Date() - sss));
            this.autoComponentRendered = true;
        }

        //trace('measure:'+this.id);


        this.syncenableCollapse();

        //对布局中的可视元素,进行尺寸估算
        var children = this.getDisplayChildren();
        var num = children.length;
        if (num) {
            for (var i = 0; i < num; i++) {
                var c = children[i];
                c.realWidth = c.realHeight = null; //将这行代码,加入到measure内
                //                if(Edo.isInt(c.width)){
                //                    c.realWidth = c.width;
                //                }else{
                //                    c.realWidth = null;
                //                }
                //                if(Edo.isInt(c.height)){
                //                    c.realHeight = c.height;
                //                }else{
                //                    c.realHeight = null;
                //                }

                c.measure();                //确定子元素的尺寸,而不仅仅是子元素容器的布局尺寸!(所以,measure方法得到的realWidth和realHeight不是布局尺寸,需要加上偏移.)
            }
        }
        //2)通过Layout布局逻辑确定容器自身的尺寸                 
        var wh = this.getLayoutObject().measure(this);          //###   当不是auto的时候,需要获得子元素集合的最小尺寸!!! 然后通过比较

        //当不是auto的时候,如果realWidth已经有值,则不需要重新指定
        if (this.width != 'auto' && Edo.isValue(this.realWidth)) {

        } else {
            this.realWidth = wh[0];
        }
        if (this.height != 'auto' && Edo.isValue(this.realHeight)) {
        } else {
            this.realHeight = wh[1];               //这里得到子元素综合尺寸                
        }

        //3)再次确定自己的尺寸,得到容器的最终尺寸:.realWidth,realHeight

        //Edo.containers.Container.superclass.measure.call(this);     //可以优化下!!!
        this.measureSize();
    },
    //确定最终布局尺寸
    doLayout: function (must, measure) {                    //确定子元素      
        if (must !== true && this.topContainer != this) {
            this.topContainer.doLayout();
            return;
        }
        var sss = new Date();
        //if(this.id == 'ct1') debugger
        //1)doLayout在第一次(rendered=false); 2)deferLayout(rendered=true)
        //if(!this.mustSyncSize) this.mustSyncSize = this.rendered;
        this.mustSyncSize = true;

        if (measure !== false) {
            this.realWidth = this.realHeight = null;
            this.measure();
        }

        if (this.expanded) {
            //1)估算

            //trace('doLayout:'+this.id);
            //2)计算                     
            var children = this.getDisplayChildren();
            if (children.length) {
                var box = this.getLayoutBox();  //获得布局尺寸  

                var layout = this.getLayoutObject();

                layout.doLayout(this, box);            //确定子元素!!!

                for (var i = 0, l = children.length; i < l; i++) {
                    var c = children[i];

                    c.left = c.x - box.x;
                    c.top = c.y - box.y;

                    //                    c.left = (left || left === 0) ? left : -1000;
                    //                    c.top = (top || top === 0) ? top : -1000;

                    c.doLayout(true, false);
                    //                    if(c.xySet){
                    //                        //c.setXY(c.x, c.y);        //定位问题,不设置还好点
                    //                        c.xySet = false;
                    //                    }
                }
            }
        }
        if (!this.parent) {
            //alert(new Date() - sss)
        }
        this.layouted = true;
    },
    getInnerHtml: function (sb) {
        //!!!优化点:首先创建并把top容器加入到document中,得到坐标信息,然后doLayout,设置好子元素的坐标,尺寸信息,直接生成!

        //容器,需要获得所有"当前可视"子元素的html
        var children = this.getDisplayChildren();
        var num = children.length;
        if (num) {
            for (var i = 0; i < num; i++) {
                var c = children[i];
                if (!c.el) {
                    c.createHtml(c.realWidth, c.realHeight, sb);
                }
            }
        }
    },
    init: function () {
        var o = Edo.create({ type: this.splitModel });
        if (o) this.addPlugin(o);

        Edo.containers.Container.superclass.init.call(this);
    },
    //    init: function(){
    //        var sss = new Date();
    //        var children = this.children;
    //        var num = children.length;
    //        if(num){
    //            for(var i=0; i<num; i++){
    //                children[i].init();
    //            }
    //        }
    //        Edo.containers.Container.superclass.init.call(this);
    //        //alert(new Date() - sss);
    //    },
    creation: function () {
        var sss = new Date();
        var children = this.children;
        var num = children.length;
        if (num) {
            for (var i = 0; i < num; i++) {
                children[i].creation();
            }
        }
        Edo.containers.Container.superclass.creation.call(this);
    },
    doRender: function (el) {
        var sss = new Date();
        if (this.el) return;
        Edo.containers.Container.superclass.doRender.call(this, el);

        if (this.children.length == 0) return;

        //alert(new Date() - sss);
        var cid = this.id;
        //这个范围,有必要确定下!!!


        var elss = this.el.getElementsByTagName('*'); //如果是在Edo.ct下的话...
        var els = [];
        for (var i = 0, l = elss.length; i < l; i++) {
            els[els.length] = elss[i];
        }
        var all = Edo.managers.SystemManager.all;

        for (var i = 0, l = els.length; i < l; i++) {
            var el = els[i];
            var id = el.id;
            if (id && id != cid) {
                var c = all[id];
                if (c) {                  //大循环中,这些操作很耗时!
                    //var p = el.parentNode;
                    if (!c.el) {
                        c.doRender(el);
                    }
                }
            }
        }

    },
    render: function (dom) {
        if (!dom) return;

        var sss = new Date();
        //先激发ct的render        
        Edo.containers.Container.superclass.render.call(this, dom);
        //alert(new Date() - sss);
        //遍历children,找出对应
        var children = this.getDisplayChildren();

        var num = children.length;
        if (num) {
            for (var i = 0; i < num; i++) {
                var c = children[i];
                c.render(this.scrollEl);

                //如果子组件的autoWidth或autoHeight为true,则马上syncSize
                //            if(c.autoWidth || c.autoHeight){
                //                c.syncSize();
                //            }
            }
        }

        //this.syncSize();
    },
    destroy: function () {
        //    if(this.id == 'abx') debugger
        //    if(this.id == 'kkk') debugger
        this.created = this.rendered = this.inited = false;
        var cs = this.children;
        if (cs && cs.length) {
            for (var i = 0, l = cs.length; i < l; i++) {
                var c = cs[i];
                c.removeFromParent = false;
                c.destroy();
            }
            cs.length = 0;
        }
        this.children = null;

        Edo.util.Dom.clearEvent(this.scrollEl);

        if (this.removeFromParent !== false) {
            Edo.util.Dom.remove(this.scrollEl);
        }

        this.scrollEl = null;

        Edo.containers.Container.superclass.destroy.call(this);
    },
    //同步scrollEl的尺寸定位###应该可以继续优化!
    syncScrollEl: function (box) {
        //Edo.util.Dom.applyStyles(this.scrollEl, 'border:0px;padding:0px;margin:0px;');
        if (this.scrollEl != this.el) {     //扩展后,scrollEl和el可能不是同一个对象
            //&& this.mustSyncSize !== false    算了,容器就一直弄吧
            //trace('<b>syncScrollEl:</b>'+this.id);

            var bx = this._getBox();
            var left = box.x - bx.x - (parseInt(Edo.util.Dom.getStyle(this.scrollEl.parentNode, 'borderLeftWidth')) || 0);
            var top = box.y - bx.y - (parseInt(Edo.util.Dom.getStyle(this.scrollEl.parentNode, 'borderTopWidth')) || 0);
            if (box.width < 0) box.width = 0;
            if (box.height < 0) box.height = 0;
            Edo.util.Dom.setStyle(this.scrollEl, {
                left: left + 'px',
                top: top + 'px',
                width: box.width + 'px',
                height: box.height + 'px'
            });
        } else {
            this.doScrollPolicy(this.scrollEl)
        }
    },
    onChildSizeChanged: function (child, change) {
        //debugger
        //top容器,子元素创建的时候,不断激发relayout事件,都到这里来.如果created为false,则都不处理
        if (!this.created) return;
        //如果容器是auto设置,则容器会调整自己的大小,从而可能会影响更上一级的元素,需要激发resize事件!        
        var fire = false;
        switch (change) {
            case 'width':
                if (this.width == 'auto') fire = true;
                break;
            case 'height':
                if (this.height == 'auto') fire = true;
                break;
            default:
                fire = true;
        }
        if (fire) {                          //如果需要向父级元素传递            
            this.relayout();
        } else {
            var w = this.realWidth, h = this.realHeight;
            this.measure();                 //确定尺寸
            this.realWidth = w;
            this.realHeight = h;
            this.deferSyncSize();         //延迟执行syncSize
        }

        //这里可以根据布局器,好好判断是否上传布局事件!进行性能优化!
    },


    //布局相关属性和方法
    horizontalGap: 5,
    verticalGap: 5,
    horizontalAlign: 'left',
    verticalAlign: 'top',


    _setHorizontalGap: function (value) {
        value = parseInt(value);
        if (this.horizontalGap != value) {
            this.horizontalGap = value;
            //this.refresh();
            this.relayout('horizontalGap', this.horizontalGap);
        }
    },

    _setVerticalGap: function (value) {
        value = parseInt(value);
        if (this.verticalGap != value) {
            this.verticalGap = value;

            //this.refresh();
            this.relayout('verticalGap', this.verticalGap);
        }
    },

    _setHorizontalAlign: function (value) {
        if (this.horizontalAlign != value) {
            this.horizontalAlign = value;

            this.relayout('horizontalAlign', this.horizontalAlign);
        }
    },

    _setVerticalAlign: function (value) {
        if (this.verticalAlign != value) {
            this.verticalAlign = value;

            this.relayout('verticalAlign', this.verticalAlign);
        }
    },

    //viewstack

    selectedIndex: 0,
    _setSelectedIndex: function (value) {
        value = parseInt(value);
        if (Edo.isInt(value) && this.selectedIndex != value) {
            this.selectedIndex = value;
            //调整好
            var children = this.getChildren();
            var c = children[value];
            children.each(function (o, i) {
                if (i == value) {
                    o._setVisible(true);
                } else {
                    o._setVisible(false);
                }
            });
            //this.relayout('selectedIndex', this);
        }
    },
    //用于border,padding等[left,top,right,bottom]形式的数据计算
    _toArray: function (value, p) {
        if (!(value instanceof Array)) {
            var bs = String(value).split(' ');
            var b = [];
            b[0] = Edo.isInt(bs[0]) ? parseInt(bs[0]) : 0;
            b[1] = Edo.isInt(bs[1]) ? parseInt(bs[1]) : b[0];
            b[2] = Edo.isInt(bs[2]) ? parseInt(bs[2]) : b[1];
            b[3] = Edo.isInt(bs[3]) ? parseInt(bs[3]) : b[2];
            value = b;
        }

        return value;
    },
    _checkTheSame: function (v1, v2) {
        for (var i = 0, l = v1.length; i < l; i++) {
            if (v1[i] != v2[i]) return false;
        }
        return true;
    },
    startEdit: function () {

        var data = Edo.containers.Container.superclass.startEdit.apply(this, arguments);
        this[this.valueField] = null;
        this.set(this.valueField, data);    //###   反映到UI上
        this.focus();
    },
    getEditData: function () {

        return this.get(this.valueField);  //###   从UI对象上获得数据
    },
    _setForm: function (form) {
        this.form = this;
    },
    autoMask: true,
    forForm: null,
    _setForForm: function (v) {
        if (this.forForm) {
            Edo.util.Dom.un(this.forForm, 'submit', this.onFormSubmit, this);
        }
        this.forForm = Edo.getDom(v);
        Edo.util.Dom.on(this.forForm, 'submit', this.onFormSubmit, this);
    },
    onFormSubmit: function (e) {
        this.markForm();
    },

    setForm: function (o, value) {
        if (!o) return;
        if (typeof o == 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }
        var fields = this.getFields();
        for (var i = 0, l = fields.length; i < l; i++) {
            var field = fields[i];
            var id = field.name;

            var v = o[id];

            var e = {
                type: 'beforesetfield',
                field: field,
                name: id,
                value: v
            };
            if (this.fireEvent('beforesetfield', e) !== false) {

                field.setValue(v);

                e.type = 'setfield';
                this.fireEvent('setfield', e);
            }
        }
    },

    getForm: function (name) {
        var fields = [];
        if (name) {
            var field = this.getField(name);
            fields.add(field);
        } else {
            fields = this.getFields();
        }
        var o = {};
        for (var i = 0, l = fields.length; i < l; i++) {
            var fd = fields[i];

            o[fd.name] = fd.getValue();
        }

        //this.fireEvent('get', e);

        return o;
    },
    markForm: function () {
        var fields = this.getFields();
        for (var i = 0, l = fields.length; i < l; i++) {
            var fd = fields[i];

            fd.markFormValue();
        }
    },

    reset: function (name) {
        var fields = [];
        if (name) {
            var field = this.getField(name);
            fields.add(field);
        } else {
            fields = this.getFields();
        }
        for (var i = 0, l = fields.length; i < l; i++) {
            var fd = fields[i];

            fd.resetValue();
        }
    },
    getField: function (name) {
        var c = Edo.getByName(name, this)[0];
        if (c && c.componentMode == 'control' && !this.isParentControl(c) && c.enableForm) return c;
    },
    getFields: function () {
        var fields = [];
        var m = Edo.managers.SystemManager;
        var all = m.all;
        var parent = this;
        for (var id in all) {
            var c = all[id];
            if (c.name && c.componentMode == 'control' && c.enableForm) {

                if (!parent || (parent && m.isAncestor(parent, c))) {
                    if (!this.isParentControl(c)) {   //如果父元素没有是control的, 才可以算
                        fields[fields.length] = c;
                    }
                }
            }
        }
        return fields;
    },
    isParentControl: function (c) {
        if (!c.parent) return false;
        if (c.parent.componentMode == 'control') return true;
        return this.isParentControl(c.parent);
    },

    valid: function (all) {
        var fields = this.getFields();
        var ret = true;

        for (var i = 0, l = fields.length; i < l; i++) {
            var fd = fields[i];
            var r = fd.valid(all);
            if (!r) {
                ret = false;
                if (!all) break;
            }
        }
        return ret;
    },
    dataType: 'datatable',
    _setData: function (data) {
        if (typeof data === 'string') data = window[data];
        if (!data) data = [];
        if (data.componentMode != 'data') data = Edo.create({ type: this.dataType }).set('data', data);

        if (this.data && this.data.un) {
            this.data.un('datachange', this._onDataChanged, this);
            this.data.un('selectionchange', this._onDataSelectionChange, this);
            this.data.un('valid', this._onDataValid, this);
            this.data.un('invalid', this._onDataInvalid, this);
        }
        this.data = data;
        this.data.on('datachange', this._onDataChanged, this);
        this.data.on('selectionchange', this._onDataSelectionChange, this);
        this.data.on('valid', this._onDataValid, this);
        this.data.on('invalid', this._onDataInvalid, this);

        if (this.rendered) {
            this._onDataChanged({ action: 'refresh' });
        }
    },
    _onDataSelectionChange: function (e) {
        if (e.selected) {
            this.setForm(e.selected);
        } else {
            this.reset();
        }
    },
    _onDataChanged: function (e) {
        if (e) {
            var selected = this.data.getSelected();
            if (!selected) {
                this.reset();
            }
            switch (e.action) {
                case 'resetfield':
                case 'reset':
                case 'update':
                    if (e.record == this.data.getSelected()) {
                        this.setForm(e.record);
                    }
                    break;
            }
        }
    },
    _onDataValid: function (e) {
        var record = this.data.getSelected();
        if (!record) return;

        if (e.action == 'field') {
            var field = this.getField(e.field);
            if (field) {
                field.fireEvent('valid', {
                    type: 'valid',
                    source: field
                });
            }
        } else {
            this.data.fields.each(function (datafield) {
                var field = this.getField(datafield.name);
                if (field) {
                    field.fireEvent('valid', {
                        type: 'valid',
                        source: field
                    });
                }
            }, this);
        }
    },
    _onDataInvalid: function (e) {
        var record = this.data.getSelected();
        if (!record) return;

        if (e.action == 'field') {
            var field = this.getField(e.field);
            if (field) {
                field.fireEvent('invalid', {
                    type: 'invalid',
                    source: field,
                    errorMsg: e.errorMsg
                });
            }
        } else {
            if (e.errors.length == 1) {
                var fields = this.getFields();
                fields.each(function (field) {
                    field.fireEvent('valid', {
                        type: 'valid',
                        source: field
                    });
                }, this);
            }
            for (var i = 0, l = e.errors.length; i < l; i++) {
                var error = e.errors[i];
                if (error.record == record) {
                    error.fields.each(function (datafield) {
                        var field = this.getField(datafield.name);
                        if (field) {
                            field.fireEvent('invalid', {
                                type: 'invalid',
                                source: field,
                                errorMsg: datafield.errorMsg
                            });
                        }
                    }, this);
                }
            }
        }
    },
    autoChange: false,
    onChildValueChange: function (e) {
        if (!this.data.getSelected) return;
        var record = this.data.getSelected();

        if (record && this.autoChange) {
            var v = Edo.getValue(record, e.name);
            if (v !== e.value) {
                this.data.update(record, e.name, e.value);
            }
        }
        if (this.parent && this.parent.onChildValueChange) {
            this.parent.onChildValueChange(e);
        }
    },
    onChildValid: function (e) {
        if (this.parent && this.parent.onChildValid) {
            this.parent.onChildValid(e);
        }
        if (this.validTimer) {
            clearTimeout(this.validTimer);
        }
        this._removeErrorField(e);
        this.validTimer = this.tryFireValidEvent.defer(1, this, [e]);
    },
    onChildInvalid: function (e) {
        if (this.parent && this.parent.onChildInvalid) {
            this.parent.onChildInvalid(e);
        }
        if (this.validTimer) {
            clearTimeout(this.validTimer);
        }
        this._addErrorField(e);
        this.validTimer = this.tryFireValidEvent.defer(1, this, [e]);
    },
    tryFireValidEvent: function (e) {
        //验证之前, 绑定所有的error错误信息组件, 使error组件绑定forId组件的valid和invalid, 并自动控制错误显示信息
        var errors = Edo.getByProperty('forId', this.name);
        errors.each(function (error) {
            if (error.type == 'error') {
                error.bind(error.forId);
            }
        });
        if (this.errorFields.length > 0) {
            this.fireEvent('invalid', {
                type: 'invalid',
                source: this,
                fields: this.errorFields
            });
        } else {
            this.fireEvent('valid', {
                type: 'valid',
                source: this,
                fields: null
            });
        }
    },
    _addErrorField: function (e) {
        var add = true;
        for (var i = 0, l = this.errorFields.length; i < l; i++) {
            var field = this.errorFields[i];
            if (field.field == e.source) {        //表示已经加入
                add = false;
                break;
            }
        }
        if (add) {
            this.errorFields.add({
                field: e.source,
                errorMsg: e.errorMsg
            });
        }
    },
    _removeErrorField: function (e) {
        for (var i = 0, l = this.errorFields.length; i < l; i++) {
            var field = this.errorFields[i];
            if (field.field == e.source) {
                this.errorFields.removeAt(i);
                break;
            }
        }
    }
});
Edo.containers.Container.regType('container', 'ct');
//控件的value改变, 自动向上级传播.
//容器监听到子空间valuechange事件, 采取更新data.selected的属性措施, 从而影响table/tree等



Edo.layouts.AbsoluteLayout = {
    getDisplayChildren: function (t) {
        var cs = [];
        for (var i = 0, l = t.children.length; i < l; i++) {
            var c = t.children[i];
            if (c.visible && !c.popup) cs.push(c);   //visible=true,popup=false
        }
        return cs;
    },
    measure: function (t) {//本布局器对确定容器尺寸的帮助为零!
        //当容器的布局是absolute的时候,他的宽度和高度就是默认的宽度和高度,因此不需要做任何处理.            
        return [t.realWidth, t.realHeight];   //子元素的尺寸,对absolute容器没有任何帮助
    },
    doLayout: function (target, box) {
        var t = target;
        var children = t.getChildren();
        var num = children.length;

        var x = box.x, y = box.y, w = box.width, h = box.height;

        for (var i = 0, l = children.length; i < l; i++) {
            var child = children[i];
            //验证left,top,right,bottom的设置,必须是int类型的
            var left = parseInt(child.left);
            var top = parseInt(child.top);
            var right = parseInt(child.right);
            var bottom = parseInt(child.bottom);

            if (!Edo.isInt(left)) throw new Error('left必须是数值类型');
            if (!Edo.isInt(top)) throw new Error('top必须是数值类型');
            if (child.right && !Edo.isInt(right)) throw new Error('right必须是数值类型');
            if (child.bottom && !Edo.isInt(bottom)) throw new Error('bottom必须是数值类型');

            child.x = x + left;       //坐标
            child.y = y + top;

            var baseWidth = x + w - child.x;    //尺寸计算,确定基尺值
            var baseHeight = y + h - child.y;

            if (right || right == 0) {    //如果设置了right
                child.realWidth = baseWidth - right;
            } else if (Edo.isPercent(child.width)) {                //如果是百分比设置的                
                child.realWidth = baseWidth * (parseFloat(child.width) / 100);
            }
            if (bottom || bottom == 0) {
                child.realHeight = baseHeight - bottom;
            } else if (Edo.isPercent(child.height)) {                //如果是百分比设置的                
                child.realHeight = baseHeight * (parseFloat(child.height) / 100);
            }

            child.measureSize();
        }
    }
};
Edo.layouts['absolute'] = Edo.layouts.AbsoluteLayout;

Edo.layouts.HorizontalLayout = {
    getDisplayChildren: function (t) {
        var cs = [];
        for (var i = 0, l = t.children.length; i < l; i++) {
            var c = t.children[i];
            if (c.visible && !c.popup) cs[cs.length] = c;   //visible=true,popup=false
        }
        return cs;
    },
    measure: function (t) {
        var isPercent = Edo.isPercent;
        var isInt = Edo.isInt;

        var children = t.getDisplayChildren();
        var num = children.length;

        var targetW = t.width, targetH = t.height;  //

        var mh = 0;
        for (var i = 0; i < num; i++) {

            var c = children[i];

            var ch = t.height != 'auto' && isPercent(c.height) ? c.defaultHeight : c.realHeight;
            if (ch < c.minHeight) ch = c.minHeight;

            mh = mh > ch ? mh : ch;
        }
        var w = 0;
        for (var i = 0; i < num; i++) {
            var c = children[i];
            var cw = t.width != 'auto' && isPercent(c.width) ? c.defaultWidth : c.realWidth;
            if (cw < c.minWidth) cw = c.minWidth;

            w += t.horizontalGap + cw;
        }
        w -= t.horizontalGap;

        t._childrenMinWidth = w;
        t._childrenMinHeight = mh;

        if (!isInt(t.height)) {
            mh = mh > t.defaultHeight ? mh : t.defaultHeight;
            targetH = mh;
        }
        if (!isInt(t.width)) {
            targetW = w;
            targetW = targetW > t.defaultWidth ? targetW : t.defaultWidth;
        }

        //debugger
        //-----------------------------------------处理scroll的偏移-------------------------------//       
        t.horizontalOffset = 0;
        t.verticalOffset = 0;

        //1)如果是on,则必定增加                
        if (t.width == 'auto' && t.verticalScrollPolicy == 'auto' && isInt(t.height)) {//必须是auto,才有可能附加滚动条偏移            
            if (t.getLayoutBox().height < t._childrenMinHeight) {
                t.horizontalOffset = 17;
            }
        }

        if (t.height == 'auto' && t.horizontalScrollPolicy == 'auto' && isInt(t.width)) {
            if (t.getLayoutBox().width < t._childrenMinWidth) {          //###这里的targetW,应该是减掉offset之后的尺寸(如果是绝对设置),而如果是
                t.verticalOffset = 17;
            }
        }

        if (t.horizontalScrollPolicy == 'on') {       //如果横向为on,则竖向必定加17
            t.verticalOffset = 17;
        } else if (t.horizontalScrollPolicy == 'off') {
            t.verticalOffset = 0;
        }
        if (t.verticalScrollPolicy == 'on') {
            t.horizontalOffset = 17;
        } else if (t.verticalScrollPolicy == 'off') {
            t.horizontalOffset = 0;
        }

        targetW += t.horizontalOffset;
        targetH += t.verticalOffset;
        //-----------------------------------------处理scroll的偏移-------------------------------//

        return [targetW, targetH];
    },

    doLayout: function (t, box) {
        var isPercent = Edo.isPercent;
        //var isInt = Edo.isInt;

        t.verticalGap = parseInt(t.verticalGap) || 0;
        t.horizontalGap = parseInt(t.horizontalGap) || 0;

        //调整x,y,t可能有滚动条,所以需要获取偏移
        //alert(t.scrollEl.dom.scrollLeft+":"+t.scrollEl.dom.scrollTop);
        if (t.scrollEl) {
            box.x -= (t.scrollEl.scrollLeft || 0);
            box.y -= (t.scrollEl.scrollTop || 0);
        }

        //传递进来的box是可能包含滚动条的,本布局器应该根据一些设置,调整最终的layout box            
        if (t.horizontalScrollPolicy == 'on') box.height -= 17;
        else if (t.width != 'auto' && t.horizontalScrollPolicy == 'auto') {
            if (t._childrenMinWidth > box.width) box.height -= 17;
        }
        if (t.verticalScrollPolicy == 'on') box.width -= 17;
        else if (t.height != 'auto' && t.verticalScrollPolicy == 'auto') {
            if (t._childrenMinHeight > box.height) box.width -= 17;
        }


        var children = t.getDisplayChildren();
        var num = children.length;

        var baseW = box.width, baseH = box.height;
        //baseW需要重新计算:    baseW = realHeight - (num-1)*HGap - (所有非百分比的realWidth之和)
        var percents = [], allPercent = 0;
        var cw = box.width;
        for (var i = 0; i < num; i++) {
            var c = children[i];
            if (isPercent(c.width)) {                        //如果子元素的高度是百分比,则不减掉                
                percents.push(c);
            } else {
                cw -= c.realWidth;
            }
        }
        baseW = cw - (num - 1) * t.horizontalGap;

        function markPercent() { //重新计算allPercent,以及每个child的__layoutPercent
            allPercent = 0;
            for (var i = 0; i < percents.length; i++) {
                var c = percents[i];
                allPercent += parseFloat(c.width);
            }

            for (var i = 0; i < percents.length; i++) {               //找出百分比设置的组件,将其百分比另行设置
                var c = percents[i];
                if (allPercent > 100) {
                    c.__layoutPercent = parseFloat(c.width) / allPercent;
                } else {
                    c.__layoutPercent = parseFloat(c.width) / 100;
                }
            }
        }
        markPercent(percents);

        //设置子元素的宽度和高度,依赖于百分比
        var x = box.x, y = box.y, w = box.width, h = box.height;
        //if(t.id == 'ct2') alert(x+":"+y+","+w+":"+h);       

        var allChildWidth = allChildHeight = 0;

        for (var i = 0; i < num; i++) {
            var child = children[i];
            if (isPercent(child.width)) {
                var _w = baseW * child.__layoutPercent;

                child.realWidth = _w;
                //                if(_w > child.realWidth) child.realWidth = _w;
                //                else{   //如果计算得到的值小于组件默认的最小尺寸,则不设置,并调整baseW和其他组件的percent
                //                    percents.remove(child);
                //                    baseW -= child.realWidth;
                //                    markPercent();
                //                }                
            }
            if (isPercent(child.height)) {
                var _h = baseH * parseFloat(child.height) / 100;
                child.realHeight = _h;
            }

            child.x = x;
            child.y = y;
            x += t.horizontalGap + child.realWidth;

            allChildWidth += child.realWidth;
            allChildHeight += child.realHeight;
        }
        t.allChildWidth = allChildWidth + t.horizontalGap * (num - 1);
        t.allChildHeight = allChildHeight + t.verticalGap * (num - 1);

        //进行align处理        
        var px = box.x + box.width - (x - t.horizontalGap);
        var py = box.y + box.height - (y - t.verticalGap);

        if (t.horizontalAlign == 'left') px = 0;
        else if (t.horizontalAlign == 'center') px = px / 2; //横向偏移        

        x = box.x, y = box.y;
        for (var i = 0; i < num; i++) {
            var child = children[i];
            child.x += px;

            if (t.verticalAlign == 'top') py = y;
            else if (t.verticalAlign == 'middle') py = y + h / 2 - child.realHeight / 2;
            else py = y + h - child.realHeight;
            child.y = py;
        }

    }
};
Edo.layouts['horizontal'] = Edo.layouts.HorizontalLayout;

Edo.layouts.VerticalLayout = {
    getDisplayChildren: function (t) {
        var cs = [];
        for (var i = 0, l = t.children.length; i < l; i++) {
            var c = t.children[i];
            if (c.visible && !c.popup) cs[cs.length] = c;   //visible=true,popup=false
        }
        return cs;
    },
    //确定容器尺寸, 进来的时候,所有子元素的realWidth和realHeight都被确定了 !!!
    measure: function (t) {  //布局器确定容器大小和子元素大小,并返回             
        var isPercent = Edo.isPercent;
        var isInt = Edo.isInt;

        //        t.verticalGap = parseInt(t.verticalGap);
        //        t.horizontalGap = parseInt(t.horizontalGap);

        var children = t.getDisplayChildren();
        var num = children.length;

        var targetW = t.width, targetH = t.height;  //

        var mw = 0;
        //if(t.id == 'fl')debugger
        for (var i = 0; i < num; i++) {
            var c = children[i];

            //如果父容器不是auto(表示父容器的尺寸不是由子元素决定的)
            var cw = t.width != 'auto' && isPercent(c.width) ? c.defaultWidth : c.realWidth;
            if (cw < c.minWidth) cw = c.minWidth;

            mw = mw > cw ? mw : cw;
        }

        var h = 0;
        for (var i = 0; i < num; i++) {
            var c = children[i];
            //如果子元素的高度是百分比的话,则只需要判断他的minHeight即可

            var ch = t.height != 'auto' && isPercent(c.height) ? c.defaultHeight : c.realHeight;
            if (ch < c.minHeight) ch = c.minHeight;

            h += t.verticalGap + ch;
        }
        h -= t.verticalGap;

        t._childrenMinWidth = mw;
        t._childrenMinHeight = h;

        //分两种:一种是确定的,如width=100,则不需要做任何处理;一种是不确定的:百分比,auto
        //1)宽度处理
        if (!isInt(t.width)) { //判断width元素:int, 100%, auto                
            mw = mw > t.defaultWidth ? mw : t.defaultWidth; //找出来的宽度,不能小于容器自定义的minWidth
            targetW = mw;
        }
        //2)高度处理
        if (!isInt(t.height)) {
            targetH = h;
            targetH = targetH > t.defaultHeight ? targetH : t.defaultHeight;
        }

        //-----------------------------------------处理scroll的偏移-------------------------------//   
        t.horizontalOffset = 0;
        t.verticalOffset = 0;

        //1)如果是on,则必定增加

        if (t.width == 'auto' && t.verticalScrollPolicy == 'auto' && isInt(t.height)) {//必须是auto,才有可能附加滚动条偏移            
            if (t.getLayoutBox().height < t._childrenMinHeight) {
                t.horizontalOffset = 17;
            }
        }

        if (t.height == 'auto' && t.horizontalScrollPolicy == 'auto' && isInt(t.width)) {
            if (t.getLayoutBox().width < t._childrenMinWidth) {          //###这里的targetW,应该是减掉offset之后的尺寸(如果是绝对设置),而如果是
                t.verticalOffset = 17;
            }
        }

        if (t.horizontalScrollPolicy == 'on') {       //如果横向为on,则竖向必定加17
            t.verticalOffset = 17;
        } else if (t.horizontalScrollPolicy == 'off') {
            t.verticalOffset = 0;
        }
        if (t.verticalScrollPolicy == 'on') {
            t.horizontalOffset = 17;
        } else if (t.verticalScrollPolicy == 'off') {
            t.horizontalOffset = 0;
        }

        targetW += t.horizontalOffset;
        targetH += t.verticalOffset;
        //-----------------------------------------处理scroll的偏移-------------------------------//

        return [targetW, targetH];
        //容器得到一个布局器通过自身布局规则建议的尺寸值:realWidth,realHeight.这两个值会在doLayout内被修正,得到一个最终的值.
        //如果本容器是顶级容器,则最终尺寸就被确定了;如果不是顶级容器,则本方法获得的是一个最小尺寸参考值,最终尺寸由父容器和本容器的尺寸配置生成.
    },
    //#####doLayout
    doLayout: function (t, box) {    //容器的尺寸已经被最终确认下来了,针对box进行定位和尺寸百分比设置     
        var isPercent = Edo.isPercent;
        //var isInt = Edo.isInt;

        //        t.verticalGap = parseInt(t.verticalGap);
        //        t.horizontalGap = parseInt(t.horizontalGap);

        //调整x,y,t可能有滚动条,所以需要获取偏移
        //alert(t.scrollEl.dom.scrollLeft+":"+t.scrollEl.dom.scrollTop);
        if (t.scrollEl) {
            box.x -= (t.scrollEl.scrollLeft || 0);
            box.y -= (t.scrollEl.scrollTop || 0);
        }

        //传递进来的box是可能包含滚动条的,本布局器应该根据一些设置,调整最终的layout box
        if (t.horizontalScrollPolicy == 'on') box.height -= 17;
        else if (t.width != 'auto' && t.horizontalScrollPolicy == 'auto') {
            if (t._childrenMinWidth > box.width) box.height -= 17;
        }
        if (t.verticalScrollPolicy == 'on') box.width -= 17;
        else if (t.height != 'auto' && t.verticalScrollPolicy == 'auto') {
            if (t._childrenMinHeight > box.height) box.width -= 17;
        }

        var children = t.getDisplayChildren();
        var num = children.length;

        var baseW = box.width, baseH = box.height;
        //baseH需要重新计算:    baseH = realHeight - (num-1)*VGap - (所有非百分比的realHeight之和)
        var percents = [], allPercent = 0;
        var ch = box.height;

        for (var i = 0; i < num; i++) {
            var c = children[i];
            if (isPercent(c.height)) {                        //如果子元素的高度是百分比,则不减掉                
                percents[percents.length] = c;
            } else {
                ch -= c.realHeight;
            }
        }
        baseH = ch - (num - 1) * t.verticalGap;

        //if(t.id == 'ct')debugger

        function markPercent() {
            allPercent = 0;
            for (var i = 0; i < percents.length; i++) {
                var c = percents[i];
                allPercent += parseFloat(c.height);
            }

            for (var i = 0; i < percents.length; i++) {               //找出百分比设置的组件,将其百分比另行设置
                var c = percents[i];
                if (allPercent > 100) {
                    c.__layoutPercent = parseFloat(c.height) / allPercent;
                } else {
                    c.__layoutPercent = parseFloat(c.height) / 100;
                }
            }
        }
        markPercent();

        //----------------具体设置开始
        //设置子元素的宽度和高度,依赖于百分比
        var x = box.x, y = box.y, w = box.width, h = box.height;
        //if(t.id == 'ct1') alert(x+":"+y+","+w+":"+h);

        var allChildWidth = allChildHeight = 0;
        for (var i = 0; i < num; i++) {
            var child = children[i];
            if (isPercent(child.width)) {
                var _w = baseW * parseFloat(child.width) / 100;
                child.realWidth = _w;
            }
            if (isPercent(child.height)) {
                var _h = baseH * child.__layoutPercent;

                child.realHeight = _h;
                //                if(_h > child.realHeight) child.realHeight = _h;
                //                else{
                //                    percents.remove(child);
                //                    baseH -= child.realHeight;
                //                    markPercent();
                //                }
            }

            child.x = x;
            child.y = y;
            y += t.verticalGap + child.realHeight;

            allChildWidth += child.realWidth;
            allChildHeight += child.realHeight;
        }
        t.allChildWidth = allChildWidth + t.horizontalGap * (num - 1);
        t.allChildHeight = allChildHeight + t.verticalGap * (num - 1);

        //进行align处理        
        var px = box.x + box.width - (x - t.horizontalGap);
        var py = box.y + box.height - (y - t.verticalGap);
        if (t.verticalAlign == 'top') py = 0;
        else if (t.verticalAlign == 'middle') py = py / 2;

        x = box.x, y = box.y;
        for (var i = 0; i < num; i++) {
            var child = children[i];

            child.y += py;
            if (t.horizontalAlign == 'left') px = x;
            else if (t.horizontalAlign == 'center') px = x + w / 2 - child.realWidth / 2;
            else px = x + w - child.realWidth;
            child.x = px;
        }
    }
};
Edo.layouts['vertical'] = Edo.layouts.VerticalLayout;

Edo.layouts.ViewStackLayout = {
    getDisplayChildren: function (t) {
        var cs = [];
        var c = t.children[t.selectedIndex];
        if (c) cs.push(c);
        return cs;
    },
    measure: function (t) {
        var c = t.children[t.selectedIndex] || {};
        var w = c.realWidth || 0, h = c.realHeight || 0;
        return [w > t.realWidth ? w : t.realWidth, h > t.realHeight ? h : t.realHeight];
    },
    doLayout: function (t, box) {
        var children = t.getChildren();
        var index = t.selectedIndex;
        var c = t.children[index];
        children.each(function (o, i) {
            o.x = box.x;
            o.y = box.y;
            if (i == index) {
                //o.setVisible(true);
                //                o.visible = true;
                if (o.el) o.el.style.display = 'block';
            } else {
                //o.setVisible(false);
                //                o.visible = false;
                if (o.el) o.el.style.display = 'none';
            }
        });
        c.xySet = true;
        if (Edo.isPercent(c.width)) c.realWidth = box.width * parseFloat(c.width) / 100;
        if (Edo.isPercent(c.height)) c.realHeight = box.height * parseFloat(c.height) / 100;

        c.measureSize();
    }
};
Edo.layouts['viewstack'] = Edo.layouts.ViewStackLayout;



Edo.controls.DataView = function () {

    Edo.controls.DataView.superclass.constructor.call(this);

    this.selecteds = [];
    this._setData([]);
}
Edo.controls.DataView.extend(Edo.controls.Control, {

    data: null,

    defaultWidth: 100,

    defaultHeight: 50,

    loading: false,             //当loading为true时,显示loadingText内容,false时进行tpl.run(data)
    loadingText: '',            //加载时显示文本
    emptyText: '',              //数据为空时显示文本

    itemSelector: 'e-dataview-item',             //item选择器: 样式类, item查找函数
    itemCls: 'e-dataview-item',
    elCls: 'e-dataview',

    verticalScrollPolicy: 'auto',
    horizontalScrollPolicy: 'auto',


    simpleSelect: false,

    multiSelect: false,

    enableSelect: true,

    repeatSelect: true,            //是否可以重复选择

    selectOnly: true,               //简单选择模式下, 只能选择一个


    selectAction: 'itemmousedown',

    overCls: 'e-dataview-item-over',            //高亮样式
    selectedCls: 'e-dataview-item-selected',    //选中样式

    enableTrackOver: false,            //是否支持高亮(true的时候,overClass才有用处)

    tpl:
'<%var data = this.data.view; %>\
<%for(var i=0,l=data.length; i<l; i++){ %>\
    <%var o = data[i]; %>\
    <%=this.getItemHtml(o, i)%>\
<%} %>',
    itemTpl: null,               //行模板

    selectModel: 'RowSelect',

    dragDropModel: 'RowDragDrop',
    dragDropAction: 'move',

    enableDragDrop: false,

    outRemoveFocus: true,
    _focusItem: null,

    valueField: 'value',        //值字段    
    defaultValue: '',
    delimiter: ',',

    set: function (o, value) {
        if (!o) return;
        if (typeof o === 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }

        if (o.data) {
            this._setData(o.data);
            delete o.data;
        }

        return Edo.controls.DataView.superclass.set.call(this, o);
    },
    _setValue: function (v) {
        this.setValue(v);
    },

    setValue: function (vv) {
        if (!Edo.isValue(vv)) vv = [];
        if (typeof vv == 'string') vv = vv.split(this.delimiter);
        if (!(vv instanceof Array)) vv = [vv];

        var fs = this.fireSelection;
        if (vv.length > 0) this.fireSelection = false;
        this.clearSelect();

        if (!this.multiSelect) {  //如果是单选,则只选中最后一个
            vv = [vv[vv.length - 1]];
        }

        var rs = [];
        vv.each(function (v) {
            var o = {};
            o[this.valueField] = v;  //{id: 1}
            var record = this.data.find(o);
            if (record) {
                rs.add(record);
            }
        }, this);

        this.fireSelection = fs;
        this.selectRange(rs, false);

    },
    getValue: function () {
        var rs = [];
        this.selecteds.each(function (o) {
            if (this.valueField == '*') rs.add(o);
            else rs.add(o[this.valueField]);
        }, this);
        return rs.join(this.delimiter);
    },
    createChildren: function (el) {
        Edo.controls.DataView.superclass.createChildren.call(this, el);
        this.scrollEl = this.viewport = this.el;
        //this.doScrollPolicy(this.el);
        this.ctEl = this.viewport;
    },
    init: function () {
        //拖拽和选择插件
        var o = Edo.create({ type: this.dragDropModel });
        if (o) this.addPlugin(o);

        var o = Edo.create({ type: this.selectModel });
        if (o) this.addPlugin(o);

        var o = new Edo.plugins.TableKeyboard();
        if (o) this.addPlugin(o);

        Edo.controls.DataView.superclass.init.call(this);
    },
    initEvents: function () {
        this.on('click', this._onClick, this);
        this.on('mousedown', this._onMouseDown, this);
        this.on('dblclick', this._onDblClick, this);
        this.on('mousemove', this._onMouseMove, this);
        this.on('mouseout', this._onMouseOut, this);
        Edo.controls.DataView.superclass.initEvents.call(this);

    },
    getViewportBox: function () {
        return Edo.util.Dom.getBox(this.viewport);
    },
    getCtEl: function () {
        return this.ctEl;
    },
    //从一个dom,找到此dom父级代表item的元素
    getItemElByChild: function (el) {
        var t = typeof this.itemSelector;
        if (t == 'string') {
            return Edo.util.Dom.findParent(el, this.itemSelector, 20);
        } else if (t == 'function') {
            return this.itemSelector(el);
        }
    },
    getItemEl: function (record) {
        record = this.getRecord(record);
        var d = Edo.getDom(this.createItemId(record));
        return d;
    },
    addItemCls: function (record, cls) {
        var el = this.getItemEl(record);
        if (el) Edo.util.Dom.addClass(el, cls);
    },
    removeItemCls: function (record, cls) {
        var el = this.getItemEl(record);
        if (el) Edo.util.Dom.removeClass(el, cls);
    },
    getItemBox: function (record) {
        var itemEl = this.getItemEl(record);
        return Edo.util.Dom.getBox(itemEl);
    },
    //获得所有的item(dom)
    getItems: function () {
        if (!this._items) {
            this._items = Edo.util.Dom.getsbyClass(this.itemSelector, this.el);
        }
        return this._items;
    },
    //
    getItemIndex: function (item) {
        var items = this.getItems();
        return items.indexOf(item);
    },
    getByIndex: function (index) {
        var items = this.getItems();
        return items[index];
    },
    _onClick: function (e) {
        var el = this.fireItemEvent(e, 'click');
    },
    _onDblClick: function (e) {
        this.fireItemEvent(e, 'dblclick');
    },
    _onMouseDown: function (e) {
        this.fireItemEvent(e, 'mousedown');

        //        
        this.focus();
    },
    focus: function () {
        if (!this.focusElement) {
            this.focusElement = Edo.util.Dom.append(this.el, '<a class="e-focus" href="#"></a>');
        }
        var el = this.focusElement;
        try {
            setTimeout(function () {
                try {
                    el.focus();
                } catch (e) { }
            }, 100);
        } catch (e) { }
    },
    _onMouseMove: function (e) {
        var e = this.fireItemEvent(e, 'mousemove');

        if (e.within(this.viewport) && e.item) {
            this.focusItem(e.item);
        }
    },
    _onMouseOut: function (e) {

        if (this.outRemoveFocus) {
            this.blurItem(this._focusItem);
        }
    },
    getRecordByEvent: function (e) {
        var el = this.getItemElByChild(e.target);
        if (el) {
            return this.data.getById(this.getItemId(el.id));
        }
    },
    fireItemEvent: function (e, name) {

        var record = this.getRecordByEvent(e);
        if (record) {
            name = 'item' + name;
            e = Edo.apply(e, {
                type: name,
                source: this,
                item: record
            });
            this.fireEvent(name, e);
        }
        return e
    },
    enableScrollIntoView: true,
    focusItem: function (record) {

        if (!this.enableTrackOver) return;
        record = this.getRecord(record);
        if (!record) return;
        if (record == this._focusItem) return;
        this.blurItem(this._focusItem);
        var d = this.getItemEl(record);
        if (d) {
            Edo.util.Dom.addClass(d, this.overCls);
        }
        if (this.enableScrollIntoView) this.scrollIntoView(record);
        this._focusItem = record;
    },
    scrollIntoView: function (record) {
        var d = this.getItemEl(record);
        if (d) {
            Edo.util.Dom.scrollIntoView(d, this.scrollEl, false);
        }
    },
    blurItem: function (record) {
        record = this.getRecord(record);
        var items = record ? [this.getItemEl(record)] : this.getItems();
        for (var i = 0, l = items.length; i < l; i++) {
            var d = this.getItemEl(items[i]);
            if (d) Edo.util.Dom.removeClass(d, this.overCls);
        }
        this._focusItem = null;
    },

    getFocusItem: function () {
        return this._focusItem;
    },
    getRecord: function (record) {
        var type = Edo.type(record);
        if (type == 'number') {
            record = this.data.getAt(record);
        } else if (type == 'string') {
            record = this.data.getById(record);
        } else if (type == 'element') {
            record = this.data.getById(this.getItemId(record.id));
        }
        return record;
    },
    checkSelecteds: function () {
        for (var i = this.selecteds.length - 1; i >= 0; i--) {
            var o = this.selecteds[i];
            if (!this.data.source.contains(o)) {
                this.selecteds.removeAt(i);
            }
        }
    },

    isSelected: function (record) {
        return this.selecteds.contains(record);
    },

    getSelected: function () {
        return this.selecteds[this.selecteds.length - 1];
    },

    getSelecteds: function () {
        return this.selecteds.clone();
    },

    doDeselect: function (recoreds) {
        var doc = document;
        recoreds.each(function (o) {
            var d = doc.getElementById(this.createItemId(o));
            if (d) Edo.util.Dom.removeClass(d, this.selectedCls);
        }, this);
    },
    doSelect: function (recoreds) {
        var doc = document;
        recoreds.each(function (o) {
            var d = doc.getElementById(this.createItemId(o));
            if (d) Edo.util.Dom.addClass(d, this.selectedCls);
        }, this);
        if (recoreds.length > 0) {
            var record = recoreds[recoreds.length - 1];
            //var d = Edo.getDom(this.createItemId(record));
            //if(d && this.enableScrollIntoView) Edo.util.Dom.scrollIntoView(d, this.scrollEl,false);
        }
    },

    select: function (record) {//真正需要的是row id:record,dom

        record = this.getRecord(record);
        if (!record || record.enableSelect === false) return false;

        var isSelected = this.isSelected(record);
        if (isSelected && !this.repeatSelect) return true;   //如果已经选择过,则不继续选择
        //if(this.isSelected(record)) return true;   //如果已经选择过,则不继续选择

        if (this.fireSelection !== false) {
            if (this.fireEvent('beforeselectionchange', {
                type: 'beforeselectionchange',
                source: this,
                selected: record
            }) === false) return false;
        }

        if (!this.multiSelect) {
            this.doDeselect(this.selecteds);
            this.selecteds = [];
        }
        if (!this.isSelected(record)) {
            this.selecteds.add(record);
        }

        this.selected = this.getSelected();

        if (this.fireSelection !== false) {
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this,
                selected: this.selected
            });
            this.data.select(this.selected);

            this.doSelect(this.selecteds);

            //
            this.changeProperty('value', this.getValue(), true);
        }

        this.afterSelect();
        this._focusItem = null;
        return true;
    },

    deselect: function (record) {

        record = this.getRecord(record);
        if (!record) return false;
        if (!this.isSelected(record)) return false;

        if (this.fireSelection !== false) {
            if (this.fireEvent('beforeselectionchange', {
                type: 'beforeselectionchange',
                source: this,
                selected: this.selected,
                deselected: record
            }) === false) return false;
        }

        this.selecteds.remove(record);

        this.doDeselect([record]);

        this.selected = this.getSelected();

        if (this.fireSelection !== false) {
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this,
                selected: this.selected
            });
            this.data.select(this.selected);

            this.changeProperty('value', this.getValue(), true);
        }
        this.afterSelect();

        this._focusItem = null;
        return true;
    },
    afterSelect: function () {

    },

    selectRange: function (records, append) {
        var fs = this.fireSelection;
        this.fireSelection = false;
        if (append === false) {
            this.clearSelect();
        }
        if (!(records instanceof Array)) records = [records];
        for (var i = 0, l = records.length; i < l; i++) {
            var record = records[i];
            if (i == l - 1) {
                this.fireSelection = fs;     //最后一下: 激发事件+DOM操作+HASH存储            
            }
            this.select(record);
        }
        this.fireSelection = fs
    },

    deselectRange: function (records) {
        var fs = this.fireSelection;
        for (var i = 0, l = records.length; i < l; i++) {
            if (i == 0) this.fireSelection = false;
            if (i == l - 1) this.fireSelection = fs;
            this.deselect(records[i]);
        }
        this.fireSelection = fs;
    },
    selectAll: function () {
        this.selecteds = this.data[this.dataViewField];
        this.selected = this.getSelected();
        this.doSelect(this.selecteds);
    },
    deselectAll: function () {
        this.doDeselect(this.selecteds);
        this.selecteds = [];
        this.selected = null;
    },

    clearSelect: function () {
        this.deselectRange(this.selecteds.clone());
    },
    _setData: function (data) {
        if (typeof data === 'string') data = window[data];
        if (!data) data = [];
        if (data.componentMode != 'data') {
            if (!this.data) {
                data = new Edo.data.DataTable(data);
            } else {
                this.data.load(data);
                return;
            }
        }

        if (this.data && this.data.un) {
            this.data.un('datachange', this._onDataChanged, this);
            this.data.un('selectionchange', this._onDataSelectionChange, this);
            this.data.un('valid', this._onDataValid, this);
            this.data.un('invalid', this._onDataInvalid, this);
        }
        this.data = data;
        this.data.on('datachange', this._onDataChanged, this);
        this.data.on('selectionchange', this._onDataSelectionChange, this);
        this.data.on('valid', this._onDataValid, this);
        this.data.on('invalid', this._onDataInvalid, this);

        if (this.rendered) {
            this._onDataChanged({ action: 'refresh' });
        }

        //this.relayout('data');
    },
    _onDataValid: function (e) {

    },
    _onDataInvalid: function (e) {

    },
    _onDataSelectionChange: function (e) {
        if (e.selected) {
            if (this.isSelected(e.selected)) return;
            this.selectRange(e.selected, false);
        } else {
            this.clearSelect();
        }
    },
    enableDeferRefresh: true,
    _onDataChanged: function (e) {

        this.checkSelecteds();
        if (e) {

            switch (e.action) {
                case 'add':
                    this.insertItem(e.index, e.records, e);

                    break;
                case 'remove':
                    this.removeItem(e.records, e);

                    break;
                case 'resetfield':
                case 'reset':
                case 'update':
                    this.replaceItem([e.record], e);

                    break;
                case 'move':
                    this.moveItem(e.index, e.records, e);
                    break;
                case 'clear':
                case 'load':
                case 'refresh':
                    this.clearSelect();
                case 'filter':
                case 'sort':
                    if (this.enableDeferRefresh) this.deferRefresh();
                    else this.refresh();
                    break;
            }
        }
        this._items = null;

        //处理选中状态

        this.doSelect(this.selecteds);

        this.fixAutoSize();
        this.relayout('datachange');
    },
    createItemId: function (record) {
        return record ? this.id + '$' + record.__id : null;
    },
    getItemId: function (id) {
        if (id) return id.split('$')[1];
    },
    deferRefresh: function () {
        if (this.changeTimer) {
            clearTimeout(this.changeTimer);
            this.changeTimer = null;
        }
        this.changeTimer = this.refresh.defer(1, this, arguments);
    },

    refresh: function () {
        if (this.el) {
            this.fixAutoSize();
            this.viewport.innerHTML = this.createView();
            this._items = null;
            //this.relayout('refresh');         //不能用这个!!! 如果在syncSize中refresh就玩了
        }
        //渲染刷新事件
        this.fireEvent('refresh', {
            type: 'refresh',
            source: this
        });

        this.doSelect(this.selecteds);
    },
    createView: function () {
        var s = '';
        if (this.loading) {
            s = this.loadingText;
        } else {
            if (!this.data || this.data.getCount() == 0) {
                s = this.emptyText;
            } else if (this.tpl) {
                if (typeof this.tpl === 'string') this.tpl = new Edo.util.Template(this.tpl);
                s = this.tpl.run(this);
            }
        }
        return s;
    },
    getInnerHtml: function (sb) {
        sb[sb.length] = this.createView();
    },
    _setTpl: function (tpl) {
        if (typeof tpl === 'string') tpl = new Edo.util.Template(tpl);
        this.tpl = tpl;
    },
    _setItemTpl: function (tpl) {
        if (typeof tpl === 'string') tpl = new Edo.util.Template(tpl);
        this.itemTpl = tpl;
    },
    getItemHtml: function (record, index) {
        this.item = record;
        this.index = index;
        this.itemId = this.createItemId(record);
        var s = this.itemTpl.run(this);
        delete this.item;
        delete this.index;
        return s;
    },

    insertItem: function (index, records) {

        var target = this.getItems()[index];
        for (var i = 0, l = records.length; i < l; i++) {
            var s = this.getItemHtml(records[i], index + i);
            if (target) {
                Edo.util.Dom.before(target, s);
            } else {
                Edo.util.Dom.append(this.getCtEl(), s);
            }
        }
    },
    removeItem: function (records) {
        for (var i = 0, l = records.length; i < l; i++) {
            var d = this.getItemEl(records[i]);
            Edo.removeNode(d);
        }
    },
    replaceItem: function (records) {
        for (var i = 0, l = records.length; i < l; i++) {
            var record = records[i];
            if (!record) continue;
            var d = this.getItemEl(record);
            if (!d) continue;
            var index = this.data.indexOf(record);
            var s = this.getItemHtml(record, index);

            Edo.util.Dom.after(d, s);

            Edo.removeNode(d);
        }
    },
    moveItem: function (index, records) {

        var target = this.data.getAt(index);

        var t = this.getItemEl(target);
        for (var i = 0, l = records.length; i < l; i++) {
            var record = records[i];
            var ri = this.data.indexOf(record);
            var d = Edo.getDom(this.createItemId(record));
            if (!d) continue;
            if (t) {
                Edo.util.Dom.before(t, d);
            } else {
                Edo.util.Dom.append(this.getCtEl(), d);
            }
        }
    },
    destroy: function () {
        this.data.un('datachanged', this._onDataChanged, this);

        Edo.controls.DataView.superclass.destroy.call(this);
    }
});
Edo.controls.DataView.regType('dataview');
Edo.controls.DataView.prototype.viewRow = Edo.controls.DataView.prototype.focusItem;








Edo.lists.TableBody = function (table) {
    Edo.lists.TableBody.superclass.constructor.call(this);
    this.table = table;
    //    this.el = table.viewport;
    //    this.view = this.el.childNodes[3];
    //    this.lineview = this.el.lastChild;        

};
Edo.lists.TableBody.extend(Edo.core.Component, {
    //    _ansyView: function(){
    //        var t = this.table;
    //        if(t.headerVisible) this.el.style.top = t.headerHeight + 'px';
    //        else this.el.style.top = '0px';
    //        Edo.util.Dom.setSize(this.el, this.width, this.height);                 
    //    },
    _createView: function (sr, sc, er, ec, simple) {
        var t = this.table;

        if (sr < 0) sr = 0;
        if (sc < 0) sc = 0;
        if (er < 0 || er >= t.data.getCount()) er = t.data.getCount() - 1;
        if (ec < 0 || ec >= t.columns.length) ec = t.columns.length - 1;

        t.startColumn = sc;
        t.endColumn = ec;

        //                
        var data = t.data, view = data[t.dataViewField];
        var columns = t.columns;
        var sb = [];    //可以分两次刷新
        var top = 0;

        t.selectHash = {};
        t.selecteds.each(function (o) {
            t.selectHash[o.__id] = 1;
        });


        t.cellSelectHash = {};
        t.cellSelecteds.each(function (o) {
            t.cellSelectHash[o.record.__id + o.column.id] = 1;
        });

        sb[sb.length] = '<table class="e-table-table" cellspacing="0" cellpadding="0" border="0"><tbody>';

        for (var j = sr; j <= er; j++) {
            var r = view[j];
            t.getItemHtml(r, j, sb);
        }

        sb[sb.length] = '</tbody></table>';

        if (view.length == 0) {
            sb[sb.length] = '<div style="width:' + t.getColumnAllWidth() + 'px;height:1px;overflow:hidden;"></div>';
        }

        return sb.join('');
    }
    //    ,
    //    _updateView: function(){
    //        var t = this.table;
    //		this.view = Edo.replaceHtml(this.view, this._createView(t.startRow, t.startColumn, t.endRow, t.endColumn));				
    //		
    //		//1)
    ////		var sf = this;
    ////		//var frag = document.createDocumentFragment();
    ////		this.viewInnerHTML = this._createView(t.startRow, t.startColumn, t.endRow, t.endColumn);
    ////		
    ////		if(this.viewTimer) clearTimeout(this.viewTimer);
    ////		
    ////		this.viewTimer = setTimeout(function(){
    ////		    sf.view = Edo.replaceHtml(sf.view, sf.viewInnerHTML);				
    ////		}, 0);
    //		
    //		//frag.innerHTML = this._createView(t.startRow, t.startColumn, t.endRow, t.endColumn);
    //		
    ////		setTimeout(function(){
    ////		    //sf.view = Edo.replaceHtml(sf.view, sf._createView(t.startRow, t.startColumn, t.endRow, t.endColumn));				
    ////		    //debugger
    ////		    //sf.view.appendChild(frag);
    ////		    //document.body.appendChild(frag);
    ////		}, 0);
    //		
    //		
    //		//this.view.innerHTML = str;
    //		//!!!重要,如果直接把原有html全部删除的话,在IE下,会重复拉取icon图片.
    //    }
});

//function asyncInnerHTML(HTML, callback) {
//    var temp = document.createElement('div'),
//        frag = document.createDocumentFragment();
//    temp.innerHTML = HTML;
//    (function(){
//        if(temp.firstChild){
//            frag.appendChild(temp.firstChild);
//            setTimeout(arguments.callee, 0);
//        } else {
//            callback(frag);
//        }
//    })();
//}


Edo.lists.TableHeader = function (table) {
    Edo.lists.TableHeader.superclass.constructor.call(this);
    this.table = table;
};
Edo.lists.TableHeader.extend(Edo.core.Component, {
    _createView: function (sc, ec) {
        var t = this.table;
        if (sc < 0) sc = 0;
        if (ec < 0 || ec >= t.columns.length) ec = t.columns.length - 1;

        var columns = t.groupColumns;

        var id = t.id;
        var allColumnWidth = t.getColumnAllWidth() + 1000;

        function renderer(v, col) {
            //column:sortDir,sortType
            var s = '';
            if (col.sortDir) {
                if (isIE || isSafari) {
                    s = '&nbsp;&nbsp;&nbsp;';
                } else {
                    s = '&nbsp;&nbsp;';
                }
            }
            if (!v && v !== 0) v = '&nbsp;';
            return '<div class="e-table-header-cell-inner ' + (col.sortDir ? "e-table-sort-icon" : '') + '" style="overflow:hidden;line-height:' + col.height + 'px;font-size:12px;white-space: nowrap;">' + v + s + '</div>';
        }
        var headerStr = '<div class="e-table-header-row" style="overflow:hidden;width:' + allColumnWidth + 'px;height:' + ((t.columnDepth + 1) * t.columnHeight) + 'px;">';
        var sb = [];
        //alert(isBorderBox);   
        function createHeaderColumn(columns, left, top) {

            for (var i = 0, l = columns.length; i < l; i++) {
                var c = columns[i];
                if (c.visible === false) continue;

                var cw = c.width;
                //if(!t.isBorderBox) cw -= 1;

                var hasColumns = c.columns && c.columns.length > 0;
                var ch = hasColumns ? t.columnHeight : (t.columnDepth - c.depth + 1) * t.columnHeight;
                c.height = ch;

                var sortCls = '';
                if (c.sortDir == 'asc') sortCls = 'e-table-sort-desc';
                else if (c.sortDir == 'desc') sortCls = 'e-table-sort-asc';

                sb[sb.length] = '<div id=' + (id + "|" + c.id) + ' class="e-table-cell ' + t.headerCellCls + ' ' + (sortCls) + ' ' + (c.headerCls || '') + '" style="text-align:';
                sb[sb.length] = c.headerAlign || 'left';
                sb[sb.length] = ';position:absolute;left:';
                sb[sb.length] = left + 'px;top:';
                sb[sb.length] = top + 'px;width:';
                sb[sb.length] = cw + 'px;height:';
                sb[sb.length] = ch;
                sb[sb.length] = 'px;';
                sb[sb.length] = '">';

                var v = typeof (c.header) === 'function' ? c.header(c, t.data, t) : renderer(c.header, c);
                sb[sb.length] = Edo.isValue(v) ? v : '&nbsp;';

                sb[sb.length] = '</div>';

                if (hasColumns) {
                    createHeaderColumn(c.columns, left, top + t.columnHeight);
                }

                left += cw;

                //split resize column
                if (c.enableResize !== false && !hasColumns) {
                    sb[sb.length] = '<div id="split|' + c.id + '" class="e-table-split" style="left:' + (left - 2) + 'px;top:' + top + 'px;">&nbsp;</div>';
                }
            }
        }
        var left = isBorderBox ? 1 : -1;
        createHeaderColumn(columns, left, 0);

        headerStr += sb.join('') + '</div>';

        return headerStr;
    }
});


Edo.lists.TableFooter = function (table) {
    Edo.lists.TableFooter.superclass.constructor.call(this);
    this.table = table;
};
Edo.lists.TableFooter.extend(Edo.core.Component, {
    _createView: function (sc, ec) {
        var t = this.table;
        var sb = [];
        //        if(t.summaryRowVisible && t.summaryRowPosition == 'bottom'){           
        //            sb[sb.length] = t.createSummaryRow(sc, ec);
        //        }
        //        if(t.filterRowVisible && t.filterRowPosition == 'bottom'){           
        //            sb[sb.length] = t.createFilterRow(sc, ec);
        //        }
        return sb.join('');
    }
});

Edo.lists.Table = function () {
    Edo.lists.Table.superclass.constructor.call(this);




















    this.columns = [];
    this.cellSelecteds = [];
    this.invalidCells = {};

    this.defaultValue = [];
};
Edo.lists.Table.extend(Edo.controls.DataView, {


    dataViewField: 'view',


    startColumn: 0,
    endColumn: -2,
    startRow: 0,
    endRow: -2,

    enableRowCls: true,

    enableStripe: false,

    stripeCls: 'e-table-stripe',

    overCls: 'e-table-row-over',
    selectedCls: 'e-table-row-selected',
    itemCls: 'e-table-row',
    itemSelector: 'e-table-row',
    cellCls: 'e-table-cell',
    cellSelectedCls: 'e-table-cell-selected',
    cellDirtyCls: 'e-table-cell-dirty',
    headerCellCls: 'e-table-header-cell',

    elCls: 'e-table e-dataview e-div',

    scrolloffset: 18,

    verticalScrollPolicy: 'auto',

    horizontalScrollPolicy: 'auto',

    headerVisible: true,

    foolterVisible: false,

    verticalLine: true,

    horizontalLine: true,

    cellEditAction: 'cellmousedown',

    cellSelectAction: 'cellmousedown',

    rowSelectMode: 'single',            //single, multi

    cellSelectMode: 'single',           //single,multi

    enableCellSelect: false,

    enableCellEdit: true,

    cellDirtyVisible: true,

    columnWidth: 100,

    columnMinWidth: 20,

    columnMaxWidth: 2000,

    columnHeight: 24,

    enableColumnSort: false,

    defaultSortDir: 'asc',


    summaryRowVisible: false,

    summaryRowPosition: 'bottom',

    filterRowVisible: false,

    filterRowPosition: 'top',

    selectModel: 'TableSelect',
    dragDropModel: 'TableDragDrop',
    cellSelectModel: 'cellselect',
    cellEditModel: 'celledit',
    sortModel: 'tablesort',
    headerDragDropModel: 'headerdragdrop',
    headerSplitterModel: 'HeaderSplitter',


    enableColumnDragDrop: true,
    dragShadow: true,


    minEditorWidth: 30,

    minEditorHeight: 15,

    rowHeight: 20,

    minWidth: 100,

    minHeight: 80,

    headerHeight: 24,
    footerHeight: 24,
    viewCellId: true,

    nosetcolumnid: '不能设置重复的列id',

    headerClass: Edo.lists.TableHeader,
    bodyClass: Edo.lists.TableBody,
    footerClass: Edo.lists.TableFooter,


    autoExpandColumn: null,

    autoColumns: false,

    cellError: '此单元格验证错误',

    enableCellValid: true,

    cellValidAction: 'aftercelledit', //'beforesubmitedit',
    cellValidModel: 'cellvalid',

    showCellValid: true,

    cellInvalidCls: 'e-table-cell-invalid',

    valueField: 'data',

    setValue: function (v) {
        this.data.load(v);
    },
    getValue: function () {
        return this.data.source;
    },
    resetValue: function () {
        this.setValue(this.defaultValue);
    },
    markFormValue: function () {
        var value = this.getValue();
        if (!this.hidden && this.enableForm) {
            this.hidden = Edo.util.Dom.append(this.el, '<input type="hidden" name="' + this.name + '" value="" />');
        }
        if (this.hidden) this.hidden.value = Edo.util.JSON.encode(value);
    },

    _setRowSelectMode: function (value) {
        if (value != this.rowSelectMode) {
            this.rowSelectMode = value;
            if (this.rowSelectMode == 'multi') {
                this.multiSelect = true;
            } else {
                this.multiSelect = false;
            }
        }
    },
    _setMultiSelect: function (value) {
        if (value != this.multiSelect) {
            this.multiSelect = value;
            if (value == true) {
                this.rowSelectMode = 'multi';
            } else {
                this.rowSelectMode = 'single';
            }
        }
    },

    within: function (e) {      //传递一个事件对象,判断是否在此控件的点击区域内       
        var ae = this.activeEditor;
        if (ae && ae.editor.within(e)) {
            return true;
        }
        return Edo.lists.Table.superclass.within.call(this, e);
    },
    createChildren: function (el) {
        Edo.lists.Table.superclass.createChildren.call(this, el);

        if (this.horizontalLine) {
            this.addCls('e-table-horizontalline');
        }
        if (this.verticalLine) {
            this.addCls('e-table-verticalline');
        }

        this.initTable();

        this.refreshFilter();
        this.refreshSummary();
    },
    init: function () {

        var o = Edo.create({ type: this.cellSelectModel });
        if (o) this.addPlugin(o);

        var o = Edo.create({ type: this.cellEditModel });
        if (o) this.addPlugin(o);

        var o = Edo.create({ type: this.cellValidModel });
        if (o) this.addPlugin(o);

        var o = Edo.create({ type: this.sortModel });
        if (o) this.addPlugin(o);

        var o = Edo.create({ type: this.headerDragDropModel });
        if (o) this.addPlugin(o);

        var o = Edo.create({ type: this.headerSplitterModel });
        if (o) this.addPlugin(o);

        this.multiSelect = this.rowSelectMode == 'multi' ? true : false;

        Edo.lists.Table.superclass.init.call(this);

        this.on('cellvalid', this._oncellvalid, this, 0);
        this.on('cellinvalid', this._oncellinvalid, this, 0);
    },
    _onMouseDown: function (e) {
        this.fireItemEvent(e, 'mousedown');

        //        
        if (!Edo.util.Dom.findParent(e.target, 'e-table-filter-row')) {
            this.focus();
        } else {
            setTimeout(function () {
                e.target.focus();
            }, 150);
        }
    },
    _oncellvalid: function (e) {

        this.clearCellInvalid(e.record, e.column);
    },
    _oncellinvalid: function (e) {

        this.showCellInvalid(e.record, e.column, e.errorMsg);
    },
    fixAutoSize: function () {
        if (this.viewport) {
            if (this.autoWidth) {
                this.viewport.style.width = 'auto';
            }
            if (this.autoHeight) {
                this.viewport.style.height = 'auto';
            }
        }
        Edo.lists.Table.superclass.fixAutoSize.call(this);
    },
    measure: function () {
        Edo.lists.Table.superclass.measure.call(this);

        if (this.autoHeight) {
            var h = this.getHorizontalScrollOffset();
            this.realHeight += h;
        }
    },
    syncBox: function () {

        this.box = Edo.util.Dom.getBox(this.el, true);
        this.headerBox = Edo.util.Dom.getBox(this.headEl, true);
        var footerHeight = this.getFooterHeight();
        Edo.util.Dom.setSize(this.viewport, this.box.width, this.box.height - this.headerBox.height - footerHeight);

        var viewportScrollLeft = this.getViewportScrollLeft();
        this.footEl.scrollLeft = viewportScrollLeft;
        this.headEl.scrollLeft = viewportScrollLeft;

        //如果不是自适应系列属性, 则不需要调整
        //        var c = this.getColumn(this.autoExpandColumn);        
        //        if(!c && !this.autoColumns){
        //            return;
        //        }

        var viewBox = Edo.util.Dom.getBox(this.viewport, true);
        this.viewBox = viewBox;
        this.viewWidth = viewBox.width;
        this.viewHeight = viewBox.height;

        //静态表格
        this.syncColumns();
        if (this.rendered && this.viewport && this.viewport.firstChild && this.viewport.firstChild.rows) {
            var row = this.viewport.firstChild.rows[0];
            if (row) {
                for (var i = 0, l = this.columns.length; i < l; i++) {
                    var cell = row.cells[i];
                    if (!cell) continue;
                    var w = this.columns[i].width;
                    if (!this.isBorderBox) w -= 1;
                    if (w < 0) w = 0;
                    cell.style.width = w + 'px';
                }
                //Edo.util.Dom.repaint(this.viewport.firstChild);   
            }
            //暂存列宽信息, 如果不一致, 才更新表头                

            this.refreshHeader();
            this.refreshFooter();
        }

        this.headerBox = Edo.util.Dom.getBox(this.headEl, true);
        var footerHeight = this.getFooterHeight();
        Edo.util.Dom.setSize(this.viewport, this.box.width, this.box.height - this.headerBox.height - footerHeight);

    },
    initTable: function () {
        this.headEl = this.el.firstChild;
        this.headRowEl = this.el.firstChild.firstChild;
        this.viewport = this.el.childNodes[1];
        this.footEl = this.el.childNodes[2];
        this.scrollEl = this.viewport;
        this.ctEl = this.getCtEl();

        this.headEl.style.height = this.headerVisible ? 'auto' : '0px';
        this.headEl.style.display = this.headerVisible ? '' : 'none';

        this.footEl.style.display = this.foolterVisible ? '' : 'none';

        this.doScrollPolicy(this.scrollEl);

        Edo.util.Dom.on(this.viewport, 'scroll', this._onViewportScroll, this);
        Edo.util.Dom.on(this.headEl, 'scroll', this._onHeaderScroll, this);
    },

    getHeaderBox: function () {
        return this.headerBox;
    },

    getViewportBox: function () {
        return this.viewBox;
    },
    getCtEl: function () {
        return this.viewport.firstChild ? this.viewport.firstChild.tBodies[0] : null;
    },
    addRowCls: function (record, cls) {
        if (!record) return;
        this.addItemCls(record, cls);
        record.__cls = cls;
    },
    removeRowCls: function (record, cls) {
        if (!record) return;
        this.removeItemCls(record, cls);
        record.__cls = "";
    },
    _onDataChanged: function (e) {
        this.refreshSummary();

        Edo.lists.Table.superclass._onDataChanged.call(this, e);
        this.doSelectCell(this.cellSelecteds);

    },
    refresh: function (all) {
        if (!this.el) return;
        if (all === true) {
            this.refreshHeader();
            this.refreshFooter();
        }
        Edo.lists.Table.superclass.refresh.call(this, all);

        var viewportScrollLeft = this.getViewportScrollLeft();
        this.footEl.scrollLeft = viewportScrollLeft;
        this.headEl.scrollLeft = viewportScrollLeft;

        this.showAllCellInvalid();
    },
    getViewportScrollLeft: function () {
        return this.viewport ? this.viewport.scrollLeft : 0;
    },
    refreshHeader: function () {
        if (this.headRowEl) {
            Edo.removeNode(this.headRowEl);
        }
        var hd = this.getHeader();
        var headHtml = hd._createView(this.startColumn, this.endColumn);
        this.headRowEl = Edo.util.Dom.preend(this.headEl, headHtml);

        if (this.filterRowVisible && this.filterRowPosition == 'top') {
            this.refreshFilter(this.startColumn, this.endColumn);
        }
        if (this.summaryRowVisible && this.summaryRowPosition == 'top') {
            this.refreshSummary(this.startColumn, this.endColumn);
        }
    },
    refreshFooter: function () {
        if (this.filterRowVisible && this.filterRowPosition == 'bottom') {
            this.refreshFilter(this.startColumn, this.endColumn);
        }
        if (this.summaryRowVisible && this.summaryRowPosition == 'bottom') {
            this.refreshSummary(this.startColumn, this.endColumn);
        }
    },
    refreshFilter: function () {

        if (!this.filterRowVisible) return;
        var ct = this.filterRowPosition == 'bottom' ? this.footEl : this.headEl;

        var html = this.createFilterRow(0, this.columns.length);
        if (this.filterEl) {
            Edo.util.Dom.clearNodes(this.filterEl);
            Edo.removeNode(this.filterEl);
        }
        this.filterEl = Edo.util.Dom.append(ct, html);
        //filter

        for (var i = 0, l = this.columns.length; i < l; i++) {
            var c = this.columns[i];
            var cwidth = c.width;

            if (!this.isBorderBox) cwidth -= 1;

            var cw = cwidth; //left 3,right 3,border-right 1                                                
            cw -= 3;

            var el = Edo.getDom(this.id + "|" + c.id + '|filter');
            if (el && c.filter) {
                el = el.firstChild;
                Edo.util.Dom.clearNodes(el);
                el.innerHTML = '';
                c.filter.set('width', cw);
                c.filter.render(el);
            }
        }
    },
    refreshSummary: function () {
        if (!this.summaryRowVisible) return;
        var ct = this.summaryRowPosition == 'bottom' ? this.footEl : this.headEl;
        var html = this.createSummaryRow(0, this.columns.length);
        if (this.summaryEl) {
            Edo.removeNode(this.summaryEl);
        }
        this.summaryEl = Edo.util.Dom.append(ct, html);
    },

    fireItemEvent: function (e, name) {
        var cellEl = this.getHeaderCellElByChild(e.target);
        if (!cellEl) cellEl = this.getCellElByChild(e.target);
        if (cellEl) {
            e.column = this.getColumn(this.getCellId(cellEl.id));
            e.columnIndex = this.columns.indexOf(e.column);
        }
        e.cellEl = cellEl;
        e.record = this.getRecordByEvent(e);
        e.rowIndex = this.data.indexOf(e.record);

        var e = Edo.lists.Table.superclass.fireItemEvent.apply(this, arguments);

        if (e.within(this.headEl)) {
            e.type = 'header' + name;

            this.fireEvent(e.type, e);

            if (e.column) {
                var fn = e.column['onheader' + name];
                if (fn) fn.call(e.column, e);
            }
        } else if (e.within(this.viewport)) {
            e.type = 'beforebody' + name;
            if (this.fireEvent(e.type, e) !== false) {

                if (e.record && cellEl) {
                    e.type = 'cell' + name;
                    this.fireEvent(e.type, e);
                }

                if (e.column) {
                    var fn = e.column['on' + name];
                    if (fn) fn.call(e.column, e);
                }

                e.type = 'body' + name;
                this.fireEvent(e.type, e);
            }
        }


        return e;
    },
    //从一个dom,找到此dom父级代表item的元素
    getCellElByChild: function (el) {
        return Edo.util.Dom.findParent(el, this.cellCls, 20);
    },
    getCellEl: function (record, column) {
        record = this.getRecord(record);
        if (!column) return;
        var d = Edo.getDom(this.createCellId(record, column));
        return d;
    },
    getHeaderCellElByChild: function (el) {
        return Edo.util.Dom.findParent(el, this.headerCellCls, 20);
    },
    //id, index, column

    getColumn: function (id) {
        var t = typeof id;
        var c = id;
        if (t == 'string') {
            c = this.columnHash[id];
        } else if (t == 'number') {
            c = this.columns[id];
        } else if (c && c.target) {
            var cellEl = this.getCellElByChild(c.target);
            if (cellEl) {
                c = this.getColumn(this.getCellId(cellEl.id));
            }
        }
        return c;
    },
    getColumnById: function (id) {
        return this.getColumn(id);
    },

    getColumnIndex: function (column) {
        column = this.getColumn(column);
        return this.columns.indexOf(column);
    },

    getColumnAllWidth: function () {
        var allw = 0;
        this.columns.each(function (column) {
            allw += column.width;
        }, this);
        return allw;
    },
    getColumnsWidth: function (start, end) {
        var allw = 0;
        for (var i = start; i < end; i++) {
            var column = this.columns[i];
            allw += column.width;
        }
        return allw;
    },
    getRowAllHeight: function () {
        var allh = 0;
        this.data.each(function (row) {
            allh += row.__height || this.rowHeight;
        }, this);
        return allh;
    },
    _setScrollLeft: function (value) {
        if (this.getViewportScrollLeft != value && this.viewport) {
            this.viewport.scrollLeft = value;
        }
    },
    _setScrollTop: function (value) {
        this.viewport.scrollTop = value;
    },
    _onHeaderScroll: function (e) {
        var sleft = this.headEl.scrollLeft;
        if (sleft != this.getViewportScrollLeft) {
            this.set('scrollLeft', sleft);
        }
    },
    _onViewportScroll: function (e) {
        this.footEl.scrollLeft = this.headEl.scrollLeft = this.viewport.scrollLeft;

        this.submitEdit();
    },
    getBody: function () {
        if (!this.body) this.body = new this.bodyClass(this);
        return this.body;
    },
    getHeader: function () {
        if (!this.header) this.header = new this.headerClass(this);
        return this.header;
    },
    getFooter: function () {
        if (!this.footer) this.footer = new this.footerClass(this);
        return this.footer;
    },

    getHeaderHeight: function () {
        if (this.headEl) {
            this.headerHeight = Edo.util.Dom.getHeight(this.headEl);
        }
        return this.headerHeight;
    },
    getFooterHeight: function () {
        if (this.footEl) {
            this.footerHeight = Edo.util.Dom.getHeight(this.footEl);
        }
        return this.footerHeight;
    },
    getCellBox: function (record, column) {
        var cellEl = this.getCellEl(record, column);
        return Edo.util.Dom.getBox(cellEl);
    },
    getColumnBox: function (column) {
        column = this.getColumn(column);
        var el = this.getColumnEl(column);
        return Edo.util.Dom.getBox(el);
    },
    getInnerHtml: function (sb) {

        this.syncColumns();
        var hd = this.getHeader();
        sb[sb.length] = '<div class="e-table-header">';
        sb[sb.length] = hd._createView(0, this.columns.length);
        sb[sb.length] = '</div><div class="e-table-viewport">'
        sb[sb.length] = this.createView();
        sb[sb.length] = '</div>';
        sb[sb.length] = '<div class="e-table-footer">';
        sb[sb.length] = this.getFooter()._createView(0, this.columns.length);
        sb[sb.length] = '</div>';
    },
    createView: function () {
        var bd = this.getBody();
        //status = this.scrollLeft +":"+new Date()+":"+this.startRow+":"+this.startColumn+":"+this.endRow+":"+this.endColumn;
        var sr = this.startRow, er = this.endRow, sc = this.startColumn, ec = this.endColumn;
        if (sr < 0) sr = 0;
        if (sc < 0) sc = 0;
        if (er < 0 || er >= this.data.getCount()) er = this.data.getCount() - 1;
        if (ec < 0 || ec >= this.columns.length) ec = this.columns.length - 1;

        if (this.live) {
            sr = 0;
        }

        return bd._createView(sr, sc, er, ec);
    },
    columnChangedRefresh: true,

    _setColumns: function (columns) {

        var t = this;
        var columnWidth = this.columnWidth;
        var columnHeight = this.columnHeight;
        var minWidth = this.columnMinWidth;
        var enableColumnSort = this.enableColumnSort;

        var ids = this.columnHash = {};

        var cols = [];
        var hasGroupColumn = false;
        var columnDepth = 0;

        function doColumn(c, depth, visible) {
            if (columnDepth < depth) columnDepth = depth;
            c.depth = depth;
            c.width = parseInt(c.width);
            if (isNaN(c.width)) c.width = columnWidth;
            //}
            c._width = c.width;

            if (!c.height) c.height = columnHeight;
            else c.height = parseInt(c.height);

            if (!c.minWidth) c.minWidth = minWidth;
            else c.minWidth = parseInt(c.minWidth);

            if (!c.editIndex) c.editIndex = c.dataIndex;

            c.id = c.id || Edo.id(null, 'col-');
            if (ids[c.id]) throw new Error(this.nosetcolumnid);
            ids[c.id] = c;

            //renderer
            if (c.renderer) {
                if (typeof c.renderer === 'string') c.renderer = window[c.renderer];
            }
            //editor
            if (c.editor) {
                if (typeof c.editor === 'string') {
                    var ed = window[c.editor];
                    if (!ed) {
                        ed = Edo.create({ type: c.editor });
                    }
                    c.editor = ed;
                }
                var ed = c.editor;
                if (ed) {
                    ed.minWidth = 30;
                    c.editor = Edo.create(ed);
                    c.editor.owner = t;
                }
            }

            var filter = c.filter;
            if (filter) {
                c.filter = Edo.create(filter);

                c.filter.owner = t;
            }

            //enableSort, sortType, sortDir
            //c.sortType = c.sortType || String;  //默认是字符串类型            
            if (typeof c.enableSort == 'undefined') c.enableSort = enableColumnSort;

            if (c.headerText) {
                c.header = c.headerText;
            }

            if (c.columns) {  //
                c.groupColumn = true;
                hasGroupColumn = true;
                var colSpan = 0;
                for (var i = 0, l = c.columns.length; i < l; i++) {
                    var col = c.columns[i];
                    doColumn(col, depth + 1, c.visible);
                    col.groupid = c.id;
                    if (col.visible !== false) {
                        colSpan += col.colSpan;
                    }
                }
                c.colSpan = colSpan;



            } else {
                c.colSpan = 1;
                if (c.visible !== false && visible !== false) {
                    cols[cols.length] = c; //没有子columns,才算是实际的column,否则是groupColumn
                }
            }


        }
        for (var i = 0, l = columns.length; i < l; i++) {
            doColumn(columns[i], 0, true);
        }

        this.hasGroupColumn = hasGroupColumn;
        this.groupColumns = columns || [];
        this.columns = cols;
        this.columnDepth = columnDepth;

        for (var i = 0, l = cols.length; i < l; i++) {
            cols[i].index = i;
        }

        //计算每个列的colSpan, 是其下的最长列数
        //        var colSpan = 0;
        //        function getColSpan(c){
        //            
        //        }
        //        for(var i=0,l=columns.length; i<l; i++){
        //            colSpan = 0;
        //            getColSpan(columns[i]);
        //        }

        //this.doLayout();
        this.measureSize();

        this.syncColumns();

        this.startColumn = 0;
        this.endColumn = this.columns.length - 1;

        if (this.rendered && this.columnChangedRefresh) {

            this.refresh(true);
            this.syncBox();
            //this.deferRefresh();
        }
    },
    _onDataValid: function (e) {

        if (e.action == 'field') {
            var columns = this.getColumnsByDataIndex(e.field);
            columns.each(function (column) {
                this.fireEvent('cellvalid', {
                    type: 'cellvalid',
                    source: this,
                    record: e.record,
                    column: column,
                    field: column.dataIndex,
                    value: e.record[column.dataIndex]
                });
            }, this);

        } else {
            this.data.view.each(function (record) {
                this.columns.each(function (column) {
                    this.fireEvent('cellvalid', {
                        type: 'cellvalid',
                        source: this,
                        record: record,
                        column: column,
                        field: column.dataIndex,
                        value: record[column.dataIndex]
                    });
                }, this);
            }, this);
        }
    },
    _onDataInvalid: function (e) {

        if (e.action == 'field') {
            var columns = this.getColumnsByDataIndex(e.field);
            columns.each(function (column) {
                this.fireEvent('cellinvalid', {
                    type: 'cellinvalid',
                    errorMsg: e.errorMsg,
                    source: this,
                    record: e.record,
                    column: column,
                    field: column.dataIndex,
                    value: e.value
                });
            }, this);

        } else {
            for (var i = 0, l = e.errors.length; i < l; i++) {
                var error = e.errors[i];
                error.fields.each(function (datafield) {
                    var columns = this.getColumnsByDataIndex(datafield.name);
                    columns.each(function (column) {
                        this.fireEvent('cellinvalid', {
                            type: 'cellinvalid',
                            errorMsg: datafield.errorMsg,
                            source: this,
                            record: error.record,
                            column: column,
                            field: column.dataIndex,
                            value: datafield.value
                        });
                    }, this);
                }, this);
            }
        }
    },
    getVerticalScrollOffset: function () {
        var offset = 0;
        if (this.verticalScrollPolicy == 'on') {
            offset = this.scrolloffset;
        } else if (this.verticalScrollPolicy == 'off') {

        } else {
            if (!this.viewport) return 0;
            //width = Edo.util.Dom.getViewWidth(this.viewport) - 4 ;
            //if(this.viewport.scroll

            //var isScrollable = Edo.util.Dom.isScrollable(this.viewport, false);
            //if(isScrollable) width -= this.scrolloffset;
            var vh = Edo.util.Dom.getHeight(this.viewport, true);
            var th = Edo.util.Dom.getHeight(this.viewport.firstChild);
            if (th > vh) {
                offset = this.scrolloffset;
            }
        }
        return offset;
    },
    getHorizontalScrollOffset: function () {
        var offset = 0;
        if (this.horizontalScrollPolicy == 'on') {
            offset = this.scrolloffset;
        } else if (this.horizontalScrollPolicy == 'off') {

        } else {
            if (!this.viewport) return 0;
            //width = Edo.util.Dom.getViewWidth(this.viewport) - 4 ;
            //if(this.viewport.scroll

            //var isScrollable = Edo.util.Dom.isScrollable(this.viewport, false);
            //if(isScrollable) width -= this.scrolloffset;
            var vh = Edo.util.Dom.getWidth(this.viewport, true);
            var th = Edo.util.Dom.getWidth(this.viewport.firstChild);
            if (th > vh) {
                offset = this.scrolloffset;
            }
        }
        return offset;
    },
    //计算列的实际宽度值,以便撑满表格
    syncColumns: function () {
        var width = this.realWidth;
        if (this.viewport) {
            width = Edo.util.Dom.getWidth(this.viewport, true);
        }
        width -= this.getVerticalScrollOffset();
        var oldWidth = width;

        var allw = 0;
        var fixw = 0;

        var c = this.getColumn(this.autoExpandColumn);

        if (c || this.autoColumns) {
            for (var i = 0, l = this.columns.length; i < l; i++) {
                var column = this.columns[i];
                if (column.fixWidth) {
                    fixw += column._width;
                } else {
                    allw += column._width;
                }
            }
        }

        if (c) {
            width -= fixw;
            w = width - allw + c._width;
            if (w < this.columnMinWidth) w = this.columnMinWidth;
            else if (w > this.columnMaxWidth) w = this.columnMaxWidth;
            //c.width = Math.round(w);
            c.width = parseInt(w);

            if (c.width < c.minWidth) c.width = c.minWidth;
        } else if (this.autoColumns) {
            width -= fixw;
            this.columns.each(function (column) {
                if (column.fixWidth) {
                    column.width = column._width;
                } else {
                    //column.width = Math.round(column.width / allw * width);
                    column.width = parseInt(column._width / allw * width);
                }
                if (column.width < column.minWidth) column.width = column.minWidth;
            }, this);

            var xpWidth = fixw;
            this.columns.each(function (column) {
                xpWidth += column.width;
            });
            if (this.columns.length > 1) {
                var lastColumn = this.columns[this.columns.length - 1];
                lastColumn.width = lastColumn.width + (oldWidth - xpWidth);
            }
        }

        function sync(columns) {
            columns.each(function (c) {
                if (c.columns) {
                    sync(c.columns);
                    var w = 0;
                    c.columns.each(function (col) {
                        if (col.visible !== false) {
                            w += col.width;
                        }
                    });
                    c.width = w;
                }
            });
        }
        sync(this.groupColumns);
    },

    sortColumn: function (column, sortDir) {
        column = this.getColumn(column);
        if (!column) return;

        //sortDir = sortDir || column.sortDir;

        for (var i = 0, l = this.columns.length; i < l; i++) {
            var c = this.columns[i];
            if (c == column) {
                if (sortDir) c.sortDir = sortDir;
                else {
                    if (c.sortDir == 'asc') c.sortDir = 'desc';
                    else if (c.sortDir == 'desc') c.sortDir = 'asc';
                    else c.sortDir = this.defaultSortDir;
                }
            } else {
                delete c.sortDir;
            }
        }

        var mapping = column.dataIndex;
        var dir = column.sortDir;
        var sortType = column.sortType;

        this.data.sort(function (pre, next) {
            var r;
            if (sortType) {
                r = sortType(Edo.getValue(pre, mapping)) > sortType(Edo.getValue(next, mapping));
            } else {
                r = Edo.getValue(pre, mapping) > Edo.getValue(next, mapping);
            }
            if (dir == 'asc') return r;
            else return !r;
        });
    },
    removeColumn: function (c) {
        if (c.groupid) {
            var gc = this.getColumn(c.groupid);
            gc.columns.remove(c);
            if (gc.columns.length == 0) this.groupColumns.remove(gc);
            c.groupid = null;
        } else {
            this.groupColumns.remove(c)
        }
    },

    getColumns: function () {
        return this.groupColumns;
    },
    _getColumns: function () {
        return this.groupColumns;
    },
    getColumnsByDataIndex: function (dataIndex) {
        var columns = [];
        this.columns.each(function (column) {
            if (column.dataIndex == dataIndex) {
                columns.add(column);
            }
        });
        return columns;
    },
    insertColumn: function (target, col, insert) {
        if (Edo.isNumber(target)) target = this.columns[target];

        this.removeColumn(col, false);

        var gc, index;
        if (target.groupid) {
            gc = this.getColumn(target.groupid);
            index = gc.columns.indexOf(target);
            gc = gc.columns;
        } else {
            gc = this.groupColumns;
            index = gc.indexOf(target);
        }

        if (insert == 'preend') {
            gc.insert(index, col);
        } else {
            gc.insert(index + 1, col);
        }
    },
    createCellId: function (record, column) {
        return this.id + '|' + record.__id + '|' + column.id;
    },
    createColumnId: function (column) {
        return this.id + '|' + column.id;
    },
    getCellId: function (id) {
        if (id) {
            ids = id.split('|');
            id = ids[ids.length - 1];
        }
        return id;
    },
    getColumnByEvent: function (e) {
        var cellEl = this.getHeaderCellElByChild(e.target);
        if (!cellEl) cellEl = this.getCellElByChild(e.target);
        if (cellEl) {
            return this.getColumn(this.getCellId(cellEl.id));
        }
    },
    getColumnEl: function (column) {
        column = this.getColumn(column);
        var d = Edo.getDom(this.createColumnId(column));
        return d;
    },
    scrollIntoView: function (record, column) {
        var record = this.getRecord(record), column = this.getColumn(column);
        if (!column) return;
        var d = this.getItemEl(record);
        var el = this.getCellEl(record, column);
        if (d || el) {
            Edo.util.Dom.scrollIntoView(el || d, this.scrollEl, el ? true : false);
        }
    },

    getCellSelected: function () {
        var cell = this.cellSelecteds[this.cellSelecteds.length - 1];
        if (cell) cell.cell = cell.record[cell.column.dataIndex];
        return cell;
    },

    getCellSelecteds: function () {
        var cs = this.cellSelecteds;
        var cells = []; //row, column
        cs.each(function (id) {
            var rc = id.split('|');
            var cell = {
                record: this.data.getById(rc[0]),
                column: this.getColumn(rc[1])
            };
            cell.cell = cell.row[cell.column.dataIndex];

            cells.add(cell);
        }, this);

        return cells;
    },
    doSelectCell: function (cells) {
        cells.each(function (o) {
            var d = Edo.getDom(this.createCellId(o.record, o.column));
            if (d) Edo.util.Dom.addClass(d, this.cellSelectedCls);
        }, this);
    },
    doDeselectCell: function (cells) {
        cells.each(function (o) {
            var d = Edo.getDom(this.createCellId(o.record, o.column));
            if (d) Edo.util.Dom.removeClass(d, this.cellSelectedCls);
        }, this);
    },

    isCellSelected: function (record, column) {
        record = this.getRecord(record);
        column = this.getColumn(column);

        for (var i = 0, l = this.cellSelecteds.length; i < l; i++) {
            var c = this.cellSelecteds[i];
            if (c.record == record && c.column == column) {
                return true;
            }
        }
    },

    selectCell: function (record, column) { //    
        if (!this.enableCellSelect) return;

        record = this.getRecord(record);
        column = this.getColumn(column);
        if (!record) return;

        if (this.isCellSelected(record, column) && !this.repeatSelect) return false;

        var cell = { record: record, column: column };

        if (this.fireSelection !== false) {
            if (this.fireEvent('beforecellselectionchange', {
                type: 'beforecellselectionchange',
                source: this,
                record: record,
                column: column,
                value: Edo.getValue(record, column.dataIndex),
                cellSelected: cell
            }) === false) return false;
        }

        if (this.cellSelectMode == 'single') {
            this.doDeselectCell(this.cellSelecteds);
            this.cellSelecteds = [];
        }

        this.cellSelecteds.add(cell);
        this.doSelectCell([cell]);

        this.cellSelected = this.getCellSelected();

        if (this.fireSelection !== false) {
            this.fireEvent('cellselectionchange', {
                type: 'cellselectionchange',
                source: this,
                value: Edo.getValue(record, column.dataIndex),
                record: record,
                column: column,
                cellSelected: this.cellSelected
            });
        }
    },

    deselectCell: function (record, column) {

        record = this.getRecord(record);
        column = this.getColumn(column);

        if (!this.isCellSelected(record, column)) return false;

        var cell = {
            record: record,
            column: column
        };

        if (this.fireSelection !== false) {
            if (this.fireEvent('beforecellselectionchange', {
                type: 'beforecellselectionchange',
                source: this,
                record: record,
                column: column,
                cell: record[column.dataIndex],
                decellSelected: cell
            }) === false) return false;
        }

        for (var i = 0, l = this.cellSelecteds.length; i < l; i++) {
            var c = this.cellSelecteds[i];
            if (c.record == cell.record && c.column == cell.column) {
                this.cellSelecteds.removeAt(i);
                break;
            }
        }

        this.doDeselectCell([cell]);

        if (this.fireSelection !== false) {
            this.fireEvent('cellselectionchange', {
                type: 'cellselectionchange',
                source: this,
                unselected: record
            });
        }
        return true;
    },

    selectCellRange: function (cells, append) {
        var fs = this.fireSelection;
        this.fireSelection = false;
        if (append === false) {
            this.clearCellSelect();
        }
        for (var i = 0, l = cells.length; i < l; i++) {
            if (i == l - 1) this.fireSelection = fs;
            var cell = cells[i];
            this.selectCell(cell.record, cell.column);
        }
        this.fireSelection = fs;
    },

    deselectCellRange: function (cells) {
        var fs = this.fireSelection;
        this.fireSelection = false;
        for (var i = 0, l = cells.length; i < l; i++) {
            if (i == l - 1) this.fireSelection = fs;
            var cell = cells[i];
            this.deselectCell(cell.record, cell.column);
        }
        this.fireSelection = fs;
    },

    clearCellSelect: function () {
        this.deselectCellRange(this.cellSelecteds.clone());
    },

    beginEdit: function (record, column, editor) {
        this.doBeginEdit.defer(50, this, arguments);
    },
    beginNextEdit: function () {
        var ae = this.activeEditor;
        if (!ae) return false;
        var rowIndex = ae.rowIndex, columnIndex = ae.columnIndex;
        columnIndex += 1;
        if (columnIndex >= this.columns.length) {
            rowIndex += 1;
            columnIndex = 0;
            if (rowIndex >= this.data.getCount()) {
                rowIndex = this.data.getCount() - 1;
                columnIndex = this.columns.length - 1;
            }
        }
        this.submitEdit();
        this.selectCell(rowIndex, columnIndex);
        this.beginEdit(rowIndex, columnIndex);
    },
    doBeginEdit: function (record, column, editor) {

        record = this.getRecord(record);
        column = this.getColumn(column);

        if (!record || !column) return false;

        this.submitEdit();      //首先截止

        var rowIndex = this.data.indexOf(record);
        var columnIndex = this.columns.indexOf(column);

        column.editor = Edo.create(column.editor);
        editor = editor || column.editor;

        //如果不能编辑
        if (column.enableEdit === false || !editor) {
            if (Edo.isValue(column.forId)) {          //如果有forId,则进行另外的cell编辑
                col = this.getColumnIndex(this.getColumn(column.forId));

                column = this.getColumn(column.forId);
                return this.beginEdit(record, column);
            }
            return false;
        }

        //如果行被编辑enableEdit === false,则不进行编辑                
        if (record.enableEdit === false) return;
        if (!Edo.isValue(column.editIndex)) column.editIndex = column.dataIndex;
        var value = Edo.getValue(record, column.editIndex);

        if (column.editIndex == '*') value = record;

        var cellbox = this.getCellBox(record, column);
        var e = {
            type: 'beforecelledit',
            source: this,

            rowIndex: rowIndex,
            record: record,
            columnIndex: columnIndex,
            column: column,
            field: column.dataIndex,

            cellbox: cellbox,
            editor: editor,
            value: value
        };
        //组织编辑,替换编辑器(value)

        if (this.fireEvent('beforecelledit', e) === false) return false;

        //保存当前编辑器数据对象
        this.activeEditor = e;

        editor = e.editor;

        editor.owner = this;    //设置编辑器和表格的所属关系

        editor.minWidth = this.minEditorWidth;
        editor.minHeight = this.minEditorHeight;

        //editor.resetValue();

        editor.startEdit(value, cellbox.x, cellbox.y, cellbox.width, cellbox.height);

        e.type = 'celledit';
        this.fireEvent('celledit', e);
    },
    getEditRecord: function () {
        return this.activeEditor ? this.activeEditor.record : null;
    },

    submitEdit: function (data) {

        var ed = this.activeEditor;
        if (!ed) return;

        var rowIndex = ed.rowIndex,
            columnIndex = ed.columnIndex,
            column = this.columns[columnIndex],
            field = ed.field,
            editor = ed.editor,
            value = ed.value,
            record = ed.record;

        var data2 = editor.completeEdit(); // editor.getEditValue();
        data = Edo.isValue(data) ? data : data2;

        this.activeEditor = null;

        var oldValue = Edo.getValue(record, field);
        if (column.editIndex == '*') oldValue = record; //如果有editIndex,并且为'*',则...

        var e = {
            type: 'beforesubmitedit',
            source: this,
            rowIndex: rowIndex,   //行索引
            record: record,
            columnIndex: columnIndex,    //列配置对象
            column: column,
            field: field,
            editor: editor,
            oldValue: oldValue,
            value: data //新值
        };

        if (!Edo.isValue(value) && !Edo.isValue(e.value)) {
            value = e.value;
        }
        if (e.value != value && this.fireEvent('beforesubmitedit', e) !== false) {
            //            this.activeEditor = null;
            //            editor.completeEdit();

            data = e.value;
            if (column.editIndex == '*') {
                //this.data.updateRecord.defer(80, this.data, [record, data]);
                this.data.updateRecord(record, data);
            } else {
                this.data.update(record, field, data);
            }
            e.type = 'submitedit';
            this.fireEvent('submitedit', e);
        }

        e.type = 'aftercelledit';
        this.fireEvent('aftercelledit', e);

        this.showAllCellInvalid();

        this.focus();
    },

    cancelEdit: function () {

        var ed = this.activeEditor;
        if (!ed) return;
        ed.editor.completeEdit();

        var column = ed.column;
        var record = ed.record;

        var e = {
            type: 'aftercelledit',
            source: this,
            rowIndex: ed.rowIndex,   //行索引
            record: record,
            columnIndex: ed.columnIndex,    //列配置对象
            column: column,
            field: column.dataIndex,
            editor: ed.editor,
            value: Edo.getValue(record, column.dataIndex)
        };
        this.fireEvent('aftercelledit', e);

        this.activeEditor = null;

        this.showAllCellInvalid();

        this.focus();
    },

    valid: function (records, all) {
        var ret = Edo.lists.Table.superclass.valid.call(this, all);
        if (ret == true) {

            ret = this.validRow(records, all);
        }
        return ret;
    },

    validRow: function (records, all) {
        if (!records || records === true) records = this.data.view;
        if (!(records instanceof Array)) records = [records];
        var ret = true;

        var isBreak = false;
        records.each(function (record) {
            this.columns.each(function (column) {
                var r = this.validCell(record, column);
                if (!r) {
                    ret = r;
                    if (!all) {
                        isBreak = true;
                        return false;
                    }
                }
            }, this);

            if (isBreak) {
                return false;
            }
        }, this);
        return ret;
    },

    validCell: function (record, column, value, validFn) {
        record = this.getRecord(record);
        column = this.getColumn(column);
        value = typeof value !== 'undefined' ? value : Edo.getValue(record, column.dataIndex);
        validFn = validFn || column.valid;
        if (!validFn) return true;

        if (typeof validFn !== 'function') {
            eval('validFn = function(value){' + validFn + '}');
        }
        if (!validFn) return true;
        var ret = validFn.call(this, value, record, column, this.data.indexOf(record), this.data, this);
        if (ret === true || ret === undefined) {
            this.fireEvent('cellvalid', {
                type: 'cellvalid',
                source: this,
                record: record,
                column: column,
                field: column.dataIndex,
                value: value
            });
            //this.clearCellInvalid(record, column); 

            return true;
        }
        else {
            var msg = ret === false ? this.cellError : ret;
            this.fireEvent('cellinvalid', {
                type: 'cellinvalid',
                errorMsg: msg,
                source: this,
                record: record,
                column: column,
                field: column.dataIndex,
                value: value
            });
            //this.showCellInvalid(record, column, msg);
            return false;
        }
    },
    getInvalidTip: function () {
        if (!this.showCellValid) return;
        if (!this.invalidTip) {
            this.invalidTip = Edo.managers.TipManager.reg({
                target: this,
                cls: 'e-invalid-tip',
                showTitle: false,
                autoShow: true,
                autoHide: true,
                trackMouse: true,
                mouseOffset: [0, 0],
                ontipshow: this.onCellTipShow.bind(this)
            });
        }
        return this.invalidTip;
    },
    onCellTipShow: function (e) {
        var cellEl = this.getCellElByChild(e.target);
        if (!cellEl) return false;

        var column = this.getColumn(this.getCellId(cellEl.id));
        var record = this.getRecordByEvent(e);
        if (!record || !column) return false;
        var msg = this.isInvalidCell(record, column);

        var cellbox = this.getCellBox(record, column);
        e.xy = [cellbox.x + cellbox.width + 2, cellbox.y];

        var box = this._getBox(true);
        if (e.xy[0] > box.right) e.xy[0] = box.right;

        if (msg) {
            this.invalidTip.html = msg;
        } else {
            return false;
        }


    },

    showAllCellInvalid: function () {
        var invalidCells = this.invalidCells;
        for (var id in invalidCells) {
            var ids = id.split('|');
            var record = this.data.getById(ids[0]);
            if (!record) {
                delete invalidCells[id];
                continue;
            }
            this.showCellInvalid(ids[0], ids[1], invalidCells[id]);
        }
    },

    showCellInvalid: function (record, column, msg) {
        this.invalidCells[record.__id + "|" + column.id] = msg;
        var el = this.getCellEl(record, column);
        if (el) Edo.util.Dom.addClass(el, this.cellInvalidCls);

        this.getInvalidTip();
    },

    clearCellInvalid: function (record, column) {
        delete this.invalidCells[record.__id + "|" + column.id];
        var el = this.getCellEl(record, column);
        if (el) Edo.util.Dom.removeClass(el, this.cellInvalidCls);
    },
    //清除错误验证
    clearInvalid: function () {
        Edo.lists.Table.superclass.clearInvalid.call(this);

    },

    isInvalidCell: function (record, column) {
        record = this.getRecord(record);
        column = this.getColumn(column);
        return this.invalidCells[record.__id + "|" + column.id];
    },
    columnRenderer: null,
    getItemHtml: function (record, index, sb) {
        if (!record) return;
        var ret = !sb;
        if (!sb) sb = [];

        var t = this, data = this.data;
        var columns = t.columns;

        var h = record.__height - 1;
        if (isNaN(h)) {
            h = this.rowHeight; //'auto';
            if (h != 'auto') h = parseInt(h - 1) + 'px';
        }
        else h = h + 'px';

        var rowid = t.createItemId(record);
        //__style, __cls, rowRenderer
        //var rowCls = rowStyle = '';
        var rowClsIndex = rowStyleIndex = -1;

        sb[sb.length] = '<tr unselectable="on"  id="';
        sb[sb.length] = rowid;
        sb[sb.length] = '" class="' + t.itemCls + ' ';
        if (this.enableStripe && index % 2 == 1) sb[sb.length] = this.stripeCls + ' ';
        if (t.selectHash[record.__id]) sb[sb.length] = this.selectedCls + ' ';
        if (t.enable === false) sb[sb.length] = 'e-disabled ';
        rowClsIndex = sb.length;
        sb[sb.length] = '';
        sb[sb.length] = '" style="';
        rowStyleIndex = sb.length;
        sb[sb.length] = '';

        //sb[sb.length] = ';height:';			            
        //sb[sb.length] = h;	    
        //sb[sb.length] = ';width:';

        sb[sb.length] = ';width:';


        sb[sb.length] = t.viewWidth;
        sb[sb.length] = 'px;">';

        var left = 0;
        for (var i = this.startColumn, l = this.endColumn; i <= l; i++) {
            var c = columns[i];
            if (!c) continue;
            var cw = c.width;

            if (!this.isBorderBox) cw -= 1;


            var v = record[c.dataIndex];
            var renderer = c.renderer || this.columnRenderer;
            v = renderer ? renderer.call(c, v, record, c, index, data, t) : v;

            var cls = '';
            if (c.cls) cls += c.cls;

            var cellid = t.createCellId(record, c);

            if (t.viewCellId) {
                sb[sb.length] = '<td unselectable="on" id="' + cellid + '" class="';
            } else {
                sb[sb.length] = '<td unselectable="on" class="';
            }
            if (t.cellSelectHash[record.__id + c.id]) sb[sb.length] = this.cellSelectedCls + ' ';

            sb[sb.length] = t.cellCls + ' ';

            if (t.invalidCells[record.__id + "|" + c.id]) {
                sb[sb.length] = t.cellInvalidCls + ' ';
            }

            sb[sb.length] = cls;                        //cls

            if (index == this.startRow || (index == 0 && this.live)) {   //!!!这里有问题!!!                     
                sb[sb.length] = ' " style="width:';
                sb[sb.length] = cw;
                sb[sb.length] = 'px;height:';
            } else {
                sb[sb.length] = ' " style="height:';
            }
            if (i == this.startColumn) {
                sb[sb.length] = h;
                sb[sb.length] = 'px;text-align:';
            } else {
                sb[sb.length] = 'auto;text-align:';
            }
            sb[sb.length] = c.align || 'left';
            if (c.style) {				                //style
                sb[sb.length] = ';' + c.style + '">';
            } else {
                sb[sb.length] = ';">';
            }

            //e-table-cell-inner
            sb[sb.length] = '<div class="e-table-cell-inner ';
            if (t.cellDirtyVisible && data.isFieldModify(record, c.dataIndex)) {
                sb[sb.length] = this.cellDirtyCls + ' ';
            }
            sb[sb.length] = '" style="height:';
            sb[sb.length] = h;
            sb[sb.length] = ';';
            if (c.innerStyle) {
                sb[sb.length] = c.innerStyle;
            }
            sb[sb.length] = '">';

            if (v || v === 0 || v === false) {
                sb[sb.length] = v;
            } else {
                sb[sb.length] = '&nbsp;';
            }

            sb[sb.length] = '</div>';

            sb[sb.length] = '</td>';

        }
        sb[sb.length] = '</tr>';

        if (this.enableRowCls) {
            sb[rowClsIndex] = record.__cls || '';
            sb[rowStyleIndex] = record.__style || '';
        }

        if (ret) return sb.join('');
    },
    removeItem: function (records) {
        var zeroIndex = false;
        for (var i = 0, l = records.length; i < l; i++) {
            if (records[i].__index == 0) zeroIndex = true;
        }
        Edo.lists.Table.superclass.removeItem.apply(this, arguments);
        if (zeroIndex) {
            this.syncBox();
        }
    },
    createFilterRow: function (sc, ec) {
        var t = this;
        var id = t.id;
        if (sc < 0) sc = 0;
        if (ec < 0 || ec >= t.columns.length) ec = t.columns.length - 1;

        function renderer(v, col) {
            //column:sortDir,sortType
            var s = '';
            if (col.sortDir) {
                if (isIE || isSafari) {
                    s = '&nbsp;&nbsp;&nbsp;';
                } else {
                    s = '&nbsp;&nbsp;';
                }
            }
            if (!v && v !== 0) v = '&nbsp;';
            return '<div class="e-table-header-cell-inner ' + (col.sortDir ? "e-table-sort-icon" : '') + '" style="overflow:hidden;line-height:16px;font-size:12px;white-space: nowrap;">' + v + s + '</div>';
        }
        var allColumnWidth = t.getColumnAllWidth() + 1000;
        var columns = t.columns;
        var sb = [];
        sb[sb.length] = '<div class="e-table-filter-row" style="width:' + allColumnWidth + 'px;"><table class="e-table-table" cellspacing="0" cellpadding="0" border="0" ><tbody><tr>';
        var left = 0;
        for (var i = sc, l = ec; i <= l; i++) {
            var c = columns[i];
            var cwidth = c.width;

            if (!this.isBorderBox) cwidth -= 1;

            left += cwidth;

            var cw = cwidth;


            sb[sb.length] = '<td id="' + (id + "|" + c.id) + '|filter" class="e-table-cell ' + t.headerCellCls + ' ' + (c.headerCls || '') + '" style="width:' + cw + 'px;text-align:';
            sb[sb.length] = c.headerAlign || 'left';
            sb[sb.length] = ';overflow:">';
            var v = renderer('', c);
            sb[sb.length] = Edo.isValue(v) ? v : '&nbsp;';

            sb[sb.length] = '</td>';
        }

        sb[sb.length] = '</tr></tbody></table></div>';

        return sb.join('');
    },
    createSummaryRow: function (sc, ec) {

        var t = this;
        var id = t.id;
        if (sc < 0) sc = 0;
        if (ec < 0 || ec >= t.columns.length) ec = t.columns.length - 1;

        function renderer(v, col) {
            //column:sortDir,sortType
            var s = '';
            if (col.sortDir) {
                if (isIE || isSafari) {
                    s = '&nbsp;&nbsp;&nbsp;';
                } else {
                    s = '&nbsp;&nbsp;';
                }
            }
            if (!v && v !== 0) v = '&nbsp;';
            return '<div class="e-table-header-cell-inner ' + (col.sortDir ? "e-table-sort-icon" : '') + '" style="overflow:hidden;line-height:16px;font-size:12px;white-space: nowrap;">' + v + s + '</div>';
        }
        var allColumnWidth = t.getColumnAllWidth() + 1000;
        var columns = t.columns;
        var sb = [];
        sb[sb.length] = '<div class="e-table-summary-row" style="width:' + allColumnWidth + 'px;"><table class="e-table-table" cellspacing="0" cellpadding="0" border="0" ><tbody><tr>';
        var left = 0;

        for (var i = sc, l = ec; i <= l; i++) {
            var c = columns[i];
            var cwidth = c.width;

            if (!this.isBorderBox) cwidth -= 1;

            left += cwidth;

            var cw = cwidth;

            sb[sb.length] = '<td id="' + (id + "|" + c.id) + '|summary" class="e-table-cell ' + t.headerCellCls + ' ' + (c.headerCls || '') + '" style="width:' + cw + 'px;text-align:';
            sb[sb.length] = c.headerAlign || 'left';
            sb[sb.length] = ';overflow:hidden;">';
            var v = typeof (c.summary) === 'function' ? c.summary(c, t.data, t) : renderer(c.summary, c);
            sb[sb.length] = Edo.isValue(v) ? v : '&nbsp;';

            sb[sb.length] = '</td>';
        }

        sb[sb.length] = '</tr></tbody></table></div>';

        return sb.join('');
    },
    syncSize: function () {
        Edo.lists.Table.superclass.syncSize.apply(this, arguments);

        //Edo.util.Dom.setSize(this.el, this.realWidth, this.realHeight);

        //this.submitEdit();

        this.syncBox();

        var sf = this;
        setTimeout(function () {

            Edo.util.Dom.repaint(sf.el);
        }, 50);



    },
    destroy: function () {
        Edo.util.Dom.clearEvent(this.scrollEl);
        if (this.header) {
            this.header.destroy();
        }
        if (this.body) {
            this.body.destroy();
        }
        this.scrollEl = this.header = this.body = null;

        Edo.lists.Table.superclass.destroy.call(this);
    },
    isBorderBox: isBorderBox || isChrome || isSafari
});

Edo.lists.Table.regType('table');


Edo.lists.Table.createCheckColumn = function (o) {
    return Edo.apply({
        trueValue: true,
        falseValue: false,
        text: '',
        renderer: function (v, r, c, i, data, t) {
            v = v == c.trueValue;
            return '<div class="e-tree-checkbox" style="text-align:center;line-height:0px;height:18px;padding-left:0px;position:relative;"><div class="e-tree-check-icon  ' + (v ? "e-table-checked" : "") + '" style="position:absolute;width:15px;height:18px;left:50%;margin-left:-8px;">' + c.text + '</div></div>';
        },
        onclick: function (e) {
            if (Edo.util.Dom.findParent(e.target, 'e-tree-check-icon')) {
                var table = e.source;
                var column = e.column;
                var field = e.column.editIndex;
                var record = e.record;
                var value = e.record[field] == column.trueValue ? column.falseValue : column.trueValue;
                var rowIndex = table.data.indexOf(record);
                var columnIndex = table.columns.indexOf(column);

                var e = {
                    type: 'beforecelledit',
                    source: table,

                    rowIndex: rowIndex,
                    record: record,
                    columnIndex: columnIndex,
                    column: column,
                    field: field,

                    value: value
                };
                if (table.fireEvent('beforecelledit', e) === false) return false;

                table.data.update(record, field, value);

                if (this.onsubmitedit) this.onsubmitedit.call(e.source, e);

                e.type = 'celledit';
                table.fireEvent('celledit', e);
            }
        }
    }, o);
}

Edo.lists.Table.createRadioColumn = function (o) {
    return Edo.apply({
        valueField: 'id',
        displayField: 'name',
        renderer: function (v, r, c, i, data, t) {
            var sb = [];
            for (var i = 0, l = c.data.length; i < l; i++) {
                var o = c.data[i];
                var ov = o[c.valueField];
                sb.push('<span class="e-table-radio"><input ' + (ov == v ? "checked" : '') + ' value="' + ov + '"  type="radio" id="' + (t.id + r.__id + c.id) + '" /><label>' + o[c.displayField] + '</label></span>');
            }
            return sb.join('');
            //v = v == c.trueValue;
            //return '<div class="e-tree-checkbox" style="text-align:center;line-height:0px;height:18px;padding-left:0px;position:relative;"><div class="e-tree-check-icon  '+(v ? "e-table-checked" : "")+'" style="position:absolute;width:15px;height:18px;left:50%;margin-left:-8px;">'+c.text+'</div></div>';
        },
        onclick: function (e) {
            e.stopDefault();
            var table = e.source;
            var column = e.column;
            var field = e.column.editIndex;
            var record = e.record;
            var value = e.record[field];
            var rowIndex = table.data.indexOf(record);
            var columnIndex = table.columns.indexOf(column);

            if (e.target.id == table.id + record.__id + column.id) {
                var value = e.target.value;
                var e = {
                    type: 'beforecelledit',
                    source: table,

                    rowIndex: rowIndex,
                    record: record,
                    columnIndex: columnIndex,
                    column: column,
                    field: field,

                    value: value
                };
                if (table.fireEvent('beforecelledit', e) === false) return false;

                table.data.update(record, field, value);

                if (this.onsubmitedit) this.onsubmitedit.call(e.source, e);

                e.type = 'celledit';
                table.fireEvent('celledit', e);
            }
        }
    }, o);
}

Edo.lists.Table.createMultiColumn = function (o) {
    return Edo.apply({
        preid: Edo.id(),
        id: Edo.id(),
        checkCls: 'e-table-checkbox',
        checkedCls: 'e-table-checked',
        header: function () {
            return '<div id="' + this.preid + this.id + '" class="' + this.checkCls + ' ' + (this.checked ? this.checkedCls : '') + '"></div>';
        },
        width: 25,
        minWidth: 24,
        multi: true,
        enableResize: false,
        enableSort: false,
        fixWidth: true,
        renderer: function (v, r, c, i, data, t) {
            if (r.enableSelect != false) {
                return '<div class="' + this.checkCls + '"></div>';
            }
        },
        onheaderclick: function (e) {
            var c = this;
            var table = e.source;
            var id = this.preid + this.id;
            var d = Edo.getDom(id);

            if (d) {
                if (c.checked) {
                    c.checked = !c.checked;
                    //table.deselectRange(table.data[table.dataViewField]);
                    table.deselectAll();
                    if (d) Edo.util.Dom.removeClass(d, this.checkedCls);
                } else {
                    c.checked = !c.checked;

                    //table.selectRange(table.data[table.dataViewField]);
                    table.selectAll();

                    if (d) Edo.util.Dom.addClass(d, this.checkedCls);
                }
            }
        }
    }, o);
}

Edo.lists.Table.createSingleColumn = function (o) {
    o = Edo.apply({
        checkCls: 'e-table-radiobox',
        checkedCls: 'e-table-radiobox-checked'
    }, o);
    c = Edo.lists.Table.createMultiColumn(o);
    return c;
}



Edo.lists.Tree = function () {


    Edo.lists.Tree.superclass.constructor.call(this);

    this.on('beforebodymousedown', this._onbeforebodymousedownHandler, this, 0);
}
Edo.lists.Tree.extend(Edo.lists.Table, {

    elCls: 'e-tree e-table e-dataview e-div',


    treeColumn: '',
    treeColumnCls: 'e-tree-treecolumn',

    collapseCls: 'e-tree-collapse',
    expandCls: 'e-tree-expanded',

    dragDropModel: 'treedragdrop',
    enableColumnSort: false,

    enableStripe: false,

    _onbeforebodymousedownHandler: function (e) {

        var t = e.target;
        var r = e.record;
        if (e.button == Edo.util.MouseButton.left) {
            var nodeicon = Edo.util.Dom.hasClass(t, 'e-tree-nodeicon');

            if (nodeicon) {

                if (this.fireEvent('beforetoggle', {
                    type: 'beforetoggle',
                    source: this,
                    record: r,
                    row: r
                }) !== false) {

                    if (Edo.util.Dom.findParent(t, 'e-tree-collapse', 3)) {

                        this.submitEdit();
                        //this.data.expand(r);
                        this.data.expand.defer(1, this.data, [r]);
                        return false;
                    } else if (Edo.util.Dom.findParent(t, 'e-tree-expanded', 3)) {
                        this.submitEdit();

                        //this.data.collapse(r);
                        this.data.collapse.defer(1, this.data, [r]);

                        return false;
                    }
                    this.fireEvent('toggle', {
                        type: 'toggle',
                        source: this,
                        record: r,
                        row: r
                    });
                } else {

                    r.expanded = !r.expanded;
                    //this.data.toggle(r);
                    return false;
                }
            }
        }
    },
    _onDataChanged: function (e) {
        if (e) {
            switch (e.action) {
                case 'expand':
                    this.doExpand(e.record);
                    break;
                case 'collapse':
                    this.doCollapse(e.record);
                    break;
            }
        }
        Edo.lists.Tree.superclass._onDataChanged.call(this, e);
    },
    doCollapse: function (record) {
        this.refresh();
        return;
        var d = this.getItemEl(record);
        if (d) {
            d = Edo.util.Dom.getbyClass('e-treenode', d);
            Edo.util.Dom.removeClass(d, this.expandCls);
            Edo.util.Dom.addClass(d, this.collapseCls);
        }
        this.data.iterateChildren(record, function (o) {
            if (!this.data.isDisplay(o)) {
                var d = this.getItemEl(o);
                if (d) {
                    d.style.display = 'none';
                }
            }
        }, this);
    },
    doExpand: function (record) {
        this.refresh();
        return;
        var d = this.getItemEl(record);
        if (d) {
            d = Edo.util.Dom.getbyClass('e-treenode', d);
            Edo.util.Dom.addClass(d, this.expandCls);
            Edo.util.Dom.removeClass(d, this.collapseCls);
        }
        //判断是否有子元素DOM, 如果没有, 则加上
        if (record.children && record.children.length > 0) {
            //var cnode = record.children
        }
        this.data.iterateChildren(record, function (o, i) {
            if (this.data.isDisplay(o)) {
                var p = this.data.findParent(o);
                var d = this.getItemEl(o);

                //                if(!d){
                //                    this.insertItem(i, [o], {parentNode: p});
                //                    d = this.getItemEl(o);
                //                }

                d.style.display = '';
            }
        }, this);
    },
    //刷新节点(以及子节点)
    refreshNode: function (node) {

    },
    applyTreeColumn: function () {
        var column = this.getColumn(this.treeColumn);
        if (!column) column = this.columns[0];
        this.treeColumn = column.id;

        if (column.renderer == this.treeRenderer) return;
        if (column.renderer) column._renderer = column.renderer;
        column.renderer = this.treeRenderer;

        column.cls = this.treeColumnCls;
    },
    treeRenderer: function (v, r, c, i, data, t) {
        var left = r.__depth * 18;
        var w = c.width;
        if (w < 60) w = 60;

        var hasChildren = r.__hasChildren || r.__viewicon;
        //var s = '<div class="e-treenode '+(hasChildren ? (r.expanded !== false ? 'e-tree-expanded' : 'e-tree-collapse') : '')+'" style="padding-left:'+left+'px;height:'+(r.__height)+'px;">';                                                

        var expanded = r.expanded;


        var rowCls = hasChildren ? (expanded == true ? t.expandCls : t.collapseCls) : '';

        //var s = '<div class="e-treenode '+(hasChildren ? (expanded == true ? t.expandCls : t.collapseCls) : '')+'" style="padding-left:'+left+'px;height:'+(r.__height)+'px;">';                                                
        var s = '<div class="e-treenode ' + rowCls + '" style="padding-left:' + left + 'px;height:' + (r.__height) + 'px;">';

        var offset = left;
        if (hasChildren && r.__viewToggle !== false) {
            s += '<a href="javascript:;" hidefocus class="e-tree-nodeicon" style="left:' + offset + 'px;"></a>';
        }
        offset += 18;

        if (r.icon) {
            s += '<div class="' + r.icon + '" style="width:18px;height:20px;overflow:hidden;position:absolute;top:0px;left:' + offset + 'px;"></div>';
            offset += 16;
        }

        if (c._renderer && c._renderer != t.treeRenderer) {
            v = c._renderer(v, r, c, i, data, t);
        }
        s += '<div id="' + t.id + '-textnode-' + r.__id + '" class="e-tree-nodetext" style="left:' + (offset + 2) + 'px;">' + v + '</div>';
        s += '</div>';

        if (!data.isDisplay(r)) {

            r.__style = 'display:none';
        } else {
            r.__style = 'display:""';
        }

        return s;
    },
    _setColumns: function (columns) {
        Edo.lists.Tree.superclass._setColumns.call(this, columns);

        this.applyTreeColumn();
    },
    _setData: function (data) {
        if (typeof data == 'string') data = window[data];
        if (!data) return;
        if (data.componentMode != 'data') {
            if (!this.data) {
                data = new Edo.data.DataTree(data);
            } else {
                this.data.load(data);
                return;
            }
        }
        Edo.lists.Tree.superclass._setData.call(this, data);
    },
    getTextNode: function (record) {
        record = this.getRecord(record);
        return Edo.getDom(this.id + '-textnode-' + record.__id);
    },
    insertItem: function (index, records, e) {
        var p = e.parentNode;

        var targetRecord = p.children[index + 1];
        var target = this.getItemEl(targetRecord);
        for (var i = 0, l = records.length; i < l; i++) {
            var record = records[i];
            var s = this.getItemHtml(record, index + i);
            var el;
            if (target) {
                el = Edo.util.Dom.before(target, s);
            } else {
                el = Edo.util.Dom.append(this.getCtEl(), s);
            }

            this.data.iterateChildren(record, function (o, j) {
                var s = this.getItemHtml(o, j);
                el = Edo.util.Dom.after(el, s);
            }, this);
        }
    },
    removeItem: function (records) {
        for (var i = 0, l = records.length; i < l; i++) {
            var record = records[i];
            var d = this.getItemEl(record);
            Edo.removeNode(d);

            this.data.iterateChildren(record, function (o) {
                var d = this.getItemEl(o);
                Edo.removeNode(d);
            }, this);
        }
    },
    moveItem: function (index, records, e) {//是否当发生一些不常见操作的时候, 直接refresh?

        this.refresh();
        //        var p = e.parentNode;
        //        var target = p.children[index];
        //                   
        //        for(var i=0,l=records.length; i<l; i++){
        //            var record = records[i];
        //            var ri = this.data.indexOf(record);
        //            var d = Edo.getDom(this.createItemId(record));
        //            
        //            var t = 
        //            Edo.util.Dom.before(t, d);
        //        }
    }
});

Edo.lists.Tree.regType('tree');
Edo.plugins.RowSelect = function () {
    Edo.plugins.RowSelect.superclass.constructor.call(this);
};
Edo.plugins.RowSelect.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on(owner.selectAction, this.onSelectAction, this, 0);
    },
    canSelect: function (e) {
        if (!this.owner.enableSelect) return false;
        var item = e.item;
        if (item.enableSelect === false) return false;
        return true;
    },
    multiSelect: function (e) {
        var t = this.owner;
        var record = e.item;
        if (t.isSelected(record)) {
            if (t.repeatSelect) {

            } else {
                t.deselect(record);
            }
        } else {
            t.selectRange([record], !t.selectOnly);
        }
    },
    singleSelect: function (e) {
        var t = this.owner;
        var view = t.data.view;
        var record = e.item;

        if (t.isSelected(record) && !t.repeatSelect) {
            if (e.column && e.column.multi) {
                t.deselect(record);
            }
        } else {
            t.select(record);
        }
    },
    onSelectAction: function (e) {
        e.stop();

        var t = this.owner;
        var view = t.data.view;
        var record = e.item;

        if (!this.canSelect(e)) return;

        if (record.enable === false) return;

        if (!t.multiSelect) {     //单选       
            this.singleSelect(e);
        } else {
            if (e.ctrlKey && !t.simpleSelect) {

                if (t.isSelected(record)) {
                    t.deselect(record);
                } else {
                    t.select(record);
                    //this.currentSelected = record;
                }
            } else if (e.shiftKey && !t.simpleSelect) {
                e.stop();
                var selected = t.getSelected();

                //                var selected = this.currentSelected;
                //                if (!selected) {
                //                    selected = t.getSelected();
                //                }
                if (selected && t.data.getRange) {
                    var i1 = t.data.indexOf(selected);
                    var i2 = t.data.indexOf(record);
                    var rs = t.data.getRange(i1, i2);
                    t.selectRange(rs, false);


                } else {
                    t.select(record);
                    //this.currentSelected = record;
                }


            } else {
                this.multiSelect(e);

            }
        }

        var record = t.getSelected();
        if (record) {
            var d = Edo.getDom(t.createItemId(record));
            if (d && t.enableScrollIntoView) Edo.util.Dom.scrollIntoView(d, t.scrollEl, false);
        }

        return false;
        //        if(e.ctrlKey || e.shiftKey) e.stopDefault();
    },
    destroy: function () {
        this.owner.un(this.owner.selectAction, this.onSelectAction, this);
        this.owner = null;
        Edo.plugins.RowSelect.superclass.destroy.call(this);
    }
});

Edo.plugins.RowSelect.regType('RowSelect');
Edo.plugins.RowDragDrop = function () {
    Edo.plugins.RowDragDrop.superclass.constructor.call(this);
};
Edo.plugins.RowDragDrop.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on('itemmousedown', this.onItemMouseDown, this);

        Edo.managers.DragManager.regDrop(owner);

        //判断是否可以drop
        owner.on('dropenter', this.onDropEnter, this);
        owner.on('dropmove', this.onDropMove, this);
        owner.on('dragdrop', this.onDragDrop, this);
    },
    onDropEnter: function (e) {
        //当dragObject符合此协议时,接受dragdrop
        e.dragData.insertAction = null;
        if (e.dragData.action == 'rowdragdrop') {
            Edo.managers.DragManager.acceptDragDrop();
        }
    },
    onDropMove: function (e) {
        var dd = e.dragData;
        dd.insertAction = null;

        var tb = e.dropObject;

        var row = tb.getRecordByEvent(e.event);
        if (!row) {
            this.clearInsertProxy();
            e.feedback.setFeedback('no');
            return;
        }

        dd.targetItem = row;

        dd.insertAction = this.checkDropAction(dd, e);


        if (!dd.insertAction || (e.dropObject == e.dragObject && dd.data.indexOf(dd.targetItem) != -1)) {
            e.feedback.setFeedback('no');
            this.clearInsertProxy();
            dd.insertAction = null;
        } else {
            if (this.canDrop(dd, e) === false) {
                Edo.managers.DragManager.rejectDragDrop();
            } else {
                Edo.managers.DragManager.acceptDragDrop();
            }
        }
    },
    onDragDrop: function (e) {
        var dd = e.dragData;
        if (e.dropObject && dd.targetItem && dd.insertAction) {
            if (e.dropObject.dragDropAction) {

                this.doDragDrop(dd, e);
            }
            this.clearInsertProxy();
        }
    },
    canDrag: function (e) {
        var tb = this.owner;
        if (!tb.enableDragDrop) return false;

        var row = e.item;

        if (!row || row.enableDragDrop === false) return false;

        return true;
    },
    onItemMouseDown: function (e) {

        if (this.canDrag(e) === false) return;

        var sf = this;
        var tb = this.owner;

        var row = e.item;

        Edo.managers.DragManager.startDrag({
            dragObject: tb,
            dragData: {
                action: 'rowdragdrop',
                source: tb,
                data: this.getDragData(e)
            },
            delay: 200,

            event: e,
            alpha: 1,
            enableDrop: true,
            xOffset: 15,
            yOffset: 18,

            ondragstart: function (e) {
                sf.feedbackproxy = p = new Edo.managers.DragProxy({
                    html: sf.getDragInner(e)
                }).render();

                this.feedback = p;
                this.proxy = p.el;
            },
            ondragmove: function (e) {
                if (e.dropObject) {
                    sf.clearInsertProxy();
                    e.feedback.setFeedback('no');
                }
            },
            ondragcomplete: function (e) {
                sf.clearInsertProxy();
                this.feedback.destroy();
                this.destroy = null;
            }
        });
    },
    clearInsertProxy: function () {
        Edo.managers.DragProxy.hideInsertProxy();
    },
    //@@@
    checkDropAction: function (dragData, e) {
        var rowBox = e.dropObject.getItemBox(dragData.targetItem);
        if (e.now[1] <= rowBox.y + rowBox.height / 2) return 'preend';
        return 'append';
    },
    //@@@
    //获得拖拽对象
    getDragData: function (e) {
        var rs = this.owner.getSelecteds();
        if (!rs.contains(e.item)) {
            rs.add(e.item);
        }
        return rs;
    },
    //@@@
    getDragInner: function (e) {
        return e.dragData.data.length + '行';
    },
    //@@@
    doInsertProxy: function (dragData, e) {
        var ip = Edo.managers.DragProxy.getInsertProxy();

        var rowBox = e.dropObject.getItemBox(dragData.targetItem);

        if (dragData.insertAction == 'append') {
            rowBox.y += rowBox.height;
        }
        rowBox.y -= 1;
        rowBox.height = 2;
        Edo.util.Dom.setBox(ip, rowBox);

        var target = e.dropObject;
        if (ip.parentNode != target.el) {
            target.el.appendChild(ip);
        }
    },
    //@@@
    getFeedback: function (insert) {
        return 'add';
    },
    //
    canDrop: function (dragData, e) {
        var row = e.dragData.targetItem;
        //if(row.name == '组件') debugger
        if (!row || row.enableDragDrop === false) return false;

        e.feedback.setFeedback(this.getFeedback(dragData.insertAction));
        this.doInsertProxy(dragData, e);
    },

    doDragDrop: function (dragData, e) {
        var source = e.dragObject, target = e.dropObject,
            targetItem = dragData.targetItem, insert = dragData.insertAction, data = dragData.data;

        var action = source.dragDropAction == 'move' ? 'move' : 'copy';

        var e = {
            type: 'beforerowdragdrop',
            dragDropAction: action,     //拖拽命令:决定原row是否删除
            insertAction: insert,       //加入命名:决定数据怎么加入(preend, append, add)
            source: this.owner,
            dragData: source.data,
            dropData: target.data,
            rows: data,
            targetItem: targetItem
        }

        if (this.owner.fireEvent('beforerowdragdrop', e) === false) return;

        var index = target.data.indexOf(targetItem);
        if (insert == 'append') index += 1;


        var cloneData = [];
        for (var i = 0, l = data.length; i < l; i++) {
            cloneData.add(Edo.data.cloneData(data[i]));
        }

        if (source.data === target.data) {
            if (target.dragDropAction == 'move') {
                target.data.move(data, index);
                cloneData = data;
            } else {
                target.data.insertRange(index, cloneData);
            }
        } else {
            target.data.insertRange(index, cloneData);
            if (target.dragDropAction == 'move') {
                source.data.removeRange(data);
            } else {

            }
        }

        //target.clearSelect();
        target.selectRange(cloneData);

        e.type = 'rowdragdrop';
        this.owner.fireEvent('rowdragdrop', e)
    },

    destroy: function () {
        this.owner.un('itemmousedown', this.onItemMouseDown, this);
        this.owner = null;

        Edo.plugins.RowDragDrop.superclass.destroy.call(this);
    }
});

Edo.plugins.RowDragDrop.regType('RowDragDrop');
Edo.plugins.CellSelect = function () {
    Edo.plugins.CellSelect.superclass.constructor.call(this);
};
Edo.plugins.CellSelect.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on(owner.cellSelectAction, this.onCellSelectAction, this, 0);
    },
    onCellSelectAction: function (e) {
        var t = this.owner;
        var view = t.data.view;
        var record = e.record;
        var column = e.column;

        if (t.enableCellSelect) {
            if (t.cellSelectMode == 'single') {
                t.selectCell(record, column);
            } else {
                //如果是多选,则配合ctrl和shift,进行不同逻辑的选择
                if (e.ctrlKey) {
                    if (t.isCellSelected(record, column) && !this.repeatSelect) {
                        t.deselectCell(record, column);
                    } else {
                        t.selectCell(record, column);
                    }
                } else if (e.shiftKey) {

                    var selected = t.getCellSelected();
                    var cells = [];
                    if (selected) {
                        var start = t.data.indexOf(record);
                        var startColumn = t.columns.indexOf(column);
                        var end = t.data.indexOf(selected.record);
                        var endColumn = t.columns.indexOf(selected.column);
                        if (start > end) {
                            var index = start;
                            start = end;
                            end = index;

                            var tcolumn = startColumn;
                            startColumn = endColumn;
                            endColumn = tcolumn;
                        }

                        if (start == end && startColumn > endColumn) {
                            var tcolumn = startColumn;
                            startColumn = endColumn;
                            endColumn = tcolumn;
                        }

                        for (var i = start; i <= end; i++) {
                            for (var j = 0, k = t.columns.length; j < k; j++) {
                                var cell = {
                                    record: t.data.getAt(i),
                                    column: t.columns[j]
                                };
                                if (start == end) {
                                    if (j >= startColumn && j <= endColumn) {
                                        cells.add(cell);
                                    }
                                    continue;
                                }
                                if (i == start && j >= startColumn) {
                                    cells.add(cell);
                                }
                                if (i == end && j <= endColumn) {
                                    cells.add(cell);
                                }
                                if (i > start && i < end) {
                                    cells.add(cell);
                                }
                            }
                        }
                    } else {
                        cells.add({
                            record: record,
                            column: column
                        });
                    }
                    t.selectCellRange(cells, false);

                } else {

                    //如果没有按ctrlKey,则只选当前点击的一个
                    if (t.isCellSelected(record, column) && !this.repeatSelect) {
                        //t.deselectCell(record, column);
                    } else {
                        t.fireSelection = false;
                        t.clearCellSelect();
                        t.fireSelection = true;
                        t.selectCell(record, column);
                    }
                }
            }
        }
    },
    destroy: function () {
        this.owner.un(this.owner.cellSelectAction, this.onCellSelectAction, this);
        this.owner = null;
        Edo.plugins.RowSelect.superclass.destroy.call(this);
    }
});

Edo.plugins.CellSelect.regType('cellselect');
Edo.plugins.CellEdit = function () {
    Edo.plugins.CellEdit.superclass.constructor.call(this);
};
Edo.plugins.CellEdit.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on(owner.cellEditAction, this.onCellEditAction, this, 0);
    },
    onCellEditAction: function (e) {
        if (e.button != Edo.util.MouseButton.left) return;
        var t = this.owner;
        var view = t.data.view;
        var record = e.record;
        var column = e.column;

        if (t.enableCellEdit) {
            t.beginEdit.defer(50, t, [record, column]);
        }
    },
    destroy: function () {
        this.owner.un(this.owner.cellEditAction, this.onCellEditAction, this);
        this.owner = null;
        Edo.plugins.RowSelect.superclass.destroy.call(this);
    }
});

Edo.plugins.CellEdit.regType('celledit');
Edo.plugins.CellValid = function () {
    Edo.plugins.CellValid.superclass.constructor.call(this);
};
Edo.plugins.CellValid.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on(owner.cellValidAction, this.onCellValidAction, this, 0);
    },
    onCellValidAction: function (e) {
        var t = this.owner;
        var view = t.data.view;
        var record = e.record;
        var column = e.column;

        if (t.enableCellValid) {
            return t.validCell(record, column, e.value);
        }
    },
    destroy: function () {
        this.owner.un(this.owner.cellValidAction, this.onCellValidAction, this);
        this.owner = null;
        Edo.plugins.RowSelect.superclass.destroy.call(this);
    }
});

Edo.plugins.CellValid.regType('cellvalid');
Edo.plugins.TableSelect = function () {
    Edo.plugins.TableSelect.superclass.constructor.call(this);
};
Edo.plugins.TableSelect.extend(Edo.plugins.RowSelect, {
    multiSelect: function (e) {
        var t = this.owner;
        var record = e.item;

        if (t.isSelected(record) && !this.repeatSelect) {
            if (e.column && e.column.multi) {
                t.deselect(record);

            } else {
                if (t.repeatSelect) {

                } else {
                    t.deselect(record);
                }
            }
        } else {
            selectOnly = t.selectOnly;

            if (e.column && e.column.multi) {
                selectOnly = false;

            }
            t.selectRange([record], !selectOnly);
        }
    }
    //    ,
    //    canSelect: function(e){
    //        if(!this.owner.enableSelect) return false;
    //        var item = e.item;
    //        if(item.enableSelect === false) return false;
    //        //if(!e.column || !e.column.multi) return false;
    //        return true;
    //    }
});

Edo.plugins.TableSelect.regType('TableSelect');
Edo.plugins.TableDragDrop = function () {
    Edo.plugins.TableDragDrop.superclass.constructor.call(this);
};
Edo.plugins.TableDragDrop.extend(Edo.plugins.RowDragDrop, {
    canDrag: function (e) {

        var tb = this.owner;
        if (!tb.enableDragDrop) return false;

        var row = e.item;

        if (!row || row.enableDragDrop === false) return false;

        if (!e.column || !e.column.enableDragDrop) return false;

        return true;
    }
});

Edo.plugins.TableDragDrop.regType('TableDragDrop');
Edo.plugins.TableSort = function () {
    Edo.plugins.TableSort.superclass.constructor.call(this);
};
Edo.plugins.TableSort.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on('headerclick', this.onheaderclick, this, 0);
    },
    onheaderclick: function (e) {

        if (e.button != Edo.util.MouseButton.left) return;
        var t = this.owner;
        var view = t.data.view;
        var record = e.record;
        var column = e.column;

        if (!column || column.enableSort === false) return;

        t.sortColumn(column);
    },
    destroy: function () {
        this.owner.un('headerclick', this.onheaderclick, this);
        this.owner = null;
        Edo.plugins.RowSelect.superclass.destroy.call(this);
    }
});


Edo.plugins.TableSort.regType('TableSort');
Edo.plugins.HeaderDragDrop = function () {
    Edo.plugins.HeaderDragDrop.superclass.constructor.call(this);
};
Edo.plugins.HeaderDragDrop.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on('headermousedown', this.onheadermousedown, this, 0);
    },
    onheadermousedown: function (e) {
        if (e.button != Edo.util.MouseButton.left) return;
        var tb = this.owner;
        var view = tb.data.view;
        var record = e.record;
        var column = e.column;
        var t = e.target;

        if (!tb.enableColumnDragDrop || !column || column.enableMove === false) return;

        if (!Edo.util.Dom.hasClass(e.target, 'e-table-header-cell-inner')) return;

        var text = typeof (column.header) === 'function' ? column.header(column, tb) : column.header;
        var p;

        var box = tb._getBox(true);

        var targetColumn, insert;

        Edo.managers.DragManager.startDrag({
            event: e,
            dragData: column,
            alpha: 1,
            enableDrop: true,
            dragObject: tb,
            xOffset: 15,
            yOffset: 18,

            ondragstart: function (e) {
                p = new Edo.managers.DragProxy({
                    html: text,
                    shadow: tb.dragShadow
                }).render();

                this.proxy = p.el;
            },
            ondragmove: function (e) {
                var x = e.now[0], y = e.now[1];

                targetColumn = insert = null;

                if (x < box.x || x > box.right || y < box.y || y > box.bottom) {
                    p.setFeedback('no');
                    Edo.managers.DragProxy.hideUpDownProxy();
                } else {
                    var index = tb.getColumnIndex(column);
                    var nowColumn = tb.getColumnByEvent(e.event);
                    var nowIndex = tb.columns.indexOf(nowColumn);
                    if (!nowColumn) return;
                    var nowBox = tb.getColumnBox(nowColumn);
                    if (nowIndex == index ||
                        (nowIndex + 1 == index && x >= nowBox.x + nowBox.width / 2) ||
                        (nowIndex - 1 == index && x <= nowBox.x + nowBox.width / 2)) {
                        p.setFeedback('no');
                        Edo.managers.DragProxy.hideUpDownProxy();
                    } else {

                        targetColumn = nowColumn;
                        var updown = Edo.managers.DragProxy.getUpDownProxy();
                        if (x < nowBox.x + nowBox.width / 2) {
                            insert = 'preend';

                            Edo.util.Dom.setXY(updown[0], [nowBox.x - 5, nowBox.y - 10]);
                            Edo.util.Dom.setXY(updown[1], [nowBox.x - 5, nowBox.y + nowBox.height]);
                        }
                        else {
                            insert = 'append';

                            Edo.util.Dom.setXY(updown[0], [nowBox.x - 5 + nowBox.width, nowBox.y - 10]);
                            Edo.util.Dom.setXY(updown[1], [nowBox.x - 5 + nowBox.width, nowBox.y + nowBox.height]);
                        }

                        p.setFeedback('yes');
                    }
                }

            },
            ondragcomplete: function (e) {
                Edo.managers.DragProxy.hideUpDownProxy();
                p.destroy();
                //
                if (targetColumn) {
                    tb.insertColumn(targetColumn, column, insert);
                    tb.set('columns', tb.getColumns());
                }

            }

        });
    },
    destroy: function () {
        this.owner.un('headermousedown', this.onheadermousedown, this);
        this.owner = null;
        Edo.plugins.RowSelect.superclass.destroy.call(this);
    }
});

Edo.plugins.HeaderDragDrop.regType('HeaderDragDrop');
Edo.plugins.HeaderSplitter = function () {
    Edo.plugins.HeaderSplitter.superclass.constructor.call(this);
};
Edo.plugins.HeaderSplitter.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on('headermousedown', this.onheadermousedown, this, 0);
    },
    onheadermousedown: function (e) {
        if (e.button != Edo.util.MouseButton.left) return;
        var tb = this.owner;
        var view = tb.data.view;
        var record = e.record;
        var column = e.column;
        var t = e.target;

        if (!Edo.util.Dom.hasClass(t, 'e-table-split')) return;
        var columnId = t.id.split('|');
        columnId = columnId[columnId.length - 1];
        column = tb.getColumn(columnId);
        if (!column || column.enableResize === false) return;

        function syncResize(proxy, box) {
            Edo.util.Dom.setBox(proxy, {
                x: box.right - 5,
                y: box.y,
                width: 5,
                height: column.height//box.height + tb.viewHeight
            });
        }
        Edo.managers.ResizeManager.startResize({
            target: t,
            box: tb.getColumnBox(column),
            event: e,
            handler: 'e',
            handlerEl: t,
            autoResize: false,
            minWidth: column.minWidth,
            autoProxy: false,

            onresizestart: function (e) {
                this.splitproxy = t.cloneNode(false);
                this.splitproxy.style.backgroundColor = 'black';
                this.splitproxy.style.zIndex = 20000001;
                Edo.util.Dom.setOpacity(this.splitproxy, .3);
                Edo.util.Dom.append(document.body, this.splitproxy);

                syncResize(this.splitproxy, e.box);
            },
            onresize: function (e) {
                syncResize(this.splitproxy, e.box);
            },
            onresizecomplete: function (e) {

                column.width = e.size.width - 2; //超出的部分,必须要剪掉                
                tb.set('columns', tb.getColumns());
                Edo.util.Dom.remove(this.splitproxy);
            }
        });


    },
    destroy: function () {
        this.owner.un('headermousedown', this.onheadermousedown, this);
        this.owner = null;
        Edo.plugins.RowSelect.superclass.destroy.call(this);
    }
});

Edo.plugins.HeaderSplitter.regType('HeaderSplitter');
Edo.plugins.TreeDragDrop = function () {
    Edo.plugins.TreeDragDrop.superclass.constructor.call(this);
};
Edo.plugins.TreeDragDrop.extend(Edo.plugins.TableDragDrop, {
    checkDropAction: function (dragData, e) {
        var node = dragData.targetItem;
        var rowBox = e.dropObject.getItemBox(dragData.targetItem);

        var y = e.now[1], rowY = rowBox.y, rowHeight = rowBox.height;

        if (node.children) {
            if (y <= rowY + rowHeight / 3) return 'preend';
            if (y >= rowY + rowHeight - rowHeight / 3) return 'append';
            return 'add';
        } else {
            return Edo.plugins.TreeDragDrop.superclass.checkDropAction.apply(this, arguments);
        }
    },
    clearInsertProxy: function () {
        Edo.managers.DragProxy.hideInsertProxy();
        //清除e-tree-drop-add
        if (this.addinrow) {

            Edo.util.Dom.removeClass(this.addinrow, 'e-tree-add');
            this.addinrow = null;
        }
    },
    doInsertProxy: function (dragData, e) {
        var insert = dragData.insertAction,
            target = e.dropObject,
            row = dragData.targetItem;

        this.clearInsertProxy();
        if (insert == 'add') {
            Edo.managers.DragProxy.hideInsertProxy();

            var rnode = target.getTextNode(row);
            if (rnode) {
                Edo.util.Dom.addClass(rnode, 'e-tree-add');

                this.addinrow = rnode;
            }

        } else {
            Edo.plugins.TreeDragDrop.superclass.doInsertProxy.apply(this, arguments);
        }
    },
    getFeedback: function (insert) {

        if (insert == 'add') return 'add';
        return insert;
    },
    canDrop: function (dragData, e) {
        var source = e.dragObject,
            target = e.dropObject,
            nowRow = dragData.sourceRow,
            targetItem = dragData.targetItem;

        var allow = true;

        //!!!
        //status = e.dropObject.id+":"+new Date();

        //if(source == target){   //判断nowRow是否在targetItem之内            
        for (var i = 0, l = dragData.data.length; i < l; i++) {
            var nowRow = dragData.data[i];
            var isChild = Edo.data.DataTree.isAncestor(nowRow, targetItem);
            allow = !isChild;
            if (!allow) break;

            if (targetItem == nowRow) {
                allow = false;
                break;
            }
        }
        //}

        //开放一个接口
        //this.owner.fireEvent('

        if (allow) {
            return Edo.plugins.TreeDragDrop.superclass.canDrop.apply(this, arguments);
        } else {
            dragData.insertAction = null;
            this.clearInsertProxy();
            e.feedback.setFeedback('no');
        }
    },
    doDragDrop: function (dragData, e) {
        var source = e.dragObject, target = e.dropObject,
            targetItem = dragData.targetItem, insert = dragData.insertAction, data = dragData.data;

        var action = source.dragDropAction == 'move' ? 'move' : 'copy';

        var e = {
            type: 'beforerowdragdrop',
            dragDropAction: action,     //拖拽命令:决定原row是否删除
            insertAction: insert,       //加入命名:决定数据怎么加入(preend, append, add)
            source: this.owner,
            dragData: source.data,
            dropData: target.data,
            rows: data,
            targetItem: targetItem
        }

        if (this.owner.fireEvent('beforerowdragdrop', e) === false) return;

        var isonedata = source.data === target.data;
        var d = target.data;

        //        var cloneData = [];
        //        for(var i=0,l=data.length; i<l; i++){
        //            cloneData.add(Edo.data.cloneData(data[i]));
        //        }
        cloneData = Edo.clone(data);


        if (source.data === target.data) {

            if (target.dragDropAction == 'move') {
                target.data.move(data, targetItem, insert);
            }

        } else {
            //target.data.insertRange(index, cloneData);

            if (target.dragDropAction == 'move') {
                source.data.removeRange(data);
                target.data.move(data, targetItem, insert);
            } else {

            }
        }


        //        if(isonedata) d.beginChange();
        //        
        //        var view = data;
        //        for(var i=0,l=view.length; i<l; i++){       
        //            var now = view[i];
        //            
        //            if(source.dragDropAction == 'move'){
        //                source.deselect(now);
        //                source.data.remove(now);       //drag对象删除数据
        //            }
        //            
        //            now = Edo.data.cloneData(now);                        
        //            d.move(now, targetItem, insert);
        //            
        //            view[i] = now;
        //        }

        target.selectRange(data);

        //if(isonedata) d.endChange(action);

        e.type = 'rowdragdrop';
        this.owner.fireEvent('rowdragdrop', e)
    }
});

Edo.plugins.TreeDragDrop.regType('treedragdrop');
Edo.plugins.TableKeyboard = function () {
    Edo.plugins.TableKeyboard.superclass.constructor.call(this);
};
Edo.plugins.TableKeyboard.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on("keydown", this.onKeyAction, this, 0);
    },
    onKeyAction: function (e) {
        if (e.ctrlKey) return;
        var t = this.owner, enableCellSelect = t.enableCellSelect, enableSelect = t.enableSelect;

        if (Edo.util.Dom.findParent(e.target, 'e-table-filter-row')) return;

        var cell = t.getCellSelected ? t.getCellSelected() : null;
        var record = t.getSelected();
        if (e.keyCode == 13) {
            e.stop();
        }
        if (!cell && !record) return;

        var record = record || cell.record, column = cell ? cell.column : -1;
        var rowIndex = t.data.indexOf(record), columnIndex = t.columns.indexOf(column);

        switch (e.keyCode) {
            case 37:                //左
                if (column != -1) {
                    columnIndex -= 1;
                    if (columnIndex < 0) columnIndex = 0;
                }
                break;
            case 38:                //上
                rowIndex -= 1;
                if (rowIndex < 0) rowIndex = 0;
                break;
            case 39:                //右        
                if (column != -1) {
                    columnIndex += 1;
                    if (columnIndex >= t.columns.length) columnIndex = t.columns.length - 1;
                }
                break;
            case 40:                //下
                rowIndex += 1;
                if (rowIndex >= t.data.getCount()) rowIndex = t.data.getCount() - 1;
                break;
            case 13:                //enter            
                if (column != -1) {
                    if (t.enableCellEdit) {
                        t.beginEdit.defer(50, t, [record, column]);
                    }
                }
                e.stop();
                return;
                break;
            case 27:                //esc
                e.stop();
                return;
                break;
        }
        var intoView = false;
        if (column != -1) {
            if (enableCellSelect) {
                t.selectCell(rowIndex, columnIndex);
                intoView = true;
                e.stop();
            }
        }

        if (enableSelect && (!e.ctrlKey && !e.shiftKey)) {
            t.selectRange(rowIndex, false);
            intoView = true;
            e.stop();
        }
        if (e.shiftKey) {
            e.stop();
        }

        if (intoView) t.scrollIntoView(rowIndex, columnIndex);
    },
    destroy: function () {
        this.owner.un("keydown", this.onKeyAction, this);
        this.owner = null;
        Edo.plugins.TableKeyboard.superclass.destroy.call(this);
    }
});

Edo.plugins.TableKeyboard.regType('TableKeyboard');
//实现容器(horizontal,vertical)布局模式下的split功能:拖拽调节尺寸, 缩放
Edo.plugins.ContainerSplitter = function () {
    Edo.plugins.ContainerSplitter.superclass.constructor.call(this);
};
Edo.plugins.ContainerSplitter.extend(Edo.core.Component, {
    init: function (owner) {
        this.owner = owner;

        this.owner.on('syncsize', this.onContainerSyncSize, this);
        this.owner.on('mousedown', this._onMouseDown, this);
        //this.owner.on('splitclick', this._onSplitClick, this);


    },
    onContainerSyncSize: function (e) {

        var ct = this.owner;
        var children = ct.getDisplayChildren();

        //if(ct.id == 'box222') alert(ct.id);

        //if(!this.enableSplit) return;
        //1)如果是horizontal,vertical两种方式的布局
        //2)horizonGap,verticalGap影响...

        if (this.splitters) {
            for (var id in this.splitters) {
                Edo.util.Dom.remove(this.splitters[id]);
            }
        }

        var index = ['horizontal', 'vertical'].indexOf(ct.layout);
        if (index == -1) return false;

        this.splitters = {};

        var gap = ct[['horizontalGap', 'verticalGap'][index]];
        var spliterCls = ['e-spliter-h', 'e-spliter-v'][index];


        for (var i = 0, l = children.length; i < l; i++) {
            var c = children[i];
            if (c.splitRegion) {

                var handlecls = 'e-ct-spliter-handle-' + c.splitRegion;
                var cls = 'e-ct-spliter-' + c.splitRegion;
                if (!c.expanded) {
                    cls += ' ' + ct.collapseCls;
                }

                var s = '<div id="for-' + c.id + '" class="e-ct-spliter ' + cls + '"><div class="e-ct-spliter-inner"><a href="#" onclick="return false" hideFocus class="e-ct-spliter-handle ' + handlecls + '"></a></div></div>';

                if (c.splitRenderer) {
                    s = c.splitRenderer.call(c);
                }

                var el = Edo.util.Dom.preend(ct.scrollEl, s);

                var box = c._getBox();

                if (index == 0) {

                    box.width = gap;

                    if (c.splitPlace == 'before') {
                        box.x -= gap;
                    } else {
                        box.x = box.right;
                    }
                } else {

                    box.height = gap;

                    if (c.splitPlace == 'before') {
                        box.y -= gap;
                    } else {
                        box.y = box.bottom;
                    }
                }
                Edo.util.Dom.setBox(el, box);

                el.control = c.id;
                this.splitters[c.id] = el;
            }
        }
    },
    _onMouseDown: function (e) {
        if (e.button != Edo.util.MouseButton.left) return;
        var t = e.target;
        if (this.splitters) {
            for (var id in this.splitters) {
                var el = this.splitters[id];

                if (e.within(el)) {
                    var cmp = Edo.getCmp(el.control);
                    var className = t.className;
                    if (Edo.util.Dom.hasClass(t, 'e-ct-spliter-handle')) {
                        var direction = 'east';
                        direction = className.indexOf('west') != -1 ? 'west' : direction;
                        direction = className.indexOf('north') != -1 ? 'north' : direction;
                        direction = className.indexOf('south') != -1 ? 'south' : direction;

                        cmp.fireEvent('splitclick', {
                            type: 'splitclick',
                            source: cmp,
                            split: el,
                            splitRegion: direction
                        });
                    } else {
                        this.splitResize(e, cmp, el);
                    }
                }
            }
        }
    },
    splitResize: function (e, cmp, el) {
        var ct = this.owner;

        if (!cmp.expanded) return;

        var index = ['horizontal', 'vertical'].indexOf(ct.layout);
        var gap = index == 0 ? ct.horizontalGap : ct.verticalGap;
        function syncResize(proxy, box) {
            if (index == 0) {
                Edo.util.Dom.setBox(proxy, {
                    x: box.right - gap - (cmp.splitPlace == 'before' ? box.width - gap : 0),
                    y: box.y,
                    width: gap,
                    height: box.height
                });
            } else {
                Edo.util.Dom.setBox(proxy, {
                    x: box.x,
                    y: box.bottom - gap - (cmp.splitPlace == 'before' ? box.height - gap : 0),
                    width: box.width,
                    height: gap
                });
            }
        }


        var handlers = ['e', 's'];
        if (cmp.splitPlace == 'before') handlers = ['w', 'n'];

        Edo.managers.ResizeManager.startResize({
            target: cmp,
            event: e,
            handler: handlers[index],
            handlerEl: el,
            autoResize: false,
            minWidth: cmp.minWidth + this.horizontalGap,
            minHeight: cmp.minHeight + this.verticalGap,
            autoProxy: false,

            onresizestart: function (e) {
                this.splitproxy = el.cloneNode(false);
                this.splitproxy.style.backgroundColor = 'black';
                Edo.util.Dom.setOpacity(this.splitproxy, .3);
                Edo.util.Dom.append(document.body, this.splitproxy);

                syncResize(this.splitproxy, e.box);
            },
            onresize: function (e) {
                syncResize(this.splitproxy, e.box);
            },
            onresizecomplete: function (e) {
                Edo.util.Dom.remove(this.splitproxy);
                if (index == 0) {
                    cmp._setWidth(e.size.width - cmp.parent.horizontalGap);
                } else {
                    cmp._setHeight(e.size.height - cmp.parent.verticalGap);
                }
            }
        });
    },
    destroy: function () {

        this.owner.un('syncsize', this.onContainerSyncSize, this);
        this.owner.un('mousedown', this._onMouseDown, this);

        this.owner = null;

        Edo.plugins.ContainerSplitter.superclass.destroy.call(this);
    }
});

Edo.plugins.ContainerSplitter.regType('containersplitter');

Edo.lists.SuperGrid = function () {
    Edo.lists.SuperGrid.superclass.constructor.call(this);

}
Edo.lists.SuperGrid.extend(Edo.lists.Table, {

    //headerClass: Edo.lists.SuperGridHeader,

    enableCellSelect: true,

    columnWidth: 100,
    rowHeight: 20,
    maxVerticalScrollPosition: 0,
    maxHorizontalScrollPosition: 0,
    verticalScrollPolicy: 'auto',
    horizontalScrollPolicy: 'auto',

    scrollOffset: 18,
    scrollHeight: 0,
    scrollLeft: 0,
    scrollTop: 0,

    startRowIndex: 0,
    startColumnIndex: 0,

    //view显示区域范围
    startColumn: -1,
    endColumn: -2,
    startRow: -1,
    endRow: -2,

    headerHeight: 24,
    scrollIntoView: function (record, column) {
        var record = this.getRecord(record), column = this.getColumn(column);

        var cellBox = this._getCellOffsetBox(record, column);
        var viewBox = this.getViewportBox();
        var scrollLeft = this.scrollLeft, scrollTop = this.scrollTop;

        if (cellBox.x < this.scrollLeft) {  //左
            scrollLeft = cellBox.x;
        }
        if (cellBox.x + cellBox.width > this.scrollLeft + viewBox.width) {  //右
            //alert("右");
            scrollLeft = cellBox.x + cellBox.width - (viewBox.width);
        }
        //alert((cellBox.x + cellBox.width) +":"+scrollLeft);
        if (cellBox.y < this.scrollTop) {  //上
            scrollTop = cellBox.y;
        }

        if (cellBox.y + cellBox.height > this.scrollTop + viewBox.height) { //下
            scrollTop = cellBox.y + cellBox.height - viewBox.height
        }
        if (scrollLeft != this.scrollLeft) {
            this._setScrollLeft(scrollLeft);
        }
        if (scrollTop != this.scrollTop) {
            this._setScrollTop(scrollTop);
        }
        //        if(scrollLeft<= 250) debugger
        //        status = scrollLeft;

        //        var d = this.getItemEl(record);
        //        var el = this.getCellEl(record, column);
        //        if(d || el) {                        
        //            Edo.util.Dom.scrollIntoView(el || d, this.scrollEl, el ? true : false);
        //        }
    },
    _setRowHeight: function (v) {

        v = parseInt(v);
        if (isNaN(v)) return;
        if (v != this.rowHeight) {
            this.rowHeight = v;


            this.data.source.each(function (o) {
                o.__height = v;
            });
            var view = this.data[this.dataViewField]
            view.each(function (o) {
                o.__height = v;
            });

            this.relayout('rowHeight');
        }
    },

    elCls: 'e-supergrid e-table e-dataview e-div',
    _setScrollHeight: function (value) {
        value = parseInt(value);
        if (value != this.scrollHeight) {
            this.scrollHeight = value;
            //this.derferRefresh();
            this.relayout('scrollHeight');
        }
    },
    _onDataChanged: function (e) {
        //当刷新的时候, 取消编辑
        this.cancelEdit();
        if (e) {

        }
        this.refreshSummary();
        this.doSelectCell(this.cellSelecteds);
        this.checkSelecteds();
        this.relayout('datachanged');
    },
    initTable: function () {

        Edo.lists.SuperGrid.superclass.initTable.apply(this, arguments);

        Edo.util.Dom.applyStyles(this.scrollEl, 'overflow:hidden;overflow-x:hidden;overflow-y:hidden;');
    },
    doScrollPolicy: function () { },
    createChildren: function () {
        Edo.lists.SuperGrid.superclass.createChildren.apply(this, arguments);

        this.vscrollbar = Edo.create({
            type: 'vscrollbar',
            visible: false,
            style: 'position:absolute;left:auto;top:' + this.getHeaderHeight() + 'px;right:0;',
            render: this.el
        });
        this.hscrollbar = Edo.create({
            type: 'scrollbar',
            visible: false,
            style: 'position:absolute;top:auto;left:0;bottom:0;',
            render: this.el
        });
    },
    initHeader: true,
    getInnerHtml: function (sb) {
        var hd = this.getHeader();
        sb[sb.length] = '<div class="e-table-header">';
        if (this.initHeader) {
            sb[sb.length] = hd._createView(this.startColumn, this.endColumn);
        }
        sb[sb.length] = '</div><div class="e-table-viewport">'
        //sb[sb.length] = this.createView();
        sb[sb.length] = '</div>';
        sb[sb.length] = '<div class="e-table-footer">';
        sb[sb.length] = this.getFooter()._createView(0, this.columns.length);
        sb[sb.length] = '</div>';
    },
    initEvents: function () {
        this.on('mousedown', this._onMousedown, this, 0);
        this.on('dblclick', this._onDblClick, this, 0);
        this.on('click', this._onClick, this, 0);
        this.on('mousemove', this._onMouseMove, this, 0);
        this.on('mouseout', this._onMouseOut, this, 0);

        this.on('scroll', this._onScroll, this, 0, 0);

        Edo.util.Dom.on(this.el, "mousewheel", function (e) {
            this.direction = 'vertical';
            var sf = this;
            var offset = e.getWheelDelta() * 24;

            var top = this.scrollTop;
            var v = this.vscrollbar.value;
            this.vscrollbar.set('value', this.scrollTop - offset);

            if (this.vscrollbar.value != v) {
                var sf = this;
                if (this.deferScrollComplete) {
                    clearTimeout(this.deferScrollComplete);
                    this.deferScrollComplete = null;
                }
                this.deferScrollComplete = setTimeout(function () {
                    sf.vscrollbar.completeScroll();
                    sf.deferScrollComplete = null;
                }, 100);
            }

            if (top != this.vscrollbar.value) {
                e.stopDefault();
            }
            this.submitEdit();
        }, this, 0);

        Edo.lists.Table.superclass.initEvents.call(this);

        function onScrollStart(e) {
            this.scrollTop = this.vscrollbar.value;
            this.scrollLeft = this.hscrollbar.value;
            this.direction = e.direction;

            this.fireEvent('scrollstart', Edo.apply(e, {
                type: 'scrollstart',
                source: this,
                scrollLeft: this.scrollLeft,
                scrollTop: this.scrollTop,
                direction: e.direction
            }));
        }
        function onScrollComplete(e) {
            this.scrollTop = this.vscrollbar.value;
            this.scrollLeft = this.hscrollbar.value;
            this.direction = e.direction;

            this.fireEvent('scrollcomplete', Edo.apply(e, {
                type: 'scrollcomplete',
                source: this,
                scrollLeft: this.scrollLeft,
                scrollTop: this.scrollTop,
                direction: e.direction
            }));
        }

        function onScroll(e) {

            this.scrollTop = this.vscrollbar.value;
            this.scrollLeft = this.hscrollbar.value;
            this.direction = e.direction;

            this.fireEvent('scroll', Edo.apply(e, {
                type: 'scroll',
                source: this,
                scrollLeft: this.scrollLeft,
                scrollTop: this.scrollTop,
                direction: e.direction
            }));

            //当刷新的时候, 取消编辑
            this.cancelEdit();
        }

        this.vscrollbar.on('scrollstart', onScrollStart, this, 0);
        this.hscrollbar.on('scrollstart', onScrollStart, this, 0);
        this.vscrollbar.on('scroll', onScroll, this, 0);
        this.hscrollbar.on('scroll', onScroll, this, 0);
        this.vscrollbar.on('scrollcomplete', onScrollComplete, this, 0);
        this.hscrollbar.on('scrollcomplete', onScrollComplete, this, 0);
    },
    count: 0,
    horizontalScrollRefresh: false,
    _onScroll: function (e) {

        var all = this.direction == 'horizontal' && this.horizontalScrollRefresh;

        //if(this.type == 'gantt') debugger                
        if (this.live || this.autoScrollView === false) {
            //this.refresh(false);
            this.markViewRegion();
            return;
        }
        this.count++;
        if (isSafari) {
            if (this.count >= 5) {
                this.refresh.defer(1, this, [all]);
                this.count = 0;
            }
        } else {
            this.refresh(all);
        }

    },
    _setScrollLeft: function (value) {
        if (this.scrollLeft != value) {
            this.hscrollbar.set('value', value);
            //this.direction = 'horizontal';
        }
    },
    _setScrollTop: function (value) {
        if (this.scrollTop != value) {
            this.vscrollbar.set('value', value);
            //this.direction = 'vertical';
        }
    },
    markViewRegion: function () {
        //        var box = Edo.util.Dom.getBox(this.el, true);        
        //        var hdHeight = this.getHeaderHeight();
        //        var footerHeight = this.getFooterHeight();
        //        var width = box.width, height = box.height - hdHeight - footerHeight;        

        this.markColumnsAndRows(this.scrollLeft, this.scrollTop, this.scrollLeft + this.viewWidth, this.scrollTop + this.viewHeight);
    },
    refresh: function (all) {

        this.markViewRegion();

        var rowRefresh = !(this.startRow == this.lastStartRow && this.lastEndRow == this.endRow);
        var columnRefresh = !(this.lastStartColumn == this.startColumn && this.lastEndColumn == this.endColumn);

        this.lastStartRow = this.startRow;
        this.lastEndRow = this.endRow;
        this.lastStartColumn = this.startColumn;
        this.lastEndColumn = this.endColumn;

        //必须刷新body, 就那么小的地方                
        if (this.el) {
            this.viewport = Edo.replaceHtml(this.viewport, this.createView());
            Edo.util.Dom.applyStyles(this.viewport, 'overflow:hidden;overflow-x:hidden;overflow-y:hidden;');
            this.scrollEl = this.viewport;
        }
        if (this.scrollLeft == this.hscrollbar.maxValue) {
            this.viewport.scrollLeft = Math.abs(this.scrollLeft - this.getOffsetByColumn(this.startColumn));
        } else {
            this.viewport.scrollLeft = 0;
        }

        //如果是自适应, 则刷新        
        var isSync = this.getColumn(this.autoExpandColumn) || this.autoColumns;

        if (all === true || isSync) {
            this.refreshHeader();
            this.refreshFooter();
        }

        var viewportScrollLeft = this.getViewportScrollLeft();
        this.footEl.scrollLeft = viewportScrollLeft;
        this.headEl.scrollLeft = viewportScrollLeft;

        //渲染刷新事件
        this.fireEvent('refresh', {
            type: 'refresh',
            source: this
        });
    },
    getViewportScrollLeft: function () {
        return this.getColumnsWidth(0, this.startColumn) + this.viewport.scrollLeft;
    },
    //找从左侧到column的尺寸之和
    getOffsetByColumn: function (column) {
        column = this.getColumn(column);
        var allw = 0;
        this.columns.each(function (c) {
            if (column == c) return false;
            allw += c.width;

        }, this);
        return allw;
    },
    getVerticalScrollOffset: function () {
        var offset = 0;
        if (this.verticalScrollPolicy == 'on') {
            offset = this.scrolloffset;
        } else if (this.verticalScrollPolicy == 'off') {

        } else {
            var box = this.el ? Edo.util.Dom.getBox(this.el, true) : this.realHeight;
            var hdHeight = this.getHeaderHeight();
            var sh = this.headerVisible ? box.height - hdHeight : box.height;
            if (this.maxVerticalScrollPosition > sh) {
                offset = this.scrolloffset;
            }
        }
        return offset;
    },
    getHorizontalScrollOffset: function () {
        var offset = 0;
        if (this.horizontalScrollPolicy == 'on') {
            offset = this.scrolloffset;
        } else if (this.horizontalScrollPolicy == 'off') {

        } else {
            var box = this.el ? Edo.util.Dom.getBox(this.el, true) : this.realWidth;
            if (this.maxHorizontalScrollPosition > box.width) {
                offset = this.scrolloffset;
            }
        }
        return offset;
    },
    syncBox: function (next) {

        this.markMaxScrollPosition();   //得到最长列宽和最高行高

        if (!this.el) return;

        var box = Edo.util.Dom.getBox(this.el, true);

        var hdHeight = this.getHeaderHeight();
        var footerHeight = this.getFooterHeight();

        this.syncColumns();             //根据列宽, 计算每个列的实际列宽                   
        this.markMaxScrollPosition();   //列宽调整后,重新得到最长列宽和最高行高        

        var width = box.width - this.getVerticalScrollOffset();
        var height = box.height - this.getHorizontalScrollOffset();

        Edo.util.Dom.setSize(this.viewport, width, height - hdHeight - footerHeight);

        var viewBox = Edo.util.Dom.getBox(this.viewport, true);
        this.viewBox = viewBox;
        this.viewWidth = viewBox.width;
        this.viewHeight = viewBox.height;

        this.vscrollbar.set('style', 'top:' + hdHeight + 'px');

        this.vscrollbar.set('height', height - hdHeight - footerHeight);
        this.hscrollbar.set('width', width);

        //
        this.hscrollbar.set('style', 'top:' + (this.vscrollbar.height + this.getHeaderHeight()) + 'px;bottom:auto;');

        this.vscrollbar.set('visible', !!this.getVerticalScrollOffset());
        this.hscrollbar.set('visible', !!this.getHorizontalScrollOffset());

        //alert(this.maxVerticalScrollPosition - this.viewHeight);

        this.vscrollbar.set('maxValue', this.maxVerticalScrollPosition - this.viewHeight); //+this.scrollOffset);
        this.hscrollbar.set('maxValue', this.maxHorizontalScrollPosition - this.viewWidth);

        //表格本身的scrollTop和scrollLeft需要调整
        if (this.scrollTop > this.vscrollbar.maxValue) {
            this._setScrollTop(this.vscrollbar.maxValue);
        }
        if (this.scrollLeft > this.hscrollbar.maxValue) {
            this._setScrollLeft(this.hscrollbar.maxValue);
        }

        if (this.foolterVisible) {
            this.footEl.style.top = this.getHorizontalScrollOffset() + 'px';
        }
    },
    getDataView: function () {
        return this.data[this.dataViewField];
    },
    _getCellOffsetBox: function (record, column) {
        record = this.getRecord(record);
        column = this.getColumn(column);

        //这里计算有问题!
        var left = 0, top = 0;
        var h = 0, w = 0;
        var rowIndex = this.data.indexOf(record), columnIndex = this.columns.indexOf(column);
        var data = this.getDataView(), columns = this.columns;
        for (var i = 0, l = rowIndex; i <= l; i++) {
            h = data[i].__height;
            if (!h) h = data[i].__height = this.rowHeight;
            top += h;
            if (i == l) top -= h;
        }
        for (var i = 0, l = columnIndex; i <= l; i++) {
            w = columns[i].width;
            left += w;
            if (i == l) left -= w;
        }
        var viewbox = this.getViewportBox();
        var box = {
            //                x: viewbox.x+left - this.scrollLeft,
            //                y: viewbox.y+top - this.scrollTop,
            x: left,
            y: top,
            width: w,
            height: h
        };
        return box;
    },
    //确定滚动条的最大position
    markMaxScrollPosition: function () {
        //1)计算columns
        var columns = this.columns;
        var hp = 0;
        for (var i = this.startColumnIndex, l = columns.length; i < l; i++) {
            var w = columns[i].width;
            if (!w) w = columns[i].width = this.columnWidth;
            hp += w;
        }

        var data = this.getDataView();     //如果没有columns,则使用data的第一行的所有field生成默认columns!
        var vp = 0;
        for (var i = this.startRowIndex, l = data.length; i < l; i++) {
            var h = data[i].__height;
            if (!h) h = data[i].__height = this.rowHeight;
            //if(!h) h = this.rowHeight;
            vp += h;
        }
        this.maxHorizontalScrollPosition = hp;
        this.maxVerticalScrollPosition = vp;

        if (this.maxVerticalScrollPosition >= this.realHeight && data.length > 0) {
            this.maxVerticalScrollPosition += (data[data.length - 1].__height || this.rowHeight);
        }

        //如果是live模式, 则表格的maxVerticalScrollPosition恒定时传递的scrollHeight的值
        if (this.live) {
            if (this.scrollHeight) this.maxVerticalScrollPosition = this.scrollHeight;
        }
        //        if(this.maxHorizontalScrollPosition >= this.realWidth){
        //            this.maxHorizontalScrollPosition += this.columns[this.columns.length-1].width;        
        //        }
    },
    //根据尺寸区域,计算出行/列区域
    markColumnsAndRows: function (left, top, right, bottom) {
        //加上lockOffset   
        var ds = this.getDataView();
        var columns = this.columns;
        //columnes
        if (!columns || columns.length == 0) return;
        var x = 0;
        for (var i = this.startColumnIndex, l = columns.length; i < l; i++) {
            var c = columns[i];
            if (!c) continue;
            var w = columns[i].width;
            x += w;
            if (x > left) {
                this.startColumn = i;
                x -= w;
                break;
            }
        }
        if (this.startColumn >= columns.length) this.startColumn = columns.length - 1;
        for (var i = this.startColumn, l = columns.length; i < l; i++) {
            var c = columns[i];
            if (!c) continue;

            var w = columns[i].width;
            x += w;
            if (x > right) {
                this.endColumn = i;
                break;
            }
            this.endColumn = i;
        }

        //		if(this.endColumn
        //data

        if (this.live) {
            this.startRow = parseInt(this.scrollTop / this.rowHeight);
            this.endRow = this.startRow + parseInt(this.viewHeight / this.rowHeight);
            return;
        }

        if (!ds || ds.length == 0) return;
        //startRow
        var y = 0;
        for (var i = this.startRowIndex, l = ds.length; i < l; i++) {
            var h = ds[i].__height || this.rowHeight;
            y += h;
            if (y > top) {
                this.startRow = i;
                //y -= h;			
                break;
            }
        }

        if (this.startRow >= ds.length) this.startRow = ds.length - 1;
        //endRow		
        for (var i = this.startRow, l = ds.length; i < l; i++) {
            var r = ds[i];
            if (!r) continue;
            var h = r.__height || this.rowHeight;
            y += h;
            if (y > bottom) {
                this.endRow = i;
                break;
            }
            this.endRow = i;
        }
        this.endRow += 1;
    }
	,
    syncSize: function () {

        Edo.lists.SuperGrid.superclass.syncSize.apply(this, arguments);
        this.syncBox();
        this.refresh(); //因为是动态的,所以可以即时刷新, 数据量比较小
    }
});

Edo.lists.SuperGrid.regType('SuperGrid');


Edo.lists.TreeGrid = function () {


    Edo.lists.TreeGrid.superclass.constructor.call(this);

    this.on('beforebodymousedown', this._onbeforebodymousedownHandler, this);
}
Edo.lists.TreeGrid.extend(Edo.lists.SuperGrid, {


    treeColumn: '',
    treeColumnCls: 'e-tree-treecolumn',

    collapseCls: 'e-tree-collapsed',
    expandCls: 'e-tree-expanded',

    dragDropModel: 'treedragdrop',
    enableColumnSort: false,

    enableStripe: false,

    showNodeLines: false,
    createChildren: function () {
        Edo.lists.TreeGrid.superclass.createChildren.apply(this, arguments);
        this._coreShowNodeLines();
    },
    _coreShowNodeLines: function () {
        if (this.el) {
            if (this.showNodeLines) {
                Edo.util.Dom.removeClass(this.el, "e-tree-nodelines-none");
            } else {
                Edo.util.Dom.addClass(this.el, "e-tree-nodelines-none");
            }
        }
    },
    _setShowNodeLines: function (value) {
        if (this.showNodeLines != value) {
            this.showNodeLines = value;
            this._coreShowNodeLines();
        }
    },

    _onbeforebodymousedownHandler: function (e) {
        var t = e.target;
        var r = e.record;

        if (e.button == Edo.util.MouseButton.left) {
            var nodeicon = Edo.util.Dom.hasClass(t, 'e-tree-nodeicon');
            if (nodeicon) {
                if (this.fireEvent('beforetoggle', {
                    type: 'beforetoggle',
                    source: this,
                    row: r,
                    record: r
                }) !== false) {
                    if (Edo.util.Dom.findParent(t, 'e-tree-collapsed', 3)) {
                        //this.data.expand.defer(1, this.data, [r]);
                        this.submitEdit();
                        this.data.expand(r);
                        return false;
                    } else if (Edo.util.Dom.findParent(t, 'e-tree-expanded', 3)) {
                        this.submitEdit();

                        this.data.collapse(r);
                        //this.data.collapse.defer(1, this.data, [r]);

                        return false;
                    }
                    this.fireEvent('toggle', {
                        type: 'toggle',
                        source: this,
                        row: r,
                        record: r
                    });
                } else {

                    r.expanded = !r.expanded;
                    //this.data.toggle(r);
                    return false;
                }


            }

        }
    },
    applyTreeColumn: function () {
        var column = this.getColumn(this.treeColumn);
        //        if(!column) column = this.columns[0];
        //        this.treeColumn = column.id;
        if (!column) return;

        if (column.renderer == this.treeRenderer) return;
        if (column.renderer) column._renderer = column.renderer;
        column.renderer = this.treeRenderer;
        column.cls = this.treeColumnCls;
    },
    //rootOffset: 0,
    //    _setRootOffset: function(value){
    //        if(this.rootOffset != value){
    //            this.rootOffset = value;
    //            this.relayout('rootOffset');
    //        }
    //    },
    _getNodeLeft: function (r) {
        var offset = parseInt(r._offset);
        if (isNaN(offset)) offset = 0;
        return r.__depth * 18 + offset;
    },
    treeRenderer: function (v, r, c, i, data, t) {
        var left = t._getNodeLeft(r);
        var w = c.width;
        if (w < 60) w = 60;

        var hasChildren = r.__hasChildren || r.__viewicon;
        //var s = '<div class="e-treenode '+(hasChildren ? (r.expanded !== false ? 'e-tree-expanded' : 'e-tree-collapsed') : '')+'" style="padding-left:'+left+'px;height:'+(r.__height)+'px;">';                                                

        var expanded = r.expanded;


        var rowCls = hasChildren ? (expanded == true ? t.expandCls : t.collapseCls) : '';

        //var s = '<div class="e-treenode '+(hasChildren ? (expanded == true ? t.expandCls : t.collapseCls) : '')+'" style="padding-left:'+left+'px;height:'+(r.__height)+'px;">';                                                
        var s = '<div class="e-treenode ' + rowCls + '" style="padding-left:' + left + 'px;height:' + (r.__height) + 'px;">';

        //if(r.Name == "分发给工作组成员" && r.Duration == 16) debugger
        //绘制节点线
        var topNode = data.findTop(r);
        var isLast = data.isLast(topNode);
        var node = r, __left = left - 18;
        while (1) {
            if (node.__pid == -1) break;
            var parentNode = data.getById(node.__pid);
            if (isLast && data.isLast(parentNode)) {

            } else {
                s += '<div class="e-tree-nodeline" style="left:' + __left + 'px;"></div>';
            }
            node = parentNode;
            __left -= 18;
        }




        var offset = left;
        //if (hasChildren && r.__viewToggle !== false) {
        //s += '<a href="javascript:;" hidefocus class="e-tree-nodeicon" style="left:' + offset + 'px;"></a>';
        //}
        if (data.isLast(r)) {
            s += '<a href="javascript:;" hidefocus class="e-tree-nodeicon e-tree-lastnode" style="left:' + offset + 'px;"></a>';
        } else {
            s += '<a href="javascript:;" hidefocus class="e-tree-nodeicon" style="left:' + offset + 'px;"></a>';
        }

        offset += 18;

        if (r.icon) {
            s += '<div class="' + r.icon + '" style="width:18px;height:20px;overflow:hidden;position:absolute;top:0px;left:' + offset + 'px;"></div>';
            offset += 16;
        }

        if (c._renderer) {
            v = c._renderer(v, r, c, i, data, t);
        }
        s += '<div id="' + t.id + '-textnode-' + r.__id + '" class="e-tree-nodetext" style="left:' + (offset + 2) + 'px;">' + v + '</div>';
        s += '</div>';

        //        if(!data.isDisplay(r)) {
        //            
        //            r.__style = 'display:none';
        //        }            

        return s;
    },
    _setColumns: function (columns) {
        Edo.lists.TreeGrid.superclass._setColumns.call(this, columns);
        this.applyTreeColumn();
    },

    _setData: function (data) {
        //必须经过dataTree转换
        if (typeof data == 'string') data = window[data];
        if (!data) return;
        if (data.componentMode != 'data') {
            if (!this.data) {
                data = new Edo.data.DataTree(data);
            } else {
                this.data.load(data);
                return;
            }
        }
        Edo.lists.TreeGrid.superclass._setData.call(this, data);
    },
    getTextNode: function (record) {
        record = this.getRecord(record);
        return Edo.getDom(this.id + '-textnode-' + record.__id);
    }
});

Edo.lists.TreeGrid.regType('TreeGrid', 'supertree');
Edo.plugins.GanttTimeLineResize = function () {
    Edo.plugins.GanttTimeLineResize.superclass.constructor.call(this);
};
Edo.plugins.GanttTimeLineResize.extend(Edo.core.Component, {

    init: function (owner) {

        if (this.owner) return;
        this.owner = owner;

        //owner.on('headermousedown', this.onMousedown, this);                
    },
    onMousedown: function (e) {
        var gantt = this.owner;

        var xy = e.xy;

        var view = gantt.dateView;

        var widthField = view.split('-')[1] + 'Width';
        var width = gantt[widthField];

        var drag = new Edo.util.Drag({
            onMove: function (e) {
                //向右是放大, 左是缩小  季(左) - 时(右)
                var offset = (e.now[0] - xy[0]);

                //document.title = gantt[widthField] + offset;

                gantt.set(widthField, width + offset);
            }
        });

        drag.start(e);

    },
    destroy: function () {

        this.owner.un('mousedown', this.onMousedown, this);

        Edo.plugins.GanttTimeLineResize.superclass.destroy.call(this);
    }
});
Edo.plugins.GanttTimeLineResize.regType('GanttTimeLineResize');
Edo.plugins.GanttProgressLine = function () {
    Edo.plugins.GanttProgressLine.superclass.constructor.call(this);
};
Edo.plugins.GanttProgressLine.extend(Edo.core.Component, {
    deferRenderTime: 200,

    init: function (owner) {

        if (this.owner) return;
        this.owner = owner;

        owner.on('refresh', this.onRefresh, this);
    },
    onRefresh: function (e) {
        var gantt = this.owner;
        //绘制进度线折线
        if (this.progressLineCanvas) {
            Edo.removeNode(this.progressLineCanvas)
            this.progressLineCanvas = null;
        }
        if (this.progressLineTimer) {
            clearTimeout(this.progressLineTimer)
            this.progressLineTimer = null;
        }
        if (gantt.enableDeferRenderProgressLine) {
            this.progressLineTimer = this.doRender.defer(this.deferRenderTime, this);
        } else {
            this.doRender();
        }
    },
    doRender: function () {
        var gantt = this.owner;
        this.progressLineTimer = null;
        if (!gantt.progressLineVisible) return;

        var progressLines = gantt.progressLines;    //这个任务连线的计算, 是否也应该放入插件?(这个算法还是比较简单的吧)

        var view = gantt.viewport;

        Edo.util.Dom.append(view, gantt.progresssb.join(''));

        this.progressLineCanvas = Edo.util.Dom.append(view, '<canvas style="position:absolute;left:0px;top:0px;z-index:19;" width="' + gantt.viewWidth + '" height="' + gantt.viewHeight + '"></canvas>');
        if (window.G_vmlCanvasManager_) this.progressLineCanvas = G_vmlCanvasManager_.initElement(this.progressLineCanvas);
        var ctx = this.progressLineCanvas.getContext('2d');
        //水印
        //            ctx.font = "12px Times New Roman";  
        //            ctx.fillStyle = "Black";
        //            if(ctx.fillText) ctx.fillText("易度甘特图http://www.edogantt.com", 5, 15);  


        for (var i = 0, l = progressLines.length; i < l; i++) {
            var p = progressLines[i];
            if (i == 0) {
                ctx.moveTo(p[0], p[1]);
            } else {
                ctx.lineTo(p[0], p[1]);
            }
        }
        ctx.strokeStyle = 'red';
        ctx.stroke();

    },
    destroy: function () {

        this.owner.un('refresh', this.onRefresh, this);

        Edo.plugins.GanttProgressLine.superclass.destroy.call(this);
    }
});
Edo.plugins.GanttProgressLine.regType('ganttprogressline');
Edo.plugins.GanttTaskLine = function () {
    Edo.plugins.GanttTaskLine.superclass.constructor.call(this);
};
Edo.plugins.GanttTaskLine.extend(Edo.core.Component, {
    deferRenderTime: 200,

    init: function (owner) {

        if (this.owner) return;
        this.owner = owner;

        owner.on('refresh', this.onRefresh, this);
    },
    onRefresh: function (e) {
        var gantt = this.owner;

        if (!gantt.taskLineVisible) return;

        if (this.taskLineTimer) {
            clearTimeout(this.taskLineTimer)
            this.taskLineTimer = null;
        }
        if (gantt.enableDeferRenderTaskLine) {
            this.taskLineTimer = this.doRender.defer(this.deferRenderTime, this);
        } else {
            this.doRender();
        }
    },
    doRender: function () {
        var gantt = this.owner;
        this.taskLineTimer = null;
        if (!gantt.taskLineVisible) return;

        var lineString = this.getView(gantt.startRow, gantt.endRow, gantt.viewWidth, gantt.viewHeight);

        Edo.util.Dom.append(gantt.viewport, lineString);
    },
    destroy: function () {

        this.owner.un('refresh', this.onRefresh, this);

        Edo.plugins.GanttTaskLine.superclass.destroy.call(this);
    },
    getView: function (sr, er, viewWidth, viewHeight) {
        var lines = this.createLines(sr, er);

        var lineString = this.createLineView(lines, viewWidth, viewHeight);

        return lineString;
    },
    createLines: function (sr, er) {

        //找出显示在可视viewport区域的任务关联性连线集合
        var gantt = this.owner;
        var data = gantt.data.view, ganttData = gantt.data;


        if (gantt.live) {
            er = er - sr;
            sr = 0;
        }

        var links = [];

        var startTask = ganttData.view[0];

        for (var i = 0, l = data.length; i < l; i++) {
            var r = data[i];
            var lks = r.PredecessorLink;
            var index1 = r.__index;
            //if(r.Name == '行为需求分析') debugger
            var ProjectUID = r.ProjectUID;
            if (lks && lks.length > 0) {

                for (var j = 0, k = lks.length; j < k; j++) {
                    var lk = lks[j];
                    var ptask = ganttData.getTask(lk.PredecessorUID, ProjectUID);
                    //判断前置和后续任务是否是一个项目的
                    if (!ptask || (ptask.__id && !ganttData.getViewById(ptask.__id))) {
                        continue;
                    }
                    //知道了两个任务,现在判断这两个任务的关系连线是否显示,就看他们的rowIndex范围,是否与目前的可视化范围交互
                    var index2 = ptask.__index;
                    //if(!ganttData.getTask(ptask.UID)) {	//判断是否在hashTask上,重要		            
                    if (!Edo.isValue(index2)) {
                        index2 = ptask.__index = ptask.___index - startTask.___index + startTask.__index;
                    }

                    if ((index1 >= sr && index1 <= er)
                        || (index2 >= sr && index2 <= er)
                        || (index1 < sr && index2 > er)
                        || (index2 < sr && index1 > er)
                    ) {
                        lk.TaskUID = r.UID;
                        links[links.length] = lk;
                    }
                }
            }
        }

        return links;
    },
    createLineView: function (links, viewW, viewH) {
        var t = this.owner;
        var data = t.data.view, ganttData = t.data;

        var widthGet = t.widthGet;
        var linkType = Edo.data.DataGantt.PredecessorLinkType;
        var halfHeight = t.rowHeight / 2; // 1/2的行高
        var offsetH = 5;    //top到item dom的偏移

        var rowHeight = t.rowHeight;
        var startRowTop = t.live ? 0 : t.startRowTop;

        //var baseTop = t.live ? t.startRow * t.rowHeight : 0;

        var sb = [];

        var startDate = t.columns[t.startColumn].date, finishDate = t.columns[t.endColumn].date;
        for (var i = 0, l = links.length; i < l; i++) {
            var link = links[i];

            var r = ganttData.getTask(link.TaskUID, link.ProjectUID);
            var task = ganttData.getTask(link.PredecessorUID, link.ProjectUID);        //出发任务            
            if (!r || !task) continue;
            //if(r.Name == '行为需求分析') debugger
            var w = (r.Finish - r.Start) / widthGet;
            //if(r.Name == '行为需求分析') debugger
            if (r.Start > finishDate || r.Finish < startDate) continue;

            var left = t.getDateLeft(r.Start);               //应该标记                        

            var top = r.__index * rowHeight - startRowTop;          //这里注意一下:如果是做成让每一行都不同高度, 这里的计算应该改变

            if (r.Milestone) {            //里程碑			  
                w = 12;
            } else if (r.Duration === 0) {      //如果是0工时,则...			    
                w = 12;
            }

            if (r.Duration === 0 || r.Milestone) {
                left -= 6;
                w += 12;
            } else if (r.Summary) {
                left -= 3;
                w += 6;
            }

            var linkCls = r.Critical && task.Critical ? 'e-point-critical' : '';

            var lt = linkType[link.Type];

            var sourceDate = task[lt.Date[0]], targetDate = r[lt.Date[1]];

            var horizontalW = (sourceDate - targetDate) / widthGet;      //横向距离			        			        
            var verticalH = (r.__index - task.__index) * t.rowHeight;   //计算出两个任务之间的纵向距离

            var sLeft = t.getDateLeft(task.Start);
            var sW = (task.Finish - task.Start) / widthGet;

            if (task.Duration === 0 || task.Milestone) {
                sLeft -= 6;
                sW += 12;
            } else if (task.Summary) {
                sLeft -= 3;
                sW += 6;
            }

            //确定"出发"坐标,和"终点"坐标
            var points = [];

            var sX, sY, tX, tY;

            var offset = 10;

            var direction = r.__index > task.__index ? 'down' : 'up';
            //首先,根据出发跟终点任务的index,决定down或up;然后,如果有left或right,具体再改变

            if (link.Type == 0) { //FF
                sX = sLeft + sW;
                sY = top - verticalH;

                tX = left + w;
                tY = top;

                //第一个坐标点
                points.add({ x: sX, y: sY + halfHeight });

                //if(tX >= sX + offset){
                if (tX >= sX) {
                    points.add({
                        x: tX,
                        y: sY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + offsetH
                    });
                } else if (tX <= sX) {
                    points.add({
                        x: sX + offset,
                        y: sY + halfHeight
                    });

                    points.add({
                        x: sX + offset,
                        y: tY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + halfHeight
                    });
                    direction = 'left';
                } else {
                    points.add({
                        x: tX + offset,
                        y: sY + halfHeight
                    });
                    points.add({
                        x: tX + offset,
                        y: tY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + halfHeight
                    });
                    direction = 'left';
                }


            } else if (link.Type == 1) { //FS
                sX = sLeft + sW;
                sY = top - verticalH;

                tX = left;
                tY = top;

                //第一个坐标点
                points.add({ x: sX, y: sY + halfHeight });

                //if(tX >= sX+offset){
                if (tX >= sX) {
                    points.add({
                        x: tX,
                        y: sY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + offsetH
                    });
                } else {
                    points.add({
                        x: sX + offset,
                        y: sY + halfHeight
                    });
                    //找到次一个任务节点,并根据上下关系,进行处理
                    var _Y = r.__index > task.__index ? tY : tY + t.rowHeight;

                    points.add({
                        x: sX + offset,
                        y: _Y
                    });
                    points.add({
                        x: tX - offset,
                        y: _Y
                    });
                    points.add({
                        x: tX - offset,
                        y: tY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + halfHeight
                    });

                    direction = 'right';
                }


            } else if (link.Type == 2) { //SF

                sX = sLeft;
                sY = top - verticalH;

                tX = left + w;
                tY = top;

                //第一个坐标点
                points.add({ x: sX, y: sY + halfHeight });

                //if(tX <= sX-offset){
                if (tX <= sX) {
                    points.add({
                        x: tX,
                        y: sY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + offsetH
                    });
                } else {
                    points.add({
                        x: sX - offset,
                        y: sY + halfHeight
                    });
                    //找到次一个任务节点,并根据上下关系,进行处理
                    var _Y = r.__index > task.__index ? tY : tY + t.rowHeight;

                    points.add({
                        x: sX - offset,
                        y: _Y
                    });
                    points.add({
                        x: tX + offset,
                        y: _Y
                    });
                    points.add({
                        x: tX + offset,
                        y: tY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + halfHeight
                    });
                    direction = 'left';
                }
            } else if (link.Type == 3) { //SS
                sX = sLeft;
                sY = top - verticalH;

                tX = left;
                tY = top;

                //第一个坐标点
                points.add({ x: sX, y: sY + halfHeight });

                //if(tX <= sX - offset){
                if (tX <= sX) {
                    points.add({
                        x: tX,
                        y: sY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + offsetH
                    });
                } else if (tX >= sX) {
                    points.add({
                        x: sX - offset,
                        y: sY + halfHeight
                    });

                    points.add({
                        x: sX - offset,
                        y: tY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + halfHeight
                    });
                    direction = 'right';
                } else {
                    points.add({
                        x: tX - offset,
                        y: sY + halfHeight
                    });
                    points.add({
                        x: tX - offset,
                        y: tY + halfHeight
                    });
                    points.add({
                        x: tX,
                        y: tY + halfHeight
                    });
                    direction = 'right';
                }

            }

            //判断此段线,是否在可视区域内:1不在可视区域内,删除;2.与可视区交叉,则修正坐标点,减少dom尺寸


            //根据points坐标点集合,按顺序画出线条...
            var pre = null;
            var linkinfo = link.TaskUID + '$' + link.PredecessorUID;
            for (var ii = 0, ll = points.length; ii < ll; ii++) {
                var p = points[ii];
                if (pre) {
                    if (p.x == pre.x) {   //竖
                        var _y = p.y < pre.y ? p.y : pre.y;
                        var _h = Math.abs(p.y - pre.y);

                        //偏移处理
                        //_y -= 1;
                        _h += 1;

                        if (pre.x < 0 || pre.x > viewW || _y > viewH || _y + _h < 0) {
                        } else {
                            //if(linkinfo == '68$16') debugger
                            var __top = _y, __bottom = _y + _h;
                            if (__top < 0) __top = 0;
                            if (__bottom > viewH) __bottom = viewH;

                            sb[sb.length] = '<div linkinfo="' + linkinfo + '" class="e-gantt-linkinfo e-point ' + linkCls + '" style="left:' + pre.x + 'px;top:' + __top + 'px;width:1px;height:' + (__bottom - __top) + 'px;"></div>';
                        }
                    } else {              //横
                        var _x = p.x < pre.x ? p.x : pre.x;
                        var _w = Math.abs(p.x - pre.x);

                        //偏移处理
                        //pre.y -= 1;
                        _w += 1;

                        if (pre.y < 0 || pre.y > viewH || _x > viewW || _x + _w < 0) {
                        } else {
                            var __left = _x, __right = _x + _w;
                            if (__left < 0) __left = 0;
                            if (__right > viewW) __right = viewW;

                            sb[sb.length] = '<div linkinfo="' + linkinfo + '" class="e-gantt-linkinfo e-point ' + linkCls + '" style="left:' + __left + 'px;top:' + pre.y + 'px;width:' + (__right - __left) + 'px;height:1px;"></div>';
                        }
                    }
                }
                pre = p;
            }
            if (pre) {
                var _left = pre.x, _top = pre.y;
                if (direction == 'up') {
                    _top += 10;
                    _left -= 4;
                } else if (direction == 'down') {
                    _top -= 5;
                    _left -= 4;
                }
                else if (direction == 'left') {
                    _top -= 4;
                    _left += 0;
                }
                else if (direction == 'right') {
                    _top -= 4;
                    _left -= 4;
                }

                if (_left + 16 < 0 || left > viewW || _top + 16 < 0 || _top > viewH) {
                } else {
                    sb[sb.length] = '<div linkinfo="' + linkinfo + '" class="e-gantt-linkinfo ' + 'e-gantt-arrowhead-' + direction + ' ' + (linkCls ? 'e-gantt-arrowhead-' + direction + '-critical' : '') + '" style="left:' + (_left) + 'px;top:' + (_top) + 'px;"></div>';
                }
            }
        }
        return sb.join('');
    }
});

Edo.plugins.GanttTaskLine.regType('gantttaskline');
Edo.plugins.GanttDragDrop = function () {
    Edo.plugins.GanttDragDrop.superclass.constructor.call(this);
};
Edo.plugins.GanttDragDrop.extend(Edo.core.Component, {

    init: function (owner) {

        if (this.owner) return;
        this.owner = owner;

        owner.on('mousedown', this.onMousedown, this);
    },
    onMousedown: function (e) {
        var gantt = this.owner;

        this.record = gantt.getRecordByEvent(e);
        this.dragItem = gantt.getRecordByEvent(e);

        if (!this.dragItem) return;

        if (!gantt.enableDragDrop) return;

        this.ganttBodyBox = gantt.getViewportBox(); //gantt.getViewportBox();

        this.ganttBodyX = this.ganttBodyBox.x;

        var rowid = gantt.createItemId(this.dragItem);

        this.dragItemDom = Edo.getDom(rowid + '-v');

        if (!this.dragItemDom) return;

        var t = e.target;

        if (Edo.util.Dom.hasClass(t, 'e-gantt-item-track') || Edo.util.Dom.hasClass(t, 'e-gantt-item-actual')) {
            return;
        }

        if (Edo.util.Dom.hasClass(t, 'e-gantt-item')) {
            this.handleItemDrag(e);
        } else if (Edo.util.Dom.hasClass(t, 'e-gantt-resize-percentcomplete')) {
            this.handlePercentCompleteDrag(e);
        } else if (Edo.util.Dom.hasClass(t, 'e-gantt-rezie-l')) {
            this.handleStartDrag(e);
        } else if (Edo.util.Dom.hasClass(t, 'e-gantt-rezie-r')) {
            this.handleFinishDrag(e);
        }
    },
    destroy: function () {

        this.owner.un('mousedown', this.onMousedown, this);

        Edo.plugins.GanttDragDrop.superclass.destroy.call(this);
    },
    getDateByOffset: function (x) {
        //1)获得目标对象的坐标
        var left = x - this.ganttBodyX;
        //2)计算出当前目标对象的日期时间       
        return this.owner.getDateByOffset(left);
    },
    handleItemDrag: function (e) {
        var gantt = this.owner, task = this.dragItem;
        if (task.enableDragItem === false) return;
        if (gantt.enableDragItem == false && task.enableDragItem !== true) return;

        var sf = this;

        var record = this.record;
        var r = this.dragItem;

        var t = this.dragItemDom;

        if (gantt.fireEvent('itemdragstart', {
            type: 'itemdragstart',
            event: e,
            item: r,
            action: 'Item'
        }) === false) return;

        var autoScrollTimer;
        var autoScrollView = gantt.autoScrollView;

        Edo.managers.DragManager.startDrag({
            event: e,
            dragObject: t,
            dragStyle: 'cursor:move;',
            proxy: true,
            alpha: .3,
            ondragstart: function (e) {
                Edo.util.Dom.addClass(gantt.el, gantt.dragingCls);

                gantt.set('autoScrollView', true);
            },
            ondragmove: function (e) {
                //调节控制滚动条
                //控制横向拖拽  
                if (!gantt.enableDragItemDown) {
                    e.xy[1] = e.initXY[1];
                }

                //tip
                var x = e.event.xy[0], y = e.event.xy[1];

                var sdate = sf.getDateByOffset(e.xy[0]);
                var edate = new Date(sdate.getTime() + (r.Finish - r.Start));

                var targetRecord = gantt.getRecordByEvent(e.event);
                gantt.fireEvent('itemdrag', {
                    type: 'itemdrag',
                    event: e.event,
                    item: r,
                    record: record,
                    targetRecord: targetRecord,
                    action: 'Item',
                    Start: sdate,
                    Finish: edate
                });

                //sf.autoScrollView(e.event);

                //
                clearInterval(autoScrollTimer);
                autoScrollTimer = sf.autoScrollView.time(30, sf, [e.event], true);

                //status = it.Name;
                //                status = e.event.target.outerHTML;
            },
            ondragcomplete: function (e) {
                //var sss = new Date();                
                var sdate = sf.getDateByOffset(e.xy[0]);
                var edate = new Date(sdate.getTime() + (r.Finish - r.Start));

                Edo.util.Dom.removeClass(gantt.el, gantt.dragingCls);

                var targetRecord = gantt.getRecordByEvent(e.event);
                //alert(targetItem.Name);
                gantt.fireEvent('itemdragcomplete', {
                    type: 'itemdragcomplete',
                    event: e.event,
                    item: r,
                    record: record,
                    targetRecord: gantt.enableDragItemDown ? targetRecord : record,
                    action: 'Item',
                    Start: sdate,
                    Finish: edate
                })

                //alert(new Date() - sss);
                gantt.set('autoScrollView', autoScrollView);
                clearInterval(autoScrollTimer);
            }
        });
    },
    handlePercentCompleteDrag: function (e) {
        var gantt = this.owner, task = this.dragItem;
        if (gantt.enableDragPercentComplete == false && task.enableDragPercentComplete !== true) return;

        var sf = this;
        var r = this.dragItem;

        var t = this.dragItemDom;

        var gw = Edo.util.Dom.getWidth(t);

        var pcEl = Edo.util.Dom.findChild(t, gantt.percentCompleteCls);

        var pw = Edo.util.Dom.getWidth(pcEl);

        var w = (r.Finish - r.Start) / gantt.widthGet;

        function getPercentComplete(width) {
            var p = width / gw;
            if (p > 1) p = 1;
            if (p < 0) p = 0;
            return Math.round(p * 100);
        }

        if (gantt.fireEvent('itemdragstart', {
            type: 'itemdragstart',
            event: e,
            item: r,
            action: 'PercentComplete'
        }) === false) return;


        Edo.managers.ResizeManager.startResize({
            target: t,
            event: e,
            handler: 'e',
            handlerEl: e.target,
            autoResize: false,
            minWidth: 0,
            maxWidth: 100,
            autoProxy: false,
            onresizestart: function (e) {
                Edo.util.Dom.addClass(gantt.el, gantt.dragingCls);


            },
            onresize: function (e) {
                var pc = getPercentComplete(e.size.width);
                var pw = pc / 100 * w;

                pcEl.style.width = pw + 'px';

                gantt.fireEvent('itemdrag', {
                    type: 'itemdrag',
                    event: e.event,
                    item: r,
                    action: 'PercentComplete',
                    PercentComplete: pc
                });

                //sf.autoScrollView(e.event);
            },
            onresizecomplete: function (e) {
                //还原到原来的尺寸
                setTimeout(function () {
                    Edo.util.Dom.setWidth(pcEl, pw);
                }, 1);
                var pc = getPercentComplete(e.size.width);

                Edo.util.Dom.removeClass(gantt.el, gantt.dragingCls);

                gantt.fireEvent('itemdragcomplete', {
                    type: 'itemdragcomplete',
                    event: e.event,
                    item: r,
                    action: 'PercentComplete',
                    PercentComplete: pc
                })
            }
        });

    },
    handleStartDrag: function (e) {
        var gantt = this.owner, task = this.dragItem;
        if (gantt.enableDragStart == false && task.enableDragStart !== true) return;
        this.doStartFinishDrag(e, 'Start');
    },
    handleFinishDrag: function (e) {
        var gantt = this.owner, task = this.dragItem;
        if (gantt.enableDragFinish == false && task.enableDragFinish !== true) return;
        this.doStartFinishDrag(e, 'Finish');
    },
    doStartFinishDrag: function (e, action) {
        var gantt = this.owner, task = this.dragItem;

        var r = this.dragItem;
        var t = this.dragItemDom;
        var sf = this;
        var handler = action == 'Start' ? 'w' : 'e';

        var evt = {
            type: 'itemdragstart',
            event: e,
            item: r,
            action: action
        };
        if (this.fireEvent('beforetaskdrag', evt) === false) return;

        Edo.managers.ResizeManager.startResize({
            target: t,
            event: e,
            handler: handler,
            handlerEl: e.target,
            autoResize: false,
            minWidth: 4,
            onresizestart: function (e) {
                Edo.util.Dom.addClass(gantt.el, gantt.dragingCls);
            },
            onresize: function (e) {
                var x = e.event.xy[0], y = e.event.xy[1];
                var sdate = r.Start, edate = r.Finish;

                var date;
                if (action == 'Start') {     //start

                    date = sf.getDateByOffset(e.box.x);

                    edate = new Date(date.getTime() + (edate - sdate));
                    sdate = date;
                } else {      //finish                    
                    date = sf.getDateByOffset(e.box.right);

                    edate = date;
                }

                evt.type = 'itemdrag';
                evt.event = e.event;
                evt[action] = date;
                gantt.fireEvent('itemdrag', evt);

                sf.autoScrollView(e.event);
            },
            onresizecomplete: function (e) {
                var date;
                if (action == 'Start') {
                    date = gantt.getDateByOffset(e.box.x - gantt.getViewportBox().x);
                } else {
                    date = gantt.getDateByOffset(e.box.right - gantt.getViewportBox().x);
                }

                Edo.util.Dom.removeClass(gantt.el, gantt.dragingCls);

                evt.type = 'itemdragcomplete';
                evt.event = e.event;
                evt[action] = date;
                gantt.fireEvent('itemdragcomplete', evt);
            }
        });
    },
    scrollLeftOffset: 20,
    scrollTopOffset: 20,
    scrollRightOffset: 20,
    scrollBottomOffset: 20,

    horizontalIncrement: 15,
    verticalIncrement: 15,

    autoScrollView: function (e) {
        //判断鼠标坐标位, 如果超过甘特图视图区左/右侧一定偏移尺寸, 则自动向左或向右调节滚动偏移
        var gantt = this.owner, task = this.dragItem;
        var x = e.xy[0], y = e.xy[1];
        var box = this.ganttBodyBox;

        var hbar = gantt.hscrollbar, vbar = gantt.vscrollbar;
        var hvalue = hbar.value, vvalue = vbar.value;


        if (box.right - this.scrollRightOffset <= x && x <= box.right) {
            var so = this.scrollRightOffset / 3;
            if (box.right - so <= x && x <= box.right) {
                hbar.set('value', hvalue + this.horizontalIncrement * 2);
            } else {
                hbar.set('value', hvalue + this.horizontalIncrement);
            }
        } else if (box.x <= x && x <= box.x + this.scrollLeftOffset) {
            var so = this.scrollLeftOffset / 3;
            if (box.x <= x && x <= box.x + so) {
                hbar.set('value', hvalue - this.horizontalIncrement * 2);
            } else {
                hbar.set('value', hvalue - this.horizontalIncrement);
            }
        }


        //        if(box.bottom - this.scrollBottomOffset <= y && y <= box.bottom){
        //            var so = this.scrollBottomOffset / 3;
        //            if(box.bottom - so <= y && y <= box.bottom){
        //                vbar.set('value', vvalue + this.verticalIncrement *2);
        //            }else{
        //                vbar.set('value', vvalue + this.verticalIncrement);
        //            }
        //        }else if(box.y <= y && y <= box.y + this.scrollTopOffset){
        //            var so = this.scrollTopOffset / 3;
        //            if(box.y <= y && y <= box.y + so){
        //                vbar.set('value', vvalue - this.verticalIncrement*2);
        //            }else{
        //                vbar.set('value', vvalue - this.verticalIncrement);
        //            }
        //        }


        //拖拽操作友好性, 还要继续完善!!!

    }
});
Edo.plugins.GanttDragDrop.regType('ganttdragdrop');
Edo.plugins.GanttToolTip = function () {
    Edo.plugins.GanttToolTip.superclass.constructor.call(this);
};
Edo.plugins.GanttToolTip.extend(Edo.core.Component, {

    init: function (owner) {

        if (this.owner) return;
        this.owner = owner;

        owner.on('itemdrag', this.onItemDrag, this);
        owner.on('scroll', this.onScroll, this);
        owner.on('scrollcomplete', this.onScrollComplete, this);

        this.allowClear = false;
        var sf = this;
        setInterval(function () {
            var tip = owner.tip;
            if (tip.autoHide == true && sf.allowClear) {
                Edo.managers.TipManager.clear(tip);
            }
        }, 500);
    },
    onItemDrag: function (e) {
        var gantt = this.owner;

        var tip = gantt.tip;
        e.xy = e.event.xy;
        var x = e.event.xy[0], y = e.event.xy[1], html = '';

        var rowY = e.event.xy[1];
        try {
            rowY = gantt[gantt.dragItemBoxMethod](e.item).y;
        } catch (e) {

        }

        switch (e.action) {
            case 'Item':
                html = gantt.itemDragTipRenderer(e);
                x = e.xy[0], y = e.xy[1];

                var viewBox = gantt.viewBox;
                if (viewBox.right - 210 < x && x < viewBox.right && viewBox.y < y && y < viewBox.y + 45) {
                    x = viewBox.right - 210;
                    y = viewBox.y + viewBox.height - 45;
                } else {
                    x = viewBox.right - 210;
                    y = viewBox.y + 5;
                }

                break;
            case 'PercentComplete':
                html = gantt.percentCompleteDragTipRenderer(e);
                x = e.xy[0], y = e.xy[1];
                y = rowY - 25;
                x -= 30;
                break;
            case 'Start':
                html = gantt.startDragTipRenderer(e);
                x = e.xy[0], y = e.xy[1];
                y = rowY - 25;
                break;
            case 'Finish':
                html = gantt.finishDragTipRenderer(e);
                x = e.xy[0], y = e.xy[1];
                y = rowY - 25;
                break;
        }

        Edo.managers.TipManager.clear(tip);
        tip.html = html;
        Edo.managers.TipManager.show(x, y, tip);
    },
    onScroll: function (e) {
        var gantt = this.owner;
        if (!gantt.scrollTipVisible) return;
        //debugger
        //if (!gantt.isDisplay()) return;

        this.allowClear = true;

        var html, x, y;
        var bdbox = gantt.getViewportBox();

        //gantt.refresh(false); 
        //gantt.markViewRegion(); //需要根据当前滚动条位置, 计算得到当前的显示区域

        if (gantt.direction == 'vertical') {
            x = bdbox.right - 167;
            y = bdbox.y + 5;

            var r = gantt.data.getAt(gantt.startRow);

            html = gantt.verticalScrollTipRenderer(r, gantt, e);
        } else {
            x = bdbox.x + 5;
            y = bdbox.bottom - 17 - 10;

            var date = gantt.getDateByOffset(0);
            html = gantt.horizontalScrollTipRenderer(date, gantt, e);
        }

        var tip = gantt.tip;
        Edo.managers.TipManager.clear(tip);
        tip.html = html;
        tip.autoHide = false;
        Edo.managers.TipManager.show(x, y, tip);
    },
    onScrollComplete: function (e) {
        this.owner.tip.autoHide = true;

        var sf = this;
        setTimeout(function () {
            sf.allowClear = false;
        }, 800);
    },
    destroy: function () {
        this.owner.un('itemdrag', this.onItemDrag, this);
        this.owner.un('scroll', this.onScroll, this);
        Edo.plugins.GanttToolTip.superclass.destroy.call(this);
    }
});
Edo.plugins.GanttToolTip.regType('gantttooltip');
Edo.plugins.GanttMenu = function () {
    Edo.plugins.GanttMenu.superclass.constructor.call(this);
};
Edo.plugins.GanttMenu.extend(Edo.core.Component, {
    init: function (owner) {
        if (this.owner) return;
        this.owner = owner;

        owner.on('contextmenu', this.oncontextmenu, this);
    },
    destroy: function () {
        this.owner.un('contextmenu', this.oncontextmenu, this);
        Edo.plugins.GanttMenu.superclass.destroy.call(this);
    },
    oncontextmenu: function (e) {
        var gantt = this.owner;
        if (gantt.enableMenu === false) return false;
        var ct = gantt;
        if (ct && !ct.within(e)) return;
        e.stopDefault();

        var menu = this.getMenu();
        Edo.managers.PopupManager.createPopup({
            target: menu,
            x: e.xy[0] + 1,
            y: e.xy[1] + 1,
            onout: function (e) {
                Edo.managers.PopupManager.removePopup(menu);
            }
        });
    },
    getMenu: function () {
        var gantt = this.owner.gantt || this.owner;

        function viewModeClick(e) {
            gantt.set('viewMode', this.name);
        }

        if (!this.menu) {
            this.menu = Edo.create({
                type: 'menu',
                shadow: true,
                autoHide: true,
                visible: false,
                width: 120,
                children: [
                    {
                        type: 'button',
                        name: 'gototask',
                        icon: 'e-icon-gototask',
                        text: Edo.lists.Gantt.gotoTask,
                        onclick: function (e) {
                            var r = gantt.getSelected();
                            if (r) {
                                //gantt.scrollIntoTask(r, true);
                                gantt.scrollIntoTask(r);
                            }
                        }
                    }, {
                        type: 'hsplit', name: 'split1'
                    },
                    {
                        type: 'button',
                        icon: 'e-icon-upgrade',
                        text: Edo.lists.Gantt.upgradeTaskText,
                        name: 'upgrade'
                    },
                    {
                        type: 'button',
                        icon: 'e-icon-downgrade',
                        text: Edo.lists.Gantt.downgradeTaskText,
                        name: 'downgrade'
                    }, {
                        type: 'hsplit'
                    },
                    {
                        type: 'button',
                        icon: 'e-icon-add',
                        text: Edo.lists.Gantt.addTask,
                        name: 'add'
                    },
                    {
                        type: 'button',
                        icon: 'e-icon-edit',
                        text: Edo.lists.Gantt.editTask,
                        name: 'edit'
                    }, {
                        type: 'button',
                        icon: 'e-icon-delete',
                        text: Edo.lists.Gantt.deleteTask,
                        name: 'delete'
                    },
                    {
                        type: 'hsplit', name: 'split2'
                    }, {
                        type: 'button',
                        text: Edo.lists.Gantt.trackText,
                        popupWidth: 130,
                        menu: [
                            {
                                type: 'button',
                                icon: gantt.progressLineVisible ? 'e-icon-checkbox-checked' : 'e-icon-checkbox',
                                checked: gantt.progressLineVisible,
                                text: Edo.lists.Gantt.progressLine,
                                onclick: function () {
                                    if (this.checked) {
                                        this.set('icon', 'e-icon-checkbox');
                                    } else {
                                        this.set('icon', 'e-icon-checkbox-checked');
                                    }
                                    this.checked = !this.checked;

                                    gantt.set('progressLineVisible', this.checked);
                                }
                            }, {
                                type: 'hsplit'
                            }, {
                                type: 'button',
                                text: Edo.lists.Gantt.createbaseline,
                                onclick: function () {
                                    gantt.data.createBaseline();
                                    gantt.data.refresh();
                                }
                            }, {
                                type: 'button',
                                text: Edo.lists.Gantt.clearbaseline,
                                onclick: function () {
                                    gantt.data.clearBaseline();
                                    gantt.data.refresh();
                                }
                            }
                        ]
                    },
                    {
                        type: 'hsplit', name: 'split3'
                    },
                    {
                        name: 'dateviewmenu',
                        type: 'button',
                        text: Edo.lists.Gantt.timeLine,
                        onpopupshow: function (e) {

                            var dateviewbar = Edo.getByName('dateviewbar', this.menu)[0];
                            var viewbar = Edo.getByName(gantt.dateView, dateviewbar)[0];
                            dateviewbar.set('selectedItem', viewbar);
                        },
                        menu: [
                            { name: 'dateviewbar', type: 'togglebar', width: '100%', layout: 'vertical', border: 0, padding: 0, verticalGap: 0,
                                onselectionchange: function (e) {
                                    gantt.set('dateView', e.source.selectedItem.name);
                                },
                                children: [
                                    { type: 'button', text: Edo.lists.Gantt.yearQuarter, width: '100%', icon: ' ', name: 'year-quarter' },
                                    { type: 'button', text: Edo.lists.Gantt.yearMonth, width: '100%', icon: ' ', name: 'year-month' },
                                    { type: 'button', text: Edo.lists.Gantt.yearWeek, width: '100%', icon: ' ', name: 'year-week' },
                                    { type: 'button', text: Edo.lists.Gantt.yearDay, width: '100%', icon: ' ', name: 'year-day' },
                                    { type: 'button', text: Edo.lists.Gantt.quarterMonth, width: '100%', icon: ' ', name: 'quarter-month' },
                                    { type: 'button', text: Edo.lists.Gantt.quarterWeek, width: '100%', icon: ' ', name: 'quarter-week' },
                                    { type: 'button', text: Edo.lists.Gantt.quarterDay, width: '100%', icon: ' ', name: 'quarter-day' },
                                    { type: 'button', text: Edo.lists.Gantt.monthWeek, width: '100%', icon: ' ', name: 'month-week' },
                                    { type: 'button', text: Edo.lists.Gantt.monthDay, width: '100%', icon: ' ', name: 'month-day' },
                                    { type: 'button', text: Edo.lists.Gantt.weekDay, width: '100%', icon: ' ', name: 'week-day' },
                                    { type: 'button', text: Edo.lists.Gantt.weekHour, width: '100%', icon: ' ', name: 'week-hour' },
                                    { type: 'button', text: Edo.lists.Gantt.dayHour, width: '100%', icon: ' ', name: 'day-hour' }
                                ]
                            }
                        ]
                    }, {
                        type: 'button',
                        name: 'viewmodemenu',
                        text: Edo.lists.Gantt.viewText,
                        onpopupshow: function (e) {
                            this.menu.getChildren().each(function (c) {
                                c.set('icon', 'e-icon-checkbox');
                            });
                            Edo.getByName(gantt.viewMode, this.menu)[0].set('icon', 'e-icon-checkbox-checked');
                        },
                        menu: [
                            {
                                type: 'button',
                                text: Edo.lists.Gantt.ganttView,
                                icon: 'e-icon-checkbox',
                                name: 'gantt',
                                onclick: viewModeClick
                            }, {
                                type: 'button',
                                icon: 'e-icon-checkbox',
                                text: Edo.lists.Gantt.trackView,
                                name: 'track',
                                onclick: viewModeClick
                            }
                        ]
                    }
                    , {
                        type: 'hsplit', name: 'split4'
                    },
                    {
                        type: 'button',
                        name: 'viewdate',
                        text: '项目日期',
                        popupWidth: 165,
                        onpopupshow: this.onViewDateShow.bind(this),
                        menu: {
                            type: 'menu',
                            cls: 'e-menu-clear',
                            verticalGap: 3,
                            name: 'viewdatemenu',
                            children:
                            [
                                {
                                    type: 'formitem',
                                    label: '开始日期:',
                                    children: [
                                        {
                                            type: 'date',
                                            name: 'start',
                                            ondatechange: this.onStartClick.bind(this)
                                        }
                                    ]
                                },
                                {
                                    type: 'formitem',
                                    label: '完成日期:',
                                    children: [
                                        {
                                            type: 'date',
                                            name: 'finish',
                                            ondatechange: this.onFinishClick.bind(this)
                                        }
                                    ]
                                }
                            ]
                        }
                    }
                ],
                render: document.body
            });
        }
        return this.menu;
    },
    onStartClick: function (e) {
        var gantt = this.owner;
        gantt.set('startDate', e.date);
    },
    onFinishClick: function (e) {
        var gantt = this.owner;
        gantt.set('finishDate', e.date);
    },
    onViewDateShow: function (e) {
        var gantt = this.owner;
        var start = Edo.getByName('start', e.source.menu)[0];
        var finish = Edo.getByName('finish', e.source.menu)[0];

        start.set('date', gantt.startDate);
        finish.set('date', gantt.finishDate);
    }
});
Edo.plugins.GanttMenu.regType('ganttmenu');


Edo.lists.GanttHeader = function (table) {
    Edo.lists.GanttHeader.superclass.constructor.call(this);
    this.table = table;
};
Edo.lists.GanttHeader.extend(Edo.core.Component, {
    _createView: function (sc, ec) {
        var t = this.table;
        if (sc < 0) sc = 0;
        if (ec < 0 || ec >= t.columns.length) ec = t.columns.length - 1;

        //var columns = t.groupColumns;
        var columns = t.columns;


        //根据startColumn和endColumn, 拼接出一个columns出来
        var cols = [], groups = [], colHash = {};
        for (var i = sc; i <= ec; i++) {
            var c = columns[i];
            if (colHash[c.groupid]) continue;
            var gc = t.getColumn(c.groupid);
            groups.add(gc);

            colHash[c.groupid] = gc;
        }
        columns = groups;
        //宽度调整
        var preOffset = 0;
        for (var i = 0, l = columns.length; i < l; i++) {
            var gc = columns[i];
            var aw = 0;
            for (var j = 0, k = gc.columns.length; j < k; j++) {
                var c = gc.columns[j];
                c.offsetWidth = 0;
                if (c.index < sc) continue;
                //if(c.index > ec) break;
                aw += c.width;
            }
            //gc.offsetWidth = parseInt(gc.offsetWidth) || 0;
            aw += preOffset;
            if (gc.offsetWidth) {
                aw += gc.offsetWidth;

                preOffset = -gc.offsetWidth;
            }
            gc.width = aw;
        }
        var tops = columns;
        var bottoms = t.columns.getRange(sc, ec);


        var id = t.id;
        var allColumnWidth = t.getColumnAllWidth() + 1000;

        function renderer(v, col) {
            //column:sortDir,sortType
            var s = '';
            if (col.sortDir) {
                if (isIE || isSafari) {
                    s = '&nbsp;&nbsp;&nbsp;';
                } else {
                    s = '&nbsp;&nbsp;';
                }
            }
            if (!v && v !== 0) v = '&nbsp;';
            return '<div class="e-table-header-cell-inner ' + (col.sortDir ? "e-table-sort-icon" : '') + '" style="overflow:hidden;line-height:' + col.height + 'px;font-size:12px;white-space: nowrap;">' + v + s + '</div>';
        }
        var headerStr = '<div class="e-table-header-row" style="overflow:hidden;width:' + allColumnWidth + 'px;height:' + ((t.columnDepth + 1) * t.columnHeight) + 'px;">';
        var sb = [];

        function createHeaderColumn(columns, left, top) {
            for (var i = 0, l = columns.length; i < l; i++) {
                var c = columns[i];

                var cw = c.width;
                var ch = t.columnHeight;
                c.height = ch;

                var sortCls = '';
                if (c.sortDir == 'asc') sortCls = 'e-table-sort-desc';
                else if (c.sortDir == 'desc') sortCls = 'e-table-sort-asc';

                sb[sb.length] = '<div id=' + (id + "|" + c.id) + ' class="e-table-cell ' + t.headerCellCls + ' ' + (sortCls) + ' ' + (c.headerCls || '') + '" style="text-align:';
                sb[sb.length] = c.headerAlign || 'left';
                sb[sb.length] = ';position:absolute;left:';
                sb[sb.length] = left + 'px;top:';
                sb[sb.length] = top + 'px;width:';
                sb[sb.length] = cw + 'px;height:';
                sb[sb.length] = ch;
                sb[sb.length] = 'px;';
                sb[sb.length] = '">';

                var v = typeof (c.header) === 'function' ? c.header(c, t.data, t) : renderer(c.header, c);
                sb[sb.length] = Edo.isValue(v) ? v : '&nbsp;';

                sb[sb.length] = '</div>';

                left += cw;
            }
        }

        var left = isBorderBox ? 1 : -1;
        createHeaderColumn(tops, left, 0);
        createHeaderColumn(bottoms, left, t.columnHeight);

        headerStr += sb.join('') + '</div>';

        return headerStr;
    }
});
Edo.lists.GanttBody = function (table) {
    Edo.lists.GanttBody.superclass.constructor.call(this, table);
};
Edo.lists.GanttBody.extend(Edo.lists.TableBody, {
    _createView: function (sr, sc, er, ec, viewW, viewH) {

        var t = this.table;

        if (sr < 0) sr = 0;
        if (sc < 0) sc = 0;
        if (er < 0 || er >= t.data.getCount()) er = t.data.getCount() - 1;
        if (ec < 0 || ec >= t.columns.length) ec = t.columns.length - 1;
        t.startColumn = sc;
        t.endColumn = ec;
        //                
        var data = t.data, view = data[t.dataViewField];
        var columns = t.columns;
        var sb = [];    //可以分两次刷新
        var top = 0;

        t.selectHash = {};
        t.selecteds.each(function (o) {
            t.selectHash[o.__id] = 1;
        });

        t.resourceSelectHash = {};
        if (t.resourceSelecteds) {
            t.resourceSelecteds.each(function (o) {
                t.resourceSelectHash[o.__id] = 1;
            });
        }

        var columnView = 'day';
        if (t.dateView) {
            columnView = t.dateView.split('-')[1];
        }

        var sdate = columns[sc].start;            //开始的column
        var edate = columns[ec].finish.add(Date.DAY, 1);
        var isTrack = t.viewMode == 'track'; //是否是跟踪甘特图		
        var dayView = columnView == 'day';

        sb[sb.length] = '';

        //背景:单元格
        var left = 0;
        viewH = viewH || (er - sr + 1) * t.rowHeight; //t.viewHeight;
        if (viewH < t.viewHeight) viewH = t.viewHeight;
        if (viewW) t.viewWidth = viewW;
        viewW = viewW || t.viewWidth;

        var cw = t.columnWidth;

        var WeekStartDay = t.WeekStartDay;

        var isWorkingDate = t.data.isWorkingDate;

        for (var i = sc, l = ec; i <= l; i++) {
            var c = columns[i];
            var day = c.date.getDay();
            var cls = '';

            var colW = cw;

            var style = '';

            var weekend = (columnView == 'day' || columnView == 'hour') && isWorkingDate && t.data.isWorkingDate(c.date) == false;
            if (weekend) {
                cls = 'e-gantt-weekend';
            }

            if (columnView == 'day' && day == WeekStartDay) {
                style = 'border-left:dashed 1px #000;';
                //left -= 1;
            }
            if (columnView != 'day') {
                style = 'border-right:solid 1px #ccc;';
                colW--;
            }

            if (weekend) {
                sb[sb.length] = '<div class="e-gantt-date-bg ' + cls + '" style="' + style + ';position:absolute;overflow:hidden;left:'
                sb[sb.length] = left;
                sb[sb.length] = 'px;top:0px;width:';
                sb[sb.length] = colW;
                sb[sb.length] = 'px;height:';
                sb[sb.length] = viewH;
                sb[sb.length] = 'px;"></div>';
            }

            left += cw;
        }

        var boxHash = {};

        //进度线绘制信息
        t.progressLines = [];
        t.progresssb = [];

        var top = 0;
        for (var j = sr; j <= er; j++) {
            var r = view[j];
            r.PercentComplete = r.PercentComplete || 0;
            var h = r.__height;
            if (isNaN(r.__height)) {
                h = this.rowHeight;
            };
            if (isBorderBox) h -= 1;

            t.getItemHtml(r, j, sb, top, sdate, edate, isTrack, boxHash, t.progresssb, t.progressLines);

            top += h;

            if (isBorderBox) top += 1;
        }

        //延迟创建渲染透明操作元素		
        if (this.controlTimer) {
            clearTimeout(this.controlTimer)
            this.controlTimer = null;
        }
        var sf = this;
        if (t.useItemProxy) {
            this.controlTimer = setTimeout(function () {
                sf.controlTimer = null;

                var sbb = [];
                for (var j = sr; j <= er; j++) {
                    var r = view[j];
                    var box = boxHash[r.__id];
                    t.getItemProxy(r, box[0], box[1], box[2], box[3], box[4], true, sbb, boxHash);
                }
                Edo.util.Dom.append(t.viewport, sbb.join(''));
            }, 800);
        }

        return sb.join('');
    }
});


Edo.lists.Gantt = function () {
    Edo.lists.Gantt.superclass.constructor.call(this);

}
Edo.lists.Gantt.extend(Edo.lists.TreeGrid, {
    rowOverCls: 'e-gantt-item-over',
    rowSelectedCls: 'e-gantt-item-selected',

    enableRowCls: false,
    enableColumnCls: false,
    useItemProxy: true,
    dragItemBoxMethod: 'getItemBox',

    weeks: ['日', '一', '二', '三', '四', '五', '六'],
    fullView: false,

    enableSummary: false,

    selectToTask: false,

    enableDeferRenderProgressLine: true,

    enableDeferRenderTaskLine: true,

    viewMode: 'gantt',

    baselineIndex: 0,

    progressLineVisible: false,

    taskLineVisible: true,

    taskNameVisible: true,

    scrollTipVisible: false,

    collapseVisible: false,

    dateView: 'week-day',

    weekStartDay: 0,

    //日期列宽度属性
    minuteWidth: 90,
    hourWidth: 96,
    dayWidth: 24,
    weekWidth: 50,
    monthWidth: 80,
    quarterWidth: 120,

    _minuteWidth: 120,
    _hourWidth: 96,
    _dayWidth: 24,
    _weekWidth: 50,
    _monthWidth: 80,
    _quarterWidth: 120,

    //拖拽相关属性

    enableDragDrop: true,                   //

    enableDragItem: true,

    enableDragStart: true,                  //开始日期拖拽

    enableDragFinish: true,                 //完成日期拖拽

    enableDragPercentComplete: true,        //完成百分比拖拽

    enableDragPredecessorLink: true,

    startDate: new Date(),

    finishDate: new Date().add(Date.DAY, 7),

    //一些插件对象
    progressLineType: 'ganttprogressline',      //进度线插件
    taskLineType: 'gantttaskline',              //任务连线插件
    ganttDragDropType: 'ganttdragdrop',         //甘特图item拖拽插件
    ganttToolTipType: 'gantttooltip',           //甘特图tip提示框插件
    timeLineResize: 'ganttTimeLineResize',

    //开始偏移日期
    startOffsetDays: 0,
    //完成偏移日期
    finishOffsetDays: 7,

    itemRenderer: null,
    percentCompleteRenderer: null,

    //渲染器

    //    taskNameRenderer: function(task, gantt){
    //        return task.Name;
    //    },    

    taskTipRenderer: function (r, gantt, viewMode) {
        var isActual = viewMode == 'actual';
        var isTrack = viewMode == 'track';

        var taskname;
        if (r.Summary) taskname = Edo.lists.Gantt.summaryText;
        else if (r.Milestone) taskname = Edo.lists.Gantt.milestoneText;
        else if (r.Critical) taskname = Edo.lists.Gantt.criticalText;
        else taskname = Edo.lists.Gantt.taskText;

        if (isTrack) taskname = Edo.lists.Gantt.baselineText;
        else if (isActual) taskname = Edo.lists.Gantt.actualText;

        var bl = r.Baseline ? r.Baseline[gantt.baselineIndex] : null;

        var columnView = this.getColumnDateView();
        var dateformat = Edo.lists.Gantt.tipDateFormat;

        var start = r.Start;
        if (isTrack) start = bl.Start;
        else if (isActual) start = r.ActualStart;

        var finish = r.Finish;
        if (isTrack) finish = bl.Finish;
        else if (isActual) finish = r.ActualFinish;

        var html = '<div style="text-align:center;font-size:13px;line-height:18px;"><b>' + taskname + '</b></div>'
            + '<div style="line-height:18px;"><b>' + Edo.lists.Gantt.taskText + ': </b>' + r.Name + '</div>'
            + '<div style="line-height:18px;"><b>' + Edo.lists.Gantt.percentCompleteText + ': </b>' + r.PercentComplete + '%</div>'
            + '<div style="line-height:18px;"><b>' + Edo.lists.Gantt.startText + ': </b>' + (start ? start.format(dateformat) : '') + '</div>'
            + '<div style="line-height:18px;"><b>' + Edo.lists.Gantt.finishText + ': </b>' + (finish ? finish.format(dateformat) : '') + '</div>'
        return html;
    },

    predecessorLinkTipRenderer: function (task, link, gantt, e) {
        preTask = gantt.data.getTask(link.PredecessorUID);
        var html = '<div>' + Edo.lists.Gantt.linktaskText + '：' + Edo.data.DataGantt.PredecessorLinkType[link.Type].Name + '</div><div>' + Edo.lists.Gantt.fromText + '： ' + preTask.Name + '</div><div>' + Edo.lists.Gantt.toText + '： ' + task.Name + '</div>';
        return html;
    },

    itemDragTipRenderer: function (e) {
        var s = '<b>' + Edo.lists.Gantt.startText + ':</b>' + e.Start.format(Edo.lists.Gantt.tipDateFormat) + '<br/><b>截止日期:</b>' + e.Finish.format(Edo.lists.Gantt.tipDateFormat);

        return s;
    },

    percentCompleteDragTipRenderer: function (e) {
        return '<b>' + Edo.lists.Gantt.percentCompleteText + ':</b>' + e.PercentComplete + '%';
    },

    startDragTipRenderer: function (e) {
        return '<b>' + Edo.lists.Gantt.startText + ':</b>' + e.Start.format(Edo.lists.Gantt.tipDateFormat);
    },

    finishDragTipRenderer: function (e) {
        return '<b>' + Edo.lists.Gantt.finishText + ':</b>' + e.Finish.format(Edo.lists.Gantt.tipDateFormat);
    },

    verticalScrollTipRenderer: function (r, gantt, e) {
        return '<div style="width:150px;overflow:hidden;white-space:nowrap;">' + (Edo.isValue(r.ID) ? Edo.lists.Gantt.No + ' ' + r.ID + '<br/> ' : '') + Edo.lists.Gantt.name + r.Name + '</div>';
    },

    horizontalScrollTipRenderer: function (date, gantt, e) {
        return '<div style="width:105px;overflow:hidden;">' + date.format(Edo.lists.Gantt.scrollDateFormat) + '</div>';
    },

    headerHeight: 48,
    horizontalScrollRefresh: true,
    headerClass: Edo.lists.GanttHeader,
    bodyClass: Edo.lists.GanttBody,
    elCls: 'e-table-sliver e-gantt e-supergrid e-table e-dataview e-div',

    itemCls: 'e-gantt-row',
    itemSelector: 'e-gantt-row',
    ganttItemCls: 'e-gantt-item',
    percentCompleteCls: 'e-gantt-percentcomplete',
    dragingCls: 'e-gantt-draging',                                //当拖拽时, 给甘特图设置这个样式,以便消除一些外观效果

    initEvents: function () {

        this.on('selectionchange', this._onGanttSelectionChange, this);

        this.on('dblclick', this._onGanttDblClick, this);

        Edo.lists.Gantt.superclass.initEvents.call(this);
    },
    _onGanttDblClick: function (e) {
        var linkinfo = e.target.linkinfo || e.target.getAttribute("linkinfo");
        if (linkinfo) {
            var uids = linkinfo.split('$');
            this.fireEvent('tasklinkdblclick', {
                type: 'tasklinkdblclick',
                source: this,
                task: this.data.getTask(uids[0]),
                predecessorTask: this.data.getTask(uids[1])
            });
        } else {
            var r = this.getSelected();
            if (r) {
                this.fireEvent('taskdblclick', {
                    type: 'taskdblclick',
                    source: this,
                    task: r
                });
            }
        }
    },
    _onGanttSelectionChange: function (e) {
        var sel = this.getSelected();
        if (sel && this.selectToTask) {
            this.scrollIntoTask(sel);
        }
    },
    _setColumns: function () { },

    _setData: function (data) {
        //必须经过dataTree转换
        if (typeof data == 'string') data = window[data];
        if (!data) return;
        if (data.componentMode != 'data') {
            if (!this.data) {
                data = new Edo.data.DataGantt(data);
            } else {
                this.data.load(data);
                return;
            }
        }
        Edo.lists.Gantt.superclass._setData.call(this, data);
    },
    columnRenderer: function (column, dataview, view) {
        return column.header;
    },

    r: '%E6%9C%AC%E7%89%88%E6%9C%AC%E5%85%8D%E8%B4%B9%E8%AF%95%E7%94%A8%E5%88%B0%E6%9C%9F,%E5%8F%AF%E5%8E%BB%E6%98%93%E5%BA%A6%E7%94%98%E7%89%B9%E5%9B%BE%E7%BD%91%E7%AB%99%E9%87%8D%E6%96%B0%E4%B8%8B%E8%BD%BD%E6%88%96%E8%B4%AD%E4%B9%B0%E6%AD%A3%E5%BC%8F%E7%89%88',
    //d: new Date(2010, 11, 1).getTime(),
    createColumns: function (start, finish) {
        var sss = new Date();
        //        if(new Date().getTime() > 32506329600000) {
        //            var dd = window['dec'+'od'+'eU'+'RI'];

        //            eval("window['al'+'ert'](dd(this['r']))");
        //        }

        this.startDate = start;
        this.finishDate = finish;

        var weeks = this.weeks;
        var columns = [];

        var sf = this;
        var dateView = this.dateView.split('-');
        var top = dateView[0], bottom = dateView[1];
        //top:周,月,季,年; bottom:天,周,月,季
        //1)按top计算出groupColumn; 2)遍历这个column,按bottom计算


        start = start.add(Date.DAY, -this.startOffsetDays).clearTime();
        finish = finish.add(Date.DAY, this.finishOffsetDays).clearTime();  //多加7天
        if (finish <= start) {
            finish = start.add(Date.DAY, this.finishOffsetDays).clearTime();  //多加7天
        }

        function createBottomColumns(columns, view) {
            if (columns.length == 1) columns[0].finish = columns[0].finish.add(Date.DAY, 1);


            for (var i = 0, l = columns.length; i < l; i++) {
                var c = columns[i];
                var sd = c.start, fd = c.finish;

                var cs = c.columns = [];

                var fdTime = fd.getTime();



                for (var d = sd; d.getTime() < fdTime; ) {
                    var dayView = view == 'day';
                    var v = tv = nextd = null;

                    if (view == 'minute') {
                        v = '<div class="e-gantt-dateview-minute"><div>00</div><div>15</div><div>30</div><div>45</div></div>';
                        tv = '';
                        nextd = new Date(d.getTime() + 3600000);
                    } else if (view == 'hour') {
                        //v = '<div class="e-gantt-viewhour-am">上午</div><div class="e-gantt-viewhour-pm">下午</div>';
                        v = '';
                        //tv = d.format(Edo.lists.Gantt.hourFormat);
                        tv = '';
                        //nextd = d.add(Date.DAY, 1);
                        nextd = new Date(d.getTime() + 86400000);
                    } else if (dayView) {
                        v = weeks[d.getDay()];
                        tv = (d.getMonth() + 1) + "-" + d.getDate();
                        //tv =d.format(Edo.lists.Gantt.weekFormat);
                        //nextd = d.add(Date.DAY, 1);
                        nextd = new Date(d.getTime() + 86400000);
                    } else if (view == 'week') {
                        var d2 = d.add(Date.DAY, 6);
                        v = d.getDate() + ' - ' + d2.getDate();
                        tv = d.format('Y-m-d') + ' ' + Edo.lists.Gantt.toText + ' ' + d2.format('Y-m-d');
                        nextd = d2.add(Date.DAY, 1);
                    } else if (view == 'month') {
                        v = (d.getMonth() + 1) + Edo.lists.Gantt.monthText;
                        tv = d.format(Edo.lists.Gantt.monthFormat);
                        nextd = d.add(Date.MONTH, 1);
                    } else if (view == 'quarter') {
                        v = getQuarter(d.getMonth() + 1);
                        tv = d.format(Edo.lists.Gantt.quarterFormat) + (d.getMonth() + 3) + '月';
                        nextd = d.add(Date.MONTH, 3);
                    }

                    var _start = new Date(d.getTime());
                    var column = {
                        enableMove: false,
                        enableResize: false,
                        enableSort: false,
                        date: _start,
                        start: _start,
                        finish: new Date(nextd.getTime()),
                        header: '<div  title="' + tv + '">' + v + '</div>',
                        headerAlign: 'center'
                    };
                    //cs.add(column);
                    cs[cs.length] = column;

                    if (view == 'hour') {
                        column.headerCls = 'e-gantt-dateview-hour';
                    }

                    //if(!dayView) column.header = sf.columnRenderer(column, sf.dateView, view);                    
                    if (sf.columnRenderer) {
                        column.header = sf.columnRenderer(column, sf.dateView, view);
                    }

                    d = nextd;
                }
            }
        }

        function getQuarter(month) {
            if (month >= 1 && month <= 3) return 1;
            if (month >= 4 && month <= 6) return 2;
            if (month >= 7 && month <= 9) return 3;
            return 4;
        }

        switch (this.dateView) {
            case 'hour-minute':
                var finishTime = finish.getTime();
                for (var date = start; date.getTime() <= finishTime; ) {
                    var sd = new Date(date.getTime());
                    var fd = new Date(sd.getTime() + 3600000); //增加一小时

                    var tit = (date.getMonth() + 1) + "月" + date.getDate() + "日 " + date.getHours() + '点';
                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        //header: date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"-"+date.getHours(),//date.format('Y-m-d'),
                        header: '<div title="' + (date.getFullYear()) + '年">' + tit + '</div>',
                        headerAlign: 'center'
                    };
                    //columns.add(column);
                    columns[columns.length] = column;

                    //column.header = this.columnRenderer(column, this.dateView, 'week');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;
                //创建"分"列
                createBottomColumns(columns, bottom);

                break;
            case 'day-hour':
                var finishTime = finish.getTime();
                for (var date = start; date.getTime() <= finishTime; ) {
                    var sd = new Date(date.getTime()); //date.clone();
                    //-sd.getDay() + this.weekStartDay 获得一周的第一天
                    //var fd = sd.add(Date.DAY, -sd.getDay() + this.weekStartDay + 7);
                    //var fd = sd.add(Date.DAY, 1);
                    var fd = new Date(sd.getTime() + 86400000);

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate(), //date.format('Y-m-d'),
                        headerAlign: 'center'
                    };
                    //columns.add(column);
                    columns[columns.length] = column;

                    //column.header = this.columnRenderer(column, this.dateView, 'week');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;
                //创建"天"列
                createBottomColumns(columns, bottom);

                break;
            case 'week-hour':
                for (var date = start; date <= finish; ) {
                    var sd = new Date(date.getTime()); //date.clone();
                    //-sd.getDay() + this.weekStartDay 获得一周的第一天
                    var fd = sd.add(Date.DAY, -sd.getDay() + this.weekStartDay + 7);

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate(), //date.format('Y-m-d'),
                        headerAlign: 'center'
                    };
                    columns[columns.length] = column;

                    //column.header = this.columnRenderer(column, this.dateView, 'week');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;
                //创建"天"列            
                createBottomColumns(columns, bottom);

                break;
            case 'week-day':
                var finishTime = finish.getTime();
                for (var date = start; date.getTime() <= finishTime; ) {
                    var sd = date.clone();
                    //-sd.getDay() + this.weekStartDay 获得一周的第一天
                    var fd = sd.add(Date.DAY, -sd.getDay() + this.weekStartDay + 7);
                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.format('Y-m-d'),
                        headerAlign: 'center'
                    };
                    //columns.add(column);
                    columns[columns.length] = column;
                    column.header = this.columnRenderer(column, this.dateView, 'week');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;
                //创建"天"列
                createBottomColumns(columns, bottom);

                break;
            case 'month-day':
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();
                    var fd = new Date(sd.getFullYear(), sd.getMonth() + 1, 1);    //下个月的第一天

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.format('Y-m-d'),
                        headerAlign: 'center'
                    };
                    columns.add(column);

                    column.header = this.columnRenderer(column, this.dateView, 'month');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
            case 'quarter-day':

                for (var date = start; date <= finish; ) {
                    var sd = date.clone();
                    var q = getQuarter(sd.getMonth() + 1);    //获得本季度
                    //计算下一个季度(可能垮年
                    var year = sd.getFullYear();
                    var month = q * 3 + 1;    //下一个季度的第一个月
                    if (q + 1 == 5) {
                        year += 1;
                        month = 1;
                    }

                    var fd = new Date(year, month - 1, 1);    //下个季度的第一天

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: String.format(Edo.lists.Gantt.quarterformat2, year, q),
                        headerAlign: 'center'
                    };
                    columns.add(column);

                    column.header = this.columnRenderer(column, this.dateView, 'quarter');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
            case 'year-day':
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();
                    var fd = new Date(sd.getFullYear() + 1, 0, 1);    //下一年,1月1号

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.format(Edo.lists.Gantt.yearFormat),
                        headerAlign: 'center'
                    };
                    columns.add(column);

                    column.header = this.columnRenderer(column, this.dateView, 'year');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;

            case 'month-week':
                //将开始和结束日期,设置成一周的开始
                start = start.add(Date.DAY, -start.getDay() + this.weekStartDay);
                finish = finish.add(Date.DAY, -finish.getDay() + this.weekStartDay + 7);

                var preOffset = 0;

                var dayWidth = this.weekWidth / 7;
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();
                    //先找到下月第一天, 找到本月最后一天,定位这天的周末,将这些天赋值给本月
                    var d1 = new Date(sd.getFullYear(), sd.getMonth() + 1, 1);    //下个月的第一天
                    var d2 = d1.add(Date.DAY, -1);                              //本月最后一天
                    var fd = d2.add(Date.DAY, -d2.getDay() + this.weekStartDay + 7);   //本月最后一天的周末+1天

                    var offsetDate = ((fd - d2) / (10000 * 360 * 24) - 1);
                    var offsetWidth = offsetDate * dayWidth;

                    var column = {
                        enableSort: false,
                        //offsetWidth: -offsetWidth + preOffset,
                        offsetWidth: -offsetWidth,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.format(Edo.lists.Gantt.monthFormat2),
                        headerAlign: 'center'
                    };
                    columns.add(column);

                    column.header = this.columnRenderer(column, this.dateView, 'month');

                    preOffset = offsetWidth;

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
            case 'quarter-week':
                //将开始和结束日期,设置成一周的开始
                start = start.add(Date.DAY, -start.getDay() + this.weekStartDay);
                finish = finish.add(Date.DAY, -finish.getDay() + this.weekStartDay + 7);
                var dayWidth = this.weekWidth / 7;
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();
                    var q = getQuarter(sd.getMonth() + 1);    //获得本季度
                    //计算下一个季度(可能垮年
                    var year = sd.getFullYear();
                    var month = q * 3 + 1;    //下一个季度的第一个月
                    if (q + 1 == 5) {
                        year += 1;
                        month = 1;
                    }

                    var d1 = new Date(year, month - 1, 1);                        //下个季度的第一天
                    var d2 = d1.add(Date.DAY, -1);                              //本季度最后一天
                    var fd = d2.add(Date.DAY, -d2.getDay() + this.weekStartDay + 7);   //本季度最后一天的周末+1天


                    var offsetDate = ((fd - d2) / (10000 * 360 * 24) - 1);
                    var offsetWidth = offsetDate * dayWidth;

                    var column = {
                        enableSort: false,
                        date: date,
                        offsetWidth: -offsetWidth,
                        start: sd,
                        finish: fd,
                        header: String.format(Edo.lists.Gantt.quarterformat2, year, q),
                        headerAlign: 'center'
                    };
                    columns.add(column);

                    column.header = this.columnRenderer(column, this.dateView, 'quarter');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
            case 'year-week':
                //将开始和结束日期,设置成一周的开始
                start = start.add(Date.DAY, -start.getDay() + this.weekStartDay);
                finish = finish.add(Date.DAY, -finish.getDay() + this.weekStartDay + 7);
                var dayWidth = this.weekWidth / 7;
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();

                    var d1 = new Date(sd.getFullYear() + 1, 0, 1);    //下一年,1月1号                                
                    var d2 = d1.add(Date.DAY, -1);                              //本年最后一天
                    var fd = d2.add(Date.DAY, -d2.getDay() + this.weekStartDay + 7);   //本年最后一天的周末+1天

                    var offsetDate = ((fd - d2) / (10000 * 360 * 24) - 1);
                    var offsetWidth = offsetDate * dayWidth;

                    var column = {
                        enableSort: false,
                        date: date,
                        offsetWidth: -offsetWidth,
                        start: sd,
                        finish: fd,
                        header: date.format(Edo.lists.Gantt.yearFormat),
                        headerAlign: 'center'
                    };
                    columns.add(column);

                    column.header = this.columnRenderer(column, this.dateView, 'year');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
            case 'quarter-month':
                //将开始和结束日期,设置成一个月的开始
                start = start.add(Date.DAY, -start.getDate() + 1);
                finish = new Date(finish.getFullYear(), finish.getMonth() + 2, 1);
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();
                    var q = getQuarter(sd.getMonth() + 1);    //获得本季度
                    //计算下一个季度(可能垮年
                    var year = sd.getFullYear();
                    var month = q * 3 + 1;    //下一个季度的第一个月
                    if (q + 1 == 5) {
                        year += 1;
                        month = 1;
                    }

                    var fd = new Date(year, month - 1, 1);                        //下个季度的第一天
                    if (q == 4) year -= 1;

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: String.format(Edo.lists.Gantt.quarterformat2, year, q),
                        headerAlign: 'center'
                    };
                    columns.add(column);

                    column.header = this.columnRenderer(column, this.dateView, 'quarter');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
            case 'year-month':
                //将开始和结束日期,设置成一个月的开始
                start = start.add(Date.DAY, -start.getDate() + 1);
                finish = new Date(finish.getFullYear(), finish.getMonth() + 2, 1);
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();

                    var fd = new Date(sd.getFullYear() + 1, 0, 1);    //下一年,1月1号

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.format(Edo.lists.Gantt.yearFormat),
                        headerAlign: 'center'
                    };
                    columns.add(column);
                    column.header = this.columnRenderer(column, this.dateView, 'year');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
            case 'year-quarter':
                //将开始和结束日期,设置成一个季度的开始
                start = new Date(start.getFullYear(), getQuarter(start.getMonth() + 1) * 3 - 3, 1);
                finish = new Date(finish.getFullYear(), getQuarter(finish.getMonth() + 1) * 3 + 3, 1);
                if (start.getTime() == finish.getTime()) finish = finish.add(Date.MONTH, 3);
                for (var date = start; date <= finish; ) {
                    var sd = date.clone();

                    var fd = new Date(sd.getFullYear() + 1, 0, 1);    //下一年,1月1号

                    var column = {
                        enableSort: false,
                        date: date,
                        start: sd,
                        finish: fd,
                        header: date.format(Edo.lists.Gantt.yearFormat),
                        headerAlign: 'center'
                    };
                    columns.add(column);
                    column.header = this.columnRenderer(column, this.dateView, 'year');

                    date = fd;
                }
                columns[columns.length - 1].finish = finish;

                createBottomColumns(columns, bottom);
                break;
        }

        //alert(new Date() - sss);
        //debugger
        return columns;
    },


    _setFullView: function (value) {
        if (this.fullView != value) {
            this.fullView = value;

            this._setDateView('year-quarter');


            this.relayout('fullView');
        }
    },
    _setProgressLineVisible: function (value) {
        if (this.progressLineVisible != value) {
            this.progressLineVisible = value;

            this.changeProperty('progressLineVisible');
            this.relayout('progressLineVisible');
        }
    },
    _setHourWidth: function (value) {
        if (this.hourWidth != value) {
            this.hourWidth = value;
            this.dataViewChange = true;
            this.changeProperty('hourWidth');
            this.relayout('hourWidth');
        }
    },
    _setDayWidth: function (value) {
        if (this.dayWidth != value) {
            this.dayWidth = value;
            this.dataViewChange = true;
            this.changeProperty('dayWidth');
            this.relayout('dayWidth');
        }
    },
    _setWeekWidth: function (value) {
        if (this.weekWidth != value) {
            this.weekWidth = value;
            this.dataViewChange = true;
            this.changeProperty('weekWidth');
            this.relayout('weekWidth');
        }
    },
    _setMonthWidth: function (value) {
        if (this.monthWidth != value) {
            this.monthWidth = value;
            this.dataViewChange = true;
            this.changeProperty('monthWidth');
            this.relayout('monthWidth');
        }
    },
    _setQuarterWidth: function (value) {
        if (this.quarterWidth != value) {
            this.quarterWidth = value;
            this.dataViewChange = true;
            this.changeProperty('quarterWidth');
            this.relayout('quarterWidth');
        }
    },
    _setDateView: function (value) {
        if (this.dateView != value) {

            //切换日期
            //this[value.split('-')[1]+'Width'];

            this.dateView = value;
            this.changeProperty('dateview');
            this.dataViewChange = true;
            this.relayout('dateView');
        }
    },
    _setViewMode: function (value) {
        if (this.viewMode != value) {
            this.viewMode = value;
            this.changeProperty('viewMode');
            this.relayout('viewMode');
        }
    },
    _setStartDate: function (value) {
        if (!Edo.isDate(value)) return;
        if (this.startDate != value) {
            this.startDate = value;

            this.changeProperty('startDate');
            this.dataViewChange = true;
            this.relayout('startDate');
        }
    },
    _setFinishDate: function (value) {
        if (!Edo.isDate(value)) return;
        if (this.finishDate != value) {
            this.finishDate = value;

            this.changeProperty('finishDate');
            this.dataViewChange = true;
            this.relayout('finishDate');
        }
    },

    dataViewChange: true,
    createDataView: function () {

        this.dataViewChange = false;

        var value = this.dateView;

        var dayView = value.split('-')[1] == 'day';
        var dayCls = 'e-gantt-notdayview';

        this.columnWidth = this[value.split('-')[1] + 'Width'];

        var columns = this.createColumns(this.startDate, this.finishDate);

        Edo.lists.Gantt.superclass._setColumns.call(this, columns);

        if (dayView) {
            this.removeCls(dayCls);
        } else {
            this.addCls(dayCls);
        }


        //if(this.rendered) this.refresh.defer(100, this, [true]);
    },
    init: function () {
        this.set('plugins', [
            this.progressLineType,
            this.taskLineType,
            this.ganttDragDropType,
            this.ganttToolTipType,
            this.timeLineResize
        ]);

        Edo.lists.Gantt.superclass.init.apply(this, arguments);

        this.createTip();

        this.createDataView();
    },
    ///////////////定位相关计算
    //根据日期, 找出对应的列索引:可以优化一下,用半分查找法
    getColumnIndexByDate: function (date) {

        var columns = this.columns;

        var first = columns[0];
        var last = columns[columns.length - 1];

        if (date < first.date) return -100;


        //简单进行了一下2分查找
        var i = parseInt(columns.length / 2);
        var c = columns[i];
        if (date < c.date) {
            i = parseInt(i / 2);
            c = columns[i];
            if (date < c.date) {
                i = 0;
            } else {

            }
        } else {
            var i2 = i;
            i = i + parseInt((columns.length - i) / 2);
            c = columns[i];
            if (date < c.date) {
                i = i2;
            } else {

            }
        }

        for (var l = columns.length; i < l; i++) {
            var c = columns[i];

            if (c.start <= date && date <= c.finish) {
                break;
            }

            if (date < c.date) {
                i -= 1;
                break;
            }
        }
        //		if(columns.length == 1 && (!date >= c.start)) {
        //		    i = 0;
        //        }
        return i;
    },
    getCtEl: function () {
        return this.viewport;
    },
    getDateLeft: function (date, startColumnLeft) {
        //if(this.dateView == 'year-quarter') debugger       
        if (!date) return 0;

        //if(!this.taskLeftHash) this.taskLeftHash = {};

        var datetime = date.getTime();
        var left = this.taskLeftHash[datetime];
        if (left != undefined) {
            return left;
        }

        var t = this;
        var columns = t.columns;
        var colIndex = t.getColumnIndexByDate(date);

        if (colIndex == -100) {
            this.taskLeftHash[datetime] = -100;
            return -100;
        }

        var c = columns[colIndex];
        if (!c) c = columns[colIndex - 1];
        if (!c) return 0;

        if (!Edo.isValue(startColumnLeft)) startColumnLeft = t.startColumnLeft;

        left = (date - c.date) / t.widthGet + (colIndex * t.columnWidth - startColumnLeft);
        this.taskLeftHash[datetime] = left;
        return left;
    },
    //根据偏移, 获得日期
    getDateByOffset: function (offset) {
        //有多种日期视图

        //var hourWidth = this.columnWidth / 24;         //1小时为多少px		
        var hourWidth = this.getHourWidth();    //1小时为多少px		

        var columnView = this.getColumnDateView();
        if (columnView == 'minute') {
            return this.columns[this.startColumn].date.add(Date.MINUTE, (offset / hourWidth) * 60);
        } else {
            var hour = Math.round(offset / hourWidth);      //当前偏移一共是多少小时		
            //startColumn的日期, 加上偏移的小时
            return this.columns[this.startColumn].date.add(Date.HOUR, hour);
        }
    },
    scrollIntoTask: function (task, finish) {
        var rowIndex = task.__index;

        //this.viewRow(task);

        this.taskLeftHash = {};
        var left = this.getDateLeft(task[finish ? 'Finish' : 'Start'], 0);

        this.set('scrollLeft', left - this.viewWidth / 2);
        this.refresh(true);
    },
    columnChangedRefresh: false,
    initHeader: false,
    refresh: function (all) {
        this.markColumnsAndRows(this.scrollLeft, this.scrollTop, this.scrollLeft + this.viewWidth, this.scrollTop + this.viewHeight);

        this.startColumnLeft = this.startColumn * this.columnWidth;
        this.startRowTop = this.startRow * this.rowHeight;
        this.widthGet = this.createWidthGet();
        //清空上次,保存本次刷新的任务左侧偏移
        this.taskLeftHash = {};

        if (this.el) {
            this.viewport.innerHTML = this.createView();
        }

        if (all !== false || !this.initHeader) {
            this.refreshHeader();
            if (!this.initHeader) {
                this.syncBox.defer(1, this);
            }
            this.initHeader = true;
        }

        //渲染刷新事件
        this.fireEvent('refresh', {
            type: 'refresh',
            source: this
        });
    },
    syncSize: function () {
        if (this.dataViewChange) this.createDataView();
        Edo.lists.Gantt.superclass.syncSize.apply(this, arguments);
    },
    getColumnDateView: function () {
        var columnView = 'day';
        if (this.dateView) {
            columnView = this.dateView.split('-')[1];
        }
        return columnView;
    },
    createWidthGet: function () {
        var hourWidth = this.getHourWidth();
        var widthGet = 10000 * 360 / hourWidth;         //
        return widthGet;
    },
    getHourWidth: function () {
        var columnView = this.getColumnDateView();

        var hourWidth = this.columnWidth / 24;         //1小时为多少px
        if (columnView == 'minute') hourWidth = hourWidth * 24;
        if (columnView == 'week') hourWidth = hourWidth / 7;
        if (columnView == 'month') hourWidth = hourWidth / 30;
        if (columnView == 'quarter') hourWidth = hourWidth / 90;

        return hourWidth;
    },
    createTip: function () {
        if (!this.tip) {



            var sf = this;
            this.tip = Edo.managers.TipManager.reg({
                target: this,
                trackMouse: true,
                autoShow: true,
                autoHide: true,
                showTitle: false,
                //mouseOffset: [10, 10],
                mouseOffset: [0, 0],
                showDelay: 500,
                html: ''
                ,
                ontipshow: this.onTipShow.bind(this)
            });
        }
        return this.tip;
    },
    onTipShow: function (e) {
        var tip = this.tip;
        var ganttData = this.data;
        var ret;
        var linkinfo = e.target.linkinfo || e.target.getAttribute("linkinfo");
        if (Edo.util.Dom.findParent(e.target, 'e-gantt-item')
            || Edo.util.Dom.findParent(e.target, 'e-gantt-zeroduration')
        ) {
            Edo.managers.TipManager.clear(tip);
            tip.autoHide = true;
            var r = this.getRecordByEvent(e);
            if (!r) return;
            var rowB = this.getItemBox(r).bottom;
            e.xy[1] = rowB;
            var viewMode = 'gantt';
            if (Edo.util.Dom.hasClass(e.target, 'e-gantt-item-track')) viewMode = 'track';
            else if (Edo.util.Dom.hasClass(e.target, 'e-gantt-item-actual')) viewMode = 'actual';
            tip.html = this.taskTipRenderer(r, this, viewMode);
            ret = true;
        } else if (linkinfo) { //任务相关性连线
            Edo.managers.TipManager.clear(tip);
            var uids = linkinfo.split('$');
            var taskuid = uids[0], predecessoruid = uids[1];

            var task = ganttData.getTask(taskuid), preTask = ganttData.getTask(predecessoruid);
            if (!task || !preTask) return

            var link = ganttData.getPredecessorLink(task, preTask);

            e.xy[0] += 15;
            e.xy[1] += 15;
            tip.html = this.predecessorLinkTipRenderer(task, link, this, e);
            tip.autoHide = true;
            ret = true;
        } else {

            ret = false;
        }

        return ret;
    },
    getRecordByEvent: function (e) {
        var record = Edo.lists.Gantt.superclass.getRecordByEvent.call(this, e);
        if (!record) {
            record = this.data.getById(e.target.id);
        }
        return record;
    },
    isSummary: function (r) {
        return (r.Summary || r.IsSubproject) && !this.enableSummary;
    },
    minItemWidth: 0,
    itemHeight: 10, //13,
    simpleItem: true,
    itemOffset: 20,
    getItemHtml: function (r, index, sb, top, sdate, edate, isTrack, boxHash, psb, progressLines) {        //top
        var ret = !sb;
        if (!sb) sb = [];

        r.IsSubproject = parseInt(r.IsSubproject);

        var isSummary = this.isSummary(r);

        var t = this, widthGet = this.widthGet, viewW = this.viewWidth;

        var h = r.__height; 							//h							

        var rowid = t.createItemId(r);

        //var w = (r.Finish - r.Start) / widthGet; 	//w			

        var left = t.getDateLeft(r.Start);          //left
        var right = t.getDateLeft(r.Finish);          //left
        var w = right - left;

        var pw = r.PercentComplete / 100 * w;

        var itemString = '';
        if (t.itemRenderer) {
            itemString = t.itemRenderer(r, left, top, w, h, t);
        }

        //行开始
        var cls = '';
        if (r.IsSubproject) cls += ' e-gantt-subproject';
        else if (isSummary) cls += ' e-gantt-sumary';

        r.Critical ? cls += ' e-gantt-critical' : '';

        if (this.enableRowCls && r.__cls) cls += ' ' + r.__cls;

        sb[sb.length] = '<div id="'
        sb[sb.length] = rowid;
        sb[sb.length] = '" class="';
        sb[sb.length] = this.itemCls;
        sb[sb.length] = cls;

        if (t.selectHash[r.__id]) {
            sb[sb.length] = ' ' + t.selectedCls + '" style="height:';
        } else {
            sb[sb.length] = ' " style="height:';
        }
        sb[sb.length] = isBorderBox ? h + 1 : h - 1;
        sb[sb.length] = 'px;width:';
        sb[sb.length] = viewW;
        sb[sb.length] = 'px;top:';
        sb[sb.length] = top - 1;
        sb[sb.length] = 'px;">';


        var isHalf = isTrack || this.viewMode == 'actual';

        if (!isTrack && (!r.Start || !r.Finish || r.Start > edate || r.Finish < sdate || r.enableVisible == false || r.enableVisible == 'false')) {   //这里已经筛选出了符合日期范围的任务		    
            sb[sb.length] = '</div>';
        } else {
            //加入itemrender逻辑:显示条形图的时候			
            sb[sb.length] = itemString;
            if (r.Milestone) {            //里程碑
                sb[sb.length] = '<div class="e-gantt-zeroduration" style="top:6px;left:';
                sb[sb.length] = left - 6;
                sb[sb.length] = 'px;"></div>';
                w = 12;
            } else if (r.Duration === 0) {      //如果是0工时,则...
                sb[sb.length] = '<div class="e-gantt-zeroduration" style="top:6px;left:';
                sb[sb.length] = left - 6;
                sb[sb.length] = 'px;"></div>';
                w = 12;
            } else {
                //if (r.Name == 'task4') debugger
                var canDragItem = (t.enableDragItem && r.enableDragItem !== false) && !this.isSummary(r) && !r.Milestone && t.enableDragDrop;

                sb[sb.length] = '<div id="';
                sb[sb.length] = rowid;
                sb[sb.length] = '-v" class="e-gantt-item" style="height:';

                //var itemH = this.isSummary(r) ? 7 : h-12;
                var itemH = this.itemHeight;
                if (isHalf) itemH = itemH / 2;
                if (isSummary) itemH = 13;
                //if(this.isSummary(r)
                //if(r.Name == '起草初步的软件规范') debugger

                var rowH = itemH;
                if (rowH < this.itemHeight) {
                    rowH = isBorderBox ? rowH + 2 : rowH; //
                }

                sb[sb.length] = rowH;

                //如果是summary, 则优化ganttItem宽度
                var __left = left, __right = left + w;
                if (__left < 0) __left = 0 - this.itemOffset;
                if (__right > viewW) __right = viewW;

                sb[sb.length] = 'px;width:';
                var iw = __right - __left - 1;
                if (iw < this.minItemWidth) iw = this.minItemWidth;
                if (this.isSummary(r) && iw < 12) iw = 12;

                if (__right >= viewW && iw > 12) iw += this.itemOffset;

                sb[sb.length] = iw;
                sb[sb.length] = 'px;left:';
                sb[sb.length] = __left;
                sb[sb.length] = 'px;top:';
                sb[sb.length] = 4;
                sb[sb.length] = 'px;';
                if (canDragItem) sb[sb.length] = 'cursor:move;';
                sb[sb.length] = '">';

                //左右圆角
                if (!this.simpleItem || (isSummary && iw > 12)) {
                    sb[sb.length] = '<div class="e-gantt-item-outer"><div class="e-gantt-item-inner"></div></div>';
                }

                //task
                if (!isSummary) {
                    //完成百分比
                    if (t.percentCompleteRenderer) {
                        sb[sb.length] = t.percentCompleteRenderer(pw, r, t);
                    } else {
                        sb[sb.length] = '<div style="width:';
                        sb[sb.length] = pw + (left - __left);
                        sb[sb.length] = 'px;" class="e-gantt-percentcomplete"></div>';
                    }
                }
                sb[sb.length] = '</div>';

                //跟踪条形图,创建另一个比较基准条形图
                if (isTrack && r.Baseline && r.Baseline.length > 0) {  //如果是摘要,则不创建对比甘特图
                    var bl = r.Baseline[this.baselineIndex];

                    var _l = t.getDateLeft(bl.Start);
                    var _w = (bl.Finish - bl.Start) / widthGet;

                    sb[sb.length] = '<div class="e-gantt-item-track e-gantt-item" style="height:';
                    sb[sb.length] = 5;
                    sb[sb.length] = 'px;width:';
                    sb[sb.length] = _w - 1;
                    sb[sb.length] = 'px;left:';
                    sb[sb.length] = _l;
                    sb[sb.length] = 'px;top:';
                    sb[sb.length] = isSummary ? 5 + itemH / 2 : 5 + itemH;
                    sb[sb.length] = 'px;"></div>';
                }
                //实际日期条形图
                if (this.viewMode == 'actual' && !isSummary && r.ActualStart && r.ActualFinish) {  //如果是摘要,则不创建实际条形图

                    var _l = t.getDateLeft(r.ActualStart);
                    var _w = (r.ActualFinish - r.ActualStart) / widthGet;

                    sb[sb.length] = '<div class="e-gantt-item-actual e-gantt-item" style="height:';
                    sb[sb.length] = rowH;
                    sb[sb.length] = 'px;width:';
                    sb[sb.length] = _w - 1;
                    sb[sb.length] = 'px;left:';
                    sb[sb.length] = _l;
                    sb[sb.length] = 'px;top:';
                    sb[sb.length] = 5 + itemH;
                    sb[sb.length] = 'px;"></div>';
                }
            }

            if (t.taskNameVisible) {
                sb[sb.length] = '<div class="e-gantt-item-name" style="left:';
                sb[sb.length] = left + w + 5;
                sb[sb.length] = 'px;">';
                sb[sb.length] = t.taskNameRenderer ? t.taskNameRenderer(r, t) : r.Name;
                sb[sb.length] = '</div>';
            }

            if (t.collapseVisible && this.isSummary(r)) {
                var hasChildren = r.__hasChildren || r.__viewicon;
                var expanded = r.expanded;
                var treeCls = hasChildren ? (expanded == true ? t.expandCls : t.collapseCls) : '';

                sb[sb.length] = '<div class="' + treeCls + '" style="position:absolute;left:';
                sb[sb.length] = left - 20;
                sb[sb.length] = 'px;top:0px;"><div class="e-tree-nodeicon"></div></div>';
            }

            //行结尾
            sb[sb.length] = '</div>';

            //进度线
            if (t.progressLineVisible) {
                var left2 = r.PercentComplete / 100 * w + left - 5;
                var top2 = top + h / 2 - 5;

                psb[psb.length] = '<div class="e-gantt-progress-icon" style="left:';
                psb[psb.length] = left2;
                psb[psb.length] = 'px;top:';
                psb[psb.length] = top2;
                psb[psb.length] = 'px;"></div>';

                progressLines[progressLines.length] = [left2 + 5, top2 + 5];
            }
        }

        boxHash[r.__id] = [left, top, w, h, itemH];

        if (ret) return sb.join('');
    },
    getItemProxy: function (r, left, top, w, h, itemH, dayView, sbb) {
        var t = this;
        var viewW = this.viewWidth;
        //增加操作div:拖拽, 日期调节, 完成度调节
        var topplace = top + 7;
        var pw = r.PercentComplete / 100 * w;
        //1)用于拖拽整个ganttitem
        var canDragItem = (t.enableDragItem && r.enableDragItem !== false) && !this.isSummary(r) && !r.Milestone && t.enableDragDrop;

        var enableSyncDate = this.data.enableSyncDate;

        var __left = left, __right = left + w;
        if (__left < 0) __left = 0;
        if (__right > viewW) __right = viewW;

        //if(r.Name == '项目范围规划') debugger
        //        if(!this.isSummary(r) || (enableSyncDate && r.enableSyncDate !== false)){
        //        //if((enableSyncDate && r.enableSyncDate !== false)){
        //            sbb[sbb.length] = '<div id="';
        //            sbb[sbb.length] = r.__id;
        //            sbb[sbb.length] = '" class="e-gantt-item" style="z-index:110;background:none;border:none;height:';
        //            sbb[sbb.length] = itemH;
        //            sbb[sbb.length] = 'px;width:';
        //            sbb[sbb.length] = __right - __left;
        //            sbb[sbb.length] = 'px;left:';
        //            sbb[sbb.length] = __left;
        //            sbb[sbb.length] = 'px;top:';
        //            sbb[sbb.length] = topplace;
        //            sbb[sbb.length] = 'px;';
        //            sbb[sbb.length] = canDragItem ? 'cursor:move;' : '';
        //            sbb[sbb.length] = '"></div>';
        //        }

        //完成百分比调节器
        if (dayView && !this.isSummary(r) && !r.Milestone && (t.enableDragPercentComplete && r.enableDragPercentComplete !== false) && t.enableDragDrop) {
            sbb[sbb.length] = '<div id="' + r.__id + '" class="e-gantt-resize-percentcomplete" style="left:';
            sbb[sbb.length] = pw + left - 2;
            sbb[sbb.length] = 'px;top:';
            sbb[sbb.length] = topplace;
            sbb[sbb.length] = 'px;"></div>';
        }

        //开始,截止日期两个调节handler
        if (!this.isSummary(r) && !r.Milestone && t.enableDragDrop) {
            //left
            if (t.enableDragStart && r.enableDragStart !== false) {
                sbb[sbb.length] = '<div id="' + r.__id + '" class="e-gantt-rezie-l" style="left:';
                sbb[sbb.length] = left - 3;
                sbb[sbb.length] = 'px;top:';
                sbb[sbb.length] = topplace;
                sbb[sbb.length] = 'px;">&nbsp;</div>';
            }
            //right
            if (t.enableDragFinish && r.enableDragFinish !== false) {
                sbb[sbb.length] = '<div id="' + r.__id + '" class="e-gantt-rezie-r" style="left:';
                sbb[sbb.length] = left + w;
                sbb[sbb.length] = 'px;top:';
                sbb[sbb.length] = topplace;
                sbb[sbb.length] = 'px;">&nbsp;</div>';
            }
        }
    }
});

Edo.lists.Gantt.regType('gantt');
Edo.lists.Gantt.DateView = ['year-quarter', 'year-month', 'year-week', 'year-day', 'quarter-month', 'quarter-week', 'quarter-day', 'month-week', 'month-day', 'week-day', 'week-hour', 'day-hour', 'hour-minute'];



Edo.managers.SystemManager = {
    all: {},

    register: function (c) {
        if (!c.id) throw new Error("必须保证组件具备id");
        var o = Edo.managers.SystemManager.all[c.id];
        if (o) throw new Error("已存在id为:" + o.id + "的组件");

        Edo.managers.SystemManager.all[c.id] = c;
        if (c.id == 'window')
            if (window[c.id]) throw new Error("不能设置此ID:" + o.id);
        window[c.id] = c;
    },

    unregister: function (c) {
        delete Edo.managers.SystemManager.all[c.id];
        //if(c == WINDOW[c.id]) WINDOW[c.id] = null;//非常耗性能!
        //WINDOW[c.id] = null;
    },

    destroy: function (c) {
        if (c) {
            if (typeof c === 'string') c = Edo.managers.SystemManager.all[c];
            if (c) c.destroy();
        } else {
            var un = Edo.managers.SystemManager.unregister;
            var all = Edo.managers.SystemManager.all;
            //1)找出所有的topContainer顶级容器
            var tops = [];
            for (var id in all) {
                var o = all[id];
                if (!o.parent) tops.push(o);
            }
            var sss = new Date();
            for (var i = 0, l = tops.length; i < l; i++) {
                var o = tops[i];
                if (all[o.id]) {
                    o.destroy(true);
                }
            }
            //Binding.clearBind();                        
        }
    },

    get: function (id) {
        return Edo.managers.SystemManager.all[id];
    },

    getByName: function (name, parent) {
        return Edo.managers.SystemManager.getByProperty('name', name, parent);
    },

    getByProperty: function (name, value, parent) {  //
        var cs = [];
        var m = Edo.managers.SystemManager;
        var all = m.all;
        for (var id in all) {
            var o = all[id];
            if (o[name] == value) {
                if (!parent || (parent && m.isAncestor(parent, o))) {
                    cs.push(o);
                }
            }
        }
        return cs;
    },

    getType: function (type) {
        return Edo.types[type.toLowerCase()];
    },

    build: function (config) {
        if (!config) return;
        if (typeof config === 'string') {
            if (Edo.types[config.toLowerCase()]) {
                config = { type: config };
            }
            else {
                //config = styleHandler(config);//
                var swf = Edo.getParser();
                var o = swf.parserXML(config);
                if (!o.success) throw new Error(o.errorMsg);
                var config = o.json;
                if (!config.renderTo) config.renderTo = document.body;
            }
        }
        if (!config.constructor.superclass) {
            var cls = Edo.types[config.type.toLowerCase()];
            if (!cls) throw new Error('组件类未定义');
            var obj = new cls();
            if (obj.set) {
                obj.set(Edo.apply({}, config)); //复制配置对象, 防止配置对象被删除属性, 从而是配置对象的拷贝失效,出问题
            }
            return obj;
        }
        return config;
    },
    //判断p是否是c的父元素    
    isAncestor: function (p, c) {
        if (p === c) return true;
        var ret = false;
        while (c = c.parent) {
            ret = c == p;
            if (ret) break;
        }
        return ret;
    }
};
Edo.regCmp = Edo.managers.SystemManager.register;
Edo.unregCmp = Edo.managers.SystemManager.unregister;
Edo.get = Edo.getCmp = Edo.managers.SystemManager.get;
Edo.build = Edo.create = Edo.managers.SystemManager.build;
Edo.getType = Edo.managers.SystemManager.getType;
Edo.getByName = Edo.managers.SystemManager.getByName;
Edo.getByProperty = Edo.managers.SystemManager.getByProperty;
Edo.isAncestor = Edo.managers.SystemManager.isAncestor;


Edo.managers.DragManager = {
    drops: {},

    event: null,
    dragObject: null,
    dropObject: null,       //当enableDrop=true时,此属性可能为有值
    dragData: null,
    alpha: .5,


    isDragging: false,

    enableFeedback: false,
    feedback: 'none',

    drag: null,


    startDrag: function (cfg) {
        //1)proxy:    true:自身的拷贝,false:自身,dom:拖拽dom元素
        //2)enableDrop:       是否投放
        //dragObject:   UIComplete:启动拖拽的组件,dom
        //dragData:               拖拽数据
        //event:               当前鼠标事件对象            
        //xOffset:              //不传值,则自动计算
        //yOffset:
        //alpha:                    拖拽代理的透明度(默认0.5)
        //feedback:                 表明此次拖拽的操作效果:copy,move,none   ?

        if (!cfg.dragObject) throw new Error("必须有拖拽启动对象");
        if (!cfg.event) throw new Error("必须有拖拽初始事件对象");

        if (cfg.event.button != 0) return false;

        if (this.isDragging) {
            this.drag.stop(this.event);
        }

        this.enableDrop = this.proxy = this.now = this.drag = this.dropObject = this.dragObject = this.dragData = this.event = this.xOffset = this.yOffset = null;

        drag = new Edo.util.Drag({
            delay: this.delay,
            capture: this.capture,
            onStart: this.onStart.bind(this),
            onMove: this.onMove.bind(this),
            onStop: this.onStop.bind(this)
        });
        this.drag = drag;

        Edo.apply(this, cfg, {
            delay: 80,
            capture: false,
            ondragstart: Edo.emptyFn,
            ondragmove: Edo.emptyFn,
            ondragcomplete: Edo.emptyFn,

            ondropenter: Edo.emptyFn,
            ondropover: Edo.emptyFn,
            ondropout: Edo.emptyFn,
            ondropmove: Edo.emptyFn,
            ondragdrop: Edo.emptyFn
        });

        this.initEvent = this.event;


        drag.start(this.event);

    },

    acceptDragDrop: function () {
        this.canDrop = true;
    },

    rejectDragDrop: function () {
        this.canDrop = false;
    },

    regDrop: function (cmp) {
        this.drops[cmp.id] = cmp;
    },

    unregDrop: function (cmp) {
        delete this.drops[cmp.id];
    },
    //private
    fire: function (e, o) {
        var dm = Edo.managers.DragManager;
        //计算出elxy
        dm.xy = [dm.now[0] + dm.xOffset, dm.now[1] + dm.yOffset];

        Edo.apply(e, dm);
        o = o || dm.dragObject;

        if (o.el) {
            o.fireEvent(e.type, e);
        } else {
            Edo.util.Dom.fireEvent(o, e.type, e);
        }
        e['on' + e.type].call(dm, e);

        //status = e.xy;
        //dm.now = e.now;//修改坐标的关键
        dm.now = [e.xy[0] - dm.xOffset, e.xy[1] - dm.yOffset];

        //status = dm.xy;
    },
    findDrop: function (e, t) {

        //top.document.title = this.dropObject+":"+new Date().getTime();
        t = t || e.target;
        while (t && t !== document) {
            //这里可以优化:将所有drop保存一个idHash,就不需要for,而直接判断是否有了.
            for (var id in this.drops) {
                var drop = this.drops[id];
                var el = drop.el || drop;
                if (el === t) {
                    return drop;
                }
            }
            t = t.parentNode;
        }
    },
    onStart: function (drag) {

        this.event = drag.event;
        this.now = drag.now;

        this.el = this.dragObject.el || this.dragObject;
        var xy = Edo.util.Dom.getXY(this.el);

        this.initXY = xy;

        if (!this.xOffset && this.xOffset !== 0) this.xOffset = xy[0] - drag.now[0];
        if (!this.yOffset && this.yOffset !== 0) this.yOffset = xy[1] - drag.now[1];

        this.isDragging = true;
        this.fire({
            type: 'dragstart',
            source: this
        });

        if (this.proxy === true) {
            this.proxy = this.el.cloneNode(false);
            //this.proxy.className = 'e-dragdrop-proxy';

            Edo.util.Dom.setStyle(this.proxy, this.proxyStyle);
            Edo.util.Dom.addClass(this.proxy, this.proxyCls);

            document.body.appendChild(this.proxy);

            var size = Edo.util.Dom.getSize(this.el);
            Edo.util.Dom.setSize(this.proxy, size.width, size.height);

            this.removeProxy = true;
        } else if (!this.proxy) {
            this.proxy = this.el;
        }


        this._zIndex = Edo.util.Dom.getStyle(this.proxy, 'z-index');
        if (this.proxy._zIndex) {
            this._zIndex = this.proxy._zIndex;
        }
        this.proxy._zIndex = this._zIndex;

        this._opacity = 1; //Edo.util.Dom.getOpacity(this.proxy);          
        Edo.util.Dom.setStyle(this.proxy, 'z-index', 9999999);
        Edo.util.Dom.setOpacity(this.proxy, this.alpha);

        var position = Edo.util.Dom.getStyle(this.proxy, 'position');
        if (position != 'relative' && position != 'absolute') {
            this.proxy.style.position = 'relative';
        }

        Edo.util.Dom.setStyle(this.proxy, this.dragStyle);

        Edo.util.Dom.setXY(this.proxy, xy);

        this.leftTop = [parseInt(Edo.util.Dom.getStyle(this.proxy, 'left')) || 0, parseInt(Edo.util.Dom.getStyle(this.proxy, 'top')) || 0];

        //document.title= new Date();
    },
    onMove: function (drag) {

        if (!this.isDragging) return;
        this.event = drag.event;
        this.now = drag.now;
        this.move = true;

        var el = this.event.target;

        var proxyBox = Edo.util.Dom.getBox(this.proxy);
        if (this.proxy != this.el && Edo.util.Dom.isInRegin(this.now, proxyBox)) {
            var display = this.proxy.style.display;
            this.proxy.style.display = 'none';
            //隐藏当前显示的Tips

            var scroll = Edo.util.Dom.getScroll();
            el = document.elementFromPoint(this.now[0], this.now[1] - scroll.top);
            while (el && el != document) {
                if (el.tagName) break;
                el = el.parentNode;
            }
            if (el) {
                this.event.target = el;
            }
            this.proxy.style.display = display;
        }
        //this.canDrop = false;
        //先激发dragmove事件,监听dragmove,修改drag.now,则可以改变拖拽的坐标    
        this.fire({
            type: 'dragmove',
            source: this
        });

        //Edo.util.Dom.setXY(this.proxy, [drag.now[0] + this.xOffset, drag.now[1] + this.yOffset ]);
        if (this.move !== false && this.proxy) {
            var s = this.proxy.style;
            s.left = (this.now[0] + this.xOffset - this.initXY[0] + this.leftTop[0]) + 'px';
            s.top = (this.now[1] + this.yOffset - this.initXY[1] + this.leftTop[1]) + 'px';
        }

        if (this.enableDrop) {

            var now = this.dropObject;          //保存当前的drop

            this.dropObject = null;

            //这个循环是必要的!不要轻易去掉                        
            while (el && el !== document) {

                var drop = this.findDrop.call(this, this.event, el);
                if (!drop) {                     //没找到新drop,则退出
                    if (now) {
                        this.dropObject = now;
                        this.fire({
                            type: 'dropout',
                            source: this.dropObject
                        }, this.dropObject);
                    }
                    this.dropObject = null;
                    break;
                }

                this.dropObject = drop; //将新drop赋予dropObject,并测试,如果通过,则break                        

                if (drop == now) break;      //如果原来的drop和新的drop是同一个,则不需要进行置换
                //如果这个等式成立,那么drop原先已经经过dropenter判断过了.


                this.fire({
                    type: 'dropenter',
                    source: this.dropObject
                }, this.dropObject);

                if (this.canDrop) {
                    if (now) {
                        this.dropObject = now;
                        this.fire({                          //激发旧drop的out
                            type: 'dropout',
                            source: this.dropObject
                        }, this.dropObject);
                    }
                    this.dropObject = drop;
                    this.fire({                          //激发新drop的over
                        type: 'dropover',
                        source: this.dropObject
                    }, this.dropObject);
                    break;
                } else {
                    el = (drop.el || drop).parentNode;  //没通过,继续循环
                }
            }
        }

        //dropmove,当有drop对象时激发
        if (this.dropObject) {
            this.fire({
                type: 'dropmove',
                source: this.dropObject
            }, this.dropObject);
        }
    },
    onStop: function (drag) {
        if (!this.isDragging) return;
        this.event = drag.event;
        this.now = drag.now;

        this.onMove(drag);

        if (this.canDrop && this.dropObject) {
            this.fire({
                type: 'dropout',
                source: this.dropObject
            }, this.dropObject);

            this.fire({
                type: 'dragdrop',
                source: this.dropObject
            }, this.dropObject);
        }

        var xy = [this.now[0] + this.xOffset, this.now[1] + this.yOffset];

        if (this.autoDragDrop) {
            if (this.dragObject.set) {
                this.dragObject.set('XY', xy);
            } else {
                Edo.util.Dom.setXY(this.dragObject, xy);
            }
        }

        this.fire({
            type: 'dragcomplete',
            source: this
        });

        this.isDragging = false;
        this.alpha = .5;
        //this.now = this.drag = this.dropObject = this.dragObject = this.dragData = this.event = this.xOffset = this.yOffset = null;
        this.xy = this.enableDrop = this.now = this.drag = this.dropObject = this.dragObject = this.dragData = this.event = this.xOffset = this.yOffset = null;
        //this.proxy.style.position = this._position;


        Edo.util.Dom.setStyle(this.proxy, 'z-index', this._zIndex);
        Edo.util.Dom.setOpacity(this.proxy, this._opacity);
        if (this.el !== this.proxy && this.removeProxy) {
            Edo.util.Dom.remove(this.proxy);
            this.removeProxy = false;
        }
        this.proxy = null;
    }

};

Edo.managers.DragProxy = function (cfg) {
    Edo.apply(this, cfg, {
        feedback: 'no',   //copy,move,no,yes,add,none,between,preend,append
        html: ' '
    });
};
Edo.managers.DragProxy.prototype = {
    setFeedback: function (feedback) {
        if (this.feedback != feedback) {
            Edo.util.Dom.removeClass(this.el, this.getFeedbackCls(this.feedback));
            this.feedback = feedback;
            Edo.util.Dom.addClass(this.el, this.getFeedbackCls(this.feedback));
        }
    },
    setHtml: function (html) {
        this.innerEl.innerHTML = html;
    },
    getFeedbackCls: function (value) {
        return 'e-dragproxy-' + value;
    },
    render: function (p) {
        this.el = Edo.util.Dom.append(p || document.body,
                '<div class="e-dragproxy ' + this.getFeedbackCls(this.feedback) + '"><div class="feedback"></div><div class="inner">' + this.html + '</div></div>');
        this.feedbackEl = this.el.firstChild;
        this.innerEl = this.el.lastChild;

        if (this.shadow) Edo.util.Dom.addClass(this.el, 'e-shadow');

        var size = Edo.util.Dom.getSize(this.el);
        if (size.width < 25) Edo.util.Dom.setWidth(this.el, 25);
        if (size.height < 24) Edo.util.Dom.setHeight(this.el, 24);
        return this;
    },
    destroy: function () {
        Edo.util.Dom.remove(this.el);
        this.el = this.feedbackEl = this.innerEl = null;
    }
}

Edo.managers.DragProxy.getUpDownProxy = function () {
    if (!this.up) {
        this.up = Edo.util.Dom.append(document.body, '<div class="e-dragproxy-up"></div>');
    }
    if (!this.down) {
        this.down = Edo.util.Dom.append(document.body, '<div class="e-dragproxy-down"></div>');
    }
    this.up.style.visibility = 'visible';
    this.down.style.visibility = 'visible';

    return [this.up, this.down];
}
Edo.managers.DragProxy.hideUpDownProxy = function () {
    if (this.up) {
        this.up.style.visibility = 'hidden';

    }
    if (this.down) {

        this.down.style.visibility = 'hidden';
    }
}
Edo.managers.DragProxy.clearUpDownProxy = function () {
    if (this.up) {
        Edo.util.Dom.remove(this.up);
        this.up = null;
    }
    if (this.down) {
        Edo.util.Dom.remove(this.down);
        this.down = null;
    }
}

Edo.managers.DragProxy.getInsertProxy = function (direction) {
    if (!this.insert) {
        this.insert = Edo.util.Dom.append(document.body, '<div></div>');
    }
    this.insert.className = 'e-dragproxy-insert' + (direction || '');
    this.insert.style.visibility = 'visible';
    return this.insert;
}
Edo.managers.DragProxy.hideInsertProxy = function () {
    if (this.insert) {
        this.insert.style.visibility = 'hidden';

    }
}


Edo.managers.PopupManager = {
    zindex: 9100,
    popups: {},

    createPopup: function (cfg) {
        var cmp = cfg.target;
        if (!cmp) return false;
        var bd = Edo.getBody();

        Edo.applyIf(cfg, {
            x: 'center',
            y: 'middle',
            //shadow: true,
            modal: false,
            modalCt: bd,
            onout: Edo.emptyFn,
            onin: Edo.emptyFn,
            onmousedown: Edo.emptyFn
        });

        var x = cfg.x, y = cfg.y, modalCt = cfg.modalCt;

        if (Edo.isValue(cfg.width)) cmp._setWidth(cfg.width);
        if (Edo.isValue(cfg.height)) cmp._setHeight(cfg.height);

        var zIndex = this.zindex++;
        cmp._setStyle('z-index:' + zIndex + ';position:absolute;');
        cfg.zIndex = zIndex;

        var boxCt = Edo.util.Dom.getBox(modalCt);
        var sizeCt = Edo.util.Dom.getViewSize(document);
        boxCt.width = sizeCt.width;
        boxCt.height = sizeCt.height;

        if (!cmp.layouted) {
            cmp.doLayout();
        }
        var boxCmp = cmp._getBox();

        if ((!x && x !== 0) || x == 'center') {
            x = (boxCt.x + boxCt.width / 2) - boxCmp.width / 2
        }
        if ((!y && y !== 0) || y == 'middle') {
            y = (boxCt.y + boxCt.height / 2) - boxCmp.height / 2
        }
        //调节下坐标定位(如果是边缘等)
        cmp.set('visible', true);
        cmp._setXY([x, y]);

        cmp.left = parseInt(cmp.el.style.left);
        cmp.top = parseInt(cmp.el.style.top);

        if (isOpera) cmp._setXY(x, y);

        if (cfg.modal) {
            Edo.util.Dom.mask(modalCt);
            if (modalCt._mask) modalCt._mask.style.zIndex = zIndex - 1;
        } else {
            this.unmask(cmp, modalCt);
        }

        //cmp._setStyle('z-index:'+(parseInt(modalCt._mask.style.zIndex)+1)+';');

        this.popups[cmp.id] = cfg;

        if (cfg.focus) {
            cmp.focus.defer(30, cmp);
        }

        //status = cmp.id +":"+new Date();  

        setTimeout(function () {
            Edo.util.Dom.repaint(cmp.el);
        }, 10);

        //对tab键的特殊控制处理
    },

    removePopup: function (cmp) {
        var o = this.popups[cmp.id];
        if (!o) return;

        cmp._setX(-3000);
        cmp.blur();
        //cmp.set('visible', false);

        //cmp.set.defer(1, cmp, ['visible', false]);
        if (o.modal) {
            //遍历popups,判断是否有一样的modalCt,如果有,则不unmask
            //unmask.defer(100, null, [cmp, cmp.modalCt]);
            this.unmask(cmp, o.modalCt);
        }
        delete cmp.modalCt;
        delete this.popups[cmp.id];
    },
    unmask: function (cmp, mct) {
        var repeat = false;
        var zindex = -1;
        for (var id in this.popups) {
            if (id == cmp.id) continue;
            var pop = this.popups[id];
            if (pop.modalCt === mct && pop.modal) {
                repeat = true;
                zindex = pop.zIndex;
                break;
            }
        }
        if (repeat) {
            if (mct._mask) mct._mask.style.zIndex = zindex - 1;
        }
        else Edo.util.Dom.unmask(mct);
    }
};
Edo.util.Dom.on(document, 'mousedown', function (e) {
    var popups = Edo.managers.PopupManager.popups;

    for (var id in popups) {
        var popup = popups[id];
        popup.onmousedown(e);
        if (!popup.target.within(e)) {
            popup.onout(e);
        } else {
            popup.onin(e);
        }
    }
});

Edo.managers.ResizeManager = {
    all: {},
    overlay: null,

    startResize: function (cfg) {
        //event:事件对象
        //handler:方向
        //handlerEl:方向元素
        //minWidth,minHeight
        //target:   目标对象

        Edo.apply(this, cfg, {
            minWidth: 10,
            minHeight: 10,
            onresizestart: Edo.emptyFn,
            onresize: Edo.emptyFn,
            onresizecomplete: Edo.emptyFn
        });
        var drag = new Edo.util.Drag({
            onStart: this.onStart.bind(this),
            onMove: this.onMove.bind(this),
            onStop: this.onStop.bind(this)
        });

        //初始化拖拽需要的一些坐标属性    
        var el = this.target.el || this.target;
        this.initXY = this.event.xy;                     //鼠标的初始点击位置
        this.handlerBox = Edo.util.Dom.getBox(this.handlerEl);        //目标handler的初始位置
        this.box = this.box || Edo.util.Dom.getBox(el);

        drag.start(this.event);

    },

    reg: function (o) {
        //target: 目标组件对象
        //transparent:是否透明
        //handlers: [e东,s南,w西,n北, es东南, ws西南, sn西北, en东北]
        //mode: 'in','out','hide'   //在内显示,在外显示,隐藏等...默认在内不显示

        var el = o.target.el || o.target;
        var id = el.id;
        if (!id) throw new Error('必须指定id');
        if (this.all[id]) this.unreg(o);
        this.all[id] = o;
        o.minWidth = o.minWidth || o.target.minWidth || 10;
        o.minHeight = o.minHeight || o.target.minHeight || 10;

        o.els = {};
        if (!o.handlers) o.handlers = ['se'];
        for (var i = 0; i < o.handlers.length; i++) {
            var h = o.handlers[i];
            var s = '<div direction="' + h + '" class="e-resizer e-resizer-' + h + '"></div>';
            var d = Edo.util.Dom.append(el, s);
            d.direction = h;
            if (o.resizable !== false) {
                Edo.util.Dom.on(d, 'mousedown', this.onMouseDown, o);
            }
            o.els[h] = d;
        }

        if (!o.transparent) Edo.util.Dom.addClass(el, 'e-resizer-over');
        if (o.cls) Edo.util.Dom.addClass(el, o.cls);
        if (o.square) Edo.util.Dom.addClass(el, 'e-resizer-square');

    },

    unreg: function (id) {
        id = id.id || id;
        var o = this.all[id];
        if (o) {
            for (var h in o.els) {
                var el = o.els[h];
                Edo.util.Dom.clearEvent(el);
                Edo.util.Dom.removeClass(el, 'e-resizer-over');
                Edo.util.Dom.removeClass(el, 'e-resizer-square');
                Edo.util.Dom.remove(el);
            }
            delete this.all[o.id];
        }
    },
    //private
    fire: function (e, o) {

        this.size = this.box = this.getResizeBox(this.event);

        e = Edo.apply(e, this);
        o = o || this.target;

        if (o.el) {
            o.fireEvent(e.type, e);
        } else {
            Edo.util.Dom.fireEvent(o, e.type, e);
        }
        e['on' + e.type].call(this, e);
    },
    getOverlay: function (cursor) {
        var bd = document;
        if (!this.overlay) {
            this.overlay = Edo.util.Dom.append(bd.body, '<div class="e-resizer-overlay"></div>');
            Edo.util.Dom.selectable(this.overlay, false);
        }
        Edo.util.Dom.setStyle(this.overlay, "cursor", cursor);
        Edo.util.Dom.setSize(this.overlay, Edo.util.Dom.getScrollWidth(bd), Edo.util.Dom.getScrollHeight(bd));
        bd.body.appendChild(this.overlay);
    },
    getResizeBox: function (e) {
        //拖拽的时候,要注意,不能小于minWidth,minHeight
        var xy = e.xy;

        this.offsetX = this.handlerBox.right - this.initXY[0];
        this.offsetY = this.handlerBox.bottom - this.initXY[1]; //右下
        this.offsetX2 = this.initXY[0] - this.handlerBox.x;     //左上
        this.offsetY2 = this.initXY[1] - this.handlerBox.y;

        var px = xy[0] + this.offsetX, py = xy[1] + this.offsetY; //当前x,y鼠标位置,加入了偏移   
        var px2 = xy[0] - this.offsetX, py2 = xy[1] - this.offsetY; //当前x,y鼠标位置,加入了偏移   


        var x = this.box.x, y = this.box.y, w = this.box.width, h = this.box.height, right = this.box.right, bottom = this.box.bottom;

        var mw = this.minWidth || 0, mh = this.minHeight || 0;
        switch (this.handler) {
            case "e":
                w = px - x;
                w = Math.max(mw, w);
                break;
            case "s":
                h = py - y;
                h = Math.max(mh, h);
                break;
            case "se":
                w = px - x;
                w = Math.max(mw, w);
                h = py - y;
                h = Math.max(mh, h);
                break;
            case "n":
                if (bottom - py2 < mh) {
                    y = bottom - mh;
                } else {
                    y = py2;
                }
                h = bottom - y;
                break;
            case "w":
                if (right - px2 < mw) {
                    x = right - mw;
                } else {
                    x = px2;
                }
                w = right - x;
                break;
            case "nw":
                if (bottom - py2 < mh) {
                    y = bottom - mh;
                } else {
                    y = py2;
                }
                h = bottom - y;
                if (right - px2 < mw) {
                    x = right - mw;
                } else {
                    x = px2;
                }
                w = right - x;
                break;
            case "ne":
                if (bottom - py2 < mh) {
                    y = bottom - mh;
                } else {
                    y = py2;
                }
                h = bottom - y;
                w = px - x;
                w = Math.max(mw, w);
                break;
            case "sw":
                if (right - px2 < mw) {
                    x = right - mw;
                } else {
                    x = px2;
                }
                w = right - x;
                h = py - y;
                h = Math.max(mh, h);
                break;
        }
        return { x: x, y: y, width: w, height: h, right: x + w, bottom: y + h }
    },
    onStart: function (drag) {
        this.event = drag.event;
        var t = this.handlerEl;

        if (this.autoProxy !== false) {
            this.proxy = Edo.util.Dom.append(document.body, '<div class="e-resizer-proxy"></div>');
            Edo.util.Dom.setBox(this.proxy, this.box);
        }

        this.getOverlay(Edo.util.Dom.getStyle(t, 'cursor'));


        this.fire({
            type: 'resizestart',
            target: this
        });
    },
    onMove: function (drag) {
        this.event = drag.event;
        //        fire({
        //            type: 'resize',
        //            target: this
        //        });
        if (this.autoProxy !== false) {
            var box = this.getResizeBox(this.event);
            Edo.util.Dom.setBox(this.proxy, box);
        }
        this.fire({
            type: 'resize',
            target: this
        });
    },
    onStop: function (drag) {
        this.event = drag.event;

        var size = this.getResizeBox(this.event);

        if (this.autoResize !== false) {
            if (this.target.el) {
                this.target.set('size', size);
            } else {
                Edo.util.Dom.setSize(this.target, size.width, size.height);
            }
        }
        Edo.util.Dom.remove(this.overlay);
        if (this.autoProxy !== false) {
            Edo.util.Dom.remove(this.proxy);
        }

        this.fire({
            type: 'resizecomplete',
            target: this
        });
        this.autoResize = this.handler = this.handlerEl = this.proxy = this.initXY = this.handlerBox = this.box = this.autoProxy = null;
    },
    onMouseDown: function (e) {
        if (e.button != 0) return false;

        var t = e.target;

        //得到目标DOM元素
        var el = this.target.el || this.target;
        //得到拖拽方向
        var d = t.direction;

        if (d && t.parentNode === el) {
            //            Edo.managers.ResizeManager.startResize({
            //                target: this.target,
            //                event: e,
            //                handler: d,
            //                handlerEl: t,
            //                minWidth: this.minWidth,
            //                minHeight: this.minHeight
            //            });
            Edo.managers.ResizeManager.startResize(Edo.applyIf({
                target: this.target,
                event: e,
                handler: d,
                handlerEl: t,
                minWidth: this.minWidth,
                minHeight: this.minHeight
            }, this));
            e.stop();
        }
    }
};

Edo.managers.TipManager = {
    tips: {},
    //tpl: new Edo.util.Template('<table class="e-tip <%=this.cls%>" cellspacing="0" border="0" cellpadding="0"><tr class="e-group-t"><td class="e-group-tl"></td><td class="e-group-tc"></td><td class="e-group-tr"></td></tr><tr class="e-group-m"><td class="e-group-ml"></td><td class="e-group-mc"><%if(this.showTitle){%><div class="e-tip-header"><%= this.title%><%if(this.showClose){%><div class="e-tip-close" onclick="Edo.managers.TipManager.hide(\'<%= this.target.id%>\')"></div><%}%></div><%}%><div class="e-group-body"><%= this.html%></div></td><td class="e-group-mr"></td></tr><tr class="e-group-b"><td class="e-group-bl"></td><td class="e-group-bc"></td><td class="e-group-br"></td></tr></table>'),
    tpl: new Edo.util.Template('<div class="e-tip <%=this.cls%>"><%if(this.showTitle){%><div class="e-tip-header"><%= this.title%><%if(this.showClose){%><div class="e-tip-close" onclick="Edo.managers.TipManager.hide(\'<%= this.target.id%>\')"></div><%}%></div><%}%><div class="e-group-body"><%= this.html%></div></div>'),
    show: function (x, y, cfg) {
        this.hide(cfg);
        var target = cfg.target;
        //clearTimeout(cfg.hideTimer);
        if (!cfg.tipEl) {

            var s = this.tpl.run(cfg);

            cfg.tipEl = Edo.util.Dom.append(document.body, s);

            //            var w = Edo.util.Dom.getWidth(cfg.tipEl);
            //            Edo.util.Dom.setWidth(cfg.tipEl, w);
            //Edo.util.Dom.repaint(cfg.tipEl);
        }

        var x = x + cfg.mouseOffset[0], y = y + cfg.mouseOffset[1];
        var size = Edo.util.Dom.getSize(cfg.tipEl);

        var w = Edo.util.Dom.getViewWidth(document);
        var h = Edo.util.Dom.getViewHeight(document);
        if (x + size.width > w) {
            x = w - size.width;
        }
        if (y + size.height > h) {
            y = h - size.height;
        }
        Edo.util.Dom.setXY(cfg.tipEl, [x, y]);
    },
    hide: function (cfg) {

        if (!cfg) return;
        if (typeof cfg === 'string') cfg = this.tips[cfg];
        clearTimeout(cfg.showTimer);
        Edo.util.Dom.remove(cfg.tipEl);
        cfg.tipEl = null;
    },
    clear: function (tip) {
        if (tip.tipEl) {
            Edo.util.Dom.remove(tip.tipEl);
            tip.tipEl = null;
        }
        return tip;
    },

    reg: function (cfg) {
        cfg = Edo.apply({}, cfg, {
            target: null,               //注册提示的对象
            cls: '',                    //
            html: '',
            title: '',

            ontipshow: Edo.emptyFn,
            ontiphide: Edo.emptyFn,

            showTitle: false,           //是否显示标题
            autoShow: true,             //是否mouseover显示
            autoHide: true,             //是否mouseout隐藏
            showClose: false,           //是否显示关闭按钮
            showImage: false,           //
            trackMouse: false,          //是否跟随鼠标移动而显示
            showDelay: 100,             //显示延迟
            hideDelay: 200,             //隐藏延迟            

            mouseOffset: [15, 18]         //显示的偏移坐标
        });

        if (!cfg.target) return false;

        //注册提示配置信息
        this.unreg(cfg.target);

        var target = cfg.target;
        this.tips[target.id] = cfg;

        //if(target.id == 'btn2') debugger;
        if (cfg.autoShow) {
            if (cfg.trackMouse) {
                target.on('mousemove', this.onmousemove, this);
            } else {
                target.on('mouseover', this.onmouseover, this);
            }
        }
        //如果是autoHide,则绑定上去,要不不绑定
        target.on('mouseout', this.onmouseout, this);
        return cfg;
    },

    unreg: function (cmp) {
        //取消提示
        var tip = this.tips[cmp.id];
        if (tip) {

            this.hide(tip);
            var target = tip.target;
            target.un('mouseover', this.onmouseover, this);
            target.un('mousemove', this.onmousemove, this);
            target.un('mouseout', this.onmouseout, this);

            delete this.tips[cmp.id];
        }
    },
    //private
    onmouseover: function (e) {
        var tip = this.tips[e.source.id];
        if (tip.tipEl) {
            var el = e.getRelatedTarget();
            if (Edo.util.Dom.contains(e.source.el, el)) return;
        }
        if (tip.ontipshow(e) !== false) {
            tip.showTimer = this.show.defer(tip.showDelay, this, [e.xy[0], e.xy[1], tip]);
        } else {
            //if(tip.autoHide) tip.hideTimer = this.hide.defer(tip.hideDelay, this, [tip]);
            //if (tip.autoHide) this.hide(tip);
            this.hide(tip);
        }
    },
    onmousemove: function (e) {
        var tip = this.tips[e.source.id];
        //document.title = new Date();
        clearTimeout(tip.showTimer);
        if (tip.ontipshow(e) !== false) {
            //this.show(e.xy[0], e.xy[1], tip);
            tip.showTimer = this.show.defer(tip.showDelay, this, [e.xy[0], e.xy[1], tip]);
        } else {
            //if(tip.autoHide) tip.hideTimer = this.hide.defer(tip.hideDelay, this, [tip]);

            if (tip.autoHide) this.hide(tip);
        }
    },
    onmouseout: function (e) {
        var el = e.getRelatedTarget();
        var tip = this.tips[e.source.id];
        if (Edo.util.Dom.contains(e.source.el, el)) return;
        //alert(1);
        if (tip.autoHide) {
            tip.ontiphide(e);
            if (tip.showTimer) {
                clearTimeout(tip.showTimer);
                tip.showTimer = null;
            }

            //tip.hideTimer = this.hide.defer(tip.hideDelay, this, [tip]);
            if (tip.autoHide) this.hide(tip);

        }
    }
}


Edo.data.DataModel = function () {
    Edo.data.DataModel.superclass.constructor.call(this);

    this.fields = [];
    this.fieldsMap = {};



}
Edo.data.DataModel.extend(Edo.core.Component, {

    _setFields: function (fields) {
        if (!fields) fields = [];
        this.fieldsMap = {};
        for (var i = 0, l = fields.length; i < l; i++) {
            var f = fields[i];
            if (typeof f == 'string') {
                fields[i] = { name: f };
            }
            if (typeof f.mapping == 'undefined') {
                f.mapping = f.name;
            }
            if (typeof f.convert == 'string') {
                f.convert = Edo.data.DataModel[f.convert.toLowerCase()];
            }
            if (f.type) {
                f.type = f.type.toLowerCase();
            }
            this.fieldsMap[f.name] = f;
        }
        this.fields = fields;
    },
    getField: function (name) {
        return this.fieldsMap[name];
    },
    convert: function (data) {
        if (!data) data = [];
        if (!(data instanceof Array)) data = [data];

        for (var i = 0, l = data.length; i < l; i++) {
            var record = data[i];
            this.convertRecord(record);
        }

        return data;
    },
    convertRecord: function (record) {
        var fields = this.fields;

        var getMapping = this.getMappingValue;

        for (var i = 0, l = fields.length; i < l; i++) {
            var field = fields[i];

            var mapping = field.mapping;
            var v = mapping.indexOf('.') == -1 ? record[mapping] : getMapping(record, mapping);

            v = record[field.name] = field.convert ? field.convert(v, record, field) : v;

            if (field.type) {
                var convert = Edo.data.Convertor[field.type.toLowerCase()];
                record[field.name] = convert ? convert(v, record, field) : v;
            }

        }
        return record;
    },
    getMappingValue: function (o, mapping) {
        var maps = mapping.split('.');
        var v = o;
        for (var i = 0, l = maps.length; i < l; i++) {
            v = v[maps[i]];
        }
        return v;
    },
    newRecord: function (append) {
        var record = {};
        var fields = this.fields;
        for (var i = 0, l = fields.length; i < l; i++) {
            var field = fields[i];
            if (Edo.isValue(field.defaultValue)) {
                record[field.name] = field.defaultValue;
            }
        }
        Edo.apply(record, append);
        return record;
    },
    errorMsg: '错误',
    valid: function (records, all) {
        if (!records || records === true) return;
        if (!(records instanceof Array)) records = [records];
        if (this.fields.length == 0) return true;
        var ret = true, isBreak = false;
        var fields = this.fields;
        var errors = [];
        for (var i = 0, l = records.length; i < l; i++) {
            if (isBreak) break;
            var record = records[i];

            var error = { record: record, fields: [] };    //错误记录

            for (var j = 0, k = fields.length; j < k; j++) {
                var field = fields[j];
                //如果没有验证条件, 则不参与验证
                if (field.required !== true && !field.valid) continue;
                var value = Edo.getValue(record, field.name);

                var r = this.validField(record, field.name, value);
                if (r !== true) {
                    ret = false;

                    error.fields.add({
                        name: field.name,
                        value: value,
                        errorMsg: r === false ? this.errorMsg : r
                    });

                    if (!all) {
                        isBreak = true;
                        break;
                    }
                }
            }

            if (error.fields.length > 0) {
                errors.add(error);
            }

        }

        if (errors.length > 0) {
            this.fireEvent('invalid', {
                type: 'invalid',
                source: this,
                errors: errors
            });
        } else {
            this.fireEvent('valid', {
                type: 'valid',
                source: this,
                errors: errors
            });
        }

        return ret;
    },
    requiredMsg: '不能为空',
    validField: function (record, name, value, fireValid) {
        var ret = true;
        var field = this.getField(name);
        //如果没有valid, 并且没有设置required, 则直接返回
        if (field && (field.required || field.valid)) {
            var valid = field.valid;
            if (typeof valid === 'string') {
                valid = Edo.core.Validator[valid.toLowerCase()];
            }

            value = arguments.length == 2 ? Edo.getValue(record, name) : value;

            if (field.required === true) {
                if (value === undefined || value === null || value === '') ret = this.requiredMsg;
            }

            if (ret === true && valid) {
                ret = valid(value, record, name, this);
            }
        }
        if (fireValid === true) {
            if (ret !== true) {
                this.fireEvent('invalid', {
                    type: 'invalid',
                    source: this,
                    action: 'field',
                    record: record,
                    field: name,
                    value: value,
                    errorMsg: ret
                });
            } else {
                this.fireEvent('valid', {
                    type: 'valid',
                    action: 'field',
                    source: this,
                    record: record,
                    field: name,
                    value: value
                });
            }
        }

        return ret;
    }
});

Edo.data.DataModel.regType('DataModel');

//converts
Edo.data.Convertor = {
    stripRe: /[\$,%]/g,

    'string': function (v, record, field) {
        return (v === undefined || v === null) ? '' : String(v);
    },
    'int': function (v, record, field) {
        return v !== undefined && v !== null && v !== '' ?
            parseInt(String(v).replace(this.stripRe, ""), 10) : 0;
    },
    'float': function (v, record, field) {
        return v !== undefined && v !== null && v !== '' ?
            parseFloat(String(v).replace(stripRe, ""), 10) : '';
    },
    'bool': function (v) {
        return v === true || v === "true" || v == 1;
    },
    'date': function (v, record, field) {
        if (!v) {
            return '';
        }
        if (Edo.isDate(v)) {
            return v;
        }
        var dateFormat = field.dateFormat;
        if (dateFormat) {
            if (dateFormat == "timestamp") {
                return new Date(v * 1000);
            }
            if (dateFormat == "time") {
                return new Date(parseInt(v, 10));
            }
            return Date.parseDate(v, dateFormat);
        }
        var parsed = Date.parse(v);
        return parsed ? new Date(parsed) : null;
    },
    'array': function (v, record, field) {
        if (!v && (v !== 0 || v !== false)) return [];
        return v.push ? v : [v];
    }
};


Edo.data.DataTable = function (data) {
    Edo.data.DataTable.superclass.constructor.call(this);

    this.source = this.view = [];
    this.modified = {};
    this.removed = {};
    this.idHash = {};

    //if(data) 
    this.load(data);

    //this._setConn(new Edo.data.PagingConnection());

}
Edo.data.DataTable.extend(Edo.data.DataModel, {
    componentMode: 'data',

    autoValid: true,    //自动验证:修改后, 选择后    

    dataTable: true,

    getSource: function () {
        return this.source;
    },


    _setData: function (data) {
        this.load(data);
    },
    refresh: function (view, action, event) {                     //刷新数据视图
        this.view = this.createView(view || this.view);
        this.fire(action || 'refresh', event);                  //数据组件,只需要监听refresh事件即可!
    },

    load: function (data, view) {
        if (!data) data = [];
        var e = { data: data };
        if (this.fire('beforeload', e) !== false) {   //这是属于"破坏"性的操作,如果有必要,要保存原来的操作结果,请使用beforeload事件

            this._doLoad(data);

            this.source = data;       //更新原始数据                    
            this.modified = {};
            this.removed = {};
            //this.idHash = {};            

            this.view = this.createView(view || data);

            this.changed = false;   //修改changed标记(表示数据是没有经过任何数据操作:增,删,改)

            this.canFire = true;

            this.fire('load', e);
        }
    },
    isChanged: function () {
        var changed = this.changed;
        if (!changed) changed = this.getAdded() != 0;
        if (!changed) changed = this.getUpdated() != 0;
        if (!changed) changed = this.getDeleted() != 0;
        return changed;
    },
    createView: function (view) {
        return view;
    },

    reload: function () {
        this.source.each(function (o) {
            delete o.__status;
        });

        this.load(this.source, this.view);
    },

    add: function (record) {
        this.insert(this.view.length, record);
    },

    addRange: function (records) {
        this.insertRange(this.view.length, records);
    },

    insert: function (index, record) {
        this.insertRange(index, [record]);
    },

    insertRange: function (index, records) {
        if (!records || !(records instanceof Array)) return;

        var d = this.source, v = this.view;

        for (var i = 0, len = records.length; i < len; i++) {
            var r = records[i];
            v.insert(index + i, r);

            if (d !== v) d.insert(index, r);         //在源数据上也新增

            this._doAdd(r);

            if (this.autoValid) {
                this.valid(r, true);
            }
        }
        this.fire('add', {
            records: records,
            index: index
        });
        this.changed = true;
    },


    remove: function (record) {
        this.removeRange([record]);
    },

    removeAt: function (index) {
        this.remove(this.getAt(index));
    },

    removeRange: function (records) {
        if (!records || !(records instanceof Array)) return;
        records = records.clone();

        for (var i = 0, l = records.length; i < l; i++) {
            var record = records[i];
            var index = this.view.indexOf(record);
            if (index > -1) {
                this.view.removeAt(index);
                this.source.remove(record);             //从源数据上也删除
                //                delete this.idHash[record.__id];
                //                delete this.modified[record.__id];

                this._doRemove(record);
            }
        }

        this.fire('remove', { records: records });
        this.changed = true;
    },

    updateRecord: function (record, o) {
        var canFire = this.canFire;
        this.beginChange();
        for (var p in o) {
            this.update(record, p, o[p]);
        }
        this.canFire = canFire;
        this.fire('update', {
            record: record
        });
        this.changed = true;
        if (this.autoValid) {
            this.valid(record, true);
        }
    },

    update: function (record, field, value) {
        var old = Edo.getValue(record, field);
        //var old = record[field];
        //alert(old+":"+value);
        var type = typeof (old);
        if (type == 'object') {
            if (old === value) return false;
        }
        else if (String(old) == String(value)) return false;

        //record[field] = value;
        Edo.setValue(record, field, value);

        //to data
        this._doUpdate(record, field, old);

        this.fire('update', {
            record: record,
            field: field,
            value: value
        });
        this.changed = true;

        if (this.autoValid) {
            this.validField(record, field, value, true);
            //this.valid(record, true);
        }
        return true;
    },

    move: function (records, index) {//index:数字, 行对象       

        var target = Edo.isInt(index) ? this.getAt(index) : index;

        if (!Edo.isArray(records)) records = [records];
        //debugger

        for (var j = 0, l = records.length; j < l; j++) {
            var record = records[j];

            this.view.remove(record);

            if (target) {
                var index = this.indexOf(target);
                this.view.insert(index, record);
            } else {
                this.view.add(record);
            }
        }

        //alert(this.indexOf(target));       
        this.fire('move', {
            records: records,
            index: this.indexOf(target)
        });

        this.changed = true;

    },

    clear: function () {
        this.source = this.view = [];
        this.modified = {};
        this.removed = {};
        this.fire('clear');
        this.changed = true;
    },

    reset: function (record) {
        var r = this.modified[record.__id];
        if (r) {
            Edo.apply(record, r);

            delete this.modified[record.__id];
            delete record.status;

            this.fire('reset', {
                record: record
            });
        }
    },

    resetField: function (record, field) {
        var r = this.modified[record.__id];
        if (r) {
            var v = r[field];
            if (typeof v !== 'undefined') {
                delete r[field];
                record[field] = v;

                this.fire('resetfield', {
                    record: record,
                    field: field,
                    value: v
                });
            }
        }
    },

    sort: function (sortFn) {
        //        function sortFn(pre, now){
        //            if(fn(pre, now) == true) return 1;
        //            else return -1;
        //        }
        //可以自己实现一个优化排序算法
        this.view.sortByFn(sortFn);
        if (this.view !== this.source) {
            this.source.sortByFn(sortFn);
        }
        this.fire('sort');
    },

    sortField: function (field, direction) {
        direction = direction || 'ASC';
        var fn;
        if (direction.toUpperCase() == 'ASC') {
            fn = function (r1, r2) {
                var v1 = r1[f], v2 = r2[f];
                return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);
            };
        } else {
            fn = function (r1, r2) {
                var v1 = r1[f], v2 = r2[f];
                return v1 < v2 ? 1 : (v1 > v2 ? -1 : 0);
            };
        }
        this.sort(fn);
    },

    filter: function (fn, scope) {
        var view = this.view = [];
        var data = this.source;
        for (var i = 0, j = data.length; i < j; i++) {
            var record = data[i];
            if (fn.call(scope, record, i, data) !== false) view[view.length] = record;
        }
        this.view = this.createView(this.view);
        this.fire('filter');
    },

    clearFilter: function () {
        this.filterFn = null;
        if (this.isFiltered()) {
            this.view = this.createView(this.source);
            this.fire('filter');
        }
    },

    isFiltered: function () {
        return this.view.length != this.source.length;
    },
    //查找:传递一个object,找出属性符合最多的对象的Index.
    //这其实是个很复杂的概念:1)默认是从当前数据视图查找;2)如果传递source对象,就从source对象查找(并且如果是删除的数据,不会查找出来)

    findIndex: function (attribute, array) {
        if (array === true) array = this.source;
        array = array || this.view;
        for (var i = 0, l = array.length; i < l; i++) {
            var o = array[i];
            if (o === attribute) return i;

            var all = true;     //全部匹配才行
            for (var p in attribute) {
                if (attribute[p] != o[p]) {
                    all = false;
                    break;
                }
            }
            if (all) return i;
        }
        return -1;
    },

    find: function (attribute, array) {
        if (array !== false) array = this.source;
        array = array || this.view;
        return array[this.findIndex(attribute, array)];
    },

    getById: function (id) {
        return this.idHash[id];
    },

    getViewById: function (id) {
        return this.viewHash[id];
    },

    getAt: function (index) {
        return this.view[index];
    },

    indexOf: function (record) {
        return this.view.indexOf(record);
    },

    indexOfId: function (id) {
        var r = this.idHash[id];
        return r ? this.indexOf(r) : -1;
    },

    getCount: function () {
        return this.view.length;
    },

    isEmpty: function () {
        return this.view.length == 0;
    },

    each: function (fn, scope) {
        this.view.each(fn, scope);
    },
    fire: function (action, event) {
        if (this.canFire) {
            event = Edo.apply({
                type: 'datachange',
                action: action,
                source: this
            }, event);
            this._onFire(event);

            if (['beforeload', 'load', 'refresh', 'filter', 'collapse', 'expand', 'sort'].indexOf(action) == -1) {
                this.changed = true;
            }

            return this.fireEvent('datachange', event);
        }
    },

    isModify: function (record) {
        var ro = this.modified[record.__id];
        if (!ro) return false;
        return true;
    },

    isFieldModify: function (record, field) {
        var ro = this.modified[record.__id];
        if (!ro) return false;
        if (typeof (ro[field]) !== 'undefined') return true;
        return false;
    },

    beginChange: function () {
        this.canFire = false;
    },

    endChange: function (action, event) {
        this.canFire = true;
        this.refresh(null, action, event);

        if (this.autoValid) {
            this.valid(true, true);
        }
    },

    getDeleted: function () {
        var rs = [];
        var os = this.removed;
        for (var id in os) {
            var o = os[id];
            rs.add(o);
        }
        return this.clearData(rs);
    },

    getUpdated: function (idField) {
        idField = idField || this.idField;
        var rs = [];

        if (idField === true) {        //返回整条记录
            var rs = [];
            for (var i = 0, l = this.source.length; i < l; i++) {
                var o = this.source[i];
                if (o.__status == 'update') {
                    rs.add(o);
                }
            }
            return this.clearData(rs);
        } else {
            var os = this.modified;
            for (var id in os) {
                var o = os[id];
                var r = this.getById(id);

                o[idField] = r[idField];
                rs.add(o);
                for (var p in o) {
                    o[p] = r[p];
                }

            }
        }
        return this.clearData(rs);
    },
    clearData: function (rs) {
        var list = [];
        for (var i = 0, l = rs.length; i < l; i++) {
            list.add(Edo.data.cloneData(rs[i]));
        }
        return list;
    },

    getAdded: function () {
        var rs = [];
        for (var i = 0, l = this.source.length; i < l; i++) {
            var o = this.source[i];
            if (o.__status == 'add') {
                rs.add(o);
            }
        }
        return this.clearData(rs);
    },
    _doLoad: function (data) {
        if (this.fields.length > 0) {
            data = this.convert(data);
        }

        var idHash = this.idHash = {};    //load的时候,清空原有的idHash
        var id = Edo.data.DataTable.id;
        for (var i = 0, l = data.length; i < l; i++) {
            var r = data[i];
            if (!r.__id || idHash[r.__id]) r.__id = id++;        //千万不能动...
            //r.__id = id++;
            idHash[r.__id] = r;
            delete r.__status;
            //r.__status = 'normal';
        }
        Edo.data.DataTable.id = id;
    },
    _doAdd: function (record) {
        if (record.__id) {
            var r = this.idHash[record.__id];
            if (r) {
                if (r !== record) {
                    record.__id = null;
                }
            }
        }
        if (!record.__id) {
            record.__id = Edo.data.DataTable.id++;
        }
        this.idHash[record.__id] = record;
        if (!record.__status) record.__status = 'add';
        else delete record.__status;
    },
    _doRemove: function (record) {
        if (record.__status != 'add') {
            record.__status = 'remove';
            this.removed[record.__id] = record;
        }
        delete this.idHash[record.__id];
        delete this.modified[record.__id];
        //delete record.__id;
    },
    idField: 'id',
    _doUpdate: function (record, field, value) {
        if (record.__status != 'add') {
            record.__status = 'update';
            var erow = this.modified[record.__id];
            if (!erow) {
                erow = this.modified[record.__id] = {};
            }
            if (typeof erow[field] === 'undefined') erow[field] = value;   //只保存一次,初始值
        }
    },
    _onFire: function (e) {
        if (e.action == 'update') return;
        var viewHash = this.viewHash = {};         //viewHash是可视的数据视图快速索引  
        var view = this.view;
        for (var i = 0, l = view.length; i < l; i++) {
            var r = view[i];
            viewHash[r.__id] = r;
            r.__index = i;
        }
    },

    getRecord: function (record) {
        var type = Edo.type(record);
        if (type == 'number') {
            record = this.getAt(record);
        } else if (type == 'string') {
            record = this.getById(record);
        }
        return record;
    },
    getRange: function (start, end) {
        if (start > end) {
            var t = start;
            start = end;
            end = t;
        }
        var range = [];
        for (var i = start; i <= end; i++) {
            range.push(this.getAt(i));
        }
        return range;
    },

    //数据指针:选中状态逻辑(单选)    
    selected: null,
    getSelectedIndex: function () {
        return this.indexOf(this.selected);
    },
    getSelected: function () {
        if (!this.selected || this.indexOf(this.selected) == -1) {
            this.selected = null;
            return null;
        }
        return this.selected;
    },
    isSelected: function (record) {
        record = this.getRecord(record);
        if (record == this.selected) return true;
        return false;
    },
    select: function (record) {
        record = this.getRecord(record);
        if (!record || this.isSelected(record)) return false;
        if (this.fireEvent('beforeselectionchange', {
            type: 'beforeselectionchange',
            source: this,
            selected: record
        }) === false) return false;
        this.selected = record;
        this.fireEvent('selectionchange', {
            type: 'selectionchange',
            source: this,
            selected: this.selected
        });

        if (this.autoValid) {

            this.valid(record, true);
        }
    },
    deselect: function (record) {
        record = this.getRecord(record);
        if (!record || !this.isSelected(record)) return false;
        if (this.fireEvent('beforeselectionchange', {
            type: 'beforeselectionchange',
            source: this,
            selected: this.selected,
            deselected: record
        }) === false) return false;
        this.selected = null;
        this.fireEvent('selectionchange', {
            type: 'selectionchange',
            source: this,
            selected: this.selected
        });
    },
    clearSelect: function () {
        this.deselect(this.selected);
    },
    firstSelect: function () {
        this.select(0);
    },
    prevSelect: function () {
        this.select(this.getSelectedIndex() - 1);
    },
    nextSelect: function () {
        this.select(this.getSelectedIndex() + 1);
    },
    lastSelect: function () {
        this.select(this.getCount() - 1);
    },
    valid: function (records, all) {
        if (!records || records === true) records = this.view;
        return Edo.data.DataTable.superclass.valid.call(this, records, all);
    },
    _setFields: function (fields) {
        Edo.data.DataTable.superclass._setFields.call(this, fields);
        this.convert(this.source);
    }
});

Edo.data.DataTable.id = 1000;

Edo.data.DataTable.regType('datatable');


Edo.data.cloneData = function (o) {
    var c = null;
    if (o instanceof Array) {
        c = o.clone();
    } else {
        c = Edo.apply({}, o);
    }
    delete c.__id;
    delete c.__index;
    delete c.__status;

    delete c.__pid;
    delete c.__preid;
    delete c.__nextid;
    delete c.__hasChildren

    delete c.__depth;
    delete c.expanded;
    delete c.__height;

    return c;
}




Edo.data.DataTree = function (data) {
    Edo.data.DataTree.superclass.constructor.call(this, data);
}
Edo.data.DataTree.extend(Edo.data.DataTable, {
    getSource: function () {
        return this.children;
    },
    reload: function () {
        this.source.each(function (o) {
            delete o.__status;
        });
        this.load(this.children, this.view);
    },

    load: function (tree, view) {
        if (!tree) tree = [];
        if (!(tree instanceof Array)) tree = [tree];

        this.children = tree;

        this.source = [];
        var data = [];
        this._createTable(tree, data);
        this.source = data;

        if (!view) {
            view = [];

            this._createTree(this.children, -1, 0, view, this.filterFn);

        }

        Edo.data.DataTree.superclass.load.call(this, data, view);
    },
    //是不是在本方法内,重新排定table?
    syncTreeView: function (action, event) {
        var view = [];  //获得树形筛选后的表格view视图
        this._createTree(this.children, -1, 0, view, this.filterFn);
        this.refresh(view, action, event);
    },
    //deep深度, stopNode截止的节点, 

    collapse: function (node, deep) {
        node.expanded = false;

        if (deep) {
            this.iterateChildren(node, function (o) {
                o.expanded = false;
            });
        }
        //this.canFire = true;
        if (this.canFire) {
            this.syncTreeView('collapse', { record: node });
        }
    },

    expand: function (node, deep) {
        node.expanded = true;

        var p = this.findParent(node);
        while (p) {
            p.expanded = true;
            p = this.findParent(p);
        }

        if (deep) {
            this.iterateChildren(node, function (o) {
                o.expanded = true;
            });
        }

        //this.canFire = true;
        if (this.canFire) {
            this.syncTreeView('expand', { record: node });
        }
    },

    expandAll: function () {
        this.beginChange();
        this.source.each(function (o) {
            this.expand(o, true);
        }, this);
        this.endChange();
    },

    collapseAll: function () {
        this.beginChange();
        this.source.each(function (o) {
            this.collapse(o, true);
        }, this);
        this.endChange();
    },

    toggle: function (node, deep) {

        if (node.expanded) this.collapse(node, deep);
        else this.expand(node, deep);
    },

    add: function (node, p) {
        if (!p) p = this;
        if (!p.children) p.children = [];
        return this.insert(p.children.length, node, p);
    },

    addRange: function (nodes, p) {
        if (!p) p = this;
        if (!p.children) p.children = [];
        return this.insertRange(p.children.length, nodes, p);
    },

    insert: function (index, node, p) {
        this.insertRange(index, [node], p);
    },

    insertRange: function (index, records, p) {
        if (!records || !(records instanceof Array)) return;

        if (!p) p = this;
        if (!p.children) p.children = [];

        var cs = p.children;
        for (var i = 0, len = records.length; i < len; i++) {
            var r = records[i];
            cs.insert(index + i, r);
            this._doAdd(r);

            this.iterateChildren(r, function (n) {
                this._doAdd(n);
            }, this);
        }

        //重新生成表格, 这时候, 这个data应该是source, 没有任何过滤
        var data = [];
        this._createTable(this.children, data);
        this.source = data;

        this.syncTreeView('add', {
            records: records,
            index: index,
            parentNode: p
        });

        this.changed = true;
    },

    remove: function (node) {

        var p = this.findParent(node);
        if (p) {
            p.children.remove(node);
            this.source.remove(node);             //从源数据上也删除
            this._doRemove(node);


            var data = [];
            this._createTable(this.children, data);
            this.source = data;

            this.syncTreeView('remove', {

                records: [node],
                parent: p
            });

            this.changed = true;
        }
    },
    _doAdd: function (node) {
        Edo.data.DataTree.superclass._doAdd.call(this, node);
        this.iterateChildren(node, function (cnode) {
            Edo.data.DataTree.superclass._doAdd.call(this, cnode);
        }, this);
    },
    _doRemove: function (node) {
        Edo.data.DataTree.superclass._doRemove.call(this, node);
        this.iterateChildren(node, function (cnode) {
            Edo.data.DataTree.superclass._doRemove.call(this, cnode);
        }, this);
    },

    removeAt: function (index, p) {
        if (!p) throw new Error("父节点为空");
        var node = p && p.children ? p.children[index] : null;
        if (node) {
            this.remove(node, p);
        }
    },

    removeRange: function (records, node) {

        if (!records || !(records instanceof Array)) return;
        records = records.clone();
        for (var i = 0, l = records.length; i < l; i++) {
            var record = records[i];

            node = this.findParent(record);
            if (node) {
                node.children.remove(record);
                this.source.remove(record);             //从源数据上也删除
                this._doRemove(record);
            }
        }

        this.changed = true;

        if (this.canFire) {
            this.syncTreeView('remove', {
                records: records,
                parent: node
            });
        }

    },

    move: function (nodes, target, action) {
        if (!nodes) return;
        if (!Edo.isArray(nodes)) nodes = [nodes];
        var p = this.findParent(target);
        if (!p) return;

        this.beginChange();
        for (var i = 0, l = nodes.length; i < l; i++) {
            var node = nodes[i];

            var index = p.children.indexOf(target);

            if (Edo.isNumber(action)) {
                p = target;
                index = action;
            } else if (action == 'append') {
                index += 1;
            } else if (action == 'add') {
                p = target;
                if (!p.children) p.children = []
                index = p.children.length;
            } else if (action == "preend") {

            }

            //这里好像有点性能问题
            if (this.isAncestor(node, p) || node == p) {
                return false;
            }

            var p1 = this.findParent(node);
            if (p1 == p) {
                var index2 = p1.children.indexOf(node);
                if (index2 <= index) {
                    index -= 1;
                }
            }

            var status = node.__status;
            var status2 = p.__status;

            //这里不太对, 造成了很大的性能开销
            //            this.remove(node);
            //            this.insert(index, node, p);
            //从旧删除
            var pold = this.findParent(node);
            if (pold) {
                pold.children.remove(node);
            }
            //在新插入
            p.children.insert(index, node);

            this._doAdd(node);

            this.iterateChildren(node, function (n) {
                this._doAdd(n);
            }, this);

            node.__status = status;
            p.__status = status2;
        }

        this.endChange('move', { records: nodes, index: index, parentNode: p });

        this.changed = true;
    },
    endChange: function (action, event) {

        var data = [];
        this._createTable(this.children, data);
        this.source = data;

        this.syncTreeView('action', event);

        Edo.data.DataTree.superclass.endChange.call(this, action, event);
    },

    filter: function (fn, scope) {
        //遍历每一个节点,如果true,则生成
        var view = [];
        this.filterFn = fn;
        this._createTree(this.children, -1, 0, view, fn, scope);
        this.refresh(view, 'filter');
    },

    findParent: function (node) {
        //return findParent(this, node);
        var p = this.getById(node.__pid);
        if (!p && this.getById(node.__id)) p = this;
        return p;
    },
    findTop: function (node) {
        while (1) {
            if (node.__pid == -1) return node;
            node = this.getById(node.__pid);
        }
    },

    getChildAt: function (parentNode, index) {
        if (parentNode.children) {
            return parentNode.children[index];
        }
    },

    indexOfChild: function (parentNode, node) {
        if (parentNode.children) {
            return parentNode.children.indexOf(node);
        }
        return -1;
    },

    isFirst: function (node) {
        return !node.__preid;
    },

    isLast: function (node) {
        return !node.__nextid;
    },

    isLeaf: function (node) {
        return !node.children || node.children.length == 0;
    },
    getPrev: function (node) {
        return this.getById(node.__preid);
    },
    getNext: function (node) {
        return this.getById(node.__nextid);
    },
    //Returns the path for this node. The path can be used to expand or select this node programmatically.
    //attr: (可选) 默认的组成路径的属性,默认是__id.
    getPath: function (node, attr) {

    },

    getDepth: function (node) {
        return node.__depth;
    },

    eachChildren: function (node, fn, scope) {
        var cs = node.children;
        if (cs) {
            cs.each(fn, scope);
        }
    },

    iterateChildren: function (p, fn, scope) {
        if (!fn) return;
        p = p || this;
        var cs = p.children;
        if (cs) {
            for (var i = 0, l = cs.length; i < l; i++) {
                var c = cs[i];
                if (fn.call(scope || this, c, i) === false) return;
                this.iterateChildren(c, fn, scope);
            }
        }
    },

    contains: function (parentNode, childNode) {
        while (childNode.__pid != -1) {
            if (childNode.__pid == parentNode.__id) return true;
            childNode = this.getById(childNode.__pid);
        }
        return false;
    },

    hasChildren: function (node) {
        return node.children && node.children.length > 0;
    },

    //将树形结构数据,转换为表格结构(其实是得到source源数据表格)
    _createTable: function (tree, data) {       //是一个树结构的数组

        for (var i = 0, l = tree.length; i < l; i++) {
            var t = tree[i];

            if (!t.__id) t.__id = Edo.data.DataTable.id++;

            var cs = t.children;

            data[data.length] = t;

            if (cs && cs.length > 0) {
                this._createTable(cs, data);
            }
        }
    },
    //得到树形的view数据视图表格, 主要是更新状态
    _createTree: function (tree, pid, depth, data, fn, scope) {
        if (!fn) fn = this.filterFn;

        var hasInView = false;  //是否有包含在视图内的节点
        for (var i = 0, l = tree.length; i < l; i++) {
            var t = tree[i];

            t.__pid = pid;
            t.__depth = depth;
            t.__hasChildren = t.children && t.children.length > 0;

            if (typeof (t.expanded) === 'undefined') t.expanded = true;
            else t.expanded = !!t.expanded;

            //__preid, __nextid
            t.__preid = t.__nextid = null;
            if (i != 0) t.__preid = tree[i - 1].__id;
            if (i != l - 1) t.__nextid = tree[i + 1].__id;

            var next = fn ? fn.call(scope, t, i) : true;   //是否继续

            var index = data ? data.length : 0;    //保留索引

            if (next !== false) {     //如果next不为false,则先加上了
                if (data) data[index] = t;

                hasInView = true;
            }
            var childInView = false;

            if (t.__hasChildren) {
                //如果确定是不是收缩的,则传递data, 否则传递null
                childInView = this._createTree(t.children, t.__id, depth + 1, t.expanded !== false ? data : null, fn);
            }

            if (childInView && next === false) {  //如果next为false,但是子元素内有符合条件的, 则插入
                if (data) data.insert(index, t);

                hasInView = true;
            }
        }
        return hasInView;
    },
    isAncestor: function (p, n) {
        if (!p || !n) return false;
        while (n) {
            n = this.findParent(n);
            if (n == p) return true;
        }
        return false;
    },
    isDisplay: function (record) {
        var p = this.findParent(record);
        if (!p || p == this) return true;

        if (!p.expanded) return false;
        return this.isDisplay(p);
    },

    getChildren: function (node, deep) {
        if (deep) {
            var children = [];
            this.iterateChildren(node, function (n) {
                children.add(n);
            });
            return children;
        } else {
            return node.children;
        }
    }
});

Edo.data.DataTree.regType('datatree');

//判断n是否是p的子节点
Edo.data.DataTree.isAncestor = function (p, n) {
    if (!p || !n) return false;
    var cs = p.children;
    if (cs) {
        for (var i = 0, l = cs.length; i < l; i++) {
            var c = cs[i];
            if (c == n) return true;
            var r = Edo.data.DataTree.isAncestor(c, n);
            if (r) return true;
        }
    }
    return false;
}



Edo.data.ArraytoTree = function (data, id, pid) {
    var tree = [];

    //建立快速索引
    var idHash = {};
    for (var i = 0, l = data.length; i < l; i++) {
        var o = data[i];
        idHash[o[id]] = o;
    }

    //数组转树形
    for (var i = 0, l = data.length; i < l; i++) {
        var o = data[i];
        var p = idHash[o[pid]];
        if (!p) {
            tree.push(o);
            continue;
        }
        if (!p.children) {
            p.children = [];
        }
        p.Summary = 1;
        p.children.push(o);
    }
    return tree;
}



Edo.core.Validator = function () {
    Edo.core.Validator.superclass.constructor.call(this);
};
Edo.core.Validator.extend(Edo.core.Component, {

    errorMsg: '错误',

    _setTarget: function (value) {
        if (typeof value == 'string') value = Edo.get(value);
        this.target = value;
        this.bindTarget();
    },

    _setProperty: function (value) {
        this.property = value;
        this.bindTarget();
    },

    _setValid: function (value) {
        if (typeof value === 'string') {
            var fn = Edo.core.Validator[this.validFn.toLowerCase()];
            if (fn) {
                value = fn;
            }
        }
        var _ = value;

        if (typeof value !== 'function') {
            eval('_ = function(value){' + value + '}');
        }
        this.validFn = _;
    },
    bindTarget: function () {
        if (this.target && this.property) {
            this.doBind.defer(100, this); //延迟100毫秒
        }
    },
    doBind: function () {
        this.target = typeof this.target === 'string' ? window[this.target] : this.target;
        if (this.target && this.property) {
            var etype = this.target.validPropertyEvent;
            this.target.un(etype, this.doValid, this);
            this.target.on(etype, this.doValid, this);
        }
    },
    doValid: function (e) {
        var t = this.target;
        if (!t.autoValid) return;
        if (t.validPropertyEvent !== 'propertychange' || (e.property == this.property)) {
            //如果是propertychange事件, 则改变的属性, 必须符合约定
            //如果不是propertychange事件, 则可以直接获取值, 进行验证
            var v = t.validPropertyEvent !== 'propertychange' ? e[this.property] : e['value'];

            return this.valid(v, e);
        }
    },

    valid: function (v, e) {
        var t = this.target;
        if (typeof v == 'undefined') v = t[this.property];
        if (this.validFn) {
            var ret = this.validFn.call(this.target, v, e, t);
            if (ret === true || ret === undefined) {
                t.fireEvent('valid', {
                    type: 'valid',
                    source: t,
                    property: this.property,
                    value: v
                });
                return true;
            }
            else {
                var msg = ret === false ? this.errorMsg : ret;
                t.fireEvent('invalid', {
                    type: 'invalid',
                    source: t,
                    errorMsg: msg,
                    property: this.property,
                    value: v
                });
                return false;
            }
        }
        return true;
    }
});

//提供一些标准常用的验证逻辑,如邮件,网址,等...
Edo.core.Validator.regType('validator');

Edo.apply(Edo.core.Validator, {
    //不能为空
    required: function (v, e, obj) {
        if (v === undefined || v === '') return false;
        return true;
    },
    //长度限制
    length: function (v) {
        var min = Edo.isNumber(this.minLength) ? this.minLength : 0;
        var max = Edo.isNumber(this.maxLength) ? this.maxLength : 5;
        if (!Edo.isValue(v)) return false;
        if (v.length > min && v.length <= max) return true;
        return "不在" + min + "~" + max + "长度范围内";
    },
    //必须是数字
    number: function (v) {
        var text = v;
        var num = parseInt(text);
        if (num != text) {
            return "必须输入数字";
        }
        var min = this.minValue || 0;
        var max = Edo.isNumber(this.maxValue) ? this.maxValue : 100;
        if (num < min || num > max) {
            return '只能输入' + min + '~' + max + '数值范围';
        }
        return true;
    }
});

Edo.core.Space = function () {
    Edo.core.Space.superclass.constructor.call(this);
};
Edo.core.Space.extend(Edo.core.UIComponent, {

    defaultWidth: 0,

    defaultHeight: 0,

    minWidth: 0,

    minHeight: 0,
    _setHtml: function () { }
});


Edo.core.Space.regType('space');

Edo.core.Split = function () {
    Edo.core.Split.superclass.constructor.call(this);
};
Edo.core.Split.extend(Edo.core.UIComponent, {

    defaultWidth: 6,

    defaultHeight: 20,

    minWidth: 6,
    elCls: 'e-split',
    getInnerHtml: function (sb) {
        sb[sb.length] = '<div class="e-split-inner"></div>';
    },
    _setHtml: function () { }
});

Edo.core.Split.regType('split');

Edo.core.HSplit = function () {
    Edo.core.HSplit.superclass.constructor.call(this);
};
Edo.core.HSplit.extend(Edo.core.Split, {

    height: 4,

    width: '100%',

    defaultHeight: 4,

    minHeight: 4,

    elCls: 'e-split e-split-v e-div'
});


Edo.core.HSplit.regType('hsplit');

Edo.MessageBox = {
    OK: ['ok'],
    CANCEL: ['cancel'],
    OKCANCEL: ['ok', 'cancel'],
    YESNO: ['yes', 'no'],
    YESNOCANCEL: ['yes', 'no', 'cancel'],

    INFO: 'e-messagebox-info',
    WARNING: 'e-messagebox-warning',
    QUESTION: 'e-messagebox-question',
    ERROR: 'e-messagebox-error',
    DOWNLOAD: 'e-messagebox-download',

    buttonText: {
        ok: "确定", //"OK",
        cancel: "取消", //"Cancel",
        yes: "是", //"Yes",
        no: "否"//"No"
    },
    saveText: '保存中...',

    hide: function (e) {
        var c = this.config;
        var hide = true;
        if (c && e) {

            var progress = Edo.getByName('progress', this.dlg)[0];
            var text = Edo.getByName('text', this.dlg)[0];
            var value = progress ? progress.getValue() : null;
            if (text) {
                value = text.getValue();
            }
            hide = c.callback.call(c.scope, e ? e.source.action || 'cancel' : 'cancel', value);
        }
        clearInterval(this.timer);
        clearTimeout(this.hideTimer);
        if (this.dlg && hide !== false) this.dlg.hide();
    },

    show: function (c) {

        var children = c.children;
        delete c.children;

        Edo.applyIf(c, {
            autoClose: false,           //是否自动关闭
            closeTime: 3000,
            enableClose: true,
            title: '',
            titleIcon: '',
            msg: '',
            width: 'auto',
            height: 'auto',
            callback: Edo.emptyFn
        });
        clearInterval(this.timer);

        this.config = c;

        if (!this.dlg) {
            this.dlg = Edo.create({
                //render: '#body',
                cls: 'e-dragtitle',
                type: 'window',
                minWidth: 180,
                minHeight: 60,
                verticalGap: 0,
                titlebar: [
                    {
                        cls: 'e-titlebar-close',
                        onclick: this.hide.bind(this)
                    }
                ],
                children: [
                    {
                        type: 'box',
                        width: '100%',
                        border: 0,
                        minHeight: 40,
                        layout: 'horizontal'
                    }, {
                        type: 'ct',
                        width: '100%',
                        layout: 'horizontal',
                        defaultHeight: 28,
                        horizontalAlign: 'center',
                        verticalAlign: 'bottom',
                        horizontalGap: 10
                    }
                ]
            });

            this.body = this.dlg.getChildAt(0);
            this.foot = this.dlg.getChildAt(1);
        }

        var g = this.dlg;
        g.set(c);

        g.titlebar.getChildAt(0).set('visible', c.enableClose !== false);

        //        this.body.removeAllChildren(true);
        //        this.foot.removeAllChildren(true);

        var o = {
            layout: 'horizontal',
            children: [
                {
                    type: 'div',
                    width: 44,
                    height: 35,
                    cls: c.icon,
                    visible: !!c.icon
                }, {
                    type: 'label',
                    text: c.msg
                }
            ]
        };

        if (c.prompt) {
            o = {
                layout: 'vertical',
                verticalGap: 0,
                children: [
                    {
                        type: 'label',
                        width: '100%',
                        text: c.msg
                    }, {
                        name: 'text',
                        type: c.multiline ? 'textarea' : 'text',
                        text: c.text,
                        width: '100%',
                        minHeight: c.multiline ? 80 : 22
                    }
                ]
            };

        } else if (c.progress) {
            o = {
                layout: 'vertical',
                verticalGap: 0,
                children: [
                    {
                        type: 'label',

                        text: c.msg
                    }, {
                        name: 'progress',
                        type: 'progress',
                        width: '100%',
                        text: c.progressText || '',
                        progress: c.progressValue || 0
                    }
                ]
            };
        }
        if (c.wait) {
            var o = {
                layout: 'horizontal',
                children: [
                    {
                        type: 'div',
                        width: 44,
                        height: 50,
                        cls: c.icon,
                        visible: !!c.icon
                    },
                    {
                        type: 'ct',
                        width: '100%',
                        height: '100%',
                        children: [
                            {
                                type: 'label',
                                text: c.msg
                            }, {
                                type: 'progress',
                                showText: false,
                                text: c.progressText || '',
                                progress: c.progressValue || 0,
                                width: '100%'
                            }
                        ]
                    }

                ]
            };
        }

        if (children) {
            o = {
                children: children
            }
        }

        this.body.set(o);

        //var btns = 
        var buttons = c.buttons || [];
        var btns = [];
        buttons.each(function (o) {
            o = typeof o == 'string' ? {
                type: 'button',
                text: this.buttonText[o],
                action: o,
                minWidth: 70,
                defaultHeight: 22,
                onclick: this.hide.bind(this)
            } : o;

            btns.add(o);

        }, this);

        this.foot.set({
            visible: btns.length > 0,
            children: btns
        });

        if (c.wait) {
            var p = this.body.getChildAt(1).getChildAt(1);
            var pi = 0;
            this.timer = setInterval(function () {
                pi += 10;
                p.set({
                    progress: pi
                });
                if (pi >= 100) pi = 0;
            }, c.interval || 200);
        }

        g.show(c.x, c.y, true);
        if (c.autoClose) {
            var closeTime = c.autoClose === true ? c.closeTime : c.autoClose;
            this.hideTimer = this.hide.defer(closeTime, this);
        }
        return this;
    },

    alert: function (title, msg, callback, scope) {
        this.show({
            title: title,
            msg: msg,
            buttons: this.OK,
            callback: callback,
            scope: scope
        });
        return this;
    },

    confirm: function (title, msg, callback, scope) {
        this.show({
            title: title,
            msg: msg,
            buttons: this.YESNO,
            callback: callback,
            scope: scope,
            icon: this.QUESTION
        });
        return this;
    },

    prompt: function (title, msg, callback, scope, multiline, text) {
        this.show({
            title: title,
            msg: msg,
            buttons: this.OKCANCEL,
            callback: callback,
            minWidth: 250,
            scope: scope,
            prompt: true,
            multiline: multiline,
            text: Edo.isValue(text) ? text : ''
        });
        return this;
    },

    updateProgress: function (value, text) {
        var p = this.body.getChildAt(1);
        if (p && p.type == 'progress') {
            p.set({
                progress: value,
                text: text
            });
        }
    },

    loading: function (title, msg) {
        Edo.MessageBox.show({
            title: title,
            msg: msg,
            children: [
            {
                type: 'div',
                cls: 'e-messagebox-wait',
                width: '100%',
                height: '100%'
            }
           ],
            width: 250
        });
    },

    saveing: function (title, msg) {
        Edo.MessageBox.show({
            title: title,
            msg: msg,
            enableClose: false,
            progressText: this.saveText,
            width: 300,
            wait: true,
            interval: 200,
            icon: 'e-messagebox-save'
        });
    }
};


Edo.core.Module = function () {





    Edo.core.Module.superclass.constructor.call(this);
};
Edo.core.Module.extend(Edo.core.UIComponent, {
    elCls: 'e-module e-div',

    autoMask: true,

    src: '',
    _setSrc: function (value) {
        if (this.src != value) {
            this.src = value;
            this.load(value);
            this.changeProperty('src', value);
        }
    },
    _setHtml: function () { },

    createChildren: function () {
        Edo.core.Module.superclass.createChildren.apply(this, arguments);
        if (this.src) {
            this.load(this.src);
        }
    },

    syncSize: function () {
        Edo.core.Module.superclass.syncSize.call(this);
        if (this.iframe) {
            var size = Edo.util.Dom.getSize(this.el, true);
            this.iframe.style.width = size.width + 'px';
            this.iframe.style.height = size.height + 'px';
        }
    },
    //    doCreateChildWindow: function(){
    //        var cEdo = this.childWindow.Edo;
    //        if(cEdo){                    
    //            cEdo.create({
    //                type: 'button',
    //                redner: '#body'
    //            });
    //            this.configGet = null;
    //            
    //            this.fireEvent('load',{
    //                type: 'load',
    //                source: sf                
    //            });
    //        }else{
    //            this.doCreateChildWindow.defer(100, this);
    //        }
    //    },
    doLoad: function () {
        if (this.configGet === false) {
            doLoad.defer(50, this);
            return;
        }

        this.childWindow = this.iframe.contentWindow;

        //        if(this.isConfig){               //暂时不支持此功能:以后应该是edo字符串库,直接操作滴
        //            //引用js和css资源,并...从ajax得到配置xml或json
        //            var html = '<html><body><style type="text/css">html,body{width:100%;height:100%;overflow:hidden;}</style></body></html>'+Edo_core_Module_res+'<script type="text/javascript"></script>';
        //            var doc = this.childWindow.document;
        //            doc.open();
        //            doc.write(html);
        //            doc.close();
        //            
        //            this.doCreateChildWindow.defer(100, this);            
        //            return;
        //        }

        if (this.autoMask !== false) this.unmask();

        this.fireEvent('load', {
            type: 'load',
            source: this,
            src: this.src,
            window: this.childWindow
        });
    },

    load: function (src, isConfig) {
        this.src = src;
        if (!this.el) return false;

        if (this.unload() === false) return;

        if (this.autoMask !== false) this.mask();

        this.src = src;
        this.isConfig = isConfig;
        //        
        //debugger
        var im = this.iframe = document.createElement('iframe');
        im.frameBorder = 0;
        //im.scrolling = 'no';
        im.style.width = (this.realWidth || 0) + 'px';
        im.style.height = (this.realHeight || 0) + 'px';

        this.el.appendChild(im);

        if (isConfig) {
            this.configGet = false;
            var sf = this;
            Edo.util.Ajax.request({
                url: src,
                type: 'get',
                onSuccess: function (text) {
                    sf.configGet = text;
                },
                onFail: function () {
                    throw new Error("没有获得module的配置对象资源");
                }
            });
            //1)ajax src
            //2)iframe创建
            //两者都是异步的,所以,需要一个同步
        } else {
            setTimeout(function () {
                im.src = src;
            }, 10);
        }

        function checkIFrame() {

            var win = this.iframe.contentWindow;
            try {
                if (win && win.document && win.document.readyState == "complete") {
                    this.doLoad();
                } else {
                    checkIFrame.defer(20, this);
                }
            } catch (e) {
                this.doLoad(false);
            }
        }
        if (isGecko && !isChrome) {
            var sf = this;
            im.onload = this.doLoad.bind(this);
        } else {
            checkIFrame.defer(20, this);
        }


        //优化
        //1)xml的获取由module进行
        //2)同时生成iframe,不需要等iframe加载完毕才进行xml的获取
    },

    unload: function () {
        if (this.src && this.iframe) {
            //卸载不通过,则不进行更换src操作
            if (this.fireEvent('beforeunload', { type: 'beforeunload', source: this }) === false) return false;

            this.iframe.src = 'javascript:false;'; //'abort:blank';//
            Edo.util.Dom.remove(this.iframe);
            this.childWindow = this.src = this.iframe = this.isConfig = null;
            this.fireEvent('unload', {
                type: 'unload',
                source: this
            });
        }
    }
});
Edo.core.Module.regType('module');
Edo.ns('Edo.rpc');
Edo.rpc.Client = function (url) {
    this.url = url;
    this.createInvokes(Edo.rpc.Client.rpcConfig);
    Edo.rpc.Client.superclass.constructor.call(this);
}
Edo.rpc.Client.extend(Edo.core.Component, {
    url: '',
    method: 'post',
    timeout: 0,
    abort: function (rid) {
        Edo.util.Ajax.abort(rid);
        rid = null;
    },
    createInvokes: function (classes) {

        for (var clsName in classes) {
            var clsMap = classes[clsName];
            var methods = clsMap.methods;

            var d = clsName.split(".");
            var rootCls = d[0];
            var o = cls = {};
            [ ].each.call(d.slice(1), function (v2) {
                o = o[v2] = o[v2] || {};
            });

            var sf = this;
            for (var i = 0, l = methods.length; i < l; i++) {
                var method = methods[i];
                var m = function () {
                    var args = [].slice.apply(arguments);
                    return this.client.invoke(this.className, this.methodName, args, this.url || this.client.url);
                }
                m.className = clsName;
                m.methodName = method;
                m.url = clsMap.url;
                m.client = this;

                o[method] = m.bind(m);
            }

            this[rootCls] = cls;
        }
    },
    invoke: function (clazz, method, params, url) {
        if (!(params instanceof Array)) params = [params];

        var fail = typeof params[params.length - 1] === 'function' ? params.pop() : null;
        var success = typeof params[params.length - 1] === 'function' ? params.pop() : null;
        if (fail && !success) {
            success = fail;
            fail = null;
        }

        var async = false;
        if (success) async = true;

        var result = null;
        var rid = Edo.util.Ajax.request({
            url: url || this.url,
            async: async,
            type: this.method,
            params: {
                'class': clazz,
                method: method,
                params: params
            },
            onSuccess: function (txt, ajax) {
                var o = txt;
                try {
                    o = Edo.util.JSON.decode(txt);  //�������SON, �������Ϣ
                } catch (e) {
                    o = {
                        error: -1,
                        message: txt
                    }
                }
                ajax.responseObject = o;
                if (o.error == 0) {
                    if (success) success(o.result, ajax);
                } else {
                    if (fail) fail(o.error, o.message, ajax);

                    if (async == false) throw o; //���, ���� �����
                }

                if (async == false) {
                    result = o.result;
                }
            },
            onFail: function (code, ajax) {
                if (fail) fail(code, ajax.request.responseText, ajax);

                if (async == false) {
                    var error = {
                        error: code,
                        message: ajax.request.responseText
                    };
                    throw error;
                }
            }
        })

        return result === null ? rid : result;
    }
});

Edo.rpc.Client.rpcConfig = {};
Edo.rpc.Client.regService = function (clazz, methods, url) {
    if (!(methods instanceof Array)) methods = [methods];
    var cls = this.rpcConfig[clazz];
    if (!cls) {
        cls = this.rpcConfig[clazz] = { name: clazz };
    }
    cls.url = url;
    var allMethods = cls["methods"];
    if (!allMethods) {
        allMethods = cls["methods"] = [];
    }

    allMethods.addRange(methods);
}

AJAXRPC_Client = Edo.rpc.Client;



Edo.containers.Box = function () {
    Edo.containers.Box.superclass.constructor.call(this);
};
Edo.containers.Box.extend(Edo.containers.Container, {

    defaultWidth: 60,

    defaultHeight: 22,

    border: [1, 1, 1, 1],

    padding: [5, 5, 5, 5],

    elCls: 'e-box',

    bodyStyle: '',          //bodyStyle

    bodyCls: '',

    getInnerHtml: function (sb) {
        var box = this._getBox();
        var lb = this.getLayoutBox(box);

        sb[sb.length] = '<div class="e-box-scrollct ';
        sb[sb.length] = this.bodyCls;
        sb[sb.length] = '" style="';
        sb[sb.length] = this.bodyStyle;
        sb[sb.length] = ';width:';
        sb[sb.length] = lb.width;
        sb[sb.length] = 'px;height:';
        sb[sb.length] = lb.height;
        sb[sb.length] = 'px;margin:0px;padding:0px;border:0px;position:absolute;left:';

        sb[sb.length] = lb.x - box.x - this.border[3];
        sb[sb.length] = 'px;top:';
        sb[sb.length] = lb.y - box.y - this.border[0];
        sb[sb.length] = 'px;';
        sb[sb.length] = ';' + this.doScrollPolicy();


        sb[sb.length] = '"></div>';
    },

    createHtml: function (w, h, appendArray) {
        this._getBPStyle();

        return Edo.containers.Box.superclass.createHtml.call(this, w, h, appendArray);
    },

    createChildren: function (el) {
        Edo.containers.Box.superclass.createChildren.call(this, el);
        this.scrollEl = this.el.firstChild; //这个操作很耗时 1000个box生成,要耗掉150左右毫秒
    },
    measure: function () {
        Edo.containers.Box.superclass.measure.call(this);

        var b = this.border;
        var p = this.padding;
        //1)给得到的布局参考值加上所有的偏移
        this.realWidth += b[3] + p[3] + b[1] + p[1];        //如果不是绝对设置的,则需要加上偏移
        this.realHeight += b[0] + p[0] + b[2] + p[2];

        //2)确保操作...
        this.measureSize();
    },
    getLayoutBox: function (box) {           //获取容器的布局尺寸!!!要减掉偏移!!!
        var b = this.border;
        var p = this.padding;

        box = box || Edo.containers.Box.superclass.getLayoutBox.call(this);
        box.width = box.width - b[3] - p[3] - b[1] - p[1];
        box.height = box.height - b[0] - p[0] - b[2] - p[2];
        box.x += b[3] + p[3];
        box.y += b[0] + p[0];
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;             //这里的逻辑没问题!!!
    },
    syncSize: function () {
        var w = this.realWidth, h = this.realHeight;

        this.domWidth = w;
        this.domHeight = h;
        Edo.containers.Box.superclass.syncSize.call(this);

    },
    _setBodyCls: function (value) {
        if (this.bodyCls != value) {
            this.bodyCls = value;
            if (this.el) {
                Edo.util.Dom.addClass(this.scrollEl, value);
            }
            this.changeProperty('bodyCls', value);
        }
    },
    _setBodyStyle: function (value) {
        if (this.bodyStyle != value) {
            this.bodyStyle = value;
            if (this.el) {
                Edo.util.Dom.applyStyles(this.scrollEl, value);
            }
            this.changeProperty('bodyStyle', value);
        }
    },
    _getBPStyle: function () {
        var b = this.border; //, p = this.padding;
        var style = 'border-left-width:' + b[3] + 'px;border-top-width:' + b[0] + 'px;border-right-width:' + b[1] + 'px;border-bottom-width:' + b[2] + 'px;';
        //style += 'padding-left:'+ p[0]+'px;padding-top:'+ p[1]+'px;padding-right:'+ p[2]+'px;padding-bottom:'+ p[3]+'px;';        
        this.addStyle(style);
    },
    _setBorder: function (value) {
        value = this._toArray(value);
        if (!this._checkTheSame(this.border, value)) {
            this.border = value;
            this.addStyle(this._getBPStyle());
            this.relayout('border', value);
            this.changeProperty('border', value);
        }
    },
    _setPadding: function (value) {
        value = this._toArray(value);
        if (!this._checkTheSame(this.padding, value)) {

            this.padding = value;
            this.addStyle(this._getBPStyle());

            this.relayout('padding', value);
            this.changeProperty('padding', value);
        }
    }
});
Edo.containers.Box.regType('box');

Edo.containers.Group = function () {
    Edo.containers.Group.superclass.constructor.call(this);

};
Edo.containers.Group.extend(Edo.containers.Container, {

    defaultWidth: 60,

    defaultHeight: 22,

    elCls: 'e-group e-ct e-div',


    padding: [2, 3, 3, 3],

    syncScrollEl: function (box) {

        //Edo.util.Dom.applyStyles(this.scrollEl, 'border:0px;padding:0px;margin:0px;');
        if (this.scrollEl != this.el) {     //扩展后,scrollEl和el可能不是同一个对象
            //&& this.mustSyncSize !== false    算了,容器就一直弄吧
            //trace('<b>syncScrollEl:</b>'+this.id);

            var bx = this._getBox();
            var left = box.x - bx.x; // - (parseInt(Edo.util.Dom.getStyle(this.scrollEl.parentNode, 'borderLeftWidth')) || 0);
            var top = box.y - bx.y; // - (parseInt(Edo.util.Dom.getStyle(this.scrollEl.parentNode, 'borderTopWidth')) || 0);
            if (box.width < 0) box.width = 0;
            if (box.height < 0) box.height = 0;
            this.scrollEl.style.width = box.width + 'px';
            this.scrollEl.style.height = box.height + 'px';
        }
    },
    getHeaderHtml: function () {
        return '';
    },
    getInnerHtml: function (sb) {
        var p = this.padding;
        var l = p[3], t = p[0], r = p[1], b = p[2];
        var h = this.realHeight - t - b;
        var w = this.realWidth - l - r;
        var headerHtml = this.getHeaderHtml(sb);
        sb[sb.length] = '<table cellspacing="0" border="0" cellpadding="0"><tr class="e-group-t"><td class="e-group-tl" style="height:' + t + 'px;width:' + l + 'px;"></td><td class="e-group-tc" style="width:' + w + 'px;">' + headerHtml + '</td><td class="e-group-tr" style="width:' + r + 'px;"></td></tr><tr class="e-group-m"><td class="e-group-ml"></td><td class="e-group-mc"><div class="e-group-body" style="width:' + w + 'px;height:' + h + 'px;"></div></td><td class="e-group-mr"></td></tr><tr class="e-group-b"><td class="e-group-bl" style="height:' + b + 'px;width:' + l + 'px;"></td><td class="e-group-bc"></td><td class="e-group-br" style="width:' + r + 'px;"></td></tr></table>';
    },
    createChildren: function (el) {
        Edo.containers.Group.superclass.createChildren.call(this, el);
        this.table = this.el.firstChild;
        this.tc = this.table.rows[0].cells[1];
        this.mc = this.table.rows[1].cells[1];
        this.bc = this.table.rows[2].cells[1];
        this.scrollEl = this.mc.firstChild;

        this.bodyEl = this.mc.firstChild;
        //        this.Group = this.el.firstChild;
        //        this.scrollEl = this.Group.lastChild;
        //        this.legendEl = this.Group.firstChild;
        //        this.checkbox = this.legendEl.firstChild;
        //        this.titleEl = this.legendEl.lastChild;
    },

    measure: function () {
        Edo.containers.Group.superclass.measure.call(this);

        this.realWidth += this.padding[1] + this.padding[3];
        this.realHeight += this.padding[0] + this.padding[2];
        this.measureSize();
    },
    syncSize: function () {
        Edo.containers.Group.superclass.syncSize.call(this);


        var p = this.padding;
        var t = p[0], r = p[1], b = p[2], l = p[3];
        var h = this.realHeight - t - b;
        var w = this.realWidth - l - r;

        this.tc.style.width = w + 'px';

        this.bodyEl.style.width = w + 'px';
        this.bodyEl.style.width = w + 'px';


    },
    getLayoutBox: function () {
        var box = Edo.containers.Group.superclass.getLayoutBox.call(this);
        box.x += this.padding[0];
        box.y += this.padding[1];
        box.width -= (this.padding[1] + this.padding[3]);
        box.height -= (this.padding[0] + this.padding[2]);

        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;             //这里的逻辑没问题!!!
    },
    _setPadding: function (value) {
        value = this._toArray(value);
        if (!this._checkTheSame(this.padding, value)) {
            var b = this.padding = value;
            //this.addStyle(this._getBPStyle());


            this.relayout('padding', value);
            this.changeProperty('padding', value);
        }
    }
});
Edo.containers.Group.regType('group');

Edo.containers.FieldSet = function () {
    Edo.containers.FieldSet.superclass.constructor.call(this);

};
Edo.containers.FieldSet.extend(Edo.containers.Container, {

    defaultWidth: 80,

    defaultHeight: 20,

    collapseHeight: 18,

    legend: '',


    padding: [3, 5, 5, 5],

    elCls: 'e-fieldset e-div',

    getInnerHtml: function (sb) {
        sb[sb.length] = '<fieldset class="e-fieldset-fieldset"><legend class="e-fieldset-legend">';
        sb[sb.length] = '<span class="' + (this.enableCollapse ? 'e-fieldset-icon' : '') + '">';
        sb[sb.length] = this.legend || '&#160;';
        sb[sb.length] = '</span></legend><div class="e-fieldset-body" style="';
        sb[sb.length] = this.doScrollPolicy();
        sb[sb.length] = '"></div></fieldset>';
    },
    createChildren: function (el) {
        Edo.containers.FieldSet.superclass.createChildren.call(this, el);

        this.fieldset = this.el.firstChild;
        this.scrollEl = this.fieldset.lastChild;
        this.legendEl = this.fieldset.firstChild;
        this.titleEl = this.legendEl.lastChild;
    },
    initEvents: function () {
        Edo.containers.FieldSet.superclass.initEvents.call(this);
        Edo.util.Dom.on(this.legendEl, 'click', this._onLegendClick, this);
    },
    _onLegendClick: function (e) {

        if (this.enableCollapse) {
            this.toggle();
        }
    },
    syncSize: function () {
        Edo.containers.FieldSet.superclass.syncSize.apply(this, arguments);

        Edo.util.Dom.setSize(this.fieldset, this.realWidth, this.realHeight);
    },
    measure: function () {
        Edo.containers.FieldSet.superclass.measure.call(this);

        this.realWidth += 2;
        this.realHeight += 20;

        var p = this.padding;
        this.realWidth += p[3] + p[1];
        this.realHeight += p[0] + p[2];

        this.measureSize();
    },
    getLayoutBox: function () {
        var box = Edo.containers.FieldSet.superclass.getLayoutBox.call(this);
        box.x += 1;
        box.y += 1;
        box.width -= 2;
        box.height -= 20;

        var p = this.padding;
        box.width = box.width - p[3] - p[1];
        box.height = box.height - p[0] - p[2];
        box.x += p[3];
        box.y += p[0];

        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;             //这里的逻辑没问题!!!
    },
    _setLegend: function (value) {
        if (this.legend !== value) {
            this.legend = value;
            if (this.el) {
                this.titleEl.innerText = value;
            }
            this.changeProperty('legend', value);
        }
    },
    _setPadding: function (value) {
        value = this._toArray(value);
        if (!this._checkTheSame(this.padding, value)) {
            this.padding = value;
            this.relayout('padding', value);
            this.changeProperty('padding', value);
        }
    },
    destroy: function () {
        Edo.util.Dom.clearEvent(this.legendEl);
        this.legendEl = null;

        Edo.containers.FieldSet.superclass.destroy.call(this);
    }
});

Edo.containers.FieldSet.regType('fieldset');



Edo.containers.Panel = function () {
    Edo.containers.Panel.superclass.constructor.call(this);

    //titleclick
};
Edo.containers.Panel.extend(Edo.containers.Box, {

    collapseHeight: 26,

    collapseWidth: 26,

    minHeight: 22,          //当折叠后,此容器不调用measure和doLayout,不操作子元素    

    headerHeight: 25,

    title: '',

    titleIcon: '',

    titleCollapse: false,

    titlebar: null,

    elCls: 'e-panel e-box e-div',

    getInnerHtml: function (sb) {
        var h = this.headerHeight;
        if (!isBorderBox) {
            h -= 1;
        }
        sb[sb.length] = '<div class="e-panel-header" style="height:' + h + 'px;line-height:' + h + 'px;"><div class="e-panel-title">' + this.doTitle() + '</div><div class="e-titlebar"></div></div>';
        Edo.containers.Panel.superclass.getInnerHtml.call(this, sb);
    },
    initEvents: function () {
        Edo.containers.Panel.superclass.initEvents.call(this);

        this.on('click', this._onClick, this);
    },
    createChildren: function (el) {
        Edo.containers.Panel.superclass.createChildren.call(this, el);
        this.scrollEl = this.el.lastChild;

        this.headerCt = this.el.firstChild;
        this.titleCt = this.headerCt.firstChild;
        this.titlebarCt = this.headerCt.lastChild;

        if (this.titlebar) {
            this.titlebar.render(this.titlebarCt);
        }
    },
    measure: function () {
        Edo.containers.Panel.superclass.measure.call(this);
        this.realHeight += this.headerHeight;
        this.measureSize();
    },
    getLayoutBox: function () {
        var box = Edo.containers.Panel.superclass.getLayoutBox.call(this);
        var hd = this.headerHeight;
        box.y += hd;
        box.height -= hd;

        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    doTitle: function () {
        var title = "";
        if (this.titleIcon) title += '<div style="float:left;margin-right:3px;" class="e-panel-titleicon ' + this.titleIcon + '"></div>';
        if (this.title) title += '<div style="float:left;">' + this.title + '</div>';
        if (this.titleCt) this.titleCt.innerHTML = title;
        return title;
    },
    _setTitle: function (value) {
        if (this.title != value) {
            this.title = value;
            this.doTitle();
            this.changeProperty('title', value);
        }
    },
    _setTitleIcon: function (value) {
        if (this.titleIcon != value) {
            this.titleIcon = value;
            this.doTitle();
            this.changeProperty('titleIcon', value);
        }
    },
    _setHeaderHeight: function (value) {
        value = parseInt(value);
        if (isNaN(value)) throw new Error('headerHeight must is Number type');
        if (this.headerHeight != value) {
            this.headerHeight = value;

            this.collapseHeight = value + this.border[0];
            this.relayout('headerHeight', value);
        }
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        var h = this.headerHeight;
        if (!isBorderBox) {
            h -= 1;
        }
        if (this.el) {
            this.headerCt.style.height = h + 'px';
            this.headerCt.style.lineHeight = h + 'px';
        }
        Edo.containers.Panel.superclass.syncSize.call(this);
    },
    _setTitlebar: function (value) {
        if (!(value instanceof Array)) value = [value];
        value.each(function (o) {
            var c = o.cls;
            Edo.apply(o, {
                type: 'button',
                simpleButton: true,
                height: 15,
                width: 15,
                minHeight: 15,
                minWidth: 15,
                cls: c,
                overCls: o.overCls || c + '-over',
                focusCls: o.focusCls || c + '-focus',
                pressedCls: o.pressedCls || c + '-pressed'
            });
        });

        if (this.titlebar) {
            this.titlebar.destory();
        }

        this.titlebar = Edo.create({
            type: 'ct',
            layout: 'horizontal',
            horizontalGap: 1,
            height: 15,
            children: value,
            onclick: function (e) {
                e.stop();
            }
        });
        this.titlebar.owner = this;

        if (this.titlebarCt) {
            this.titlebar.render(this.titlebarCt);
        }
    },
    _onClick: function (e) {
        if (e.within(this.headerCt) && this.titleCollapse) {
            this.toggle();
        }
    }

    ////    setCollapseProperty: function(value){
    ////        this.collapseProperty = value;
    ////        if(this.el){
    ////            if(value == 'width'){
    ////                //e-collapse-width
    ////                Edo.util.Dom.addClass(this.titleCt, 'e-collapse-width');
    ////                Edo.util.Dom.addClass(this.scrollEl, 'e-collapse-width');
    ////            }else{
    ////                Edo.util.Dom.removeClass(this.titleCt, 'e-collapse-width');
    ////                Edo.util.Dom.removeClass(this.scrollEl, 'e-collapse-width');
    ////            }
    ////        }
    ////    } 
});

Edo.containers.Panel.regType('panel', 'dialog');

Edo.containers.Dialog = Edo.containers.Panel;

Edo.containers.FormItem = function () {
    Edo.containers.FormItem.superclass.constructor.call(this);

};
Edo.containers.FormItem.extend(Edo.containers.Box, {

    defaultHeight: 22,

    minHeight: 22,

    border: [0, 0, 0, 0],

    padding: [0, 0, 0, 0],


    labelPosition: 'left',

    label: '',

    labelWidth: 60,
    labelHeight: 22,

    labelAlign: 'left',

    labelCls: '',

    labelStyle: '',

    elCls: 'e-formitem e-box e-div',

    forId: '',  //如果没有设置, 可以focus第一个child

    measure: function () {
        Edo.containers.FormItem.superclass.measure.call(this);
        if (this.labelPosition == 'left' || this.labelPosition == 'right') {
            this.realWidth += this.labelWidth;
        } else {
            this.realHeight += this.labelHeight;
        }

        this.measureSize();
    },
    getLayoutBox: function () {
        var box = Edo.containers.FormItem.superclass.getLayoutBox.call(this);

        switch (this.labelPosition) {
            case "top":
                box.y += this.labelHeight;
                box.height -= this.labelHeight;
                break;
            case "right":
                box.width -= this.labelWidth;
                break;
            case "bottom":
                box.height -= this.labelHeight;
                break;
            case "left":
                box.x += this.labelWidth;
                box.width -= this.labelWidth;
                break;
        }

        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },

    doLabel: function () {
        if (this.el) {
            this.labelCt.innerHTML = '<div style="float:' + this.labelAlign + ';">' + this.label + '</div>';
        }
    },
    _setLabel: function (value) {
        if (this.label != value) {
            this.label = value;
            this.doLabel();
        }
    },
    _setLabelAlign: function (value) {
        if (this.labelAlign != value) {
            this.labelAlign = value;
            this.doLabel();
        }
    },
    _setLabelPosition: function (value) {
        if (this.labelPosition != value) {
            this.labelPosition = value;
            this.relayout('labelPosition', value);
        }
    },
    _setLabelStyle: function (value) {
        if (this.labelStyle != value) {
            this.labelStyle = value;
            if (this.labelCt) Edo.util.Dom.applyStyles(this.labelCt, value);
        }
    },
    _setLabelCls: function (value) {
        if (this.labelCls != value) {
            this.labelCls = value;
            Edo.util.Dom.addClass(this.labelCt, this.labelCls);
        }
    },
    _setLabelWidth: function (value) {
        value = parseInt(value);
        if (isNaN(value)) throw new Error('labelWidth must is Number type');
        if (this.labelWidth != value) {
            this.labelWidth = value;

            this.relayout('labelWidth', value);
        }
    },
    _setForId: function (value) {

        if (this.forId != value) {
            this.forId = value;

            var cmp = Edo.getCmp(value);
            if (cmp) {
                //点击事件,激发cmp的focus方法.

                //获取cmp的focusEl,设置htmlFor
            } else {
                this.labelCt.dom.htmlFor = value;
            }
        }
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.containers.FormItem.superclass.syncSize.call(this);

        var box = this.getLayoutBox();
        var w = this.labelWidth, h = this.labelHeight;
        var style = 'top:auto;right:auto;bottom:auto;left:auto;';
        switch (this.labelPosition) {
            case "top":
                w = box.width;
                style += 'left:' + this.padding[3] + 'px;top:' + this.padding[0] + 'px';
                break;
            case "right":
                h = box.height;
                style += 'right:' + this.padding[1] + 'px;top:' + this.padding[0] + 'px';
                break;
            case "bottom":
                w = box.width;
                style += 'left:' + this.padding[3] + 'px;bottom:' + this.padding[2] + 'px';
                break;
            case "left":
                h = box.height;
                style += 'left:' + this.padding[3] + 'px;top:' + this.padding[0] + 'px';
                break;
        }

        Edo.util.Dom.setSize(this.labelCt, w, h);
        Edo.util.Dom.applyStyles(this.labelCt, style);
    },
    getInnerHtml: function (sb) {
        sb[sb.length] = '<label for="' + this.forId + '" class="e-formitem-label ' + this.labelCls + '" style="overflow:hidden;text-align:' + this.labelAlign + ';' + this.labelStyle + ';">' + this.label + '</label>';
        Edo.containers.Panel.superclass.getInnerHtml.call(this, sb);
    },
    createChildren: function (el) {
        Edo.containers.FormItem.superclass.createChildren.call(this, el);
        this.scrollEl = this.el.lastChild;

        this.labelCt = this.el.firstChild;
    }
});

Edo.containers.FormItem.regType('formitem');

Edo.containers.Application = function () {
    Edo.containers.Application.superclass.constructor.call(this);
};
Edo.containers.Application.extend(Edo.containers.Box, {
    fireTimer: null,


    minWidth: 400,

    minHeight: 200,

    elCls: 'e-app e-box e-div',

    notHasParent: 'the app module cannot have the father object',

    initEvents: function () {
        if (!this.design) {
            if (this.parent) throw new Error(this.notHasParent);
            Edo.util.Dom.on(window, 'resize', function (e) {

                //if(isIE){
                if (this.fireTimer) clearTimeout(this.fireTimer);
                this.fireTimer = this.onWindowResize.defer(100, this, [e]);
                //            }else{
                //                this.onWindowResiz(e);
                //            }
            }, this);
        }
        Edo.containers.Application.superclass.initEvents.call(this);
    },
    measure: function () {
        //if(this.id == 'design_app') debugger
        if (!this.design) this.syncViewSize();
        Edo.containers.Application.superclass.measure.call(this);
    },
    syncViewSize: function () {
        var dh = Edo.util.Dom, doc = document;


        var w = dh.getViewWidth(doc);
        var h = dh.getViewHeight(doc);

        this.width = w;
        this.height = h;


    },
    onWindowResize: function (e) {

        this.syncViewSize();

        this.relayout('size', this);

        this.fireTimer = null;
    },
    //    render: function(dom){    
    //        if(!this.design) dom = '#body';
    //        Edo.containers.Application.superclass.render.call(this, dom);
    //    },
    destroy: function () {
        //DomHelper.clearEvent(window);
        Edo.containers.Application.superclass.destroy.call(this);
    }
});
Edo.containers.Application.regType('app');

Edo.containers.Window = function () {
    Edo.containers.Window.superclass.constructor.call(this);
};
Edo.containers.Window.extend(Edo.containers.Panel, {

    renderTo: '#body',

    shadow: true,

    minWidth: 180,

    minHeight: 80,

    //elCls: 'e-window e-dialog e-group',

    initEvents: function () {
        Edo.containers.Window.superclass.initEvents.call(this);

        this.on('mousedown', this.onMouseDown, this);
    },

    onMouseDown: function (e) {
        if (e.within(this.headerCt)) {
            Edo.managers.DragManager.startDrag({
                event: e,
                delay: 0,
                capture: false,
                autoDragDrop: true,
                proxy: true,
                proxyCls: 'e-dragdrop-proxy',
                dragObject: this
            });
        }
        this.focus();
    },

    show: function (x, y, modal) {
        this.render(this.renderTo);
        this.addCls('e-drag-title');
        Edo.managers.PopupManager.createPopup({
            target: this,
            x: x,
            y: y,
            modal: modal
        });
        return this;
    },

    hide: function () {
        this.set('visible', false);
        Edo.managers.PopupManager.removePopup(this);
        return this;
    }
});

Edo.containers.Window.regType('window');


Edo.navigators.Navigator = function () {
    Edo.navigators.Navigator.superclass.constructor.call(this);

    //titleclick
};
Edo.navigators.Navigator.extend(Edo.containers.Box, {

    layout: 'horizontal',
    scrollOffset: 30,

    startVisible: true,
    endVisible: true,
    startWidth: 18,
    startHeight: 18,
    endWidth: 18,
    endHeight: 18,
    startCls: '',
    endCls: '',

    elCls: 'e-nav e-box e-ct e-div',

    //导航容器,滚动条必须设置为off

    horizontalScrollPolicy: 'off',

    verticalScrollPolicy: 'off',

    //这里不加measure应该是对的
    //    measure: function(){
    //        Edo.navigators.Navigator.superclass.measure.call(this);
    //        if(this.startVisible){
    //            if(this.layout == 'horizontal'){
    //                this.realWidth += this.startWidth;                
    //            }else if(this.layout == 'vertical'){
    //                this.realHeight += this.startHeight;
    //            }
    //        }
    //        if(this.endVisible){
    //            if(this.layout == 'horizontal'){                                
    //                this.realWidth += this.endWidth;   
    //            }else if(this.layout == 'vertical'){                
    //                this.realHeight += this.endHeight;
    //            }
    //        }        
    //        this.measureSize();
    //    },
    getNavLayoutBox: function () {
        //var box = Edo.navigators.Navigator.superclass.getLayoutBox.call(this); 
        var box = this.getLayoutBox();
        if (this.startVisible) {
            if (this.layout == 'horizontal') {
                box.x += this.startWidth;
                box.width -= this.startWidth;
            } else if (this.layout == 'vertical') {
                box.y += this.startHeight;
                box.height -= this.startHeight;
            }
        }
        if (this.endVisible) {
            if (this.layout == 'horizontal') {
                box.width -= this.endWidth;
            } else if (this.layout == 'vertical') {
                box.height -= this.endHeight;
            }
        }
        //        if(this.navBarWidthGet){
        //            box.x += this.buttonWidth;
        //            box.width -= this.buttonWidth * 2;            
        //            this.navBarWidthGet = false;
        //        }

        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    syncSize: function () {
        Edo.navigators.Navigator.superclass.syncSize.call(this);

        //如果超出滚动条,则...
        var dom = this.scrollEl;
        var doNav = false;
        if (this.layout == 'horizontal' && dom.scrollWidth > dom.clientWidth) {
            doNav = true;
        } else if (this.layout == 'vertical' && dom.scrollHeight > dom.clientHeight) {
            doNav = true;
        }
        if (doNav) {
            var box = this.getNavLayoutBox();
            this.syncScrollEl(box);

            if (this.startVisible) {
                var left = box.x, top = box.y, w = this.startWidth, h = this.startHeight;
                if (this.layout == 'horizontal') {
                    left -= (this.startWidth + this.padding[3]);
                    h = box.height;
                }
                if (this.layout == 'vertical') {
                    top -= (this.startHeight + this.padding[0]);
                    w = box.width;
                }
                this.startButton._setBox({
                    x: left,
                    y: top,
                    width: w,
                    height: h
                });
                //this.startButton._setVisible(true);
            } else {
                //this.startButton._setVisible(false);
            }
            if (this.endVisible) {
                var left = box.x, top = box.y, w = this.endWidth, h = this.endHeight;
                if (this.layout == 'horizontal') {
                    left += box.width + this.padding[1];
                    h = box.height;
                }
                if (this.layout == 'vertical') {
                    top += box.height + this.padding[2];
                    w = box.width;
                }

                this.endButton._setBox({
                    x: left,
                    y: top,
                    width: w,
                    height: h
                });
                //this.endButton._setVisible(true);
            } else {
                //this.endButton._setVisible(false);
            }
        } else {
            this.startButton.el.style.left = '-300px';
            this.endButton.el.style.left = '-300px';
            this.startButton.el.style.position = 'absolute';
            this.endButton.el.style.position = 'absolute';
            //            this.startButton._setVisible(false);
            //            this.endButton._setVisible(false);
        }

    },
    createChildren: function (el) {
        Edo.navigators.Navigator.superclass.createChildren.call(this, el);
        //        this.endButtonCt = this.el.lastChild;        
        //        this.startButtonCt = this.endButtonCt.previousSibling;

        this.startButton = Edo.create({
            type: 'button',
            icon: 'e-nav-start',
            minHeight: 5,
            //simpleButton: true,
            onclick: this.preScrollView.bind(this),
            render: this.el
        });
        this.endButton = Edo.create({
            icon: 'e-nav-end',
            minHeight: 5,
            type: 'button',
            //simpleButton: true,
            onclick: this.nextScrollView.bind(this),
            render: this.el
        });
    },
    _setHorizontalScrollPolicy: function (value) {
        this.horizontalScrollPolicy = 'off';
    },
    _setVerticalScrollPolicy: function (value) {
        this.verticalScrollPolicy = 'off';
    },
    //    _onButtonClick: function(e){
    //        var offset = 0;
    //        var sp = this.layout == 'horizontal' ? 'scrollLeft' : 'scrollTop';
    //        if(this.startButton == e.source){
    //            offset = -30;
    //        }else{
    //            offset = 30;
    //        }
    //        this.scrollEl[sp] += offset;
    //    },
    preScrollView: function () {
        var sp = this.layout == 'horizontal' ? 'scrollLeft' : 'scrollTop';
        this.scrollEl[sp] -= this.scrollOffset;
    },
    nextScrollView: function () {
        var sp = this.layout == 'horizontal' ? 'scrollLeft' : 'scrollTop';
        this.scrollEl[sp] += this.scrollOffset;
    }
});

Edo.navigators.Navigator.regType('navigator', 'nav');

Edo.navigators.Menu = function () {
    Edo.navigators.Menu.superclass.constructor.call(this);

};
Edo.navigators.Menu.extend(Edo.navigators.Navigator, {

    layout: 'vertical',

    minWidth: 40,
    minHeight: 20,
    defaultHeight: 20,

    defaultWidth: 100,

    verticalGap: 0,

    padding: [1, 1, 1, 1],

    startWidth: 8,
    startHeight: 8,
    endWidth: 8,
    endHeight: 8,


    autoHide: false,

    elCls: 'e-box e-menu ', //e-toolbar e-toolbar-over

    initEvents: function () {
        this.on('click', this._onClick, this);

        Edo.navigators.Menu.superclass.initEvents.call(this);
    },
    _onClick: function (e) {
        var oi = this.overItem;

        if (!oi && this.layout == 'horizontal') {
            return;
        }

        if (this.autoHide) {
            this.hideMenu();
        }
    },
    hideMenu: function () {
        Edo.managers.PopupManager.removePopup(this);

        if (this.owner && this.owner.type == 'button') {
            this.owner.hidePopup();


            if (this.owner.parent && this.owner.parent.type == "menu") {
                this.owner.parent.hideMenu();
            }
        }
    },
    _onChildOver: function (e) {
        var btn = e.source;
        var oi = this.overItem;
        if (!oi && this.layout == 'horizontal') {
            return;
        }

        this.showMenu(btn);
    },
    _onChildClick: function (e) {
        var btn = e.source;
        var oi = this.overItem;

        if (!oi && this.layout == 'horizontal') {
            this.showMenu(e.source);
        }

    },
    showMenu: function (btn) {
        var oi = this.overItem;
        if (oi && oi.menu) {
            oi.hidePopup();
        }

        this.overItem = btn;
        if (btn.menu) {
            var box = btn._getBox(true);
            if (this.layout == 'vertical') {
                btn.showPopup(box.x + box.width, box.y, false, null, -btn.realWidth, btn.realHeight);
            } else {
                btn.showPopup();
            }
            //btn.menu.hideMenu();
        }
    },
    _onChildPopupHide: function (e) {
        this.overItem = null;
    },
    addChildAt: function () {
        var o = arguments[1];
        if (this.layout == 'vertical') {
            o.width = '100%';
            o.icon = o.icon || ' ';
            if (o.menu) o.arrowMode = 'menu';
            else o.arrowMode = null;
        }
        o.showMenu = false;

        var c = Edo.navigators.Menu.superclass.addChildAt.apply(this, arguments);
        c.on('mouseover', this._onChildOver, this);
        c.on('click', this._onChildClick, this);
        c.on('popuphide', this._onChildPopupHide, this);

        return c;
    }
});
Edo.navigators.Menu.regType('menu');

Edo.navigators.ToggleBar = function (config) {





    Edo.navigators.ToggleBar.superclass.constructor.call(this);
};
Edo.navigators.ToggleBar.extend(Edo.navigators.Navigator, {
    componentMode: 'control',
    valueField: 'selectedIndex',

    selectedIndex: -1,

    selectedItem: null,

    selectedCls: 'e-togglebar-selected',

    elCls: 'e-togglebar e-nav e-box e-ct e-div',

    addChildAt: function (index, c) {
        var c = Edo.navigators.ToggleBar.superclass.addChildAt.apply(this, arguments);
        c.on('click', this._onChildrenClick, this);
        return c;
    },
    removeChildAt: function (index) {
        var c = Edo.navigators.ToggleBar.superclass.removeChildAt.apply(this, arguments);

        if (index == this.selectedIndex) {
            var child = this.getChildAt(index);
            if (!child) {
                child = this.getChildAt(index - 1);
            }
            if (child) {
                this.set('selectedItem', child);
            } else {
                this._setSelectedIndex(-1);
            }
        }

        return c;
    },

    _setSelectedItem: function (value) {
        var index = this.children.indexOf(value);
        if (index != -1 && value != this.selectedItem) {
            this.selectedIndex = -1;
        }
        this._setSelectedIndex(index);
    },
    _setSelectedIndex: function (value) {

        if (!this.rendered) {
            this.selectedIndex = value;
            return;
        }
        if (value < 0) {
            value = -1;
        }
        if (value >= this.children.length) {
            value = this.children.length - 1;
        }
        if (this.selectedIndex === value) return;

        if (this.fireEvent('beforeselectionchange', {
            type: 'beforeselectionchange',
            source: this,
            selectedIndex: value
        }) !== false) {
            this.selectedIndex = value;
            this.children.each(function (o, i) {
                if (i == value) {
                    o.set('pressed', true);
                    o.addCls(this.selectedCls);
                }
                else {
                    o.set('pressed', false);
                    o.removeCls(this.selectedCls);
                }
            }, this);

            this.selectedItem = this.getDisplayChildren()[value];
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this,
                index: this.selectedIndex,
                selectedIndex: this.selectedIndex
            });

            this.changeProperty('selectedIndex', value);
            this.el.style.visible = 'hidden';
            //this.el.style.display = 'none';
            this.relayout('selectedIndex');
        }
    },
    _onChildrenClick: function (e) {
        var index = this.children.indexOf(e.source);
        if (index != this.selectedIndex) {
            this._setSelectedIndex(index);
        }
    },
    render: function () {
        Edo.navigators.ToggleBar.superclass.render.apply(this, arguments);
        var index = this.selectedIndex;
        delete this.selectedIndex;
        this._setSelectedIndex(index);
    }
});

Edo.navigators.ToggleBar.regType('togglebar');


Edo.navigators.TabBar = function (config) {
    Edo.navigators.TabBar.superclass.constructor.call(this);

};

Edo.navigators.TabBar.extend(Edo.navigators.ToggleBar, {


    defaultHeight: 25,

    border: [1, 1, 1, 1],

    padding: [2, 5, 2, 5],

    width: '100%',

    verticalAlign: 'bottom',

    horizontalGap: 3,

    minHeight: 17,

    selectedCls: 'e-tabbar-selected',
    itemCls: 'e-tabbar-item',   //这个本来是为了做button的加粗效果,现在缓一缓,后续在做.主要是button的动态生成获取坐标,破坏了样式影响性!这是一个"框架级"的"严重"问题.

    elCls: 'e-tabbar e-togglebar e-nav e-box e-ct e-div',
    //selectedIndex: -1,


    position: 'top',

    _setPosition: function (value) {
        if (this.position != value) {
            this.position = value;
            switch (value) {
                case 'top':
                    this.verticalAlign = 'bottom';
                    break;
                case 'bottom':
                    this.verticalAlign = 'top';
                    break;
                case 'left':
                    this.horizontalAlign = 'right';
                    break;
                case 'right':
                    this.horizontalAlign = 'left';
                    break;
            }

            this.changeProperty('position', this.position);
            this.relayout('position', this);
        }
    },

    getInnerHtml: function (sb) {
        this.elCls += ' e-tabbar';
        this.elCls += ' e-tabbar-' + this.position;

        Edo.navigators.TabBar.superclass.getInnerHtml.call(this, sb);
        sb[sb.length] = '<div class="e-tabbar-line"></div>';
    },
    createChildren: function (el) {
        Edo.navigators.TabBar.superclass.createChildren.call(this, el);
        this.stripEl = Edo.util.Dom.append(this.scrollEl, '<div class="e-tabbar-line-strip"></div>');
        this.lineEl = this.scrollEl.nextSibling;
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.navigators.TabBar.superclass.syncSize.call(this);

        //this.doSyncLine.defer(100, this);
        this.doSyncLine();
    },
    doSyncLine: function () {
        var box = this._getBox(true);
        var scrollBox = Edo.util.Dom.getBox(this.scrollEl);

        var selBox = this.selectedItem ? this.selectedItem._getBox(true) : { x: 0, y: 0, width: 0, height: 0, right: 0, bottom: 0 };
        switch (this.position) {
            case "top":
                selBox.x += 1;
                selBox.y = selBox.bottom - 1;
                selBox.width -= 2;
                selBox.height = 1;

                box.y = scrollBox.bottom - 1;
                box.height = 5; //box.bottom - scrollBox.bottom;
                break;
            case "bottom":
                selBox.x += 1;
                selBox.width -= 2;
                selBox.height = 1;

                box.height = scrollBox.y - box.y + 1;
                break;
            case "left":
                selBox.x = selBox.right - 1;
                selBox.width = 1;
                selBox.y += 1;
                selBox.height -= 2;

                box.x = scrollBox.right - 1;
                box.width = 5; //box.right - scrollBox.right; 
                break;
            case "right":
                selBox.y += 1;
                selBox.height -= 2;
                selBox.width = 1;

                //box.x = selBox.right-1;
                box.width = scrollBox.x - box.x + 1;


        }
        Edo.util.Dom.setBox(this.stripEl, {
            x: selBox.x,
            y: selBox.y,
            width: selBox.width,
            height: selBox.height
        });

        Edo.util.Dom.setBox(this.lineEl, box);
    },
    addChildAt: function () {
        var a = arguments[1];
        if (this.position == 'top' || this.position == 'bottom') {
            a.height = "100%";
            //a.autoHeight = false;
        } else {
            a.width = "100%";
            //a.autoWidth = false;
        }
        var c = Edo.navigators.TabBar.superclass.addChildAt.apply(this, arguments);
        c.addCls('e-tabbar-item');
        return c;
    }
});

Edo.navigators.TabBar.regType('tabbar');

Edo.navigators.VTabBar = function (config) {
    Edo.navigators.VTabBar.superclass.constructor.call(this);

};

Edo.navigators.VTabBar.extend(Edo.navigators.TabBar, {

    width: 'auto',

    height: '100%',

    verticalAlign: 'top',

    horizontalAlign: 'left',

    defaultWidth: 22,

    defaultHeight: 60,

    position: 'left',

    layout: 'vertical',

    padding: [2, 2, 2, 3],
    verticalGap: 2
});
Edo.navigators.VTabBar.regType('vtabbar');

Edo.navigators.PagingBar = function (config) {

    Edo.navigators.PagingBar.superclass.constructor.call(this);


};
Edo.navigators.PagingBar.extend(Edo.containers.Box, {
    componentMode: 'control',
    elCls: 'e-pagingbar e-box e-ct e-div',

    size: 20,

    index: 0,

    total: -1,

    totalPage: -1,
    loading: false,

    autoPaging: false,   //创建成功,自动激发分页

    totalTpl: '第 <input class="e-pagingbar-input" onchange="<%=this.id%>.change(parseInt(this.value)-1, <%=this.size%>);" onkeydown="if(event.keyCode==13) <%=this.id%>.change(parseInt(this.value)-1, <%=this.size%>);" type="text" value="<%=this.index+1%>"/> / <%=this.totalPage%>页, 每页<input onchange="<%=this.id%>.change(<%=this.index%>, this.value);" onkeydown="if(event.keyCode==13) <%=this.id%>.change(<%=this.index%>, this.value);" class="e-pagingbar-input"  type="text" value="<%=this.size%>"/>条, 共<%=this.total%>条',

    layout: 'horizontal',
    layout: 'horizontal',
    border: [0, 0, 0, 0],
    padding: [0, 0, 0, 0],

    barVisible: true,

    infoVisible: true,

    _setSize: function (v) {
        this.size = v;
    },
    init: function () {
        var pager = this;
        this.set({
            verticalAlign: 'middle',
            'children': [
                {
                    name: 'barct', type: 'ct', layout: 'horizontal', horizontalGap: 0, verticalAlign: 'middle', height: '100%',
                    children: [
                        {
                            type: 'button',
                            name: 'first',
                            defaultHeight: 16,
                            icon: 'e-page-first',
                            onclick: this.doFirst.bind(this)
                        },
                        {
                            type: 'button',
                            name: 'pre',
                            defaultHeight: 16,
                            icon: 'e-page-pre',
                            onclick: this.doPre.bind(this)
                        },
                        {
                            type: 'button',
                            name: 'next',
                            defaultHeight: 16,
                            icon: 'e-page-next',
                            onclick: this.doNext.bind(this)
                        },
                        {
                            type: 'button',
                            name: 'last',
                            defaultHeight: 16,
                            icon: 'e-page-last',
                            onclick: this.doLast.bind(this)
                        }
                    ]
                },
                {
                    type: 'button',
                    name: 'refresh',
                    defaultHeight: 16,
                    icon: 'e-icon-refresh',
                    onclick: this.doRefresh.bind(this)
                },
                {
                    type: 'button',
                    name: 'nowait',
                    defaultHeight: 16,
                    icon: 'e-page-nowait'
                },
                {
                    type: 'space', name: 'space', width: '100%'
                },
                {
                    name: 'infoct', type: 'ct', layout: 'horizontal', verticalAlign: 'middle',
                    children: [
                    //                        {
                    //                            type: 'text',
                    //                            name: 'index',
                    //                            textStyle: 'text-align:center',
                    //                            text: '1',
                    //                            width: 25,
                    //                            ontextchange: function(e){
                    //        //                        var index = parseInt(this.text);
                    //        //                        if(isNaN(index)){
                    //        //                            alert("只能输入数字");
                    //        //                            this.set('text', pager.index);
                    //        //                            return;
                    //        //                        }
                    //        //                        pager.change(index);
                    //                            }
                    //                        },
                        {
                        type: 'label',
                        name: 'view'
                    }
                    ]
                }
            ]
        });
        Edo.navigators.PagingBar.superclass.init.apply(this, arguments);

        var sf = this;
        function get(name) {
            return Edo.getByName(name, sf)[0];
        }

        var btns = this.children;
        this.firstBtn = get("first");
        this.preBtn = get("pre");
        this.nextBtn = get("next");
        this.lastBtn = get("last");

        this.refreshBtn = get("refresh");
        this.loadBtn = get("nowait");

        //this.indexText = get("index");
        //this.sizeText = get("size");
        this.viewText = get("view");
        //
        this.barCt = get('barct');
        this.infoCt = get('infoct');

        this.space = get('space');

        this.barCt.set('visible', this.barVisible);
        this.infoCt.set('visible', this.infoVisible);

        if (this.autoPaging) {
            this.change.defer(this.padingDelay, this);
        }
    },
    padingDelay: 100,
    doFirst: function (e) {
        this.change(0);
    },
    doPre: function (e) {
        this.change(this.index - 1);
    },
    doNext: function (e) {
        this.change(this.index + 1);
    },
    doLast: function (e) {
        this.change(this.totalPage - 1);
    },
    doRefresh: function (e) {
        this.change();
    },

    change: function (index, size, params) {
        index = parseInt(index);
        size = parseInt(size);
        var e = {
            type: 'beforepaging',
            source: this,
            index: Edo.isValue(index) ? index : this.index,
            size: Edo.isValue(size) ? size : this.size,
            params: params
        }

        if (this.fireEvent('beforepaging', e) !== false) {

            this.index = e.index;
            this.size = e.size;

            this.compute();

            this.loading = true;
            this.refresh();

            e.type = 'paging';
            this.fireEvent('paging', e);
        }

    },
    compute: function (index, size, total) {
        if (Edo.isValue(index)) this.index = index;
        if (Edo.isValue(size)) this.size = size;
        if (Edo.isValue(total)) this.total = total;
        this.totalPage = parseInt(this.total / this.size) + (this.total % this.size ? 1 : 0);
        //this.totalPage -= 1;

        if (this.index >= this.totalPage - 1) {
            this.index = this.totalPage - 1;
        }
        if (this.index < 0) this.index = 0;
    },

    refresh: function (total) {
        if (Edo.isValue(total)) {
            this.total = total;
        }
        //根据当前的分页数据,更新按钮状态(可用/不可用等..)

        this.compute();

        //loading
        if (this.inited) {
            this.firstBtn.set('enable', this.index != 0);
            this.preBtn.set('enable', this.index != 0);

            this.nextBtn.set('enable', !(this.totalPage == 0) && this.index != this.totalPage - 1);
            this.lastBtn.set('enable', !(this.totalPage == 0) && this.index != this.totalPage - 1);

            this.viewText.set('text', '');
            this.viewText.set('text', new Edo.util.Template(this.totalTpl).run(this));

            this.loadBtn.set('icon', this.loading ? 'e-page-wait' : 'e-page-nowait');

            this.loading = false;
        }
    }
});

Edo.navigators.PagingBar.regType('pagingbar');
Edo.navigators.PagingBar.prototype.refreshView = Edo.navigators.PagingBar.prototype.refresh;


Edo.controls.Button = function () {




    //arrowclick
    Edo.controls.Button.superclass.constructor.call(this);
}
Edo.controls.Button.extend(Edo.controls.Control, {
    enableForm: false,

    simpleButton: false,

    autoWidth: true,
    autoHeight: true,

    defaultWidth: 18,

    defaultHeight: 21,
    height: 21,

    minWidth: 20,

    minHeight: 18,

    showMenu: true,

    popupWidth: 110,

    //widthGeted: false,//当width为auto的时候,是不是要获取dom的宽度

    text: '',

    icon: '',

    iconAlign: 'left',

    arrowMode: '',   //menu, split, close

    arrowAlign: 'right',

    //使用enableClose: true, 用一个占位的div来做close trigger

    autoClose: true,    //如果arrowMode为close,并且autoClose:true,则点击arrow区域,自动destroy此组件


    pressed: false,

    overCls: 'e-btn-over',
    pressedCls: 'e-btn-click',

    togglCls: 'e-btn-pressed',
    enableToggle: false,

    elCls: 'e-btn',

    _onMouseEvent: function (e) {
        if (!this.isEnable()) return;
        e.source = this;
        if (!this.design) {

            if (e.type == 'click') {
                var outer = e.target.firstChild;
                if (outer && outer.nodeType == 3) {
                    outer = null;
                }

                if (this.arrowMode && this.arrowMode != 'menu' && Edo.util.Dom.hasClass(e.target, 'e-btn-arrow')) {
                    e.type = 'arrowclick';
                    this.fireEvent('arrowclick', e);
                    return;
                }
            }

            this.fireEvent(e.type, e);
        }
    },
    createPopup: function () {
        Edo.controls.Button.superclass.createPopup.apply(this, arguments);
        this.menu = this.popupCt;
    },
    showPopup: function () {
        this._setPressed(true);
        Edo.controls.Button.superclass.showPopup.apply(this, arguments);
    },
    hidePopup: function () {
        Edo.controls.Button.superclass.hidePopup.apply(this, arguments);

        this._setPressed(false);

        if (this.menu && this.menu.children) {
            this.menu.children.each(function (o) {
                if (o.type == 'button' && o.rendered) {
                    o.hidePopup();
                }
            });
        }
    },
    getClass: function () {
        var s = '';
        if (this.icon) {
            if (this.text) {
                s += ' e-btn-icontext';
                s += ' e-btn-icon-align-' + this.iconAlign;
            }
            else s += ' e-btn-icon';
        }
        this.getArrowCls();
        s += ' ' + this.arrowCls;


        if (this.pressed) s += ' ' + this.togglCls;

        return s;
    },
    htmlType: 'a',
    getInnerHtml: function (sb) {
        //if(this.text == '取消') debugger 
        if (this.simpleButton) return this.text || '';

        this.elCls += this.getClass();
        var w = Edo.isValue(this.realWidth) ? this.realWidth + 'px' : 'auto';
        var h = Edo.isValue(this.realHeight) ? this.realHeight + 'px' : 'auto';

        var text = this.text || '&nbsp;';
        sb[sb.length] = '<span class="e-btn-text ' + this.icon + '">' + text + '</span>';
        if (this.arrowMode) {
            sb[sb.length] = '<span class="e-btn-arrow"></span>';
        }
    },
    createChildren: function (el) {
        Edo.controls.Button.superclass.createChildren.call(this, el);

        if (!this.design) {
            this.el.href = 'javascript:;';
        }
        this.el.hideFocus = true;

        if (this.simpleButton) return '';

        this.field = this.el.firstChild;
        if (this.splitMode == 'split') this.splitEl = this.el.lastChild;


        //this.el.onmousedown = 'return false';
        //this.el.href = '#';

    },
    initEvents: function () {
        if (!this.design) {
            var el = this.el;
            //if(this.simpleButton) el = this.el;
            Edo.util.Dom.addClassOnClick(el, this.pressedCls);
            //Edo.util.Dom.addClassOnOver(el, this.overCls);

            this.on('click', this._onClick, this, 0);
            this.on('arrowclick', this._onArrowClick, this, 0);
        }

        Edo.controls.Button.superclass.initEvents.call(this);
    },
    _onClick: function (e) {
        e.stopDefault();
        if (this.enableToggle) {
            var pressed = !this.pressed;
            this._setPressed(pressed);
        }
        if (this.popupDisplayed) {
            this.hidePopup();
        } else if (this.showMenu && this.menu) {
            this.showPopup();
            this.menu = this.popupCt;

            //this._setPressed(true);
        }
    },
    _onArrowClick: function (e) {
        if (this.arrowMode == 'close' && this.autoClose) {
            this.destroy();
        }

        if (this.popupDisplayed) {
            this.hidePopup();
        } else if (this.arrowMode == 'split' && this.showMenu && this.menu) {
            this.showPopup();
            this.menu = this.popupCt;

            this._setPressed(true);
        }
    },
    getArrowCls: function () {
        var ac = this.arrowCls;
        this.arrowCls = '';
        if (this.arrowMode == 'menu') this.arrowCls = 'e-btn-arrow-' + this.arrowAlign;
        else if (this.arrowMode == 'split') this.arrowCls = 'e-btn-split-' + this.arrowAlign;
        else if (this.arrowMode == 'close') this.arrowCls = 'e-btn-close-' + this.arrowAlign;
        return this.arrowCls;
    },
    _setArrowMode: function (value) {
        if (this.arrowMode != value) {
            this.arrowMode = value;
            this.getArrowCls();
        }
    },
    _setArrowAlign: function (value) {
        if (this.arrowAlign != value) {
            this.arrowAlign = value;
            this.getArrowCls();
        }
    },
    _setMenu: function (value) {
        if (this.popupCt) this.popupCt.parent.remove(this.popupCt);
        if (value instanceof Array) value = {
            type: 'menu',
            shadow: this.popupShadow,
            autoHide: true,
            children: value
        }
        this.menu = this.popupCt = value;

        this.createPopup();
    },

    _setOverCls: function (value) {
        if (this.overCls != value) {
            var old = this.overCls;
            this.overCls = value;
            if (this.el) {
                Edo.util.Dom.removeClass(this.el, old);
                this.el.overCls = value;
            }
            this.changeProperty('overcls', value);
        }
    },
    _setPressedCls: function (value) {
        if (this.pressedCls != value) {
            var old = this.pressedCls;
            this.pressedCls = value;
            if (this.el) {
                Edo.util.Dom.removeClass(this.el, old);
                this.el.clickCls = value;
            }
            this.changeProperty('pressedcls', value);
        }
    },

    _setWidth: function (w) {
        if (!Edo.isInt(w) && this.width != w) {
            this.widthGeted = false; //重新获得
        }
        Edo.controls.Button.superclass._setWidth.call(this, w);
    },
    addCls: function (cls) {
        Edo.controls.Button.superclass.addCls.call(this, cls);
        if (!Edo.isInt(this.width)) {
            this.widthGeted = false; //重新获得                        
            if (this.el) {
                this.el.style.width = 'auto';
            }
        }
    },
    removeCls: function (cls) {
        Edo.controls.Button.superclass.removeCls.call(this, cls);
        if (!Edo.isInt(this.width)) {
            this.widthGeted = false; //重新获得
            if (this.el) {
                this.el.style.width = 'auto';
            }
        }

    },
    _setText: function (value) {
        if (this.text != value) {
            this.text = value;
            if (!this.simpleButton) {
                if (this.el) {
                    this.field.innerHTML = value;
                    //this.field.textContent = value;
                }
            } else {

            }
            if (!Edo.isInt(this.width)) {
                this.widthGeted = false; //重新获得
                if (this.el) {
                    this.el.style.width = 'auto';
                    //Edo.util.Dom.repaint(this.el);
                }
            }
            this.changeProperty('text', value);
            if (!Edo.isInt(this.width)) {
                this.relayout('text', value);
            }
        }
    },
    _setIcon: function (value) {
        if (this.icon != value) {
            if (this.el) {
                Edo.util.Dom.removeClass(this.field, this.icon);
                Edo.util.Dom.addClass(this.field, value);
            }
            this.icon = value;
        }
    },
    _setPressed: function (value) {
        value = Edo.toBool(value);
        if (this.pressed != value) {
            this.pressed = Edo.toBool(value);
            if (this.el) {
                if (this.pressed) {
                    Edo.util.Dom.addClass(this.el, this.togglCls);
                } else {
                    Edo.util.Dom.removeClass(this.el, this.togglCls);
                }
            }
            this.fireEvent('toggle', {
                type: 'toggle',
                source: this,
                pressed: value
            });

            //            if(!Edo.isInt(this.width)){
            //                this.widthGeted = false;//重新获得
            //                if(this.table) {                    
            //                    this.table.style.width = 'auto';
            //                    Edo.util.Dom.repaint(this.table);
            //                }
            //            }
            //            this.changeProperty('pressed', value);
            //            if(!Edo.isInt(this.width)){
            //                this.relayout('pressed', value);
            //            }
        }
    },
    syncSize: function () {
        Edo.controls.Button.superclass.syncSize.call(this);
        //        this.el.style.visibility = 'visible';
        //        alert(this.realHeight);
        if (this.field) {
            var tb = Edo.util.Dom.getPadding(this.field, "tb") + 1;
            //if(isIE) tb += 1;
            this.field.style.lineHeight = (this.realHeight - tb) + 'px';
        }
    },
    destroy: function () {

        Edo.util.Dom.clearEvent(this.field);
        Edo.controls.Button.superclass.destroy.call(this);
    }
});
Edo.controls.Button.regType('button');

Edo.controls.Error = function () {
    Edo.controls.Error.superclass.constructor.call(this);

};
Edo.controls.Error.extend(Edo.controls.Control, {

    text: '',

    autoWidth: true,

    autoHeight: true,
    elCls: 'e-error e-div',
    minHeight: 16,

    visible: false,
    getInnerHtml: function (sb) {
        sb[sb.length] = '<div class="e-error-inner">' + this.text + '</div><a class="e-error-close"></a>';
    },
    init: function () {
        Edo.controls.Error.superclass.init.call(this);

        this.on('click', function (e) {
            var id = '';
            if (Edo.util.Dom.hasClass(e.target, 'e-error-link')) {
                id = e.target.id;
            }
            if (Edo.util.Dom.hasClass(e.target, 'e-error-close')) {
                this._onValid();
                id = this.fields[0] ? this.fields[0].field.id + '-error' : '';
            }

            id = id.substr(0, id.length - 6);
            var target = Edo.get(id);
            if (!target) return;
            target.focus();

        }, this);
    },
    _setText: function (value) {
        if (this.text !== value) {
            this.text = value;
            if (this.el) {
                this.el.firstChild.innerHTML = value;
                //this.el.style.width = 'auto';
            }
            if (!Edo.isInt(this.width)) {
                this.widthGeted = false;
                //                this.el.style.width = 'auto';
                //                Edo.util.Dom.repaint(this.el);
            }
            if (!Edo.isInt(this.height)) {
                this.heightGeted = false;
            }
            //this.changeProperty('text', value);
            this.relayout('text', value);
        }
    },
    bind: function (forId) {
        this.unbind(forId);
        var target = Edo.get(forId);
        if (!target) return;
        target.on('valid', this._onValid, this);
        target.on('invalid', this._onInValid, this);
    },
    unbind: function (forId) {
        var target = Edo.get(forId);
        if (!target) return;
        target.un('valid', this._onValid, this);
        target.un('invalid', this._onInValid, this);
    },
    _onValid: function (e) {

        this.set('visible', false);
        this.set('text', '');
    },
    _onInValid: function (e) {

        this.fields = [];
        if (e.fields) {
            this.fields = e.fields;
        } else {
            this.fields.add({
                field: e.source,
                errorMsg: e.errorMsg
            });
        }
        var errorMsg = '';

        this.fields.each(function (field) {
            errorMsg += '<a id="' + field.field.id + '-error" class="e-error-link" href="#" onclick="return false;">' + field.errorMsg + "</a>";
        });
        this.set('text', errorMsg);
        this.set('visible', true);
    }
});

Edo.controls.Error.regType('error');

Edo.controls.Slider = function () {



    Edo.controls.Slider.superclass.constructor.call(this);


};
Edo.controls.Slider.extend(Edo.controls.Control, {

    valueField: 'value',

    defaultValue: 0,

    value: 0,

    minValue: 0,

    maxValue: 100,
    //keyIncrement: 1,

    increment: 0,

    defaultWidth: 100,

    defaultHeight: 22,

    minHeight: 22,

    minWidth: 30,

    direction: 'horizontal',    //vertical

    thumbCls: 'e-slider-thumb',
    thumbOverCls: 'e-slider-thumb-over',
    thumbPressedClass: 'e-slider-thumb-pressed',

    elCls: 'e-slider',
    getInnerHtml: function (sb) {
        if (this.direction == 'vertical') {
            this.elCls += ' e-slider-v';
        }
        sb[sb.length] = '<div class="e-slider-start"><div class="e-slider-end"><div class="e-slider-inner" style="';
        if (this.direction == 'vertical') {
            sb[sb.length] = 'height:';
            sb[sb.length] = this.realHeight - 14;
            sb[sb.length] = 'px;';
        }
        sb[sb.length] = '"><div class="';
        sb[sb.length] = this.thumbCls;
        sb[sb.length] = '"></div></div></div></div>';
    },
    createChildren: function (el) {
        Edo.controls.Slider.superclass.createChildren.call(this, el);
        this.startEl = this.el.firstChild;
        this.endEl = this.startEl.firstChild;
        this.inner = this.endEl.lastChild;
        this.thumb = this.inner.lastChild;
        this.halfThumb = 15 / 2;

        this.thumbDrag = new Edo.util.Drag({
            //onStart: this.onThumbDragStart.bind(this),
            onMove: this._onThumbDragMove.bind(this),
            onStop: this._onThumbDragComplete.bind(this)
        });
    },
    initEvents: function () {
        if (!this.design) {
            this.on('mousedown', this._onMouseDown);
            Edo.util.Dom.addClassOnClick(this.thumb, this.thumbPressedClass);
            Edo.util.Dom.addClassOnOver(this.thumb, this.thumbOverCls);
        }
        Edo.controls.Slider.superclass.initEvents.call(this);

    },

    syncSize: function () {
        //this.realWidth -= 7;
        Edo.controls.Slider.superclass.syncSize.call(this);

        if (this.mustSyncSize !== false) {
            if (this.direction == 'vertical') {
                //this.inner.style.height = (this.realHeight - 14) + 'px';//偏移
                //Edo.util.Dom.setHeight(this.el, this.realHeight);
            } else {
                //Edo.util.Dom.setWidth(this.el, this.realWidth);
            }
        }
        //do value  :   以后会发展成为一个模式set...
        var v = this.normalizeValue(this.value);
        if (!isNaN(v)) {
            this.value = v;
            this.moveThumb(this.translateValue(this.value));

        }

        if (this.direction == 'vertical') {
            this.inner.style.height = (this.realHeight - 14) + 'px';
        }
    },
    _onMouseDown: function (e) {
        if (e.button == Edo.util.MouseButton.left) {
            this.ctBox = Edo.util.Dom.getBox(this.inner);
            if (e.within(this.thumb)) {
                this.initXY = Edo.util.Dom.getXY(this.thumb);
                this.xOffset = this.initXY[0] - e.xy[0];
                this.yOffset = this.initXY[1] - e.xy[1];

                this.thumbDrag.start(e);

                this.fireEvent('slidestart', {
                    type: 'slidestart',
                    source: this,
                    xy: Edo.util.Dom.getXY(this.thumb)
                });
            } else {
                //点击thumb之外
                //var pos = (drag.now[index] + offset) - t;
                var index = this.direction == 'horizontal' ? 0 : 1;
                var t = this.direction == 'horizontal' ? this.ctBox.x : this.ctBox.y;
                var pos = e.xy[index] - t;
                this.anim = true;
                this._setValue(Math.round(this.reverseValue(pos)));
                this.anim = false;
            }
        }
    },
    //    onThumbDragStart: function(e){
    //        this.fireEvent('slidestart', {
    //            type: 'slidestart',
    //            source: this,
    //            xy: [e.now[0]+this.xOffset, e.now[1]+this.yOffset]
    //        });
    //    },
    _onThumbDragMove: function (drag) {
        var xy = this.initXY;

        var index = this.direction == 'horizontal' ? 0 : 1;
        var offset = this.direction == 'horizontal' ? this.xOffset : this.yOffset;
        var t = this.direction == 'horizontal' ? this.ctBox.x : this.ctBox.y;



        var pos = (drag.now[index] + offset) - t;
        this._setValue(Math.round(this.reverseValue(pos)));


        xy = drag.now;
        xy[index] = t + this.translateValue(this.value);
        this.fireEvent('slide', {
            type: 'slide',
            source: this,
            xy: xy
        });
    },
    _onThumbDragComplete: function (e) {
        this.fireEvent('slidecomplete', {
            type: 'slidecomplete',
            source: this,
            xy: [e.now[0] + this.xOffset, e.now[1] + this.yOffset]
        });
    },
    _setValue: function (v) {
        v = this.normalizeValue(v);
        if (v !== this.value && this.fireEvent('beforevaluechange', {
            type: 'beforevaluechange',
            source: this,
            value: v
        }) !== false) {

            this.value = v;
            if (this.el) {
                this.moveThumb(this.translateValue(v));
            }
            //            this.fireEvent('valuechange', {
            //                type: 'valuechange',
            //                source: this,
            //                value: v
            //            });

            this.changeProperty('value', this.value);
        }
    },
    //将value转换为坐标
    translateValue: function (v) {
        var ratio = this.getRatio();
        return (v * ratio) - (this.minValue * ratio) - this.halfThumb;
    },
    //将坐标转换为值
    reverseValue: function (pos) {
        var ratio = this.getRatio();
        return (pos + this.halfThumb + (this.minValue * ratio)) / ratio;
    },
    getRatio: function () {
        var fn = this.direction == 'horizontal' ? 'getWidth' : 'getHeight';
        var now = Edo.util.Dom[fn](this.inner);

        var v = this.maxValue - this.minValue;
        return v == 0 ? now : (now / v);
    },
    moveThumb: function (pos) {
        if (this.thumb) {
            var p = this.direction == 'horizontal' ? 'left' : 'top';
            if (this.anim) {
                var o = {};
                o[p] = pos;
                new Edo.util.Fx.Style({
                    el: this.thumb,
                    duration: 300
                }).start(o);
            } else {
                this.thumb.style[p] = pos + 'px';
            }
        }
    },
    //确保值的有效性
    normalizeValue: function (v) {
        if (typeof v != 'number') {
            v = parseInt(v);
        }
        if (isNaN(v)) v = this.minValue;
        v = Math.round(v);
        v = this.doSnap(v); //v = Edo_controls_Slider__doSnap.call(this, v);    => v = AAA.call(this.v);
        v = v.constrain(this.minValue, this.maxValue);
        return v;
    },
    //根据increment增值,以及当前的value,得到一个新的有效value
    doSnap: function (value) {
        if (!this.increment || this.increment == 1 || !value) {
            return value;
        }
        var newValue = value, inc = this.increment;
        var m = value % inc;
        if (m > 0) {
            if (m > (inc / 2)) {
                newValue = value + (inc - m);
            } else {
                newValue = value - m;
            }
        }
        return newValue.constrain(this.minValue, this.maxValue);
    },
    destroy: function () {
        Edo.util.Dom.clearEvent(this.thumb);
        this.thumb = null;

        Edo.controls.Slider.superclass.destroy.call(this);
    }

});
Edo.controls.Slider.regType('slider');



Edo.controls.VSlider = function () {
    Edo.controls.VSlider.superclass.constructor.call(this);
};
Edo.controls.VSlider.extend(Edo.controls.Slider, {

    direction: 'vertical',

    minWidth: 22,

    minHeight: 30,

    defaultHeight: 100,

    defaultWidth: 22
});
Edo.controls.VSlider.regType('vslider');

Edo.controls.ScrollBar = function () {
    Edo.controls.ScrollBar.superclass.constructor.call(this);



};
Edo.controls.ScrollBar.extend(Edo.controls.Control, {

    valueField: 'value',

    defaultValue: 0,

    minWidth: 17,

    minHeight: 17,

    defaultWidth: 100,

    defaultHeight: 17,

    upWidth: 17,
    upHeight: 17,
    downWidth: 17,
    downHeight: 17,

    maxValue: 0,

    value: 0,

    minHandleSize: 7,

    //up/down的增量

    incrementValue: 10,

    alternateIncrementValue: 30,

    spinTime: 20,

    scrollTime: 70,


    direction: 'horizontal',    //horizontal

    elCls: 'e-div e-scrollbar',

    upPressedCls: 'e-scrollbar-up-pressed',
    downPressedCls: 'e-scrollbar-down-pressed',
    handlePressedCls: 'e-scrollbar-handle-pressed',

    set: function (o, value) {
        if (!o) return;
        if (typeof o == 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }

        if (o.maxValue) {
            this._setMaxValue(o.maxValue);
            delete o.maxValue;
        }
        if (o.maxValue) {
            this._setMaxValue(o.maxValue);
            delete o.maxValue;
        }

        Edo.controls.ScrollBar.superclass.set.call(this, o);
    },

    getInnerHtml: function (sb) {
        if (this.direction == 'horizontal') {
            this.elCls += ' e-hscrollbar';
        }
        sb[sb.length] = '<div class="e-scrollbar-handle"><div class="e-scrollbar-handle-outer"></div><div class="e-scrollbar-handle-center"></div><div class="e-scrollbar-handle-inner"></div></div><div class="e-scrollbar-up"></div><div class="e-scrollbar-down"></div>';
    },
    createChildren: function (el) {
        Edo.controls.ScrollBar.superclass.createChildren.call(this, el);

        this.handle = this.el.firstChild;
        this.upBtn = this.el.childNodes[1];
        this.downBtn = this.el.lastChild;
    },
    initEvents: function () {
        if (!this.design) {
            this.on('mousedown', this._onMousedown, this);
            Edo.util.Dom.addClassOnClick(this.upBtn, this.upPressedCls);
            Edo.util.Dom.addClassOnClick(this.downBtn, this.downPressedCls);
            Edo.util.Dom.addClassOnClick(this.handle, this.handlePressedCls);


            Edo.util.Dom.addClassOnClick(this.el, '', this._stopSpin.bind(this));
        }

        Edo.controls.ScrollBar.superclass.initEvents.call(this);
    },
    syncSize: function () {
        Edo.controls.ScrollBar.superclass.syncSize.call(this);

        var handleSize = this.getHandleSize();
        if (this.direction == 'vertical') {
            Edo.util.Dom.setHeight(this.handle, handleSize);
        } else {
            Edo.util.Dom.setWidth(this.handle, handleSize);
        }

        this.syncScroll();
    },
    //获得handle的尺寸
    getHandleSize: function () {

        var size = 0;

        var btnSize,            //滚动条按钮尺寸
            size,               //滚动条尺寸
            max,                //滚动条最大尺寸
            handleSize;         //handle尺寸

        if (this.direction == 'vertical') {
            btnSize = this.upHeight + this.downHeight;
            size = this.realHeight;
            max = this.maxValue;
        } else {
            btnSize = this.upWidth + this.downWidth;
            size = this.realWidth;
            max = this.maxValue;
        }

        if (size == max + size) {
            handleSize = 0;
        } else {
            handleSize = Math.max(this.minHandleSize, Math.round((size - btnSize) * size / (max + size)))
        }

        return handleSize;
    },
    //获得handle的偏移定位
    getHandleOffset: function (handleSize) {
        var btnSize,            //滚动条按钮尺寸
            size,               //滚动条尺寸
            max,                //滚动条最大尺寸
            realOffset

        if (this.direction == 'vertical') {
            btnSize = this.upHeight + this.downHeight;
            size = this.realHeight;
            max = this.maxValue;

            realOffset = this.value;
        } else {
            btnSize = this.upWidth + this.downWidth;
            size = this.realWidth;
            max = this.maxValue;

            realOffset = this.value;
        }

        //获得 size - btnSize - handleSize的尺寸
        var moveSize = size - btnSize - handleSize;     //这个就是可移动的范围

        offset = realOffset / max * moveSize;
        return offset;
    },
    createvalue: function (offset, moveSize) {
        var value = offset * this.maxValue / moveSize
        return Math.floor(value);
    },
    createvalue: function (offset, moveSize) {
        var value = offset * this.maxValue / moveSize
        return Math.floor(value);
    },
    //获得可移动的区域
    getMoveReginBox: function () {
        var box = this.getBox();
        var size = this.getHandleSize();
        if (this.direction == 'vertical') {
            box.y += this.upHeight;
            box.height -= (this.upHeight + this.downHeight + size);
        } else {
            box.x += this.upWidth;
            box.width -= (this.upWidth + this.downWidth + size);
        }
        box.right = box.x + box.width;
        box.bottom = box.y + box.height;
        return box;
    },
    _onMousedown: function (e) {
        if (e.button != 0) return false;

        var t = e.target;

        var isVertical = this.direction == 'vertical';
        this._stopSpin();

        if (Edo.util.Dom.contains(this.handle, t)) {
            Edo.managers.DragManager.startDrag({
                event: e,
                capture: true,
                dragObject: this.handle,
                delay: 30,
                alpha: 1,
                enableDrop: false,
                ondragstart: this._onHandleDragStart.bind(this),
                ondragmove: this._onHandleDragMove.bind(this),
                ondragcomplete: this._onHandleDragComplete.bind(this)
            });
            this.dragTimer = new Date();
        } else if (Edo.util.Dom.contains(this.upBtn, t)) {     //up
            this._startSpin('up', this.incrementValue, this.spinTime);
            this.canFireComplete = true;

        } else if (Edo.util.Dom.contains(this.downBtn, t)) {   //down
            this._startSpin('down', this.incrementValue, this.spinTime);
            this.canFireComplete = true;
        } else {

            var moveBox = this.getMoveReginBox();
            var handleSie = this.getHandleSize();
            if (isVertical) moveBox.height += this.downHeight + handleSie;
            else moveBox.width += this.downWidth + handleSie;

            var moveBox2 = this.getMoveReginBox();

            var handleXY = Edo.util.Dom.getXY(this.handle);

            if (Edo.util.Dom.isInRegin(e.xy, moveBox)) {

                if (isVertical) {

                    this.stopOffset = this.createvalue(Math.abs(moveBox2.y - e.xy[1]), moveBox2.height);
                    if (e.xy[1] < handleXY[1]) {
                        this._startSpin('up', this.alternateIncrementValue, this.spinTime);
                    } else {
                        this._startSpin('down', this.alternateIncrementValue, this.spinTime);
                    }
                } else {
                    this.stopOffset = this.createvalue(Math.abs(moveBox2.x - e.xy[0]), moveBox2.width);

                    if (e.xy[0] < handleXY[0]) {
                        this._startSpin('up', this.alternateIncrementValue, this.spinTime);
                    } else {
                        this._startSpin('down', this.alternateIncrementValue, this.spinTime);
                    }
                }
            }
            //alert(this.stopOffset);
            this.canFireComplete = true;
        }

    },
    doSpin: function (spinDirection, increment) {

        var isVertical = this.direction == 'vertical';
        if (spinDirection == 'up') {
            if (isVertical) {
                var v = this.value - increment;
                if (Edo.isValue(this.stopOffset) && v < this.stopOffset) {
                    return;
                }
                this._setValue(v);
            } else {
                var v = this.value - increment;
                if (Edo.isValue(this.stopOffset) && v < this.stopOffset) {
                    return;
                }
                this._setValue(v);
            }
        } else {
            if (isVertical) {
                var v = this.value + increment;

                if (Edo.isValue(this.stopOffset) && v > this.stopOffset) {
                    return;
                }
                this._setValue(v);
            } else {
                var v = this.value + increment;
                if (Edo.isValue(this.stopOffset) && v > this.stopOffset) {
                    return;
                }
                this._setValue(v);
            }
        }
    },
    _startSpin: function (spinDirection, increment, spinTime) {
        this.spinDirection = spinDirection;

        this.spinTimer = this.doSpin.time(spinTime, this, [spinDirection, increment], true);
    },
    _stopSpin: function () {
        clearInterval(this.spinTimer);
        this.spinTimer = null;
        this.stopOffset = null;
        //这里也应该激发scrollComplete事件

        if (this.canFireComplete) {
            this.completeScroll();
            this.canFireComplete = false;
        }
    },

    _onHandleDragStart: function (e) {
        //得到可拖拽的box区域, 将拖拽范围限定        
        this.moveBox = this.getMoveReginBox();

        this.enableSyncScroll = false;

        if (!isIE8) {
            var sf = this;
            this.scrollTimer = setInterval(function () {
                if (sf.direction == 'vertical') {
                    sf._setValue(sf._value);
                } else {
                    sf._setValue(sf._value);
                }
            }, this.scrollTime);
        }

        this.fireEvent('scrollstart', {
            type: 'scrollstart',
            source: this,
            direction: this.direction,
            value: this.value,
            maxValue: this.maxValue
        });
    },
    _onHandleDragMove: function (e) {

        var dm = e.source;
        var moveBox = this.moveBox;

        var isVertical = this.direction == 'vertical';

        if (isVertical) {
            e.xy[0] = moveBox.x;

            if (e.xy[1] < moveBox.y) e.xy[1] = moveBox.y;
            if (e.xy[1] > moveBox.bottom) e.xy[1] = moveBox.bottom;

            //先设置一个_value属性, 用来后续的设置
            this._value = this.createvalue(e.xy[1] - moveBox.y, moveBox.height);
        } else {
            e.xy[1] = moveBox.y;

            if (e.xy[0] < moveBox.x) e.xy[0] = moveBox.x;
            if (e.xy[0] > moveBox.right) e.xy[0] = moveBox.right;

            this._value = this.createvalue(e.xy[0] - moveBox.x, moveBox.width);
        }
        if (isIE8) {
            if (this.dragTimer) {
                var d = new Date();
                if (d - this.dragTimer >= this.scrollTime) {
                    this.dragTimer = d;
                    this._setValue(this._value);
                }
            }
        }

    },
    _onHandleDragComplete: function (e) {

        this.enableSyncScroll = true;

        clearInterval(this.scrollTimer);

        if (Edo.isValue(this._value)) {
            this._setValue(this._value);
        }

        this._value = null;

        this.completeScroll();

    },
    completeScroll: function () {
        this.fireEvent('scrollcomplete', {
            type: 'scrollcomplete',
            source: this,
            direction: this.direction,
            value: this.value
        });
    },
    _setValue: function (value) {

        if (!Edo.isValue(value)) return;
        if (value < 0) value = 0;
        if (value > this.maxValue) value = this.maxValue;
        if (this.value != value) {
            this.value = value;
            this.syncScroll();

            this.fireEvent('scroll', {
                type: 'scroll',
                source: this,
                direction: this.direction,
                value: this.value
            });
        }
    },
    _setMaxValue: function (value) {
        if (value < 0) value = 0;
        if (this.maxValue != value) {
            this.maxValue = value;
            this.relayout('maxValue');
        }
    },
    //根据maxValue,maxValue, value, value,将handle定位
    syncScroll: function () {
        if (!this.el || this.enableSyncScroll == false) return;
        //根据当前的value/value,进行定位
        var handleSize = this.getHandleSize();
        if (handleSize != 0) {
            var handleOffset = this.getHandleOffset(handleSize);

            var offset = handleOffset + (this.direction == 'vertical' ? this.upHeight : this.upWidth);
            if (this.direction == 'vertical') {
                this.handle.style.top = offset + 'px';
            } else {
                this.handle.style.left = offset + 'px';
            }
        }
    }

});
Edo.controls.ScrollBar.regType('scrollbar');

Edo.controls.VScrollBar = function () {
    Edo.controls.VScrollBar.superclass.constructor.call(this);
};
Edo.controls.VScrollBar.extend(Edo.controls.ScrollBar, {

    defaultWidth: 17,

    defaultHeight: 100,

    direction: 'vertical'
});

Edo.controls.VScrollBar.regType('vscrollbar');

Edo.controls.CheckBox = function (config) {

    Edo.controls.CheckBox.superclass.constructor.call(this);
};
Edo.controls.CheckBox.extend(Edo.controls.Control, {

    valueField: 'checked',

    defaultValue: false,

    autoWidth: true,


    minWidth: 20,

    text: '',

    checked: false,
    trueValue: true,
    falseValue: false,
    _getChecked: function () {
        return this.checked == true ? this.trueValue : this.falseValue;
    },
    set: function (o, value) {
        if (!o) return;

        if (typeof o === 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }

        var checked = o.checked;
        delete o.checked;

        Edo.controls.CheckBox.superclass.set.call(this, o);

        if (checked !== undefined) {
            this._setChecked(checked);
        }
        return this;
    },

    elCls: 'e-checkbox',
    checkedCls: 'e-checked',
    overcheckedCls: 'e-checked-over',
    pressedcheckedCls: 'e-checked-pressed',

    getInnerHtml: function (sb) {
        if (this.checked) this.elCls += ' ' + this.checkedCls;
        sb[sb.length] = '<div class="e-checked-icon"><div class="e-checked-icon-inner"></div></div><div class="e-checked-text">';
        sb[sb.length] = this.text;
        sb[sb.length] = '</div>';
    },
    createChildren: function (el) {

        Edo.controls.CheckBox.superclass.createChildren.call(this, el);
        this.iconEl = this.el.firstChild;
        this.textEl = this.el.lastChild;
    },
    initEvents: function () {
        if (!this.design) {
            var el = this.el;

            Edo.util.Dom.addClassOnOver(el, this.overcheckedCls);
            Edo.util.Dom.addClassOnClick(el, this.pressedcheckedCls);

            this.on('click', this._onClick, this);
        }
        Edo.controls.CheckBox.superclass.initEvents.call(this);
    },
    _onClick: function (e) {
        if (!this.enable) return;

        var checked = this.checked === this.trueValue ? this.falseValue : this.trueValue
        this._setChecked(checked);
    },
    _setText: function (value) {
        if (this.text !== value) {
            this.text = value;
            if (this.el) {
                this.textEl.innerHTML = this.text;
            }
            if (!Edo.isInt(this.width)) {
                this.widthGeted = false;
            }
            this.changeProperty('text', value);
            this.relayout('text', value);
        }
    },
    _setChecked: function (value) {

        //        var v;
        //        try{
        //            v = Edo.toBool(value);
        //        }catch(e){
        //            v = value == this.trueValue ? true : false;
        //        }        

        if (this.checked !== value) {
            this.checked = value === this.trueValue ? this.trueValue : this.falseValue;
            if (this.el) {
                if (this.checked === this.trueValue) {
                    Edo.util.Dom.addClass(this.el, this.checkedCls);
                } else {
                    Edo.util.Dom.removeClass(this.el, this.checkedCls);
                }
            }
            this.fireEvent('checkedchange', {
                type: 'checkedchange',
                source: this,
                checked: this.checked
            });
            this.changeProperty('checked', this.checked);
        }
    }
});

Edo.controls.CheckBox.regType('checkbox', 'check');

Edo.controls.Radio = function () {

    Edo.controls.Radio.superclass.constructor.call(this);
};
Edo.controls.Radio.extend(Edo.controls.CheckBox, {
    elCls: 'e-radiobox',
    _onClick: function (e) {
        if (!this.enable) return;
        if (this.checked === true) return;
        Edo.controls.Radio.superclass._onClick.call(this, e);
        //如果本控件有name,则找出所有同name的组件,如果另外的组件也是radio,则将其他的设置为false
        if (this.name) {
            var os = Edo.managers.SystemManager.getByName(this.name);
            os.each(function (o) {
                if (o != this && o.isType('radio')) o._setChecked(false);
            }, this);
        }
    }
});

Edo.controls.Radio.regType('radio');


Edo.controls.TextInput = function () {
    Edo.controls.TextInput.superclass.constructor.call(this);





};
Edo.controls.TextInput.extend(Edo.controls.Control, {
    _htmltype: 'text',
    _mode: 'text',


    maxLength: 1000,

    lineHeight: true,   //true(跟realHeight一样), false,不设置, 具体值30...


    defaultWidth: 100,

    minWidth: 20,

    defaultHeight: 21,
    minHeight: 20,

    text: '',

    readOnly: false,


    selectOnFocus: false,

    textStyle: '',
    textCls: '',

    elCls: 'e-text',


    emptyText: '',

    emptyCls: 'e-text-empty',

    //disabledClass : "",
    focusClass: 'e-text-focus',

    //激发el.onchange

    changeAction: 'change',

    focus: function () {
        //if(this.field) this.field.focus();
        var el = this.field;
        if (this.blurTimer) {
            clearTimeout(this.blurTimer);
            this.blurTimer = null;
        }
        setTimeout(function () {
            Edo.util.Dom.focus(el);
        }, 100);
    },
    blur: function () {
        var sf = this;
        this.blurTimer = setTimeout(function () {
            Edo.util.Dom.blur(sf.field);
        }, 100);
        if (this.field) this.field.blur();
    },
    getInnerHtml: function (sb) {

        var w = this.getFieldWidth() - 8; //8是 (1border + 3padding) * 2        
        var h = lh = this.realHeight - 4; //4是 (1border + 1padding) * 2

        if (this.lineHeight && this.lineHeight !== true) {
            lh = this.lineHeight;
        }

        var text = this.text;
        //        var text = Edo.isValue(this.text) ? this.text : this.emptyText;
        //        this.elCls += ' ' + (text === this.emptyText ? this.emptyCls : '');

        if (this._mode == 'text') {
            sb[sb.length] = '<input maxlength="' + this.maxLength + '" type="' + this._htmltype + '" ';
        } else {
            sb[sb.length] = '<textarea ';
        }

        //if(!this.isEnable()) sb[sb.length] = ' disabled="disabled" '; 
        //if(!this.enable) sb[sb.length] = ' disabled="disabled" '; 
        sb[sb.length] = ' autocomplete="off" class="e-text-field" style="height:';
        sb[sb.length] = h;
        sb[sb.length] = 'px;line-height:';
        sb[sb.length] = lh;
        sb[sb.length] = 'px;width:';
        sb[sb.length] = w;
        sb[sb.length] = 'px;';
        sb[sb.length] = this.textStyle;
        sb[sb.length] = '" ';

        if (this.readOnly) {
            sb[sb.length] = ' readonly="true" ';
            this.elCls += ' e-text-readonly ';
        }

        if (this._mode == 'text') {
            sb[sb.length] = ' value="';
            sb[sb.length] = text;
            sb[sb.length] = '" />';
        } else {
            sb[sb.length] = '>';
            sb[sb.length] = text;
            sb[sb.length] = '</textarea>';
        }
    },
    createChildren: function (el) {
        Edo.controls.TextInput.superclass.createChildren.call(this, el);
        this.field = this.el.firstChild;
    },
    initEvents: function () {
        Edo.controls.TextInput.superclass.initEvents.call(this);
        var de = Edo.util.Dom;
        var f = this.field;
        de.on(f, 'blur', this._onBlur, this);
        de.on(f, 'focus', this._onFocus, this);


        de.on(f, this.changeAction, this._onTextChange, this);
        //是否及时change?         
    },
    _onBlur: function (e) {
        if (this.within(e)) {
            return;
        }

        var sf = this;
        this.blurTimer = setTimeout(function () {
            Edo.util.Dom.removeClass(sf.el, sf.focusClass);
        }, 10);

        e.type = 'textblur';
        e.target = this;
        this.fireEvent('textblur', e);

        e.type = 'blur';
        this.fireEvent('blur', e);

        //        if(this.field){
        //            if(this.field.value === this.emptyText){
        //                this.field.value = '';
        //                Edo.util.Dom.addClass(this.el, this.emptyCls);
        //            }else{
        //                Edo.util.Dom.removeClass(this.el, this.emptyCls);
        //            }
        //            
        //        }
    },
    _onFocus: function (e) {
        e.stopDefault();

        //        if(this.blurTimer) {
        //            clearTimeout(this.blurTimer);
        //            this.blurTimer = null;
        //        }

        //Edo.util.Dom.addClass(this.el, this.focusClass);  

        if (this.isReadOnly()) {
            if (this.field) Edo.util.Dom.blur(this.field);
            this.blur();
            return;
        }

        var sf = this;
        setTimeout(function () {
            Edo.util.Dom.addClass(sf.el, sf.focusClass);
        }, 1);
        function _() {
            if (this.field) this.field.select();
        }
        if (this.selectOnFocus) {
            _.defer(20, this);
        }

        e.type = 'textfocus';
        e.target = this;
        this.fireEvent('textfocus', e);

        e.type = 'focus';
        this.fireEvent('focus', e);

        //        if(this.field){
        //            if(this.field.value === this.emptyText){
        //                this.field.value = '';
        //                Edo.util.Dom.addClass(this.el, this.emptyCls);
        //            }else{
        //                Edo.util.Dom.removeClass(this.el, this.emptyCls);
        //            }
        //        }
    },
    _onTextChange: function (e) {
        this._setText(this.field.value);
    },
    _setEnable: function (value) {
        Edo.controls.TextInput.superclass._setEnable.call(this, value);
        if (this.field) {
            this.field.disabled = !this.isEnable();
        }
    },
    _setText: function (value) {
        if (!Edo.isValue(value)) value = '';

        if (this.text !== value) {
            if (this.fireEvent('beforetextchange', {
                type: 'beforetextchange',
                source: this,
                text: value
            }) === false) {
                if (this.el) this.field.value = this.text;
                return;
            }

            this.text = value;      //text就是传递进来的值
            if (this.el) {
                //                if(!Edo.isValue(value)) {   //如果为空,则使用emptyText显示
                //                    value = this.emptyText;                                        
                //                }
                this.field.value = value;
                //                if(value === this.emptyText){
                //                    Edo.util.Dom.addClass(this.el, this.emptyCls);
                //                }else{
                //                    Edo.util.Dom.removeClass(this.el, this.emptyCls);
                //                }
            }

            this.changeProperty('text', value);

            this.fireEvent('textchange', {
                type: 'textchange',
                source: this,
                text: this.text
            });
        }
    },
    _setReadOnly: function (value) {
        if (this.readOnly != value) {
            this.readOnly = value;
            if (this.el) {
                this.field.readOnly = value;
                if (value) {
                    Edo.util.Dom.addClass(this.el, 'e-text-readonly');
                } else {
                    Edo.util.Dom.removeClass(this.el, 'e-text-readonly');
                }
            }
        }
    },
    //
    getFieldWidth: function () {
        var w = this.realWidth;
        //        if(isSafari) {
        //            w -= 8;
        //        }
        return w;
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.TextInput.superclass.syncSize.call(this);

        var w = this.getFieldWidth(), h = this.realHeight, f = this.field;
        Edo.util.Dom.setSize(f, w, h - 2);
        //line-height
        if (this.lineHeight) {
            var h = this.lineHeight === true ? (h - 4) : this.lineHeight;
            f.style.lineHeight = h + 'px';
        }
    },
    destroy: function () {
        Edo.util.Dom.clearEvent(this.field);
        Edo.util.Dom.remove(this.field);
        this.field = null;
        Edo.controls.TextInput.superclass.destroy.call(this);
    },
    //---------------验证
    clearInvalid: function () {
        Edo.controls.TextInput.superclass.clearInvalid.call(this);
        if (this.el) Edo.util.Dom.removeClass(this.el, 'e-form-invalid');
    },
    showInvalid: function (msg) {
        Edo.controls.TextInput.superclass.showInvalid.call(this, msg);
        if (this.showValid) {
            if (this.el) Edo.util.Dom.addClass(this.el, 'e-form-invalid');
            else this.cls += ' e-form-invalid';
        }
    }
    //    ,   
    //    //---------------编辑    
    //    startEdit: function(data, x, y, w, h){
    //        Edo.controls.TextInput.superclass.startEdit.apply(this, arguments);
    //        //this.field.select();
    //    }
});

Edo.controls.TextInput.regType('textinput', 'text');

Edo.controls.Password = function () {

    Edo.controls.Password.superclass.constructor.call(this);
};
Edo.controls.Password.extend(Edo.controls.TextInput, {
    _htmltype: 'password'
});

Edo.controls.Password.regType('password');

Edo.controls.TextArea = function () {
    Edo.controls.TextArea.superclass.constructor.call(this);
}
Edo.controls.TextArea.extend(Edo.controls.TextInput, {
    _mode: 'textarea',
    lineHeight: 14,


    defaultHeight: 40
});
Edo.controls.TextArea.regType('textarea');



Edo.controls.Trigger = function () {




    Edo.controls.Trigger.superclass.constructor.call(this);
};
Edo.controls.Trigger.extend(Edo.controls.TextInput, {
    enableResizePopup: true,

    maxHeight: 500,

    triggerPopup: false,
    downShowPopup: true,
    triggerCls: 'e-trigger',
    triggerOverCls: 'e-trigger-over',
    triggerPressedCls: 'e-trigger-pressed',

    triggerVisible: true,

    initEvents: function () {
        Edo.controls.Trigger.superclass.initEvents.call(this);

        if (!this.design) {
            Edo.util.Dom.on(this.el, 'mousedown', this._onTriggerMousedown, this);
            var el = this.trigger;
            Edo.util.Dom.addClassOnClick(el, this.triggerPressedCls);
            Edo.util.Dom.addClassOnOver(el, this.triggerOverCls);

            this.on('keydown', this.__onKeyDown, this);
        }
    },
    __onKeyDown: function (e) {
        switch (e.keyCode) {
            case 40:
                if (!this.popupDisplayed && this.downShowPopup) this.showPopup();
                break;
            //        case 9: 
            //            this.hidePopup(); 
            //        break; 
        }
    },
    _onTriggerMousedown: function (e) {
        if (this.readOnly || e.within(this.trigger)) {
            this._onTrigger.defer(1, this, [e]);
        }
    },
    _onTrigger: function (e) {
        e.stopDefault();

        if (!this.isEnable()) return;
        if (this.parent && this.parent.isReadOnly()) {
            return;
        }
        if (e.button != Edo.util.MouseButton.left) return;

        e.type = 'beforetrigger';
        e.source = this;

        if (this.fireEvent('beforetrigger', e) === false) return;


        if (this.triggerPopup) {
            if (this.popupDisplayed) {
                this.hidePopup();
            } else {
                this.showPopup();
            }
        }
        //this.focus();
        this.focus.defer(50, this); //这行代码真恶心..
        //this.field.focus();
        //        var sf = this;
        //        setTimeout(function(){
        //            sf.field.focus();
        //        }, 100);
        e.type = 'trigger';
        this.fireEvent('trigger', {
            type: 'trigger',
            source: this
        });
    },
    //    setEnable: function(value){
    //        Edo.controls.Trigger.superclass.setEnable.call(this, value);
    //        if(this.trigger){
    //            if(this.isEnable()){
    //                Edo.util.Dom.removeClass(this.trigger, "e-disabled");
    //            }else{        
    //                Edo.util.Dom.addClass(this.trigger, "e-disabled");
    //            }
    //        }
    //    },
    getTriggerHtml: function () {
        return '<div class="e-trigger-icon"></div>';
    },
    getInnerHtml: function (sb) {
        Edo.controls.Trigger.superclass.getInnerHtml.call(this, sb);

        sb[sb.length] = '<div class="';

        sb[sb.length] = this.triggerCls;
        sb[sb.length] = '" style="display:';
        sb[sb.length] = this.triggerVisible ? 'block' : 'none';
        sb[sb.length] = ';height:';
        sb[sb.length] = this.realHeight - 1;
        sb[sb.length] = 'px;">';
        sb[sb.length] = this.getTriggerHtml();
        sb[sb.length] = '</div>';
    },
    createChildren: function (el) {
        Edo.controls.Trigger.superclass.createChildren.call(this, el);
        this.trigger = this.field.nextSibling;
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.Trigger.superclass.syncSize.call(this);

        if (this.triggerVisible) {
            this.trigger.style.display = 'block';
            Edo.util.Dom.setHeight(this.trigger, this.realHeight - 4);
        } else {
            this.trigger.style.display = 'none';
        }
    },
    triggerWidth: 19,
    getFieldWidth: function () {
        return this.realWidth - (this.triggerVisible ? this.triggerWidth : 0);
    },
    destroy: function () {
        Edo.util.Dom.clearEvent(this.trigger);
        Edo.util.Dom.remove(this.trigger);
        this.trigger = null;
        Edo.controls.Trigger.superclass.destroy.call(this);
    }
});

Edo.controls.Trigger.regType('trigger');

Edo.controls.Search = function (config) {


    Edo.controls.Search.superclass.constructor.call(this);

};
Edo.controls.Search.extend(Edo.controls.Trigger, {

    clearVisible: false,

    elCls: 'e-text e-search',

    clearTriggerCls: 'e-trigger e-trigger-clear e-close',
    clearTriggerOverCls: 'e-trigger-over',

    getFieldWidth: function () {
        return this.realWidth - (this.clearVisible ? this.triggerWidth * 2 : (this.triggerVisible ? this.triggerWidth : 0));
    },
    getInnerHtml: function (sb) {
        Edo.controls.Search.superclass.getInnerHtml.call(this, sb);

        sb[sb.length] = '<div class="';

        sb[sb.length] = this.clearTriggerCls;
        sb[sb.length] = '" style="display:' + (this.clearVisible ? 'block' : 'none') + ';right:' + this.triggerWidth + 'px;height:';
        sb[sb.length] = this.realHeight - 1;
        sb[sb.length] = 'px;">';
        sb[sb.length] = this.getTriggerHtml();
        sb[sb.length] = '</div>';
    },
    initEvents: function () {
        Edo.controls.Search.superclass.initEvents.call(this);

        if (!this.design) {
            Edo.util.Dom.on(this.clearTrigger, 'mousedown', this._onClearTrigger, this);
            Edo.util.Dom.addClassOnOver(this.clearTrigger, this.clearTriggerOverCls);
        }
    },
    createChildren: function (el) {
        Edo.controls.Search.superclass.createChildren.call(this, el);
        this.clearTrigger = this.el.lastChild;
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.Search.superclass.syncSize.call(this);
        this.clearTrigger.style.display = this.clearVisible ? 'block' : 'none';

        this.clearTrigger.style.height = (this.realHeight - 6) + 'px';

        var fieldwidth = this.getFieldWidth();
        if (this.clearVisible) {
            this.clearTrigger.style.left = fieldwidth + 'px';
        }
    },
    _setClearVisible: function (value) {
        if (this.clearVisible != value) {
            this.clearVisible = value;
            if (this.el) {
                this.syncSize();
            }
            this.changeProperty('clearVisible', value);
        }
    },
    _onClearTrigger: function (e) {
        if (!this.enable) return;
        if (e.button != Edo.util.MouseButton.left) return;
        this._setClearVisible(false);

        this.focus.defer(50, this);

        e.type = 'cleartrigger';
        e.source = this;
        this.fireEvent('cleartrigger', e);

    }

});
Edo.controls.Search.regType('search');

Edo.controls.ComboBox = function (config) {


    Edo.controls.ComboBox.superclass.constructor.call(this);

    //this.data = new Edo.data.DataTable();
    this._setData([]);

    this.selecteds = [];
};
Edo.controls.ComboBox.extend(Edo.controls.Trigger, {

    //textchangeSelect: false,

    triggerPopup: true,

    displayField: 'text',

    selectedIndex: -1,      //当前选中索引

    selectedItem: null,     //当前选中对象

    //data: null,

    popupWidth: '100%',

    autoSelect: true,
    selectHidePopup: true,


    tableConfig: null,

    set: function (o, value) {
        if (!o) return;

        if (typeof o === 'string') {
            var _ = o;
            o = {};
            o[_] = value;
        }

        var selectedIndex = o.selectedIndex;
        delete o.selectedIndex;

        Edo.controls.ComboBox.superclass.set.call(this, o);


        if (Edo.isValue(selectedIndex)) {
            this._setSelectedIndex(selectedIndex);
        }
        return this;
    },
    //    initEvents: function(){        
    //        Edo.controls.ComboBox.superclass.initEvents.call(this);
    //    },
    __onKeyDown: function (e) {

        this.text = this.field.value;

        if (this.popupDisplayed) {
            var row = this.table.getFocusItem();
            var rowIndex = this.table.data.indexOf(row);

            switch (e.keyCode) {
                case 37:
                    break;
                case 38:                //上
                    rowIndex -= 1;
                    if (rowIndex < 0) {

                    } else {
                        this.table.focusItem(rowIndex);
                    }
                    break;
                case 39:
                    break;
                case 40:                //下

                    rowIndex += 1;
                    if (rowIndex > this.table.data.getCount()) {

                    } else {
                        this.table.focusItem(rowIndex);
                    }
                    break;
                case 13:
                    var row = this.table.getFocusItem();

                    this.hidePopup();

                    this.table.select(row);
                    break;
                case 27:
                    this.hidePopup();
                    break;
                default:
                    this.data.filter(this.filterFn, this);

                    if (this.data.getCount() == 0) {
                        this.hidePopup();
                    } else {
                        this.showPopup();
                    }
                    break;
            }
        } else {
            switch (e.keyCode) {
                case 13:
                case 27:
                case 37:
                case 38:
                case 39:

                    break;

                case 40:                    //下
                    this.showPopup();

                    this.hoverDefaultRow();
                    break;
                default:
                    this.data.filter(this.filterFn, this);

                    if (this.data.getCount() == 0) {
                        this.hidePopup();
                    } else {
                        this.showPopup();
                    }
                    this.hoverDefaultRow();
                    break;
            }
        }

    },
    filterFn: function (o) {
        var text = String(o[this.displayField]);
        if (text.indexOf(this.text) != -1) {
            return true;
        } else {
            return false;
        }
    },
    _onTrigger: function (e) {
        Edo.controls.ComboBox.superclass._onTrigger.call(this, e);
        this.data.clearFilter();

        //this.hoverDefaultRow();
    },
    popupReset: false,

    showPopup: function () {
        if (this.popupReset) {
            this.resetValue();
        }
        Edo.controls.ComboBox.superclass.showPopup.call(this);

        //this.table.clearSelect();
        //this.table.select(this.selectedItem);   
    },
    hidePopup: function () {
        Edo.controls.ComboBox.superclass.hidePopup.call(this);
        this.data.clearFilter();
    },
    hoverDefaultRow: function () {
        var row = this.selectedItem;
        if (!row) {
            row = this.data.getAt(0);
        }
        if (this.table && row) {
            this.table.focusItem.defer(100, this.table, [row]);
        }
    },

    _setSelectedItem: function (value) {

        if (this.selectedItem != value) {

            var index = this.data.findIndex(value);

            this._setSelectedIndex(index);

            this.changeProperty('selectedItem', value, true);
        }
    },
    delimiter: ',',

    _setSelectedIndex: function (value) {

        var d = this.data;
        if (value < 0) {
            value = -1;
        }
        if (value >= this.data.getCount()) {
            value = this.data.getCount() - 1;
        }
        if (this.selectedIndex != value) {

            var sel = this.data.getAt(value);
            if (this.fireEvent('beforeselectionchange', {
                type: 'beforeselectionchange',
                source: this,
                selectedIndex: value,
                selectedItem: sel
            }) === false) return false;

            this.selectedIndex = value;
            this.selectedItem = sel;

            if (!this.multiSelect) {      //保留多选接口
                this.selecteds = [];
            }
            if (value != -1) {
                this.selecteds.add(sel);
            }

            var texts = [];

            this.selecteds.each(function (o) {
                texts.add(o[this.displayField]);
            }, this);

            this._setText(texts.join(this.delimiter));

            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this,
                selectedIndex: value,
                selectedItem: this.selectedItem
            });

            this.changeProperty('selectedIndex', value);

            return true;
        }
    },
    //    _setMultiSelect: function(value){
    //        value = Edo.toBool(value);
    //        if(this.multiSelect != value){
    //            this.multiSelect = value;
    //            if(this.table){
    //                this.table.set('rowSelectMode', this.multiSelect ? 'multi' : 'single');
    //            }
    //            this.changeProperty('multiSelect', value); 
    //        }
    //    },
    _setData: function (data) {
        if (typeof data === 'string') data = window[data];
        if (!data) return;
        if (data.componentMode != 'data') {
            if (!this.data) {
                data = new Edo.data.DataTable(data);
            } else {
                this.data.load(data);
                return;
            }
        }

        if (this.data) {
            this.data.un('datachange', this._onDataChange);
        }

        this.data = data;

        this.set('selectedIndex', -1);

        this.data.on('datachange', this._onDataChange, this);

        if (this.table) this.table.set('data', data);

        this.changeProperty('data', data);

    },
    _onSelection: function (e) {
        var table = e.source;

        if (this.canChange === false) return;
        //if(this.selectedItem == table.selected) return;

        if (this.autoSelect) {

            this.clearSelect();
            this._setSelectedItem(table.selected);

            if (this.selectHidePopup) {
                this.hidePopup();
            }

            //this.focus.defer(100, this);
            this.focus();
        }
    },
    clearSelect: function () {
        this.selectedItem = null;
        this.selectedIndex = -1;
    },
    createPopup: function () {
        Edo.controls.ComboBox.superclass.createPopup.apply(this, arguments);
        //        Edo.applyIf(this.tableConfig, {
        //            horizontalLine: false
        //        });

        if (!this.table) {
            var sf = this;
            var columns = [{ id: 'display', width: '100%', header: '', dataIndex: this.displayField, style: 'cursor:default;'}];

            this.table = Edo.build(Edo.apply({
                type: 'table',
                style: 'border:0',
                outRemoveFocus: false,
                verticalLine: false,
                horizontalLine: false,
                enableCellSelect: false,
                headerVisible: false,
                enableTrackOver: true,
                width: '100%',
                height: '100%',
                minHeight: 10,
                autoHeight: true,
                rowHeight: 20,
                autoColumns: true,
                //autoExpandColumn: 'display',
                selectedCls: 'e-table-row-over',
                data: this.data,
                columns: columns,
                repeatSelect: true,
                onmousedown: function (e) {
                    sf.focus();
                },
                onselectionchange: this._onSelection.bind(this)
            }, this.tableConfig));



            this.popupCt.addChild(this.table);

            this.table.set('cls', 'e-table-hover');
        }

        this.canChange = false;
        this.table.deselect();

        if (this.selectedItem && this.selectedIndex != -1) this.table.select(this.selectedItem);
        else this.table.clearSelect();

        this.canChange = true;
    },
    _onDataChange: function (e) {      //combo的data改变后,要将table的数据改变    

        //this.set('selectedIndex', -1);
        //        if(this.table){
        //            this.table._setData(this.data);
        //        }        
    },

    valueField: 'text',
    defaultValue: '',
    setValue: function (v) {
        var o = {};
        o[this.valueField] = v;  //{id: 1}
        var index = this.data.findIndex(o);
        this._setSelectedIndex(index);
    },
    getValue: function () {
        var o = this.selectedItem;
        if (o) o = o[this.valueField];
        return o;
    }
});

Edo.controls.ComboBox.regType('combo');

Edo.controls.Date = function (config) {



    Edo.controls.Date.superclass.constructor.call(this);
    //this.date = new Date();

    //Edo.util.Dom.on(this.field, 'keydown', this._onComboFieldKeyDown, this, 0);    
};
Edo.controls.Date.extend(Edo.controls.Trigger, {
    showTime: false,
    disabledDates: null,
    useEndDate: false,

    enableResizePopup: false,

    valueField: 'date',

    popupWidth: 230,

    popupHeight: 220,
    maxPopupHeight: 250,

    popupType: 'ct',

    triggerPopup: true,




    format: 'Y-m-d',

    inputFormat: 'Y-m-dTH:i:s',


    elCls: 'e-text e-date',

    enableTime: true,

    datepickerClass: 'datepicker',

    valueFormat: false,

    getValue: function () {
        var v = Edo.controls.DatePicker.superclass.getValue.call(this);
        if (v && this.valueFormat) v = v.format(this.format);
        return v;
    },

    convertDate: function (date) {
        var v = date;
        if (!Edo.isDate(date)) {
            date = Date.parseDate(v, this.inputFormat);
            if (!Edo.isDate(date)) date = Date.parseDate(v, this.format);
        }
        return date;
    },

    required: true,
    _setRequired: function (v) {
        if (this.required != v) {
            this.required = v;
            if (this.datePicker) {
                this.datePicker.set('required', v);
            }
        }
    },
    _setDate: function (value) {

        value = this.convertDate(value);

        if (!Edo.isDate(value) && this.required) {
            if (this.date) {
                this._setText(this.date.format(this.format));
            }
            return;
        }

        if (this.date != value) {

            if (this.fireEvent('beforedatechange', {
                type: 'beforedatechange',
                source: this,
                date: value
            }) == false) return;

            if (value) {
                value = new Date(value.getTime());
                if (this.useEndDate) {
                    value.setHours(23);
                    value.setMinutes(23);
                    value.setSeconds(59);
                } else {
                    if (this.showTime) {
                    } else {
                        value.setHours(0);
                        value.setMinutes(0);
                        value.setSeconds(0);
                    }
                }
            }

            this.date = value;

            this.changeProperty('date', value);

            this.fireEvent('datechange', {
                type: 'datechange',
                target: this,
                date: this.date
            });
        }
        if (Edo.isDate(this.date)) {
            this._setText(this.date.format(this.format));
        } else {
            this._setText('');
        }
    },
    _dateChangeHandler: function (e) {
        if (this.datePicker && this.datePicker.created) {

            this._setDate(e.date);

        }
    },
    within: function (e) {      //传递一个事件对象,判断是否在此控件的点击区域内.

        var r = false;
        if (this.datePicker) r = this.datePicker.within(e);      //datePicker有一些下拉框是脱离这个环境的
        return Edo.controls.Date.superclass.within.call(this, e) || r;
    },
    createPopup: function () {

        Edo.controls.Date.superclass.createPopup.apply(this, arguments);

        if (!this.datePicker) {
            this.datePicker = Edo.build({
                type: this.datepickerClass,
                width: '100%',
                height: '100%',
                date: this.date,
                required: this.required,
                showTime: this.showTime,
                disabledDates: this.disabledDates,
                onDatechange: this._dateChangeHandler.bind(this)
            });

            this.datePicker.on("dateselection", function (e) {
                this.hidePopup();
                this.focus();
            }, this);

            this.popupCt.addChild(this.datePicker);

            //this.popupCt.doLayout();
        } else {
            this.datePicker.set('date', this.date);
        }
    },
    _onTextChange: function (e) {
        Edo.controls.Date.superclass._onTextChange.call(this, e);
        this._setDate(this.text);
    }
});
Edo.controls.Date.regType('date');

Edo.controls.DatePicker = function (config) {




    Edo.controls.DatePicker.superclass.constructor.call(this);

    //this.date = new Date();
    this.viewDate = new Date();
};
Edo.controls.DatePicker.extend(Edo.containers.Box, {
    componentMode: 'control',

    disabledDates: null,    //[{start: date, end: date}, ...]


    valueField: 'date',

    weekStartDay: 0,
    weeks: ['日', '一', '二', '三', '四', '五', '六'],


    valueField: 'date',

    width: 185,

    minWidth: 185,
    defaultWidth: 185,
    minHeight: 160,
    defaultHeight: 190,


    padding: [0, 0, 0, 0],

    verticalGap: 0,

    elCls: 'e-box e-datepicker e-div',




    format: 'Y-m-d',

    yearFormat: 'Y年',
    monthFormat: 'm月',

    todayText: '今天',
    clearText: '清除',
    autoSelectDate: true,
    //clearVisible: true,

    required: true,

    valueFormat: false,

    footerVisible: true,
    headerVisible: true,
    footerHeight: 35,
    headerHeight: 25,

    isWorkingDate: function (date) {
        var day = date.getDay();
        return !(day == 0 || day == 6);
    },

    dateRenderer: function (v, r, c, i, d, t) {
        var h = t.rowHeight - 3;
        var cls = (v.viewdate.getMonth() != v.date.getMonth() ? 'e-datepicker-outmonthday' : '');
        if (!t.owner.isWorkingDate(v.date)) c.cls = ' e-datepicker-noworkingday';
        else c.cls = '';

        if (this.isDisableDate(v.date) == true) {
            cls += " e-datepicker-disableddate "
        }

        return '<a style="line-height:' + h + 'px;100%" class="' + cls + '" href="javascript:;" hidefocus>' + v.date.getDate() + '</a>';
    },

    getValue: function () {
        var v = Edo.controls.DatePicker.superclass.getValue.call(this);
        if (v && this.valueFormat) v = v.format(this.format);
        return v;
    },

    within: function (e) {      //传递一个事件对象,判断是否在此控件的点击区域内.        
        //var dom = DomQuery.findParent(e.target, '.e-datepicker-pupselect', 2);

        var r1 = this.monthpop ? e.within(this.monthpop) : false;
        var r2 = this.yearpop ? e.within(this.yearpop) : false;

        return Edo.controls.DatePicker.superclass.within.call(this, e) || r1 || r2;
    },
    //private
    monthClickHandler: function (e) {
        var dh = Edo.util.Dom;
        var doc = document, bd = doc.body;
        if (!this.monthpop) {
            var s = '<div class="e-datepicker-pupselect">';
            for (var i = 1; i <= 12; i++) {
                s += '<a href="#" onclick="return false">' + i + '</a>';
            }
            s += '</div>';

            //var s = '<div class="e-datepicker-pupselect"><a href="javascript://void(0);">1</a><a href="javascript://void(0);">2</a><a href="javascript://void(0);">3</a><a href="javascript://void(0);">4</a><a href="javascript://void(0);">5</a><a href="javascript://void(0);">6</a><a href="javascript://void(0);">7</a><a href="javascript://void(0);">8</a><a href="javascript://void(0);">9</a><a href="javascript://void(0);">10</a><a href="javascript://void(0);">11</a><a href="javascript://void(0);">12</a></div>';
            this.monthpop = Edo.util.Dom.append(bd, s);

        }
        this.monthpop.style.display = 'block';
        var bs = dh.getBox(this.monthEl);
        //var xy =dh.getXY(this.header);
        var xy = this.headerCt.getBox();
        dh.setXY(this.monthpop, [bs.x, xy.y + bs.height]);

        function onmousedown(e) {

            if (e.within(this.monthpop)) {
                var d = this.viewDate.clone();
                d.setMonth(parseInt(e.target.innerHTML) - 1);
                this._setViewDate(d);
            }
            //dh.remove.defer(10, dh, [this.monthpop]);
            //dh.remove(this.monthpop);
            //this.monthpop = null;
            this.monthpop.style.display = 'none';
            dh.un(doc, 'mousedown', onmousedown, this);
        }
        dh.on(doc, 'mousedown', onmousedown, this);
    },
    yearClickHandler: function (e) {
        var dh = Edo.util.Dom;
        var doc = document, bd = doc.body;
        var date = this.viewDate;
        function createYear() {
            var year = date.getFullYear(); //在当前年上+-4
            if (!this.yearpop) {
                var sb = ['<div class="e-datepicker-pupselect">'];
                sb.push('<a class="pre" href="#" onclick="return false" >-</a>');
                for (var i = year - 4; i <= year + 4; i++) {
                    sb.push('<a href="#" onclick="return false">', i, '</a>');
                }
                sb.push('<a class="next" href="#" onclick="return false" >+</a>');
                this.yearpop = Edo.util.Dom.append(bd, sb.join(''));
            } else {
                var j = 1;
                var cs = this.yearpop.childNodes;
                for (var i = year - 4; i <= year + 4; i++) {
                    cs[j++].innerHTML = i;
                }
            }
            this.yearpop.style.display = 'block';
        }
        createYear.call(this);

        var bs = dh.getBox(this.yearEl);
        var xy = this.headerCt.getBox();
        dh.setXY(this.yearpop, [bs.x, xy.y + bs.height]);

        var index = 0, tt = null;
        var sf = this;
        function updateYear() {
            date = date.add(Date.YEAR, index);
            createYear.call(sf);
        }

        function onmouseup(e) {
            clearInterval(tt);

            dh.un(doc, 'mouseup', onmouseup, this);
        }

        function onmousedown(e) {
            var remove = false;
            if (e.within(this.yearpop)) {
                var t = e.target;
                if (dh.hasClass(t, 'pre')) {

                    dh.on(doc, 'mouseup', onmouseup, this);
                    index = -1;
                    updateYear();
                    tt = setInterval(updateYear, 100);

                } else if (dh.hasClass(t, 'next')) {
                    dh.on(doc, 'mouseup', onmouseup, this);
                    index = 1;
                    updateYear();
                    tt = setInterval(updateYear, 100);

                } else {
                    var d = this.viewDate.clone();
                    var y = parseInt(e.target.innerHTML);
                    if (isNaN(y)) return;
                    d.setFullYear(y);
                    this._setViewDate(d);
                    remove = true;
                }
            } else {
                remove = true;
            }

            if (remove) {
                //dh.remove.defer(10, dh, [this.yearpop]);
                //this.yearpop = null;
                this.yearpop.style.display = 'none';

                dh.un(doc, 'mousedown', onmousedown, this);
            }

        }

        dh.on(doc, 'mousedown', onmousedown, this);

    },
    preMonth: function (e) {
        this._setViewDate(this.viewDate.add(Date.MONTH, -1));
    },
    nextMonth: function (e) {
        this._setViewDate(this.viewDate.add(Date.MONTH, 1));
    },
    preYear: function (e) {
        this._setViewDate(this.viewDate.add(Date.YEAR, -1));
    },
    nextYear: function (e) {
        this._setViewDate(this.viewDate.add(Date.YEAR, 1));
    },
    selectToday: function (e) {
        this._setDate(new Date());
        this.fireEvent('dateselection', {
            type: 'dateselection',
            source: this,
            date: this.date
        });
    },
    clearDate: function () {
        this.set('date', null);
    },
    _onMouseDown: function (e) {

        if (e.within(this.leftYearEl)) this.preYear(e);
        else if (e.within(this.rightYearEl)) this.nextYear(e);
        else if (e.within(this.leftEl)) this.preMonth(e);
        else if (e.within(this.rightEl)) this.nextMonth(e);
        else if (e.within(this.monthEl)) this.monthClickHandler(e);
        else if (e.within(this.yearEl)) this.yearClickHandler(e);
        else if (e.within(this.daysCt)) this.daysCtClickHandler(e);

        e.stopDefault();
    },
    _onBeforeTableCellSelectionChange: function (e) {

        var cell = e.value;
        if (cell) {
            var d = cell.date;

            //if(!this.date || (d.format(this.format) != this.date.format(this.format))){                
            if (this.autoSelectDate) {

                var timeDate = this.timeControl.value;

                d.setHours(timeDate.getHours());
                d.setMinutes(timeDate.getMinutes());
                d.setSeconds(timeDate.getSeconds());
                if (this.isDisableDate(d) == true) {
                    return false;
                }

                this._setDate(d);
                this.fireEvent('dateselection', {
                    type: 'dateselection',
                    source: this,
                    date: this.date
                });


            }
            return false;
            //}

        }
    },
    isDisableDate: function (date) {
        if (!this.disabledDates) return false;
        for (var i = 0, l = this.disabledDates.length; i < l; i++) {
            var dd = this.disabledDates[i];
            if (dd.start <= date && date <= dd.end) {
                return true;
            }
        }
        return false;
    },
    showTime: false,
    timeFormat: 'H',
    init: function () {

        //trace("datepicker init!!!");
        var year = this.viewDate.format(this.yearFormat), month = this.viewDate.format(this.monthFormat);

        //创建内置的对象
        this.setChildren([
            {
                type: 'div', width: '100%', height: this.headerHeight,
                html: '<table class="e-datepicker-header" border="0" cellpadding="0" cellspacing="0" ><tr><td width="10%" style="text-align:center;"><a hidefocus href="javascript:;" class="e-datepicker-year-left"></a></td><td  class="e-datepicker-year">' + year + '</td><td width="10%" style="text-align:center;"><a hidefocus href="javascript:;" class="e-datepicker-year-right"></a></td><td ></td><td width="10%" style="text-align:center;"><a hidefocus href="javascript:;" class="e-datepicker-left"></a></td><td  class="e-datepicker-month">' + month + '</td><td width="10%" style="text-align:center;"><a href="javascript:;" hidefocus class="e-datepicker-right"></a></td></tr></table>'
            },
            {
                type: 'table', enableCellSelect: true, enableSelect: false, cellPaddingLeft: 0, cellPaddingRight: 0, enableHoverRow: false, cls: 'e-datepicker-body', style: 'border:0', width: '100%', height: '100%', verticalScrollPolicy: 'off', horizontalScrollPolicy: 'off', minHeight: 134, verticalLine: false, horizontalLine: false, autoColumns: true, enableDragDrop: false,
                repeatSelect: true,
                columns: this.createDateColumns(),
                onbeforecellselectionchange: this._onBeforeTableCellSelectionChange.bind(this)
            },
            {
                layout: 'horizontal', type: 'box', cls: 'e-datepicker-foot', border: [1, 0, 0, 0], width: '100%', height: this.footerHeight, verticalAlign: 'middle', horizontalAlign: 'center', padding: 0,
                children: [
                    { type: 'timespinner', name: 'time', visible: this.showTime, format: this.timeFormat },
                    {
                        type: 'button', width: 50, height: 22, text: this.todayText,
                        onclick: this.selectToday.bind(this)
                    },
                    { type: 'space', width: '10%', visible: !this.required },
                    {
                        type: 'button', visible: !this.required, width: 50, height: 22, text: this.clearText,
                        onclick: this.clearDate.bind(this)
                    }
                ]
            }
        ]);
        this.headerCt = this.getChildAt(0);
        this.table = this.getChildAt(1);
        this.footCt = this.getChildAt(2);

        this.headerCt.set('visible', this.headerVisible);
        this.footCt.set('visible', this.footerVisible);

        this.table.owner = this;

        this.on('click', this._onMouseDown, this);

        this.table.set('rowHeight', this.getDateHeight());
        this.table.set('data', this.createViewData());

        this.timeControl = Edo.getByName("time", this)[0];
        this.timeControl.set("value", this.date);

        this.timeControl.on("valuechange", function (e) {
            this.allowSetTime = false;
            var timeDate = this.timeControl.value;
            var d = this.date.clone();
            d.setHours(timeDate.getHours());
            d.setMinutes(timeDate.getMinutes());
            d.setSeconds(timeDate.getSeconds());
            this._setDate(d);
            this.allowSetTime = true;
        }, this);
        //        this.timeControl.on("valuechange", function (e) {
        //            //alert(1);
        //        });

        Edo.controls.DatePicker.superclass.init.call(this);
    },
    createDateColumns: function () {
        var columns = [];

        for (var i = this.weekStartDay, dataIndex = 0, l = this.weekStartDay + 7; i < l; i++, dataIndex++) {
            var week = i >= 7 ? i - 7 : i;
            columns.add({
                header: this.weeks[week],
                enableMove: false,
                enableSort: false,
                enableResize: false,
                headerAlign: 'center',
                dataIndex: dataIndex,
                //width: '100%',
                renderer: this.dateRenderer.bind(this)
            });
        }
        return columns;
    },
    //根据viewdate的year,month,生成一个7列6行的dataTable数据,用于表格生成
    createViewData: function () {
        var data = [];

        var date = this.viewDate.add(Date.DAY, -this.viewDate.getDate() + 1); //获得本月第一天        
        date = date.add(Date.DAY, -date.getDay() + this.weekStartDay);  //获得当前周的第一天

        //      

        var h = this.getDateHeight();

        var bdHeight = this.getTableBodyHeight();
        var xpHeight = bdHeight - h * 6;

        for (var j = 0; j < 6; j++) {
            var record = [];
            //record.__height =  h;
            for (var i = this.weekStartDay, l = this.weekStartDay + 7; i < l; i++) {
                record.add({
                    viewdate: this.viewDate,
                    date: date
                });

                if (j == 0 && record.length == 1) this.startDate = date.clone();
                if (j == 4 && record.length == 7) this.finishDate = date.clone();

                date = date.add(Date.DAY, 1);
            }
            data.add(record);

            //if(j==4) record.__height = h + xpHeight;
        }

        //this.viewData = data;

        return data;
    },
    _setWeekStartDay: function (value) {
        this.weekStartDay = value;
    },
    _setViewDate: function (value) {
        if (typeof value == 'string') {
            value = Date.parseDate(value, this.format);
        }
        if (!Edo.isDate(value)) return;
        if (this.viewDate != value) {
            this.viewDate = value;
            if (this.inited) {
                this.table.set('data', this.createViewData());
                //this.refresh();   //在syncSize的时候会自动调用,所以注释掉
            }
        }
    },
    _setDate: function (value) {
        if (typeof value == 'string') {
            value = Date.parseDate(value, this.format);
        }
        if (!Edo.isDate(value)) {
            value = null;
            if (this.required) return false;
        }
        if (this.date != value) {

            if (this.fireEvent('beforedatechange', {
                type: 'beforedatechange',
                source: this,
                date: this.date
            }) == false) return;

            this.date = value;

            if (this.allowSetTime !== false) {
                if (this.timeControl) {
                    this.timeControl.set("value", this.date);
                }
            }

            this._setViewDate.defer(100, this, [this.date ? this.date.clone() : new Date()]);

            this.changeProperty('date', value);
            this.fireEvent('datechange', {
                type: 'datechange',
                source: this,
                date: value
            });
        }
    },
    refresh: function () {
        var year = this.viewDate.format(this.yearFormat), month = this.viewDate.format(this.monthFormat);

        if (this.el) {
            this.yearEl.innerHTML = year;
            this.monthEl.innerHTML = month;
        }

        var rowHeight = this.getDateHeight();
        this.table.set('rowHeight', rowHeight);

        var bdHeight = this.getTableBodyHeight();
        var xpHeight = bdHeight - rowHeight * 6;
        this.table.data.view[4].__height = rowHeight + xpHeight;

        this.table.deferRefresh(true);

        this.viewSelectDate(true);

    },
    viewSelectDate: function (select) {
        var row = col = -1;
        var view = this.table.data.view; //this.createViewData();
        var dateFormat = this.date ? this.date.format('Y-m-d') : null;
        for (var i = 0, l = view.length; i < l; i++) {
            var r = view[i];
            for (var j = 0; j < 7; j++) {
                if (r[j].date.format('Y-m-d') == dateFormat) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }
        if (row != -1 && col != -1) {
            var cell = this.table.data.getAt(row)[col];

            //            var d = cell.date;
            //            var cansel = !this.date || (d.format(this.format) != this.date.format(this.format))
            //            
            if (select !== false) {
                this.table.fireSelection = false;
                this.table.selectCell(row, col)
                this.table.fireSelection = true;
            } else {
                this.table.deselectCell(row, col)
            }
        }


    },
    syncSize: function () {
        Edo.controls.DatePicker.superclass.syncSize.call(this);


        this.refresh();
    },
    getTableBodyHeight: function () {
        return this.realHeight - (this.footerVisible ? this.footerHeight : 0) - (this.headerVisible ? 28 : 0) - this.table.getHeaderHeight();
    },
    getDateHeight: function () {

        var h = this.table ? this.getTableBodyHeight() / 6 : 23;
        h = parseInt(h);

        return h;
    },
    render: function (p) {
        Edo.controls.DatePicker.superclass.render.call(this, p);

        var hel = this.headerCt.el.firstChild; //!!!

        var tds = hel.rows[0].cells;
        this.leftEl = Edo.util.Dom.getbyClass('e-datepicker-left', hel);
        this.rightEl = Edo.util.Dom.getbyClass('e-datepicker-right', hel);

        this.monthEl = Edo.util.Dom.getbyClass('e-datepicker-month', hel);
        this.yearEl = Edo.util.Dom.getbyClass('e-datepicker-year', hel);

        this.leftYearEl = Edo.util.Dom.getbyClass('e-datepicker-year-left', hel);
        this.rightYearEl = Edo.util.Dom.getbyClass('e-datepicker-year-right', hel);


        //        this.table.set('data', this.createViewData());
        //        this.viewSelectDate(true);
    }

});

Edo.controls.DatePicker.regType('datepicker');

Edo.controls.Label = function () {

    Edo.controls.Label.superclass.constructor.call(this);
};
Edo.controls.Label.extend(Edo.controls.Control, {
    elCls: 'e-label',

    autoWidth: true,

    autoHeight: true,

    minWidth: 20,



    text: '',

    forId: '',

    //    sizeSet: false,
    //    widthGeted: false,

    getInnerHtml: function (sb) {
        sb[sb.length] = this.text;
    },
    _setText: function (value) {
        if (this.text !== value) {
            this.text = value;
            if (this.el) {
                this.el.innerHTML = value;
                //this.el.style.width = 'auto';
            }
            if (!Edo.isInt(this.width)) {
                this.widthGeted = false;
                //                this.el.style.width = 'auto';
                //                Edo.util.Dom.repaint(this.el);
            }
            if (!Edo.isInt(this.height)) {
                this.heightGeted = false;
            }
            this.changeProperty('text', value);
            this.relayout('text', value);
        }
    }
});

Edo.controls.Label.regType('label');

Edo.controls.Spinner = function (config) {




    Edo.controls.Spinner.superclass.constructor.call(this);

};
Edo.controls.Spinner.extend(Edo.controls.Trigger, {
    valueField: 'value',
    downShowPopup: false,
    defaultValue: 0,

    value: 0,

    //    minValue: 0,

    //    maxValue: 100,    

    incrementValue: 1,

    alternateIncrementValue: 5, //shift+click时

    alternateKey: 'shiftKey',

    spinTime: 100,
    //triggerCls: 'e-trigger-empty',
    //withKeyUp: false,
    valueField: 'value',

    getTriggerHtml: function () {
        this.elCls += ' e-spinner';
        return '<div class="e-spinner-up" unselectable="on"><div class="e-trigger-icon" unselectable="on"></div></div><div class="e-spinner-split"></div><div class="e-spinner-down" unselectable="on"><div class="e-trigger-icon" unselectable="on"></div></div>'
    },

    createChildren: function (el) {
        Edo.controls.Spinner.superclass.createChildren.call(this, el);
        this.upEl = this.trigger.firstChild;
        this.downEl = this.trigger.lastChild;

        var v = this.value;
        this.value = null;
        this._setValue(v);
    },
    initEvents: function () {
        if (!this.design) {

            Edo.util.Dom.addClassOnOver(this.upEl, 'e-spinner-up-over');
            Edo.util.Dom.addClassOnClick(this.upEl, 'e-spinner-up-pressed');
            Edo.util.Dom.addClassOnOver(this.downEl, 'e-spinner-down-over');
            Edo.util.Dom.addClassOnClick(this.downEl, 'e-spinner-down-pressed');

            Edo.util.Dom.on(this.field, 'keydown', this._onKeyDown, this);

            Edo.util.Dom.on(document.body, 'mouseup', this.stopSpin, this);

            this.on('spin', this._onSpin, this);
        }

        Edo.controls.Spinner.superclass.initEvents.call(this);
    },
    _onTrigger: function (e) {
        if (this.isReadOnly()) return;
        Edo.controls.Spinner.superclass._onTrigger.call(this, e);
        var direction;

        if (e.within(this.upEl)) {
            direction = 'up';
        } else if (e.within(this.downEl)) {
            direction = 'down';
        }
        if (direction) {
            this.startSpin(direction, e);
        }
    },
    startSpin: function (direction, e) {
        this.fireSpin(direction, e);

        this.spinTimer = this.fireSpin.time(this.spinTime, this, [direction, e]);


    },
    stopSpin: function (e) {
        var sf = this;
        setTimeout(function () {
            clearInterval(sf.spinTimer);
            sf.spinTimer = null;
        }, 1);
        //Edo.util.Dom.un(document.body, 'mouseup', this.stopSpin, this);
    },
    _onKeyDown: function (e) {
        switch (e.keyCode) {
            case 38:
                this.fireSpin('up', e);
                break;
            case 40:
                this.fireSpin('down', e);
                break;
        }
    },
    fireSpin: function (direction, e) {
        //

        this.fireEvent('spin', {
            type: 'spin',
            direction: direction,
            source: this,
            event: e
        });
    },
    _onSpin: function (e) {
        this.text = this.field.value;
        this.spin(this.field.value, e.direction, e.event[this.alternateKey]);
    },
    spin: function (value, direction, alternate) {
        var v = this.normalizeValue(value);

        var incr = (alternate == true) ? this.alternateIncrementValue : this.incrementValue;
        if (Edo.isValue(direction)) {
            (direction == 'down') ? v -= incr : v += incr;
        }
        this._setValue(v);
    },
    //确保值的有效性
    normalizeValue: function (value) {
        var v = value;

        var v = parseFloat(v);
        if (isNaN(v)) v = this.value;

        if (this.minValue != undefined && v < this.minValue) {
            v = this.minValue;
        }
        if (this.maxValue != undefined && v > this.maxValue) {
            v = this.maxValue;
        }
        return v;
    },
    _setValue: function (v) {
        v = this.normalizeValue(v);

        var e = {
            type: 'beforevaluechange',
            source: this,
            name: this.name,
            value: v,
            text: v
        };
        if (this.fireEvent('beforevaluechange', e) !== false) {
            //if(v !== this.value && this.fireEvent('beforevaluechange', e !== false)){
            this.value = e.value;
            //this.setText(e.text);
            this._setText(e.text);

            e.type = 'valuechange';
            //this.fireEvent('valuechange', e);

            this.changeProperty('value', this.value);
        }
    },
    _onTextChange: function (e) {

        this.text = this.field.value;

        this.spin(this.field.value);
    }
});

Edo.controls.Spinner.regType('spinner');

Edo.controls.DateSpinner = function (config) {

    Edo.controls.DateSpinner.superclass.constructor.call(this);

};
Edo.controls.DateSpinner.extend(Edo.controls.Spinner, {
    defaultValue: new Date(),

    value: new Date(),

    incrementValue: 1,

    alternateIncrementValue: 1,


    format: "Y-m-d",

    incrementConstant: Date.DAY,

    alternateIncrementConstant: Date.MONTH,

    spin: function (value, direction, alternate) {

        var v = this.normalizeValue(value);
        var dir = (direction == 'down') ? -1 : 1;
        var incr = (alternate == true) ? this.alternateIncrementValue : this.incrementValue;
        var dtconst = (alternate == true) ? this.alternateIncrementConstant : this.incrementConstant;

        if (Edo.isValue(direction)) {
            v = v.add(dtconst, dir * incr);
        }

        this._setValue(v);
    },
    _setText: function (v) {

        v = this.value;
        if (v && v.getFullYear) {

            v = v.format(this.format);
        }
        Edo.controls.DateSpinner.superclass._setText.call(this, v);
    },
    //确保值的有效性
    normalizeValue: function (date) {
        var dt = date;

        dt = Date.parseDate(dt, this.format);
        if (!dt) {
            dt = this.value;
        }

        var min = (typeof this.minValue == 'string') ? Date.parseDate(this.minValue, this.format) : this.minValue;
        var max = (typeof this.maxValue == 'string') ? Date.parseDate(this.maxValue, this.format) : this.maxValue;

        if (this.minValue != undefined && dt < min) {
            dt = min;
        }
        if (this.maxValue != undefined && dt > max) {
            dt = max;
        }
        return dt;
    },
    getValue: function () {
        return this.value.format(this.format);
    }
});

Edo.controls.DateSpinner.regType('datespinner');

Edo.controls.TimeSpinner = function () {

    Edo.controls.TimeSpinner.superclass.constructor.call(this);

};
Edo.controls.TimeSpinner.extend(Edo.controls.DateSpinner, {

    format: "H:i:s",

    incrementValue: 1,

    incrementConstant: Date.HOUR,

    alternateIncrementValue: 1,

    alternateIncrementConstant: Date.HOUR,
    normalizeValue: function (value) {
        if (typeof value == 'string') {
            //var v = Date.parseDate(value, 'H:i:s');
            var v = Date.parseDate(value, this.format);

            if (!v) {
                var sps = value.split(':');
                var h = parseInt(sps[0]);
                var i = parseInt(sps[1]);
                var s = parseInt(sps[2]);

                var sb = [];
                if (!isNaN(h)) {
                    if (String(h).length == 1) sb.add('0' + h);
                    else sb.add(h);
                }
                if (!isNaN(i)) {
                    if (String(i).length == 1) sb.add('0' + i);
                    else sb.add(i);
                }
                if (!isNaN(s)) {
                    if (String(s).length == 1) sb.add('0' + s);
                    else sb.add(s);
                }
                v = sb.join(':');
            }
            if (v) {
                value = v;
            }
        }
        return Edo.controls.TimeSpinner.superclass.normalizeValue.call(this, value);
    }
});
Edo.controls.TimeSpinner.regType('timespinner');

Edo.controls.HtmlEditor = function () {
    Edo.controls.HtmlEditor.superclass.constructor.call(this);
}
Edo.controls.HtmlEditor.extend(Edo.controls.TextArea, {
    minWidth: 120,
    minHeight: 50,
    elCls: 'e-htmleditor e-div  e-text',

    editConfig: null,
    createChildren: function (el) {
        Edo.controls.HtmlEditor.superclass.createChildren.call(this, el);
        this.editor = CKEDITOR.replace(this.field,
            Edo.apply({
                skin: "v2", height: this.realHeight - 52,
                toolbar: 'Basic',
                toolbarCanCollapse: false,
                resize_enabled: false,
                toolbar_Basic: [['Font', 'Bold', 'Italic', 'Underline', '-', 'Subscript', 'Superscript', '-', 'TextColor', 'BGColor', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', '-', 'Maximize', 'Source']]
            }, this.editConfig));

    },
    syncSize: function () {
        Edo.controls.HtmlEditor.superclass.syncSize.apply(this, arguments);

        //        this.editor.wrapping.style.width = (this.realWidth-1)+'px';
        //        this.editor.wrapping.style.height = (this.realHeight-1)+"px";

        Edo.util.Dom.setSize(this.el, this.realWidth, this.realHeight);
    },
    _setText: function (text) {
        this.text = text;
        if (this.editor) this.editor.setData(text);
    },
    _getText: function (text) {
        if (this.editor) this.text = this.editor.getData();
        return this.text;
    }
});
Edo.controls.HtmlEditor.regType('htmleditor');


Edo.controls.CodeEditor = function () {

    Edo.controls.CodeEditor.superclass.constructor.call(this);
};
Edo.controls.CodeEditor.extend(Edo.controls.TextArea, {
    minWidth: 120,
    minHeight: 50,
    elCls: 'e-codeeditor e-div  e-text',
    path: "/",
    createChildren: function (el) {
        Edo.controls.CodeEditor.superclass.createChildren.call(this, el);

        var w = isNaN(this.realWidth) ? this.defaultWidth : this.realWidth - 1;
        var h = isNaN(this.realHeight) ? this.defaultHeight : this.realHeight - 1;


        this.editor = CodeMirror.fromTextArea(this.field, {
            width: w + 'px',
            height: h + "px",
            parserfile: ["tokenizejavascript.js", "parsejavascript.js"],
            stylesheet: [this.path + "css/jscolors.css"],
            path: this.path + 'js/'
            //,lineNumbers: true
        });

    },

    syncSize: function () {
        Edo.controls.CodeEditor.superclass.syncSize.apply(this, arguments);

        this.editor.wrapping.style.width = (this.realWidth - 1) + 'px';
        this.editor.wrapping.style.height = (this.realHeight - 1) + "px";

        Edo.util.Dom.setSize(this.el, this.realWidth, this.realHeight);
    },
    _setText: function (text) {
        this.text = text;
        if (this.editor) {
            try {
                this.editor.setCode(text);
            } catch (e) {
                this._setText.defer(100, this, [text]);
            }
        }
    },
    _getText: function (text) {
        if (this.editor) this.text = this.editor.getCode();
        return this.text;
    }
});
Edo.controls.CodeEditor.regType('codeeditor');


Edo.controls.Progress = function () {



    Edo.controls.Progress.superclass.constructor.call(this);
};
Edo.controls.Progress.extend(Edo.core.UIComponent, {
    valueField: 'progress',
    defaultValue: 0,

    minWidth: 50,
    defaultWidth: 100,

    showText: true,

    progress: 0,

    text: '',

    elCls: 'e-progress e-div',
    _setProgress: function (value) {
        if (value < 0) value = 0;
        if (value > 100) value = 100;
        if (this.progress != value) {
            this.progress = value;
            if (this.el) {
                this.textEl.innerHTML = this.getProgressDesc();
                Edo.util.Dom.setWidth(this.progressEl, this.getProgressWidth());
            }
            this.changeProperty('progress');

            this.fireEvent('progresschange', {
                type: 'progresschange',
                source: this,
                progress: value,
                text: this.text
            });
        }
    },
    _setText: function (value) {
        if (this.text != value) {
            this.text = value;
            if (this.el) {
                this.textEl.innerHTML = this.getProgressDesc();
            }
            this.changeProperty('text');
        }
    },
    getProgressDesc: function () {
        return this.showText ? this.progress + '% ' + this.text : '&#160';
    },
    getProgressWidth: function () {
        return parseInt(this.realWidth * this.progress / 100) - 1;
    },
    getInnerHtml: function (sb) {
        sb[sb.length] = '<div class="e-progress-bar"></div><div class="e-progress-text"></div>';
    },
    createChildren: function (el) {
        Edo.controls.Label.superclass.createChildren.call(this, el);

        this.progressEl = this.el.firstChild;
        this.textEl = this.el.lastChild;
    }
});

Edo.controls.Progress.regType('progress');

Edo.controls.MultiSelect = function () {

    Edo.controls.MultiSelect.superclass.constructor.call(this);
};
Edo.controls.MultiSelect.extend(Edo.lists.Table, {
    headerVisible: false,
    minWidth: 80,
    minHeight: 50,

    rowSelectMode: 'multi',
    enableDragDrop: true,    //允许行拖拽
    dragDropAction: 'move', //copy

    displayText: '名称',
    displayField: 'text',
    valueField: 'text',

    autoExpandColumn: 'display',

    defaultValue: '',
    setValue: Edo.controls.DataView.prototype.setValue,
    getValue: Edo.controls.DataView.prototype.getValue,

    init: function () {
        this.set('columns', [
            Edo.lists.Table.createMultiColumn(),
            { id: 'display', width: '100%', header: this.displayText, dataIndex: this.displayField }
        ]);
        Edo.controls.MultiSelect.superclass.init.call(this);
    }
});

Edo.controls.MultiSelect.regType('multiselect');

Edo.controls.CheckGroup = function (config) {
    Edo.controls.CheckGroup.superclass.constructor.call(this);
};
Edo.controls.CheckGroup.extend(Edo.controls.DataView, {

    autoWidth: true,

    autoHeight: true,

    multiSelect: true,
    //mustSelect: false,

    repeatSelect: false,
    selectOnly: false,

    elCls: 'e-checkgroup',

    itemSelector: 'e-checkgroup-item',
    itemCls: 'e-checkgroup-item',
    selectedCls: 'e-checkgroup-item-checked',

    displayField: 'text',

    valueField: 'value',

    repeatLayout: 'table',  //flow

    repeatDirection: 'vertical', // horizontal

    repeatItems: 10000,

    itemWidth: 'auto',

    itemHeight: 'auto',

    cellPadding: 0,

    cellSpacing: 2,

    tpl: '<%=this.createView();%>',

    itemStyle: 'padding-right:3px;',
    itemRenderer: function () {

    },
    createView: function () {
        var data = this.data;
        if (!data || data.getCount() == 0) return '';
        data = this.data.view;
        if (this.repeatLayout == 'table') {
            data = this.createTableData(data);
            var sb = ['<table cellpadding="' + this.cellPadding + '" cellspacing="' + this.cellSpacing + '">'];
            for (var i = 0, l = data.length; i < l; i++) {
                var r = data[i];
                sb[sb.length] = '<tr>';
                for (var j = 0, k = r.length; j < k; j++) {
                    var o = r[j];
                    sb[sb.length] = '<td style="vertical-align:top;">';
                    sb[sb.length] = this.getItemHtml(o);
                    sb[sb.length] = '</td>';

                }
                sb[sb.length] = '</tr>';
            }
            sb[sb.length] = '</table>';
            return sb.join('');
        } else {
            var sb = [];
            for (var i = 0, l = data.length; i < l; i++) {
                var o = data[i];
                sb[sb.length] = this.getItemHtml(o, 'float:left');
            }
            return sb.join('');
        }
    },
    createTableData: function (data) {
        var view = [];

        for (var i = 0, l = data.length; i < l; i++) {
            var o = data[i];

            var index = parseInt(i / this.repeatItems);
            if (this.repeatDirection == 'vertical') {
                index = i % this.repeatItems;

            }
            var r = view[index];
            if (!r) {
                r = [];
                view.add(r);
            }
            r.add(o);
        }
        return view;
    },
    getItemHtml: function (o, style) {
        var s = '<div id=' + this.createItemId(o) + ' class="' + this.itemCls + ' ' + (o.enable === false ? 'e-disabled' : '') + '" style="float:left;' + style + ';' + this.itemStyle + ';width:' + this.itemWidth + '">' + o[this.displayField] + '</div>';
        return s;
    }
});
Edo.controls.CheckGroup.regType('checkgroup');


Edo.controls.RadioGroup = function () {

    Edo.controls.RadioGroup.superclass.constructor.call(this);
};
Edo.controls.RadioGroup.extend(Edo.controls.CheckGroup, {

    multiSelect: false,
    mustSelect: true,
    elCls: 'e-radiogroup',
    itemCls: 'e-checkgroup-item'
});

Edo.controls.RadioGroup.regType('radiogroup');


Edo.controls.SWF = function () {
    Edo.controls.SWF.superclass.constructor.call(this);
}
Edo.controls.SWF.extend(Edo.controls.Control, {

    minWidth: 100,

    minHeight: 50,

    defaultWidth: 100,

    defaultHeight: 50,

    flashVersion: '9.0.45',

    backgroundColor: '#ffffff',

    wmode: 'opaque',

    url: undefined,
    swfId: undefined,
    swfWidth: '100%',
    swfHeight: '100%',

    expressInstall: false,

    createChildren: function (el) {
        Edo.controls.SWF.superclass.createChildren.call(this, el);

        var params = {
            allowScriptAccess: 'always',
            bgcolor: this.backgroundColor,
            wmode: this.wmode
        }, vars = {
            allowedDomain: document.location.hostname,
            //elementID: this.getId(),
            elementID: this.id
            //,eventHandler: 'Ext.FlashEventProxy.onEvent'
        };

        var id = this.id + 'swf';

        Edo.util.Dom.append(this.el, '<div id="' + id + '"></div>');

        new swfobject.embedSWF(this.url, id, this.swfWidth, this.swfHeight, this.flashVersion,
            this.expressInstall ? Edo.controls.SWF.EXPRESS_INSTALL_URL : undefined, vars, params);

        this.swf = Edo.getDom(id);
    },
    syncSize: function () {
        Edo.controls.SWF.superclass.syncSize.apply(this, arguments);


        //        this.editor.wrapping.style.width = (this.realWidth-1)+'px';
        //        this.editor.wrapping.style.height = (this.realHeight-1)+"px";
        //        
        //        Edo.util.Dom.setSize(this.el, this.realWidth, this.realHeight);
    }
});

Edo.controls.SWF.regType('swf');
Edo.controls.SWF.EXPRESS_INSTALL_URL = 'http:/' + '/swfobject.googlecode.com/svn/trunk/swfobject/expressInstall.swf';



Edo.controls.FileUpload = function () {
    if (typeof SWFUpload == 'undefined') {
        throw new Error("请引入swfupload.js");
    }





    Edo.controls.FileUpload.superclass.constructor.call(this);
}
Edo.controls.FileUpload.extend(Edo.controls.Control, {

    swfUploadConfig: null,

    buttonWidth: 69,

    autoClear: true,

    textVisible: true,

    elCls: 'e-fileupload e-div',

    getInnerHtml: function (sb) {
        var id = this.id + "$place";
        var inputid = this.id + "$input";
        var w = this.realWidth - this.buttonWidth;
        sb[sb.length] = '<input id="' + inputid + '" readOnly type="text" class="e-fileupload-text" value="" style="width:' + w + 'px;"> <span id="' + id + '"></span>';
    },
    createChildren: function () {
        Edo.controls.FileUpload.superclass.createChildren.apply(this, arguments);

        var id = this.id + "$place";

        var c = Edo.apply({
            //upload_url: "upload.aspx",
            //file_post_name: "resume_file",
            //flash_url : "../../source/res/swfupload.swf",

            button_window_mode: SWFUpload.WINDOW_MODE.OPAQUE,
            button_image_url: "XPButtonUploadText_61x22.png",
            //button_placeholder_id : "spanButtonPlaceholder",
            button_width: 61,
            button_height: 22,

            // Flash file settings
            file_size_limit: "64",
            file_types: "*.jpg;*.gif;*.png;*.bmp",
            file_types_description: "All Files",
            file_upload_limit: "0",
            file_queue_limit: "0",

            file_queued_handler: this._onFileQueued.bind(this),
            file_queue_error_handler: this._onFileQueueError.bind(this),

            upload_start_handler: this._onFileStart.bind(this),
            upload_error_handler: this._onFileError.bind(this),
            upload_success_handler: this._onFileSuccess.bind(this)
        }, this.swfUploadConfig);
        c.button_placeholder_id = id;

        this.upload = new SWFUpload(c);

        var inputid = this.id + "$input";
        this.inputField = document.getElementById(inputid);
    },
    _onFileQueued: function (file) {
        var txt = this.inputField;

        var txtFileName = txt.value;
        if (txtFileName && txtFileName != file.name) {
            //this.upload.cancelUpload();    
        }

        txt.value = file.name;

        var e = {
            type: 'filequeued',
            source: this,
            upload: this.upload,
            filename: file.name,
            file: file
        };
        this.fireEvent('filequeued', e);
    },
    _onFileQueueError: function (file, errorCode, message) {
        var e = {
            type: 'filequeueerror',
            source: this,
            upload: this.upload,
            file: file,
            errorCode: errorCode,
            message: message
        };
        this.fireEvent('filequeueerror', e);
    },
    _onFileStart: function () {
        var e = {
            type: 'filestart',
            source: this,
            upload: this.upload
        };
        this.fireEvent('filestart', e);
    },
    _onFileError: function (file, errorCode, message) {
        var e = {
            type: 'fileerror',
            source: this,
            upload: this.upload,
            file: file,
            errorCode: errorCode,
            message: message
        };
        this.fireEvent('fileerror', e);
    },
    _onFileSuccess: function (file, serverData) {
        if (this.autoClear) this.inputField.value = '';

        var e = {
            type: 'filesuccess',
            source: this,
            upload: this.upload,
            file: file,
            serverData: serverData
        };
        this.fireEvent('filesuccess', e);
    },
    syncSize: function () {
        Edo.controls.FileUpload.superclass.syncSize.call(this);

        if (this.textVisible) {
            var w = this.realWidth - this.buttonWidth;
            Edo.util.Dom.setWidth(this.inputField, w);
            this.inputField.style.display = '';
        } else {

            this.inputField.style.display = 'none';
        }
    }
});

Edo.controls.FileUpload.regType('fileupload');
Edo.controls.PercentSpinner = function () {

    Edo.controls.PercentSpinner.superclass.constructor.call(this);
};
Edo.controls.PercentSpinner.extend(Edo.controls.Spinner, {
    minValue: 0,
    maxValue: 100,
    _setText: function (v) {

        v += '%';
        Edo.controls.PercentSpinner.superclass._setText.call(this, v);
    }
});

Edo.controls.PercentSpinner.regType('percentspinner');

Edo.controls.MultiCombo = function () {

    Edo.controls.MultiCombo.superclass.constructor.call(this);
};
Edo.controls.MultiCombo.extend(Edo.controls.Trigger, {
    tableConfig: null,

    data: null,

    valueField: 'id',
    displayText: '名称',
    displayField: 'text',
    valueField: 'text',
    delimiter: ',',

    triggerPopup: true,
    readOnly: true,

    multiSelect: true,
    _setMultiSelect: function (value) {
        if (value != this.multiSelect) {
            this.multiSelect = value;
            if (this.table) this.table.set('multiSelect', value);
            //            if(value == false){
            //                if(this.table) this.table.set('rowSelectMode', 'single');      
            //            }
        }
    },
    //    _setValue: function(values){
    //        this.table.setValue(vs);
    //    },
    setValue: function (vv) {
        this.table.setValue(vv);
    },
    getValue: function () {
        return this.table.getValue();
    },

    viewText: function () {
        var texts = [];

        var selecteds = this.table.getSelecteds();
        selecteds.each(function (o) {
            texts.add(o[this.displayField]);
        }, this);
        this._setText(texts.join(this.delimiter));
    },

    _setData: function (data) {
        if (typeof data === 'string') data = window[data];
        if (!data) return;
        if (!data.dataTable) data = new Edo.data.DataTable(data || []);

        this.data = data;

        this.set('text', '');

        if (this.table) this.table.set('data', data);

        this.changeProperty('data', data);
    },

    onselectionchange: function (e) {
        this.selectionchanged = true;
        this.viewText();
    },
    init: function () {
        Edo.controls.MultiCombo.superclass.init.apply(this, arguments);

        if (!this.table) {
            this.table = Edo.create(Edo.apply({
                type: 'multiselect',
                style: 'border:0',
                width: '100%',
                height: '100%',
                autoHeight: true,
                multiSelect: this.multiSelect,
                minHeight: 80,
                maxHeight: 250,
                displayText: this.displayText,
                displayField: this.displayField,
                valueField: this.valueField,
                delimiter: this.delimiter,
                onselectionchange: this.onselectionchange.bind(this)
            }, this.tableConfig));
        }
        this.table.set('data', this.data);
    },
    showPopup: function () {
        Edo.controls.MultiCombo.superclass.showPopup.apply(this, arguments);

        if (!this.table.parent) {
            this.popupCt.addChild(this.table);
        }

        var sels = this.table.getSelecteds().clone();
        this.table.set('data', this.data);
        this.table.selectRange(sels);
    },
    hidePopup: function () {
        Edo.controls.MultiCombo.superclass.hidePopup.apply(this, arguments);

        if (this.selectionchanged) {
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this
            });
        }
        this.selectionchanged = false;
    }
});

Edo.controls.MultiCombo.regType('multicombo');

Edo.controls.TreeSelect = function () {

    Edo.controls.TreeSelect.superclass.constructor.call(this);
};
Edo.controls.TreeSelect.extend(Edo.controls.Trigger, {
    treeConfig: null,

    data: null,

    multiSelect: true,
    popupMinWidth: 150,

    enableResizePopup: true,

    popupHeight: 150,

    valueField: 'id',
    displayText: '名称',
    displayField: 'text',
    valueField: 'text',
    delimiter: ',',

    autoExpandColumn: 'display',

    triggerPopup: true,
    readOnly: true,

    setValue: function (vv) {
        Edo.controls.DataView.prototype.setValue.call(this.tree, vv);
    },
    getValue: function () {
        return Edo.controls.DataView.prototype.getValue.call(this.tree);
    },

    viewText: function () {
        var texts = [];

        var selecteds = this.tree.getSelecteds();
        selecteds.each(function (o) {
            texts.add(o[this.displayField]);
        }, this);
        this._setText(texts.join(this.delimiter));
    },

    _setData: function (data) {
        if (typeof data === 'string') data = window[data];
        if (!data) return;
        if (!data.dataTable) data = new Edo.data.DataTree(data || []);

        this.data = data;

        if (this.tree) this.tree.set('data', data);

        this.changeProperty('data', data);
    },

    onselectionchange: function (e) {
        this.selectionchanged = true;
        this.viewText();
        this.changeProperty('value', this.getValue(), true);
    },
    init: function () {
        Edo.controls.TreeSelect.superclass.init.apply(this, arguments);

        if (!this.tree) {
            this.tree = Edo.create(Edo.apply({
                type: 'tree',
                style: 'border:0',
                width: '100%',
                height: '100%',
                //autoHeight: true,                
                minHeight: 80,
                maxHeight: 250,
                treeColumn: 'display',
                multiSelect: this.multiSelect,
                rowSelectMode: this.multiSelect ? 'multi' : 'single',
                autoExpandColumn: this.autoExpandColumn,
                valueField: this.valueField,
                columns: [
                    Edo.lists.Table.createMultiColumn(),
                    { id: 'display', width: '100%', header: this.displayText, dataIndex: this.displayField }
                ],
                delimiter: this.delimiter,
                onselectionchange: this.onselectionchange.bind(this)
            }, this.treeConfig));
        }
        this.tree.set('data', this.data);
    },
    showPopup: function () {
        Edo.controls.TreeSelect.superclass.showPopup.apply(this, arguments);

        if (!this.tree.parent) {
            this.popupCt.addChild(this.tree);
        }
        var sels = this.tree.getSelecteds().clone();
        this.tree.set('data', this.data);
        this.tree.selectRange(sels);
    },
    hidePopup: function () {
        Edo.controls.TreeSelect.superclass.hidePopup.apply(this, arguments);

        if (this.selectionchanged) {
            this.fireEvent('selectionchange', {
                type: 'selectionchange',
                source: this
            });
        }
        this.selectionchanged = false;
    }
});

Edo.controls.TreeSelect.regType('TreeSelect');
Edo.controls.DurationSpinner = function (config) {

    Edo.controls.DurationSpinner.superclass.constructor.call(this);

};
Edo.controls.DurationSpinner.extend(Edo.controls.Spinner, {
    value: {
        Duration: 0,
        DurationFormat: 37,
        Estimated: 1
    },
    minValue: -10000,

    incrementValue: 1,
    alternateIncrementValue: 8,

    durationFormat: null,       //必须有一个工期format数组

    mustDay: false,
    mustInt: true,
    //durationFormat:
    //	incrementConstant : Date.DAY,	
    //	alternateIncrementConstant : Date.MONTH,

    spin: function (value, direction, alternate) {
        if (!value) value = '0';

        var Estimated = value.indexOf('?') != -1;

        var v = parseFloat(value);
        var dv = value.replace(v, '');
        if (!dv) dv = 'd';
        else if (dv == '?') dv = 'd?';
        if (this.mustDay) {
            if (dv != 'd' || dv != 'd?') dv = 'd';
        }

        if (this.mustInt && dv != 'd') v = parseInt(v);

        var DurationFormat = this.durationFormat[dv];

        if (!DurationFormat) {
            this._setValue(this.value);
            return;
        }

        var incr = (alternate == true) ? this.alternateIncrementValue : this.incrementValue;
        if (Edo.isValue(direction)) {
            (direction == 'down') ? v -= incr : v += incr;
        }

        var f = this.durationFormat[DurationFormat];
        v *= f[0];

        v = {
            Duration: v,
            DurationFormat: DurationFormat,
            Estimated: Estimated
        }

        this._setValue(v);
    },
    _setText: function (v) {

        if (!this.durationFormat) return;

        var f = this.durationFormat[v.DurationFormat];
        if (!f) {
            v = '0d';
        } else {
            v = (v.Duration / f[0]) + f[2] + (v.Estimated ? '?' : '')
        }
        Edo.controls.DurationSpinner.superclass._setText.call(this, v);
    }
    ,
    //确保值的有效性
    normalizeValue: function (v) {

        v.Duration = Edo.controls.DurationSpinner.superclass.normalizeValue.call(this, v.Duration);

        return v;
    }
});

Edo.controls.DurationSpinner.regType('durationspinner');

Edo.controls.AutoComplete = function () {
    Edo.controls.AutoComplete.superclass.constructor.call(this);


}
Edo.controls.AutoComplete.extend(Edo.controls.ComboBox, {

    changeAction: 'keyup',

    popupHeight: 120,
    popupWidth: '100%',

    url: '',

    queryDelay: 500,

    popupShadow: false,

    pageVisible: false,

    autoFill: true,

    maxSize: 10,

    //rootField: '',

    initEvents: function () {
        if (!this.design) {
            this.on('popupshow', this._onpopupshow, this, 0);
            this.on('keyup', this._onkeyup, this, 0);
            this.on('trigger', this._ontrigger, this, 0);
        }
        Edo.controls.AutoComplete.superclass.initEvents.call(this);
    },

    _onpopupshow: function (e) {
        var combo = this;
        if (!this.pager) {

            this.popupCt.set({
                verticalGap: 0
            });
            this.popupCt.addChild({
                visible: this.pageVisible,
                infoVisible: false,
                type: 'pagingbar',
                name: 'pager',
                cls: 'e-toolbar',
                border: [0, 1, 0, 1],
                onpaging: function (e) {
                    combo.query(combo.text, this.index, this.size);
                }
            });
            this.table.set('autoHeight', false);

            //            this.popupCt.addChild({
            //                type: 'box',
            //                width: '100%',                
            //                border: 0,
            //                layout: 'horizontal',
            //                horizontalAlign: 'right',
            //                children: [
            //                    {
            //                        type: 'button',
            //                        cls: 'e-toolbar',
            //                        text: '取消'
            //                    },
            //                    {
            //                        type: 'button',
            //                        width: 50,
            //                        text: '确定'
            //                    }
            //                ]
            //            });
            this.pager = Edo.getByName('pager', this.popupCt)[0];

        }
    },
    _onkeyup: function (e) {

        if (e.keyCode == 40) {
            this.showPopup();
            if (this.data.getCount() == 0) {
                this.pager.change();
            } else {
                //鼠标移动操作表格行
            }
        } else if (e.keyCode == 13) {
            this.hidePopup();
        } else {
            var key = this.field.value;
            //            if(key == ''){
            //                this.data.clear();
            //                //this.hidePopup();
            //                return;
            //            }
            this.showPopup();
            this.pager.change();
        }
    },
    _ontrigger: function (e) {
        if (this.data.getCount() == 0) {
            this.pager.change();
        }
    },

    query: function (key, index, size) {
        //        if(key == '') {      
        //            return;
        //        }
        if (this.selectedItem && this.selectedItem[this.displayField] == key) {
            return;
        }

        var params = {
            key: key,
            index: index || 0,
            size: size || this.maxSize
        };
        var e = {
            type: 'beforequery',
            source: this,
            queryParams: params
        };
        if (this.fireEvent('beforequery', e) === false) return;

        this.table.loadingMask = true;
        this.table.zeroMask = true;
        this.table.mask();

        if (this.queryTimer) {
            clearTimeout(this.queryTimer);
            this.queryTimer = null;
        }
        var combo = this;
        this.queryTimer = setTimeout(function () {
            combo.doQuery(e.queryParams);
        }, this.queryDelay);

    },
    doQuery: function (params) {
        var combo = this;

        if (this.queryAjax) {
            Edo.util.Ajax.abort(this.queryAjax);
        }

        this.queryAjax = Edo.util.Ajax.request({
            url: this.url,
            params: params,
            onSuccess: function (text) {

                if (combo.autoFill) {
                    var data = Edo.util.JSON.decode(text);
                    combo.data.load(data);
                }
                combo.fireEvent('query', {
                    type: 'query',
                    success: true,
                    data: text
                });
                combo.table.unmask();
            },
            onFail: function (code) {
                if (combo.autoFill) {
                    combo.data.clear();
                }
                combo.fireEvent('query', {
                    type: 'query',
                    success: false,
                    errorCode: code
                });
                combo.table.unmask();
            }
        });

    },
    _onTextChange: function (e) {
        Edo.controls.AutoComplete.superclass._onTextChange.call(this, e);
        if (this.field.value == "") {
            this.set('selectedIndex', -1);
        }
    }
    ,
    setValue: function (v) {
        if (v && this.selectedItem != v) {
            this.selectedItem = v;
            this.set('text', v[this.displayField]);
        }
    },
    getValue: function () {
        var o = this.selectedItem;
        if (o) o = o[this.valueField];
        return o;
    }
});
Edo.controls.AutoComplete.regType('autocomplete');

Edo.controls.Lov = function () {
    Edo.controls.Lov.superclass.constructor.call(this);

    this.windowConfig = {
        width: 500,
        height: 300,
        left: 'center',
        top: 'middle',
        toolbar: 'no',
        scrollbars: 'no',
        menubar: 'no',
        resizable: 'no',
        location: 'no',
        status: 'no'
    };
    this.pageConfig = {

};





this.data = {};
};
Edo.controls.Lov.extend(Edo.controls.Trigger, {


    valueField: 'value',

    displayField: 'text',

    url: '',

    params: null,

    pageName: '',

    autoMask: true,
    maskTarget: '#body',
    readOnly: true,
    elCls: 'e-text e-search',
    initEvents: function () {
        Edo.controls.Lov.superclass.initEvents.call(this);

        if (!this.design) {
            this.on('trigger', this._onLovTrigger, this, 0);
        }
    },
    _onLovTrigger: function (e) {
        this.open(this.pageConfig);
    },
    createPageConfigString: function (obj) {
        //坐标尺寸计算
        var sb = [];

        var sw = window.screen.width, sh = window.screen.height - 100;
        var w = obj.width, h = obj.height;
        for (var p in obj) {
            var v = obj[p];
            if (p == 'left' && v == 'center') {
                v = sw / 2 - w / 2;
            }
            if (p == 'top' && v == 'middle') {
                v = sh / 2 - h / 2;
            }
            sb.push(p + '=' + v);
        }
        return sb.join(',');
    },
    getValue: function () {
        if (this.data) return this.data[this.valueField];
        return null;
    },
    setValue: function (v) {
        this._setData(v);
    },
    _setData: function (v) {
        if (!Edo.isValue(v)) return;
        if (v !== this.data) {
            this.data = v;
            this.set('text', v[this.displayField]);
        }
    },

    submit: function (data) {
        if (typeof data != 'string') {
            var o = { data: data };
            data = Edo.util.JSON.encode(o);
            data = Edo.util.JSON.decode(data).data;
        } else {
            data = Edo.util.JSON.decode(data);
        }

        this.setValue(data);

        var e = {
            type: 'submit',
            source: this,
            data: data
        };
        this.fireEvent('submit', e);

        this.data = data;

        this.close();
    },

    open: function (config) {

        if (this.pager) {
            this.close();
        }

        var e = {
            type: 'beforeopen',
            source: this
        };
        if (this.fireEvent('beforeopen', e) === false) return false;

        if (this.autoMask) {
            var obj = (this.maskTarget == '#body' || !this.maskTarget) ? document.body : this.maskTarget;
            if (Edo.type(obj) == 'element') {
                Edo.util.Dom.mask(obj);
            } else {
                obj.mask();
            }
        }
        var config = Edo.applyIf(this.pageConfig, this.windowConfig);
        var cstr = this.createPageConfigString(config);

        var url = this.urlEncode(this.params, this.url.indexOf('?') == -1 ? this.url + '?' : this.url);

        this.pager = window.open(url, this.pageName, cstr);
        this.pager.focus();
        var sf = this;

        window.submitPagerData = function (data) {
            sf.submit(data);
        }

        setTimeout(function () {
            Edo.util.Dom.on(sf.pager, 'unload', sf.onPageUnload, sf);
        }, this.deferBindUnload);

        this.startFocus();

        e.type = 'open';
        e.pager = this.pager;
        this.fireEvent('open', e);
    },
    urlEncode: function (o, pre) {
        var undef, buf = [], key, e = encodeURIComponent;

        for (key in o) {
            undef = typeof o[key] === 'undefined';
            [ ].each.call(undef ? key : o[key], function (val, i) {
                buf.push("&", e(key), "=", (val != key || !undef) ? e(val) : "");
            });
        }
        if (!pre) {
            buf.shift();
            pre = "";
        }
        return pre + buf.join('');
    },
    deferBindUnload: 800,

    close: function () {

        if (this.pager) {
            this.pager.close();
            var obj = this.maskTarget == '#body' ? document.body : this.maskTarget;
            if (Edo.type(obj) == 'element') {
                Edo.util.Dom.unmask(obj);
            } else {
                obj.unmask();
            }
        }

        Edo.util.Dom.un(this.pager, 'unload', this.onPageUnload, this);
        this.stopFocus();
        this.pager = null;

        this.fireEvent('close', {
            type: 'close',
            source: this
        });
    },
    onWindowFocus: function (e) {
        try {
            Edo.util.Dom.focus(this.pager);
        } catch (e) {

        }
    },
    onPageUnload: function (e) {
        this.close();
    },
    startFocus: function () {
        this.stopFocus();
        Edo.util.Dom.on(window, 'focus', this.onWindowFocus, this);
    },
    stopFocus: function () {
        Edo.util.Dom.un(window, 'focus', this.onWindowFocus, this);
    },
    destroy: function () {
        this.close();

        Edo.controls.Lov.superclass.destroy.call(this);
    }
});

Edo.controls.Lov.regType('lov');

Edo.controls.SuperSelect = function () {
    Edo.controls.SuperSelect.superclass.constructor.call(this);


};
Edo.controls.SuperSelect.extend(Edo.controls.ComboBox, {

    multiSelect: true,

    autoHeight: true,

    popupReset: false,

    triggerVisible: false,

    elCls: 'e-superselect',

    delimiter: ',',

    valueField: 'id',

    textField: 'text',
    itemRenderer: function (r, rowIndex) {
        return '<a href="javascript:void(0);" hidefocus id="' + this.createItemId(r) + '" class="' + this.itemCls + '">' + r[this.textField] + '<span id="' + rowIndex + '" class="e-superselect-item-close"></span></a>';
    },

    setValue: function (vv) {
        if (!Edo.isValue(vv)) vv = [];
        if (typeof vv == 'string') vv = vv.split(this.delimiter);
        if (!(vv instanceof Array)) vv = [vv];

        this.selecteds = [];
        vv.each(function (v) {
            var o = {};
            o[this.valueField] = v;  //{id: 1}
            var index = this.data.findIndex(o);
            if (index != -1) {
                this.selecteds.add(this.data.getAt(index));
            }
        }, this);

        this.view.data.load(this.selecteds);

        this.fixAutoSize();
        this.relayout();
    },
    getValue: function () {
        var rs = [];

        this.selecteds.each(function (o) {
            if (this.valueField == '*') rs.add(o);
            else rs.add(o[this.valueField]);
        }, this);

        return rs.join(this.delimiter);
    },

    initEvents: function () {

        this.on('keydown', this._onkeydown, this);
        this.on('click', this._onclick, this);

        this.on('beforeselectionchange', this._onSuperSelectBeforeSelectionChange, this, 0);

        Edo.controls.SuperSelect.superclass.initEvents.call(this);
    },
    _onclick: function (e) {
        if (Edo.util.Dom.hasClass(e.target, 'e-superselect-item-close')) {
            var rowIndex = parseInt(e.target.id);
            var row = this.view.data.getAt(rowIndex);

            this.selecteds.remove(row);
            this.view.data.load(this.selecteds);

            //调整尺寸
            this.fixAutoSize();

            this.fixInput(this.itemIndex);

            this.relayout('select');
        }
    },
    _onkeydown: function (e) {

        var inField = this.field == e.target;
        if (inField && this.field.value != '') return;

        if (this.popupDisplayed) {
            if (e.keyCode == 8 && this.field.value == '') {
                this.hidePopup.defer(100, this);
            }
            return;
        }
        var autoSize = true;

        switch (e.keyCode) {
            case 8:
                e.stopDefault();
                if (inField) {
                    this.view.data.removeAt(this.itemIndex);
                } else {
                    var sels = this.view.getSelecteds();
                    this.view.data.removeRange(sels);

                }
                this.itemIndex -= 1;
                var item = this.view.getByIndex(this.itemIndex);
                if (!item) {
                    this.itemIndex -= 1;
                    item = this.view.getByIndex(this.itemIndex);
                }

                break;
            case 127:
                if (inField) {
                    this.view.data.removeAt(this.itemIndex + 1);
                } else {
                    var sels = this.view.getSelecteds();
                    this.view.data.removeRange(sels);
                }

                break;
            case 37:
            case 38:
                this.itemIndex -= 1;


                this.view.clearSelect();
                break;
            case 39:
                this.itemIndex += 1;
                //            
                this.view.clearSelect();
                break;
            case 40:
                return;
                break;
            case 36: //home
                this.itemIndex = -1;

                this.view.clearSelect();
                break;
            case 35: //end
                this.itemIndex = this.view.data.getCount() - 1;

                this.view.clearSelect();
                break;
            default:
                //            this.view.fixAutoSize();
                //            this.view.relayout('input');
                //            this.fixAutoSize();
                //            this.relayout('input');
                return;
        }
        if (this.itemIndex < -1) {
            this.itemIndex = -1;
        }
        if (this.itemIndex > this.view.data.getCount() - 1) {
            this.itemIndex = this.view.data.getCount() - 1;
        }
        this.fixInput(this.itemIndex);

        this.fixAutoSize();
        this.relayout();

    },
    _onSuperSelectBeforeSelectionChange: function (e) {


        var sel = e.selectedItem;
        if (!sel) return false;

        if (!this.multiSelect) {
            this.selecteds = [];
        }
        this.itemIndex += 1;

        this.selecteds.insert(this.itemIndex, sel);

        if (this.itemIndex < -1) {
            this.itemIndex = -1;
        }
        if (this.itemIndex > this.view.data.getCount() - 1) {
            this.itemIndex = this.view.data.getCount() - 1;
        }

        //调整尺寸


        this.view.data.load(this.selecteds);

        this.field.value = '';

        //this.fixInput(this.itemIndex);
        this.fixInput.defer(10, this, [this.itemIndex]);   //###        

        this.fixAutoSize();
        this.relayout('select');

        return false;
    },
    createChildren: function (el) {
        Edo.controls.SuperSelect.superclass.createChildren.call(this, el);

        //this.selecteds.addRange(this.data.source);

        this.view = Edo.create({
            type: 'dataview',
            render: this.el,
            itemSelector: 'e-superselect-item',
            itemCls: 'e-superselect-item',
            selectedCls: 'e-superselect-item-checked',
            cls: 'e-superselect-inner',

            width: (this.realWidth || this.defaultWidth) - (this.triggerVisible ? 20 : 0),
            //height: this.realHeight || this.defaultHeight,
            autoHeight: true,

            data: this.selecteds,

            emptyText: '请选择',
            tpl: '<%=this.createView()%>',

            valueField: this.valueField,
            textField: this.textField,
            itemRenderer: this.itemRenderer,

            enableDeferRefresh: false,

            createView: function () {
                var data = this.data;
                if (!data || data.getCount() == 0) return '';
                data = this.data.view;

                var text = '<input id="' + this.id + 'text' + '" type="text" class="e-superselect-text" autocomplete="off" size="24" />';

                var sb = [''];
                for (var i = 0, l = data.length; i < l; i++) {
                    var o = data[i];
                    sb[sb.length] = this.itemRenderer(o, i);
                }
                //sb[sb.length] = text;

                //sb[sb.length] = this.itemRenderer(data[0]);

                sb[sb.length] = '<div class="e-clear"></div>';

                //sb[sb.length] =text;
                return sb.join('');
            },
            onmousedown: this._onViewMouseDown.bind(this)
        });

        Edo.util.Dom.addClass(this.field, 'e-superselect-text');

        var viewEl = this.view.el;
        Edo.util.Dom.append(viewEl, this.field);

        this.initInput();


        Edo.util.Dom.on(document, 'mousedown', function (e) {
            if (this.within(e)) {
                Edo.util.Dom.addClass(this.el, this.focusClass);
            } else {
                this.view.clearSelect();
                Edo.util.Dom.removeClass(this.el, this.focusClass);
            }
        }, this);
    },
    createPopup: function () {

        Edo.controls.SuperSelect.superclass.createPopup.apply(this, arguments);

    },
    showPopup: function (e) {
        Edo.controls.SuperSelect.superclass.showPopup.call(this, e);
        this.data.filter(this.filterFn, this);
    },
    filterFn: function (o) {
        var data = this.selecteds;
        if (data.indexOf(o) != -1) return false;

        var text = String(o[this.displayField]);
        if (text.indexOf(this.text) != -1) {
            return true;
        } else {
            return false;
        }
    },
    syncSize: function () {    //设置组件尺寸,并设置容器子元素的所有尺寸!
        Edo.controls.SuperSelect.superclass.syncSize.call(this);


        this.autoInput();
    },
    initInput: function () {
        if (!this.input) {
            this.input = this.field;
            Edo.util.Dom.on(this.input, 'keydown', function (e) {
                this.autoInput();
            }, this);
            Edo.util.Dom.on(this.input, 'keyup', function (e) {
                this.autoInput();
            }, this);
        }

    },
    //自适应输入框的宽度
    autoInput: function () {
        var text = this.field.value;
        if (text != '') {
            Edo.util.Dom.addClass(this.field, 'e-supserselect-text-inputed');

            var size = Edo.util.TextMetrics.measure(this.field, text);

            Edo.util.Dom.setWidth(this.field, size.width + 21); //2是padding-right
        } else {
            Edo.util.Dom.removeClass(this.field, 'e-supserselect-text-inputed');
            this.field.style.width = '15px';
        }
        this.field.style.height = '20px';
        this.field.style.lineHeight = '20px';
        //Edo.util.Dom.repaint(this.input);
        //实时看组件的高度是否有变动, 如果有,则调整尺寸

        //        
        //        this.heightGet = false;
        //        this.view.heightGet = false;
        //        this.relayout('input');
    },
    fixInput: function (index) {
        var items = this.view.getItems()
        var targetItem = items[index];
        var viewEl = this.view.el;
        if (targetItem) {
            Edo.util.Dom.after(targetItem, this.field);
        } else {
            Edo.util.Dom.preend(viewEl, this.field);
        }
        this.focus.defer(50, this);
    },
    _onViewMouseDown: function (e) {
        var el = this.view.getItemEl(e.target);

        if (el) {
            this.itemIndex = this.view.getItemIndex(el);
            return;
        }

        this.view.clearSelect();

        //根据鼠标坐标, 判断在哪一个之后(如果没有找到目标记录, 则加在第一个)        
        this.itemIndex = this.findTargetItem(e.xy);
        this.fixInput(this.itemIndex);
        //if(e.target != this.field){

        //}

        //
    },
    findTargetItem: function (xy) {
        var boxs = [];
        var items = this.view.getItems()
        for (var i = 0, l = items.length; i < l; i++) {
            var item = items[i];
            var box = Edo.util.Dom.getBox(item);
            boxs.push(box);

            box.el = item;
        }
        var elBox = this.getBox();

        for (var i = 0, l = boxs.length; i < l; i++) {
            var box = boxs[i];
            var next = boxs[i + 1];
            if (next && box.y == next.y) {
                box.right = next.x;
            } else {
                box.right = elBox.right;
            }
            box.width = box.right - box.x;
        }
        for (var i = 0, l = boxs.length; i < l; i++) {
            var box = boxs[i];

            if (Edo.util.Dom.isInRegin(xy, box)) {
                return i;      //放指定之后
            }

            if (i == 0) {
                box.right = box.x;
                box.x = elBox.x;
                box.width = box.right - box.x;

                if (Edo.util.Dom.isInRegin(xy, box)) {
                    return -1;   //放第一个
                }
            }
        }
        //否则放最后一个
        return items.length - 1;
    },
    destroy: function () {

        Edo.controls.SuperSelect.superclass.destroy.call(this);
    }
});

Edo.controls.SuperSelect.regType('SuperSelect');    
