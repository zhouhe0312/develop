package cn.powertime.iatp.filepreview.model;

/**
 * Created by kl on 2018/1/17.
 * Content :
 */
public class FileAttribute {

    private FileType type;

    private String suffix;

    private String name;

    private String fileId;

    private String uri;

    private String path;

    public FileAttribute() {
    }

    public FileAttribute(FileType type, String suffix, String name,String fileId, String uri, String path) {
        this.type = type;
        this.suffix = suffix;
        this.name = name;
        this.fileId = fileId;
        this.uri = uri;
        this.path = path;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
