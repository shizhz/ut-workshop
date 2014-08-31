package org.shizhz.infrastructure.persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.shizhz.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testContext.xml")
@Transactional
public class UserRepositoryHibernateTest {
    @Autowired
    private SessionFactory sessionFactory;
    private Session currentSession;
    @Autowired
    private UserRepositoryHibernate userRepositoryHibernate;
    private User user;

    @Before
    public void setUp() throws Exception {
        currentSession = sessionFactory.getCurrentSession();
        user = getUser("test@email.com", "shizhz", "password");
    }

    private User getUser(String email, String username, String password) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

    @Test
    public void should_store_user_to_db() throws Exception {
        // given
        int userSize = currentSession.createQuery("from User").list().size();
        Long id = user.getId();

        // when
        userRepositoryHibernate.save(user);

        // then
        assertThat(id, is(nullValue()));
        assertThat(userSize, is(0));
        List<User> userList = currentSession.createCriteria(User.class).list();
        assertThat(userList.size(), is(1));

        User obtainedUser = userList.get(0);
        assertThat(obtainedUser.getEmail(), is(equalTo(user.getEmail())));
        assertThat(obtainedUser.getPassword(), is(equalTo(user.getPassword())));
        assertThat(obtainedUser.getUsername(), is(equalTo(user.getUsername())));
    }

    @Test
    public void should_update_user_correctly() throws Exception {
        // given
        currentSession.save(user);

        // when
        user.setUsername("new username");
        user.setEmail("new@email.com");
        userRepositoryHibernate.update(user);

        // then
        User obtainedUser = (User) currentSession.createCriteria(User.class).list().get(0);
        assertThat(obtainedUser.getEmail(), is(equalTo("new@email.com")));
        assertThat(obtainedUser.getUsername(), is(equalTo("new username")));
        assertThat(obtainedUser.getPassword(), is(equalTo(user.getPassword())));
    }

    @Test
    public void should_get_specified_user_from_db_by_id() throws Exception {
        // given
        Long userId = (Long) currentSession.save(user);

        // when
        User obtainedUser = userRepositoryHibernate.find(userId);

        // then
        assertThat(obtainedUser.getUsername(), is(equalTo(user.getUsername())));
        assertThat(obtainedUser.getPassword(), is(equalTo(user.getPassword())));
        assertThat(obtainedUser.getEmail(), is(equalTo(user.getEmail())));
    }

    @Test
    public void should_remove_specified_user_correctly() throws Exception {
        // given
        currentSession.save(user);
        User user1 = getUser("t@email.com", "nameofuser", "textpasswd");
        currentSession.save(user1);

        // when
        userRepositoryHibernate.remove(user);

        // then
        List<User> userList = currentSession.createCriteria(User.class).list();
        assertThat(userList.size(), is(1));

        User obtainedUser = userList.get(0);
        assertThat(obtainedUser.getId(), is(notNullValue()));
        assertThat(obtainedUser.getEmail(), is(equalTo(user1.getEmail())));
        assertThat(obtainedUser.getUsername(), is(equalTo(user1.getUsername())));
        assertThat(obtainedUser.getPassword(), is(equalTo(user1.getPassword())));
    }
}