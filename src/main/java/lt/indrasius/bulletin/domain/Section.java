package lt.indrasius.bulletin.domain;

/**
 * Created by mantas on 15.5.1.
 */
public class Section {
    private SectionId sectionId = SectionId.create();
    private String title;
    private String description;

    public Section() {}

    public Section(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public SectionId getSectionId() {
        return sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
