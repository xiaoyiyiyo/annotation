package www.xiaoyiyo.mybatis;

import www.xiaoyiyiyo.mybatis.bean.SiteDo;
import www.xiaoyiyiyo.mybatis.operate.Insert;
import www.xiaoyiyiyo.mybatis.operate.Select;

import java.util.List;

/**
 * Created by xiaoyiyiyo on 2018/5/23.
 */
public interface SiteMapper {

    @Select("select * from inav_site")
    public List<SiteDo> listSites();

}
