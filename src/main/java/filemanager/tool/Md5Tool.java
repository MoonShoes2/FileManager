package filemanager.tool;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

public class Md5Tool {

    // 返回固定位数随机字符串
    public static String getRandomStr(int length) {
        if (length <= 0) {
            return "";
        }
        String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            sb.append(charPool.charAt(index));
        }
        return sb.toString();
    }

    // 字符串转md5
    public static String strToMd5(String input) {
        return input == null ? null : DigestUtils.md5DigestAsHex(input.getBytes());
    }

    // 给字符串撒盐并转md5
    public static String strAddSaltMd5(String key, String salt) {
        if (key == null || salt == null) {
            throw new IllegalArgumentException("key 和 salt 不能为 null");
        }
        int mid = key.length() / 2;                             // 插入盐值到 key 的中间、首尾再拼一次等
        String mixed = key.substring(0, mid) + salt + key.substring(mid);  // key 中间插入 salt
        mixed = salt + mixed + salt;                            // 再在首尾拼接盐值
        mixed = mixed + key.length() + ":" + salt.length();     // 最终再加一层混淆，加上 key 的长度和 salt 的长度
        return DigestUtils.md5DigestAsHex(mixed.getBytes());    // 返回 MD5 值
    }

}
