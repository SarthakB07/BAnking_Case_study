package com.ofss.bankapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ofss.bankapp.beans.AccountPreference;

@Repository
public interface AccountPreferenceRepository extends JpaRepository<AccountPreference, Long> {}
