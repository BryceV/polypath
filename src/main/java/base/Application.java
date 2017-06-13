package base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

  // TODO: This should be stored in the DB
  public static final String CURRENT_YEAR_AND_TERM = "FALL2016";

  public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

  @Component
  public class ErrorPageConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
      registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
    }
  }
}
