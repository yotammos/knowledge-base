//package com.knowledgebase.models
//
//import io.circe.{Decoder, HCursor}
//
//case class StockInfoResponseBody(open: String, high: String, low: String, close: String)
//
//object StockInfoResponseBody {
//  case class TimeSeries60Min(open: String, high: String, low: String, close: String)
//
//  lazy implicit val timeSeries60MinDecoder: Decoder[TimeSeries60Min] = (c: HCursor) =>
//    for {
//      open <- c.get[String]("1. open")
//      high <- c.get[String]("2. high")
//      low <- c.get[String]("3. low")
//      close <- c.get[String]("4. close")
//    } yield TimeSeries60Min(
//      open,
//      high,
//      low,
//      close
//    )
//
//  lazy implicit val stockInfoResponseBodyDecoder: Decoder[StockInfoResponseBody] =
//    (c: HCursor) => {
//      c.get[TimeSeries60Min]("Time Series(60min)")
//        .
//    }
//}
