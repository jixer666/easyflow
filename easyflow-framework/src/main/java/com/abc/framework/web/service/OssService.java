package com.abc.framework.web.service;

import com.abc.system.domain.vo.FileVO;
import com.abc.system.domain.vo.FileUploadDTO;
import org.springframework.http.ResponseEntity;

public interface OssService {

    FileVO uploadOss(FileUploadDTO req);

    ResponseEntity<byte[]> downloadOss(Long fileId);

}
