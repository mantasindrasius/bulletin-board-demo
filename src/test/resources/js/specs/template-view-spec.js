var expect = chai.expect;

describe("TemplateView", function() {
    it("should render", function() {
        var template = sinon.stub();

        template.returns('XYZ');

        var view = new TemplateView(template);
        var dataPromise = Promise.resolve('Hello');

        return view.render(dataPromise).then(function(html) {
            expect(html).to.be.equal('XYZ');
            expect(template.calledWith('Hello')).to.be.true;
        });
    });
});