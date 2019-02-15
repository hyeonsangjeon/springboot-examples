package mybatis;

import com.rest.dao.BookDao;
import com.rest.dto.BookDto;
import mybatis.config.DataSourceConfigTest;
import mybatis.config.UserTestConfigruation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {UserTestConfigruation.class, DataSourceConfigTest.class})
public class MybatisQueryTest {

    @Autowired
    private BookDao bookDao;

    int expectPage =0;

    @Before
    public void setup() {
        expectPage = 792;
    }

    @Test
//    @Transactional
//    @Rollback(true)
    public void sqlSessionTest() {
        Long bookId = 1L;
        BookDto resultBook = bookDao.selectBookOne(bookId);
        assertThat(expectPage, is(equalTo(resultBook.getPages())));
    }

}

