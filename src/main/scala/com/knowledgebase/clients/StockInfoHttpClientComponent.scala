package com.knowledgebase.clients

import com.knowledgebase.models.{FIVE_MINUTES, StockInfoInterval, StockTimeSeriesType, TIME_SERIES_INTRADAY}
import com.knowledgebase.utils.Defaults
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Method, Request, Response, Status}
import com.twitter.util.Future
import io.circe._

trait StockInfoHttpClientComponent {
  def stockInfoHttpClient: StockInfoHttpClient

  class StockInfoHttpClient(apiKey: String) {
    private val host = Defaults.STOCK_HOST
    private val client: Service[Request, Response] = Http.client.withTls(host).newService(s"$host:${Defaults.STOCK_PORT}")

    def getStockData(symbol: String, seriesType: StockTimeSeriesType = TIME_SERIES_INTRADAY, interval: StockInfoInterval = FIVE_MINUTES): Future[String] =
      client(
        Request(Method.Get, s"/query?function=$seriesType&symbol=$symbol&interval=$interval&apikey=$apiKey")
      ) onSuccess { rep: Response =>
        println(s"Success getting $symbol data, status code = " + rep.statusCode)
        println("content = " + rep.getContentString())
      } onFailure { error: Throwable =>
        println(s"failed getting $symbol data, error = " + error.getMessage)
      } map { res =>
        if (res.status == Status.Ok)
          parser.decode(res.getContentString())(Decoder[String].prepare(
            _.downField("Time Series (60min)").downField("2019-11-05 15:30:00").downField("4. close")
          )) match {
            case Right(close) => close
            case Left(error) => throw new Exception("failed parsing get stock info response body")
          }
        else
          throw new Exception("failed parsing get stock info response body")
      }
  }
}
