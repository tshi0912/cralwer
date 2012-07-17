package cf.crawler.vos;

import org.hibernate.validator.constraints.NotEmpty;

public class CrawlVo {
	
	@NotEmpty
	private String url;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
