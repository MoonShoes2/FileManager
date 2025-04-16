package filemanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import filemanager.entity.FileGroup;
import filemanager.mapper.ApkFileMapper;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.*;

@Service
public class ApkFileService {

    @Autowired
    ApkFileMapper apkFileMapper;

    // 根据apk包id查询文件组信息
    public Map<String, Object> queryGroupInfoByPackageId(String packageId) {
        // 查询文件组信息
        FileGroup fileGroup = apkFileMapper.queryFileGroupByPackageId(packageId);
        if (fileGroup == null) {
            return null;
        }
        // 将FileGroup实体类转为HashMap
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.convertValue(fileGroup, Map.class);
        // 查询版本号
        String version = apkFileMapper.queryFileVersionNameById(fileGroup.getId());
        result.put("latestVersion", version);
        return result;
    }

    // --暂时没用
    public FileGroup queryFileGroupByPackageId(String packageId) {
        return apkFileMapper.queryFileGroupByPackageId(packageId);
    }

    // 保存图片图标到目录
    public String saveApkIconTemp(ApkFile apkFile, ApkMeta apkMeta, String fileNameWithoutExt, String path) {
        try {
            if (apkMeta == null)
                apkMeta = apkFile.getApkMeta();
            String iconFilePath = apkMeta.getIcon();  // 从 ApkMeta 获取图标的文件路径
            if (iconFilePath == null)
                throw new Exception("APK 文件没有图标");
            byte[] iconBytes = apkFile.getFileData(iconFilePath); // 获取图标文件的字节数组
            // 自动识别图片格式
            String formatName = "png"; // 默认格式
            try (ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(iconBytes))) {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    formatName = reader.getFormatName().toLowerCase();  // 比如 png、jpeg、bmp
                }
            }
            // 构建目录
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();
            // 拼接文件名：fileName + "." + formatName
            File iconFile = new File(dir, fileNameWithoutExt + "." + formatName);
            // 保存图标
            try (FileOutputStream fos = new FileOutputStream(iconFile)) {
                fos.write(iconBytes);
                fos.flush();
            }
            return iconFile.getName();
        } catch (Exception e) {
            System.err.println("保存图标失败: " + e.getMessage());
            return null;
        }
    }

    // File类转base64
    public String fileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            System.err.println("文件转Base64失败: " + e.getMessage());
            return null;
        }
    }

}
