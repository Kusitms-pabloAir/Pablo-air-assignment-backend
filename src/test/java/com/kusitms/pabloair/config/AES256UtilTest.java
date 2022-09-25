package com.kusitms.pabloair.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AES256UtilTest {

    @Test
    @DisplayName("1234567890abcdefg를 암호화한 뒤 출력하고 다시 복호화해서 출력해보기")
    void 암호화_및_복호화() {
        AES256Util aes256Util = new AES256Util();
        String plainText = "1234567890abcdefg";
        String cipherText;
        String decryptedText;

        /* 암호화 */
        try {
            cipherText = aes256Util.encrypt(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /* 비문 출력 */
        System.out.println(cipherText);

        /* 복호화 */
        try {
            decryptedText = aes256Util.decrypt(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /* 복호화된 평문 출력 */
        System.out.println(decryptedText);

        /* 비교 */
        Assertions.assertEquals(plainText, decryptedText);
    }
}