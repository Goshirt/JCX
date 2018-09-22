package com.helmet.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.helmet.entity.Menu;
import com.helmet.entity.Role;
import com.helmet.entity.User;
import com.helmet.repository.MenuRepository;
import com.helmet.repository.RoleRepository;
import com.helmet.repository.UserRepository;

/**
 * shiro自定义realm
 * 
 * @author Helmet
 * 2018年5月29日
 */
public class MyRealm extends AuthorizingRealm{
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private RoleRepository roleRepository;

	@Resource
	private MenuRepository menuRepository;
	
	/**
	 * 授权处理
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//该info用来进行角色权限验证，需要封装当前用户拥有的角色名的set,以及拥有的权限菜单名
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取当前用户名
		String userName=(String)SecurityUtils.getSubject().getPrincipal();
		//通过用户名获取当前的用户
		User currentUser=userRepository.getUserByUserName(userName);
		//通过当前用户的userId获取该用户的所有角色
		List<Role> roles=roleRepository.getRolesByUserId(currentUser.getUserId());
		//一个装有该用户所有角色名的set,shiro框架进行角色权限验证时使用
		Set<String> roleNames=new HashSet<>();
		for (Role role : roles) {
			roleNames.add(role.getRoleName());
			List<Menu> menus=menuRepository.getMenuByRoleId(role.getRoleId());
			for (Menu menu : menus) {
				//把该用户拥有的角色的权限菜单都添加到info中
				info.addStringPermission(menu.getMenuName());
			}
		}
		info.setRoles(roleNames);;
		return info;
	}

	/**
	 * 身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取登录时封装到token中的用户名
		String userName=(String) token.getPrincipal();
		User user=userRepository.getUserByUserName(userName);
		if (user!=null) {
			AuthenticationInfo info=new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "sss");
			return info;
		}else {
			return null;
		}
	}

}
