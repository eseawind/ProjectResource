package com.bootstarpDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baseCore.controller.BaseController;
/**
 * <p>功能描述：bootstrap 案例</p>
 *作者：SShi11
 *日期：Apr 25, 2017 1:56:24 PM
 */
@Controller
@RequestMapping("/bootstrapDemo")
public class BootstrapDemoController extends BaseController<BootstrapDemoController> {
	private String commUrl="/bootstrapDoc/docs/examples";
	
	@RequestMapping("/blog")
	public String blog(){
		return commUrl+"/blog/index.html";
	}
	@RequestMapping("/carousel")
	public String carousel(){
		return commUrl+"/carousel/index.html";
	}
	@RequestMapping("/cover")
	public String cover(){
		return commUrl+"/cover/index.html";
	}
	@RequestMapping("/dashboard")
	public String dashboard(){
		return commUrl+"/dashboard/index.html";
	}
	@RequestMapping("/grid")
	public String grid(){
		return commUrl+"/grid/index.html";
	}
	@RequestMapping("/jumbotron")
	public String jumbotron(){
		return commUrl+"/jumbotron/index.html";
	}
	@RequestMapping("/jumbotronNarrow")
	public String jumbotronNarrow(){
		return commUrl+"/jumbotron-narrow/index.html";
	}
	@RequestMapping("/justifiedNav")
	public String justifiedNav(){
		return commUrl+"/justified-nav/index.html";
	}
	@RequestMapping("/navbar")
	public String navbar(){
		return commUrl+"/navbar/index.html";
	}
	@RequestMapping("/navbarFixedTop")
	public String navbarFixedTop(){
		return commUrl+"/navbar-fixed-top/index.html";
	}
	@RequestMapping("/navbarStaticTop")
	public String navbarStaticTop(){
		return commUrl+"/navbar-static-top/index.html";
	}
	@RequestMapping("/nonResponsive")
	public String nonResponsive(){
		return commUrl+"/non-responsive/index.html";
	}
	@RequestMapping("/offcanvas")
	public String offcanvas(){
		return commUrl+"/offcanvas/index.html";
	}
	@RequestMapping("/screenshots")
	public String screenshots(){
		return commUrl+"/screenshots/index.html";
	}
	@RequestMapping("/signin")
	public String signin(){
		return commUrl+"/signin/index.html";
	}
	@RequestMapping("/starterTemplate")
	public String starterTemplate(){
		return commUrl+"/starter-template/index.html";
	}
	@RequestMapping("/stickyFooter")
	public String stickyFooter(){
		return commUrl+"/sticky-footer/index.html";
	}
	@RequestMapping("/stickyFooterNavbar")
	public String stickyFooterNavbar(){
		return commUrl+"/sticky-footer-navbar/index.html";
	}
	@RequestMapping("/theme")
	public String theme(){
		return commUrl+"/theme/index.html";
	}
	@RequestMapping("/tooltipViewport")
	public String tooltipViewport(){
		return commUrl+"/tooltip-viewport/index.html";
	}
}
