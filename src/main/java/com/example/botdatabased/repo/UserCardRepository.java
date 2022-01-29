package com.example.botdatabased.repo;

import com.example.botdatabased.models.UserCard;
import org.springframework.data.repository.CrudRepository;

public interface UserCardRepository extends CrudRepository<UserCard,Long> {
}
