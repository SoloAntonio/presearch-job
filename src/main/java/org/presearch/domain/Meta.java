package org.presearch.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Meta {

    @Column(length=500, unique=false)
    private String description;
    @Column(length=250, unique=false)
    private String url;
    @Column(length=500, unique=false)   
    private String serverDescription;
    @Column(length=250, unique=false)
    private String serverUrl;
    @Column(length=150, nullable=false, unique=false)
    private String gatewayPool;
    @Column(length=38, nullable=false, unique=false)
    private String remoteAddress;
    @Column(length=8, nullable=false, unique=false)
    private String version;

}
