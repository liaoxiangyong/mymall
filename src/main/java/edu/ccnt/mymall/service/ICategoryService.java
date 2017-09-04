package edu.ccnt.mymall.service;

import edu.ccnt.mymall.common.ServerResponse;

public interface ICategoryService {

    ServerResponse<String> addCategory(Integer parentId, String categoryName);
}
