package com.flatangle.rubbishsearch.controller;

import com.flatangle.rubbishsearch.POJO.entity.Garbage;
import com.flatangle.rubbishsearch.POJO.params.CompleteAnswerParams;
import com.flatangle.rubbishsearch.common.Result;
import com.flatangle.rubbishsearch.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DaY1zz
 * @create 2022-03-29-17:29
 */
@RestController
@RequestMapping("/answer")
public class AnswerController {


    @Resource
    private AnswerService answerService;

    @GetMapping
    public Result<?> getQuestions(@RequestParam int cityID) {
        List<Garbage> questions = answerService.getQuestions(cityID);
        return Result.success(questions);
    }

    @PostMapping("/complete")
    public Result<?> completeAnswer(@RequestBody CompleteAnswerParams completeAnswerParams) {
        answerService.completeAnswer(completeAnswerParams.getUserID(),completeAnswerParams.getScore(),completeAnswerParams.getFalseRecord());
        return Result.success();
    }


}
