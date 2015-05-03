function API() {
    function handle(method, uri, data) {
        if (uri === 'api/boards') {
            return service.storeBoard(data)
                .then(function(boardId) {
                    return Promise.resolve(JSON.stringify(boardId));
                }).catch(function(err) {
                    return err;
                });
        } else if (uri.indexOf('api/boards/') === 0) {
            var uriParts = uri.split('/');
            var boardId = uriParts[2];

            if (uriParts.length > 3 && uriParts[3].length > 0) {
                return handleSubComponents(method, boardId, uriParts.slice(3), data);
            } else {
                return service.getBoard(boardId).then(function (board) {
                    return Promise.resolve(JSON.stringify(board));
                });
            }
        } else {
            return Promise.resolve(JSON.stringify({ error: 'Unknown URI' + uri }));
        }
    }

    function handleSubComponents(method, boardId, uriParts, data) {
        switch (uriParts[0]) {
            case 'sections':
                return service.addSection(boardId, data).then(function(sectionId) {
                    return Promise.resolve(JSON.stringify(sectionId));
                });
        }
    }

    return {
        handle: handle
    }
}