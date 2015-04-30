var http = require('http');
var spawn = require('child_process').spawn;

function Server() {
    this.start = function(filename, cb) {
        spawn('java', ['-jar', filename]);

        new PortAwaiter(8080, 5).await(cb);
    };

}

function PortAwaiter(port, timeout) {
    this.await = function(cb) {
        retry(timeout, cb);
    };

    function retry(numTries, cb) {
        function processError() {
            if (numTries <= 1)
                cb();
            else
                setTimeout(function() { retry(numTries - 1, cb); }, 1000);
        }

        http.get('http://localhost:' + port + '/index.html', function (res) {
            console.log("Got response: " + res.statusCode);

            cb();
        }).on('error', function (e) {
            console.log("Retries left... " + numTries);

            processError();
        });
    }
}

module.exports = {
    server: new Server
};