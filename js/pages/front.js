function FrontPage(context) {
    function getData(state) {
        var board = getBoard.call(state);
        var session = getSessions.call(state);

        return Promise.all([board, session]);
    }

    function renderData(state) {
        return getData(state).then(function(resultArr) {
            var dataState = renderDataState({
                board: resultArr[0],
                sessions: resultArr[1]
            });

            return '<html><body>' + dataState + '</body></html>';
        }).catch(function(e) {
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
        renderData: renderData
    };
}