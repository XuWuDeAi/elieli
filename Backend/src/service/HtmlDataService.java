package service;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.New;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.sun.mail.handlers.text_html;

import af.sql.AfSqlInsert;
import entity.Chasing;
import entity.Home;
import entity.Leaderboard;
import unit.C3P0Factory;
import unit.HttpUnit;

public class HtmlDataService {

	public static String[] weeks = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

	public static void updateHome() throws Exception {
		String value = null;
		try {
			value = HttpUnit.get("http://www.ezdmw.com/");
			Document doc = Jsoup.parse(value);
			Element masthead = doc.select("div.some_gai").first();
			List<Element> mastheads = masthead.select("div[style]");
			if (!(mastheads.size() <= 0)) {
				C3P0Factory.execute("DELETE FROM `home`");
			}
			for (int i = 0; i < mastheads.size(); i++) {
				Element item = mastheads.get(i);

				Element a = item.select("a").get(1);
				String linkHref = a.attr("href");
				linkHref = linkHref.split("=")[1];
				String img = a.getElementsByTag("img").attr("src");
				String title = a.getElementsByTag("img").attr("title");
				C3P0Factory.insert(new Home(img, title, linkHref, item.select("span").get(0).text()));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return;

	}

	public static void updateLeaderboard() throws Exception {

		String value;
		value = HttpUnit.get("http://www.ezdmw.com/Home/Index/fan_ranking.html?name=" + getYear() + "年" + getSeason()
				+ "月&atype=hor");
		Document doc = Jsoup.parse(value);
		Element masthead = doc.getElementById("ranking_tab1");
		List<Element> mastheads = masthead.select("a[target]");
		if (!(mastheads.size() <= 0)) {
			C3P0Factory.execute("DELETE FROM `leaderboard`");
		}
		for (int i = 0; i < mastheads.size(); i++) {
			Element item = mastheads.get(i);

			int postion = Integer.parseInt(item.selectFirst("span").text());
			String name = item.selectFirst("strong").text();
			String attributes = item.select("strong").get(1).text();
			String status = item.select("strong").get(2).text();
			String newstatus = item.select("strong").get(3).text();
			int id = Integer.parseInt(getZhen(item.select("a").first().attr("href")));

			String pic = item.selectFirst("img").attr("src");
			C3P0Factory.insert(new Leaderboard(name, postion, id, pic, status, newstatus, attributes));
		}

	}

	public static void updateChasing() throws Exception {
		String value;
		value = HttpUnit.get("http://www.ezdmw.com/Home/Index/end_comic.html");
		Document doc = Jsoup.parse(value);
		List<Element> mastheads = doc.select("aside");
		 if (!(mastheads.size() <= 0)) {
		 C3P0Factory.execute("DELETE FROM `chasing`");
		 }
		for (int i = 0; i < 7; i++) {
			String week = weeks[i];
			Element itemAsides = mastheads.get(i);
			List<Element> itemDivs = itemAsides.select("div");
			for (int j = 0; j < itemDivs.size(); j++) {
				// Integer.parseInt(getZhen(item.selectFirst("a").attr("href")));
				Element item = itemDivs.get(j);
				int id = Integer.parseInt(getZhen(item.selectFirst("a").attr("href")));
				String status = item.select("span").first().text();
				Element img = item.select("img[title]").first();
				String name = img.attr("title");
				String pic = img.attr("src");
				C3P0Factory.insert(new Chasing(name, pic, status, id, week));
			}

		}

	}

	public static JSONObject getShowFan(String nk) throws Exception {

		String value;
		value = HttpUnit.get("http://www.ezdmw.com/Home/Index/video_play.html?nk=" + nk);
		Document doc = Jsoup.parse(value);
		Element container = doc.select("div.container").first();
		String name = container.select("h4.top_title").first().text();
		String pic = container.select("img[src]").first().attr("src");
		String attributes = container.select("h2").get(0).text();
		String status = container.select("h2").get(1).text();
		String introduction = container.select("div[style$=padding:10px;line-height:30px;]").get(0).text();
		Element section = container.select("section[class$=anthology]").first();
		List<Element> items = section.select("a");

		JSONArray aitems = new JSONArray();
		for (int i = 0; i < items.size(); i++) {
			Element item = items.get(i);
			String href = item.attr("href");
			String hrefname = item.select("span").attr("class");
//			if (hrefname.equals("连载") || isInteger(hrefname)) {
//				continue;
//			}
			if (hrefname.equals("连载") ) {
				continue;
			}
			JSONObject aitem = new JSONObject();
			aitem.put("href", href);
			aitem.put("hrefname", hrefname);
			aitems.put(aitem);
		}
		JSONObject resp = new JSONObject();
		resp.put("id", nk);
		resp.put("name", name);
		resp.put("pic", pic);
		resp.put("attributes", attributes);
		resp.put("status", status);
		resp.put("name", name);
		resp.put("introduction", introduction);
		resp.put("aitems", aitems);
		return resp;
	}

	public static JSONArray search(String value) throws Exception {

		value = HttpUnit.get("http://www.ezdmw.com/Home/Index/search.html?searchText=" + value);
		Document doc = Jsoup.parse(value);
		Element masthead = doc.select("section[class$=some_drama]").first();
		List<Element> divs = masthead.select("div");
		JSONArray arry = new JSONArray();
		for (int i = 0; i < divs.size(); i++) {
			Element item = divs.get(i);
			String name = getZhenString(item.select("p").text(), "[^\\x00-\\xff]");
			String pic = item.select("img").first().attr("src");
			int id = Integer.parseInt(getZhenString(item.select("a").first().attr("href"), "[0-9]"));

			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("pic", pic);
			json.put("id", id);
			arry.put(json);
		}
		return arry;
	}

	public static String getVedioUrl(String href) throws Exception {

		String value;
		value = HttpUnit.get("http://www.ezdmw.com" + href);
		Document doc = Jsoup.parse(value);
		String src = doc.select("iframe").first().attr("src");
		if (src.contains("http:")) {

			value = HttpUnit.get(src);
			// System.out.println(src);
			// Document vedioDoc = Jsoup.parse(value);
			// System.out.println(vedioDoc.select("script").toString());
			// return getHref2(vedioDoc.select("script").toString());

		} else {
			src = "http://www.ezdmw.com/" + src;
			// value = HttpUnit.get(src);
			//
			// Document vedioDoc = Jsoup.parse(value);
			// System.out.println(vedioDoc);
			// return getHref(vedioDoc.select("script").get(3).toString());

		}

		return src;

	}

	public static int getSeason() {
		int seasonNumber = Calendar.getInstance().get(Calendar.MONTH);
		return seasonNumber >= 1 && seasonNumber <= 3 ? 4
				: seasonNumber >= 4 && seasonNumber <= 6 ? 7
						: seasonNumber >= 7 && seasonNumber <= 9 ? 10 : seasonNumber >= 10 ? 1 : 1;
	}

	public static String getYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String formatDate = sdf.format(date);
		return formatDate;
	}

	public static String getZhen(String s) {
		System.out.print(s);

		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(s);
		String strs = "";
		while (m.find()) {
			strs = strs + m.group();

		}
		return strs.toString();
	}

	public static String getZhenString(String s, String zhen) {
		Pattern p = Pattern.compile(zhen);
		Matcher m = p.matcher(s);
		String strs = "";
		while (m.find()) {
			strs = strs + m.group();

		}
		return strs.toString();
	}

	public static String getHref(String s) {

		String strs = "";
		Pattern p = Pattern.compile("[a-zA-z]+://[^\\s]*");
		Matcher m = p.matcher(s);
		while (m.find()) {
			strs = strs + m.group();
		}
		String[] array = strs.split(",");
		String[] array2 = array[1].split(",");
		strs = array2[0].split("'")[0];
		System.out.print(s);
		return strs;

	}

	public static String getHref2(String s) {

		String strs = "";
		Pattern p = Pattern.compile("[a-zA-z]+://[^\\s]*");
		Matcher m = p.matcher(s);
		while (m.find()) {
			strs = strs + m.group();
		}

		return strs.split("\"")[0];

	}

	/*
	 * 方法二：推荐，速度最快 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static void main(String[] args) {
		// System.out.println(gethtml());

		try {
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
