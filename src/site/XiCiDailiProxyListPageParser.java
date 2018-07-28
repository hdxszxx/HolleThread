package site;

import common.HttpTool;
import crawler.XiCiDaiLiCrawler;
import entity.XiCiDaiLi;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MacFan
 * user:created by MacFan
 * DATE: 2018/7/13
 */
public class XiCiDailiProxyListPageParser {

    private final String zcwz = XiCiDaiLiCrawler.getProperties("cswz");

    public List<XiCiDaiLi> parse(String html){
        Document document = null;
        List<XiCiDaiLi>  XiCiDaiLiList = new ArrayList<XiCiDaiLi>();
        document = Jsoup.parse(html);
        Elements elements = document.select("#ip_list tr[class]");
        for (Element element:elements) {
            XiCiDaiLi xiCiDaiLi = new XiCiDaiLi();
            xiCiDaiLi.setIp(element.select("td:eq(1)").html());
            xiCiDaiLi.setPrpo(element.select("td:eq(2)").html());
            xiCiDaiLi.setCountry("中国");
            xiCiDaiLi.setAddress(element.select("td:eq(3) a").html());
            xiCiDaiLi.setIsAnon(element.select("td:eq(4)").html());
            xiCiDaiLi.setType(element.select("td:eq(5)").html());
            xiCiDaiLi.setSpeed(element.select("td:eq(6) div[class='bar']").attr("title"));
            xiCiDaiLi.setConTime(element.select("td:eq(7) div[class='bar']").attr("title"));
            xiCiDaiLi.setExistTime(element.select("td:eq(8)").html());
            xiCiDaiLi.setValidateTime(element.select("td:eq(9)").html());
            XiCiDaiLiCrawler.cesiDali(xiCiDaiLi);
            System.out.println("开始获取:"+xiCiDaiLi.toString());
        }
        return XiCiDaiLiList;
    }
}
