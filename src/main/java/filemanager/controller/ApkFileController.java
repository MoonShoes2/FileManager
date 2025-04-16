package filemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import filemanager.entity.FileGroup;
import filemanager.entity.Msg;
import filemanager.service.ApkFileService;
import filemanager.tool.ApplicationTool;
import filemanager.tool.NumberTool;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.*;

/**
 * @BelongsProject: FileManager
 * @BelongsPackage: filemanager.controller
 * @Author: 梁晓飞
 * @CreateTime: 2025-03-31  14:38
 * @Description: TODO apk文件操作控制器
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/apk_file")
public class ApkFileController {

    @Autowired
    ApkFileService apkFileService;

    @Value("${file.apk-path-temp}")
    private String tempPath;

    @PostMapping("/upload")
    public Msg uploadFile(
            @RequestParam("file") MultipartFile file,               //文件块
            @RequestParam("fileName") String fileName,              //文件名
            @RequestParam("currentChunk") int currentChunk,         //当前块的索引
            @RequestParam("totalChunks") int totalChunks,           //总块数
            @RequestParam("userId") int userId                      //用户id
    ) {
        try {
            File userTempDir = new File(tempPath + "\\" + userId);                  //临时文件夹
            File tempFile = new File(userTempDir, fileName);        //临时文件
            // 创建用户特定的临时文件夹
            if (!userTempDir.exists()) {
                userTempDir.mkdirs();
            }
            // 上传第一个文件块时，删除临时目录下同名文件
            if (currentChunk == 0) {
                if (tempFile.exists()) {
                    boolean deleted = tempFile.delete();    //删除旧的临时文件
                    if (!deleted) {
                        return new Msg(1, "无法删除旧的临时文件");
                    }
                }
                tempFile.createNewFile();                   //创建新的临时文件
                ApplicationTool.addFileTemp(userId, tempFile.getPath());
            }
            // 文件块追加到临时文件中
            try (FileOutputStream outputStream = new FileOutputStream(tempFile, true)) {
                byte[] bytes = file.getBytes();
                outputStream.write(bytes);
            }
            // 如果所有文件块都上传完成
            if (currentChunk == totalChunks - 1) {
                try (ApkFile apk = new ApkFile(tempFile)) {
                    ApkMeta apkMeta = apk.getApkMeta();
                    // 保存图标文件到临时目录
                    String iconTemp = String.valueOf(System.currentTimeMillis());
                    iconTemp = apkFileService.saveApkIconTemp(apk, apkMeta, iconTemp, tempPath + "\\" + userId);
                    // 整理返回值
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("packageId", apkMeta.getPackageName());
                    result.put("packageName", apkMeta.getName());
                    result.put("versionCode", apkMeta.getVersionCode());
                    result.put("versionName", apkMeta.getVersionName());
                    result.put("icon", iconTemp);
                    result.put("size", (double) (tempFile.length() * 100 / 1024 / 1024) / 100 + " MB");
                    ApplicationTool.fileTempEnd(tempFile.getPath(), tempPath + "\\" + userId + "\\" + iconTemp);
                    return new Msg(0, "文件上传完成", result);
                }
            }
            ApplicationTool.fileTempUpdate(tempFile.getPath());
            return new Msg(0, "文件块总数：" + totalChunks + "，已提交：" + currentChunk, NumberTool.getPercent(currentChunk, totalChunks));
        } catch (IOException e) {
            return new Msg(1, "文件上传异常", e.getMessage());
        }
    }

    // 获取临时icon文件
    @GetMapping("/file_temp")
    public ResponseEntity<Resource> fileTemp(@RequestParam int userId, @RequestParam String fileName) {
        try {
            // 创建文件对象
            File file = new File(tempPath, userId + "\\" + fileName);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();  // 如果文件不存在，返回 404
            }
            // 创建 Resource 对象，读取文件
            Resource resource = new FileSystemResource(file);
            // 设置响应头
            String contentDisposition = "attachment; filename=\"" + file.getName() + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // 通常设置为二进制流
                    .body(resource);
        } catch (Exception e) {
            // 如果发生异常，返回 500 错误
            return ResponseEntity.status(500).body(null);
        }
    }

    // 根据包id查询项目
    @PostMapping("/query_group_info_by_packageid")
    public Msg queryGroupInfoByPackageId(@RequestParam("packageId") String packageId) {
        Map<String, Object> fileGroupInfo = apkFileService.queryGroupInfoByPackageId(packageId);
        if (fileGroupInfo == null) {
            return new Msg(1, "无文件组");
        } else {
            return new Msg(0, "查询成功", fileGroupInfo);
        }
    }

}
