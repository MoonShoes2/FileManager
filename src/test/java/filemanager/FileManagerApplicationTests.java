package filemanager;

import filemanager.entity.FileGroup;
import filemanager.service.ApkFileService;
import net.dongliu.apk.parser.ApkFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileManagerApplicationTests {

    @Autowired
    ApkFileService apkFileService;

    @Test
    void contextLoads() {
        FileGroup apkFile = apkFileService.queryFileGroupByPackageId("123");
        System.out.println(apkFile);
    }

}
