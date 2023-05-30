package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Employee;

/**
 * @author xukai
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据id删除分类
     * @param id
     */
    public void remove(Long id);

}
