import common.HttpTool;
import crawler.XiCiDaiLiCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    public static void main(String[] args) {
//        String[] data = HttpTool.DailiGET("http://www.open-open.com","UTF-8",20000,20000,"118.31.220.3",8080);
//            String[] data = HttpTool.GET("http://www.xicidaili.com/nn/3","UTF-8",500,1000);
//            System.out.println(data[1]);
        new XiCiDaiLiCrawler().startCrawl();
    }

}
