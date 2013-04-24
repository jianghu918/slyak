package com.slyak.cms.widgets.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.enums.InputType;
import com.slyak.cms.core.model.Settings;
import com.slyak.comment.model.Comment;
import com.slyak.comment.service.CommentService;

@Widgets("news")
public class NewsWidgets {
	
	private static final String BIZ="news";

	@Autowired
	private CommentService commentService;

	@Widget(settings = {
			@Setting(key = "type", value = "",optionsLoader="getNewsTypes"),
			@Setting(key = "style", value = ""),
			@Setting(key = "fetchSize", value = "10"),
			@Setting(key = "template",value = "list1.tpl",options={"list1.tpl","list2.tpl"}),
			@Setting(key = "diy",value ="",inputType = InputType.TEXTAREA )
			},onEdit="addType",onRemove="removeType")
	public String list(Settings settings,ModelMap modelMap) {
		modelMap.put("comments", commentService.listComments(NumberUtils.parseNumber(settings.get("fetchSize"), Integer.class), BIZ, settings.get("type")));
		return settings.get("template");
	}
	
	@Widget(show=false)
	public String detail(Long newsId,ModelMap modelMap) {
		if (newsId != null) {
			modelMap.put("comment", commentService.findOne(newsId));
		}
		return "detail.tpl";
	}
	
	public void addNews(Comment comment,ModelMap modelMap){
		commentService.save(comment);
		modelMap.put("newsType", comment.getOwner());
	}
	
	public void addType(com.slyak.cms.core.model.Widget widget){
		Map<String, String> settings = widget.getSettings();
		if(!CollectionUtils.isEmpty(settings)){
			String type = settings.get("type");
		}
	}
	
	public void removeType(Settings settings){
		String type = settings.get("type");
	}
	
	@Widget(settings = {@Setting(key = "pageSize", value = "10")})
	public String manager(Integer page,String newsType,Settings settings,ModelMap modelMap) {
		List<String> newsTypes = commentService.listBizOwners(BIZ);
		if(!CollectionUtils.isEmpty(newsTypes)){
			modelMap.put("newsTypes", newsTypes);
			if(page==null){
				page =1;
			}
			if(newsType==null){
				newsType = newsTypes.get(0);
			}
			modelMap.put("newsType", newsType);
			PageRequest pageRequest = new PageRequest(page-1, NumberUtils.parseNumber(settings.get("pageSize"), Integer.class));
			modelMap.put("page", commentService.getComments(pageRequest, BIZ, newsType));
		}
		return "manager.tpl";
	}
	
	public void removeNews(Long newsId){
		commentService.remove(newsId);
	}
}
