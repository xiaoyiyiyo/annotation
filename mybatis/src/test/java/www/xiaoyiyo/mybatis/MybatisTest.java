package www.xiaoyiyo.mybatis;

import org.junit.Test;
import www.xiaoyiyiyo.mybatis.bean.SiteDo;
import www.xiaoyiyiyo.mybatis.proxy.ProxyFactory;

import java.util.List;

/**
 * Created by xiaoyiyiyo on 2018/5/24.
 */
public class MybatisTest {

    @Test
    public void findSite() {
        SiteMapper siteMapper = ProxyFactory.getBean(SiteMapper.class);
        List<SiteDo> sites = siteMapper.listSites();
        for (SiteDo siteDo : sites) {
            System.out.println(siteDo.getName());
        }
    }

}
