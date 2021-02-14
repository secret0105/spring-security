package com.xiaoshazhou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoshazhou.module.MyUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TODO
 *
 * @author zhoujin
 * @date 2021/2/14 17:36
 */
@Mapper
public interface MyUserDetailsServiceMapper{

    /**
     * 根据前端传过来的用户名来查询用户基本信息
     * @param username
     * @return
     */
    @Select("select username,password,enabled\n"+
            "from sys_user u where u.username = #{username}")
    MyUserDetails findByUsername(@Param("username") String username);

    /**
     * 根据前端传过来的用户名查询用户角色信息
     * @param username
     * @return
     */
    @Select("select role_code from sys_role r\n"+
            "left join sys_user_role ur on r.id = ur.role_id\n" +
            "left join sys_user u on u.id = ur.user_id\n" +
            "where u.username=#{username}")
    List<String> findRoleByUsername(@Param("username") String username);

    /**
     * 根据用户角色查询对应的用户权限
     *
     * @param roleCodes:角色码列表
     * @return
     */
    @Select({
            "<script>",
                "select url from sys_menu m",
                "left join sys_role_menu rm on m.id = rm.menu_id",
                "left join sys_role r on r.id = rm.role_id",
                "where r.role_code in",
                "<foreach collection='roleCodes' item='roleCode' open='(' separator=',' close = ')'>",
                    "#{roleCode}",
                "</foreach>",
            "</script>"
    })
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);
}
