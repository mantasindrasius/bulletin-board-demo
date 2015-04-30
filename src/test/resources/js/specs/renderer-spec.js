var expect = chai.expect;

describe("renderer", function() {
    dust.loadSource(dust.compile('<div>Hello {name}!</div>', 'hello-world'));

    it("should render a template", function() {
        var renderer = new Renderer();

        return renderer
            .render('hello-world', { name: 'World' })
            .then(function(html) {
                expect(html).to.be.equal('<div>Hello World!</div>');
            });
    });

    it("should create a rendering function for a template", function() {
        var renderer = new Renderer();

        var template = renderer
            .getTemplate('hello-world');

        return template({ name: 'World' })
            .then(function(html) {
                expect(html).to.be.equal('<div>Hello World!</div>');
            });
    });
});