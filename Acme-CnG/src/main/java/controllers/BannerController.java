
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import domain.Banner;

@Controller
@RequestMapping("/banner")
public class BannerController extends AbstractController {

	//Services-------------------------

	@Autowired
	private BannerService	bannerService;


	//Constructor----------------------

	public BannerController() {
		super();
	}

	//List--------------------------

	@RequestMapping(value = "/listAdmin", method = RequestMethod.GET)
	public ModelAndView listAdmin() {
		ModelAndView result;
		Collection<Banner> banners;
		banners = bannerService.findAll();

		result = new ModelAndView("banner/list");
		result.addObject("banners", banners);
		result.addObject("requestURI", "banner/listAdmin.do");

		return result;
	}

	@RequestMapping(value = "/makePrincipal", method = RequestMethod.GET)
	public ModelAndView makePrincipal(@RequestParam int bannerId) {
		ModelAndView result;
		Banner b = bannerService.findOne(bannerId);
		try {
			bannerService.makePrincipal(b);
			result = listAdmin();
		} catch (Throwable oops) {
			result = listAdmin();
			result.addObject("message", "master-page.commit.error");
		}

		return result;
	}

}
