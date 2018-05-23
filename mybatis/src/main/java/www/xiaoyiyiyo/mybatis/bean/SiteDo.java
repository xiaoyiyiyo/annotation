package www.xiaoyiyiyo.mybatis.bean;

/**
 * Created by xiaoyiyiyo on 2018/5/23.
 *
 * 属性字段的映射还需要处理，比如该类中的属性不应有下划线。
 */
public class SiteDo {

    private String site_url;

    private String name;

    private String desc;

    public SiteDo() {
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
