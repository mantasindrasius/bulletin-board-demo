var gulp = require('gulp');
var karma = require('karma').server;
var bower = require('gulp-bower');
var server = require('./server.js').server;
var spawn = require('child_process').spawn;

var karmaConf = {
    reporters: ['spec'],
    singleRun: true,
    autoWatch: false,
    basePath: '',
    frameworks: ['mocha', 'chai', 'sinon'],
    files: [
        'bower_components/diff-dom/diffDOM.js',
        'bower_components/promise-js/promise.js',
        'bower_components/dustjs-linkedin/dist/dust-full.js',
        'bower_components/dustjs-linkedin-helpers/dist/dust-helpers.js',
        'js/dust-template-loader.js',
        'js/dom-adapter.js',
        'js/http-client.js',
        'js/spec/init-functions.js',
        'js/spec/dom-adapter-spec.js',
        'js/renderer.js',
        'js/views/template-view.js',
        'src/test/resources/js/specs/dust-template-loader-spec.js',
        'src/test/resources/js/specs/renderer-spec.js',
        'src/test/resources/js/specs/template-view-spec.js',
        { pattern: 'js/templates/*.dust', watched: true, included: false, served: true }
    ],
    browsers: ['PhantomJS_without_security'],
    browserNoActivityTimeout: 1000,
    customLaunchers: {
        PhantomJS_without_security: {
            base: 'PhantomJS',
            flags: ['--web-security=no']
        }
    }

};

gulp.task('bower', function() {
    return bower()
        .pipe(gulp.dest('bower_components/'));
});

gulp.task('test', function(done) {
    karma.start(karmaConf, done);
});

gulp.task('karma', function(done) {
    karmaConf.singleRun = false;
    karma.start(karmaConf, done);
});

gulp.task('compile-server', function(done) {
    var mvn = spawn('mvn', ['package']);

    mvn.stdout.on('data',
        function (data) {
            console.log(data.toString());
        }
    );

    mvn.on('exit', function(code) {
        console.log('compile finished with', code);

        if (code != 0)
            throw 'Maven exited with ' + code;

        done();
    });
});

gulp.task('ready-server', ['compile-server'], function(done) {
    server.start('target/bulletin-board-1.0-SNAPSHOT.one-jar.jar', done);
});

gulp.task('server-e2e', ['ready-server'], function(done) {
    gulp.start('run-server-e2e', function() {
        server.stop();
        done();
    });
});

gulp.task('run-server-e2e', function(done) {
    karma.start({
        reporters: ['spec'],
        singleRun: true,
        autoWatch: false,
        basePath: '',
        frameworks: ['mocha', 'chai', 'sinon'],
        files: [
            'bower_components/promise-js/promise.js',
            'bower_components/jquery/dist/jquery.js',
            'js/spec/server-e2e/dom-parser-fix.js',
            'js/http-client.js',
            'js/spec/server-e2e/**/*.js'
        ],
        browsers: ['PhantomJS_without_security'],
        browserNoActivityTimeout: 1000,
        customLaunchers: {
            PhantomJS_without_security: {
                base: 'PhantomJS',
                flags: ['--web-security=no']
            }
        }
    }, done);
});

gulp.task('all', ['test'], function(done) {
    gulp.start('server-e2e', done)
});

gulp.task('default', ['test']);