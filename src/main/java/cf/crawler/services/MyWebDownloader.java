package cf.crawler.services;

import net.vidageek.crawler.Page;
import net.vidageek.crawler.component.WebDownloader;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.springframework.util.StringUtils;

import cf.crawler.constants.ApplicationConfig;

public class MyWebDownloader extends WebDownloader {
	
	public Page get(final HttpClient client, final String url) {
		if(StringUtils.hasText(ApplicationConfig.httpProxyHost)){
			HttpHost proxy = new HttpHost(System.getProperty("http.proxyHost"), 
					Integer.parseInt(System.getProperty("http.proxyPort")));
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, 
					proxy);
		}
		return super.get(client, url);
	}
	
}
