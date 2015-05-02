function ApiService(httpClient) {
    this.getBoard = function(boardId) {
        return httpClient.get('api/boards/' + boardId);
    }
}