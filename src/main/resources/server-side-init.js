(function() {
    var dustLoader = new DustTemplateLoader(fs.readFile);

    dust.onLoad = function(template, callback) {
        dustLoader.load(template)
            .then(function(result) {
                callback(undefined, result);
            })
            .catch(function(err) {
                callback(err, undefined);
            })
    }
})(this);