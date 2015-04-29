function Renderer() {
    this.render = function(template, data) {
        return new Promise(function(fulfill, reject) {
            dust.render(template, data, function(err, out) {
                if (err) reject(err);
                else fulfill(out);
            });
        });
    }
}