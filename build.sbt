name := "spotifyMeTweets"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s" % "6.2",
  "io.circe"  %% "circe-core"   % "0.12.3",
  "io.circe"  %% "circe-generic" % "0.12.3",
  "io.circe" %% "circe-parser" % "0.12.3",
  "org.apache.httpcomponents" % "httpclient" % "4.5.12",
  "org.apache.kafka" %% "kafka" % "2.1.0",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5"
)

mainClass in assembly := Some("App")
assemblyJarName in assembly := "spotify-me-tweets.jar"
