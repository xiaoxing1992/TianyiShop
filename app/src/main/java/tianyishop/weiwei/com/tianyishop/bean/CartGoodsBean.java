package tianyishop.weiwei.com.tianyishop.bean;

/**
 * @类的用途: 购物车商品类
 * @作者: 任正威
 * @date: 2017/4/23.
 */

public class CartGoodsBean {
    public String goods_name;
    public float shop_price;
    public boolean is_coupon_allowed;
    public String goods_img;
    public String id;
    public int number = 1;
    public int restrict;

    /**
     * 是否处于编辑状态
     */
    public boolean isEditing;
    /**
     * 是否被选中
     */
    public boolean isChildSelected;

    @Override
    public String toString() {
        return "CartGoodsBean{" +
                "goods_name='" + goods_name + '\'' +
                ", shop_price=" + shop_price +
                ", is_coupon_allowed=" + is_coupon_allowed +
                ", goods_img='" + goods_img + '\'' +
                ", id='" + id + '\'' +
                ", number=" + number +
                ", isEditing=" + isEditing +
                ", isChildSelected=" + isChildSelected +
                '}';
    }


}
