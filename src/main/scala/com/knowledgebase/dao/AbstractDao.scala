package com.knowledgebase.dao

import java.sql.Connection

abstract class AbstractDao(
                            baseUrl: String,
                            port: Int = 3306,
                            database: String,
                            useSSL: Boolean = false,
                            driver: String,
                            username: String,
                            password: String
                          ) {
  def getDatabase: String = database
  protected var connection: Connection = _
}
