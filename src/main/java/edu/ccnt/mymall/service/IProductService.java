package edu.ccnt.mymall.service;

import com.github.pagehelper.PageInfo;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.Product;
import edu.ccnt.mymall.vo.ProductDetailVo;

public interface IProductService {
    ServerResponse productSaveOrInsert(Product product);

    ServerResponse<String> updateProductStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVo> getDetail(Integer productId);

    ServerResponse<PageInfo> manageGetProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProductList(String productName,Integer productId,int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProducts(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
