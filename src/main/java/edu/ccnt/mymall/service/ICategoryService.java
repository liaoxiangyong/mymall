package edu.ccnt.mymall.service;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.Category;

import java.util.List;

public interface ICategoryService {

    ServerResponse<String> addCategory(Integer parentId, String categoryName);

    ServerResponse<String> updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> searchChildParallelCategory(Integer parentId);

    ServerResponse<List<Integer>> searchChildCategory(Integer parentId);
}
