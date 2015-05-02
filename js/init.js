var httpClient = new HttpClient('/');
var service = new ApiService(httpClient);
var state = location.pathname.substr(1);

var dustLoader = new DustTemplateLoader(httpClient.get);

dust.onLoad = function(template, callback) {
    dustLoader.load(template)
        .then(function(result) {
            callback(undefined, result);
        })
        .catch(function(err) {
            callback(err, undefined);
        })
};

var page = FrontPage(this, state);
var body = document.getElementsByTagName('body').item(0);

page.bind(body);