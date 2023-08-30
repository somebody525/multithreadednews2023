package ph.edu.cksc.college.parallel;

import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.net.URL;

public class NewsAgent implements Runnable {

    private final String name;

    public static int runningCount = 0;
    public static long startTime;

    public NewsAgent(String name) {
        this.name = name;
    }

    public void connectAndRead() {
        try {
            URL httpUrl = new URL("https://college.cksc.edu.ph/");
            InputStreamReader in = new InputStreamReader(httpUrl.openStream());
            String content = IOUtils.toString(in);
            in.close();
            System.out.println(name + " " + content.length());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        runningCount++;
        System.out.println("thread is running...");
        connectAndRead();
        runningCount--;
        if (runningCount == 0) {
            System.out.println("Time: " + (System.nanoTime() - startTime) / 1000000);
        }
    }

    public void start() {
        Thread thread = new Thread(this, name);
        thread.start();
    }

    public static void main(String[] args){
        startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            NewsAgent agent = new NewsAgent("T" + i);
            agent.start();
        }
    }
}
