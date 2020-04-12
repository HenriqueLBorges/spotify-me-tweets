package domain.entities

import java.time.Instant

case class Tweet(id: Long, content: String, from: String,
                 likes: Int, rts: Long, isRT: Boolean, when: Instant)
