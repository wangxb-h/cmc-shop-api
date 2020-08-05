package com.fh.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class OssUtil {
    public  static String uploadFile(File file) throws Exception {
        File file1 =new File("F:\\u=2000179457,3490835254&fm=26&gp=0.jpg");
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
        String endpointPath="oss-cn-qingdao.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4GC1TCfafNBcp7z8dRzu";
        String accessKeySecret = "F5htsNBEyryBQhbvRyWCpKsTdrcARW";
        String bucketName="fh-1908a-wxb";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = new FileInputStream(file);
        ossClient.putObject(bucketName, file.getName(), inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return "https://"+bucketName+"."+endpointPath+"/"+file.getName();
    }

}
