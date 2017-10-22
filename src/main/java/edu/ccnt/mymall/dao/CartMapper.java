package edu.ccnt.mymall.dao;

import edu.ccnt.mymall.model.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbg.generated Wed Aug 02 21:00:02 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbg.generated Wed Aug 02 21:00:02 CST 2017
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbg.generated Wed Aug 02 21:00:02 CST 2017
     */
    int insertSelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbg.generated Wed Aug 02 21:00:02 CST 2017
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbg.generated Wed Aug 02 21:00:02 CST 2017
     */
    int updateByPrimaryKeySelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mmall_cart
     *
     * @mbg.generated Wed Aug 02 21:00:02 CST 2017
     */
    int updateByPrimaryKey(Cart record);

    //根据用户id和商品id查找购物车对象
    Cart findCartByUserIdAndProductId(@Param(value = "userId") Integer userId, @Param(value = "productId") Integer productId);


    List<Cart> findCartByUserId(Integer userId);

    int isAllChecked(Integer userId);

    void deleteCartByProducts(@Param(value = "userId") Integer userId,@Param(value = "productList") List<String> productList);

    int selectOrUnSelect(@Param(value = "userId") Integer userId,@Param(value = "check")Integer check, @Param(value = "productId") Integer productId);

    List<Cart> selectByUserIdAndChecked(Integer userId);
}