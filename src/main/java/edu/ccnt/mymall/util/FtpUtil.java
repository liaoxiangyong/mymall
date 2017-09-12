package edu.ccnt.mymall.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

//ftp工具类
@Slf4j
@Data
public class FtpUtil {

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static String ftpUser=PropertiesUtil.getProperty("ftp.server.user");
    private static String ftpPass =PropertiesUtil.getProperty("ftp.server.password");
    private static int ftpPort = 21;

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FtpUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    //连接ftp服务器
    private boolean connectServer(String ip,int port,String user,String password){
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user,password);
        } catch (IOException e) {
            log.error("ftp服务器连接异常",e);
        }
        return isSuccess;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FtpUtil ftpUtil = new FtpUtil(ftpIp,ftpPort,ftpUser,ftpPass);
        log.info("开始连接ftp服务器");
        boolean result = ftpUtil.uploadFile("img",fileList);
        log.info("开始连接ftp服务器,结束上传,上传结果:{}");
        return result;
    }

    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接ftp服务器
        try {
            if(connectServer(this.ftpIp,this.ftpPort,this.ftpUser,this.ftpPass)){
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                for(File fileItem : fileList){
                    fis  = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);
                }
            }
        }catch (IOException e){
            log.error("文件上传失败",e);
            uploaded = false;
            e.printStackTrace();

        }finally {
            fis.close();
            ftpClient.disconnect();
        }
        return uploaded;
    }
}
