package crawler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.HttpTool;
import entity.XiCiDaiLi;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author MacFan
 * user:created by MacFan
 * DATE: 2018/7/13
 */
public class XiCiDaiLiCrawler {

    /**
     * 进行存储的代理
     */
    public static Set<XiCiDaiLi> xiCiDaiLis = new HashSet<XiCiDaiLi>();

    private final String XICIDAILIPROXY = "xicidailiProxy";

    private final int pages = 8;

    public static final Boolean daili = Boolean.parseBoolean(getProperties("daili"));

    /**
     * 使用代理去爬取的代理
     */
    public static Set<XiCiDaiLi> DaiLi = new HashSet<XiCiDaiLi>();

    /**
     * 获取代理的线程池
     */
    public static ThreadPoolExecutor proxyPool;

    /**
     * 获取代理的线程池
     */
    public static ThreadPoolExecutor csproxyPool;

    /**
     * 构建读写锁
     */
    private static ReentrantReadWriteLock rrw = new ReentrantReadWriteLock();

    /**
     * 读锁
     */
    private static Lock readLock = rrw.readLock();
    /**
     * 写锁
     */
    private static Lock writeLock = rrw.writeLock();


    public static void setDaiLiAddLock(XiCiDaiLi xi){
        readLock.lock();
        try {
            DaiLi.add(xi);
        }finally {
            readLock.unlock();
        }
    }

    public static void setXiCiDaiLisAddLock(XiCiDaiLi xi){
        readLock.lock();
        try {
            xiCiDaiLis.add(xi);
        }finally {
            readLock.unlock();
        }
    }

    public void startCrawl(){
        initProxyPool();
        start();
        outJson();
        inpJson();
    }

    public void initProxyPool(){
        proxyPool = new ThreadPoolExecutor(50, 100, 100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(1000),new ThreadPoolExecutor.DiscardPolicy());
        proxyPool.allowCoreThreadTimeOut(true);
        csproxyPool = new ThreadPoolExecutor(100, 200, 100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),new ThreadPoolExecutor.DiscardPolicy());
        csproxyPool.allowCoreThreadTimeOut(true);
    }
    public void start(){
        String dz = getProperties(XICIDAILIPROXY);
        proxyPool.execute(new Runnable() {
            @Override
            public void run() {
                gn(dz);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        proxyPool.execute(new Runnable() {
            @Override
            public void run() {
                pt(dz);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        proxyPool.execute(new Runnable() {
            @Override
            public void run() {
                https(dz);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        proxyPool.execute(new Runnable() {
            @Override
            public void run() {
                http(dz);
            }
        });
    }

    public void gn(String dz){
        for(int i = 1;i<=pages;i++){
            String url = dz+"nn/"+i;
            System.out.println("开始爬取gn"+url);
            proxyPool.execute(new ProxyPageTask(url));
        }
    }

    public void pt(String dz){
        for(int i = 1;i<=pages;i++){
            String url = dz+"nt/"+i;
            System.out.println("开始爬取nt"+url);
            proxyPool.execute(new ProxyPageTask(url));
        }
    }

    public void https(String dz){
        for(int i = 1;i<=pages;i++){
            String url = dz+"wn/"+i;
            System.out.println("开始爬取wn"+url);
            proxyPool.execute(new ProxyPageTask(url));
        }
    }

    public void http(String dz){
        for(int i = 1;i<=pages;i++){
            String url = dz+"wt/"+i;
            System.out.println("开始爬取wt"+url);
            proxyPool.execute(new ProxyPageTask(url));
        }
    }

    public static String getProperties(String key){
        Properties p = new Properties();
        try {
            p.load(XiCiDaiLiCrawler.class.getResourceAsStream("/proxy.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p.getProperty(key);
    }

    public void outJson(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (proxyPool != null) {
                        System.out.println("开始获取当前运行线程数proxyPool:"+proxyPool.getPoolSize());
                        System.out.println("开始获取当前运行线程数csproxyPool:"+csproxyPool.getPoolSize());
                            if(proxyPool.getPoolSize() == 0 && csproxyPool.getPoolSize() == 0){
                                System.out.println("开始获取xiCiDaiLis:"+xiCiDaiLis.size());
                                System.out.println("开始获取运行次数:"+ProxyPageTask.a);
                                Gson gson =new Gson();
                                String json = gson.toJson(xiCiDaiLis);
                                String url = getProperties("outdaili");
                                BufferedWriter bd = null;
                                try {
                                    File file = new File(url);
                                    if (!file.exists()) {
                                        file.createNewFile();
                                    }
                                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                                    bd = new BufferedWriter(fw);
                                    bd.write(json);
                                    bd.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        }).start();
    }

    public void inpJson(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String url = getProperties("outdaili");
                    try {
                        System.out.println("开始获取本地缓存的代理");
                        File file = new File(url);
                        if (!file.exists()) {
                            try {
                                Thread.sleep(6000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        FileReader fr = new FileReader(file.getAbsoluteFile());
                        BufferedReader br = new BufferedReader(fr);
                        StringBuffer sb = new StringBuffer();
                        String a = null;
                        while ((a = br.readLine())!=null){
                            sb.append(a);
                        }
                        br.close();
                        Gson gs = new Gson();
                        DaiLi =gs.fromJson(sb.toString(),new TypeToken<Set<XiCiDaiLi>>(){}.getType());
                        System.out.println(DaiLi.size());
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException b) {
                            b.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException b) {
                            b.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public static void cesiDali(XiCiDaiLi xc){
        csproxyPool.execute(new Runnable() {
            @Override
            public void run() {
              String zcwz = XiCiDaiLiCrawler.getProperties("cswz");
             //测试当前代理可否使用
             String[] data = new String[2];
             try {
                 data = HttpTool.DailiGET(zcwz,"UTF-8",1000,1000,xc.getIp(),Integer.parseInt(xc.getPrpo()));
             }catch (Exception e) {
             }
             if(HttpTool.OK.equals(data[0])){
                 setXiCiDaiLisAddLock(xc);
                 setDaiLiAddLock(xc);
             }
            }
        });
    }

}
