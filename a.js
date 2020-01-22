(function(w, doc){

    function encodeFormData(data){
        var pairs = [], regexp = /%20/g;

        var value;
        for(var key in data){
            value = data[key].toString();

            // encodeURIComponent encodes spaces as %20 instead of "+"
            pairs.push(w.encodeURIComponent(key).replace(regexp, '+') +
                '=' + w.encodeURIComponent(value).replace(regexp, '+'));
        }

        return pairs.join('&');
    }

    var toString = Object.prototype.toString;

    function isFunction(obj){
        return toString.call(obj) === '[object Function]';
    }

    function isNumber(obj){
        return toString.call(obj) === '[object Number]';
    }

    function _siteId(prefix){
        var id = (new Date()).getTime() + Math.floor(Math.random() * 100000);
        return prefix ? prefix + '' + id : id;
    }

    var head = doc.getElementsByTagName('head')[0] || doc.documentElement;
    var baseElement = head.getElementsByTagName('base')[0];

    var READY_STATE_RE = /^(?:loaded|complete|undefined)/;

    var scriptOnLoad = function(node,callback){
        node.onload = node.onreadystatechange = function(){
            if(READY_STATE_RE.test(node.readyState)){

                // only run once and handle memory leak in IE
                node.onload = node.onreadystatechange = null;

                // remove the script to reduce memory leak
                head.removeChild(node);

                node = null;
            }
        };
    };

    function getScript(url, charset ,errorCallback){
        var node = doc.createElement('script');
        node.src = url;
        if(errorCallback){
            node.onerror = errorCallback;
        }
        if(charset){
            node.charset = charset;
        }

        node.async = true;

        scriptOnLoad(node);

        // IE6 Bug: http://bugs.jquery.com/ticket/2709
        if(baseElement){
            head.insertBefore(node,baseElement);
        } else {
            head.appendChild(node);
        }

        return node;
    }

    w.jsonp = function(obj){
        var jsonpName = obj.jsonp || 'callback';
        var jsonpCallback = _siteId('jsonpcallback');
        var jsonpErrorCallback = _siteId('jsonperror');
        var jsonpParams = jsonpName + '=' + jsonpCallback;

        var timeout = window.setTimeout(function(){
            if(isFunction(obj.ontimeout)){
                obj.ontimeout();
            }
        }, 5000);

        window[jsonpCallback] = function(data){
            window.clearTimeout(timeout);

            if(isFunction(obj.onsuccess)){
                obj.onsuccess(data);
            }

        };

        window[jsonpErrorCallback] = function(data){
            window.clearTimeout(timeout);

            if(isFunction(obj.onerror)){
                obj.onerror();
            }

        };

        var andTag = obj.url.indexOf('?') > 0 ? '&' : '?';

        if(obj.data){
            getScript(obj.url + andTag + encodeFormData(obj.data) + '&' + jsonpParams, obj.charset ,window[jsonpErrorCallback]);
        } else {
            getScript(obj.url + andTag + jsonpParams, obj.charset, window[jsonpErrorCallback]);
        }

    };

    var _ajaxDefaults = {
        timeout: 5000
    };

    function createXMLHttpRequest(){
        var request;
        if(w.XMLHttpRequest){
            // IE7+, Firefox, Chrome, Opera, Safari
            request = new XMLHttpRequest();
        } else {
            // IE5, IE6
            request = new ActiveXObject('Miscrosoft.XMLHTTP');
        }
        return request;
    }


    function getResponse(request){
        switch(request.getResponseHeader('Content-Type')){
            case 'text/xml' :
                return request.responseXML;
            case 'text/json' :
            case 'text/javascript' :
            case 'application/javascript' :
            case 'application/x-javascript' :
                // users should parse the result
                return request.responseText;
            default :
                return request.responseText;
        }
    }

    function ajax(obj){
        var request = createXMLHttpRequest();

        var type = obj.type.toUpperCase();

        // enable timeout
        var timer;
        if(isNumber(obj.timeout)){
            timer = w.setTimeout(function(){

                request.abort();
                request.onreadystatechange = null;

                if(isFunction(obj.ontimeout)){
                    obj.ontimeout();
                }

            }, obj.timeout || _ajaxDefaults.timeout);
        }


        request.onreadystatechange = function(){
            if(request.readyState === 4){
                // request done, cancel timeout
                if(timer) {
                    w.clearTimeout(timer);

                    timer = null;
                }

                // 200 - 300 : success
                // 304 : not modified
                // 1223 : IE bug when 204
                if(request.status >= 200 && request.status < 300 ||
                    request.status === 304 || request.status === 1223){
                    if(isFunction(obj.onsuccess)){
                        obj.onsuccess(getResponse(request));
                    }
                } else {
                    if(isFunction(obj.onerror)){
                        obj.onerror(request.status, request.statusText);
                    }
                }
            }
        };

        var async = false;
        if (obj.hasOwnProperty('async')) {
            async = obj.async;
        }

        if(type === 'GET'){
            var target = obj.url;
            if(obj.data){
                target = obj.url + '?' + encodeFormData(obj.data);
            }
            request.open('GET', target, async);
            request.send(null);
        } else if(type === 'POST'){
            request.open('POST', obj.url, async);
            request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            request.send(encodeFormData(obj.data));
        }
        return request;
    }

    w.ajax = ajax;
})(window, document);