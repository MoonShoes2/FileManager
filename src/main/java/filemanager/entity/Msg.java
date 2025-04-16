package filemanager.entity;
import filemanager.tool.Md5Tool;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Msg {

    private Integer code;           // 状态码

    private String message;         // 提示信息

    private Object data;            // 数据

    private Long timeStamp;         // 时间戳

    private String key;             // 校验信息

    private String salt;            // 盐

    public Msg() {
    }

    public Msg(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timeStamp = System.currentTimeMillis();
        this.salt = Md5Tool.getRandomStr(10);
        this.key = Md5Tool.strAddSaltMd5(String.valueOf(this.timeStamp), this.salt);
    }

    public Msg(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timeStamp = System.currentTimeMillis();
        this.salt = Md5Tool.getRandomStr(10);
        this.key = Md5Tool.strAddSaltMd5(String.valueOf(this.timeStamp), this.salt);
    }

}
