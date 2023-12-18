package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;


   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      return sessionFactory.getCurrentSession().createQuery("FROM User").getResultList();//todo: рефакторинг
   }

   @Override
   public List<User> carByUsers(String model, int series) {//todo: переходим на ссылочный тип, избавляемся от примитивов
      Session session = sessionFactory.getCurrentSession();
      Car car = session.createQuery("FROM Car WHERE model = :inputModel AND series = :inputSeries", Car.class)//todo: hql-запросы не выносятся (не видел), но выносятся SQL
              .setParameter("inputModel", model)
              .setParameter("inputSeries", series)
              .uniqueResult();
      if (car == null) {
         return null;
      } else {
         return session.createQuery("FROM User WHERE car_id = :inputCarsId", User.class)
                 .setParameter("inputCarsId", car.getId())
                 .getResultList();
      }
   }

}
