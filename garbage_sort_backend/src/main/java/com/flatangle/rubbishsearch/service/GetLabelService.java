package com.flatangle.rubbishsearch.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.flatangle.rubbishsearch.POJO.entity.Picture;
import com.flatangle.rubbishsearch.mapper.PictureMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GetLabelService {

    @Resource
    private PictureMapper pictureMapper;

    public List<Picture> findByPath(String path){
        LambdaQueryWrapper<Picture> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Picture::getPath, path);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        return pictures;
    }

    public int getLabel(String path){

        LambdaQueryWrapper<Picture> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Picture::getPath, path);
        while (true){
            Picture picture = pictureMapper.selectOne(queryWrapper);
            if(picture.getLabel() != -1){
                pictureMapper.delete(queryWrapper);
                return picture.getLabel();
            }
        }
    }

    public void insertPicture(String path){

        Picture picture = new Picture();
        picture.setPath(path);
        pictureMapper.insert(picture);

    }


}
