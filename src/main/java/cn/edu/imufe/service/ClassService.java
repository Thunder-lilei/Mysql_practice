package cn.edu.imufe.service;

import cn.edu.imufe.po.TblClass;
import cn.edu.imufe.pojo.ClassBaseInfoPojo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <h3>MysqlPractice</h3>
 * <p>班级接口</p>
 *
 * @author : 李雷
 * @date : 2020-12-17 08:58
 **/
public interface ClassService {
    TblClass selectByClassName(String className);

    Boolean selectByClassNameWithoutId(TblClass c);

    List<ClassBaseInfoPojo> getAllClassBaseInfo();

    PageInfo<?> getAllClassBaseInfo(int page, int pageSize);

    TblClass getClass(Long id);

    Integer addClass(TblClass c);

    Integer updateClass(TblClass c);

    Integer deleteClass(Long id);
}
