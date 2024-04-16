package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;

public interface IReportedUserRepository extends JpaRepository<ReportedUser, Long> {

    public void deleteByReportedUser_Id(Long userId);
}
