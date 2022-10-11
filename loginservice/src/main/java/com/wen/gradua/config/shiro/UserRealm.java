package com.wen.gradua.config.shiro;


import com.wen.gradua.pojo.User;
import com.wen.gradua.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm  extends AuthorizingRealm {

    @Autowired
    UserService userService;
//    执行授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        info.addStringPermission("user.add");
        //拿到当前的登陆对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser =(User)subject.getPrincipal();
        //添加权限，perem为数据库中的权限
        info.addStringPermission(currentUser.getPerms());
        return info;
    }
//认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken userToken=(UsernamePasswordToken)token;
        String username = userToken.getUsername();
        //查询是否存在该用户名，查询出密码
        User user = userService.findByUsername(username);
        if (user == null){
            //支持邮箱登录，查询出username和密码
            user=userService.findUsernamePasswordByEmail(username);
            if (user == null){
                //如果是QQ邮箱，也支持不输入后缀
                String[] split = username.split("@");
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer.append(split[0]);
                stringBuffer.append("@qq.com");
                //支持邮箱登录，查询出username和密码
                user=userService.findUsernamePasswordByEmail(stringBuffer.toString());
                if (user == null) {
                    //账号不存在，邮箱也不存在，返回账号不存在异常
                    return null;
                }
            }
        }
        String password = user.getPassword();
        //使用账号作为盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        String realmName = getName();
        return new SimpleAuthenticationInfo(user,password,credentialsSalt,realmName);
    }
}
