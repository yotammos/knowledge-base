package com.knowledgebase.models

abstract class StockTimeSeriesType {
  override def toString: String = StockTimeSeriesType(this)
}

case object TIME_SERIES_INTRADAY extends StockTimeSeriesType
case object DAILY extends StockTimeSeriesType

object StockTimeSeriesType {
  def apply(stockTimeSeries: StockTimeSeriesType): String =
    stockTimeSeries match {
      case TIME_SERIES_INTRADAY => "TIME_SERIES_INTRADAY"
      case DAILY => "DAILY"
    }
}