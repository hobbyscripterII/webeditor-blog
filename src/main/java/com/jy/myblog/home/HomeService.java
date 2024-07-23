package com.jy.myblog.home;

import com.jy.myblog.home.model.HomePostGetVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final HomeMapper mapper;

    List<HomePostGetVo> getPost() {
        return mapper.getPost();
    }
}
