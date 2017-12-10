import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

trait ApiRouter {
  val routes =
    pathSingleSlash {
      complete("hello")
    } ~
      pathPrefix("api") {
        pathPrefix("film") {
          pathSingleSlash {
            get {
              complete("my film")
            }
          } ~
            path("add") {
              post {
                complete("film added")
              }
            } ~
            pathPrefix(Segment) { id =>
              get {
                pathSingleSlash {
                  complete(s"Film $id")
                }
              } ~
                post {
                  path("delete") {
                    complete(s"film $id deleted")
                  }
                } ~
                path("edit") {
                  complete(s"film $id edited")
                }
            }
        }
      }
}
