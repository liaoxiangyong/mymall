package edu.ccnt.mymall.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.CategoryMapper;
import edu.ccnt.mymall.model.Category;
import edu.ccnt.mymall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

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

    @Override
    public ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName){
        log.info("修改品类名称");
        if(categoryId==null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<List<Category>> searchChildParallelCategory(Integer parentId){
        log.info("查找当前类的子分类");
        List<Category> list = categoryMapper.searchChildParallelCategory(parentId);
        if(CollectionUtils.isEmpty(list)){
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<Integer>> searchChildCategory(Integer parentId){
        log.info("查找当前类及其所有子分类");
        Set<Category> categorySet = Sets.newHashSet();
        getChildCategory(categorySet,parentId);

        List<Integer> categoryList = Lists.newArrayList();
        if(parentId!=null){
            for(Category categoryItem : categorySet){
                categoryList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    //递归算法求解
    private Set<Category> getChildCategory(Set<Category> result, Integer parentId){
        Category category  = categoryMapper.selectByPrimaryKey(parentId);
        if(category != null){
            result.add(category);
        }

        List<Category> childCategories = categoryMapper.searchChildParallelCategory(parentId);
        for(Category category1 : childCategories){
            getChildCategory(result,category1.getId());
        }
        return result;
    }
}
