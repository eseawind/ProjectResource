/**
 * @class ServerScript.Wb
 * 
 * ServerScript Wb 提供了服务器端的JavaScript方法库，以方便使用JavaScript语法进行后台的Java编程。
 */
var Base = com.wb.common.Base,
  StringBuilder = java.lang.StringBuilder,
  File = java.io.File,
  HashMap = java.util.HashMap,
  Console = com.wb.tool.Console,
  ConcurrentHashMap = java.util.concurrent.ConcurrentHashMap,
  Integer = java.lang.Integer,
  Double = java.lang.Double,
  JavaString = java.lang.String,
  JavaBoolean = java.lang.Boolean,
  JavaDate = java.util.Date,
  JavaNumber = java.lang.Number,
  Resource = com.wb.common.Resource,
  Str = com.wb.common.Str,
  Value = com.wb.common.Value,
  Var = com.wb.common.Var,
  Encrypter = com.wb.tool.Encrypter,
  DateUtil = com.wb.util.DateUtil,
  DbUtil = com.wb.util.DbUtil,
  FileUtil = com.wb.util.FileUtil,
  JsonUtil = com.wb.util.JsonUtil,
  LogUtil = com.wb.util.LogUtil,
  SortUtil = com.wb.util.SortUtil,
  StringUtil = com.wb.util.StringUtil,
  SysUtil = com.wb.util.SysUtil,
  WebUtil = com.wb.util.WebUtil,
  ZipUtil = com.wb.util.ZipUtil,
  Flow = com.wb.tool.Flow,
  Wb = {
    /**
     * @method sleep
     * Thread.sleep方法的别名。当前线程暂停执行指定数量的毫秒。
     * @param {long} millis 暂停毫秒数。
     */
    sleep: java.lang.Thread.sleep,
    /**
     * 获取带局部变量request和response的操作对象。app在整个请求周期内共享。
     * @param {HttpServletRequest} request 请求对象。
     * @param {HttpServletResponse} response 响应对象。
     * @return {Object} 操作对象。
     */
    getApp: function(request, response) {
      var app;
      app = request.getAttribute('sys.app');
      if (app) return app;
      app = {
        /**
         * 向当前用户的浏览器控制台中输出指定对象的日志信息。
         * @param {Object} object 打印的对象。
         */
        log: function(object) {
          Console.print(request, Wb.encode(object), 'log', true);
        },
        /**
         * 向当前用户的浏览器控制台中输出指定对象的提示信息。
         * @param {Object} object 打印的对象。
         */
        info: function(object) {
          Console.print(request, Wb.encode(object), 'info', true);
        },
        /**
         * 向当前用户的浏览器控制台中输出指定对象的警告信息。
         * @param {HttpServletRequest} request 请求对象。该请求对象用于关联到对应用户。
         * @param {Object} object 打印的对象。
         */
        warn: function(object) {
          Console.print(request, Wb.encode(object), 'warn', true);
        },
        /**
         * 向当前用户的浏览器控制台中输出指定对象的错误信息。
         * @param {Object} object 打印的对象。
         */
        error: function(object) {
          Console.print(request, Wb.encode(object), 'error', true);
        },
        /**
         * 向当前用户的客户端发送指定对象。
         * @param {Object} object 发送的对象。
         * @param {Boolean} [successful] 是否成功标识。如果指定该参数将采用JSON格式封装，该模式仅适用于文件上传模式的数据发送。
         */
        send: function(object, successful) {
          if (Wb.isObject(object) || Wb.isArray(object))
            object = Wb.encode(object);
          if (successful === undefined && WebUtil.jsonResponse(request) &&
            (Wb.isString(object) || object instanceof JavaString))
            successful = true; //upload模式的json格式响应
          if (successful === undefined)
            WebUtil.send(response, object);
          else
            WebUtil.send(response, object, successful);
        },
        /**
         * 获取指定文件控件的单个或多个文件。
         * @param {String} fieldName 文件控件名称。
         * @return {Object[]} 获取的文件数据[{stream:输入流,name:文件名称,size:文件长度},...]，如果未找到返回空数组。
         */
        getFiles: function(fieldName) {
          var files = [],
            index = '',
            prefix = '',
            data;

          function getData(name) {
            var file = request.getAttribute(name);
            if (file instanceof java.io.InputStream)
              return {
                stream: file,
                name: request.getAttribute(name + '__name'),
                size: request.getAttribute(name + '__size')
              };
            else
              return null;
          }
          while ((data = getData(fieldName + prefix + index))) {
            files.push(data);
            if (index === '') {
              index = 0;
              prefix = '@';
            }
            index++;
          }
          return files;
        },
        /**
         * 获取HttpServletRequest和HttpSession对象中指定名称的属性或参数。
         * 如果相同名称的属性或参数都存在则返回优先顺序最高的值。优先顺序依次为
         * HttpSession的attribute，HttpServletRequest的attribute和
         * HttpServletRequest的parameter。如果都不存在则返回null。
         * 如果name参数缺省，将返回所有值组成的对象。
         * @param {String} [name] 参数或属性或称。
         * @param {Boolean} [returnString] 如果获取单个参数，false返回原始对象值，true返回原始对象转换为字符串后的值。默认为false。
         * @return {Object} 请求对象的parameter和attribute中的值组成的对象。
         */
        get: function(name, returnString) {
          if (name === undefined) {
            //获取全部值
            var item, items, jo = WebUtil.fetch(request),
              result = {};
            items = jo.entrySet().iterator();
            while (items.hasNext()) {
              item = items.next();
              result[item.getKey()] = item.getValue();
            }
            return result;
          } else {
            //获取单个值
            return returnString ? WebUtil.fetch(request, name) : WebUtil.fetchObject(request, name);
          }
        },
        /**
         * 该方法同app.get方法，区别在于该方法获取的参数类型为js类型。
         */
        fetch: function(name, returnString) {
          var val = app.get(name, returnString);
          if (Wb.isEmpty(name)) {
            var result = {};
            Wb.each(val, function(k, v) {
              result[k] = Wb.toNative(v);
            });
            return result;
          } else
            return Wb.toNative(val);
        },
        /**
         * 获取指定名称的参数，并转换为布尔类型。
         * @param {String} name 参数名称。
         * @return {Boolean} 参数转换后的布尔值。
         */
        getBool: function(name) {
          return Wb.parseBool(app.get(name));
        },
        /**
         * 获取指定名称的参数，并转换为整数类型。
         * @param {String} name 参数名称。
         * @return {Boolean} 参数转换后的整数值。
         */
        getInt: function(name) {
          return parseInt(app.get(name), 10);
        },
        /**
         * 获取指定名称的参数，并转换为浮点数类型。
         * @param {String} name 参数名称。
         * @return {Boolean} 参数转换后的浮点值。
         */
        getFloat: function(name) {
          return parseFloat(app.get(name));
        },
        /**
         * 获取指定名称的参数，并转换为日期类型。参数必须为有效日期格式的字符串。
         * @param {String} name 参数名称。
         * @return {Boolean} 参数转换后的日期值。
         */
        getDate: function(name) {
          return Wb.strToDate(app.get(name, true));
        },
        /**
         * 遍历对象，并把对象中每个条目的值设置到request的attribute对象，attribute名称为对象中条目的名称。
         * @param {Object} object 设置的对象。
         */
        set: function(object) {
          Wb.each(object, function(k, v) {
            request.setAttribute(k, v);
          });
        },
        /**
         * 判断当前请求对指定模块是否可以访问。
         * @param {String} path 模块路径或其捷径。
         * @return {Boolean} true可以访问，false不可以访问。
         */
        perm: function(path) {
          return com.wb.common.XwlBuffer.canAccess(request, path);
        },
        /**
         * 根据当前语言种类格式化字符串。
         * @param {String} format 模板字符串。
         * @param {String...} values 格式化填充的字符串内容列表。
         */
        format: function() {
          return Str.langFormat(request.getAttribute('sys.useLang'),
            arguments[0], [].slice.call(arguments, 1));
        },
        /**
         * 根据Str.lang为当前语言代码，获取langs中对应值组成的对象。
         * @param {Object} langs 按不同语言定义的值对象。
         * @return {Object} 根据当前语言提取值后组成的对象。
         */
        getLang: function(langs) {
          var list = {},
            lang = app.format('lang');
          Wb.each(langs, function(k, v) {
            list[k] = v[lang];
          });
          return list;
        },
        /**
         * 运行SQL语句，并获取返回结果对象。
         * @param {String} sql 运行的SQL语句。
         * @param {Object} [config] 配置对象。
         * @param {String} config.jndi 数据库连接jndi。
         * @param {String} config.arrayName 执行批处理时，指定数据源来自request中存储的该变量。
         * @param {JSONArray/Array/String} config.arrayData 执行批处理时，指定数据源来自该值。
         * @param {Boolean} config.batchUpdate 是否允许批处理操作。
         * @param {String} config.type  执行何种SQL操作，可为"query","update","execute","call"，默认为自动。
         * @param {String} config.transaction 执行何种数据库事务操作，可为"start","commit","none"。
         * @param {String} config.isolation 数据库事务隔离级别，可为"readCommitted","readUncommitted","repeatableRead","serializable"。
         * @param {Boolean} config.uniqueUpdate 指定插入、更改或删除记录操作是否有且只有1条。
         * @return {Object} 运行SQL语句获得的结果，可能值为结果集，影响记录数或输出参数结果Map。
         */
        run: function(sql, config) {
          var arrayData,
            query = new com.wb.tool.Query();
          if (config && config.arrayData) {
            arrayData = config.arrayData;
            if (!(arrayData instanceof org.json.JSONArray)) {
              if (Wb.isArray(arrayData))
                arrayData = new org.json.JSONArray(Wb.encode(arrayData));
              else
                arrayData = new org.json.JSONArray(arrayData);
              config.arrayData = arrayData;
            }
          }
          Wb.apply(query, {
            request: request,
            sql: sql
          }, config);
          return query.run();
        },
        /**
         * 从数据库获取数据，并输出指定格式的脚本、图片或流数据至客户端。
         * @param {String} sql sql语句。
         * @param {Object} [config] 配置参数对象，见DataProvider控件的使用。
         * @param {Boolean} [returnScript] 是否返回脚本，true返回生成的脚本，false直接输出，默认为false。
         */
        output: function(sql, config, returnScript) {
          var configText, dp = new com.wb.controls.DpControl(); //更高级的使用可访问com.wb.tool.DataProvider
          dp.request = request;
          dp.response = response;
          if (config) {
            Wb.each(config, function(key, value) {
              config[key] = String(value);
            });
            configText = Wb.encode(config);
          } else
            configText = '{}';
          dp.configs = new org.json.JSONObject(configText);
          dp.configs.put('sql', sql);
          if (returnScript)
            return dp.getContent(false);
          else
            dp.create();
        },
        /**
         * 执行上下文绑定的insert, update, delete数据库更新操作。
         * @param {Object} config 配置参数对象，见Updater控件的使用。
         */
        update: function(configs) {
          var updater = new com.wb.tool.Updater();

          Wb.apply(updater, {
            request: request,
            fieldsMap: configs.fieldsMap ? new org.json.JSONObject(Wb.encode(configs.fieldsMap)) : null
          }, configs);
          updater.run();
        }
      };
      request.setAttribute('sys.app', app);
      return app;
    },
    /**
     * 把对象转换成以字符串形式显示的值。
     * @param {Object} object 转换的对象。
     * @return {String} 字符串形式显示的值。
     */
    toString: function(object) {
      return Object.prototype.toString.call(object);
    },
    /**
     * 判断是否是JS字符串。
     * @return {Boolean} true是，false不是。
     */
    isString: function(object) {
      return typeof object === 'string';
    },
    /**
     * 判断是否是JS数字。
     * @return {Boolean} true是，false不是。
     */
    isNumber: function(object) {
      return typeof object === 'number' && isFinite(object);
    },
    /**
     * 判断是否是JS对象。
     * @param {Object} object 判断的对象。
     * @return {Boolean} true是，false不是。
     */
    isObject: function(object) {
      return Wb.toString(object) === '[object Object]';
    },
    /**
     * 判断是否是JS数组。
     * @param {Object} object 判断的对象。
     * @return {Boolean} true是，false不是。
     */
    isArray: function(object) {
      return Wb.toString(object) === '[object Array]';
    },
    /**
     * 判断是否是JS日期。
     * @param {Object} object 判断的对象。
     * @return {Boolean} true是，false不是。
     */
    isDate: function(object) {
      return Wb.toString(object) === '[object Date]';
    },
    /**
     * 判断是否是JS布尔型。
     * @param {Object} object 判断的对象。
     * @return {Boolean} true是，false不是。
     */
    isBoolean: function(object) {
      return typeof object === 'boolean';
    },
    /**
     * 把Java的基本类型值转换为JS类型，其他类型保持不变。在Nashorn中某些类型的JS和Java类型不区分。
     * @param {Object} value 需要转换的值。
     * @return {Object} 转换为js类型后的值。
     */
    toNative: function(value) {
      if (value instanceof JavaString)
        return String(value);
      else if (value instanceof JavaNumber)
        return Number(value);
      else if (value instanceof JavaDate)
        return new Date(value.getTime());
      else if (value instanceof JavaBoolean)
        return value.booleanValue();
      else return value;
    },
    /**
     * 遍历数组或对象的各个元素。
     * @param {Array/Object} data 遍历的数组或对象。
     * @param {Function} fn 每遍历一个元素所执行的回调方法。对于数组传递的参数为值和索引，对于对象传递的参数为名称和值。
     * 如果函数返回false，将中断遍历。
     * @param {Boolean} [reverse] 是否倒序遍历，仅适用于数组，默认为false。
     * @return {Boolean} true遍历完成，否则返回索引号（数组）或false（对象）。
     */
    each: function(data, fn, reverse) {
      if (!data)
        return;
      if (Wb.isArray(data)) {
        var i, j = data.length;
        if (reverse !== true) {
          for (i = 0; i < j; i++) {
            if (fn(data[i], i) === false) {
              return i;
            }
          }
        } else {
          for (i = j - 1; i > -1; i--) {
            if (fn(data[i], i) === false) {
              return i;
            }
          }
        }
      } else {
        var property;
        for (property in data) {
          if (data.hasOwnProperty(property)) {
            if (fn(property, data[property]) === false) {
              return false;
            }
          }
        }
      }
      return true;
    },
    /**
     * 抛出Java Exception异常信息。
     * @param {String} message 异常信息。
     */
    error: function(message) {
      SysUtil.error(message);
    },
    /**
     * 判断值是否为空。如果值为null, undefined, 空串或数组长度为0，则为空。
     * @param {Object} value 需要判断的值，可以为字符串或数组等。
     * @return {Boolean} 如果为空则返回true，否则返回false。
     */
    isEmpty: function(value) {
      if (value === null || value === undefined)
        return true;
      return String(value).length === 0;
    },
    /**
     * 判断指定条目在数组中的位置。
     * @param {Array} array 数组对象。
     * @param {Object} item 需要判断的条目。
     * @return {Number} 条目在数组中的位置。第1个为0，第2个为1，依此类推。-1表示没有找到。
     */
    indexOf: function(array, item) {
      if (!array)
        return -1;
      var i, j;
      j = array.length;
      for (i = 0; i < j; i++)
        if (array[i] === item)
          return i;
      return -1;
    },
    /**
     * 对指定值按指定格式转换为字符串。
     * @param {Object} value 需要格式化的值。
     * @param {String} [format] 格式。
     * @return {String} 格式化后的字符串。
     */
    format: function(value, format) {
      if (!value)
        return '';
      value = new JavaDate(value.getTime());
      if (format)
        return DateUtil.format(value, format);
      else return DateUtil.format(value);
    },
    /**
     * 对字符、数字、日期、对象和数组等进行编码，并转换为以字符串表示的值。
     * @param {Object} o 需要编码的值。
     * @return {String} 编码后的值。
     */
    encode: function(o) {
      var i, j, buf, it, item, items, addComma;
      if (o === null || o === undefined || typeof o === 'function') {
        return 'null'; //Java无undefined类型，因此通一为null。
      } else if (typeof o == 'boolean') {
        return String(o);
      } else if (typeof o == 'number') {
        return isFinite(o) ? String(o) : 'null';
      } else if (Wb.isDate(o)) {
        return '"' + Wb.dateToStr(o) + '"';
      } else if (o instanceof JavaDate) {
        return '"' + DateUtil.dateToStr(o) + '"';
      } else if (Wb.isArray(o) || SysUtil.isArray(o)) {
        buf = new StringBuilder('[');
        j = o.length;
        for (i = 0; i < j; i++) {
          if (i > 0)
            buf.append(',');
          buf.append(Wb.encode(o[i]));
        }
        buf.append(']');
        return buf.toString();
      } else if (SysUtil.isIterable(o)) {
        buf = new StringBuilder('[');
        it = o.iterator();
        addComma = false;
        while (it.hasNext()) {
          if (addComma)
            buf.append(',');
          else
            addComma = true;
          buf.append(Wb.encode(it.next()));
        }
        buf.append(']');
        return buf.toString();
      } else if (Wb.isObject(o)) {
        buf = new StringBuilder('{');
        addComma = false;
        Wb.each(o, function(name, value) {
          if (addComma)
            buf.append(',');
          else
            addComma = true;
          buf.append(Wb.encode(name));
          buf.append(':');
          buf.append(Wb.encode(value));
        });
        buf.append('}');
        return buf.toString();
      } else if (SysUtil.isMap(o)) {
        buf = new StringBuilder('{');
        items = o.entrySet().iterator();
        addComma = false;
        while (items.hasNext()) {
          if (addComma)
            buf.append(',');
          else
            addComma = true;
          item = items.next();
          buf.append(Wb.encode(item.getKey()));
          buf.append(':');
          buf.append(Wb.encode(item.getValue()));
        }
        buf.append('}');
        return buf.toString();
      } else
        return StringUtil.encode(o);
    },
    /**
     * 对以字符串形式表示的值进行解码，并转换为与之对应的值。
     * @param {String} s 需要解码的值。
     * @param {Boolean} [safe] 如果解码过程发生异常，true返回null，false抛出异常。默认为false。
     * @return {Object} 解码后的值。
     */
    decode: function(s, safe) {
      try {
        return eval('(' + s + ')');
      } catch (e) {
        if (safe)
          return null;
        throw 'Invalid JSON "' + Wb.ellipsis(s, 100) + '"';
      }
    },
    /**
     * 判断两个字符串是否相似。如果两个字符串为null,undefined或空串返回true，否则返回转换成JS小写字符串后进行比较的结果。
     * @param {Object} str1 比较的字符串1。
     * @param {Object} str2 比较的字符串2。
     * @return {Boolean} true相似，false不相似。
     */
    isSame: function(str1, str2) {
      var jsStr1, jsStr2;
      if (Wb.isEmpty(str1))
        jsStr1 = '';
      else
        jsStr1 = String(str1);
      if (Wb.isEmpty(str2))
        jsStr2 = '';
      else
        jsStr2 = String(str2);
      return jsStr1.toLowerCase() === jsStr2.toLowerCase();
    },
    /**
     * 判断两个字符串是否相等。如果两个字符串为null,undefined或空串返回true，否则返回转换成JS字符串后进行比较的结果。
     * @param {Object} str1 比较的字符串1。
     * @param {Object} str2 比较的字符串2。
     * @return {Boolean} true相等，false不相等。
     */
    isEqual: function(str1, str2) {
      var jsStr1, jsStr2;
      if (Wb.isEmpty(str1))
        jsStr1 = '';
      else
        jsStr1 = String(str1);
      if (Wb.isEmpty(str2))
        jsStr2 = '';
      else
        jsStr2 = String(str2);
      return jsStr1 === jsStr2;
    },
    /**
     * 把值解析为布尔型。如果值为false, 'false', 0, '0', null, undefined和空串返回false，其他值返回true。
     * 如果指定默认值，则当值为null或undefined时返回此默认值。
     * @param {Object} value 需要解析的值。
     * @param {Boolean} defaultValue 如果value为null或undefined，返回此值。
     * @return {Boolean} 解析后的布尔值。
     */
    parseBool: function(value, defaultValue) {
      if (Wb.isValue(value)) {
        var str = String(value);
        return !(str == 'false' || str == '0' || str === '');
      } else {
        if (defaultValue === undefined)
          return false;
        else
          return defaultValue;
      }
    },
    /**
     * 判断值是否为不是null和undefined值。
     * @param {Object} value 任意值。
     * @return {String} 是值返回true，否则返回false。
     */
    isValue: function(value) {
      return value !== null && value !== undefined;
    },
    /**
     * 在指定对象中查找名称和值匹配的项。
     *
     * Example:
     *
     *     var item = Wb.find([{a: 123}, {a: 'abc'}], 'a', 'abc');
     *
     * @param {Mixed} object 查找的对象。
     * @param {String} name 查找的名称。
     * @param {Object} value 查找的值。
     * @return {Object} 查找到的项。如果没有找到返回null。
     */
    find: function(object, name, value) {
      var result = null;
      Wb.each(object, function(item) {
        if (item[name] === value) {
          result = item;
          return false;
        }
      });
      return result;
    },
    /**
     * 把标准日期格式yyyy-MM-dd HH:mm:ss[.f]的字符串转换成js Date格式。只有格式完全符合标准才会转换成Date格式，否则返回null。
     * @param {String} string 需要转换成日期的字符串。
     * @return {Date} 转换后的日期或null。
     */
    strToDate: function(string) {
      if (Wb.isEmpty(string)) return null;
      var str = String(string),
        date = new Date(parseInt(str.substring(0, 4), 10), parseInt(str.substring(5, 7), 10) - 1,
          parseInt(str.substring(8, 10), 10), parseInt(str.substring(11, 13), 10),
          parseInt(str.substring(14, 16), 10), parseInt(str.substring(17, 19), 10),
          Wb.opt(parseInt(str.substring(20), 10), 0)); //因Rhino bug使用Wb.opt(millSec,0)代替millSec||0
      if (date != 'Invalid Date')
        return date;
      else return null;
    },
    /**
     * 把日期对象转换成标准日期格式yyyy-MM-dd HH:mm:ss.f的字符串。如果日期为空返回null。
     * @param {Date} date 需要转换成字符串的日期。
     * @return {String} 转换后的日期或null。
     */
    dateToStr: function(date) {
      if (!date)
        return null;
      return date.getFullYear() + '-' + Wb.pad(date.getMonth() + 1, 2) + '-' + Wb.pad(date.getDate(), 2) +
        ' ' + Wb.pad(date.getHours(), 2) + ':' + Wb.pad(date.getMinutes(), 2) + ':' +
        Wb.pad(date.getSeconds(), 2) + '.' + date.getMilliseconds();
    },
    /**
     * 把config中的值复制到object中，defaults为config的默认值。
     * @param {Object} object 目标对象。
     * @param {Object} config 源对象。
     * @param {Object} [defaults] config的默认值对象。
     * @return {Object} object对象本身。
     */
    apply: function(object, config, defaults) {
      if (config) {
        Wb.each(config, function(key, value) {
          object[key] = value;
        });
      }
      if (defaults) {
        if (config)
          Wb.each(defaults, function(key, value) {
            if (!config.hasOwnProperty(key))
              object[key] = value;
          });
        else Wb.apply(object, defaults);
      }
      return object;
    },
    /**
     * 如果object中不存在相同名称的值，则把config中的值复制到object中。
     * @param {Object} object 目标对象。
     * @param {Object} config 源对象。
     * @return {Object} object对象本身。
     */
    applyIf: function(object, config) {
      if (config) {
        Wb.each(config, function(key, value) {
          if (!object.hasOwnProperty(key))
            object[key] = value;
        });
      }
      return object;
    },
    /**
     * 返回参数中首个语义非假值。Java类型的空字符串语义视为假值。如果所有参数都为假，返回最后一个参数。
     * 使用此方法可兼容不同引擎对布尔表达式处理的不同。
     * @param {Object...} values 参数值。
     * @return {Object} 首个非假值。
     */
    opt: function() {
      var v, i, j = arguments.length;
      for (i = 0; i < j; i++) {
        v = arguments[i];
        //v如果为java类型需加isEmpty判断
        if (v && !Wb.isEmpty(v))
          return v;
      }
      return v;
    },
    /**
     * 读取文件中的json对象或数组。
     * @param {File} file 文件名称。
     * @return {Object} json对象或数组。
     */
    readJson: function(file) {
      return Wb.decode(FileUtil.readString(file));
    },
    /**
     * 获得对象所有子项的名称组成的数组。
     * @param {Object} object 获取名称列表的对象。
     * @return {Array} 名称数组。
     */
    getNames: function(object) {
      var names = [];
      Wb.each(object, function(key) {
        names.push(key);
      });
      return names;
    },
    /**
     * 获取文本的省略显示。超过指定长度的文本将以“...”替代。
     * @param {String} value 需要省略的文本。
     * @param {Number} length 文本显示的最大长度。
     * @return {String} 省略后显示的文本。
     */
    ellipsis: function(value, length, word) {
      value = String(value);
      if (value && value.length > length) {
        return value.substr(0, length - 3) + "...";
      }
      return value;
    },
    /**
     * 在指定值左边填补指定字符，使值总长度达到指定长度。
     * @param {Mixed} value 需要填补的值。
     * @param {Number} size 填补后的总长度。
     * @param {String} [character] 填补的字符，默认为'0'。
     * @return {String} 填补后的字符串值。
     */
    pad: function(value, size, character) {
      var result = String(value);
      character = Wb.opt(character, '0');
      while (result.length < size) {
        result = character + result;
      }
      return result;
    },
    /**
     * 在指定值右边填补指定字符，使值总长度达到指定长度。
     * @param {Mixed} value 需要填补的值。
     * @param {Number} size 填补后的总长度。
     * @param {String} [character] 填补的字符，默认为'0'。
     * @return {String} 填补后的字符串值。
     */
    padRight: function(value, size, character) {
      var result = String(value);
      character = Wb.opt(character, '0');
      while (result.length < size) {
        result += character;
      }
      return result;
    },
    /**
     * 获取对象数据中各个对象指定名称成员值组成的数组。
     * @param {Object[]} items 对象数组。
     * @param {String} name 对象中的成员名称。
     * @return {Array} 值组成的数组。
     */
    pluck: function(items, name) {
      var i, j = items.length,
        item, data = [];
      for (i = 0; i < j; i++) {
        item = items[i];
        data.push(item ? item[name] : null);
      }
      return data;
    },
    /**
     * 获取结果集当前行所有字段名称和值组成的对象。
     * @param {ResultSet} rs 结果集
     * @param {ResultSetMetaData} [meta] 结果集元数据，如果缺少该值，系统将动态生成。基于性能考虑如果多次调用该方法，请提供该参数。
     * @return {Object} 记录数据组成的对象
     */
    getRecord: function(resultSet, meta) {
      if (!meta)
        meta = resultSet.getMetaData();
      var i, j = meta.getColumnCount(),
        record = {};
      for (i = 0; i < j; i++) {
        record[DbUtil.getFieldName(meta.getColumnLabel(i + 1))] = DbUtil.getObject(resultSet, i + 1, meta.getColumnType(i + 1));
      }
      return record;
    }
  };