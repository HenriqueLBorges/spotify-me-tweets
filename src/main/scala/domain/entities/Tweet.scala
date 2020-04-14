package domain.entities

import java.time.Instant

/** Converts a tweet to tweet entity.
 *  @param id the identification of this tweet from Twitter API.
 *  @param content the content from this tweet.
 *  @param from the user who posted it.
 *  @param likes the quantity of likes this tweet received.
 *  @param rts the quantity of times that this tweet was retweeted.
 *  @param isRT denotes if this is RT or not.
 *  @param when when this tweet was posted.
 */
case class Tweet(id: Long, content: String, from: String,
                 likes: Int, rts: Long, isRT: Boolean, when: Instant)
