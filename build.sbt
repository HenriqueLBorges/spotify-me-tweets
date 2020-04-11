name := "spotifyMeTweets"

version := "0.1"

scalaVersion := "2.13.1"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "com.danielasfregola" %% "twitter4s" % "6.2"
)
