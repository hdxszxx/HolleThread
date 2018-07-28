package crawler;

import common.HttpTool;
import entity.XiCiDaiLi;
import site.XiCiDailiProxyListPageParser;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author MacFan
 * user:created by MacFan
 * DATE: 2018/7/13
 */
public class ProxyPageTask implements Runnable{
    private String url;

    public static Integer a = 0;

    public ProxyPageTask(String url) {
        this.url = url;
    }

    @Override
    public void run() {
            String[] data = new String[2];
        if (XiCiDaiLiCrawler.daili) {
                    if(XiCiDaiLiCrawler.DaiLi.size() > 0) {
                        Iterator<XiCiDaiLi> xicdaili = XiCiDaiLiCrawler.DaiLi.iterator();
                        while (xicdaili.hasNext()){
                            XiCiDaiLi daili = xicdaili.next();
                            try {
                                data = HttpTool.DailiGET(url, "UTF-8", 2000, 2000, daili.getIp(), Integer.parseInt(daili.getPrpo()));
                            } catch (IOException e) {
                                break;
                            }
                            if(data[0].equals(HttpTool.OK)){
                                break;
                            }
                        }
                        }else{
                        data = HttpTool.GET(url, "UTF-8", 2000, 2000);
                    }
                } else {
                    data = HttpTool.GET(url, "UTF-8", 2000, 2000);
                }
        System.out.println(data[0]+"——————————————————————"+url);
            if(data[0].equals(HttpTool.OK)){
                a++;
                List<XiCiDaiLi> xiCiDaiLis = new XiCiDailiProxyListPageParser().parse(data[1]);
            }

    }
}
