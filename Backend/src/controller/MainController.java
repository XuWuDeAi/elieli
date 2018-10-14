package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;

import entity.JsonRespBean;
import service.HtmlDataService;
import unit.C3P0Factory;



@Controller
public class MainController {
	
	
	@ResponseBody
	@RequestMapping(value = "/home", produces = "application/json; charset=utf-8")
	public String home() throws Exception {
		try {		
			return JsonRespBean.success( C3P0Factory.executeQuery("SELECT * FROM `home`") );
		} catch (Exception e) {
			return JsonRespBean.erro(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/leaderboard", produces = "application/json; charset=utf-8")
	public String leaderboard() throws Exception {
		try {		
			return JsonRespBean.success( C3P0Factory.executeQuery("SELECT * FROM `leaderboard`") );
		} catch (Exception e) {
			return JsonRespBean.erro(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/chasing", produces = "application/json; charset=utf-8")
	public String chasing(String week) throws Exception {
		try {		
			return JsonRespBean.success( C3P0Factory.executeQuery("SELECT * FROM `chasing` AS a WHERE a.`week`='"+week+"'") );
		} catch (Exception e) {
			return JsonRespBean.erro(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getShowFan", produces = "application/json; charset=utf-8")
	public String getShowFan(String nk) throws Exception {
		try {		
			return HtmlDataService.getShowFan(nk).toString();
		} catch (Exception e) {
			return JsonRespBean.erro(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getVedioUrl", produces = "application/json; charset=utf-8")
	public String getVedioUrl(String href) throws Exception {
		try {		
			return JsonRespBean.success(HtmlDataService.getVedioUrl(href));
		} catch (Exception e) {
			return JsonRespBean.erro(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/search", produces = "application/json; charset=utf-8")
	public String search(String value) throws Exception {
		try {		
			return JsonRespBean.success(HtmlDataService.search(value));
		} catch (Exception e) {
			return JsonRespBean.erro(e);
		}
	}

	

}
