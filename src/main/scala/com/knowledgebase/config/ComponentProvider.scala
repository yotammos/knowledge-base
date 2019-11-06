package com.knowledgebase.config

import com.knowledgebase.clients.StockInfoHttpClientComponent
import com.knowledgebase.dao.KnowledgeBaseDaoComponent
import com.knowledgebase.services.KnowledgeBaseServiceComponent
import com.knowledgebase.utils.Defaults._

class ComponentProvider
  extends KnowledgeBaseServiceComponent
  with KnowledgeBaseDaoComponent
  with StockInfoHttpClientComponent {

  override lazy val knowledgeBaseService: KnowledgeBaseService = new KnowledgeBaseService
  override lazy val knowledgeBaseDao: KnowledgeBaseDao = new KnowledgeBaseDao(
    baseUrl = DB_BASE_URL,
    port = DB_PORT,
    useSSL = DB_USE_SSL,
    driver = DB_DRIVER,
    username = DB_USERNAME,
    password = DB_PASSWORD
  )

  override lazy val stockInfoHttpClient: StockInfoHttpClient = new StockInfoHttpClient(STOCK_API_KEY)
}
