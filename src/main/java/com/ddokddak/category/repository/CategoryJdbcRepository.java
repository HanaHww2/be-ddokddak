package com.ddokddak.category.repository;

import com.ddokddak.category.dto.CategoryTemplateJdbcDto;
import com.ddokddak.category.enums.CategoryTemplate;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.JdbcExecException;
import com.ddokddak.common.exception.type.FailedJdbcExec;
import com.ddokddak.common.repository.JdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CategoryJdbcRepository implements JdbcRepository<CategoryTemplateJdbcDto> {

    @Value("${jdbc.batch.size}")
    private int batchSize;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void batchInsert(int batchSize, List<CategoryTemplateJdbcDto> list) {
        var sql = "INSERT INTO CATEGORY (MEMBER_ID, NAME, COLOR, LEVEL, PARENT_ID, DELETE_YN, CREATED_AT) " +
                "SELECT ?, ?, ?, ?, ID, ?, NOW() FROM CATEGORY WHERE MEMBER_ID=? AND NAME=?";
        try {
            jdbcTemplate.batchUpdate(
                    sql,
                    list,
                    batchSize,
                    (ps, arg) -> {
                        ps.setLong(1, arg.memberId());
                        ps.setString(2, arg.name());
                        ps.setString(3, arg.color());
                        ps.setInt(4, arg.level());
                        ps.setString(5, "N");
                        ps.setLong(6, arg.memberId());
                        ps.setString(7, arg.mainCategoryName());
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new JdbcExecException(FailedJdbcExec.BATCH_INSERTION_FAIL);
        }
    }

    @Transactional
    public void batchInsert(List<CategoryTemplate> list, Long memberId) {
        var sql = "INSERT INTO CATEGORY (MEMBER_ID, NAME, COLOR, LEVEL, PARENT_ID, DELETE_YN, CREATED_AT) " +
                "VALUES " +
                "( ?, ?, ?, ?, (SELECT ID FROM (SELECT ID FROM CATEGORY WHERE MEMBER_ID=? AND NAME=?) AS SUB), 'N', NOW())";
        try {
            jdbcTemplate.batchUpdate(
                    sql,
                    list,
                    batchSize,
                    (ps, arg) -> {
                        ps.setLong(1, memberId);
                        ps.setString(2, arg.getName());
                        ps.setString(3, arg.getColor());
                        ps.setInt(4, arg.getParentName()==null?0:1);
                        ps.setLong(5, memberId);
                        ps.setString(6, arg.getParentName());
                        //ps.setString(7, "N");
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomApiException(FailedJdbcExec.BATCH_INSERTION_FAIL);
        }
    }
}
