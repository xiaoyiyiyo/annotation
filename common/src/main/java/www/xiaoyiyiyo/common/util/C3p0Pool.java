package www.xiaoyiyiyo.common.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by xiaoyiyiyo on 2018/5/21.
 */
public class C3p0Pool {

    private static ComboPooledDataSource dataSource = null;

    static {
        try {
            Properties config = new Properties();
            config.load(C3p0Pool.class.getClassLoader().getResourceAsStream("c3p0.properties"));
            //声明C3P0数据源对象
            dataSource = new ComboPooledDataSource();
            //设置数据库连接驱动
            dataSource.setDriverClass(
                    config.getProperty("driver"));
            //设置数据连接URL
            dataSource.setJdbcUrl(
                    config.getProperty("url"));
            //设置数据库连接用户账号
            dataSource.setUser(
                    config.getProperty("user"));
            //设置数据库连接用户账号的密码
            dataSource.setPassword(
                    config.getProperty("password"));

            //设置数据库连接池中的初始化连接对象数量
            dataSource.setInitialPoolSize(30);
            //设置数据库连接池中的最小连接对象数量
            dataSource.setMinPoolSize(30);
            //设置数据库连接池中的最大连接对象数量
            dataSource.setMaxPoolSize(60);
            //当连接不够，每次新增连接数量
            dataSource.setAcquireIncrement(10);
        }catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ThreadLocal<Connection> connThreadLocal = new ThreadLocal<>();

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConn() throws SQLException {
        Connection conn = connThreadLocal.get();
        if (conn == null) {
            conn = dataSource.getConnection();
            connThreadLocal.set(conn);
        }
        return conn;
    }

    public static void release(Connection connection, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }
}
