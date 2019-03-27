import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.net.Socket;

public class fastDFC {
    public static void main(String[] args) throws Exception {
        /**
         * 初始化文档
         * 创建跟踪服务器客户端
         * 创建跟踪服务器
         * 创建服务器存储对象
         * 上传
         */
        String path = "E:\\IDEA\\program\\ec2\\fastDFC\\src\\main\\resources\\fastConf.conf";
        ClientGlobal.init(path);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        Socket socket = trackerServer.getSocket();
        StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
        path = storageClient1.upload_appender_file1("文件名", "后缀", null);
        System.out.println("上传路径：" + path);
    }
}
