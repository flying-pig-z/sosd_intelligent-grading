package com.flyingpig.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flyingpig.dataobject.entity.Question;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("SELECT MAX(score_order) FROM question")
    Long findCorrectedNum();
}
