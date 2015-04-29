function DomAdapter(element) {
    var dd = new diffDOM();

    this.update = function(html) {
        var elmHolder = document.createElement('div');
        elmHolder.innerHTML = html;

        var diff = dd.diff(element, elmHolder);

        dd.apply(element, diff);
    }
}