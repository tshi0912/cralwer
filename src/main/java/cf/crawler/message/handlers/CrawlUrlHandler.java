package cf.crawler.message.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cf.crawler.services.CrawlerService;

@Component
public class CrawlUrlHandler {
	
	private static Logger logger = LoggerFactory
			.getLogger(CrawlUrlHandler.class);

	@Autowired
	private CrawlerService crawlerService;
	
	public void handleMessage(String url){
		if(logger.isDebugEnabled()){
			logger.debug("***** start crawl url: " +url);
		}
		crawlerService.crawlImage(url);
	}
}
