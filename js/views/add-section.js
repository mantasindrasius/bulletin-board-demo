function AddSectionView(renderer, onAddSection) {
    var templateView = new TemplateView(renderer.getTemplate('add-section'));
    var element;
    var sectionTitle;
    var addSectionButton;

    function bind(elm) {
        element = elm;
        sectionTitle = elm.getElementsByClassName('section-title-input').item(0);
        addSectionButton = elm.getElementsByClassName('add-section-button').item(0);

        addSectionButton.addEventListener('click', function() {
            onAddSection({
                title: sectionTitle.value
            });
        });
    }

    return {
        render: templateView.render,
        bind: bind
    }
}