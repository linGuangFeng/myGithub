package com.ec.service.upload;

import com.alibaba.dubbo.config.annotation.Service;
import com.ec.utils.FastDFSClient;
import com.fasterxml.jackson.annotation.JsonRawValue;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


public class UploadServiceImpl implements IUploadSerivce {

    @Value("${}")
    String url;

    /**
     * 文件上传
     * @param file
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
     return null;


    }
}
