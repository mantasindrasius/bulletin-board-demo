function FrontPage(context) {
    var renderer = new Renderer();
    var sectionsView = new TemplateView(renderer.getTemplate('sections'));
    var loggedInView = new TemplateView(renderer.getTemplate('logged-in-users'));

    function render(state) {
        var sectionsRendering = sectionsView.render(getBoard.call(state));
        var loggedInViewRendering = loggedInView.render(getSessions.call(state));

        return Promise
            .all([sectionsRendering, loggedInViewRendering])
            .then(function(htmls) {
                return '<html><body>' + htmls.join('') + '</body></html>';
            })
            .catch(function(e) {
                return renderError(e);
            });
    }

    function getData(state) {
        var board = getBoard.call(state);
        var session = getSessions.call(state);

        return Promise.all([board, session]).then(function(resultArr) {
            return {
                board: resultArr[0],
                sessions: resultArr[1]
            };
        });
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

    return {
        render: render,
        renderData: renderData
    };
}