package tianyishop.weiwei.com.tianyishop.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tianyishop.weiwei.com.tianyishop.bean.CartGoodsBean;
import tianyishop.weiwei.com.tianyishop.util.CacheUtils;

/**
 * @类的用途:购物车储存类
 * @作者: 任正威
 * @date: 2017/4/23.
 */

public class CartProvider {
    private static final String JSON_CART = "json_cart";
    public static CartProvider cartProvider;
    private final Context context;
    private SparseArray<CartGoodsBean> sparseArray;
    private List<CartGoodsBean> dataFromLocal;

    public CartProvider(Context context) {
        this.context = context;
        sparseArray = new SparseArray<>(100);
        //把存储的数据读取出来
        listToSparse();
    }

    private void listToSparse() {
        List<CartGoodsBean> cartGoodsBeanList = getAllData();
        for (int i = 0; i < cartGoodsBeanList.size(); i++) {
            CartGoodsBean cartGoodsBean = cartGoodsBeanList.get(i);
            sparseArray.put(Integer.parseInt(cartGoodsBean.id), cartGoodsBean);
        }

    }

    //
    public List<CartGoodsBean> getAllData() {

        return getDataFromLocal();
    }

    public static CartProvider getInstance() {
        if (cartProvider == null) {
            cartProvider = new CartProvider(MyApplication.getContext());
        }
        return cartProvider;
    }


    public List<CartGoodsBean> getDataFromLocal() {
        List<CartGoodsBean> carts = new ArrayList<>();
        String saveJson = CacheUtils.getString(context, JSON_CART);
        if (!TextUtils.isEmpty(saveJson)) {
            carts = new Gson().fromJson(saveJson, new TypeToken<List<CartGoodsBean>>() {
            }.getType());
        }
        return carts;
    }

    public void addData(CartGoodsBean cartGoodsBean) {
        //添加到内存中sparseArray
        CartGoodsBean tempGoodsBean = sparseArray.get(Integer.parseInt(cartGoodsBean.id));
        if (tempGoodsBean != null) {
            tempGoodsBean.number = tempGoodsBean.number + cartGoodsBean.number;
        } else {
            tempGoodsBean = cartGoodsBean;
        }
        sparseArray.put(Integer.parseInt(tempGoodsBean.id), tempGoodsBean);

        //同步到本地

        saveLocal();
    }


    public void deleteData(CartGoodsBean cartGoodsBean) {
        sparseArray.delete(Integer.parseInt(cartGoodsBean.id));
        saveLocal();
    }

    public void updataData(CartGoodsBean cartGoodsBean) {
        //内存中更新
        sparseArray.put(Integer.parseInt(cartGoodsBean.id), cartGoodsBean);
        //同步到本地
        saveLocal();
    }

    private void saveLocal() {
        //把内存中的数据转换成list列表形式
        List<CartGoodsBean> cartGoodsBeanList = sparsesToList();
        String toJson = new Gson().toJson(cartGoodsBeanList);
        CacheUtils.saveString(context, JSON_CART, toJson);

    }

    //把内存中的数据转换成list列表形式
    private List<CartGoodsBean> sparsesToList() {
        List<CartGoodsBean> carts = new ArrayList<>();
        if (sparseArray != null && sparseArray.size() > 0) {
            for (int i = 0; i < sparseArray.size(); i++) {
                CartGoodsBean cartGoodsBean = sparseArray.valueAt(i);
                carts.add(cartGoodsBean);
            }
        }
        return carts;
    }
}
