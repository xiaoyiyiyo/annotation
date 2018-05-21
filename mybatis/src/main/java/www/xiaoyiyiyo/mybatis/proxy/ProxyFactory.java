package www.xiaoyiyiyo.mybatis.proxy;

import www.xiaoyiyiyo.mybatis.proxy.AnnoMethodInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * Created by xiaoyiyiyo on 2018/5/17.
 */
public class ProxyFactory {

    public static <T> T getBean(Class<T> clazz) {
        AnnoMethodInvocationHandler invocationHandler = new AnnoMethodInvocationHandler();
        return (T)Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{clazz},
                invocationHandler);
    }
}
