var expect = chai.expect;

describe("DOM adapter", function() {
    function click(elm) {
        var evt = document.createEvent("MouseEvents");
        evt.initMouseEvent("click", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
        elm.firstChild.dispatchEvent(evt);
    }

    it("should create a node content", function() {
        var elm = document.createElement('div');
        var adapter = new DomAdapter(elm);

        adapter.update('<span><div>Hello</div></span>');

        expect(elm.innerHTML).to.be.equal('<span><div>Hello</div></span>');
    });

    it("update a node content and persist event binding", function() {
        var elm = document.createElement('div');
        elm.innerHTML = '<span>Hello</span>';

        var testEvent = '';

        elm.firstChild.addEventListener('click', function() {
            testEvent = 'Hello';
        });

        var adapter = new DomAdapter(elm);

        adapter.update('<span><div>Hello World</div></span>');

        click(elm);

        expect(elm.innerHTML).to.be.equal('<span><div>Hello World</div></span>');
        expect(testEvent).to.be.equal('Hello');
    });
});