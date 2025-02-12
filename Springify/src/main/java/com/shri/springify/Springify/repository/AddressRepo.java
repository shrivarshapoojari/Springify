package com.shri.springify.Springify.repository;

import com.shri.springify.Springify.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

}
