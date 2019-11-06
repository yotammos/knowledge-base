package com.knowledgebase.dao

import java.sql.{Connection, DriverManager}

import com.knowledgebase.models.{Interest, UserId}

trait KnowledgeBaseDaoComponent {
  def knowledgeBaseDao: KnowledgeBaseDao

  class KnowledgeBaseDao(baseUrl: String, port: Int = 3306, useSSL: Boolean = false,
                         driver: String, username: String, password: String)
    extends AbstractDao(baseUrl, port, database = "knowledge_base", useSSL, driver, username, password) {
    val url = s"$baseUrl:$port/$getDatabase?useSSL=$useSSL"

    def getInterestsByUserId(userId: UserId): Seq[Interest] = {
      var interests = Seq.empty[Interest]
      try {
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)
        val statement = connection.createStatement
        val rs = statement.executeQuery(s"SELECT interest FROM interests where user_id = $userId")
        while (rs.next) {
          interests = interests :+ rs.getString("interest")
        }
      } catch {
        case e: Exception => e.printStackTrace
      }
      connection.close
      interests
    }

    def addInterestsByUserId(userId: UserId, interests: Seq[Interest]): Unit = {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement
      val sql: String =
        s"""
           | insert into interests(user_id, interest)
           | values ${interests.map(interest => s"($userId, '$interest')").mkString(",")}
         """.stripMargin
      println(sql)
      statement execute sql
    }
  }
}
