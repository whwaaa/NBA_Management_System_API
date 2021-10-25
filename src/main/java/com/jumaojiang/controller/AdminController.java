package com.jumaojiang.controller;

import com.github.pagehelper.PageInfo;
import com.jumaojiang.pojo.Admins;
import com.jumaojiang.service.AdminService;
import com.jumaojiang.vo.AdminsQueryVo;
import com.jumaojiang.vo.AjaxResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/24
 */
@Controller
@RequestMapping("admins")
public class AdminController {

    @Autowired
    private AdminService adminsService;

    // 接收文件上传
    @ResponseBody
    @RequestMapping(value = "headImg/{adminId}")
    public AjaxResultVo receiveFile(@PathVariable(value = "adminId", required = false) Integer adminId,
                                    @RequestParam("headImg") MultipartFile file, HttpServletRequest request){
        // 存储头像文件
        String fileSavePath = request.getServletContext().getRealPath("/img/uploadFile/");
        String fileName = adminsService.uploadHeadImg(fileSavePath, file);
        if(fileName == null){
            // 储存失败
            return new AjaxResultVo(500, "服务器内部异常，请稍后再试！");
        }
        // 更新数据库信息
        Admins admins = new Admins();
        admins.setAdminId(adminId);
        admins.setHeadImg(fileName);
        Integer res = adminsService.updateByAdminsId(admins);
        if(res > 0){
            return new AjaxResultVo(200, "ok", fileName, null);
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 添加admin
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public AjaxResultVo add(Admins admin){
        if(admin.getRoleId()==null || admin.getRoleId()==-1){
            admin.setRoleId(1);
        }
        if(admin.getStatus()==null || admin.getStatus()==-1){
            admin.setStatus(1);
        }
        Integer res = adminsService.add(admin);
        if(res > 0){
            return new AjaxResultVo();
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键更新
    @ResponseBody
    @RequestMapping(value = "{adminsId}", method = RequestMethod.PUT)
    public AjaxResultVo updateUser(@PathVariable("adminsId") Integer adminsId, Admins admins){
        admins.setAdminId(adminsId);
        if(admins.getRoleId()!=null && (admins.getRoleId().equals(0) || admins.getRoleId().equals(-1)))
            admins.setRoleId(null);
        Integer res = adminsService.updateByAdminsId(admins);
        if(res > 0){
            return new AjaxResultVo();
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键查询
    @ResponseBody
    @RequestMapping(value = "{adminsId}", method = RequestMethod.GET)
    public AjaxResultVo queryByUserId(@PathVariable("adminsId") Integer adminsId){
        Admins admins = adminsService.queryByAdminsId(adminsId);
        if(admins != null){
            return new AjaxResultVo(200, "ok", admins, null);
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 多条件分页查询
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public AjaxResultVo selectAdmins(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize,
                                     AdminsQueryVo adminsQueryVo){
        if(pageNum == null || pageNum < 0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = 5;
        }
        PageInfo pageInfo = adminsService.selectAdmins(pageNum, pageSize, adminsQueryVo);
        if(pageInfo != null){
            return new AjaxResultVo(200, "ok", null, pageInfo);
        }
        return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
    }

    // 根据主键删除Admin
    @ResponseBody
    @RequestMapping(value = "{adminsId}", method = RequestMethod.DELETE)
    public AjaxResultVo deleteById(@PathVariable("adminsId") String adminsId){
        String[] split = adminsId.split("_");
        for (String s : split) {
            Integer adminId = Integer.parseInt(s);
            Integer res = adminsService.deleteById(adminId);
            if(res == 0){
                return new AjaxResultVo(500, "服务器内部异常, 请稍后再试!");
            }
        }
        return new AjaxResultVo();
    }
}














