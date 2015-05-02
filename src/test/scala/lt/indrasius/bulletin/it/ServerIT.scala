package lt.indrasius.bulletin.it

import java.net.{URI, HttpURLConnection, URL}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import lt.indrasius.bulletin.WebServer
import lt.indrasius.bulletin.domain.{BoardId, Section, Board}
import lt.indrasius.test.{ResponseMatchers, BoardHelpers}
import org.specs2.matcher.{Matcher, Matchers}
import org.specs2.mutable.SpecWithJUnit
import org.specs2.specification.Scope

import scala.io.Source
import scala.reflect.ClassTag

/**
 * Created by mantas on 15.4.30.
 */
class ServerIT extends SpecWithJUnit with ResponseMatchers with Matchers {
  import BoardHelpers._

  val serverPort = 8080
  val baseUrl = s"http://localhost:$serverPort/"

  new WebServer(serverPort).start

  lazy val objectMapper = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)

    mapper
  }

  def haveTitle(title: String): Matcher[Board] =
    be_===(title) ^^ { (_: Board).getTitle aka "board title" }

  def haveAnySections: Matcher[Board] =
    be_>(0) ^^ { (_: Board).getSections.length aka "has any sections" }

  class Context extends Scope {
    def responseFor(addr: String, withBody: String = "") = {
      val uri = new URI(baseUrl).resolve(addr)
      val con = uri.toURL.openConnection().asInstanceOf[HttpURLConnection]

      if (withBody.nonEmpty) {
        con.setRequestMethod("POST")
        con.setDoOutput(true)
        con.getOutputStream.write(withBody.getBytes())
      } else
        con.setRequestMethod("GET")

      Source.fromInputStream(con.getInputStream).mkString
    }

    def resultFor[A <: AnyRef](addr: String, withBody: String = "")(implicit tag: ClassTag[A]): A =
      objectMapper.readValue[A](responseFor(addr, withBody).getBytes,
        tag.runtimeClass.asInstanceOf[Class[A]])
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

      responseFor(boardId.id) must {
        haveElement(id = "board-sections",
          withBodyThatIs = contain("Section 1")) and
          haveElement(id = "logged-in-users",
            withBodyThatIs = contain(">xyz<"))
      }
    }

    "accept a POST request and respond" in new Context {
      val body = """{"title":"Hello","sections":[{"title":"Cool","description":"X"}]}"""

      responseFor("api/boards", body) must haveId
    }

    "respond with the Board data" in new Context {
      val body = """{"title":"Hello","sections":[{"title":"Cool","description":"X"}]}"""

      val boardId = resultFor[BoardId]("api/boards", body)
      resultFor[Board](s"api/boards/${boardId.id}") must haveTitle("Hello") and haveAnySections
    }

    "respond with static file" in new Context {
      responseFor("js/renderer.js") must contain("function")
    }
  }
}
