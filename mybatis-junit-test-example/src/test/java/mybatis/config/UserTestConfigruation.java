package mybatis.config;

import com.rest.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class UserTestConfigruation {
    @Bean("bookDao")
    @DependsOn("testSqlSessionTemplate")
    public BookDao bookDao() {
        return new BookDao();
    }

}
