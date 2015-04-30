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

gulp.task('compile-server', function() {
    spawn('mvn', ['package']);
});

gulp.task('ready-server', ['compile-server'], function(done) {
    server.start('target/bulletin-board-1.0-SNAPSHOT.one-jar.jar', done);
});

gulp.task('server-e2e', ['ready-server'], function(done) {
    karma.start({
        reporters: ['spec'],
        singleRun: true,
        autoWatch: false,
        basePath: '',
        frameworks: ['mocha', 'chai', 'sinon'],
        files: [
            'bower_components/promise-js/promise.js',
            'js/http-client.js'
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

gulp.task('default', ['test']);