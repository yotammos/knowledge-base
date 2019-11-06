package com.knowledgebase.services

import com.knowledgebase.config.ComponentProvider
import com.knowledgebase.models.{ Interest, Message, UserId }
import com.twitter.util.Future

trait KnowledgeBaseServiceComponent {
  def knowledgeBaseService: KnowledgeBaseService

  class KnowledgeBaseService {
    val context = new ComponentProvider
    val message = Message("Hello, world!")

    def getMessage: Future[Message] = Future value message

    def getInterests(userId: UserId): Future[Seq[Interest]] = {
      println("getting interests, userId = " + userId)
      val interests = context.knowledgeBaseDao.getInterestsByUserId(userId)
      Future collect interests.map(interest =>
        if (isStock(interest)) {
          context.stockInfoHttpClient.getStockData(interest)
        } else {
          Future value interest
        }
      )
    }

    def addInterests(userId: UserId, interests: Seq[Interest]): Future[Unit] = {
      println("adding interests, userId = " + userId)
      Future value context.knowledgeBaseDao.addInterestsByUserId(userId, interests)
    }

    private def isStock(interest: Interest): Boolean = interest == "SPY"
  }

}
