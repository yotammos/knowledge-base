package com.knowledgebase.utils

object Defaults {
  val STOCK_HOST: String = "www.alphavantage.co"
  val STOCK_PORT: Int = 443
  val STOCK_API_KEY: String = "4KGPZZEN7JFEN2ZH"

  val DB_BASE_URL: String = "jdbc:mysql://localhost"
  val DB_PORT: Int = 3306
  val DB_USE_SSL: Boolean = false
  val DB_DRIVER: String = "com.mysql.jdbc.Driver"
  val DB_USERNAME: String = "root"
  val DB_PASSWORD: String = "root"
}
