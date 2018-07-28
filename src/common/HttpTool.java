package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @author MacFan
 * user:created by MacFan
 * DATE: 2018/7/13
 * Http工具
 */
public final class HttpTool {
    public static final String OK = "200";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    public static String[] GET(String url, String encode, int connectTimeout, int responseTimeout){
        String[] response = new String[2];
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(responseTimeout);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(),encode));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                response[0] = String.valueOf(connection.getResponseCode());
                response[1] = sb.toString();
            }else{
                response[0] = String.valueOf(connection.getResponseCode());
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return response;
    }

    public static String[] DailiGET(String url,String encode, int connectTimeout, int responseTimeout,String ip,Integer port) throws IOException {
        String[] response = new String[2];
        BufferedReader in = null;
        try {
            //系统代理
//            // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
            System.getProperties().setProperty("http.proxyHost", ip);
            System.getProperties().setProperty("http.proxyPort", port.toString());
            URL realUrl = new URL(url);
//            Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP,new InetSocketAddress(ip,port));
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(responseTimeout);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream(),encode));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                response[0] = String.valueOf(connection.getResponseCode());
                response[1] = sb.toString();
            }else{
                response[0] = String.valueOf(connection.getResponseCode());
            }
            return response;
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
