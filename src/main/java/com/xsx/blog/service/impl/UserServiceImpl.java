package com.xsx.blog.service.impl;

import com.xsx.blog.common.Constants;
import com.xsx.blog.dto.UserInfo;
import com.xsx.blog.mapper.UsersMapper;
import com.xsx.blog.model.Users;
import com.xsx.blog.request.LoginRequest;
import com.xsx.blog.result.Result;
import com.xsx.blog.service.IMailService;
import com.xsx.blog.service.UserService;
import com.xsx.blog.util.MD5Utils;
import com.xsx.blog.util.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IMailService iMailService;


    @Override
    public int insert(Users users) {
        users.setPassword(MD5Utils.encoderByMd5(users.getUserName()+users.getPassword()));
        return usersMapper.insert(users);
    }

    @Override
    public int update(Users users) {
        return usersMapper.updateByPrimaryKeySelective(users);
    }

    @Override
    public Result<UserInfo> login(String loginType, LoginRequest loginRequest) {
        Result<UserInfo> result = new Result<UserInfo>();
        if(StringUtils.isEmpty(loginType)){
            result.setSuccess(false);
            result.setMsg("参数为空");
            return result;
        }
        Users users = null;
        if(Constants.SyetemSetting_Login.USER_PASS_VAIL.getValue().equalsIgnoreCase(loginType)){//用户名密码登录方式
            users = usersMapper.login(loginRequest.getUsername(),MD5Utils.encoderByMd5(loginRequest.getUsername()+loginRequest.getPassword()));
            if(users == null){
                result.setSuccess(false);
                result.setMsg("用户名密码错误");
                return result;
            }
        }else if(Constants.SyetemSetting_Login.MEAIL_VAIL.getValue().equalsIgnoreCase(loginType)){ //邮箱校验
            users = usersMapper.selectByEmail(loginRequest.getEmail());
            if(users == null){
                result.setSuccess(false);
                result.setMsg("邮箱有误");
                return result;
            }
            Object code = redisUtils.get(Constants.SNED_CODE_REDIS_FLAG);
            if(null == code || (code != null && !loginRequest.getCode().equals(code.toString()))){
                result.setSuccess(false);
                result.setMsg("验证码有误");
                return result;
            }
            redisUtils.del(Constants.SNED_CODE_REDIS_FLAG);
        }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(users,userInfo);
        result.setSuccess(true);
        result.setMsg("登录成功");
        result.setObject(userInfo);
        return result;
    }

    @Override
    public void sendCode(String email,Result result) {

        try{
            Users users = usersMapper.selectByEmail(email);
            if(users == null){
                result.setSuccess(false);
                result.setMsg("邮箱有误");
            }else{
                String code = com.xsx.blog.util.StringUtils.getCode();
                String content = Constants.SNED_MAIL_LOGIN_CONTENT.replace("code", code);
                iMailService.sendSimpleMail(email,Constants.SEND_MAIL_LOGIN_TITLE,content);
                redisUtils.set(Constants.SNED_CODE_REDIS_FLAG,code,1 * 60l);
                result.setSuccess(true);
                result.setMsg("发送成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMsg("发送异常");
        }
    }
}
