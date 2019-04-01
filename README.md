# sample
A sample app that includes Finatra, Akka, Slick, and Caffeine.

Some users are preloaded into an H2 Database, and you may retrieve them and store them in a cache.


To run this app via command line, navigate to the project directory and then enter the following command: `./sbt "run -http.port=:8888 -admin.port=:9990"`

Then, go to a browser and enter `http://localhost:8888/users`. This will search the database for all entries.

You may follow this up with `http://localhost:8888/users/1` or `http://localhost:8888/2`, which will search for a specific entry, and then cache the result.


This code does not include any Datadog agent or Datadog Java agent configs.
