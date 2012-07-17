package cf.crawler.services;

import java.net.URL;

import cf.crawler.tos.ImageUnitTo;

public class MockCrawlerServiceImpl implements CrawlerService {
	
	private int batchSize = 5;
	
	private static final String[] alts = new String[]{
			"Good Pics", "High Speed", "Tony's home", "You're kidding me",
			"Ops, error page", "Server can not found", "The Robo-Help", "Test Question Flunks",
			"All Too Often, a Sorry State of Affairs", "Down With Everything"
		};
	
	private static final String[] hrefs = new String[]{
			"http://img.hb.aicdn.com/8bda45a2aadd3db6af1cf107e63b7526b026551c3f540-JyIMIp_fw192",
			"http://img.hb.aicdn.com/d7ca038b16a345662582c6cd08dfa93d77786c32193cc-j9iDjE_fw192",
			"http://img.hb.aicdn.com/19e6764122b0b122e1acea4c900b4ca25f1ff04253cf2-xzNDnV_fw192",
			"http://img.hb.aicdn.com/c15180a66c9e4efbd8999e2387a6a3ea8db707853b94f-IKZYaz_fw192",
			"http://img.hb.aicdn.com/7970dde57db4b96f1bb4e68d56bfc3e16cbafd2ae549-PoLFX4_fw192",
			"http://img.hb.aicdn.com/58cc8d4f96928c71873b18c108b3e38e4e81fed72f49f-ms2oks_fw192",
			"http://img.hb.aicdn.com/6ce9a9662e904934a5ce8b2f692e65090ed4a2e346f72-SCMtMh_fw192",
			"http://img.hb.aicdn.com/f20ce52a4b7c57a7ae214ad4091324be8d4fc6f14201f-uSuplr_fw192",
			"http://img.hb.aicdn.com/d69b6ef0619afb2634761681cdcebf8953435726d635-4GgEUC_fw192",
			"http://img.hb.aicdn.com/c97176452a409cdb99ee8d41d80f43c98d45995f68298-ZH0jJt_fw192"
	};

	public ImageUnitTo[] getImageUnits(URL url) {
		int len = batchSize;
		ImageUnitTo[] ius = new ImageUnitTo[len];
		for(int i=0; i<len; i++){
			ImageUnitTo iu = new ImageUnitTo();
			int idx = (int) Math.floor(Math.random()*10%10);
			iu.setHref(hrefs[idx]);
			iu.setAlt(alts[idx]);
			iu.setFrom("huaban");
			ius[i] = iu;
		}
		return ius;
	}

	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void crawlImage(String url) {
		// TODO Auto-generated method stub
		
	}
	
}
