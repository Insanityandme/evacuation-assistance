package com.evac.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class is an entity that creates a table in the database
 * for evacuation leaders and their priority levels
 */
@Entity
@Table(name = "evacleader_priority",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "evacleader_id")
        })

public class EvacLeaderPriority {

    public EvacLeaderPriority() {
    }

    public EvacLeaderPriority(Long evacleaderId, Long priority) {
        this.evacLeaderId = evacleaderId;
        this.priority = priority;
    }

    @Id
    @NotNull
    @Column(name = "evacleader_id")
    private Long evacLeaderId;

    @NotNull
    private Long priority;

    public Long getId() {
        return evacLeaderId;
    }

    public void setId(Long id) {
        this.evacLeaderId = id;
    }

    public Long getpriority() {
        return priority;
    }

    public void setpriority(Long priority) {
        this.priority = priority;
    }

}
