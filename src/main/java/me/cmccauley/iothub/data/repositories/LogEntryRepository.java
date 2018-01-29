package me.cmccauley.iothub.data.repositories;

import me.cmccauley.iothub.data.models.LogEntry;
import org.springframework.data.repository.Repository;

public interface LogEntryRepository extends Repository<LogEntry, Long> {
}
