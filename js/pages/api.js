function API() {
    function handle(method, uri, board) {
        if (uri == 'api/boards') {
            return service.storeBoard(board)
                .then(function(boardId) {
                    return Promise.resolve(JSON.stringify(boardId));
                }).catch(function(err) {
                    return err;
                });
        } else {
            var boardId = uri.split('/').pop();
            return service.getBoard(boardId).then(function(board) {
                return Promise.resolve(JSON.stringify(board));
            });
        }
    }

    return {
        handle: handle
    }
}