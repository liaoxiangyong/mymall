package edu.ccnt.mymall.controller.web;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.service.IProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    /**
     * 查询商品列表，根据关键字，排序
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping(value = "searchProducts.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("根据关键字、页数、类别等获取商品列表")
    public ServerResponse searchProducts(@RequestParam(value = "keyword",required = false)String keyword,
                                            @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                            @RequestParam(value = "orderBy",defaultValue = "",required = false) String orderBy){
        return iProductService.searchProducts(keyword,categoryId,pageNum,pageSize,orderBy);
    }


    /**
     * 根据id获取商品详细信息
     * @param productId
     * @return
     */
    @RequestMapping(value = "getDetail.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("根据商品id获取商品详细信息")
    public ServerResponse getDetail(Integer productId){
        return iProductService.getDetail(productId);
    }
}
