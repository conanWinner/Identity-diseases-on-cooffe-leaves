package com.identify.leavescoffee;

import com.identify.leavescoffee.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.StringJoiner;

@SpringBootTest
@Slf4j
class LeavescoffeeApplicationTests {

	@Test
	void funny_test() {

		HashSet<String> role = new HashSet<>();
		role.add(Role.ADMIN.name());
		role.add(Role.USER.name());

		StringJoiner stringJoiner = new StringJoiner(" ");
		role.forEach(
				s -> stringJoiner.add(s)
		);

		log.warn(stringJoiner.toString());

	}

}
