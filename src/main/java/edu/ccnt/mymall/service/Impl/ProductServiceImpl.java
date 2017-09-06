package edu.ccnt.mymall.service.Impl;

import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.CategoryMapper;
import edu.ccnt.mymall.dao.ProductMapper;
import edu.ccnt.mymall.model.Category;
import edu.ccnt.mymall.model.Product;
import edu.ccnt.mymall.service.IProductService;
import edu.ccnt.mymall.util.DateTimeUtil;
import edu.ccnt.mymall.util.PropertiesUtil;
import edu.ccnt.mymall.vo.ProductDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

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

    @Override
    public ServerResponse<ProductDetailVo> getDetail(Integer productId){
        log.info("获取商品详细信息");
        if(productId==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGLE_ARGUMENT.getCode(),ResponseCode.ILLEGLE_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("商品不存在或已下架");
        }
        return ServerResponse.createBySuccess(assembleProductDetailVo(product));
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setMainImage(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setDetail(product.getDetail());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category.getParentId() == null){
            productDetailVo.setParentCategoryId(0);
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        return productDetailVo;
    }
}
