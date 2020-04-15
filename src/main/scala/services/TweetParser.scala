package services

import adapters.HttpAdapter
import domain.entities.Tweet

import scala.util.matching.Regex

/** TweetParser service */
object TweetParser {

  /** Verifies if the Twitter contains a link inside it's body.
   * @param tweet tweet where links will be searched.
   */
  def getSpecificLink(tweet: Tweet, host: String): Option[String] = {
    val link = this.getLink(tweet).getOrElse("")
    if(link == ""){
      Option.empty
    } else if(link contains(host)){
      Option.apply(link)
    } else {
      Option.empty
    }
  }

  /** Get a link from the tweet.
   * @param tweet tweet where links will be searched.
   */
  def getLink(tweet: Tweet): Option[String] = {
    val link = this.extractLinks(tweet).getOrElse("")
    if (link == "") {
      Option.empty
    } else if (this.isShortenedURL(link)) {
      Option.apply(HttpAdapter.get(link).headers("location"))
    } else {
      Option.apply(link)
    }
  }

  /** Extract links from a tweet.
   * @param tweet tweet where links will be searched.
   */
  private def extractLinks(tweet: Tweet): Option[String] = {
    val pattern = new Regex("(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))")
    pattern.findFirstIn(tweet.content)
  }

  /** Verifies if the tweet is shortened
   * @param link a website link
   */
  private def isShortenedURL(link: String): Boolean = {
    val shortenedURLHost = "t.co/"
    link.contains(shortenedURLHost)
  }

}
