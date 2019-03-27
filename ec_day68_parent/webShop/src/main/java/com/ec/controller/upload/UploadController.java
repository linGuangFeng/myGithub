package com.ec.controller.upload;

import com.ec.pojo.resutl.Result;
import com.ec.utils.FastDFSClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Value("${serviceAddress}")
    String url;


    @RequestMapping("uploadFile")
    public Result uploadFile(MultipartFile file)throws Exception{


        System.out.println("开始上传文件!");

        /**
         * 1、初始化配置文件
         * 2、创建跟踪服务器客户端
         * 3、获取跟踪服务器对象
         * 4、获取存储服务器对象
         * 5、上传并获取上传位置
         * 6、上传地址与服务器地址拼接并返回
         */

        try {
            String conf = "classpath:/fastDFS/fdfs_client.conf";
            String originalFilename = file.getOriginalFilename();
            String extName = FilenameUtils.getExtension(originalFilename);//获取扩展名


            FastDFSClient fastDFSClient = new FastDFSClient(conf);//工具类获取

     /* //原始方法：上传至服务器
        ClientGlobal.init(conf);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,null);
        String path = storageClient1.upload_appender_file1("文件名","后缀名",null);

      */

            String path = fastDFSClient.uploadFile(file.getBytes(), extName, null);

            return new Result(true ,url + path);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"上传失败！");
        }




    }
    /**
     * 附件上传
     * @param file
     * @return
     */
    @RequestMapping("/uploadFile1.do")
    public Result uploadFile1(MultipartFile file){
        try{
            // 将附件上传到FastDFS上
            String conf = "classpath:fastDFS/fdfs_client.conf";
            FastDFSClient fastDFSClient = new FastDFSClient(conf);
            // 附件扩展名
            String filename = file.getOriginalFilename(); // xxx.jpg
            String extName = FilenameUtils.getExtension(filename);
            String path = fastDFSClient.uploadFile(file.getBytes(), extName, null);
            // http
            // http://192.168.200.128/
            // java：维护常量  配置文件、枚举、接口（对修改关闭，对扩展开发）

//            String url = "http://192.168.200.128/" + path;
            return new Result(true, url + path);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "上传失败");
        }

    }

}
