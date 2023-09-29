package io.doctor.clinic.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SessionRepository extends JpaRepository<Session, Integer> {

}
