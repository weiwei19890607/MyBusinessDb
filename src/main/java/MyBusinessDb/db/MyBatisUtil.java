package MyBusinessDb.db;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	
    private static SqlSessionFactory sqlSessionFactory;

    static {
    	if (sqlSessionFactory == null)
    	{
    		String resource = "mybatis-config.xml";
    	
            Reader reader = null;
            try {
                reader = Resources.getResourceAsReader(resource);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }
    }
    
    public static SqlSession openSession()
    {
    	return sqlSessionFactory.openSession();
    }
}