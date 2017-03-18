package org.ec.controller.services;

import org.ec.controller.models.Meetings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingsService extends JpaRepository<Meetings, Integer>{
	
}
