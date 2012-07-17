package cf.crawler.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.vidageek.crawler.Page;
import net.vidageek.crawler.PageVisitor;
import net.vidageek.crawler.Status;
import net.vidageek.crawler.Url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cf.crawler.message.gateways.ImageUnitGateway;
import cf.crawler.tos.ImageUnitTo;

@Component
public class MyPageVisitor implements PageVisitor {

	@Autowired
	private ImageUnitGateway imageUnitGateway;
	
	private static final String IMG_REG = "<img.*src=(.*?)[^>]*?>";
	private static final String IMG_SRC_REG = "\\ssrc=\"(.*?)\"";
	private static final String IMG_ALT_REG = "\\salt=\"(.*?)\"";
	private static Logger logger = LoggerFactory.getLogger(MyPageVisitor.class);
	
	public void visit(Page page) {
		if(logger.isDebugEnabled()){
			logger.debug("****** visited url : " + page.getUrl());
		}
		String from = null;
		try {
			URL url = new URL(page.getUrl());
			from = url.getHost();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return;
		}
		String content = page.getContent();
		if(content==null || content.trim().length()==0) return;
		Pattern p = Pattern.compile(IMG_REG, Pattern.CASE_INSENSITIVE); 
		Matcher m = p.matcher(content);
		ImageUnitTo to = null;
		while(m.find()){
			String img = m.group();
			if(logger.isDebugEnabled()){
				logger.debug(img);
			}
			to = new ImageUnitTo();
			Pattern s = Pattern.compile(IMG_SRC_REG, Pattern.CASE_INSENSITIVE);
			Matcher m1 = s.matcher(img);
			if(m1.find()){
				to.setHref(m1.group(1));
			}
			s = Pattern.compile(IMG_ALT_REG, Pattern.CASE_INSENSITIVE);
			m1 = s.matcher(img);
			if(m1.find()){
				to.setAlt(m1.group(1));
			}
			to.setFrom(from);
			imageUnitGateway.sendImageUnit(to);
	     }
	}

	public void onError(Url errorUrl, Status statusError) {
		
	}

	public boolean followUrl(Url url) {
		return false;
	}

}
