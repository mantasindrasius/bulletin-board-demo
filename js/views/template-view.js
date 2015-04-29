function TemplateView(template) {
    this.render = function(dataPromise) {
        return dataPromise.then(function(data) {
            return template(data);
        })
    }
}