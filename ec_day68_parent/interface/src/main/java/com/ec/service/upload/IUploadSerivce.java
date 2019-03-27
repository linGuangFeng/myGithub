package com.ec.service.upload;


import org.springframework.web.multipart.MultipartFile;

public interface IUploadSerivce {

    String uploadFile(MultipartFile file) throws Exception;
}
