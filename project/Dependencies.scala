import sbt._

object Dependencies {

  lazy val finatraVersion = "18.6.0"
  lazy val akkaVersion = "2.5.12"
  lazy val caffeineVersion = "2.7.0"
  lazy val slickVersion = "3.3.0"
  
  lazy val datadogVersion = "2.6.1"
  lazy val guiceVersion = "4.0"
  lazy val loggingVersion = "1.6.4"
  lazy val h2Version = "1.4.191"
  
  val dependencies = Seq(
    "com.twitter" %% "finatra-http" % finatraVersion,

    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,

    "com.github.ben-manes.caffeine" % "caffeine" % caffeineVersion,

    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,

    "com.datadoghq" % "java-dogstatsd-client" % datadogVersion,
    "com.google.inject" % "guice" % guiceVersion,
    "org.slf4j" % "slf4j-nop" % loggingVersion,
    "com.h2database" % "h2" % h2Version
  )
}
