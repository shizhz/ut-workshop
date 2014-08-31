package org.shizhz.domain;

/**
 * Created by zzshi on 8/31/14.
 */
public interface UserRepository {
    public void save(User user);

    public User find(Long userId);

    public void update(User user);

    public void remove(User user);
}
