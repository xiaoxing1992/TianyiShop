package tianyishop.weiwei.com.tianyishop.util;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/24.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zhangdake on 2017/4/10.
 * 主要用于保存全选 反选状态 以及其它可能的用途
 */
public class Select_Tools_Single<T> implements Select_Tools<T> {

    /**
     * 保存选择状态的map
     */
    private HashMap<T, Boolean> map;

    public Select_Tools_Single() {
        map = new HashMap<T, Boolean>();
    }

    //get方法
    public HashMap<T, Boolean> getMap() {
        return map;
    }

    //set方法
    public void setMap(HashMap<T, Boolean> map) {
        if (map == null) {
            throw new NullPointerException();
        }
        this.map = map;
    }

    /**
     * 条目是否已经全部选中
     *
     * @return true已全部选中 false未全部选中
     */
    @Override
    public boolean isSelectAll() {
        //map无数据则直接返回false
        if (map.isEmpty()) {
            return false;
        }

        Set<Map.Entry<T, Boolean>> set = map.entrySet();

        //使用迭代器
        Iterator<Map.Entry<T, Boolean>> iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry<T, Boolean> entry = iterator.next();
            //有一个false 即返回false
            if (!entry.getValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 反选
     */
    @Override
    public void invertSelect() {

        Set<Map.Entry<T, Boolean>> set = map.entrySet();

        //把value设置成相反的值
        for (Map.Entry<T, Boolean> m : set) {
            m.setValue(!m.getValue());
        }
    }

    /**
     * 全选
     */
    @Override
    public void selectAll() {
        set_all_value(true);
    }

    /**
     * 全不选
     */
    @Override
    public void unselect() {
        set_all_value(false);
    }

    /**
     * @param flag 将map中的value全部设置成该值
     */
    @Override
    public void set_all_value(boolean flag) {

        Set<Map.Entry<T, Boolean>> set = map.entrySet();

        for (Map.Entry<T, Boolean> m : set) {
            m.setValue(flag);
        }

    }

    /**
     * 获取相应条目的选中状态
     *
     * @param t 泛型 那一条数据的选中状态
     * @return boolean 条目的选中状态
     */
    @Override
    public boolean getState(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return map.get(t);
    }

    /**
     * 向map中添加一个元素
     *
     * @param t    泛型
     * @param flag 添加时的value值
     */
    @Override
    public void putItem(T t, boolean flag) {
        if (t == null) {
            throw new NullPointerException();
        }
        map.put(t, flag);
    }

    /**
     * 向map中添加多条数据
     *
     * @param list 需要添加的list数据集合
     * @param flag 默认的选中状态
     */
    @Override
    public void putItems(List<T> list, boolean flag) {
        if (list == null) {
            throw new NullPointerException();
        }
        for (T t : list) {
            map.put(t, flag);
        }
    }

    /**
     * 改变一个数据的选中状态 并检查map中所有的value是否都为true
     *
     * @param t 需要改变状态的数据
     * @return 添加后map中的value值是否都为true 是返回true 否返回false
     */
    @Override
    public boolean clickItem(T t) {

        //获取当前的value值
        boolean flag = getState(t);

        putItem(t, !flag);
        if (flag) {
            return !flag;
        }
        return isSelectAll();
    }

    /**
     * 清空map中的所有信息
     */
    @Override
    public void clearData() {
        map.clear();
    }

}

