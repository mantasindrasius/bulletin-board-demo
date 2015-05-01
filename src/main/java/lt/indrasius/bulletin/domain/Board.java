package lt.indrasius.bulletin.domain;

/**
 * Created by mantas on 15.5.1.
 */
public class Board {
    private String title;
    private Section[] sections = new Section[0];
    private BoardId id;

    public Board() {
        id = BoardId.create();
    }

    public Board(String title, Section[] sections) {
        this();

        this.title = title;
        this.sections = sections;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Section[] getSections() {
        return sections;
    }

    public void setSections(Section[] sections) {
        this.sections = sections;
    }

    public BoardId getId() {
        return id;
    }

    public void setId(BoardId id) {
        this.id = id;
    }
}
