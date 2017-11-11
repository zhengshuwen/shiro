package junit;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
*配置文件载入类 。
*所有的在spring环境的测试类都需要继承此类。
**/

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml","classpath:spring-mvc.xml"})

/** 添加注释@Transactional 回滚对数据库操作*/
@Transactional
public class JUnitBase{ 
	
}