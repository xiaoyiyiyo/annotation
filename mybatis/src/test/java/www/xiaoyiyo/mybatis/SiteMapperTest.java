package www.xiaoyiyo.mybatis;

import www.xiaoyiyiyo.mybatis.bean.SiteDo;
import www.xiaoyiyiyo.mybatis.operate.Insert;

import java.util.List;

/**
 * Created by xiaoyiyiyo on 2018/5/23.
 */
public interface SiteMapperTest {

    @Insert("select * from inav_site")
    public List<SiteDo> listSites();

}
