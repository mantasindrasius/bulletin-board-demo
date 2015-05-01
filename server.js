var http = require('http');
var spawn = require('child_process').spawn;

var java = '/media/DevZone/jdk1.8.0_40/bin/java';
var port = 8080;

function Server() {
    var proc;

    this.start = function(filename, cb) {
        proc = spawn(java, ['-jar', filename, port]);

        new PortAwaiter(port, 10).await(cb);
    };

    this.stop = function() {
        console.log('Stopping the server');
        proc.kill('SIGINT');
    }
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
                setTimeout(function() { retry(numTries - 1, cb); }, 500);
        }

        http.get('http://localhost:' + port + '/', function (res) {
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
