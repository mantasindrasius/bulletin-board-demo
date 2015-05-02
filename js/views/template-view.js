function TemplateView(template) {
    var element;

    this.render = function(dataPromise) {
        if (dataPromise == null) dataPromise = Promise.resolve({});

        return dataPromise.then(function(data) {
            return template(data);
        })
    };

    this.bind = function(elm) {
        element = elm;
    }
}