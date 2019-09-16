package cn.powertime.iatp.vo.resp.admin;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author liqi
 * @Description: 菜单树结构vo
 * @date 2018/8/8/00811:14
 */
public class MenuTreeVo implements Serializable {

    private Long id;
    private Long pid;
    /**
     * 在vue中name表示路由名称，非vue是资源名称
     */
    private String name;
    /**
     * code 编码
     */
    private String code;
    /**
     * vue中表示路由的path
     */
    private String url;
    /**
     * 排序
     */
    private Integer sortNum;

    private String icon1;

    private String icon2;


    private String pids;
    /**
     * 1 菜单 2 按钮
     */
    private Integer type;
    private String des;

    /**
     * 子菜单
     */
    private List<MenuTreeVo> childrenMenuTree = Lists.newArrayList();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<MenuTreeVo> getChildrenMenuTree() {
        return childrenMenuTree;
    }

    public void setChildrenMenuTree(List<MenuTreeVo> childrenMenuTree) {
        this.childrenMenuTree = childrenMenuTree;
    }


    public MenuTreeVo() {
    }

    public MenuTreeVo(Long id, Long pid, String name, String code, String url, Integer sortNum, String icon1,String icon2, Integer type, String des) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.code = code;
        this.url = url;
        this.sortNum = sortNum;
        this.icon1 = icon1;
        this.icon2 = icon2;
        this.type = type;
        this.des = des;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }
}
