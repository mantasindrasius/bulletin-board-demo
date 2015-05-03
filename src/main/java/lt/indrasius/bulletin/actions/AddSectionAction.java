package lt.indrasius.bulletin.actions;

import lt.indrasius.bulletin.dao.BoardDAO;
import lt.indrasius.bulletin.domain.Board;
import lt.indrasius.bulletin.domain.BoardId;
import lt.indrasius.bulletin.domain.Section;
import lt.indrasius.bulletin.domain.SectionId;

import java.util.Arrays;

/**
 * Created by mantas on 15.5.3.
 */
public class AddSectionAction {

    private final BoardDAO boardDAO;

    public AddSectionAction(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }

    public SectionId call(String boardId, Section section) {
        Board board = boardDAO.get(BoardId.apply(boardId));
        Section[] sections = board.getSections();
        Section[] newSections = Arrays.copyOf(sections, sections.length + 1);

        newSections[sections.length] = section;

        board.setSections(newSections);
        boardDAO.store(board);

        return section.getSectionId();
    }
}
