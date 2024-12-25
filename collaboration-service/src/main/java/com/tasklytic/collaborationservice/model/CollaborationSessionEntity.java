package com.tasklytic.collaborationservice.model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("collaboration_sessions")
public class CollaborationSessionEntity {

    @PrimaryKey
    private String id; // UUID or a composite key for uniqueness in Cassandra

    @Column("task_id")
    private Long taskId;

    @Column("participants")
    private List<Long> participants; // A set or list to store participant IDs

    @Column("start_time")
    private Instant startTime;

    @Column("end_time")
    private Instant endTime;

    @Column("status")
    private String status; // e.g., "Active", "Ended"
}