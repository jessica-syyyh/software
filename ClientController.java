package com.ruoyi.my.controller;

import java.util.List;

import com.ruoyi.my.domain.Client;
import com.ruoyi.my.service.IClientService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员管理（测试）Controller
 * 
 * @author jjq
 * @date 2021-01-05
 */
@Controller
@RequestMapping("/system/client")
public class ClientController extends BaseController
{
    private String prefix = "system/client";

    @Autowired
    private IClientService clientService;

    @RequiresPermissions("system:client:view")
    @GetMapping()
    public String client()
    {
        return prefix + "/client";
    }

    /**
     * 查询会员管理（测试）列表
     */
    @RequiresPermissions("system:client:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Client client)
    {
        startPage();
        List<Client> list = clientService.selectClientList(client);
        return getDataTable(list);
    }

    /**
     * 导出会员管理（测试）列表
     */
    @RequiresPermissions("system:client:export")
    @Log(title = "会员管理（测试）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Client client)
    {
        List<Client> list = clientService.selectClientList(client);
        ExcelUtil<Client> util = new ExcelUtil<Client>(Client.class);
        return util.exportExcel(list, "client");
    }

    /**
     * 新增会员管理（测试）
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存会员管理（测试）
     */
    @RequiresPermissions("system:client:add")
    @Log(title = "会员管理（测试）", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Client client)
    {
        return toAjax(clientService.insertClient(client));
    }

    /**
     * 修改会员管理（测试）
     */
    @GetMapping("/edit/{clientName}")
    public String edit(@PathVariable("clientName") String clientName, ModelMap mmap)
    {
        Client client = clientService.selectClientById(clientName);
        mmap.put("client", client);
        return prefix + "/edit";
    }

    /**
     * 修改保存会员管理（测试）
     */
    @RequiresPermissions("system:client:edit")
    @Log(title = "会员管理（测试）", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Client client)
    {
        return toAjax(clientService.updateClient(client));
    }

    /**
     * 删除会员管理（测试）
     */
    @RequiresPermissions("system:client:remove")
    @Log(title = "会员管理（测试）", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(clientService.deleteClientByIds(ids));
    }
}
