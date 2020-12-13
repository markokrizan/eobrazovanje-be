package rs.ac.uns.ftn.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import rs.ac.uns.ftn.education.repository.BaseRepositoryImpl;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
	EducationApplication.class,
	Jsr310JpaConverters.class
})
@EnableJpaRepositories (repositoryBaseClass = BaseRepositoryImpl.class)
public class EducationApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(EducationApplication.class, args);
	}
}
