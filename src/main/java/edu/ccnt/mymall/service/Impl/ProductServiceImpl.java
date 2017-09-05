package edu.ccnt.mymall.service.Impl;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.ProductMapper;
import edu.ccnt.mymall.model.Product;
import edu.ccnt.mymall.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse productSaveOrInsert(Product product){
        log.info("更新或添加商品信息："+product);
        if(product != null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] images = product.getSubImages().split(",");
                if(images.length>0){
                    product.setMainImage(images[0]);
                }
            }
            if(product.getId()!=null){      //添加商品
                int resultCount = productMapper.insert(product);
                if(resultCount>0){
                    return ServerResponse.createBySuccess("添加商品成功");
                }
                return ServerResponse.createByErrorMessage("添加商品失败");
            }else{  //更新商品
                int resultCount = productMapper.updateByPrimaryKeySelective(product);
                if(resultCount>0){
                    return ServerResponse.createBySuccess("更新商品成功");
                }
                return ServerResponse.createByErrorMessage("更新商品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新商品参数不正确");
    }

    @Override
    public ServerResponse<String> updateProductStatus(Integer productId,Integer status){
        log.info("更新商品状态");
        if(productId == null || status == null){
            return ServerResponse.createByErrorMessage("更新商品状态参数错误");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount = productMapper.updateByPrimaryKeySelective(product);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("更新商品状态成功");
        }
        return ServerResponse.createByErrorMessage("更新商品状态失败");
    }
}
