package com.jumaojiang.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jumaojiang.mapper.AdminroleMapper;
import com.jumaojiang.mapper.AdminsMapper;
import com.jumaojiang.pojo.Adminrole;
import com.jumaojiang.pojo.Admins;
import com.jumaojiang.pojo.AdminsExample;
import com.jumaojiang.utils.FileUtil;
import com.jumaojiang.vo.AdminsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * ssm_api
 *
 * @author wuhanwei
 * @version 1.0
 * @date 2021/10/24
 */
@Service
public class AdminService {

    @Autowired
    private AdminsMapper adminsMapper;

    @Autowired
    private AdminroleMapper adminroleMapper;

    /**
     * 添加数据
     * @param admins
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer add(Admins admins){
        return adminsMapper.insertSelective(admins);
    }

    /**
     * 根据主键查询Admins
     * @param adminsId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Admins queryByAdminsId(Integer adminsId){
        return adminsMapper.selectByPrimaryKey(adminsId);
    }

    /**
     * 根据主键更新Admins
     * @param admins
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Integer updateByAdminsId(Admins admins){
        return adminsMapper.updateByPrimaryKeySelective(admins);
    }

    /**
     * 多条件分页查询
     * @param pageNum
     * @param pageSize
     * @param adminsQueryVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageInfo selectAdmins(Integer pageNum, Integer pageSize, AdminsQueryVo adminsQueryVo){
        // 创建多条件对象
        AdminsExample adminsExample = new AdminsExample();
        AdminsExample.Criteria criteria = adminsExample.createCriteria();
        // 添加查询多条件
        if(adminsQueryVo.getLoginName()!=null && !"".equals(adminsQueryVo.getLoginName())){
            criteria.andLoginNameLike("%" + adminsQueryVo.getLoginName() + "%");
        }
        if(adminsQueryVo.getPetName()!=null && !"".equals(adminsQueryVo.getPetName())){
            criteria.andPetNameLike("%" + adminsQueryVo.getPetName() + "%");
        }
        if(adminsQueryVo.getPhone()!=null && !"".equals(adminsQueryVo.getPhone())){
            criteria.andPhoneLike("%" + adminsQueryVo.getPhone() + "%");
        }
        if(adminsQueryVo.getRoleId() != null && adminsQueryVo.getRoleId()!=-1){
            criteria.andRoleIdEqualTo(adminsQueryVo.getRoleId());
        }
        // 分页配置
        PageHelper.startPage(pageNum, pageSize);
        List<Admins> admins = adminsMapper.selectByExample(adminsExample);
        for (Admins admin : admins) {
            Adminrole adminrole = adminroleMapper.selectByPrimaryKey(admin.getRoleId());
            admin.setAdminrole(adminrole);
        }
        return new PageInfo(admins);
    }

    /**
     * 根据主键逻辑删除
     * @param adminId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer deleteById(Integer adminId){
        Admins admins = new Admins();
        admins.setAdminId(adminId);
        admins.setIsDel(1);
        return adminsMapper.updateByPrimaryKeySelective(admins);
    }

    /**
     * 接收上传文件
     * @param fileSavePath
     * @param file
     * @return
     */
    public String uploadHeadImg(String fileSavePath, MultipartFile file){
        // 生成文件名
        String fileRename = FileUtil.fileRename(file);
        // 创建存储路径,存储文件
        File fileDir = new File(fileSavePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        try {
            file.transferTo(new File(fileDir, fileRename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileRename;
    }
}









