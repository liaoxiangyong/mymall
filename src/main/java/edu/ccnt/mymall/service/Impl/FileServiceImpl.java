package edu.ccnt.mymall.service.Impl;

import com.google.common.collect.Lists;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.service.IFileService;
import edu.ccnt.mymall.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service("iFileService")
public class FileServiceImpl implements IFileService{


    public String uploadFile(MultipartFile file,String path){
        log.info("上传文件");
        String fileName = file.getName();
        //文件扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("上传文件名为：{},上传文件路径为：{},新文件名：{}",fileName,path,uploadName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadName);

        try {
            //文件上传成功
            file.transferTo(targetFile);
            //已经上传到ftp服务器上
            FtpUtil.uploadFile(Lists.newArrayList(targetFile));
            //删除在tomcat中的文件
            targetFile.delete();
        } catch (IOException e) {
            log.error("文件上传异常",e);
            e.printStackTrace();
            return null;
        }
        return targetFile.getName();
    }
}
