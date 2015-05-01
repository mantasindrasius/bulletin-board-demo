var HttpClient = function(baseUrl) {
    var me = this;

    this.get = function(url) {
        return me.request('GET', url);
    };

    this.post = function(url, data) {
        var postData = JSON.stringify(data);

        return me.request('POST', url, postData, 'application/json');
    };

    this.request = function(method, relative, data, contentType) {
        var url = baseUrl + relative;
        console.log(method + ' ' + url);

        return new Promise(function(fulfill, reject) {

            var xhr = new XMLHttpRequest();

            xhr.onreadystatechange = function () {
                if (this.readyState == 4) {
                    if (this.status == 200)
                        handleContent(this, fulfill);
                    else if (this.status == 0)
                        reject('Connection refused');
                    else
                        reject(this.statusText);
                }
            };

            xhr.open(method, url);

            if (contentType != null)
                xhr.setRequestHeader("Content-Type", contentType);

            if (data != null)
                xhr.send(data);
            else
                xhr.send();
        });
    };

    function handleContent(xhr, fulfill) {
        var contentType = xhr.getResponseHeader('Content-Type');

        console.log(contentType, xhr.responseText);

        if (/^\s*text\/html\s*(?:;|$)/i.test(contentType)) {
            var parser = new DOMParser();
            var document = parser.parseFromString(xhr.responseText, "text/html");

            fulfill(document);
        } else if (/^\s*application\/json\s*(?:;|$)/i.test(contentType)) {
            fulfill(JSON.parse(xhr.responseText));
        } else {
            fulfill(xhr.responseText);
        }
    }
};
