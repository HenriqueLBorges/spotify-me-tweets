# Spotify-me Tweets

Spotify-me tweets is an App that collects spotify playlists from Twitter. The main objective is interacting with users who ask for a playlist recomendation and then sharing with them one of those playlists collected.

## How it works

Spotify-me tweets makes use of [Twitter Stream API](https://developer.twitter.com/en/docs/tutorials/consuming-streaming-data) to collect spotify playlists from users who share them with a specific hashtag. A scalable app was a important requirement, for this purpose an event driven architecture was built on. *Every tweet that comes from the stream is a new event for example*. [Apache Kafka](https://kafka.apache.org/) was choosed as an event bus for the whole solution. The current topics available in Kafka are:

| topic | What is used for |
|---|---|
| playlists  | This topic is used to exchange all spotify playlists |
| tweetsToPost  | This topic is used as a queue of tweets that will be posted by this app |

## How to run

Well, first you need to create a [Twitter App](https://developer.twitter.com) and then get all keys. You can provide the keys for the application in the [docker-compose.yaml](docker-compose.yaml) then run the command ```docker-compose up``` in the root folder.

## How to build new versions

This project utilizes the [sbt tool](https://www.scala-sbt.org/) together with [sbt assembly plugin](https://github.com/sbt/sbt-assembly) to generate a fat jar file. You can do this running the following command:

```sbt assembly```

## Future work

Although most of the core functionality are already implemented the whole solution is more complex and it will involve some other projects that I will later work on.