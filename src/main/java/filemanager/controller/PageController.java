package filemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *@BelongsProject: FileManager
 *@BelongsPackage: filemanager.controller
 *@Author: liangxiaofei
 *@CreateTime: 2024-12-15  14:59
 *@Description: TODO 页面跳转控制器
 *@Version: 1.0
 */
@Controller
@CrossOrigin
public class PageController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/view")
    public String view() {
        return "pages/view/view";
    }

    @GetMapping("/appManager")
    public String appManager() {
        return "pages/appManager/appManager";
    }

    @GetMapping("/appFile")
    public String appFile() {
        return "pages/appManager/appFile";
    }

    @GetMapping("/panel")
    public String panel() {
        return "pages/panel/panel";
    }

}
