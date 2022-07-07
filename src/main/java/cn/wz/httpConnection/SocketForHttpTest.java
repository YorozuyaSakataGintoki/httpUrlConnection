package cn.wz.httpConnection;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;

public class SocketForHttpTest {
    private int port;
    private String host;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public SocketForHttpTest(String host,int port) throws Exception{

        this.host = host;
        this.port = port;

        /**
         * http协议
         */
        // socket = new Socket(this.host, this.port);

        /**
         * https协议
         */
        socket = (SSLSocket)((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket(this.host, this.port);


    }

    public void sendGet() throws IOException {
        //String requestUrlPath = "/z69183787/article/details/17580325";
        String requestUrlPath = "/";

        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedWriter = new BufferedWriter(streamWriter);
        bufferedWriter.write("GET " + requestUrlPath + " HTTP/1.1\r\n");
        bufferedWriter.write("Host: " + this.host + "\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
        String line = null;
        while((line = bufferedReader.readLine())!= null){
            System.out.println(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();

    }


    public void sendPost() throws IOException{
        String path = "/";
        String data = URLEncoder.encode("name", "utf-8") + "=" + URLEncoder.encode("张三", "utf-8") + "&" +
                URLEncoder.encode("age", "utf-8") + "=" + URLEncoder.encode("32", "utf-8");
        // String data = "name=zhigang_jia";
        System.out.println(">>>>>>>>>>>>>>>>>>>>>"+data);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream(), "utf-8");
        bufferedWriter = new BufferedWriter(streamWriter);
        bufferedWriter.write("POST " + path + " HTTP/1.1\r\n");
        bufferedWriter.write("Host: " + this.host + "\r\n");
        bufferedWriter.write("Content-Length: " + data.length() + "\r\n");
        bufferedWriter.write("Content-Type: application/x-www-form-urlencoded\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.write(data);

        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
        String line = null;
        while((line = bufferedReader.readLine())!= null)
        {
            System.out.println(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        /**
         * http协议测试
         */
        //SocketForHttpTest forHttpTest = new SocketForHttpTest("www.baidu.com", 80);
        /**
         * https协议测试
         */
        SocketForHttpTest forHttpTest = new SocketForHttpTest("www.baidu.com", 443);
        try {
            forHttpTest.sendGet();
            //  forHttpTest.sendPost();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
