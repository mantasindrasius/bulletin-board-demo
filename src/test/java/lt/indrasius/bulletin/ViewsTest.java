package lt.indrasius.bulletin;

import lt.indrasius.nashorn.ScriptEngineBuilder;
import lt.indrasius.nashorn.mocha.*;

/**
 * Created by mantas on 15.4.28.
 */
@JSSpec("js/specs/template-view-spec.js")
public class ViewsTest extends MochaSpec {
    @JSSetup
    public void setup(ScriptEngineBuilder builder) {
        builder.withLoadedScript("js/views/template-view.js")
            .withLoadedScript("bower_components/promise-js/promise.js")
            .withLoadedScript("bower_components/sinon/lib/sinon.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/util/core.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/extend.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/typeOf.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/times_in_words.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/spy.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/call.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/behavior.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/stub.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/mock.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/collection.js")
                //.withLoadedScript("bower_components/sinon/lib/sinon/assert.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/sandbox.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/test.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/test_case.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/match.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/format.js")
                .withLoadedScript("bower_components/sinon/lib/sinon/log_error.js")
        ;
    }
}

/*        require("./sinon/extend");
        require("./sinon/typeOf");
        require("./sinon/times_in_words");
        require("./sinon/spy");
        require("./sinon/call");
        require("./sinon/behavior");
        require("./sinon/stub");
        require("./sinon/mock");
        require("./sinon/collection");
        require("./sinon/assert");
        require("./sinon/sandbox");
        require("./sinon/test");
        require("./sinon/test_case");
        require("./sinon/match");
        require("./sinon/format");
        require("./sinon/log_error");
*/