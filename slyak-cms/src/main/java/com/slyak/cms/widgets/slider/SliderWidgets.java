package com.slyak.cms.widgets.slider;

import org.apache.shiro.util.StringUtils;
import org.springframework.ui.ModelMap;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.model.Settings;

@Widgets("slider")
public class SliderWidgets {

	@Widget(settings = {
			@Setting(key = "images", value = "/widgetResource/slider/demo/slide1.jpg,/widgetResource/slider/demo/slide2.jpg,/widgetResource/slider/demo/slide3.jpg"),
			@Setting(key = "thumbnails", value = ""),
			@Setting(key = "itemWidth", value = "") })
	public String flexSlider(Settings settings, ModelMap modelMap){
		String images = settings.get("images");
		modelMap.put("images", images.split(","));
		String thumbnails = settings.get("thumbnails");
		if (StringUtils.hasText(thumbnails)) {
			modelMap.put("thumbnails", thumbnails.split(","));
		}
		String itemWidth = settings.get("itemWidth");
		if (StringUtils.hasText(itemWidth)) {
			modelMap.put("itemWidth", itemWidth);
		}
		return "flexSlider.tpl";
	}
	
	@Widget(settings = {
			@Setting(key = "images", value = "/widgetResource/slider/demo/slide1.jpg,/widgetResource/slider/demo/slide2.jpg,/widgetResource/slider/demo/slide3.jpg") })
	public String carousel(Settings settings, ModelMap modelMap){
		String images = settings.get("images");
		modelMap.put("images", images.split(","));
		return "carousel.tpl";
	}

}
