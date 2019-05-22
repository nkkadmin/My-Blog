package com.xsx.blog.util;


import com.xsx.blog.dto.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.UUID;


/**
 * fastdfs 工具类
 */
public class FastDFSClient {


    private final static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    private static StorageClient storageClient = null;
    private static StorageServer storageServer = null;
    private static TrackerServer trackerServer = null;

    static {
        try {
//            String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();;
//            ClientGlobal.init(filePath);
            //由于SpringBoot jar部署后无法读取fdfs_client.conf 文件，所以改成一下设置参数方法
            ClientGlobal.setG_connect_timeout(600);
            ClientGlobal.setG_network_timeout(600);
            ClientGlobal.setG_charset("UTF-8");
            ClientGlobal.setG_tracker_http_port(82);
            ClientGlobal.setG_anti_steal_token(false);
            ClientGlobal.setG_secret_key("123456");
            String[] szTrackerServers = {"39.96.176.80:22122"};
            InetSocketAddress[] tracker_servers = new InetSocketAddress[szTrackerServers.length];
            for(int i = 0; i < szTrackerServers.length; ++i) {
                String[] parts = szTrackerServers[i].split("\\:", 2);
                if (parts.length != 2) {
                    throw new MyException("the value of item \"tracker_server\" is invalid, the correct format is host:port");
                }
                tracker_servers[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            }
            TrackerGroup trackerGroup = new TrackerGroup(tracker_servers);
            ClientGlobal.setG_tracker_group(trackerGroup);

            storageClient = getTrackerClient();
            trackerServer = getTrackerServer();
        } catch (Exception e) {
            logger.error("FastDFS Client Init Fail!",e);
        }
    }

    public static String[] upload(String base64, HttpServletRequest request) throws Exception {
        String suffixStr = base64.split(",")[0];
        String data = base64.split(",")[1];
        String fileName = UUID.randomUUID().toString().replace("-","") + ImageUtils.getSuffix(suffixStr.split(";")[0]);
        byte[] bs = Base64Utils.decodeFromString(data);

        FastDFSFile fastDFSFile = new FastDFSFile();
        fastDFSFile.setName(fileName);
        fastDFSFile.setAuthor("xsx");
        fastDFSFile.setContent(bs);
        fastDFSFile.setExt(ImageUtils.getSuffix((suffixStr.split(";")[0])));

        String[] files = FastDFSClient.upload(fastDFSFile);
        for(String file : files){
            System.out.println(file);
        }
        return files;
    }


    public static String[] upload(FastDFSFile file) {
        logger.info("File Name: " + file.getName() + "File Length:" + file.getContent().length);

        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", file.getAuthor());

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        StorageClient storageClient=null;
        try {
            storageClient = getTrackerClient();
            uploadResults = storageClient.upload_file(file.getContent(),file.getExt(),meta_list);
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file:" + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file:" + file.getName(), e);
        }
        logger.info("upload_file time used:" + (System.currentTimeMillis() - startTime) + " ms");

        if (uploadResults == null && storageClient!=null) {
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        logger.info("upload file successfully!!!" + "group_name:" + groupName + ", remoteFileName:" + " " + remoteFileName);
        return uploadResults;
    }

    public static InputStream downFile(String groupName, String remoteFileName) {
        try {
            storageClient = new StorageClient(trackerServer, storageServer);
            byte[] fileByte = storageClient.download_file(groupName, remoteFileName);
            InputStream ins = new ByteArrayInputStream(fileByte);
            return ins;
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static void deleteFile(String groupName, String remoteFileName)
            throws Exception {
        storageClient = new StorageClient(trackerServer, storageServer);
        int i = storageClient.delete_file(groupName, remoteFileName);
        logger.info("delete file successfully!!!" + i);
    }

    public static ServerInfo[] getFetchStorages(String groupName,
                                                String remoteFileName) throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    public static String getTrackerUrl() throws IOException {
        return "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ ClientGlobal.getG_tracker_http_port()+"/";
    }

    private static StorageClient getTrackerClient() throws IOException {
        TrackerServer trackerServer = getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return  storageClient;
    }

    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return  trackerServer;
    }


}
