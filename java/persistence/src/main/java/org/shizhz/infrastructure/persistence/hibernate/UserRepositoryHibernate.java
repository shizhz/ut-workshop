package org.shizhz.infrastructure.persistence.hibernate;

import org.shizhz.domain.User;
import org.shizhz.domain.UserRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zzshi on 8/31/14.
 */
@Repository
public class UserRepositoryHibernate extends HibernateRepository implements UserRepository {
    @Override
    public void save(User user) {
        currentSession().save(user);
    }

    @Override
    public User find(Long userId) {
        return (User) currentSession().get(User.class, userId);
    }

    @Override
    public void update(User user) {
        currentSession().update(user);
    }

    @Override
    public void remove(User user) {
        currentSession().delete(find(user.getId()));
    }
}
