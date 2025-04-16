package filemanager.controller;

import org.springframework.web.bind.annotation.*;

/**
*@BelongsProject: FileManager
*@BelongsPackage: filemanager.controller
*@Author: liangxiaofei
*@CreateTime: 2024-12-14  23:58
*@Description: TODO 用户信息接口 如【登录、修改密码等】
*@Version: 1.0
*/
@CrossOrigin
@RestController
@RequestMapping("/userinfo")
public class UserController {

    @PostMapping("/login")
    public String login(@RequestBody String msg) {
        System.out.println("登录接口");
        return msg;
    }

}
