package com.kusitms.pabloair;

import com.kusitms.pabloair.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PabloAirApplicationTests {


	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Test
	void contextLoads() {

		boolean b = jwtTokenProvider.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0Iiwicm9sZXMiOiJVU0VSIiwiaWF0IjoxNjYzMzAzNzkwLCJleHAiOjE2NjQ1MTMzOTB9.qe5JVPA9g3aVVbwZfidwPRDcrpGTaOkDNGLvJPGIMETP-Qtb_H-1efMIETQxcClmtcLoXeBMEY1S4uSuPVHIUA");
		System.out.println(b);
	}


}
