package com.Hassan.app.SpringBoot.resources;

import com.Hassan.app.SpringBoot.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepoResource extends JpaRepository<Inventory, Integer> {
}
