package tianyishop.weiwei.com.tianyishop.util;

import java.util.List;

/**
 * @类的用途:
 * @作者: 任正威
 * @date: 2017/4/24.
 */

public interface Select_Tools<T> {

    /**
     * 条目是否已经全部选中
     *
     * @return true已全部选中 false未全部选中
     */
    boolean isSelectAll();

    /**
     * 反选
     */
    void invertSelect();

    /**
     * 全选
     */
    void selectAll();

    /**
     * 全不选
     */
    void unselect();

    /**
     * @param flag 将map中的value全部设置成该值
     */
    void set_all_value(boolean flag);

    /**
     * 获取相应条目的选中状态
     *
     * @param t 泛型 那一条数据的选中状态
     * @return boolean 条目的选中状态
     */
    boolean getState(T t);

    /**
     * 向map中添加一个元素
     *
     * @param t    泛型
     * @param flag 添加时的value值
     */
    void putItem(T t, boolean flag);

    /**
     * 向map中添加多条数据
     *
     * @param list 需要添加的list数据集合
     * @param flag 默认的选中状态
     */
    void putItems(List<T> list, boolean flag);

    /**
     * 改变一个数据的选中状态 并检查map中所有的value是否都为true
     *
     * @param t 需要改变状态的数据
     * @return 添加后map中的value值是否都为true 是返回true 否返回false
     */
    boolean clickItem(T t);

    /**
     * 清空所有数据
     */
    void clearData();

}