package lt.indrasius.bulletin.it

import lt.indrasius.bulletin.domain.{Board, Section}
import lt.indrasius.bulletin.framework.JSPageFactory
import lt.indrasius.test.{BoardHelpers, ResponseMatchers}
import org.specs2.matcher.Matchers
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

/**
 * Created by mantas on 15.4.21.
 */
class FrontPageTest extends SpecWithJUnit with ResponseMatchers with Matchers {

  import BoardHelpers._

  class Context extends Scope {
    val pageFactory = new JSPageFactory
    val page = pageFactory.getPage("front")
  }

  "FrontPage" should {
    "have data collected" in new Context {
      val boardId = givenBoardExists {
        new Board("Cool Board",
          Array(
            new Section("Section 1", "Cool description"),
            new Section("Section 2", "Super cool description")))
      }

      givenBoardIsVisitedBy(boardId, users = Seq("xyz", "def", "abc"))
      
      page.setState(boardId.id)

      page.renderDataState must {
        haveElement(id = "data-board",
          withBodyThatIs = hasBoardDetails(title = "Cool Board") and
            hasSection("Section 1", "Cool description") and
            hasSection("Section 2", "Super cool description")
        ) and haveElement(id = "data-sessions",
            withBodyThatIs = hasUserListOf("xyz", "def", "abc"))
      }
    }

    "render the page with components" in new Context {
      val boardId = givenBoardExists {
        new Board("Cool Board",
          Array(
            new Section("Section 1", "Cool description"),
            new Section("Section 2", "Super cool description")))
      }

      givenBoardIsVisitedBy(boardId, users = Seq("xyz", "def", "abc"))

      page.setState(boardId.id)

      val result = page.render

      println(result)

      result must {
        haveElement(id = "board-sections",
          withBodyThatIs = contain("Section 1")) and
        haveElement(id = "logged-in-users",
          withBodyThatIs = contain(">xyz<"))
      }
    }
  }
}
