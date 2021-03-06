package lt.indrasius.bulletin.actions;

import lt.indrasius.bulletin.dao.DAOs;

/**
 * Created by mantas on 15.4.21.
 */
public class Actions {
    public static GetBoardAction getBoard() {
        return new GetBoardAction(DAOs.boardDAO());
    }

    public static StoreBoardAction storeBoard() {
        return new StoreBoardAction(DAOs.boardDAO());
    }

    public static GetSessionsAction getSessions() {
        return new GetSessionsAction(DAOs.sessionDAO());
    }

    public static AddSectionAction addSection() {
        return new AddSectionAction(DAOs.boardDAO());
    }
}
