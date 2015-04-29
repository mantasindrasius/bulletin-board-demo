var expect = chai.expect;

describe("renderer", function() {
    it("should render a template", function() {
        var helloTpl = dust.compile('<div>Hello {name}!</div>', 'hello-world');

        dust.loadSource(helloTpl);

        var renderer = new Renderer();

        return renderer
            .render('hello-world', { name: 'World' })
            .then(function(html) {
                expect(html).to.be.equal('<div>Hello World!</div>');
            });
    });
});