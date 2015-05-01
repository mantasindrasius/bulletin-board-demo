function API() {
    var ArgumentAdapter = Java.type("lt.indrasius.nashorn.jsify.ArgumentAdapter");

    function handle(method, uri, data) {
        if (uri == 'api/boards') {
            var board = ArgumentAdapter.adapt(data, "lt.indrasius.bulletin.domain.Board");

            return storeBoard.call(board)
                .then(function(boardId) {
                    return Promise.resolve(JSON.stringify(boardId));
                }).catch(function(err) {
                    return err;
                });
        }
    }

    return {
        handle: handle
    }
}