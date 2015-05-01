package lt.indrasius.test

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.specs2.matcher._

/**
 * Created by mantas on 15.4.30.
 */
trait ResponseMatchers extends JsonMatchers { this: Matchers =>
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

  def haveId: Matcher[String] =
    contain("\"id\":")
}
