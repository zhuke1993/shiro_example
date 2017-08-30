package com.zhuke.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service
 * <p>
 * Created by ZHUKE on 2017/8/28.
 */
@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> queryPassword(String userName) {
        String queryPwdSql = "select password from user_info where name = '" + userName + "'";
        return jdbcTemplate.queryForList(queryPwdSql, String.class);
    }

    public List<String> queryRoles(String userName) {
        String queryRolesSql = "select roleName from user_role where userName = '" + userName + "'";
        List<String> roleNames = jdbcTemplate.queryForList(queryRolesSql, String.class);

        return CollectionUtils.isEmpty(roleNames) ? null : roleNames;
    }

    public List<String> queryPermission(String roleName) {
        String queryPermissionSql = "select permission from roles_permissions where roleName= '" + roleName + "'";
        List<String> permissions = jdbcTemplate.queryForList(queryPermissionSql, String.class);
        return CollectionUtils.isEmpty(permissions) ? null : permissions;
    }
}
