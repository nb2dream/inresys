package net.chenlin.dp.manage.material.service.impl;

import net.chenlin.dp.common.constant.MaterialTypeConstant;
import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.R;
import net.chenlin.dp.common.utils.CommonUtils;
import net.chenlin.dp.common.utils.FileUtils;
import net.chenlin.dp.common.utils.JSONUtils;
import net.chenlin.dp.common.utils.MD5Utils;
import net.chenlin.dp.manage.material.dao.ManaMaterialMapper;
import net.chenlin.dp.manage.material.dto.ManaMaterialDTO;
import net.chenlin.dp.manage.material.manager.ManaMaterialManager;
import net.chenlin.dp.manage.material.service.ManaMaterialService;
import net.chenlin.dp.manage.program.dto.ManaProgramMaterialDTO;
import net.chenlin.dp.manage.program.manager.ManaProgramMaterialManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther ruanjunxu
 * @email ruanjunxu@qq.com
 * @create 2017/10/20 15:40
 */
@Service("ManaMaterialService")
public class ManaMaterialServiceImpl implements ManaMaterialService {

    @Autowired
    private ManaMaterialManager manaMaterialManager;

    @Autowired
    private ManaProgramMaterialManager manaProgramMaterialManager;

    @Value("${material.savePath}")
    private String materialSavePath;

    @Override
    public Page<ManaMaterialDTO> listMaterialByPage(Map<String, Object> params) {
        Query query = new Query(params);
        Page<ManaMaterialDTO> page = new Page<>(query);
        manaMaterialManager.listForPage(page, query);
        return page;
    }

    @Override
    public Page<ManaMaterialDTO> listSelectMaterialByPage(Map<String, Object> params) {
        Query query = new Query(params);
        Page<ManaMaterialDTO> page = new Page<>(query);
        manaMaterialManager.listSelectMaterialByPage(page, query);
        return page;
    }

    @Override
    public R batchRemove(Long[] ids) {
        for (Long id : ids) {
            List<ManaProgramMaterialDTO> list = manaProgramMaterialManager.listProgramMaterialByMaterialId(id);
            if (!CommonUtils.isListNullOrZeroEmpty(list)) {
                StringBuilder  errMsg = new StringBuilder ("删除失败!以下节目正在使用素材(编号:" + id + ")导致无法删除。在用该素材节目编号:");
                for (ManaProgramMaterialDTO bean : list) {
                    errMsg.append(bean.getProgramId() + ",");
                }
                return R.error(errMsg.toString());
            }

        }
        for (Long id : ids) {
            ManaMaterialDTO bean = manaMaterialManager.getMaterialById(id);
            FileUtils.deleteFile(materialSavePath, "/" + bean.getPath());
        }
        int count = manaMaterialManager.batchRemove(ids);
        return CommonUtils.msg(count);
    }

    @Override
    public R updateMaterial(ManaMaterialDTO manaMaterialDTO) {
        Integer type = manaMaterialDTO.getType();
        if (type == MaterialTypeConstant.MATERIAL_TYPE_TEXT || type == MaterialTypeConstant.MATERIAL_TYPE_URL) {
            if (manaMaterialDTO.getFontSize() != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", manaMaterialDTO.getContent());
                map.put("fontSize", manaMaterialDTO.getFontSize());
                manaMaterialDTO.setContent(JSONUtils.beanToJson(map).toString());
            }
        }
        int count = manaMaterialManager.updateMaterial(manaMaterialDTO);
        return CommonUtils.msg(count);
    }

    @Override
    public R saveMaterial(ManaMaterialDTO manaMaterialDTO) {
        MultipartFile[] files = manaMaterialDTO.getFiles();
        // 素材类型 1:图片，2:视频，3:音频，4:文字,5:网址
        Integer type = manaMaterialDTO.getType();
        if (CommonUtils.isNullOrEmpty(type)) {
            return R.error(MsgConstant.MSG_PARAM_ERROR); // 参数错误
        }
        if (type == MaterialTypeConstant.MATERIAL_TYPE_TEXT || type == MaterialTypeConstant.MATERIAL_TYPE_URL) {
            if (manaMaterialDTO.getFontSize() != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("content", manaMaterialDTO.getContent());
                map.put("fontSize", manaMaterialDTO.getFontSize());
                manaMaterialDTO.setContent(JSONUtils.beanToJson(map).toString());
            }
            manaMaterialManager.saveMaterial(manaMaterialDTO);
        } else {
            if (CommonUtils.isNullOrEmpty(files)) {
                return R.error(MsgConstant.MSG_PARAM_ERROR); // 参数错误
            }
            String fileName = null;
            String newFileName = null;
            for (int i = 0; i < files.length; i++) {
                fileName = files[i].getOriginalFilename();
                String suffix = fileName.substring(fileName.lastIndexOf(".")+1); // 文件后缀

                manaMaterialDTO.setMaterialName(fileName);      // 文件原始名称
                manaMaterialDTO.setSuffix(suffix);              // 文件后缀
                // 文件字节大小转成 M 大小
                manaMaterialDTO.setFileSize(new BigDecimal(files[i].getSize()/1024.0/1024.0).setScale(2,BigDecimal.ROUND_HALF_UP).toString()); // 文件大小
                // manaMaterialDTO.setContent();
                try {
                    String fileMD5Code = MD5Utils.getFileMD5Code(FileUtils.multipartToFile(files[i]));
                    manaMaterialDTO.setContent(fileMD5Code);
                } catch (Exception e) {

                }
                try {
                    if (type ==  MaterialTypeConstant.MATERIAL_TYPE_IMAGE && CommonUtils.isImage(suffix)) {
                        newFileName = FileUtils.saveFile(materialSavePath, suffix, files[i]);
                        manaMaterialDTO.setPath(newFileName);
                    } else if (type == MaterialTypeConstant.MATERIAL_TYPE_VIDEO && CommonUtils.isVideo(suffix)) {
                        newFileName = FileUtils.saveFile(materialSavePath, suffix, files[i]);
                        manaMaterialDTO.setPath(newFileName);
                    } else if (type == MaterialTypeConstant.MATERIAL_TYPE_AUDIO && CommonUtils.isAudio(suffix)) {
                        newFileName = FileUtils.saveFile(materialSavePath, suffix, files[i]);
                        manaMaterialDTO.setPath(newFileName);
                    } else {
                        return R.error(MsgConstant.MSG_FILE_TYPE_ERROR); // 文件类型错误
                    }
                    manaMaterialManager.saveMaterial(manaMaterialDTO);
                } catch (Exception e) {
                    //TODO 打上详细出错原因
                }
            }
        }

        return CommonUtils.msg(1);
    }

    @Override
    public R getMaterialById(Long id) {
        return CommonUtils.msg(manaMaterialManager.getMaterialById(id));
    }
}
