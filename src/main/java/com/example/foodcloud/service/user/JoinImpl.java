package com.example.foodcloud.service.user;

import com.example.foodcloud.entity.User;
import com.example.foodcloud.service.user.dto.JoinServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinImpl implements JoinService {
    private final User user;
    public boolean isJoin(JoinServiceDto joinServiceDto) {
            if(user.find)
        return false;
    }
}
