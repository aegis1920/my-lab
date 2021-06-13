package things.justforfun;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class JustForFunTest {
	
	private static final String tableName = "";
	private static final String fieldNames = "";
	
	@Test
	void makeInsertSQL() throws IOException {
		File file = new File("small-things/src/test/java/things/justforfun/coupon.csv");
		Scanner myReader = new Scanner(file);
		List<String> couponCodes = new ArrayList<>();
		
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			couponCodes.add(data);
		}
		System.out.printf("INSERT INTO %s(%s) VALUES%n", tableName, fieldNames);
		String collect = couponCodes.stream()
				.map(str -> String.format("('%s', %s, %s, null, 'N', null)", str, "5", "52"))
				.collect(Collectors.joining(","));
		System.out.println(collect);
	}
}
