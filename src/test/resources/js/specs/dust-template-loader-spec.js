var expect = chai.expect;

describe("DustTemplateLoader", function() {
    it("should load a template", function() {
        var loader = new DustTemplateLoader(loadContent);

        return loader.load('test-template').then(function(out) {
            expect(out).to.be.equal('{test}xyz');
        });
    });
});