function ApiService(httpClient) {
    this.getBoard = function(boardId) {
        return httpClient.get('api/boards/' + boardId);
    };

    this.addSection = function(boardId, section) {
        return httpClient.post('api/boards/' + boardId + '/sections', section);
    }
}