package filemanager.entity;

import java.util.Date;

// 项目组实体类
public class FileGroup {

    private int id;                         // id

    private String name;                    // 名称

    private String type;                    // 文件组类型【FILE、APP、OTHER】

    private String logoAddress;             // 图标文件地址

    private String packageId;               // 软件打包id【如果是APK或IPA，存包id】

    private String memo;                    // 简介

    private int permissionType;             // 权限类型 【0:私人，1:公共】

    private int createUserId;               // 创建人id

    private Date createTime;                // 创建时间

    private int latestFileId;               // 最新文件id

    public int getLatestFileId() {
        return latestFileId;
    }

    public void setLatestFileId(int latestFileId) {
        this.latestFileId = latestFileId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getLogoAddress() {
        return logoAddress;
    }

    public void setLogoAddress(String logoAddress) {
        this.logoAddress = logoAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

