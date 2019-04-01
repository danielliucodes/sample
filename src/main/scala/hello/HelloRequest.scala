package hello

case class HelloRequest(id: Long, name: String)

case class UsersResponse(users: Seq[(String, Option[Int])])

case class UserResponse(user: (String, Option[Int]))
