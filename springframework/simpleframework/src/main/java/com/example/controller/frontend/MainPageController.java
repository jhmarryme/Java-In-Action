package com.example.controller.frontend;

import com.example.entity.dto.MainPageInfoDTO;
import com.example.entity.dto.Result;
import com.example.service.combine.HeadLineShopCategoryCombineService;
import lombok.Getter;
import org.springframework.core.annotation.Controller;
import org.springframework.inject.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainPageController {

    @Autowired("HeadLineShopCategoryCombineServiceImpl")
    @Getter
    private HeadLineShopCategoryCombineService headLineShopCategoryCombineService;

    public Result<MainPageInfoDTO> getMainPageInfo(HttpServletRequest req, HttpServletResponse resp){
        return headLineShopCategoryCombineService.getMainPageInfo();
    }

}