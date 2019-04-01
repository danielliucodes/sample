package database

import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global

object SlickDatabase {

  // the base query for the Users table
  val users = TableQuery[Users]

  val db = Database.forConfig("h2mem1")

  def getUsers() = {
    db.run(users.result).map( _.map {
      case (name, id) =>
        println(" In Database:  " + name + "\t" + id)
        (name, id)
    })
  }

  def getUser(id: Int) = {
    val filterQuery = users.filter(_.id === id)
    db.run(filterQuery.result.head)
  }
}

class Users(tag: Tag) extends Table[(String, Option[Int])](tag, "USERS") {
  // Auto Increment the id primary key column
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  // The name can't be null
  def name = column[String]("NAME")
  // the * projection (e.g. select * ...) auto-transforms the tupled
  // column values to / from a User
  def * = (name, id.?)
}
