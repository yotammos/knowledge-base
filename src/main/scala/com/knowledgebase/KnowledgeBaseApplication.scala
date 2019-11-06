package com.knowledgebase

import com.knowledgebase.config.ComponentProvider
import com.knowledgebase.models.{Interest, Interests, Message, UserId}
import com.twitter.app.Flag
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.http.filter.Cors
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch.{Endpoint, _}
import io.finch.circe._

object KnowledgeBaseApplication extends TwitterServer {

  val context = new ComponentProvider
  val port: Flag[Int] = flag("port", 8080, "TCP port for HTTP server")

  val policy: Cors.Policy = Cors.Policy(
    allowsOrigin = _ => Some("*"),
    allowsMethods = _ => Some(Seq("GET", "POST")),
    allowsHeaders = _ => Some(Seq("Accept", "Content-Type"))
  )

  final val hello: Endpoint[Message] = get("hello") {
    context.knowledgeBaseService.getMessage.map(Ok)
  }

  final val getInterests: Endpoint[Seq[Interest]] = get("interests" :: param("userId")) {
    userId: String => context.knowledgeBaseService.getInterests(userId.toLong).map(Ok)
  }

  final val addInterests: Endpoint[Unit] = post("interests" :: param("userId") :: jsonBody[Interests]) {
    (userId: String, interests: Interests) => context.knowledgeBaseService
      .addInterests(userId.toLong, interests.value).map(Ok)
  }

  val api = (hello :+: getInterests :+: addInterests).handle {
    case e: Exception => {
      println(e.getMessage)
      InternalServerError(e)
    }
  }

  val serviceWithCors: Service[Request, Response] = new Cors.HttpFilter(policy).andThen(api.toServiceAs[Application.Json])

  def main(): Unit = {
    log.info(s"Serving the application on port ${port()}")

    val server =
      Http.server
        .withStatsReceiver(statsReceiver)
        .serve(s":${port()}", serviceWithCors)
    closeOnExit(server)

    Await.ready(adminHttpServer)
  }
}
