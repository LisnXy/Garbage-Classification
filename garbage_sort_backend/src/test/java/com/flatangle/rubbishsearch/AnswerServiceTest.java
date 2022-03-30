package com.flatangle.rubbishsearch;

import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import com.flatangle.rubbishsearch.service.AnswerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DaY1zz
 * @create 2022-03-29-12:01
 */
@SpringBootTest
public class AnswerServiceTest {

    @Resource
    AnswerService answerService;

    @Test
    public void testGetQuestions(){

        for(int i = 0 ; i < 5; i++) {
            List<Garbage> questions = answerService.getQuestions(7);
            questions.forEach(System.out::println);
            System.out.println();
        }

    }


}
