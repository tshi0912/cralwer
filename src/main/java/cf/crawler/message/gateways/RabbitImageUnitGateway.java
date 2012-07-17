package cf.crawler.message.gateways;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.support.RabbitGatewaySupport;
import org.springframework.beans.factory.annotation.Autowired;

import cf.crawler.services.CrawlerService;
import cf.crawler.tos.ImageUnitTo;

public class RabbitImageUnitGateway extends RabbitGatewaySupport 
	implements ImageUnitGateway {
	
	private static Logger logger = LoggerFactory.getLogger(RabbitImageUnitGateway.class);

	@Autowired
	CrawlerService crawlerService;
	
	public void sendImageUnits() {
		sendImageUnit(null);
	}
	
	public void sendImageUnit(ImageUnitTo to) {
		if(to==null) return;
		
		String routingKey = "feed.image.crawl";
		getRabbitTemplate().convertAndSend(routingKey, to);
		if(logger.isDebugEnabled()){
			logger.debug("++++++ push image unit: " + to.getAlt() + 
					" - " + to.getHref());
		}
	}

	public void sendCrawlUrl(String url) {
		String routingKey = "feed.url.crawl";
		logger.info("++++++ push url: " + url + "into rabbitmq...");
		getRabbitTemplate().convertAndSend(routingKey, url);
	}

}
