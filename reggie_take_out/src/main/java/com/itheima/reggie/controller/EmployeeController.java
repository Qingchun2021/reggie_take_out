package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author xukai
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        /**
         * 用户名为空，则返回失败
         * 先判断用户名是否存在，不存在则返回失败
         * 判断密码是否一致，不一致则返回失败
         * 判断员工状态是否禁用，禁用则返回失败
         * 返回成功信息
         */

        if(StringUtils.isEmpty(employee.getUsername())){
            return R.error("用户名不能为空");
        }
        //将页面提交的代码进行md5加密处理
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(ObjectUtils.isEmpty(emp)){
            return R.error("登录失败!");
        }
        if(!password.equals(emp.getPassword())){
            return R.error("登录失败!");
        }
        if(emp.getStatus() == 0){
            return R.error("您已被禁用!");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);

    }

}
