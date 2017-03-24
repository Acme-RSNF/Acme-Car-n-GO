
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import domain.Banner;
import forms.BannerForm;

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

	//Creation-------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		BannerForm bannerForm;

		bannerForm = bannerService.generateForm();
		result = createEditModelAndView(bannerForm, null);

		return result;

	}

	//Edition--------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bannerId) {

		ModelAndView result;
		Banner banner;

		banner = bannerService.findOne(bannerId);
		BannerForm bannerForm=bannerService.transform(banner);
		Assert.notNull(banner);
		result = new ModelAndView("banner/edit");
		result.addObject("bannerForm", bannerForm);
		
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid BannerForm bannerForm, BindingResult binding) {

		ModelAndView result = new ModelAndView();

		if (binding.hasErrors())
			result = createEditModelAndView(bannerForm);
		else
			try {
				Banner banner= bannerService.reconstruct(bannerForm, binding);
				bannerService.save(banner);
				result = listAdmin();
			} catch (Throwable oops) {
				String msgCode = "banner.save.error";
				result = createEditModelAndView(bannerForm, msgCode);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(BannerForm bannerForm, BindingResult binding) {

		ModelAndView result;

		Banner banner = bannerService.reconstruct(bannerForm, binding);
		if (binding.hasErrors())
			result = createEditModelAndView(banner,null);
		else
			try {
				bannerService.delete(banner);
				result = listAdmin();
			} catch (Throwable oops) {
				result = createEditModelAndView(banner,null);
			}
		return result;
	}

	//Ancillary Methods---------------------------

	protected ModelAndView createEditModelAndView(Banner banner, String message) {
		ModelAndView result;

		result = new ModelAndView("banner/edit");
		result.addObject("banner", banner);
		result.addObject("message", message);
		return result;

	}

	protected ModelAndView createEditModelAndView(BannerForm bannerForm, String message) {
		ModelAndView result;

		result = new ModelAndView("banner/edit");
		result.addObject("bannerForm", bannerForm);

		result.addObject("message", message);

		return result;

	}

	protected ModelAndView createEditModelAndView(BannerForm bannerForm) {

		ModelAndView result;

		result = createEditModelAndView(bannerForm, null);

		return result;
	}

}
