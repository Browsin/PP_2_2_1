package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      Car bmw = new Car("BMW", 5);
      String model = "BMW";
      int series = 5;

      userService.add(new User("User1", "Lastname1", "user1@mail.ru",
              bmw));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru",
              bmw));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru",
              new Car("AUDI", 80)));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru",
              new Car("Mercedes-Benz", 63)));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         System.out.println("Car: " +  user.getCar());
         System.out.println();
      }
      List<User> userByCar = userService.carByUsers(model, series);
      System.out.println("Список пользователей, владеющих " + model + " " + series + ":");
      if (userByCar != null) {
         for (User user : userByCar) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
         }
      } else {
         System.out.println("Пользователей с таким автомобилем или таких автомобилей не существует");
      }
      context.close();
   }
}
