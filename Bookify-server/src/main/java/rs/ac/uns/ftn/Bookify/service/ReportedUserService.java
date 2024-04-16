package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReportedUserRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReportedUserService;

import java.util.List;

@Service
public class ReportedUserService implements IReportedUserService {
    private final IReportedUserRepository reportedUserRepository;

    @Autowired
    public ReportedUserService(IReportedUserRepository reportedUserRepository){
        this.reportedUserRepository = reportedUserRepository;
    }

    @Override
    public List<ReportedUser> getAllReports() {
        return this.reportedUserRepository.findAll();
    }

    @Override
    public boolean dismiss(Long reportId) {
        this.reportedUserRepository.deleteById(reportId);
        return true;
    }

    @Override
    @Transactional
    public void deletedUsersReports(Long userId) {
        reportedUserRepository.deleteByReportedUser_Id(userId);
    }
}
