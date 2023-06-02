package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;

/**
 * @author xukai
 */
public interface DishService extends IService<Dish> {

    /**
     * 插入菜品信息的同时也需要插入口味表
     * @param dishDto 口味
     */
    public void saveWithFlavor(DishDto dishDto);

}
