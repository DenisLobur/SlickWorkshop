import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import model.Film
import io.circe.syntax._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

trait ApiRouter extends MyDatabases with FailFastCirceSupport {
  val routes =
    pathSingleSlash {
      complete("hello")
    } ~
      pathPrefix("api") {
        pathPrefix("film") {
          pathSingleSlash {
            get {
              complete("all films")
            }
          } ~
            path("add") {
              post {
                complete("film added")
              }
            } ~
            pathPrefix(LongNumber) { id =>
              get {
                pathSingleSlash {
                  onSuccess(filmRepository.getById(id.toLong)) {
                    (result, _, _, _) => complete(result.asJson)
                  }
                }
              } ~
                post {
                  path("delete") {
                    onSuccess(filmRepository.delete(id.toLong)) {
                      result => complete(StatusCodes.OK)
                    }
                  }
                } ~
                pathPrefix("edit") {
                  complete(s"film $id edited")
                }
            }
        }
      }
}
