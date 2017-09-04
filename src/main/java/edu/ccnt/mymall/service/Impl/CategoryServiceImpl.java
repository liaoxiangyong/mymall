package edu.ccnt.mymall.service.Impl;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.CategoryMapper;
import edu.ccnt.mymall.model.Category;
import edu.ccnt.mymall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse<String> addCategory(Integer parentId,String categoryName){
        log.info("添加品类："+categoryName);
        if(parentId==null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加商品品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(1);
        int resultCount = categoryMapper.insert(category);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }
}
