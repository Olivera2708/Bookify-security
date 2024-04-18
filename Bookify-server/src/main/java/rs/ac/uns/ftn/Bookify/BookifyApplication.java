package rs.ac.uns.ftn.Bookify;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import rs.ac.uns.ftn.Bookify.repository.keystores.KeyStoreReader;
import rs.ac.uns.ftn.Bookify.repository.keystores.KeyStoreWriter;

@EnableScheduling
@SpringBootApplication
public class BookifyApplication {

	public static KeyStoreReader keyStoreReader;
	public static KeyStoreWriter keyStoreWriter;
	public static ApplicationContext context;

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		context = SpringApplication.run(BookifyApplication.class, args);

		keyStoreReader = (KeyStoreReader) context.getBean("keyStoreReader");
		keyStoreWriter = (KeyStoreWriter) context.getBean("keyStoreWriter");
	}
}
