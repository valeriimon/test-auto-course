package ua.edu.duan.book_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.duan.book_shop.entity.WarehouseEntity;

public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
}
