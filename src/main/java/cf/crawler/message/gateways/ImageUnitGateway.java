package cf.crawler.message.gateways;

import cf.crawler.tos.ImageUnitTo;

public interface ImageUnitGateway {
	
	void sendImageUnit(ImageUnitTo to);
	
	void sendCrawlUrl(String url);
}
