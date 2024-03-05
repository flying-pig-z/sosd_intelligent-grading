package com.flyingpig.service.impl;

import com.flyingpig.dataobject.entity.Question;
import com.flyingpig.mapper.QuestionMapper;
import com.flyingpig.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-18
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

}
