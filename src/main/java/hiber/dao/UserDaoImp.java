package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;
   String hqlCar = "FROM Car " +
           "WHERE model = :inputModel " +
           "AND series = :inputSeries";
   String hqlUser = "FROM User " +
           "WHERE cars_id = :inputCarsId";

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> carByUsers(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      Car car = session.createQuery(hqlCar, Car.class)
              .setParameter("inputModel", model)
              .setParameter("inputSeries", series)
              .uniqueResult();
      if (car == null) {
         return null;
      } else {
         return session.createQuery(hqlUser, User.class)
                 .setParameter("inputCarsId", car.getId())
                 .getResultList();
      }
   }

}
