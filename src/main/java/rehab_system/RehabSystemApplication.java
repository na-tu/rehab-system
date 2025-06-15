package rehab_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("rehab_system")
public class RehabSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(RehabSystemApplication.class, args);
	}
}

