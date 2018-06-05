package www.xiaoyiyo.mybatis;

import www.xiaoyiyiyo.mybatis.bean.SiteDo;
import www.xiaoyiyiyo.mybatis.operate.Delete;
import www.xiaoyiyiyo.mybatis.operate.Insert;
import www.xiaoyiyiyo.mybatis.operate.Select;
import www.xiaoyiyiyo.mybatis.operate.Update;

import java.util.List;

/**
 * Created by xiaoyiyiyo on 2018/5/23.
 */
public interface SiteMapper {

    @Select("select * from inav_site")
    List<SiteDo> listSites();

    @Select("select * from inav_site where name = ?")
    SiteDo getSite(String name);

    @Insert("insert into inav_site(site_url, name, desc) values(?, ?, ?)")
    void addSite(String siteUrl, String name, String desc);

    @Delete("delete from inav_site where name = ?")
    void deleteSite(String name);

    @Update("update inav_site set name = ?, site_url = ?, desc = ? where name = ?")
    void updateSite(String newName, String siteUrl, String desc, String oldName);
}
