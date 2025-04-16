package filemanager.tool;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongsProject: FileManager
 * @BelongsPackage: filemanager.tool
 * @Author: 梁晓飞
 * @CreateTime: 2025-03-31  16:19
 * @Description: TODO 全局工具类
 * @Version: 1.0
 */
@Component
public class ApplicationTool {

    /* 存储各个用户token */
    private static HashMap<Integer, String> token = new HashMap<>();

    /* 存储临时目录中的文件 */
    private static Map<String, HashMap> fileTempMap = new ConcurrentHashMap<>();

    /* 插入临时文件 */
    public static void addFileTemp(int usrId, String path) {
        HashMap<String, Object> fileTemp = new HashMap<>();
        fileTemp.put("userId", usrId);
        fileTemp.put("timeStamp", System.currentTimeMillis());
        fileTemp.put("state", 0);
        fileTempMap.put(path, fileTemp);
    }

    /* 更新临时文件时间戳 */
    public static void fileTempUpdate(String path) {
        HashMap<String, Object> fileTemp = fileTempMap.get(path);
        if (fileTemp != null)
            fileTemp.put("timeStamp", System.currentTimeMillis());
    }

    /* 修改临文件状态为已全部接收 */
    public static void fileTempEnd(String path, String logoPath) {
        HashMap<String, Object> fileTemp = fileTempMap.get(path);
        if (fileTemp != null)
            fileTemp.put("state", 1);
            fileTemp.put("timeStamp", System.currentTimeMillis());
            fileTemp.put("logoPath", logoPath);
    }


}
