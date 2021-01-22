import com.bluesgao.dds.DataSourceUtils;
import com.bluesgao.dds.DynamicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class DseTest {

    private JdbcTemplate jdbcTemplate;

    private DynamicDataSource dynamicDataSource;

    @Before
    public void before(){
        dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.addDataSource("127.0.0.1", 3306, "root", "123456", "test");
        DataSourceUtils.setDbKey("127.0.0.1:3306:test");
        jdbcTemplate = new JdbcTemplate(dynamicDataSource);
        jdbcTemplate.setDataSource(dynamicDataSource);
    }

    @Test
    public void test() {
        System.out.println("ok");
        int i = (Integer) jdbcTemplate.queryForObject("select count(*) from t_gg_zsdw", Integer.class);
        System.out.println("i=" + i);
    }
}
