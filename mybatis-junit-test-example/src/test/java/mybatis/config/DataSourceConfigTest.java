/*************************************************
 * File Name : API 용 DataSource
 * Author:     hyeonsangjeon
 * Crt Date :  2017.07.10
 * DESC : API mybatis config, 복잡한 쿼리나 서브쿼리의 경우 가독성을 위해 mybatis를 이용한다. 
 ***************Update Record*********************
 * Date             Author                 UpdateInfo
 * 2017.07.03       hyeonsangjeon                Make file.
 * 
 */
package mybatis.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfigTest {

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://YOUR_IP:YOUR_PORT/YOURDATABASE");
		dataSource.setUsername("USER_ID");
		dataSource.setPassword("USER_PW");

		return dataSource;
	}


	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(org.springframework.context.ApplicationContext applicationContext, DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "testSqlSessionTemplate", destroyMethod = "clearCache")
	public SqlSessionTemplate testSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);

		return transactionManager;
	}


}