package filemanager.mapper;

import filemanager.entity.FileGroup;
import net.dongliu.apk.parser.ApkFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApkFileMapper {

    FileGroup queryFileGroupByPackageId(String packageId);

    String queryFileVersionNameById(int id);

}
