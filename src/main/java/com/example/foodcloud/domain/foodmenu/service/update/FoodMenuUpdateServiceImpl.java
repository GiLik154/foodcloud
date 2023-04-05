package com.example.foodcloud.domain.foodmenu.service.update;

import com.example.foodcloud.domain.foodmenu.domain.FoodMenu;
import com.example.foodcloud.domain.foodmenu.domain.FoodMenuRepository;
import com.example.foodcloud.domain.foodmenu.service.image.ImageUploadService;
import com.example.foodcloud.domain.foodmenu.service.update.dto.FoodMenuUpdateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodMenuUpdateServiceImpl implements FoodMenuUpdateService {
    private final ImageUploadService imageUploadService;
    private final FoodMenuRepository foodMenuRepository;

    /**
     * 음식 메뉴의 id를 사용해 해당 음식 메뉴가 존재하는지 확인한다.
     * 음식메뉴가 존재하고, 이미지 파일이 존재한다면 파일을 저장하고, FoodMenu를 DB에 저장한다.
     * foodMenuUpdateServiceDto로부터 받은 정보로 FoodMenu를 업데이트 한다.
     *
     * @param foodMenuId               식당 id
     * @param foodMenuUpdateServiceDto 추가할 음식 메뉴 정보
     * @param file                     이미지 파일
     */
    @Override
    public void update(Long foodMenuId, FoodMenuUpdateServiceDto foodMenuUpdateServiceDto, File file) {
        Optional<FoodMenu> foodMenuOptional = foodMenuRepository.findById(foodMenuId);

        foodMenuOptional.ifPresent(foodMenu -> {
            if (file != null) {
                foodMenu.uploadImage(
                        imageUploadService.saveFileAndReturnFilePath(foodMenu.getRestaurant().getName(), file)
                );
            }

            foodMenu.update(foodMenuUpdateServiceDto.getName(),
                    foodMenuUpdateServiceDto.getPrice(),
                    foodMenuUpdateServiceDto.getTemperature(),
                    foodMenuUpdateServiceDto.getFoodTypes(),
                    foodMenuUpdateServiceDto.getMeatType(),
                    foodMenuUpdateServiceDto.getVegetables()
            );
        });
    }
}
