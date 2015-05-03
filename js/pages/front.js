function FrontPage(context, state) {
    var renderer = new Renderer();
    var sectionsView = new TemplateView(renderer.getTemplate('sections'));
    var loggedInView = new TemplateView(renderer.getTemplate('logged-in-users'));
    var addSectionView = AddSectionView(renderer, function(newSection) {
        addSection(newSection);
    });

    var element, board, sessions;

    function getData(state) {
        var boardLoading = service.getBoard(state);
        var sessionsLoading = service.getSessions(state);

        return Promise.all([boardLoading, sessionsLoading]).then(function(resultArr) {
            board = resultArr[0];
            sessions = resultArr[1];

            return {
                board: board,
                sessions: sessions
            };
        });
    }

    function render(state) {
        var sectionsRendering = sectionsView.render(service.getBoard(state));
        var loggedInViewRendering = loggedInView.render(service.getSessions(state));
        var addSectionRendering = addSectionView.render();

        return Promise
            .all([sectionsRendering, loggedInViewRendering, addSectionRendering])
            .then(function(htmls) {
                return htmls.join('\n');
            })
            .catch(function(e) {
                return renderError(e);
            });
    }

    function bind(elm) {
        element = elm;

        sectionsView.bind(document.getElementById('board-sections'));
        loggedInView.bind(document.getElementById('logged-in-users'));
        addSectionView.bind(document.getElementById('add-section'));
    }

    function renderData(state) {
        return getData(state).then(function(data) {
            var dataState = renderDataState(data);

            return '<html><body>' + dataState + '</body></html>';
        }).catch(function(e) {
            e.printStackTrace();
            return renderError(e);
        });
    }

    function renderDataState(state) {
        var stateText = '';

        for (key in state) {
            stateText +=
                '<script id="data-' + key + '" type="application/json">' +
                JSON.stringify(state[key]) +
                '</script>';
        }

        return stateText;
    }

    function renderError(err) {
        return "ERROR: "+ err;
    }

    function addSection(section) {
        if (board == null)
            service.getBoard(state).then(addSectionTo(section));
        else
            addSectionTo(section)(board);
    }

    function addSectionTo(section) {
        return function(currentBoard) {
            return service.addSection(state, section).then(function(sectionRef) {
                section.sectionId = sectionRef.id;
                currentBoard.sections.push(section);
                board = currentBoard;

                sectionsView.render(Promise.resolve(board)).then(function(html) {
                    var div = document.createElement('div');
                    div.innerHTML = html;

                    var elmToReplace = div.firstChild;

                    var boardSections = document.getElementById('board-sections');

                    boardSections.parentNode.replaceChild(elmToReplace, boardSections);
                });
            });
        }
    }

    return {
        render: render,
        renderData: renderData,
        bind: bind
    };
}