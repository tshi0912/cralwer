package cf.crawler.web.mvc.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cf.crawler.domains.ImageUnit;
import cf.crawler.message.gateways.ImageUnitGateway;
import cf.crawler.repositories.ImageUnitRepository;
import cf.crawler.vos.CrawlVo;
import cf.crawler.vos.ResultVo;

@Controller
public class FrontController {
	
	private static Logger logger = LoggerFactory
			.getLogger(FrontController.class);
	
	@Autowired
	private ImageUnitRepository imageUnitRepository;
	@Autowired
	private ImageUnitGateway imageUnitGateway;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String front(Model model,
			HttpServletRequest request, 
			HttpSession session){
		
		return "front";
	}
	
	@RequestMapping(value="/pull", method=RequestMethod.POST)
	public String pull(Model model, HttpServletRequest request, 
			HttpSession session){
		long ts = 0;
		try{
			ts = Long.parseLong(request.getParameter("ts"));
		}catch(NumberFormatException nfe){
			logger.info("init pulling, set ts as 0");
		}
		if(logger.isDebugEnabled()){
			logger.debug("******** pull image data after timestamp : " + ts);
		}
		Iterable<ImageUnit> ius = imageUnitRepository
				.findByRecruitedAtGreaterThan(new Date(ts),
						new Sort(new Order(Direction.DESC, "recruitedAt")));
		List<ImageUnit> images = new ArrayList<ImageUnit>();
		for(ImageUnit iu : ius){
			images.add(iu);
		}
		if(images.size()!=0){
			Date lastTs = images.get(0).getRecruitedAt();
			if(lastTs!=null){
				model.addAttribute("ts", lastTs.getTime());
			}
		}
		model.addAttribute("images", images);
		return "image.pin";
	}
	
	@RequestMapping(value="/crawl", method=RequestMethod.POST)
	public @ResponseBody ResultVo crawl(@Valid CrawlVo vo,
			BindingResult result, ModelAndView mav, 
			HttpSession session){
		if(result.hasErrors()){
			return new ResultVo(ResultVo.FAILED, "url should not empty");
		}
		imageUnitGateway.sendCrawlUrl(vo.getUrl());
		return new ResultVo(ResultVo.SUCCESS);
	}
	
	@RequestMapping(value="/images/{no}", method=RequestMethod.GET)
	public String images(@PathVariable int no,
			Model model, HttpServletRequest request, 
			HttpSession session){
		Pageable pageable = new PageRequest(Math.max(no, 0), 15, 
				new Sort(new Order(Direction.DESC, "recruitedAt")));
		Iterable<ImageUnit> ius = imageUnitRepository.findAll(pageable);
		List<ImageUnit> images = new ArrayList<ImageUnit>();
		for(ImageUnit iu : ius){
			images.add(iu);
		}
		if(images.size()!=0){
			Date lastTs = images.get(0).getRecruitedAt();
			if(lastTs!=null){
				model.addAttribute("ts", lastTs.getTime());
			}
		}
		model.addAttribute("images", images);
		return "image.pin";
	}
	
}
