package com.rest.dao;

import com.rest.dto.BookDto;
import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository("BookDao")
public class BookDao {
    private static final Logger logger = LoggerFactory.getLogger(BookDao.class);

    @Autowired
    @Qualifier("testSqlSessionTemplate")
    private SqlSession sqlSession;

    public String getCurrentTime() throws DataAccessException {
        return sqlSession.selectOne("example.mappers.Book.getCurrentTime");
    }

    public BookDto selectBookOne(Long id) throws DataAccessException {
        BookDto result = sqlSession.selectOne("example.mappers.Book.selectBookOne",id);
        return result;
    }


}
