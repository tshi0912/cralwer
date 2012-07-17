package cf.crawler.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cf.crawler.constants.ApplicationConfig;
import cf.crawler.vos.ImageReadyVo;

@Component
public class WebImageUtil {
	
	public WebImageUtil(){
		if(StringUtils.hasText(ApplicationConfig.httpProxyHost)){
			System.setProperty("http.proxyHost", ApplicationConfig.httpProxyHost);
			System.setProperty("http.proxyPort", ApplicationConfig.httpProxyPort);
		}
	}
	
	public int[] getImageSize(String url) throws Exception{
		if(url==null) return null;
		
		URL u = new URL(url);
		BufferedImage img = ImageIO.read(u);
		return new int[]{img.getHeight(), img.getWidth()};
	}
	
	public static int getPinHeight(int[] size){
		if(size==null || size.length!=2) return 0;
		double rato = (double)size[0]/(double)size[1];
		double height = rato*192;
		return (int)Math.floor(height);
	}
	
	public ImageReadyVo prepareImageFromUrl(String url) throws Exception{
		if(url==null) return null;
		
		String addr = StringUtils.trimWhitespace(url);
		URL u = null;
		BufferedImage img = null;
		File file = null;
		if(addr.indexOf(ApplicationConfig.imageRefer)==-1){
			u = new URL(url);
			img = ImageIO.read(u);
			String fileName = System.currentTimeMillis()+".jpg";
			String filePath = new StringBuilder()
					.append(ApplicationConfig.imageRepository)
					.append("/")
					.append(fileName)
					.toString();
			file = new File(filePath);
			ImageIO.write(img, "jpg", file);
			addr = filePath;
		}
		int idx = addr.lastIndexOf("/");
		file = new File(ApplicationConfig.imageRepository + "/"
				+ addr.substring(idx + 1));
		img = ImageIO.read(file);
		ImageReadyVo ir = new ImageReadyVo();
		ir.setFile(file);
		ir.setOrgSize(new Integer[] { img.getHeight(), img.getWidth() });
		return ir;
	}
}
