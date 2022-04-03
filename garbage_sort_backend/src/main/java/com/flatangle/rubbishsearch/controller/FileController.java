package com.flatangle.rubbishsearch.controller;

import com.flatangle.rubbishsearch.common.Result;
import com.flatangle.rubbishsearch.service.GetLabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/uploadImgAPP")
public class FileController {

    @Resource
    private GetLabelService getLabelService;

    /**
     *
     * @param file
     * @param request
     * @return  图片label的序号
     */
    @RequestMapping("/getFile")
    public Result<Integer> uploadImgAPP(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        String fileName = "";
        int result = -1;
        if (file != null && !file.isEmpty()) {
            try {
                //外层文件目录
                String targetSrc = request.getServletContext().getRealPath("/")+"files";
                fileName = file.getOriginalFilename();
                fileName = fileName.substring(fileName.lastIndexOf("."));
                fileName = UUID.randomUUID().toString() + fileName;
                File targetDir = new File(targetSrc);
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }
                File targetFile = new File(targetSrc, fileName);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                file.transferTo(targetFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getLabelService.insertPicture(fileName);
            result = getLabelService.getLabel(fileName); // 这里会做修改，之后直接返回种类
        }
        return Result.success(result);
    }
}
