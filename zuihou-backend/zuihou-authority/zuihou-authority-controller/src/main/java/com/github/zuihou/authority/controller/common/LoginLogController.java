package com.github.zuihou.authority.controller.common;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.zuihou.authority.dto.common.LoginLogSaveDTO;
import com.github.zuihou.authority.dto.common.LoginLogUpdateDTO;
import com.github.zuihou.authority.entity.common.LoginLog;
import com.github.zuihou.authority.service.common.LoginLogService;
import com.github.zuihou.base.BaseController;
import com.github.zuihou.base.R;
import com.github.zuihou.base.entity.SuperEntity;
import com.github.zuihou.database.mybatis.conditions.Wraps;
import com.github.zuihou.database.mybatis.conditions.query.LbqWrapper;
import com.github.zuihou.dozer.DozerUtils;
import com.github.zuihou.log.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 系统日志
 * </p>
 *
 * @author zuihou
 * @date 2019-10-20
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/loginLog")
@Api(value = "LoginLog", tags = "系统日志")
public class LoginLogController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private DozerUtils dozer;

    /**
     * 分页查询系统日志
     *
     * @param data 分页查询对象
     * @return 查询结果
     */
    @ApiOperation(value = "分页查询系统日志", notes = "分页查询系统日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页条数", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @GetMapping("/page")
    @SysLog("分页查询系统日志")
    public R<IPage<LoginLog>> page(LoginLog data) {
        IPage<LoginLog> page = getPage();
        // 构建值不为null的查询条件
        LbqWrapper<LoginLog> query = Wraps.lbQ(data);
        loginLogService.page(page, query);
        return success(page);
    }

    /**
     * 查询系统日志
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiOperation(value = "查询系统日志", notes = "查询系统日志")
    @GetMapping("/{id}")
    @SysLog("查询系统日志")
    public R<LoginLog> get(@PathVariable Long id) {
        return success(loginLogService.getById(id));
    }

    /**
     * 新增系统日志
     *
     * @param data 新增对象
     * @return 新增结果
     */
    @ApiOperation(value = "新增系统日志", notes = "新增系统日志不为空的字段")
    @PostMapping
    @SysLog("新增系统日志")
    public R<LoginLog> save(@RequestBody @Validated LoginLogSaveDTO data) {
        LoginLog loginLog = dozer.map(data, LoginLog.class);
        loginLogService.save(loginLog);
        return success(loginLog);
    }

    /**
     * 修改系统日志
     *
     * @param data 修改对象
     * @return 修改结果
     */
    @ApiOperation(value = "修改系统日志", notes = "修改系统日志不为空的字段")
    @PutMapping
    @SysLog("修改系统日志")
    public R<LoginLog> update(@RequestBody @Validated(SuperEntity.Update.class) LoginLogUpdateDTO data) {
        LoginLog loginLog = dozer.map(data, LoginLog.class);
        loginLogService.updateById(loginLog);
        return success(loginLog);
    }

    /**
     * 删除系统日志
     *
     * @param id 主键id
     * @return 删除结果
     */
    @ApiOperation(value = "删除系统日志", notes = "根据id物理删除系统日志")
    @DeleteMapping(value = "/{id}")
    @SysLog("删除系统日志")
    public R<Boolean> delete(@PathVariable Long id) {
        loginLogService.removeById(id);
        return success(true);
    }

}
