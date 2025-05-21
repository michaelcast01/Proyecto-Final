package com.example.TiendaSuplementos.Repository;

import com.example.TiendaSuplementos.Model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByEmail(String email);
    
    @Query("SELECT DISTINCT u FROM UserDetail u JOIN u.orders o WHERE o.status_id = :statusId")
    List<UserDetail> findByOrdersStatusId(@Param("statusId") Long statusId);

    @Query("SELECT u FROM UserDetail u WHERE u.role_id = :roleId")
    List<UserDetail> findByRoleId(@Param("roleId") Long roleId);
}
