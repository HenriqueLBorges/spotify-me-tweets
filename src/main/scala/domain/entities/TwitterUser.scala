package domain.entities

import java.time.Instant

/** TwitterUser entity.
 *  @param id the identification of this user from Twitter API.
 *  @param created_at when this user was created.
 *  @param email user's email
 *  @param favourites_count user's favourite count.
 *  @param followers_count user's followers count
 *  @param location user's location count.
 *  @param screen_name user's screen name.
 *  @param time_zone user's timezone
 *  @param utc_offset user's UTC offset
 *  @param verified if the user has a verified account.
 */
case class TwitterUser(id: Long, created_at: Instant, email: String, favourites_count: Int,
                       followers_count: Int, location: String, screen_name: String, time_zone: String, utc_offset: Int,
                       verified: Boolean)
