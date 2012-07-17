package cf.crawler.services;

import net.vidageek.crawler.PageCrawler;
import net.vidageek.crawler.config.CrawlerConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CrawlerServiceImpl implements CrawlerService {

	@Autowired
	private MyPageVisitor myPageVisitor;
	
	public void crawlImage(String url) {
		CrawlerConfiguration cfg = 
				new CrawlerConfiguration(url);
		cfg.downloader(new MyWebDownloader());
		PageCrawler crawler = new PageCrawler(cfg);
		crawler.crawl(myPageVisitor);
	}

}
