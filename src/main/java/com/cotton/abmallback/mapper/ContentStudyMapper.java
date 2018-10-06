package com.cotton.abmallback.mapper;

import com.cotton.abmallback.model.ContentStudy;
import com.cotton.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ContentStudyMapper extends BaseMapper<ContentStudy> {
    @Update("update content_study set message_status = #{status} where id=#{id}")
    void updateStatus(
            @Param(value = "id") Long id,
            @Param(value = "status") String status
    );
}