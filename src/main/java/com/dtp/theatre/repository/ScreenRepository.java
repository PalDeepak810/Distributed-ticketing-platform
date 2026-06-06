package com.dtp.theatre.repository;

import com.dtp.theatre.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen,Long> {
}
