package com.knowledgebase.models

abstract class StockInfoInterval {
  override def toString: String = StockInfoInterval(this)
}

case object FIVE_MINUTES extends StockInfoInterval
case object SIXTY_MINUTES extends StockInfoInterval

object StockInfoInterval {
  def apply(stockInfoInterval: StockInfoInterval): String =
    stockInfoInterval match {
      case FIVE_MINUTES => "5min"
      case SIXTY_MINUTES => "60min"
    }
}
