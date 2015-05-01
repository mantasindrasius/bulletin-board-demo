var expect = chai.expect;

describe("Front Page", function() {
    var http = new HttpClient('http://localhost:8080/');

    function whenBoardExists(board) {
        return http.post('api/boards', board)
            .then(function(resp) {
                return resp.id;
            });
    }

    it("should create a node content", function() {
        return whenBoardExists({
            title: 'Hello',
            sections: [{ 'title': 'Section 1', description: 'x' }]
        }).then(function(boardId) {
            return http.get(boardId);
        }).then(function(document) {
            expect($(document).find('#board-sections').length).not.to.be.equal(0);
        });
    });
});