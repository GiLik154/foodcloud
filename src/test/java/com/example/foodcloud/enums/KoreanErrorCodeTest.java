package com.example.foodcloud.enums;

import com.example.foodcloud.exception.NotFoundErrorCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KoreanErrorCodeTest {

    @Test
    void getName() {
        String bankNotFoundName = KoreanErrorCode.BANK_NOT_FOUND.getName();
        String methodArgumentName = KoreanErrorCode.METHOD_ARGUMENT.getName();

        assertEquals("BANK_NOT_FOUND", bankNotFoundName);
        assertEquals("METHOD_ARGUMENT", methodArgumentName);
    }

    @Test
    void getResult() {
        String bankNotFoundName = KoreanErrorCode.BANK_NOT_FOUND.getResult();
        String methodArgumentName = KoreanErrorCode.METHOD_ARGUMENT.getResult();

        assertEquals("은행 정보를 확인해주세요.", bankNotFoundName);
        assertEquals("입력값 오류가 발생하였습니다.", methodArgumentName);
    }

    @Test
    void findByResult() {
        String bankNotFoundName = KoreanErrorCode.findByResult("BANK_NOT_FOUND");
        String methodArgumentName = KoreanErrorCode.findByResult("METHOD_ARGUMENT");

        assertEquals("은행 정보를 확인해주세요.", bankNotFoundName);
        assertEquals("입력값 오류가 발생하였습니다.", methodArgumentName);
    }

    @Test
    void findByResult_찾을_수_없음() {
        NotFoundErrorCodeException e = assertThrows(NotFoundErrorCodeException.class,
                () -> KoreanErrorCode.findByResult("TEST"));

        assertEquals("Not found ErrorCode", e.getMessage());
    }
}