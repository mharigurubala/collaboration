package dao;

import java.util.List;

import project.backend.Job;

public interface JobDAO {

	void saveJob(Job job);

	List<Job> getAllJobs();

	Job getJobById(int job);	
}
