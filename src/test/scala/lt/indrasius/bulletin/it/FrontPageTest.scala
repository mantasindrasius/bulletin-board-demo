package lt.indrasius.bulletin.it

import lt.indrasius.bulletin.dao.DAOs
import lt.indrasius.bulletin.domain.{BoardId, Section, Board}
import lt.indrasius.bulletin.framework.JSPageFactory
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.specs2.matcher.{JsonMatchers, Matcher}
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

/**
 * Created by mantas on 15.4.21.
 */
class FrontPageTest extends SpecWithJUnit with JsonMatchers {
  def haveElement(id: String, withBodyThatIs: Matcher[String]): Matcher[String] =
    haveInnerHTML(withBodyThatIs) ^^ { body: String => Jsoup.parse(body).getElementById(id) aka "have script tag" }

  def haveInnerHTML(thatIs: Matcher[String]): Matcher[Element] =
    thatIs ^^ { elm: Element => elm.html() aka "innerHTML" }

  def hasBoardDetails(title: String): Matcher[String] =
    /("title" -> title)

  def hasSection(title: String, description: String): Matcher[String] =
    */("sections").andHave(allOf(aSectionWith(title, description)))

  def aSectionWith(title: String, description: String): Matcher[String] =
    /("title" -> title) and /("description" -> description)

  def hasUserListOf(users: String*): Matcher[String] =
    /("users").andHave(containAllOf(users))

  val boardDao = DAOs.boardDAO()
  val sessionsDAO = DAOs.sessionDAO()

  class Context extends Scope {
    val pageFactory = new JSPageFactory
    val page = pageFactory.getPage("front")

    def givenBoardExists(board: Board) = {
      boardDao.store(board)
      board.id
    }
    
    def givenBoardIsVisitedBy(boardId: BoardId, users: Seq[String]) = {
      users foreach { sessionsDAO.create(boardId, _) }
    }
  }

  "FrontPage" should {
    "have data collected" in new Context {
      val boardId = givenBoardExists {
        Board("Cool Board",
          sections = Array(
            Section("Section 1", "Cool description"),
            Section("Section 2", "Super cool description")))
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
        Board("Cool Board",
          sections = Array(
            Section("Section 1", "Cool description"),
            Section("Section 2", "Super cool description")))
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
