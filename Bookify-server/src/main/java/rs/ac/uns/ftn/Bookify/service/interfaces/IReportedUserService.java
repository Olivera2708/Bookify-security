package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.model.ReportedUser;

import java.util.List;

public interface IReportedUserService {
    public List<ReportedUser> getAllReports();
    public boolean dismiss(Long reportId);
    public void deletedUsersReports(Long userId);
}
