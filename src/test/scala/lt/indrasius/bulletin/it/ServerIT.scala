package lt.indrasius.bulletin.it

import java.net.{HttpURLConnection, URL}

import lt.indrasius.bulletin.WebServer
import lt.indrasius.bulletin.domain.{Section, Board}
import lt.indrasius.test.{ResponseMatchers, BoardHelpers}
import org.specs2.matcher.Matchers
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

import scala.io.Source

/**
 * Created by mantas on 15.4.30.
 */
class ServerIT extends SpecWithJUnit with ResponseMatchers with Matchers {
  new WebServer(8080).start

  import BoardHelpers._

  class Context extends Scope {
    def responseFor(addr: String, withBody: String = "") = {
      val url = new URL(addr)
      val con = url.openConnection().asInstanceOf[HttpURLConnection]

      if (withBody.nonEmpty) {
        con.setRequestMethod("POST")
        con.setDoOutput(true)
        con.getOutputStream.write(withBody.getBytes())
      } else
        con.setRequestMethod("GET")

      Source.fromInputStream(con.getInputStream).mkString
    }
  }

  "Server" should {
    "respond with HTML content" in new Context {
      val boardId = givenBoardExists {
        new Board("Cool Board",
          Array(
            new Section("Section 1", "Cool description"),
            new Section("Section 2", "Super cool description")))
      }

      givenBoardIsVisitedBy(boardId, users = Seq("xyz", "def", "abc"))

      responseFor(s"http://localhost:8080/${boardId.id}") must {
        haveElement(id = "board-sections",
          withBodyThatIs = contain("Section 1")) and
          haveElement(id = "logged-in-users",
            withBodyThatIs = contain(">xyz<"))
      }
    }

    "accept a POST request and respond" in new Context {
      val body = """{"title":"Hello","sections":[{"title":"Cool","description":"X"}]}"""

      val resp = responseFor(s"http://localhost:8080/api/boards", body)

      resp must haveId
    }
  }
}
