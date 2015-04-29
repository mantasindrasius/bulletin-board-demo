package lt.indrasius.bulletin;

import lt.indrasius.nashorn.ScriptEngineBuilder;
import lt.indrasius.nashorn.mocha.*;

/**
 * Created by mantas on 15.4.28.
 */
@JSSpec({ "js/specs/renderer-spec.js", "js/specs/dust-template-loader-spec.js" })
public class DustTest extends MochaSpec {
    @JSSetup
    public void setup(ScriptEngineBuilder builder) {
        builder.withLoadedScript("js/renderer.js")
                .withFileSystemFunctions()
                .withScriptFromClassPath("js/specs/init-functions.js")
                .withLoadedScript("js/dust-template-loader.js")
                .withLoadedScript("bower_components/promise-js/promise.js")
                .withLoadedScript("bower_components/dustjs-linkedin/dist/dust-full.js")
                .withLoadedScript("bower_components/dustjs-linkedin-helpers/dist/dust-helpers.js");
    }
}
