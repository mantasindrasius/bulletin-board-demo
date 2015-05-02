function HtmlPage() {
    var includes = [
        'js/http-client.js',
        'js/dust-template-loader.js',
        'bower_components/dustjs-linkedin/dist/dust-full.js',
        'bower_components/dustjs-linkedin-helpers/dist/dust-helpers.js',
        'js/renderer.js',
        'js/pages/front.js',
        'js/views/template-view.js',
        'js/views/add-section.js',
        'js/api-service.js',
        'js/init.js'
    ];

    var includesHtml = includes.map(function(path) {
       return '<script src="' + path + '"></script>'
    }).join('');

    this.render = function(htmlPromise) {
        return htmlPromise.then(function(html) {
            return '<html><body>' + html + includesHtml + '</body></html>';
        });
    }
}