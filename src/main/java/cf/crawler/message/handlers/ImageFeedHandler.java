package cf.crawler.message.handlers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cf.crawler.domains.ImageUnit;
import cf.crawler.repositories.ImageUnitRepository;
import cf.crawler.tos.ImageUnitTo;
import cf.crawler.utils.WebImageUtil;

@Component
public class ImageFeedHandler {
	
	private static Logger logger = LoggerFactory
			.getLogger(ImageFeedHandler.class);

	@Autowired
	private ImageUnitRepository imageUnitRepository;
	@Autowired
	private WebImageUtil webImageUtil;
	private final int threshold = 190;
	
	public void handleMessage(ImageUnitTo to){
		if(logger.isDebugEnabled()){
			logger.debug("receive image unit : " + to.getAlt() + 
					" - " + to.getHref());
		}
		ImageUnit iu = ImageUnit.from(to);
		try {
			iu.setSize(webImageUtil.getImageSize(to.getHref()));
			if(iu.getSize()==null || iu.getSize()[1] < threshold){
				logger.info("image is invalid, width not satisfy threshold " + threshold);
				return;
			}
			iu.setRecruitedAt(new Date());
			imageUnitRepository.save(iu);
		} catch (Exception e) {
			logger.info("image is invalid, "+e.getMessage());
		}
	}
	
}
