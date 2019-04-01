package hello

import java.time.Clock

import akka.actor.{ActorRefFactory, ActorSystem}
import com.google.inject.Module
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.inject.{Injector, TwitterModule}
import database.SlickDatabase.users
import slick.jdbc.H2Profile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}

object HelloWorldServerMain extends HelloWorldServer

object HelloServerModule extends TwitterModule {
  override def configure(): Unit = {
    implicit val actorSystem = ActorSystem("hello")
    implicit val ec = actorSystem.dispatcher

    bind[Clock].toInstance(Clock.systemUTC)
    bind[ActorSystem].toInstance(actorSystem)
    bind[ActorRefFactory].toInstance(actorSystem)
    bind[ExecutionContext].toInstance(ec)
  }

  override def singletonStartup(injector: Injector) {
    val actorSystem = injector.instance[ActorSystem]
    implicit val ec: ExecutionContext = actorSystem.dispatcher

    val db = Database.forConfig("h2mem1")
    println("Initializing DB")
    Await.result(db.run(DBIO.seq(
      // create the schema
      users.schema.create,

      // insert two User instances
      users += ("John Doe", None),
      users += ("Fred Smith", None),

      // print the users (select * from USERS)
      users.result.map(println)(ec)
    )), Duration.Inf)
    println("Initialized DB")

    println(".....")
  }

  override def singletonShutdown(injector: Injector) {
    Await.result(injector.instance[ActorSystem].terminate, 7 seconds)

    val db = Database.forConfig("h2mem1")
    db.close
  }
}

class HelloWorldServer extends HttpServer {
  override def modules: Seq[Module] = Seq(
    HelloServerModule
  )

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[HelloWorldController]
  }
}
