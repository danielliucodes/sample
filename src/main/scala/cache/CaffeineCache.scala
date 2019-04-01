package cache

import java.util.concurrent.{Executor, TimeUnit}

import com.github.benmanes.caffeine.cache.{AsyncCacheLoader, Caffeine}
import database.SlickDatabase

import scala.compat.java8.FutureConverters._

object CaffeineCache {

  val cache = Caffeine
      .newBuilder()
      .recordStats()
      .expireAfterWrite(5, TimeUnit.MINUTES)
      .maximumSize(500)
    .buildAsync(new AsyncCacheLoader[String, (String, Option[Int])] {
        def asyncLoad(k: String, e: Executor) = {
          namedDatabaseQuery(k).toJava.toCompletableFuture
        }
    })

  def getUsersCache() = {
    SlickDatabase.getUsers()
  }

  def namedDatabaseQuery(id: String) = {
    SlickDatabase.getUser(id.toInt)
  }

  def getUser(id: String) = {
    cache.get(id).toScala
  }
}
