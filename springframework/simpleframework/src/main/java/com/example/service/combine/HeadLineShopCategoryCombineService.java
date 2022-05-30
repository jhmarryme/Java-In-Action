package com.example.service.combine;


import com.example.entity.dto.MainPageInfoDTO;
import com.example.entity.dto.Result;

public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo();
}
