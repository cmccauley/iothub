package me.cmccauley.iothub.repositories;

import me.cmccauley.iothub.models.LogEntry;
import org.springframework.data.repository.Repository;

public interface LogEntryRepository extends Repository<LogEntry, Long> {
}
