package com.example.muadeeservice.repository.dto;

import com.example.commonapi.model.AbstractTimestampEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UPH_AUDIT")
public class UPHAudit extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPH_AUDIT_SEQUENCE")
    @SequenceGenerator(name = "UPH_AUDIT_SEQUENCE", sequenceName = "UPH_AUDIT_SEQUENCE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "REFERENCE_ID")
    private String referenceId;
    @Column(name = "REQUEST")
    private String request;
    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "MESSAGE")
    private String message;


}
