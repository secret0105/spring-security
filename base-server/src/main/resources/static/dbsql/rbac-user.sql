#查询用户基本信息
select username, password, enabled
from sys_user u
where u.username = "admin";

#查询用户角色信息
select role_code
from sys_role r
         left join sys_user_role ur on r.id = ur.role_id
         left join sys_user u on u.id = ur.user_id
where u.username = "user";

#查询带单访问权限
select distinct url
from sys_menu m
         left join sys_role_menu srm on m.id = srm.menu_id
         left join sys_role sr on sr.id = srm.role_id
where sr.role_code in ("admin", "user");

select url
from sys_menu m
         left join sys_role_menu rm on m.id = rm.menu_id
         left join sys_role r on r.id = rm.role_id
where r.role_code in ("admin");