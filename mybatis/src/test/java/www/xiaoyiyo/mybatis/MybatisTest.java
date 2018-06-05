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
    public void listSites() {
        SiteMapper siteMapper = ProxyFactory.getBean(SiteMapper.class);
        List<SiteDo> sites = siteMapper.listSites();
        for (SiteDo siteDo : sites) {
            System.out.println(siteDo.getName());
        }
    }

    @Test
    public void getSite() {
        SiteMapper siteMapper = ProxyFactory.getBean(SiteMapper.class);
        SiteDo site = siteMapper.getSite("CSDN");
        System.out.println(site.getSite_url());
    }

}
