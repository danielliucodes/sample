package hello

import akka.actor.ActorSystem
import cache.CaffeineCache
import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scala.concurrent.ExecutionContext

class HelloWorldController @Inject() (actorSystem: ActorSystem) extends Controller {

  protected implicit def executor: ExecutionContext = actorSystem.dispatcher

  get("/users") { request: Request =>

    println("GET all users request")

    println("Check database for all entries - does not cache results")

    val result = CaffeineCache.getUsersCache().map(UsersResponse)

    println("GET all users response")

    result
  }

  get("/users/:id") { request: Request =>
    println("GET one user request")

    val id = request.getParam("id")

    val result = CaffeineCache.getUser(id).map(UserResponse)

    println("Check cache for existing entry - if entry already exists, the future will complete")
    println(CaffeineCache.getUser(id))

    println("GET one user response")

    result
  }
}
